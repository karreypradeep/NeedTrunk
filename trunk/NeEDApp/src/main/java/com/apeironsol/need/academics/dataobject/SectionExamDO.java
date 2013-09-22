package com.apeironsol.need.academics.dataobject;

import java.io.Serializable;
import java.util.Collection;

import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.academics.model.SectionExamSubject;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.SectionSubject;

public class SectionExamDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long					serialVersionUID	= 5791570471760008081L;

	private AcademicYear						academicYear;

	private Klass								klass;

	private Section								section;

	private Exam								exam;

	private SectionExam							sectionExam;

	private Collection<SectionSubject>			sectionSubjects;

	private Collection<SectionExamSubject>		sectionExamSubjects;

	private Collection<SectionExamSubjectDO>	sectionExamSubjectDOs;

	public AcademicYear getAcademicYear() {
		return this.academicYear;
	}

	public void setAcademicYear(final AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	public Klass getKlass() {
		return this.klass;
	}

	public void setKlass(final Klass klass) {
		this.klass = klass;
	}

	public Section getSection() {
		return this.section;
	}

	public void setSection(final Section section) {
		this.section = section;
	}

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

	public Collection<SectionSubject> getSectionSubjects() {
		return this.sectionSubjects;
	}

	public void setSectionSubjects(final Collection<SectionSubject> sectionSubjects) {
		this.sectionSubjects = sectionSubjects;
	}

	public Collection<SectionExamSubject> getSectionExamSubjects() {
		return this.sectionExamSubjects;
	}

	public void setSectionExamSubjects(final Collection<SectionExamSubject> sectionExamSubjects) {
		this.sectionExamSubjects = sectionExamSubjects;
	}

	public Collection<SectionExamSubjectDO> getSectionExamSubjectDOs() {
		return this.sectionExamSubjectDOs;
	}

	public void setSectionExamSubjectDOs(final Collection<SectionExamSubjectDO> sectionExamSubjectDOs) {
		this.sectionExamSubjectDOs = sectionExamSubjectDOs;
	}

}
