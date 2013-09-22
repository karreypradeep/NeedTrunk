/**
 *
 */
package com.apeironsol.need.hrms.portal;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.hrms.model.EmployeeDesignation;
import com.apeironsol.need.hrms.service.EmployeeDesignationService;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;

/**
 * @author Sunny
 *
 */
@Named
@Scope("session")
public class EmployeeDesignationBean extends AbstractTabbedBean {

	/**
	 *
	 */
	private static final long				serialVersionUID	= 7756735748266594962L;

	@Resource
	private EmployeeDesignationService		employeeDesignationService;

	@Resource
	private EmployeeBean					employeeBean;

	private boolean							loadEmployeeDesignationsFlag;

	private EmployeeDesignation				employeeDesignation;

	private Collection<EmployeeDesignation>	employeeDesignations;

	private Date							designationCloseDate;

	/**
	 * @return the employeeDesignation
	 */
	public EmployeeDesignation getEmployeeDesignation() {
		return this.employeeDesignation;
	}

	/**
	 * @param employeeDesignation
	 *            the employeeDesignation to set
	 */
	public void setEmployeeDesignation(final EmployeeDesignation employeeDesignation) {
		this.employeeDesignation = employeeDesignation;
	}

	/**
	 * @return the employeeDesignations
	 */
	public Collection<EmployeeDesignation> getEmployeeDesignations() {
		return this.employeeDesignations;
	}

	/**
	 * @param employeeDesignations
	 *            the employeeDesignations to set
	 */
	public void setEmployeeDesignations(final Collection<EmployeeDesignation> employeeDesignations) {
		this.employeeDesignations = employeeDesignations;
	}

	/**
	 * @return the loadEmployeeDesignationsFlag
	 */
	public boolean isLoadEmployeeDesignationsFlag() {
		return this.loadEmployeeDesignationsFlag;
	}

	/**
	 * @param loadEmployeeDesignationsFlag
	 *            the loadEmployeeDesignationsFlag to set
	 */
	public void setLoadEmployeeDesignationsFlag(final boolean loadEmployeeDesignationsFlag) {
		this.loadEmployeeDesignationsFlag = loadEmployeeDesignationsFlag;
	}

	public void loadEmployeeDesignations() {
		if (this.loadEmployeeDesignationsFlag) {
			this.employeeDesignations = this.employeeDesignationService.findAllEmployeeDesignationsByEmployeeID(this.employeeBean.getEmployeeDO().getEmployee()
					.getId());
			this.loadEmployeeDesignationsFlag = false;
		}
	}

	public void resetEmployeeDesignation() {
		this.employeeDesignation = new EmployeeDesignation();
	}

	public void showEmployeeDesignation(final EmployeeDesignation employeeDesignation) {
		this.employeeDesignation = employeeDesignation;
	}

	public void removeEmployeeDesignation() {

		try {
			this.employeeDesignationService.removeEmployeeDesignation(this.employeeDesignation);
			this.loadEmployeeDesignationsFlag = true;
			this.loadEmployeeDesignations();
			ViewUtil.addMessage("Employee designation deleted sucessfully.", FacesMessage.SEVERITY_INFO);
			this.resetEmployeeDesignation();

		} catch (ApplicationException e) {

			ViewExceptionHandler.handle(e);
		}
	}

	public void createNewEmployeeDesignation() {
		try {
			this.employeeDesignation.setEmployee(this.employeeBean.getEmployeeDO().getEmployee());
			this.employeeDesignation = this.employeeDesignationService.createNewEmployeeDesignation(this.employeeDesignation);
			this.employeeDesignation = new EmployeeDesignation();

			ViewUtil.addMessage("Employee designation saved sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}

		this.loadEmployeeDesignationsFlag = true;
		this.loadEmployeeDesignations();
	}

	public void closeCurrentDesignation() {

		RequestContext requestContext = RequestContext.getCurrentInstance();

		try {
			this.employeeDesignation.setEmployee(this.employeeBean.getEmployeeDO().getEmployee());

			this.employeeDesignation = this.employeeDesignationService.closeEmployeeCurrentDesignation(this.employeeBean.getEmployeeDO().getEmployee()
					.getEmployeeNumber(), this.designationCloseDate);

			this.employeeDesignation = new EmployeeDesignation();

			this.showCloseDesignationSuccessMessage();

			requestContext.addCallbackParam("isValid", true);

		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
			requestContext.addCallbackParam("isValid", false);
		}

		this.loadEmployeeDesignationsFlag = true;
		this.loadEmployeeDesignations();
	}

	public void showCloseDesignationSuccessMessage() {
		ViewUtil.addMessage("Employee designation closed sucessfully.", FacesMessage.SEVERITY_INFO);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.apeironsol.smsystem.portal.AbstractTabbedBean#onTabChange()
	 */
	@Override
	public void onTabChange() {
		this.setViewOrNewAction(false);
		this.loadEmployeeDesignationsFlag = true;
		this.resetEmployeeDesignation();
	}

	public Date getDesignationCloseDate() {
		return this.designationCloseDate;
	}

	public void setDesignationCloseDate(final Date designationCloseDate) {
		this.designationCloseDate = designationCloseDate;
	}
}
