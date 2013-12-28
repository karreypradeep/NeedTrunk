/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.payroll.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.core.service.SequenceGeneratorService;
import com.apeironsol.need.financial.service.BranchAccountService;
import com.apeironsol.need.financial.service.BranchFinancialService;
import com.apeironsol.need.hrms.dataobject.EmployeeCTCDO;
import com.apeironsol.need.hrms.dataobject.EmployeeSalaryDO;
import com.apeironsol.need.hrms.model.EmployeeCTCDetails;
import com.apeironsol.need.hrms.service.EmployeeCTCService;
import com.apeironsol.need.payroll.dao.EmployeeSalaryComponentDao;
import com.apeironsol.need.payroll.dao.EmployeeSalaryDao;
import com.apeironsol.need.payroll.dao.EmployeeSalaryDeductionDao;
import com.apeironsol.need.payroll.model.EmployeeSalary;
import com.apeironsol.need.payroll.model.EmployeeSalaryComponent;
import com.apeironsol.need.payroll.model.EmployeeSalaryDeduction;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.BranchAccountTypeConstant;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.PaymentMethodConstant;
import com.apeironsol.need.util.constants.SalaryDeductionTypeConstant;
import com.apeironsol.need.util.constants.SalaryTypeConstant;
import com.apeironsol.need.util.searchcriteria.EmployeeSalarySearchCriteria;
import com.apeironsol.framework.exception.BusinessException;

/**
 * @author Sunny
 * 
 */
@Service("employeeSalaryService")
@Transactional(rollbackFor = Exception.class)
public class EmployeeSalaryServiceImpl implements EmployeeSalaryService {

	@Resource
	private EmployeeSalaryDao			employeeSalaryDao;

	@Resource
	private EmployeeCTCService			employeeCTCService;

	@Resource
	private BuildingBlockService		buildingBlockService;

	@Resource
	private EmployeeSalaryComponentDao	employeeSalaryComponentDao;

	@Resource
	private EmployeeSalaryDeductionDao	employeeSalaryDeductionDao;

	@Resource
	SequenceGeneratorService			sequenceGeneratorService;

	@Resource
	private BranchAccountService		branchAccountService;

	@Resource
	private BranchFinancialService		branchFinancialService;

	@Override
	public EmployeeSalaryDO getEmployeeSalaryDetailsForMonth(final Long employeeId, final SalaryTypeConstant salaryTypeConstant, final Date salaryMonth)
			throws BusinessException {
		final Collection<EmployeeSalary> advanceEmployeeSalaries = this.employeeSalaryDao.findEmployeeSalariesByEmployeeIdAndMonth(employeeId,
				DateUtil.returnFirstDateOfMonth(salaryMonth));
		for (final EmployeeSalary employeeSalary : advanceEmployeeSalaries) {
			if (SalaryTypeConstant.MONTHLY.equals(employeeSalary.getSalaryType())) {
				if (SalaryTypeConstant.MONTHLY.equals(salaryTypeConstant)) {
					throw new BusinessException("Salary has already been paid for Employee " + employeeSalary.getEmployee().getDisplayName() + " for month "
							+ salaryMonth);
				} else if (SalaryTypeConstant.ADVANCE.equals(salaryTypeConstant)) {
					throw new BusinessException("Advance Salary cannot be paid for Employee " + employeeSalary.getEmployee().getDisplayName() + " for month "
							+ salaryMonth + " as salary is already paid.");
				}
			}
		}

		final EmployeeSalaryDO employeeSalaryDO = new EmployeeSalaryDO();
		final Collection<EmployeeSalaryDeduction> advanceEmployeeSalaryDeductions = new ArrayList<EmployeeSalaryDeduction>();
		final EmployeeCTCDO employeeCTCDO = this.employeeCTCService
				.findEmployeeCTCByEmployeeIdAndDate(employeeId, DateUtil.returnFirstDateOfMonth(salaryMonth));
		if (employeeCTCDO.getEmployeeCTC() == null || employeeCTCDO.getAllEmployeeCTCDefinitionDetails() == null
				|| employeeCTCDO.getAllEmployeeCTCDefinitionDetails().isEmpty()) {
			throw new BusinessException("CTC not defined for month " + salaryMonth);
		}
		employeeSalaryDO.setAdvanceEmployeeSalaries(advanceEmployeeSalaries);
		if (advanceEmployeeSalaries != null) {
			Collection<EmployeeSalaryDeduction> advanceEmpSalaryDeductions = new ArrayList<EmployeeSalaryDeduction>();
			for (final EmployeeSalary employeeSalary : advanceEmployeeSalaries) {
				advanceEmpSalaryDeductions = this.employeeSalaryDeductionDao.findEmployeeSalaryDeductionsByEmployeeSalary(employeeSalary.getId());
				if (advanceEmpSalaryDeductions != null) {
					advanceEmployeeSalaryDeductions.addAll(advanceEmpSalaryDeductions);
				}
			}

		}
		employeeSalaryDO.setAdvanceEmployeeSalaryDeductions(advanceEmployeeSalaryDeductions);
		if (SalaryTypeConstant.MONTHLY.equals(salaryTypeConstant) || SalaryTypeConstant.ADVANCE.equals(salaryTypeConstant)) {
			EmployeeSalaryComponent employeeSalaryComponent = null;
			for (final EmployeeCTCDetails employeeCTCDetails : employeeCTCDO.getEmployeeCTCDefForMonthlySalary()) {
				employeeSalaryComponent = new EmployeeSalaryComponent();
				employeeSalaryComponent.setEmployeeCTCDetails(employeeCTCDetails);
				employeeSalaryComponent.setAmount(0.0);
				employeeSalaryDO.addEmployeeSalaryComponent(employeeSalaryComponent);
			}
		}

		final Collection<BuildingBlock> salaryDeductionsBuildingBlocks = this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(
				employeeCTCDO.getEmployeeCTC().getEmployee().getBranch().getId(), BuildingBlockConstant.SALARY_DEDUCTION);
		EmployeeSalaryDeduction employeeSalaryDeduction = null;
		for (final BuildingBlock buildingBlock : salaryDeductionsBuildingBlocks) {
			employeeSalaryDeduction = new EmployeeSalaryDeduction();
			employeeSalaryDeduction.setDecutionBuildingBlock(buildingBlock);
			if (buildingBlock.getSalaryDeduction().equals(SalaryDeductionTypeConstant.ADVANCE_SALARY)) {
				employeeSalaryDeduction.setAmount(employeeSalaryDO.getTotalAdvanceSalary());
			} else {
				employeeSalaryDeduction.setAmount(0.0);
			}
			employeeSalaryDO.addEmployeeSalaryDeduction(employeeSalaryDeduction);
		}
		return employeeSalaryDO;
	}

	@Override
	public EmployeeSalaryDO saveEmployeeSalary(final EmployeeSalaryDO employeeSalaryDO) throws BusinessException {

		this.branchFinancialService.checkIfBranchFinancialTransactionsAreAllowed(employeeSalaryDO.getEmployeeSalary().getEmployee().getBranch().getId(),
				employeeSalaryDO.getEmployeeSalary().getPaidDate());

		this.validateSalary(employeeSalaryDO);
		EmployeeSalary employeeSalary = employeeSalaryDO.getEmployeeSalary();
		employeeSalary.setSalaryMonth(DateUtil.returnFirstDateOfMonth(employeeSalary.getSalaryMonth()));
		if (employeeSalary.getId() == null) {
			employeeSalary.setTransactionNr(this.sequenceGeneratorService.getNextTransactionNumberForSalaryPaid());
		}
		employeeSalary.setAmount(employeeSalaryDO.getNetSalary() - employeeSalaryDO.getTotalDeduction());
		employeeSalary = this.employeeSalaryDao.persist(employeeSalary);
		EmployeeSalaryComponent employeeSalaryComponent;
		EmployeeSalaryDeduction employeeSalaryDeduction;
		if (employeeSalaryDO.getEmployeeSalaryComponents() != null) {
			for (final EmployeeSalaryComponent salaryComponent : employeeSalaryDO.getEmployeeSalaryComponents()) {
				if (salaryComponent.getAmount() > 0) {
					employeeSalaryComponent = new EmployeeSalaryComponent();
					employeeSalaryComponent.setEmployeeSalary(employeeSalary);
					employeeSalaryComponent.setEmployeeCTCDetails(salaryComponent.getEmployeeCTCDetails());
					employeeSalaryComponent.setAmount(salaryComponent.getAmount());
					this.employeeSalaryComponentDao.persist(employeeSalaryComponent);
				}
			}
		}
		if (employeeSalaryDO.getEmployeeSalaryDeductions() != null) {
			for (final EmployeeSalaryDeduction salaryDeductionComponent : employeeSalaryDO.getEmployeeSalaryDeductions()) {
				if (salaryDeductionComponent.getAmount() > 0
						&& !SalaryDeductionTypeConstant.ADVANCE_SALARY.equals(salaryDeductionComponent.getDecutionBuildingBlock().getSalaryDeduction())) {
					employeeSalaryDeduction = new EmployeeSalaryDeduction();
					employeeSalaryDeduction.setEmployeeSalary(employeeSalary);
					employeeSalaryDeduction.setDecutionBuildingBlock(salaryDeductionComponent.getDecutionBuildingBlock());
					employeeSalaryDeduction.setAmount(salaryDeductionComponent.getAmount());
					this.employeeSalaryDeductionDao.persist(employeeSalaryDeduction);
				}
			}
		}
		if (PaymentMethodConstant.CHEQUE.equals(employeeSalary.getPaymentMethod())) {
			this.branchAccountService.updateBranchAccountTransaction(employeeSalary);
		}
		return null;
	}

	private void validateSalary(final EmployeeSalaryDO employeeSalaryDO) throws BusinessException {
		final Collection<EmployeeSalary> advanceEmployeeSalaries = this.employeeSalaryDao.findEmployeeSalariesByEmployeeIdAndMonth(employeeSalaryDO
				.getEmployeeSalary().getEmployee().getId(), DateUtil.returnFirstDateOfMonth(employeeSalaryDO.getEmployeeSalary().getPaidDate()));
		for (final EmployeeSalary employeeSalary : advanceEmployeeSalaries) {
			if (employeeSalary.getPaidDate().after(employeeSalaryDO.getEmployeeSalary().getPaidDate())
					|| employeeSalary.getPaidDate().equals(employeeSalaryDO.getEmployeeSalary().getPaidDate())) {
				throw new BusinessException("Please enter paid date after " + employeeSalary.getPaidDate() + ".");
			}
		}

		if (employeeSalaryDO.getTakeHomeSalary() == 0) {
			throw new BusinessException("Net payment should be more than zero.");
		}
		if (employeeSalaryDO.getTakeHomeSalary() < 0) {
			throw new BusinessException("Net salary is less than todatl deductions.");
		}
		if (employeeSalaryDO.getEmployeeSalaryComponents() != null) {
			for (final EmployeeSalaryComponent employeeSalaryComponent : employeeSalaryDO.getEmployeeSalaryComponents()) {
				final Double frequencyAmount = employeeSalaryComponent.getEmployeeCTCDetails().getAmount()
						/ (employeeSalaryComponent.getEmployeeCTCDetails().getSalaryPaymentFrequency() != null ? employeeSalaryComponent
								.getEmployeeCTCDetails().getSalaryPaymentFrequency().getFrequency() : 12);
				if (frequencyAmount < employeeSalaryComponent.getAmount()) {
					throw new BusinessException("Net salary for " + employeeSalaryComponent.getEmployeeCTCDetails().getCtcDefinitionType().getName()
							+ " is more than gross salary.");
				}
			}
		}
	}

	@Override
	public Collection<EmployeeSalary> findEmployeeSalariesBySearchCriteria(final EmployeeSalarySearchCriteria employeeSalarySearchCriteria)
			throws BusinessException {
		return this.employeeSalaryDao.findEmployeeSalariesBySearchCriteria(employeeSalarySearchCriteria);
	}

	@Override
	public EmployeeSalaryDO getEmployeeSalaryDetailsById(final Long employeeSalaryId) throws BusinessException {
		final EmployeeSalary employeeSalary = this.employeeSalaryDao.findById(employeeSalaryId);
		final EmployeeSalaryDO employeeSalaryDO = new EmployeeSalaryDO();
		final EmployeeCTCDO employeeCTCDO = this.employeeCTCService.findEmployeeCTCByEmployeeIdAndDate(employeeSalary.getEmployee().getId(),
				employeeSalary.getSalaryMonth());

		final Collection<EmployeeSalary> advanceEmployeeSalaries = this.employeeSalaryDao.findEmployeeSalariesByEmployeeIdAndMonth(employeeSalary.getEmployee()
				.getId(), DateUtil.returnFirstDateOfMonth(employeeSalary.getSalaryMonth()));
		final Collection<EmployeeSalaryDeduction> advanceEmployeeSalaryDeductions = new ArrayList<EmployeeSalaryDeduction>();
		for (final EmployeeSalary advEmpSal : advanceEmployeeSalaries) {
			if ((!employeeSalary.getId().equals(advEmpSal.getId())) && advEmpSal.getPaidDate().before(employeeSalary.getPaidDate())) {
				employeeSalaryDO.addAdvanceEmployeeSalary(advEmpSal);
			}
		}
		if (advanceEmployeeSalaries != null) {
			Collection<EmployeeSalaryDeduction> advanceEmpSalaryDeductions = new ArrayList<EmployeeSalaryDeduction>();
			for (final EmployeeSalary empSalary : advanceEmployeeSalaries) {
				if (empSalary.getPaidDate().before(employeeSalary.getPaidDate())) {
					advanceEmpSalaryDeductions = this.employeeSalaryDeductionDao.findEmployeeSalaryDeductionsByEmployeeSalary(empSalary.getId());
					if (advanceEmpSalaryDeductions != null) {
						advanceEmployeeSalaryDeductions.addAll(advanceEmpSalaryDeductions);
					}
				}
			}

		}
		employeeSalaryDO.setAdvanceEmployeeSalaryDeductions(advanceEmployeeSalaryDeductions);

		final Collection<EmployeeSalaryComponent> employeeSalaryComponents = this.employeeSalaryComponentDao
				.findEmployeeSalaryComponentsByEmployeeSalary(employeeSalary.getId());
		final Map<Long, EmployeeSalaryComponent> employeeSalaryComponentsMap = new HashMap<Long, EmployeeSalaryComponent>();

		for (final EmployeeSalaryComponent employeeSalaryComponent : employeeSalaryComponents) {
			employeeSalaryComponentsMap.put(employeeSalaryComponent.getEmployeeCTCDetails().getId(), employeeSalaryComponent);
		}

		if (SalaryTypeConstant.MONTHLY.equals(employeeSalary.getSalaryType()) || SalaryTypeConstant.ADVANCE.equals(employeeSalary.getSalaryType())) {
			EmployeeSalaryComponent employeeSalaryComponent = null;
			for (final EmployeeCTCDetails employeeCTCDetails : employeeCTCDO.getEmployeeCTCDefForMonthlySalary()) {
				employeeSalaryComponent = new EmployeeSalaryComponent();
				employeeSalaryComponent.setEmployeeCTCDetails(employeeCTCDetails);
				employeeSalaryComponent.setAmount(employeeSalaryComponentsMap.get(employeeCTCDetails.getId()) != null ? employeeSalaryComponentsMap.get(
						employeeCTCDetails.getId()).getAmount() : 0);
				employeeSalaryDO.addEmployeeSalaryComponent(employeeSalaryComponent);
			}
		}

		final Collection<BuildingBlock> salaryDeductionsBuildingBlocks = this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(
				employeeCTCDO.getEmployeeCTC().getEmployee().getBranch().getId(), BuildingBlockConstant.SALARY_DEDUCTION);
		final Collection<EmployeeSalaryDeduction> employeeSalaryDeductions = this.employeeSalaryDeductionDao
				.findEmployeeSalaryDeductionsByEmployeeSalary(employeeSalary.getId());
		final Map<Long, EmployeeSalaryDeduction> employeeSalaryDeductionsMap = new HashMap<Long, EmployeeSalaryDeduction>();

		for (final EmployeeSalaryDeduction employeeSalaryDeduction : employeeSalaryDeductions) {
			employeeSalaryDeductionsMap.put(employeeSalaryDeduction.getDecutionBuildingBlock().getId(), employeeSalaryDeduction);
		}

		EmployeeSalaryDeduction employeeSalaryDeduction = null;
		for (final BuildingBlock buildingBlock : salaryDeductionsBuildingBlocks) {
			employeeSalaryDeduction = new EmployeeSalaryDeduction();
			employeeSalaryDeduction.setDecutionBuildingBlock(buildingBlock);
			if (buildingBlock.getSalaryDeduction().equals(SalaryDeductionTypeConstant.ADVANCE_SALARY)) {
				employeeSalaryDeduction.setAmount(employeeSalaryDO.getTotalAdvanceSalary());
			} else {
				employeeSalaryDeduction.setAmount((employeeSalaryDeductionsMap.get(buildingBlock.getId()) != null ? employeeSalaryDeductionsMap.get(
						buildingBlock.getId()).getAmount() : 0.0));
			}
			employeeSalaryDO.addEmployeeSalaryDeduction(employeeSalaryDeduction);
		}
		return employeeSalaryDO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeEmployeeSalary(final EmployeeSalary employeeSalary) throws BusinessException {
		this.branchFinancialService
				.checkIfBranchFinancialTransactionsAreAllowed(employeeSalary.getEmployee().getBranch().getId(), employeeSalary.getPaidDate());
		if (employeeSalary.getId() != null && PaymentMethodConstant.CHEQUE.equals(employeeSalary.getPaymentMethod())) {
			Long accountId = null;
			if (BranchAccountTypeConstant.BANK_ACCOUNT.equals(employeeSalary.getBranchAccountType())) {
				accountId = employeeSalary.getBranchBankAccount().getId();
			} else {
				accountId = employeeSalary.getBranchCreditAccount().getId();
			}
			this.branchAccountService.deleteBranchAccountTransactionByExternalTransactionAndAccountId(employeeSalary.getBranchAccountType(), accountId,
					employeeSalary.getTransactionNr());
		}
		final Collection<EmployeeSalaryComponent> employeeSalaryComponents = this.employeeSalaryComponentDao
				.findEmployeeSalaryComponentsByEmployeeSalary(employeeSalary.getId());
		for (final EmployeeSalaryComponent employeeSalaryComponent : employeeSalaryComponents) {
			this.employeeSalaryComponentDao.remove(employeeSalaryComponent);
		}
		final Collection<EmployeeSalaryDeduction> employeeSalaryDeductions = this.employeeSalaryDeductionDao
				.findEmployeeSalaryDeductionsByEmployeeSalary(employeeSalary.getId());
		for (final EmployeeSalaryDeduction employeeSalaryDeduction : employeeSalaryDeductions) {
			this.employeeSalaryDeductionDao.remove(employeeSalaryDeduction);
		}
		this.employeeSalaryDao.remove(employeeSalary);
	}
}
