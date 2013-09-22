/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal;

/**
 * Header menu JSF managed bean class.
 * 
 * @author Pradeep
 */
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.security.portal.LoginBean;
import com.apeironsol.need.util.constants.HeaderLegend;
import com.apeironsol.need.util.portal.ViewPathConstants;
import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope(value = "session")
public class HeaderMenu implements Serializable {

	private static final long		serialVersionUID		= -3353095608335972692L;
	private static final String		HEADER_VIEW_PARAMETER	= "headerLegend";
	private HeaderDescriptor		activeHeader;
	private List<HeaderDescriptor>	headers;
	private HeaderDescriptor		management;
	private HeaderDescriptor		employee;
	private HeaderDescriptor		student;
	private HeaderDescriptor		logout;

	@Resource(name = "loginBean")
	private LoginBean				loginBean;

	@PostConstruct
	public void init() {
		this.management = new HeaderDescriptor();
		this.management.setId("management");
		this.management.setName("Management");
		this.management.setOutcome(ViewPathConstants.BRANCH_STUDENTS);

		this.employee = new HeaderDescriptor();
		this.employee.setId("employee");
		this.employee.setName("Employee");
		this.employee.setOutcome(ViewPathConstants.EMPLOYEE_DASHBOARD);

		this.student = new HeaderDescriptor();
		this.student.setId("student");
		this.student.setName("Student");
		this.student.setOutcome(ViewPathConstants.STUDENT_DASHBOARD);

		this.logout = new HeaderDescriptor();
		this.logout.setId("logout");
		this.logout.setName("Logout");
		this.logout.setOutcome(ViewPathConstants.WELCOME);
	}

	public List<HeaderDescriptor> getHeaders() throws IOException {
		String headerLegend = ViewUtil.getViewParameterValue(HEADER_VIEW_PARAMETER);
		if (headerLegend == null && this.activeHeader != null) {
			headerLegend = this.activeHeader.getId();
		}
		this.headers = new ArrayList<HeaderDescriptor>();
		if (HeaderLegend.MANAGEMENT.equals(headerLegend) || HeaderLegend.EMPLOYEE.equals(headerLegend)) {
			this.headers.add(this.management);
			this.headers.add(this.employee);
			this.headers.add(this.logout);
		} else if (HeaderLegend.STUDENT.equals(headerLegend)) {
			this.headers.add(this.student);
			this.headers.add(this.logout);
		} else {
			this.headers.add(this.management);
			this.headers.add(this.employee);
			this.headers.add(this.student);
		}
		if (HeaderLegend.MANAGEMENT.equals(headerLegend)) {
			this.activeHeader = this.management;
		} else if (HeaderLegend.EMPLOYEE.equals(headerLegend)) {
			this.activeHeader = this.employee;
		} else if (HeaderLegend.STUDENT.equals(headerLegend)) {
			this.activeHeader = this.student;
		} else if (HeaderLegend.LOGOUT.equals(headerLegend)) {
			this.activeHeader = null;
			this.loginBean.logout();
		} else {
			this.activeHeader = null;
		}
		return this.headers;
	}

	public void setHeaders(final List<HeaderDescriptor> headers) {
		this.headers = headers;
	}

	public HeaderDescriptor getActiveHeader() {
		return this.activeHeader;
	}

	public void setActiveHeader(final HeaderDescriptor activeHeader) {
		this.activeHeader = activeHeader;
	}

	public String getActiveHeaderId() {
		if (this.activeHeader != null) {
			return this.activeHeader.getId();
		}
		return null;
	}

	public String getHeaderLegend() {
		if (this.activeHeader != null) {
			return this.activeHeader.getId();
		} else {
			return ViewUtil.getViewParameterValue(HEADER_VIEW_PARAMETER);
		}
	}

	public LoginBean getLoginBean() {
		return this.loginBean;
	}

	public void setLoginBean(final LoginBean loginBean) {
		this.loginBean = loginBean;
	}

}
