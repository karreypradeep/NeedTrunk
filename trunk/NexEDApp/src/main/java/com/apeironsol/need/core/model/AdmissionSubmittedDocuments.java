/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.apeironsol.framework.BaseEntity;

/**
 * Entity class for StudentDocumentsSubmitted
 * 
 * @author sunny
 * 
 */
@Entity
@Table(name = "ADMISSION_SUBMITTED_DOCUMENTS")
public class AdmissionSubmittedDocuments extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4425490943101228159L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDENT_ID", nullable = false)
	private Student				student;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BUILD_BLOCK_ID", nullable = false)
	private BuildingBlock		buildingBlock;

	public Student getStudent() {
		return student;
	}

	public void setStudent(final Student student) {
		this.student = student;
	}

	public BuildingBlock getBuildingBlock() {
		return buildingBlock;
	}

	public void setBuildingBlock(final BuildingBlock buildingBlock) {
		this.buildingBlock = buildingBlock;
	}

}
