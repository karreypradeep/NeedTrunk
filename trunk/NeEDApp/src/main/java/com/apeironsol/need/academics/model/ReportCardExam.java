/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.academics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for Employee
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "REPOR_CARD_EXAM")
public class ReportCardExam extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 1818722352784374443L;

	@ManyToOne
	@JoinColumn(name = "REPORT_CARD_ID", nullable = false)
	private ReportCard			reportCard;

	@Column(name = "PERCENTAGE", nullable = false)
	private Integer				percentage;

	@ManyToOne
	@JoinColumn(name = "EXAM_ID", nullable = false)
	private Exam				exam;

	/**
	 * @return the reportCard
	 */
	public ReportCard getReportCard() {
		return this.reportCard;
	}

	/**
	 * @param reportCard
	 *            the reportCard to set
	 */
	public void setReportCard(final ReportCard reportCard) {
		this.reportCard = reportCard;
	}

	/**
	 * @return the percentage
	 */
	public Integer getPercentage() {
		return this.percentage;
	}

	/**
	 * @param percentage
	 *            the percentage to set
	 */
	public void setPercentage(final Integer percentage) {
		this.percentage = percentage;
	}

	/**
	 * @return the exam
	 */
	public Exam getExam() {
		return this.exam;
	}

	/**
	 * @param exam
	 *            the exam to set
	 */
	public void setExam(final Exam exam) {
		this.exam = exam;
	}

}
