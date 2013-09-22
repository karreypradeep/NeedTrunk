package com.apeironsol.need.academics.dataobject;

import java.io.Serializable;
import java.util.Collection;

import com.apeironsol.need.core.model.Subject;

public class StudentAcademicSubjectDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long					serialVersionUID	= -7512882595862466894L;

	private Subject								subject;

	private Double								totalMaximumMarks;

	private Double								totalPassMarks;

	private Double								totalScoredMarks;

	private int									totalExamTakenCount;

	private Collection<StudentExamSubjectDO>	studentExamSubjectDOs;

	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(final Subject subject) {
		this.subject = subject;
	}

	public Double getTotalPassMarks() {
		return this.totalPassMarks;
	}

	public void setTotalPassMarks(final Double totalPassMarks) {
		this.totalPassMarks = totalPassMarks;
	}

	public Double getTotalScoredMarks() {
		return this.totalScoredMarks;
	}

	public void setTotalScoredMarks(final Double totalScoredMarks) {
		this.totalScoredMarks = totalScoredMarks;
	}

	public int getTotalExamTakenCount() {
		return this.totalExamTakenCount;
	}

	public void setTotalExamTakenCount(final int totalExamTakenCount) {
		this.totalExamTakenCount = totalExamTakenCount;
	}

	public Collection<StudentExamSubjectDO> getStudentExamSubjectDOs() {
		return this.studentExamSubjectDOs;
	}

	public void setStudentExamSubjectDOs(final Collection<StudentExamSubjectDO> studentExamSubjectDOs) {
		this.studentExamSubjectDOs = studentExamSubjectDOs;
	}

	public Double getTotalMaximumMarks() {
		return this.totalMaximumMarks;
	}

	public void setTotalMaximumMarks(final Double totalMaximumMarks) {
		this.totalMaximumMarks = totalMaximumMarks;
	}

}
