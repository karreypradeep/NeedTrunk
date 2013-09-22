package com.apeironsol.need.util.portal;

import java.io.Serializable;
import java.util.Date;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.academics.model.SectionExamSubject;
import com.apeironsol.need.core.model.Subject;

public class SectionExamTreeNode extends DefaultTreeNode implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -4018001549713121997L;

	public String				name;

	public Date					scheduledDate;

	public Date					startTime;

	public Date					endTime;

	public Double				passMarks;

	public Double				maximumMarks;

	private Subject				subject;

	private SectionExam			sectionExam;

	private SectionExamSubject	sectionExamSubject;

	private Boolean				optionsNode;

	public SectionExamTreeNode() {
		super();
	}

	public SectionExamTreeNode(final Object data, final TreeNode parent) {
		super(data, parent);

	}

	public SectionExamTreeNode(final String type, final Object data, final TreeNode parent) {
		super(type, data, parent);
	}

	public SectionExam getSectionExam() {
		return this.sectionExam;
	}

	public void setSectionExam(final SectionExam sectionExam) {
		this.sectionExam = sectionExam;
	}

	public SectionExamSubject getSectionExamSubject() {
		return this.sectionExamSubject;
	}

	public void setSectionExamSubject(final SectionExamSubject sectionExamSubject) {
		this.sectionExamSubject = sectionExamSubject;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Date getScheduledDate() {
		return this.scheduledDate;
	}

	public void setScheduledDate(final Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(final Date endTime) {
		this.endTime = endTime;
	}

	public Double getPassMarks() {
		return this.passMarks;
	}

	public void setPassMarks(final Double passMarks) {
		this.passMarks = passMarks;
	}

	public Double getMaximumMarks() {
		return this.maximumMarks;
	}

	public void setMaximumMarks(final Double maximumMarks) {
		this.maximumMarks = maximumMarks;
	}

	public Boolean getOptionsNode() {
		return this.optionsNode;
	}

	public void setOptionsNode(final Boolean optionsNode) {
		this.optionsNode = optionsNode;
	}

	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(final Subject subject) {
		this.subject = subject;
	}

}
