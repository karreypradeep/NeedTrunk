package com.apeironsol.need.academics.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.academics.model.StudentExamSubject;
import com.apeironsol.need.core.model.StudentAcademicYear;

/**
 * 
 * @author pradeep
 * 
 */
public class StudentExamAllSubjectsDO implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long						serialVersionUID	= 3846359971546080993L;

	private StudentAcademicYear						studentAcademicYear;

	private SectionExam								sectionExam;

	private final Collection<StudentExamSubject>	studentExamSubjects	= new ArrayList<StudentExamSubject>();

	/**
	 * @return the sectionExam
	 */
	public SectionExam getSectionExam() {
		return this.sectionExam;
	}

	/**
	 * @param sectionExam
	 *            the sectionExam to set
	 */
	public void setSectionExam(final SectionExam sectionExam) {
		this.sectionExam = sectionExam;
	}

	/**
	 * @return the studentExamSubjects
	 */
	public Collection<StudentExamSubject> getStudentExamSubjects() {
		return this.studentExamSubjects;
	}

	public void addStudentExamSubject(final StudentExamSubject studentExamSubject) {
		if (!this.studentExamSubjects.contains(studentExamSubject)) {
			this.studentExamSubjects.add(studentExamSubject);
		}
	}

	/**
	 * @return the studentAcademicYear
	 */
	public StudentAcademicYear getStudentAcademicYear() {
		return this.studentAcademicYear;
	}

	/**
	 * @param studentAcademicYear
	 *            the studentAcademicYear to set
	 */
	public void setStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		this.studentAcademicYear = studentAcademicYear;
	}

}
