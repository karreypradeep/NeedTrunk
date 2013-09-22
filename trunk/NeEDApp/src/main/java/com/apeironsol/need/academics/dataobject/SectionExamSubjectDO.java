package com.apeironsol.need.academics.dataobject;

import java.io.Serializable;

import com.apeironsol.need.academics.model.SectionExamSubject;
import com.apeironsol.need.core.model.Subject;

public class SectionExamSubjectDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 8138612552676569259L;

	private Subject				subject;

	private SectionExamSubject	sectionExamSubject;

	public SectionExamSubject getSectionExamSubject() {
		return this.sectionExamSubject;
	}

	public void setSectionExamSubject(final SectionExamSubject sectionExamSubject) {
		this.sectionExamSubject = sectionExamSubject;
	}

	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(final Subject subject) {
		this.subject = subject;
	}

}
