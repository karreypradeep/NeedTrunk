package com.apeironsol.need.core.portal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.AcademicYearHoliday;
import com.apeironsol.need.core.model.Batch;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Organization;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.portal.util.UserSettingsBean;
import com.apeironsol.need.core.service.AcademicYearHolidayService;
import com.apeironsol.need.core.service.AcademicYearService;
import com.apeironsol.need.core.service.AddressService;
import com.apeironsol.need.core.service.BatchService;
import com.apeironsol.need.core.service.BranchService;
import com.apeironsol.need.core.service.KlassService;
import com.apeironsol.need.core.service.OrganizationService;
import com.apeironsol.need.core.service.SectionService;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.security.portal.GrantedAuthorityBean;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;

public abstract class AbstractPortalBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID	= 8602850641919725654L;

	private static final Logger				log					= Logger.getLogger(AbstractPortalBean.class);

	/**
	 * Organization service.
	 */
	@Resource
	protected OrganizationService			organizationService;

	@Resource
	protected BranchService					branchService;

	@Resource
	protected UserSettingsBean				userSettingsBean;

	@Resource
	protected SessionBean					sessionBean;

	@Resource
	protected AcademicYearService			academicYearService;

	@Resource
	protected KlassService					klassService;

	@Resource
	protected AddressService				addressService;

	@Resource
	protected SectionService				sectionService;

	@Resource
	protected BatchService					batchService;

	@Resource
	protected AcademicYearHolidayService	academicYearHolidayService;

	@Resource
	protected StudentService				studentService;

	/**
	 * Granted authority bean.
	 */
	@Resource
	protected GrantedAuthorityBean			grantedAuthorityBean;

	/**
	 * Boolean to indicate if the current page is in view or new action. True if
	 * user is either creating a new entity or updating/viewing.
	 */
	private boolean							viewOrNewAction;

	/**
	 * Boolean to indicate if user is updating/viewing an entity. True if user
	 * is updating/viewing.
	 */
	private boolean							viewAction;

	/**
	 * Boolean to indicate if user is creating an entity. True if user is
	 * creating.
	 */
	private boolean							newAction;

	/**
	 * Boolean to indicate to load active academic years for the current branch.
	 */
	private boolean							loadActiveAcademicYearsFlag;

	/**
	 * Collection holding only active academic years for the current branch.
	 */
	private Collection<AcademicYear>		activeAcademicYears;

	/**
	 * Academic year object id selected.
	 */
	protected Long							academicYearId;

	/**
	 * Collection of all the classes present in the branch.
	 */
	protected Collection<Klass>				klasses;

	/**
	 * Boolean to indicate if classes have to be loaded from database.
	 */
	protected boolean						loadKlassesFlag;

	/**
	 * Collection of active sections for a class.
	 */
	private Collection<Section>				activeSectionsForKlass;

	/**
	 * Boolean to indicate if active sections of a class have to be loaded from
	 * database.
	 */
	private boolean							loadActiveSectionsForKlassFlag;

	/***
	 * Boolean to indicate if academic years for which admission as opened have
	 * to be loaded from database.
	 */
	private boolean							loadAcademicYearsForAdmissionsOpenedFlag;

	/**
	 * Collection of active academic years for which admission as opened.
	 */
	private Collection<AcademicYear>		academicYearsWithAdmissionOpen;

	/**
	 * Boolean to indicate to load all academic years including active and non
	 * active for the current branch.
	 */
	private boolean							loadAllAcademicYearsFlag;

	/**
	 * Collection of all academic years including active and non active for the
	 * current branch.
	 */
	private Collection<AcademicYear>		allAcademicYearsForCurrentBranch;

	/**
	 * Boolean indicator to load all active batches for the branch.
	 */
	private boolean							loadActiveBatchesForBranchFlag;

	/**
	 * Collection of active batches for the branch.
	 */
	private Collection<Batch>				activeBatchesForBranch;

	/**
	 * Boolean indicator to load all active batches for the branch.
	 */
	private boolean							loadBatchesForBranchFlag;

	/**
	 * Collection of active batches for the branch.
	 */
	private Collection<Batch>				batchesForBranch;

	private AcademicYear					academicYear;

	private boolean							loadActiveKlassesForBranchFlag;

	private Collection<Klass>				activeKlassesForBranch;

	/**
	 * Get academic year Id.
	 * 
	 * @return academic year Id.
	 */
	@NotNull(message = "model.academic_year_mandatory")
	public Long getAcademicYearId() {
		return this.academicYearId;
	}

	/**
	 * Sets academic year Id.
	 * 
	 * @param academicYearId
	 *            academic year Id.
	 */
	public void setAcademicYearId(final Long academicYearId) {
		this.academicYearId = academicYearId;
	}

	/**
	 * Returns collection of active academic years for the current branch.
	 * 
	 * @return collection of active academic years
	 */
	public Collection<AcademicYear> getActiveAcademicYears() {
		if ((this.activeAcademicYears == null) || this.activeAcademicYears.isEmpty()) {
			this.loadActiveAcademicYearsFlag = true;
			loadActiveAcademicYearsForCurrentBranch();
		}
		return this.activeAcademicYears;
	}

	/**
	 * Sets active academic years for the current branch.
	 * 
	 * @param activeAcademicYears
	 *            active academic years for the current branch.
	 */
	public void setActiveAcademicYears(final Collection<AcademicYear> activeAcademicYears) {
		this.activeAcademicYears = activeAcademicYears;
	}

	/**
	 * Returns true if active academic years have to be loaded from database.
	 * 
	 * @return true if active academic years have to be loaded from database.
	 */
	public boolean isLoadActiveAcademicYearsFlag() {
		return this.loadActiveAcademicYearsFlag;
	}

	/**
	 * Sets true if active academic years have to be loaded from database.
	 * 
	 * @param loadActiveAcademicYearsFlag
	 *            boolean indicating if active academic years have to be loaded
	 *            from database.
	 */
	public void setLoadActiveAcademicYearsFlag(final boolean loadActiveAcademicYearsFlag) {
		this.loadActiveAcademicYearsFlag = loadActiveAcademicYearsFlag;
	}

	/**
	 * Returns true if classes have to be loaded from database.
	 * 
	 * @return true if classes have to be loaded from database.
	 */
	public boolean isLoadKlassesFlag() {
		return this.loadKlassesFlag;
	}

	/**
	 * Sets true if classes have to be loaded from database.
	 * 
	 * @param loadKlassesFlag
	 *            boolean indicating if classes have to be loaded from database.
	 */
	public void setLoadKlassesFlag(final boolean loadKlassesFlag) {
		this.loadKlassesFlag = loadKlassesFlag;
	}

	/**
	 * Returns collection of classes for the current branch.
	 * 
	 * @return collection of classes for the current branch.
	 */
	public Collection<Klass> getKlasses() {
		return this.klasses;
	}

	/**
	 * Sets collection of classes for the current branch.
	 * 
	 * @param klasses
	 *            collection of classes for the current branch.
	 */
	public void setKlasses(final Collection<Klass> klasses) {
		this.klasses = klasses;
	}

	/**
	 * Returns current organization.
	 * 
	 * @return current organization.
	 */
	protected Organization getOrganization() {
		Organization organization = this.sessionBean.getCurrentOrganization();
		if (organization == null) {
			organization = this.organizationService.getOrginazation();
			this.sessionBean.setCurrentOrganization(organization);
		}
		return organization;
	}

	/**
	 * Returns all branches for the current organization.
	 * 
	 * @return all branches for the current organization.
	 */
	protected Collection<Branch> getAllBranchs() {
		try {
			return this.branchService.findAllBranchs();
		} catch (final Exception ex) {
			log.info(ex.getMessage());
			ViewUtil.addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
			return null;
		}
	}

	/**
	 * Returns the current locale.
	 * 
	 * @return the current locale.
	 */
	public Locale getLocale() {
		return this.userSettingsBean.getLocale();
	}

	/**
	 * Load active academic years for the current branch from database.
	 */
	public void loadActiveAcademicYearsForCurrentBranch() {
		if (this.loadActiveAcademicYearsFlag) {
			final Branch branch = this.sessionBean.getCurrentBranch();
			if (branch != null) {
				try {
					setActiveAcademicYears(this.academicYearService.findAcademicYearsByBranchIdAndActiveStatus(branch.getId(), true));
				} catch (final BusinessException e) {
					ViewExceptionHandler.handle(e);
				}
				this.loadActiveAcademicYearsFlag = false;
			}

		}
	}

	/**
	 * Load academic years for which admissions are opened.
	 */
	public void loadAcademicYearsForAdmissionsOpened() {
		if (this.loadAcademicYearsForAdmissionsOpenedFlag) {
			final Branch branch = this.sessionBean.getCurrentBranch();
			try {
				this.academicYearsWithAdmissionOpen = this.academicYearService.findActiveAcademicYearsByBranchIdAndAdmissionsOpen(branch.getId());
			} catch (final BusinessException e) {
				ViewExceptionHandler.handle(e);
			}
			this.loadAcademicYearsForAdmissionsOpenedFlag = false;
		}
	}

	/**
	 * Return session bean.
	 * 
	 * @return the sessionBean
	 */
	public SessionBean getSessionBean() {
		return this.sessionBean;
	}

	/**
	 * Load classes from database.
	 */
	public void loadKlasses() {
		if (isLoadKlassesFlag()) {
			try {
				setKlasses(this.klassService.findKlassesByBranchId(this.sessionBean.getCurrentBranch().getId()));
			} catch (final BusinessException e) {
				ViewExceptionHandler.handle(e);
			}
			this.loadKlassesFlag = false;
		}
	}

	/**
	 * Returns true if academic years for which admissions are opened are to be
	 * loaded from database.
	 * 
	 * @return true if academic years for which admissions are opened are to be
	 *         loaded from database.
	 */
	public boolean isLoadAcademicYearsForAdmissionsOpenedFlag() {
		return this.loadAcademicYearsForAdmissionsOpenedFlag;
	}

	/**
	 * Sets true if academic years for which admissions are opened are to be
	 * loaded from database.
	 * 
	 * @param loadAcademicYearsForAdmissionsOpenedFlag
	 *            true if academic years for which admissions are opened are to
	 *            be loaded from database.
	 */
	public void setLoadAcademicYearsForAdmissionsOpenedFlag(final boolean loadAcademicYearsForAdmissionsOpenedFlag) {
		this.loadAcademicYearsForAdmissionsOpenedFlag = loadAcademicYearsForAdmissionsOpenedFlag;
	}

	/**
	 * Returns collection of academic years for which admissions are opened.
	 * 
	 * @return collection of academic years for which admissions are opened.
	 */
	public Collection<AcademicYear> getAcademicYearsWithAdmissionOpen() {
		return this.academicYearsWithAdmissionOpen;
	}

	/**
	 * Sets collection of academic years for which admissions are opened.
	 * 
	 * @param academicYearsWithAdmissionOpen
	 *            collection of academic years for which admissions are opened.
	 */
	public void setAcademicYearsWithAdmissionOpen(final Collection<AcademicYear> academicYearsWithAdmissionOpen) {
		this.academicYearsWithAdmissionOpen = academicYearsWithAdmissionOpen;
	}

	/**
	 * Returns true if user is performing either view or create new entity
	 * action.
	 * 
	 * @return true if user is performing either view or create new entity
	 *         action.
	 */
	public boolean isViewOrNewAction() {
		return this.viewOrNewAction;
	}

	/**
	 * Set true if user is performing either view or create new entity action.
	 * 
	 * @param viewOrNewAction
	 *            true if user is performing either view or create new entity
	 *            action.
	 */
	public void setViewOrNewAction(final boolean viewOrNewAction) {
		this.viewOrNewAction = viewOrNewAction;
	}

	/**
	 * Returns true if user is performing view entity action.
	 * 
	 * @return true if user is performing view entity action.
	 */
	public boolean isViewAction() {
		return this.viewAction;
	}

	/**
	 * Set true if user is performing view entity action.
	 * 
	 * @param viewAction
	 *            true if user is performing view entity action.
	 */
	public void setViewAction(final boolean viewAction) {
		this.viewAction = viewAction;
	}

	/**
	 * Returns true if user is performing create new entity action.
	 * 
	 * @return true if user is performing create new entity action.
	 */
	public boolean isNewAction() {
		return this.newAction;
	}

	/**
	 * Returns true if user is performing create new entity action.
	 * 
	 * @param newActiontrue
	 *            if user is performing create new entity action.
	 */
	public void setNewAction(final boolean newAction) {
		this.newAction = newAction;
	}

	/**
	 * Return collection of active sections for current class.
	 * 
	 * @return collection of active classes for current branch.
	 */
	public Collection<Section> getActiveSectionsForKlass() {
		return this.activeSectionsForKlass;
	}

	/**
	 * Set collection of active sections for current class.
	 * 
	 * @param activeSectionsForKlass
	 *            collection of active sections for current class.
	 */
	public void setActiveSectionsForKlass(final Collection<Section> activeSectionsForKlass) {
		this.activeSectionsForKlass = activeSectionsForKlass;
	}

	/**
	 * Load active sections for current class.
	 */
	public void loadActiveSectionsForKlass() {

		if (this.sessionBean.getCurrentKlass() != null) {

			this.activeSectionsForKlass = this.sectionService.findActiveSectionsByKlassId(this.sessionBean.getCurrentKlass().getId());
		}
	}

	/**
	 * Return true if active sections have to be loaded from database.
	 * 
	 * @return true if active sections have to be loaded from database.
	 */
	public boolean isLoadActiveSectionsForKlassFlag() {
		return this.loadActiveSectionsForKlassFlag;
	}

	/**
	 * Set true if active sections have to be loaded from database..
	 * 
	 * @param loadActiveSectionsForKlassFlag
	 *            true if active sections have to be loaded from database.
	 */
	public void setLoadActiveSectionsForKlassFlag(final boolean loadActiveSectionsForKlassFlag) {
		this.loadActiveSectionsForKlassFlag = loadActiveSectionsForKlassFlag;
	}

	/**
	 * Return true if all academic years have to loaded from database.
	 * 
	 * @return true if all academic years have to loaded from database.
	 */
	public boolean isLoadAllAcademicYearsFlag() {
		return this.loadAllAcademicYearsFlag;
	}

	/**
	 * Set true if all academic years have to loaded from database..
	 * 
	 * @param loadAllAcademicYearsFlag
	 *            true if all academic years have to loaded from database.
	 */
	public void setLoadAllAcademicYearsFlag(final boolean loadAllAcademicYearsFlag) {
		this.loadAllAcademicYearsFlag = loadAllAcademicYearsFlag;
	}

	/**
	 * Load academic years present in the database for current branch.
	 */
	public void loadAllAcademicYearsForCurrentBranch() {
		try {
			if (isLoadAllAcademicYearsFlag()) {
				final Branch currentBranch = this.sessionBean.getCurrentBranch();
				this.allAcademicYearsForCurrentBranch = this.academicYearService.findAcademicYearsByBranchId(currentBranch.getId());
				setLoadAllAcademicYearsFlag(false);
			}
		} catch (final ApplicationException ex) {
			log.info(ex.getMessage());
			ViewExceptionHandler.handle(ex);
		}

	}

	/**
	 * Return all academic years for the current branch.
	 * 
	 * @return all academic years for the current branch.
	 */
	public Collection<AcademicYear> getAllAcademicYearsForCurrentBranch() {
		return this.allAcademicYearsForCurrentBranch;
	}

	/**
	 * Set all academic years for the current branch.
	 * 
	 * @param allAcademicYearsForCurrentBranch
	 *            all academic years for the current branch.
	 */
	public void setAllAcademicYearsForCurrentBranch(final Collection<AcademicYear> allAcademicYearsForCurrentBranch) {
		this.allAcademicYearsForCurrentBranch = allAcademicYearsForCurrentBranch;
	}

	/**
	 * Return academic year for supplied object id.
	 * 
	 * @param academicYearId
	 *            academic year id.
	 * @return academic year for supplied object id.
	 */
	public AcademicYear getAcademicYearById(final Long academicYearId) {
		AcademicYear result = null;
		if ((this.allAcademicYearsForCurrentBranch == null) || this.allAcademicYearsForCurrentBranch.isEmpty()) {
			this.loadAllAcademicYearsFlag = true;
			loadAllAcademicYearsForCurrentBranch();
		}
		for (final AcademicYear academicYear : this.allAcademicYearsForCurrentBranch) {
			if (academicYear.getId().equals(academicYearId)) {
				result = academicYear;
				break;
			}
		}
		return result;
	}

	public Collection<AcademicYearHoliday> getAcademicYearHolidaysForAcademicYearId(final Long academicYearId) {
		return this.academicYearHolidayService.findAcademicYearHolidaysByAcademicYearId(academicYearId);
	}

	public Collection<AcademicYearHoliday> getAcademicYearWeekEndsForAcademicYearId(final Long academicYearId) {
		return this.academicYearHolidayService.findAcademicYearHolidaysByAcademicYearId(academicYearId);
	}

	public int getNoOfAttendanceDaysForCurrentBranchAndAcademisYearById(final Long academicYearId) {
		int noOfDays = 365;
		final AcademicYear academicYear = getAcademicYearById(academicYearId);
		noOfDays = DateUtil.dateDiffInDays(academicYear.getStartDate(), academicYear.getEndDate());
		final Collection<AcademicYearHoliday> academicYearHolidays = getAcademicYearHolidaysForAcademicYearId(academicYearId);
		final Collection<AcademicYearHoliday> academicYearWeekEnds = getAcademicYearWeekEndsForAcademicYearId(academicYearId);
		if (academicYearHolidays != null) {
			noOfDays = noOfDays - academicYearHolidays.size();
		}

		if (academicYearWeekEnds != null) {
			noOfDays = noOfDays - academicYearWeekEnds.size();
		}

		return noOfDays;
	}

	/**
	 * Returns start date of current branch as the minimum date.
	 * 
	 * @return start date of current branch as the minimum date.
	 */
	public Date getMinimumDateForCurrentBranch() {
		return this.sessionBean.getCurrentBranch() != null ? this.sessionBean.getCurrentBranch().getStartDate() : null;
	}

	/**
	 * Returns start date of current branch as the minimum date.
	 * 
	 * @return start date of current branch as the minimum date.
	 */
	public Date getCurrentDate() {
		return DateUtil.getSystemDate();
	}

	public boolean isLoadActiveBatchesForBranchFlag() {
		return this.loadActiveBatchesForBranchFlag;
	}

	public void setLoadActiveBatchesForBranchFlag(final boolean loadActiveBatchesForBranchFlag) {
		this.loadActiveBatchesForBranchFlag = loadActiveBatchesForBranchFlag;
	}

	public Collection<Batch> getActiveBatchesForBranch() {
		return this.activeBatchesForBranch;
	}

	public void setActiveBatchesForBranch(final Collection<Batch> activeBatchesForBranch) {
		this.activeBatchesForBranch = activeBatchesForBranch;
	}

	public void loadActiveBatchesForBranch() {
		if (this.loadActiveBatchesForBranchFlag) {
			this.activeBatchesForBranch = this.batchService.findActiveBatchesByBranchId(this.sessionBean.getCurrentBranch().getId());

			this.loadActiveBatchesForBranchFlag = false;
		}
	}

	public boolean isLoadBatchesForBranchFlag() {
		return this.loadBatchesForBranchFlag;
	}

	public void setLoadBatchesForBranchFlag(final boolean loadBatchesForBranchFlag) {
		this.loadBatchesForBranchFlag = loadBatchesForBranchFlag;
	}

	public void loadBatchesForBranch() {
		if (this.loadBatchesForBranchFlag) {
			this.batchesForBranch = this.batchService.findBatchesByBranchId(this.sessionBean.getCurrentBranch().getId());

			this.loadBatchesForBranchFlag = false;
		}
	}

	public Collection<Batch> getBatchesForBranch() {
		return this.batchesForBranch;
	}

	public void setBatchesForBranch(final Collection<Batch> batchesForBranch) {
		this.batchesForBranch = batchesForBranch;
	}

	public Collection<Klass> getActiveKlassesForBranch() {
		return this.activeKlassesForBranch;
	}

	public void setActiveKlassesForBranch(final Collection<Klass> activeKlassesForBranch) {
		this.activeKlassesForBranch = activeKlassesForBranch;
	}

	public AcademicYear getAcademicYear() {
		return this.academicYear;
	}

	public void setAcademicYear(final AcademicYear academicYear) {
		this.academicYear = academicYear;
	}

	public boolean isLoadActiveKlassesForBranchFlag() {
		return this.loadActiveKlassesForBranchFlag;
	}

	public void setLoadActiveKlassesForBranchFlag(final boolean loadActiveKlassesForBranchFlag) {
		this.loadActiveKlassesForBranchFlag = loadActiveKlassesForBranchFlag;
	}

	public void loadActiveKlassesForBranch() {
		if (this.loadActiveKlassesForBranchFlag) {

			this.activeKlassesForBranch = this.klassService.findActiveKlassesByBranchId(this.sessionBean.getCurrentBranch().getId());

			this.loadActiveKlassesForBranchFlag = false;
		}
	}

	public boolean isActiveStudentsExistsForCurrentBranch() {

		return this.studentService.isActiveStudentsForBranchId(this.sessionBean.getCurrentBranch().getId());
	}
}
