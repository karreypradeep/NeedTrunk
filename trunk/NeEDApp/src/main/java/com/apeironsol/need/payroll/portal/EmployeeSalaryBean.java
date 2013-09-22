/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.payroll.portal;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.portal.util.ViewContentHandlerBean;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.financial.model.BranchBankAccount;
import com.apeironsol.need.financial.model.BranchCreditAccount;
import com.apeironsol.need.financial.service.BranchBankAccountService;
import com.apeironsol.need.financial.service.BranchCreditAccountService;
import com.apeironsol.need.hrms.dataobject.EmployeeDO;
import com.apeironsol.need.hrms.dataobject.EmployeeSalaryDO;
import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.need.hrms.portal.EmployeeBean;
import com.apeironsol.need.hrms.service.EmployeeService;
import com.apeironsol.need.payroll.model.EmployeeSalary;
import com.apeironsol.need.payroll.service.EmployeeSalaryService;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.SalaryDeductionTypeConstant;
import com.apeironsol.need.util.constants.ViewContentConstant;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.EmployeeSalarySearchCriteria;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * @author Sunny
 *
 */
@Named
@Scope("session")
public class EmployeeSalaryBean extends AbstractTabbedBean {

	/**
	 *
	 */
	private static final long				serialVersionUID				= 7390804078219558689L;

	@Resource
	private EmployeeSalaryService			employeeSalaryService;

	private boolean							loadEmployeeSalaryFlag;

	@Resource
	private EmployeeService					employeeService;

	private EmployeeDO						currentEmployeeDO;

	private Employee						currentEmployee;

	private boolean							loadEmployeeSalaryDetails;

	private EmployeeSalaryDO				currentEmployeeSalaryDO;

	private EmployeeSalary					employeeSalary;

	private Collection<EmployeeSalary>		employeeSalariesBySearchCriteria;

	private Collection<Employee>			branchEmployees;

	@Resource
	private BuildingBlockService			buildingBlockService;

	/**
	 * Collection to hold building block type designations.
	 */
	private Collection<BuildingBlock>		buildingBlockTypeDesignations;

	/**
	 * Building blocks for type departments.
	 */
	private Collection<BuildingBlock>		buildingBlockTypeDepartments;

	private boolean							loadEmployeeBuildingBlockTypes	= false;

	/**
	 * Student search criteria.
	 */
	private EmployeeSalarySearchCriteria	employeeSalarySearchCriteria	= null;

	@Resource
	private ViewContentHandlerBean			viewContentHandlerBean;

	@Resource
	private EmployeeBean					employeeBean;

	/**
	 * Building blocks for type expenses.
	 */
	private Collection<BranchBankAccount>	branchBakAccounts;

	@Resource
	private BranchBankAccountService		branchBankAccountService;

	/**
	 * Branch expense service.
	 */
	@Resource
	private BranchCreditAccountService		branchCreditAccountService;

	/**
	 * Building blocks for type expenses.
	 */
	private Collection<BranchCreditAccount>	branchCreditAccounts;

	/**
	 * @return the loadEmployeeSalaryFlag
	 */
	public boolean isLoadEmployeeSalaryFlag() {
		return this.loadEmployeeSalaryFlag;
	}

	/**
	 * @param loadEmployeeSalaryFlag
	 *            the loadEmployeeSalaryFlag to set
	 */
	public void setLoadEmployeeSalaryFlag(final boolean loadEmployeeSalaryFlag) {
		this.loadEmployeeSalaryFlag = loadEmployeeSalaryFlag;
	}

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.employeeSalarySearchCriteria == null) {
			this.employeeSalarySearchCriteria = new EmployeeSalarySearchCriteria(this.sessionBean.getCurrentBranch());
		}
	}

	@Override
	public void onTabChange() {
		this.setViewOrNewAction(false);
		this.loadEmployeeSalaryFlag = true;
		this.currentEmployee = null;
		this.currentEmployeeSalaryDO = null;
	}

	public void loadEmployeeSalaries() {
		if (this.loadEmployeeSalaryFlag) {
			this.loadEmployeeSalaryFlag = false;
		}
	}

	public void createEmployeeSalary() {
		this.setEmployeeSalary(new EmployeeSalary());
		this.currentEmployee = null;
		this.currentEmployeeDO = null;
		this.setViewOrNewAction(true);
		this.loadEmployeeSalaryDetails = false;
		this.currentEmployeeSalaryDO = null;
	}

	public void getEmployeeSalaryDetails() {
		try {
			this.currentEmployeeSalaryDO = this.employeeSalaryService.getEmployeeSalaryDetailsForMonth(this.currentEmployee.getId(), this.getEmployeeSalary()
					.getSalaryType(), this.getEmployeeSalary().getSalaryMonth());
			this.loadEmployeeSalaryDetails = true;
		} catch (final ApplicationException ae) {
			ViewExceptionHandler.handle(ae);
		}
	}

	public void showEmployeeSalaryDetails() {
		try {
			this.currentEmployee = this.getEmployeeSalary().getEmployee();
			this.currentEmployeeSalaryDO = this.employeeSalaryService.getEmployeeSalaryDetailsById(this.getEmployeeSalary().getId());
			this.setViewOrNewAction(true);
			this.setCurrentEmployeeDO(this.employeeService.findEmployeeDetailsById(this.currentEmployee.getId()));
			this.loadEmployeeSalaryDetails = true;
		} catch (final ApplicationException ae) {
			ViewExceptionHandler.handle(ae);
		}
	}

	public void removeEmployeeSalary() {
		this.loadEmployeeSalaryFlag = true;
		this.loadEmployeeSalaries();
		ViewUtil.addMessage("Employee salary deleted sucessfully.", FacesMessage.SEVERITY_INFO);
	}

	public void saveEmployeeSalary() {
		try {
			this.getEmployeeSalary().setEmployee(this.currentEmployee);
			this.currentEmployeeSalaryDO.setEmployeeSalary(this.getEmployeeSalary());
			this.employeeSalaryService.saveEmployeeSalary(this.currentEmployeeSalaryDO);
			this.loadEmployeeSalaryFlag = true;
			this.loadEmployeeSalaries();
			this.setViewOrNewAction(false);
			ViewUtil.addMessage("Employee salary saved sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (final ApplicationException ae) {
			ViewExceptionHandler.handle(ae);
		}
	}

	public void handleEmployeeChange() {
		this.setCurrentEmployeeDO(this.employeeService.findEmployeeDetailsById(this.currentEmployee.getId()));
		this.setEmployeeSalary(new EmployeeSalary());
		this.getEmployeeSalary().setEmployee(this.currentEmployee);
		this.currentEmployeeSalaryDO = null;
		this.loadEmployeeSalaryDetails = false;
	}

	public void handleSalaryTypeChange() {
		this.getEmployeeSalary().setSalaryMonth(null);
		this.handleSalaryMonthChange();
	}

	public void handleSalaryMonthChange() {
		this.getEmployeeSalary().setPaidDate(null);
		this.currentEmployeeSalaryDO = null;
		this.loadEmployeeSalaryDetails = false;
	}

	/**
	 * @return the currentEmployeeDO
	 */
	public EmployeeDO getCurrentEmployeeDO() {
		return this.currentEmployeeDO;
	}

	/**
	 * @param currentEmployeeDO
	 *            the currentEmployeeDO to set
	 */
	public void setCurrentEmployeeDO(final EmployeeDO currentEmployeeDO) {
		this.currentEmployeeDO = currentEmployeeDO;
	}

	/**
	 * @return the currentEmployee
	 */
	public Employee getCurrentEmployee() {
		return this.currentEmployee;
	}

	/**
	 * @param currentEmployee
	 *            the currentEmployee to set
	 */
	public void setCurrentEmployee(final Employee currentEmployee) {
		this.currentEmployee = currentEmployee;
	}

	/**
	 * @return the loadEmployeeSalaryDetails
	 */
	public boolean isLoadEmployeeSalaryDetails() {
		return this.loadEmployeeSalaryDetails;
	}

	/**
	 * @param loadEmployeeSalaryDetails
	 *            the loadEmployeeSalaryDetails to set
	 */
	public void setLoadEmployeeSalaryDetails(final boolean loadEmployeeSalaryDetails) {
		this.loadEmployeeSalaryDetails = loadEmployeeSalaryDetails;
	}

	/**
	 * @return the currentEmployeeSalaryDO
	 */
	public EmployeeSalaryDO getCurrentEmployeeSalaryDO() {
		return this.currentEmployeeSalaryDO;
	}

	/**
	 * @param currentEmployeeSalaryDO
	 *            the currentEmployeeSalaryDO to set
	 */
	public void setCurrentEmployeeSalaryDO(final EmployeeSalaryDO currentEmployeeSalaryDO) {
		this.currentEmployeeSalaryDO = currentEmployeeSalaryDO;
	}

	/**
	 * @return the employeeSalary
	 */
	public EmployeeSalary getEmployeeSalary() {
		return this.employeeSalary;
	}

	/**
	 * @param employeeSalary
	 *            the employeeSalary to set
	 */
	public void setEmployeeSalary(final EmployeeSalary employeeSalary) {
		this.employeeSalary = employeeSalary;
	}

	public boolean isDeductionDisabled(final SalaryDeductionTypeConstant deductionTypeConstant, final EmployeeSalary employeeSalary) {
		return SalaryDeductionTypeConstant.ADVANCE_SALARY.equals(deductionTypeConstant) || employeeSalary.getId() != null;
	}

	/**
	 * @return the employeeSalarySearchCriteria
	 */
	public EmployeeSalarySearchCriteria getEmployeeSalarySearchCriteria() {
		return this.employeeSalarySearchCriteria;
	}

	public String resetSearchCriteria() {
		this.employeeSalarySearchCriteria.resetSeacrhCriteria();
		return null;
	}

	public String searchEmployeeSalariesBySearchCriteria() {

		if (this.employeeSalarySearchCriteria.isSearchCriteriaIsEmpty()) {
			ViewUtil.addMessage("Please enter search criteria.", FacesMessage.SEVERITY_ERROR);
		} else {
			this.employeeSalarySearchCriteria.setBranch(this.sessionBean.getCurrentBranch());
			this.setEmployeeSalariesBySearchCriteria(this.employeeSalaryService.findEmployeeSalariesBySearchCriteria(this.employeeSalarySearchCriteria));
			if (this.getEmployeeSalariesBySearchCriteria() == null || this.getEmployeeSalariesBySearchCriteria().isEmpty()) {
				ViewUtil.addMessage("No employee salaries found for entered search criteria..", FacesMessage.SEVERITY_INFO);
			}
		}
		return null;
	}

	/**
	 * @return the employeeSalariesBySearchCriteria
	 */
	public Collection<EmployeeSalary> getEmployeeSalariesBySearchCriteria() {
		return this.employeeSalariesBySearchCriteria;
	}

	/**
	 * @param employeeSalariesBySearchCriteria
	 *            the employeeSalariesBySearchCriteria to set
	 */
	public void setEmployeeSalariesBySearchCriteria(final Collection<EmployeeSalary> employeeSalariesBySearchCriteria) {
		this.employeeSalariesBySearchCriteria = employeeSalariesBySearchCriteria;
	}

	public void loadEmployeeBuildingBlocks() {
		if (this.isLoadEmployeeBuildingBlockTypes()) {
			this.setBuildingBlockTypeDesignations(this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(this.sessionBean
					.getCurrentBranch().getId(), BuildingBlockConstant.DESIGNATION));
			this.setBuildingBlockTypeDepartments(this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(this.sessionBean.getCurrentBranch()
					.getId(), BuildingBlockConstant.DEPARTMENT));
			this.setBranchEmployees(this.employeeService.findAllEmployeesByBranchId(this.sessionBean.getCurrentBranch().getId()));
			this.branchBakAccounts = this.branchBankAccountService.findBranchBankAccountByBranchId(this.sessionBean.getCurrentBranch().getId());
			this.branchCreditAccounts = this.branchCreditAccountService.findBranchCreditAccountByBranchId(this.sessionBean.getCurrentBranch().getId());
			this.loadEmployeeBuildingBlockTypes = false;
		}
	}

	/**
	 * @return the buildingBlockTypeDesignations
	 */
	public Collection<BuildingBlock> getBuildingBlockTypeDesignations() {
		return this.buildingBlockTypeDesignations;
	}

	/**
	 * @param buildingBlockTypeDesignations
	 *            the buildingBlockTypeDesignations to set
	 */
	public void setBuildingBlockTypeDesignations(final Collection<BuildingBlock> buildingBlockTypeDesignations) {
		this.buildingBlockTypeDesignations = buildingBlockTypeDesignations;
	}

	/**
	 * @return the buildingBlockTypeDepartments
	 */
	public Collection<BuildingBlock> getBuildingBlockTypeDepartments() {
		return this.buildingBlockTypeDepartments;
	}

	/**
	 * @param buildingBlockTypeDepartments
	 *            the buildingBlockTypeDepartments to set
	 */
	public void setBuildingBlockTypeDepartments(final Collection<BuildingBlock> buildingBlockTypeDepartments) {
		this.buildingBlockTypeDepartments = buildingBlockTypeDepartments;
	}

	/**
	 * @return the loadEmployeeBuildingBlockTypes
	 */
	public boolean isLoadEmployeeBuildingBlockTypes() {
		return this.loadEmployeeBuildingBlockTypes;
	}

	/**
	 * @param loadEmployeeBuildingBlockTypes
	 *            the loadEmployeeBuildingBlockTypes to set
	 */
	public void setLoadEmployeeBuildingBlockTypes(final boolean loadEmployeeBuildingBlockTypes) {
		this.loadEmployeeBuildingBlockTypes = loadEmployeeBuildingBlockTypes;
	}

	public void viewEmployee(final Employee employee) {
		this.viewContentHandlerBean.setCurrentViewContent(ViewContentConstant.BRANCH_EMPLOYEES);
		EmployeeDO employeeDO = this.employeeService.findEmployeeDetailsById(employee.getId());
		this.employeeBean.setEmployeeDO(employeeDO);
		this.employeeBean.showEmployee();
	}

	/**
	 * @return the branchEmployees
	 */
	public Collection<Employee> getBranchEmployees() {
		return this.branchEmployees;
	}

	/**
	 * @param branchEmployees
	 *            the branchEmployees to set
	 */
	public void setBranchEmployees(final Collection<Employee> branchEmployees) {
		this.branchEmployees = branchEmployees;
	}

	public void handlePaymentModeChange() {
		this.employeeSalary.setChequeNumber(null);
		this.employeeSalary.setBranchBankAccount(null);
	}

	public void handleBranchAccountTypeChange() {
		this.employeeSalary.setChequeNumber(null);
		this.employeeSalary.setBranchBankAccount(null);
		this.employeeSalary.setBranchCreditAccount(null);
	}

	/**
	 * @return the branchBakAccounts
	 */
	public Collection<BranchBankAccount> getBranchBakAccounts() {
		return this.branchBakAccounts;
	}

	/**
	 * @param branchBakAccounts
	 *            the branchBakAccounts to set
	 */
	public void setBranchBakAccounts(final Collection<BranchBankAccount> branchBakAccounts) {
		this.branchBakAccounts = branchBakAccounts;
	}

	public void deleteEmployeeSalary() {
		this.employeeSalaryService.removeEmployeeSalary(this.employeeSalary);
		this.searchEmployeeSalariesBySearchCriteria();
		ViewUtil.addMessage("Employee Salary deleted sucessfully.", FacesMessage.SEVERITY_INFO);
	}

	public Collection<BranchCreditAccount> getBranchCreditAccounts() {
		return this.branchCreditAccounts;
	}

	public void setBranchCreditAccounts(final Collection<BranchCreditAccount> branchCreditAccounts) {
		this.branchCreditAccounts = branchCreditAccounts;
	}
}
