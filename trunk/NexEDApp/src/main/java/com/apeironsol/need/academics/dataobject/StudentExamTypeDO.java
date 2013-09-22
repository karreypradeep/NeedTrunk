/**
 * 
 */
package com.apeironsol.need.academics.dataobject;

import java.io.Serializable;
import java.util.List;

import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.model.StudentSection;

/**
 * @author pradeep
 * 
 */
public class StudentExamTypeDO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4767352520526864602L;

	private BuildingBlock		examTypeBuildingBlock;

	private double				totalMarksForExamType;

	private double				totalMarksScoredByStudentForExamType;

	/**
	 * List of student exam subjects.
	 */
	private List<StudentExamDO>	studentExamDOs;

	/**
	 * Student section.
	 */
	private StudentSection		studentSection;

	/**
	 * @return the studentSection
	 */
	public StudentSection getStudentSection() {
		return this.studentSection;
	}

	/**
	 * @param studentSection
	 *            the studentSection to set
	 */
	public void setStudentSection(final StudentSection studentSection) {
		this.studentSection = studentSection;
	}

	/**
	 * @return the studentExamDOs
	 */
	public List<StudentExamDO> getStudentExamDOs() {
		return this.studentExamDOs;
	}

	/**
	 * @param studentExamDOs
	 *            the studentExamDOs to set
	 */
	public void setStudentExamDOs(final List<StudentExamDO> studentExamDOs) {
		this.studentExamDOs = studentExamDOs;
	}

	/**
	 * @return the examTypeBuildingBlock
	 */
	public BuildingBlock getExamTypeBuildingBlock() {
		return this.examTypeBuildingBlock;
	}

	/**
	 * @param examTypeBuildingBlock
	 *            the examTypeBuildingBlock to set
	 */
	public void setExamTypeBuildingBlock(final BuildingBlock examTypeBuildingBlock) {
		this.examTypeBuildingBlock = examTypeBuildingBlock;
	}

	/**
	 * @return the totalMarksForExamType
	 */
	public double getTotalMarksForExamType() {
		return this.totalMarksForExamType;
	}

	/**
	 * @param totalMarksForExamType
	 *            the totalMarksForExamType to set
	 */
	public void setTotalMarksForExamType(final double totalMarksForExamType) {
		this.totalMarksForExamType = totalMarksForExamType;
	}

	/**
	 * @return the totalMarksScoredByStudentForExamType
	 */
	public double getTotalMarksScoredByStudentForExamType() {
		return this.totalMarksScoredByStudentForExamType;
	}

	/**
	 * @param totalMarksScoredByStudentForExamType
	 *            the totalMarksScoredByStudentForExamType to set
	 */
	public void setTotalMarksScoredByStudentForExamType(final double totalMarksScoredByStudentForExamType) {
		this.totalMarksScoredByStudentForExamType = totalMarksScoredByStudentForExamType;
	}

	public void computeMarksOfStudentForExamType() {
		this.totalMarksForExamType = 0;
		this.totalMarksScoredByStudentForExamType = 0;
		for (StudentExamDO studentExamDO : this.studentExamDOs) {
			studentExamDO.computeMarksOfStudent();
			this.totalMarksScoredByStudentForExamType += studentExamDO.getTotalMarksScoredByStudent();
			this.totalMarksForExamType += studentExamDO.getTotalMarksForExam();
		}
	}

}
