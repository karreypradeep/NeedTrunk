/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.hrms.portal;

import java.util.Collection;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.hrms.dataobject.EmployeeCTCDO;
import com.apeironsol.need.hrms.model.EmployeeCTC;
import com.apeironsol.need.hrms.model.EmployeeCTCDetails;
import com.apeironsol.need.hrms.service.EmployeeCTCService;
import com.apeironsol.need.hrms.service.EmployeeService;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.EmployeeCTCDefinitionTypeConstant;
import com.apeironsol.need.util.constants.SalaryFrequencyConstant;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * @author Sunny
 *
 */
@Named
@Scope("session")
public class EmployeeCTCBean extends AbstractTabbedBean {

	/**
	 *
	 */
	private static final long			serialVersionUID	= 7390804078219558689L;

	@Resource
	private EmployeeBean				employeeBean;

	@Resource
	private BuildingBlockService		buildingBlockService;

	@Resource
	private EmployeeCTCService			employeeCTCService;

	@Resource
	private EmployeeService				employeeService;

	private boolean						loadEmployeeSalaryFlag;

	private boolean						loadEmployeeCTCDefinitionBB;

	private Collection<BuildingBlock>	buildingBlockTypeCTCDef;

	private EmployeeCTC					employeeCTC;

	private Collection<EmployeeCTC>		employeeCTCsByEmployee;

	private EmployeeCTCDO				employeeCTCDO;

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

	@Override
	public void onTabChange() {
		this.setViewOrNewAction(false);
		this.loadEmployeeSalaryFlag = true;
		this.loadEmployeeSalaries();
		this.loadBuildingBlockTypeEmployeeCTCDefinition();
	}

	public void loadEmployeeSalaries() {
		if (this.loadEmployeeSalaryFlag) {
			this.employeeCTCsByEmployee = this.employeeCTCService.findAllEmployeeCTCsByEmployeeID(this.employeeBean.getEmployee().getId());
			this.loadEmployeeSalaryFlag = false;
		}
	}

	public void createEmployeeCTC() {
		this.employeeCTC = new EmployeeCTC();
		this.employeeCTC.setEmployee(this.employeeBean.getEmployee());
		if (this.employeeCTCsByEmployee == null || this.employeeCTCsByEmployee.isEmpty()) {
			this.employeeCTC.setStartDate(this.employeeBean.getEmployeeDO().getJoiningDate());
		}
		this.setEmployeeCTCDO(new EmployeeCTCDO());
		this.getEmployeeCTCDO().setEmployeeCTC(this.employeeCTC);
		this.setViewOrNewAction(true);
		this.populateCTCDefinitionTypes();
	}

	public void showEmployeeCTC() {
		this.setEmployeeCTCDO(new EmployeeCTCDO());
		this.getEmployeeCTCDO().setEmployeeCTC(this.employeeCTCService.findEmployeeCTCById(this.employeeCTC.getId()));
		this.setViewOrNewAction(true);
		this.populateCTCDefinitionTypes();
	}

	public void removeEmployeeCTC() {
		this.employeeCTCService.removeEmployeeCTC(this.employeeCTC);
		this.loadEmployeeSalaryFlag = true;
		this.loadEmployeeSalaries();
		ViewUtil.addMessage("Employee CTC deleted sucessfully.", FacesMessage.SEVERITY_INFO);
	}

	public void saveEmployeeCTC() {
		if (this.employeeCTC.getTotalCTC() > 0) {
			try {
				this.employeeCTC.setEmployee(this.employeeBean.getEmployee());
				this.employeeCTCDO = this.employeeCTCService.saveEmployeeCTC(this.getEmployeeCTCDO());
				this.employeeCTC = this.employeeCTCDO.getEmployeeCTC();
				this.loadEmployeeSalaryFlag = true;
				this.loadEmployeeSalaries();
				this.setViewOrNewAction(false);
				ViewUtil.addMessage("Employee CTC saved sucessfully.", FacesMessage.SEVERITY_INFO);
			} catch (ApplicationException be) {
				ViewExceptionHandler.handle(be);
			}

		} else {
			ViewUtil.addMessage("Employee CTC should be greater than 0.", FacesMessage.SEVERITY_ERROR);
		}
	}

	public void loadBuildingBlockTypeEmployeeCTCDefinition() {
		if (this.isLoadEmployeeCTCDefinitionBB()) {
			this.buildingBlockTypeCTCDef = this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(this.sessionBean.getCurrentBranch()
					.getId(), BuildingBlockConstant.EMPLOYEE_CTC_DEFINITION_TYPE);
		}
	}

	private void populateCTCDefinitionTypes() {
		if (this.buildingBlockTypeCTCDef != null && !this.buildingBlockTypeCTCDef.isEmpty()) {
			EmployeeCTCDetails employeeCTCDetails = null;
			boolean existingEmployeeCTCDetails = false;
			for (BuildingBlock buildingBlock : this.buildingBlockTypeCTCDef) {
				existingEmployeeCTCDetails = false;
				if (this.employeeCTCDO.getEmployeeCTCDefinitionDetailsByBBId(buildingBlock) != null) {
					employeeCTCDetails = this.employeeCTCDO.getEmployeeCTCDefinitionDetailsByBBId(buildingBlock);
					existingEmployeeCTCDetails = true;
				} else {
					employeeCTCDetails = new EmployeeCTCDetails();
					employeeCTCDetails.setCtcDefinitionType(buildingBlock);
					employeeCTCDetails.setEmployeeCTC(this.employeeCTC);
				}

				if (EmployeeCTCDefinitionTypeConstant.MONTHLY_SALARY_STRUCTURE.equals(buildingBlock.getCtcDefinitionType())) {
					if (!existingEmployeeCTCDetails) {
						employeeCTCDetails.setSalaryPaymentFrequency(SalaryFrequencyConstant.MONTHLY);
					}
					this.getEmployeeCTCDO().addMonthlySalaryType(employeeCTCDetails);
				} else if (EmployeeCTCDefinitionTypeConstant.ANNUAL_BENEFITS.equals(buildingBlock.getCtcDefinitionType())) {
					if (!existingEmployeeCTCDetails) {
						employeeCTCDetails.setSalaryPaymentFrequency(SalaryFrequencyConstant.YEARLY);
					}
					this.getEmployeeCTCDO().addAnnualBenefitType(employeeCTCDetails);
				} else if (EmployeeCTCDefinitionTypeConstant.ADDITIONAL_BENEFITS.equals(buildingBlock.getCtcDefinitionType())) {
					if (!existingEmployeeCTCDetails) {
						employeeCTCDetails.setSalaryPaymentFrequency(SalaryFrequencyConstant.YEARLY);
					}
					this.getEmployeeCTCDO().addAdditionalBenefitType(employeeCTCDetails);
				} else if (EmployeeCTCDefinitionTypeConstant.RETTIRAL_BENEFITS.equals(buildingBlock.getCtcDefinitionType())) {
					if (!existingEmployeeCTCDetails) {
						employeeCTCDetails.setSalaryPaymentFrequency(SalaryFrequencyConstant.YEARLY);
					}
					this.getEmployeeCTCDO().addRetiralBenefitType(employeeCTCDetails);
				}
			}
		}
	}

	/**
	 * @return the loadEmployeeCTCDefinitionBB
	 */
	public boolean isLoadEmployeeCTCDefinitionBB() {
		return this.loadEmployeeCTCDefinitionBB;
	}

	/**
	 * @param loadEmployeeCTCDefinitionBB
	 *            the loadEmployeeCTCDefinitionBB to set
	 */
	public void setLoadEmployeeCTCDefinitionBB(final boolean loadEmployeeCTCDefinitionBB) {
		this.loadEmployeeCTCDefinitionBB = loadEmployeeCTCDefinitionBB;
	}

	/**
	 * @return the employeeCTC
	 */
	public EmployeeCTC getEmployeeCTC() {
		return this.employeeCTC;
	}

	/**
	 * @param employeeCTC
	 *            the employeeCTC to set
	 */
	public void setEmployeeCTC(final EmployeeCTC employeeCTC) {
		this.employeeCTC = employeeCTC;
	}

	/**
	 * @return the employeeCTCsByEmployee
	 */
	public Collection<EmployeeCTC> getEmployeeCTCsByEmployee() {
		return this.employeeCTCsByEmployee;
	}

	/**
	 * @param employeeCTCsByEmployee
	 *            the employeeCTCsByEmployee to set
	 */
	public void setEmployeeCTCsByEmployee(final Collection<EmployeeCTC> employeeCTCsByEmployee) {
		this.employeeCTCsByEmployee = employeeCTCsByEmployee;
	}

	/**
	 * @return the employeeCTCDO
	 */
	public EmployeeCTCDO getEmployeeCTCDO() {
		return this.employeeCTCDO;
	}

	/**
	 * @param employeeCTCDO
	 *            the employeeCTCDO to set
	 */
	public void setEmployeeCTCDO(final EmployeeCTCDO employeeCTCDO) {
		this.employeeCTCDO = employeeCTCDO;
	}

}
