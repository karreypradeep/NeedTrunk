/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.academics.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.apeironsol.framework.BaseEntity;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Branch;

/**
 * Entity class for Employee
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "REPORT_CARD")
public class ReportCard extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long			serialVersionUID	= 3758991075643514285L;

	@ManyToOne
	@JoinColumn(name = "ACADEMIC_YEAR_ID", nullable = false)
	private AcademicYear				academicYear;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch						branch;

	@ManyToOne
	@JoinColumn(name = "GRADE_SYSTEM_ID")
	private GradeSystem					gradeSystem;

	@Column(name = "NAME", length = 50, nullable = false)
	private String						name;

	@Column(name = "PASS_MARK_EACH_SUBJECT")
	private Integer						passMarksForEachSubject;

	@OneToMany(mappedBy = "reportCard", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private Collection<ReportCardExam>	reportCardExams;

	/**
	 * @return the academicYear
	 */
	public AcademicYear getAcademicYear() {
		return this.academicYear;
	}

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * @return the gradeSystem
	 */
	public GradeSystem getGradeSystem() {
		return this.gradeSystem;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the passMarksForEachSubject
	 */
	public Integer getPassMarksForEachSubject() {
		return this.passMarksForEachSubject;
	}

	/**
	 * @return the reportCardExams
	 */
	public Collection<ReportCardExam> getReportCardExams() {
		return this.reportCardExams;
	}

	/**
	 * @param academicYear
	 *            the academicYear to set
	 */
	public void setAcademicYear(final AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * @param gradeSystem
	 *            the gradeSystem to set
	 */
	public void setGradeSystem(final GradeSystem gradeSystem) {
		this.gradeSystem = gradeSystem;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @param passMarksForEachSubject
	 *            the passMarksForEachSubject to set
	 */
	public void setPassMarksForEachSubject(final Integer passMarksForEachSubject) {
		this.passMarksForEachSubject = passMarksForEachSubject;
	}

	/**
	 * @param reportCardExams
	 *            the reportCardExams to set
	 */
	public void setReportCardExams(final Collection<ReportCardExam> reportCardExams) {
		this.reportCardExams = reportCardExams;
	}

}
