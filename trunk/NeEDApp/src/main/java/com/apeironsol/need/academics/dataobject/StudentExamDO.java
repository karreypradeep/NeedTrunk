/**
 * 
 */
package com.apeironsol.need.academics.dataobject;

import java.io.Serializable;
import java.util.List;

import com.apeironsol.need.academics.model.Exam;
import com.apeironsol.need.academics.model.StudentExamSubject;

/**
 * @author pradeep
 * 
 */
public class StudentExamDO implements Serializable {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= -7589591344062926241L;

	private Exam						exam;

	private double						totalMarksForExam;

	private double						totalMarksScoredByStudent;

	/**
	 * List of student exam subjects.
	 */
	private List<StudentExamSubject>	studentExamSubjects;

	/**
	 * @return the studentExamSubjects
	 */
	public List<StudentExamSubject> getStudentExamSubjects() {
		return this.studentExamSubjects;
	}

	/**
	 * @param studentExamSubjects
	 *            the studentExamSubjects to set
	 */
	public void setStudentExamSubjects(final List<StudentExamSubject> studentExamSubjects) {
		this.studentExamSubjects = studentExamSubjects;
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

	public void computeMarksOfStudent() {
		this.totalMarksForExam = 0;
		this.totalMarksScoredByStudent = 0;
		for (StudentExamSubject studentExamSubject : this.studentExamSubjects) {
			this.totalMarksScoredByStudent += studentExamSubject.getScoredMarks() != null ? studentExamSubject.getScoredMarks() : 0;
			this.totalMarksForExam += studentExamSubject.getSectionExamSubject().getMaximumMarks() != null ? studentExamSubject.getSectionExamSubject()
					.getMaximumMarks() : 0;
		}
	}

	/**
	 * @return the totalMarksForExam
	 */
	public double getTotalMarksForExam() {
		return this.totalMarksForExam;
	}

	/**
	 * @param totalMarksForExam
	 *            the totalMarksForExam to set
	 */
	public void setTotalMarksForExam(final double totalMarksForExam) {
		this.totalMarksForExam = totalMarksForExam;
	}

	/**
	 * @return the totalMarksScoredByStudent
	 */
	public double getTotalMarksScoredByStudent() {
		return this.totalMarksScoredByStudent;
	}

	/**
	 * @param totalMarksScoredByStudent
	 *            the totalMarksScoredByStudent to set
	 */
	public void setTotalMarksScoredByStudent(final double totalMarksScoredByStudent) {
		this.totalMarksScoredByStudent = totalMarksScoredByStudent;
	}

}
