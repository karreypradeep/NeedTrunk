/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.hostel.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.apeironsol.framework.BaseEntity;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BuildingBlock;

/**
 * Entity class for Employee
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "HOSTEL_ROOM")
public class HostelRoom extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -4095725199372619193L;

	/**
	 * Batch notification type.
	 */
	@ManyToOne
	@JoinColumn(name = "BUILDING_BLOCK_ID", nullable = false)
	private BuildingBlock		hostelRoomTypebuildingBlock;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch				branch;

	@Column(name = "ROOM_NUMBER", length = 50, nullable = false)
	private String				roomnNumber;

	@Column(name = "DESCRIPTION", length = 200, nullable = false)
	private String				description;

	@Column(name = "TOTAL_NUMBER_OF_BEDS", nullable = false)
	private Integer				totalNumberOfBeds;

	@Column(name = "BEDS_OCCUPIED")
	private Integer				bedsOccupied;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE", nullable = false)
	private Date				startDate;

	/**
	 * @return the roomnNumber
	 */
	public String getRoomnNumber() {
		return this.roomnNumber;
	}

	/**
	 * @param roomnNumber
	 *            the roomnNumber to set
	 */
	public void setRoomnNumber(final String roomnNumber) {
		this.roomnNumber = roomnNumber;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return the totalNumberOfBeds
	 */
	public Integer getTotalNumberOfBeds() {
		return this.totalNumberOfBeds;
	}

	/**
	 * @param totalNumberOfBeds
	 *            the totalNumberOfBeds to set
	 */
	public void setTotalNumberOfBeds(final Integer totalNumberOfBeds) {
		this.totalNumberOfBeds = totalNumberOfBeds;
	}

	/**
	 * @return the bedsOccupied
	 */
	public Integer getBedsOccupied() {
		return this.bedsOccupied;
	}

	/**
	 * @param bedsOccupied
	 *            the bedsOccupied to set
	 */
	public void setBedsOccupied(final Integer bedsOccupied) {
		this.bedsOccupied = bedsOccupied;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the buildingBlock
	 */
	public BuildingBlock getHostelRoomTypebuildingBlock() {
		return this.hostelRoomTypebuildingBlock;
	}

	/**
	 * @param buildingBlock
	 *            the buildingBlock to set
	 */
	public void setHostelRoomTypebuildingBlock(final BuildingBlock hostelRoomTypebuildingBlock) {
		this.hostelRoomTypebuildingBlock = hostelRoomTypebuildingBlock;
	}

}
