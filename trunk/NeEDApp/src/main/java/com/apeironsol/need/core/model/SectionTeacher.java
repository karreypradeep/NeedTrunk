package com.apeironsol.need.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.framework.BaseEntity;


@Entity
@Table(name = "SECTION_TEACHER", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "SECTION_ID", "EMPLOYEE_ID", "START_DATE" }, name = "UQ_SECTION_ID_EMPLOYEE_ID_START_DATE"),
		@UniqueConstraint(columnNames = { "SECTION_ID", "INCHARGE", "START_DATE" }, name = "UQ_SECTION_ID_INCHARGE_START_DATE") })
public class SectionTeacher extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -5978402120005556231L;

	@NotNull(message = "model.section")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SECTION_ID", nullable = false)
	private Section				section;

	@NotNull(message = "model.employee")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee			employee;

	@Column(name = "INCHARGE")
	private Boolean				incharge;

	@NotNull(message = "model.section_teacher_start_date")
	@Column(name = "START_DATE", nullable = false)
	private Date				startDate;

	@Column(name = "END_DATE")
	private Date				endDate;

	public Section getSection() {
		return section;
	}

	public void setSection(final Section section) {
		this.section = section;
	}

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @param employee
	 *            the employee to set
	 */
	public void setEmployee(final Employee employee) {
		this.employee = employee;
	}

	/**
	 * @param incharge
	 *            the incharge to set
	 */
	public void setIncharge(final Boolean incharge) {
		this.incharge = incharge;
	}

	/**
	 * @param incharge
	 *            the incharge to set
	 */
	public Boolean getIncharge() {
		return incharge;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

}
