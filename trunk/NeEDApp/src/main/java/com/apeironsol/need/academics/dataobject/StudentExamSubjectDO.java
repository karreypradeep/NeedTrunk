package com.apeironsol.need.academics.dataobject;

import java.io.Serializable;

import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.need.academics.model.SectionExamSubject;
import com.apeironsol.need.academics.model.StudentExamSubject;
import com.apeironsol.need.core.model.Subject;
import com.apeironsol.need.util.constants.StudentExamSubjectStatusConstant;
import com.apeironsol.need.util.constants.StudentSubjectExamResultConstant;

public class StudentExamSubjectDO implements Serializable {

	private static final long	serialVersionUID	= 961660541635054060L;

	private Exam				exam;

	private SectionExamSubject	sectionExamSubject;

	private StudentExamSubject	studentExamSubject;

	public Exam getExam() {
		return this.exam;
	}

	public double getPercentageScoredForSubject() {
		return (this.studentExamSubject.getScoredMarks() * 100) / this.sectionExamSubject.getMaximumMarks();
	}

	public SectionExamSubject getSectionExamSubject() {
		return this.sectionExamSubject;
	}

	public StudentExamSubject getStudentExamSubject() {
		return this.studentExamSubject;
	}

	/**
	 * @return the studentSubjectExamResult
	 */
	public StudentSubjectExamResultConstant getStudentSubjectExamResult() {
		StudentSubjectExamResultConstant studentSubjectExamResult = StudentSubjectExamResultConstant.NOT_APPLICABLE;
		if (StudentExamSubjectStatusConstant.ASSIGNED.equals(this.studentExamSubject.getStudentExamSubjectStatus())) {
			studentSubjectExamResult = StudentSubjectExamResultConstant.NOT_APPLICABLE;
		} else if ((this.studentExamSubject != null) && (this.sectionExamSubject != null)) {
			if (this.sectionExamSubject.getPassMarks() > this.studentExamSubject.getScoredMarks()) {
				studentSubjectExamResult = StudentSubjectExamResultConstant.FAIL;
			} else {
				studentSubjectExamResult = StudentSubjectExamResultConstant.PASS;
			}
		}
		return studentSubjectExamResult;
	}

	public Subject getSubject() {
		return this.sectionExamSubject.getSectionSubject().getSubject();
	}

	public void setExam(final Exam exam) {
		this.exam = exam;
	}

	public void setSectionExamSubject(final SectionExamSubject sectionExamSubject) {
		this.sectionExamSubject = sectionExamSubject;
	}

	public void setStudentExamSubject(final StudentExamSubject studentExamSubject) {
		this.studentExamSubject = studentExamSubject;
	}

}
