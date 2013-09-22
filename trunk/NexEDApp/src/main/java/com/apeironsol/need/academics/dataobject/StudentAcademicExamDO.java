package com.apeironsol.need.academics.dataobject;

import java.io.Serializable;
import java.util.Collection;

import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.need.academics.model.SectionExam;

public class StudentAcademicExamDO implements Serializable {

	private static final long					serialVersionUID	= -1847923593732294159L;

	private Exam								exam;

	private SectionExam							sectionExam;

	private Collection<StudentExamSubjectDO>	studentExamSubjectDOs;

	private int									totalSubjectsScheduled;

	private double								totalMaximumMarks;

	private double								totalScoredMarks;

	private double								percentageScored;

	private boolean								failed;

	public Exam getExam() {
		return this.exam;
	}

	public void setExam(final Exam exam) {
		this.exam = exam;
	}

	public SectionExam getSectionExam() {
		return this.sectionExam;
	}

	public void setSectionExam(final SectionExam sectionExam) {
		this.sectionExam = sectionExam;
	}

	public Collection<StudentExamSubjectDO> getStudentExamSubjectDOs() {
		return this.studentExamSubjectDOs;
	}

	public void setStudentExamSubjectDOs(final Collection<StudentExamSubjectDO> studentExamSubjectDOs) {
		this.studentExamSubjectDOs = studentExamSubjectDOs;
	}

	public double getTotalMaximumMarks() {
		return this.totalMaximumMarks;
	}

	public void setTotalMaximumMarks(final double totalMaximumMarks) {
		this.totalMaximumMarks = totalMaximumMarks;
	}

	public double getTotalScoredMarks() {
		return this.totalScoredMarks;
	}

	public void setTotalScoredMarks(final double totalScoredMarks) {
		this.totalScoredMarks = totalScoredMarks;
	}

	public int getTotalSubjectsScheduled() {
		return this.totalSubjectsScheduled;
	}

	public void setTotalSubjectsScheduled(final int totalSubjectsScheduled) {
		this.totalSubjectsScheduled = totalSubjectsScheduled;
	}

	public double getPercentageScored() {
		return this.percentageScored;
	}

	public void setPercentageScored(final double percentageScored) {
		this.percentageScored = percentageScored;
	}

	public boolean isFailed() {
		return this.failed;
	}

	public void setFailed(final boolean failed) {
		this.failed = failed;
	}

}
