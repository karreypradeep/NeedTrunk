/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.hostel.portal;

/**
 * View courses class.
 * 
 * @author Pradeep
 */
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.portal.AbstractTabbedBean;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.hostel.model.HostelRoom;
import com.apeironsol.need.hostel.model.StudentAcademicYearHostelRoom;
import com.apeironsol.need.hostel.service.HostelRoomService;
import com.apeironsol.need.hostel.service.StudentAcademicYearHostelRoomService;
import com.apeironsol.need.util.constants.BuildingBlockConstant;
import com.apeironsol.need.util.constants.ResidenceConstant;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.StudentSearchCriteria;

@Named
@Scope("session")
public class HostelRoomBean extends AbstractTabbedBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long							serialVersionUID			= -6282397725805720672L;

	private HostelRoom									hostelRoom;
	private StudentAcademicYearHostelRoom				studentAcademicYearHostelRoom;
	private Collection<HostelRoom>						hostelRooms;

	private boolean										loadHostelRoomsFlag;

	private boolean										loadStudentAcademicHostelRoomsFlag;
	/**
	 * Building blocks for type expenses.
	 */
	private Collection<BuildingBlock>					hostelRoomTypeBuildingBlocks;

	/**
	 * Building block service.
	 */
	@Resource
	private BuildingBlockService						buildingBlockService;

	/**
	 * Indicator to load expense types from database.
	 */
	private boolean										loadHostelRoomTypesFromDB;

	@Resource
	HostelRoomService									hostelRoomService;

	@Resource
	StudentAcademicYearHostelRoomService				studentAcademicYearHostelRoomService;

	private BuildingBlock								hostelRoomTypeBuildingBlockForSearch;

	private AcademicYear								academicYearForSearch;

	private AcademicYear								academicYearForOccupantsSearch;

	private StudentSearchCriteria						studentSearchCriteria		= null;

	private Collection<StudentSection>					studentSectionsBySearchCriteria;

	/**
	 * Building blocks for type expenses.
	 */
	private Collection<StudentAcademicYearHostelRoom>	studentAcademicYearHostelRooms;

	private boolean										allocateStudentToHostelRoom	= false;

	private boolean										studentSearchFlag;

	private Collection<StudentAcademicYearHostelRoom>	currentStudentAcademicYearHostelRooms;

	private boolean										loadcurrentStudentAcademicYearHostelRoomFlag;

	@PostConstruct
	public void init() {
		this.initializeSearchCriteria();
	}

	public void initializeSearchCriteria() {
		if (this.studentSearchCriteria == null) {
			this.studentSearchCriteria = new StudentSearchCriteria(this.sessionBean.getCurrentBranch());
			this.studentSearchCriteria.setResidenceConstant(ResidenceConstant.HOSTEL);
		}
	}

	public void newHostelRoom() {
		this.hostelRoom = new HostelRoom();
		this.hostelRoom.setBranch(this.sessionBean.getCurrentBranch());
		this.hostelRoom.setBedsOccupied(0);
	}

	public void newStudentAcademicYearHostelRoom() {
		this.studentAcademicYearHostelRoom = new StudentAcademicYearHostelRoom();
		this.studentAcademicYearHostelRoom.setHostelRoom(this.hostelRoom);
	}

	public void saveHostelRoom() {
		try {
			this.hostelRoom = this.hostelRoomService.saveHostelRoom(this.hostelRoom);
			ViewUtil.addMessage("Hostel room saved sucessfully.", FacesMessage.SEVERITY_INFO);
			this.loadHostelRoomsFlag = true;
		} catch (ApplicationException e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void saveHostelRoomStudentAcademicYear() {
		try {
			this.studentAcademicYearHostelRoom = this.studentAcademicYearHostelRoomService
					.saveStudentAcademicYearHostelRoom(this.studentAcademicYearHostelRoom);
			if (this.studentAcademicYearHostelRoom.getEndDate() == null) {
				ViewUtil.addMessage("Hostel room allocated to student sucessfully.", FacesMessage.SEVERITY_INFO);
			} else {
				ViewUtil.addMessage("Student vacated hostel room successfully.", FacesMessage.SEVERITY_INFO);
			}
			this.loadcurrentStudentAcademicYearHostelRoomFlag = true;
			this.hostelRoom = this.hostelRoomService.findHostelRoomById(this.hostelRoom.getId());
		} catch (ApplicationException e) {
			this.loadcurrentStudentAcademicYearHostelRoomFlag = true;
			ViewExceptionHandler.handle(e);
		}
	}

	public void removeHostelRoom() {
		try {
			this.hostelRoomService.removeHostelRoom(this.hostelRoom);
			this.hostelRoom = new HostelRoom();
			this.loadHostelRoomsFlag = true;
			ViewUtil.addMessage("Hostel room removed sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (BusinessException e) {
			ViewExceptionHandler.handle(e);
		} catch (Throwable e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void resetHostelRoom() {
		this.hostelRoom = new HostelRoom();
	}

	public void loadHostelRooms() {
		if (this.isLoadHostelRoomsFlag()) {
			this.hostelRooms = this.hostelRoomService.findAllHostelRooms(this.sessionBean.getCurrentBranch().getId());
			this.setLoadHostelRoomsFlag(false);
		}
	}

	/**
	 * @return the hostelRoom
	 */
	public HostelRoom getHostelRoom() {
		return this.hostelRoom;
	}

	/**
	 * @param hostelRoom
	 *            the hostelRoom to set
	 */
	public void setHostelRoom(final HostelRoom hostelRoom) {
		this.hostelRoom = hostelRoom;
	}

	/**
	 * @return the hostelRooms
	 */
	public Collection<HostelRoom> getHostelRooms() {
		return this.hostelRooms;
	}

	/**
	 * @param hostelRooms
	 *            the hostelRooms to set
	 */
	public void setHostelRooms(final Collection<HostelRoom> hostelRooms) {
		this.hostelRooms = hostelRooms;
	}

	/**
	 * @return the loadHostelRoomsFlag
	 */
	public boolean isLoadHostelRoomsFlag() {
		return this.loadHostelRoomsFlag;
	}

	/**
	 * @param loadHostelRoomsFlag
	 *            the loadHostelRoomsFlag to set
	 */
	public void setLoadHostelRoomsFlag(final boolean loadHostelRoomsFlag) {
		this.loadHostelRoomsFlag = loadHostelRoomsFlag;
	}

	@Override
	public void onTabChange() {
		// TODO Auto-generated method stub
		this.allocateStudentToHostelRoom = false;
	}

	/**
	 * Load building blocks for expense type for current branch.
	 */
	public void loadHostelRoomTypeBuildingBlocks() {
		if (this.loadHostelRoomTypesFromDB) {
			this.setHostelRoomTypeBuildingBlocks(this.buildingBlockService.findBuildingBlocksbyBranchIdAndBuildingBlockType(this.sessionBean.getCurrentBranch()
					.getId(), BuildingBlockConstant.HOSTEL_ROOM_TYPE));
			this.loadHostelRoomTypesFromDB = false;
		}
	}

	/**
	 * @return the hostelRoomTypeBuildingBlocks
	 */
	public Collection<BuildingBlock> getHostelRoomTypeBuildingBlocks() {
		return this.hostelRoomTypeBuildingBlocks;
	}

	/**
	 * @param hostelRoomTypeBuildingBlocks
	 *            the hostelRoomTypeBuildingBlocks to set
	 */
	public void setHostelRoomTypeBuildingBlocks(final Collection<BuildingBlock> hostelRoomTypeBuildingBlocks) {
		this.hostelRoomTypeBuildingBlocks = hostelRoomTypeBuildingBlocks;
	}

	/**
	 * @return the loadHostelRoomTypesFromDB
	 */
	public boolean isLoadHostelRoomTypesFromDB() {
		return this.loadHostelRoomTypesFromDB;
	}

	/**
	 * @param loadHostelRoomTypesFromDB
	 *            the loadHostelRoomTypesFromDB to set
	 */
	public void setLoadHostelRoomTypesFromDB(final boolean loadHostelRoomTypesFromDB) {
		this.loadHostelRoomTypesFromDB = loadHostelRoomTypesFromDB;
	}

	/**
	 * @return the hostelRoomTypeBuildingBlockForSearch
	 */
	public BuildingBlock getHostelRoomTypeBuildingBlockForSearch() {
		return this.hostelRoomTypeBuildingBlockForSearch;
	}

	/**
	 * @param hostelRoomTypeBuildingBlockForSearch
	 *            the hostelRoomTypeBuildingBlockForSearch to set
	 */
	public void setHostelRoomTypeBuildingBlockForSearch(final BuildingBlock hostelRoomTypeBuildingBlockForSearch) {
		this.hostelRoomTypeBuildingBlockForSearch = hostelRoomTypeBuildingBlockForSearch;
	}

	/**
	 * @return the academicYearForSearch
	 */
	public AcademicYear getAcademicYearForSearch() {
		return this.academicYearForSearch;
	}

	/**
	 * @param academicYearForSearch
	 *            the academicYearForSearch to set
	 */
	public void setAcademicYearForSearch(final AcademicYear academicYearForSearch) {
		this.academicYearForSearch = academicYearForSearch;
	}

	public void searchHostelRooms() {
		if (this.academicYearForSearch != null) {
			this.hostelRooms = this.hostelRoomService.findAllHostelRoomsByTypeAndBetweenDates(this.sessionBean.getCurrentBranch().getId(),
					this.hostelRoomTypeBuildingBlockForSearch.getId(), this.academicYearForSearch.getStartDate(), this.academicYearForSearch.getEndDate());
		} else {
			this.hostelRooms = this.hostelRoomService.findAllHostelRoomsByType(this.sessionBean.getCurrentBranch().getId(),
					this.hostelRoomTypeBuildingBlockForSearch.getId());
		}
	}

	public void resetSearchCriteria() {
		this.academicYearForSearch = null;
		this.hostelRoomTypeBuildingBlockForSearch = null;
		this.studentSearchCriteria.resetSeacrhCriteria();
	}

	public void viewHostelRoom() {
	}

	/**
	 * @return the studentAcademicYearHostelRooms
	 */
	public Collection<StudentAcademicYearHostelRoom> getStudentAcademicYearHostelRooms() {
		return this.studentAcademicYearHostelRooms;
	}

	/**
	 * @param studentAcademicYearHostelRooms
	 *            the studentAcademicYearHostelRooms to set
	 */
	public void setStudentAcademicYearHostelRooms(final Collection<StudentAcademicYearHostelRoom> studentAcademicYearHostelRooms) {
		this.studentAcademicYearHostelRooms = studentAcademicYearHostelRooms;
	}

	public void searchHostelRoomOccupants() {
		if (this.academicYearForOccupantsSearch != null) {
			this.studentAcademicYearHostelRooms = this.studentAcademicYearHostelRoomService.findHostelRoomOccupantsForAcademicYear(this.hostelRoom.getId(),
					this.academicYearForOccupantsSearch.getId());
		} else {
			this.studentAcademicYearHostelRooms = this.studentAcademicYearHostelRoomService.findStudentAcademicYearHostelRoomsByRoomId(this.hostelRoom.getId());
		}
	}

	/**
	 * @return the academicYearForOccupantsSearch
	 */
	public AcademicYear getAcademicYearForOccupantsSearch() {
		return this.academicYearForOccupantsSearch;
	}

	/**
	 * @param academicYearForOccupantsSearch
	 *            the academicYearForOccupantsSearch to set
	 */
	public void setAcademicYearForOccupantsSearch(final AcademicYear academicYearForOccupantsSearch) {
		this.academicYearForOccupantsSearch = academicYearForOccupantsSearch;
	}

	/**
	 * @return the studentSearchCriteria
	 */
	public StudentSearchCriteria getStudentSearchCriteria() {
		return this.studentSearchCriteria;
	}

	/**
	 * @param studentSearchCriteria
	 *            the studentSearchCriteria to set
	 */
	public void setStudentSearchCriteria(final StudentSearchCriteria studentSearchCriteria) {
		this.studentSearchCriteria = studentSearchCriteria;
	}

	public String searchStudentSectionsBySearchCriteria() {

		if (this.studentSearchCriteria.isSearchCriteriaIsEmpty()) {
			ViewUtil.addMessage("Please enter search criteria.", FacesMessage.SEVERITY_ERROR);
		} else {
			this.studentSearchCriteria.setBranch(this.sessionBean.getCurrentBranch());
			this.setStudentSectionsBySearchCriteria(this.studentService.findStudentSectionsBySearchCriteria(this.studentSearchCriteria));
			if (this.getStudentSectionsBySearchCriteria() == null || this.getStudentSectionsBySearchCriteria().isEmpty()) {
				ViewUtil.addMessage("No students found for entered search criteria..", FacesMessage.SEVERITY_INFO);
			}
		}
		return null;
	}

	/**
	 * @return the studentSectionsBySearchCriteria
	 */
	public Collection<StudentSection> getStudentSectionsBySearchCriteria() {
		return this.studentSectionsBySearchCriteria;
	}

	/**
	 * @param studentSectionsBySearchCriteria
	 *            the studentSectionsBySearchCriteria to set
	 */
	public void setStudentSectionsBySearchCriteria(final Collection<StudentSection> studentSectionsBySearchCriteria) {
		this.studentSectionsBySearchCriteria = studentSectionsBySearchCriteria;
	}

	/**
	 * @return the allocateStudentToHostelRoom
	 */
	public boolean isAllocateStudentToHostelRoom() {
		return this.allocateStudentToHostelRoom;
	}

	/**
	 * @param allocateStudentToHostelRoom
	 *            the allocateStudentToHostelRoom to set
	 */
	public void setAllocateStudentToHostelRoom(final boolean allocateStudentToHostelRoom) {
		this.allocateStudentToHostelRoom = allocateStudentToHostelRoom;
	}

	/**
	 * @return the studentAcademicYearHostelRoom
	 */
	public StudentAcademicYearHostelRoom getStudentAcademicYearHostelRoom() {
		return this.studentAcademicYearHostelRoom;
	}

	/**
	 * @param studentAcademicYearHostelRoom
	 *            the studentAcademicYearHostelRoom to set
	 */
	public void setStudentAcademicYearHostelRoom(final StudentAcademicYearHostelRoom studentAcademicYearHostelRoom) {
		this.studentAcademicYearHostelRoom = studentAcademicYearHostelRoom;
	}

	/**
	 * @return the studentSearchFlag
	 */
	public boolean isStudentSearchFlag() {
		return this.studentSearchFlag;
	}

	/**
	 * @param studentSearchFlag
	 *            the studentSearchFlag to set
	 */
	public void setStudentSearchFlag(final boolean studentSearchFlag) {
		this.studentSearchFlag = studentSearchFlag;
	}

	public String doNothing() {
		return null;
	}

	/**
	 * @return the loadStudentAcademicHostelRoomsFlag
	 */
	public boolean isLoadStudentAcademicHostelRoomsFlag() {
		return this.loadStudentAcademicHostelRoomsFlag;
	}

	/**
	 * @param loadStudentAcademicHostelRoomsFlag
	 *            the loadStudentAcademicHostelRoomsFlag to set
	 */
	public void setLoadStudentAcademicHostelRoomsFlag(final boolean loadStudentAcademicHostelRoomsFlag) {
		this.loadStudentAcademicHostelRoomsFlag = loadStudentAcademicHostelRoomsFlag;
	}

	/**
	 * @return the currentStudentAcademicYearHostelRooms
	 */
	public Collection<StudentAcademicYearHostelRoom> getCurrentStudentAcademicYearHostelRooms() {
		return this.currentStudentAcademicYearHostelRooms;
	}

	/**
	 * @param currentStudentAcademicYearHostelRooms
	 *            the currentStudentAcademicYearHostelRooms to set
	 */
	public void setCurrentStudentAcademicYearHostelRooms(final Collection<StudentAcademicYearHostelRoom> currentStudentAcademicYearHostelRooms) {
		this.currentStudentAcademicYearHostelRooms = currentStudentAcademicYearHostelRooms;
	}

	public void loadCurrentHostelStudents() {
		if (this.loadcurrentStudentAcademicYearHostelRoomFlag) {
			this.currentStudentAcademicYearHostelRooms = this.studentAcademicYearHostelRoomService.findCurrentOccupantsForHostelRoom(this.hostelRoom.getId());
			this.loadcurrentStudentAcademicYearHostelRoomFlag = false;
		}
	}

	/**
	 * @return the loadcurrentStudentAcademicYearHostelRoomFlag
	 */
	public boolean isLoadcurrentStudentAcademicYearHostelRoomFlag() {
		return this.loadcurrentStudentAcademicYearHostelRoomFlag;
	}

	/**
	 * @param loadcurrentStudentAcademicYearHostelRoomFlag
	 *            the loadcurrentStudentAcademicYearHostelRoomFlag to set
	 */
	public void setLoadcurrentStudentAcademicYearHostelRoomFlag(final boolean loadcurrentStudentAcademicYearHostelRoomFlag) {
		this.loadcurrentStudentAcademicYearHostelRoomFlag = loadcurrentStudentAcademicYearHostelRoomFlag;
	}

}