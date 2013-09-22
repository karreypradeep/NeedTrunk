package com.apeironsol.need.academics.dataobject;

import java.io.Serializable;

import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.need.academics.model.SectionExamSubject;
import com.apeironsol.need.academics.model.StudentExamSubject;
import com.apeironsol.need.core.model.Subject;

public class StudentExamSubjectDO implements Serializable {

	private static final long	serialVersionUID	= 961660541635054060L;

	private Exam				exam;

	private Subject				subject;

	private SectionExamSubject	sectionExamSubject;

	private StudentExamSubject	studentExamSubject;

	public SectionExamSubject getSectionExamSubject() {
		return this.sectionExamSubject;
	}

	public void setSectionExamSubject(final SectionExamSubject sectionExamSubject) {
		this.sectionExamSubject = sectionExamSubject;
	}

	public StudentExamSubject getStudentExamSubject() {
		return this.studentExamSubject;
	}

	public void setStudentExamSubject(final StudentExamSubject studentExamSubject) {
		this.studentExamSubject = studentExamSubject;
	}

	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(final Subject subject) {
		this.subject = subject;
	}

	public Exam getExam() {
		return this.exam;
	}

	public void setExam(final Exam exam) {
		this.exam = exam;
	}

}
