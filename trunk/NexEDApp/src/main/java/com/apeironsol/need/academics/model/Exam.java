package com.apeironsol.need.academics.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "EXAM", uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME", "BRANCH_ID" }, name = "UQ_BRANCH_ID_AND_NAME_FOR_EXAM") })
public class Exam extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -4932725383714487887L;

	@NotEmpty(message = "model.exam_name_mandatory")
	@Column(name = "NAME", nullable = false, length = 100)
	private String				name;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BUILD_BLOCK_ID", nullable = false)
	private BuildingBlock		buildingBlock;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch				branch;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public BuildingBlock getBuildingBlock() {
		return this.buildingBlock;
	}

	public void setBuildingBlock(final BuildingBlock buildingBlock) {
		this.buildingBlock = buildingBlock;
	}

	public Branch getBranch() {
		return this.branch;
	}

	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

}
