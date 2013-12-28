package com.apeironsol.need.core.portal;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.AcademicYearHoliday;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BranchAssembly;
import com.apeironsol.need.core.model.BranchRule;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.Organization;
import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.service.AcademicYearHolidayService;
import com.apeironsol.need.core.service.AcademicYearService;
import com.apeironsol.need.core.service.BranchAssemblyService;
import com.apeironsol.need.core.service.BranchRuleService;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.core.service.KlassService;
import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.need.security.model.UserAccount;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.need.util.constants.WeekDayConstant;
import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope(value = "session")
public class SessionBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long				serialVersionUID						= 3397446964824499454L;

	@Resource
	private AcademicYearService				academicYearService;

	private Employee						loginEmployee;

	private Student							loginStudent;

	private Relation						loginRelation;

	private Organization					currentOrganization;

	private Branch							currentBranch;

	private Klass							currentKlass;

	private UserAccount						currentUserAccount;

	private boolean							loadBranchTreeFromDatabase				= false;

	private boolean							loadBranchBuildingBlockTreeFromDatabase	= false;

	private int								activeTabIndex;

	private AcademicYear					currentAcademicYear;

	private StudentAcademicYear				currentStudentAcademicYear;

	private String							homeURL;

	@Resource
	private BranchAssemblyService			branchAssemblyService;

	Collection<BranchAssembly>				branchAssemblies;
	/**
	 * Collection of all the active classes present in the branch.
	 */
	private Collection<Klass>				activeKlasses;

	/**
	 * Collection of all the active classes present in the branch.
	 */
	private Collection<Klass>				allKlasses;

	@Resource
	protected KlassService					klassService;

	@Resource
	private AcademicYearHolidayService		academicYearHolidayService;

	/**
	 * Boolean to indicate if active classes have to be loaded from database.
	 */
	private boolean							loadActiveKlassesFlag;

	private Collection<AcademicYearHoliday>	academicYearHolidays;

	/**
	 * Boolean to indicate if active classes have to be loaded from database.
	 */
	private boolean							loadacademicYearHolidaysFlag;

	@Resource
	private BuildingBlockService			buildingBlockService;

	Collection<BuildingBlock>				branchBuildingBlocksForFeeType;

	@Resource
	private BranchRuleService				branchRuleService;

	private BranchRule						currentBranchRule;

	public boolean isLoadBranchBuildingBlockTreeFromDatabase() {
		return this.loadBranchBuildingBlockTreeFromDatabase;
	}

	public void setLoadBranchBuildingBlockTreeFromDatabase(final boolean loadBranchBuildingBlockTreeFromDatabase) {
		this.loadBranchBuildingBlockTreeFromDatabase = loadBranchBuildingBlockTreeFromDatabase;
	}

	public boolean isLoadBranchTreeFromDatabase() {
		return this.loadBranchTreeFromDatabase;
	}

	public void setLoadBranchTreeFromDatabase(final boolean loadBranchTreeFromDatabase) {
		this.loadBranchTreeFromDatabase = loadBranchTreeFromDatabase;
	}

	public UserAccount getCurrentUserAccount() {
		return this.currentUserAccount;
	}

	public void setCurrentUserAccount(final UserAccount currentUserAccount) {
		this.currentUserAccount = currentUserAccount;
	}

	public Branch getCurrentBranch() {
		return this.currentBranch;
	}

	public void setCurrentBranch(final Branch currentBranch) {
		this.currentBranch = currentBranch;
	}

	public Klass getCurrentKlass() {
		return this.currentKlass;
	}

	public void setCurrentKlass(final Klass currentKlass) {
		this.currentKlass = currentKlass;
	}

	public TimeZone getDefaultTimeZone() {
		return TimeZone.getDefault();
	}

	public Organization getCurrentOrganization() {
		return this.currentOrganization;
	}

	public void setCurrentOrganization(final Organization currentOrganization) {
		this.currentOrganization = currentOrganization;
	}

	public int getActiveTabIndex() {
		return this.activeTabIndex;
	}

	public void setActiveTabIndex(final int activeTabIndex) {
		this.activeTabIndex = activeTabIndex;
	}

	public AcademicYear getCurrentAcademicYear() {

		if (this.currentAcademicYear == null) {

			if (this.currentBranch != null) {
				this.currentAcademicYear = this.academicYearService.findAcademicYearByBranchIdAndDate(this.currentBranch.getId(), DateUtil.getSystemDate());
			}
		}

		return this.currentAcademicYear;
	}

	public void setCurrentAcademicYear(final AcademicYear currentAcademicYear) {
		this.currentAcademicYear = currentAcademicYear;
	}

	public String getPrinipal() {
		return ViewUtil.getPrincipal();
	}

	public String getHomeURL() {
		return this.homeURL;
	}

	public void setHomeURL(final String homeURL) {
		this.homeURL = homeURL;
	}

	public List<WeekDayConstant> getWeekDays() {
		return WeekDayConstant.getWeekDayConstantsExcludingWeekEnd(Arrays.asList(WeekDayConstant.SUNDAY));
	}

	public Date getCurrentDate() {
		return DateUtil.getSystemDate();
	}

	public StudentAcademicYear getCurrentStudentAcademicYear() {
		return this.currentStudentAcademicYear;
	}

	public void setCurrentStudentAcademicYear(final StudentAcademicYear currentStudentAcademicYear) {
		this.currentStudentAcademicYear = currentStudentAcademicYear;
	}

	/**
	 * Return collection of active classes for current branch.
	 * 
	 * @return collection of active classes for current branch.
	 */
	public Collection<Klass> getActiveKlasses() {
		if (this.loadActiveKlassesFlag || this.activeKlasses == null || this.activeKlasses.isEmpty()) {
			this.loadActiveKlassesFlag = true;
			this.loadActiveKlasses();
		}
		return this.activeKlasses;
	}

	/**
	 * Set collection of active classes for current branch.
	 * 
	 * @param activeKlasses
	 *            collection of active classes for current branch.
	 */
	public void setActiveKlasses(final Collection<Klass> activeKlasses) {
		this.activeKlasses = activeKlasses;
	}

	/**
	 * Load active classes for current branch from database.
	 */
	public void loadActiveKlasses() {

		if (this.getCurrentBranch() != null && this.loadActiveKlassesFlag) {

			this.activeKlasses = this.klassService.findActiveKlassesByBranchId(this.getCurrentBranch().getId());
			this.allKlasses = this.klassService.findKlassesByBranchId(this.getCurrentBranch().getId());
			this.loadActiveKlassesFlag = false;
		}

	}

	/**
	 * Returns true if active classes for current branch are to be loaded from
	 * database.
	 * 
	 * @return true if active classes for current branch are to be loaded from
	 *         database.
	 */
	public boolean isLoadActiveKlassesFlag() {
		return this.loadActiveKlassesFlag;
	}

	/**
	 * Set true if active classes for current branch are to be loaded from
	 * database.
	 * 
	 * @param loadActiveKlassesFlag
	 *            true if active classes for current branch are to be loaded
	 *            from database.
	 */
	public void setLoadActiveKlassesFlag(final boolean loadActiveKlassesFlag) {
		this.loadActiveKlassesFlag = loadActiveKlassesFlag;
	}

	public Date getSystemDate() {
		return DateUtil.getSystemDate();
	}

	public Employee getLoginEmployee() {
		return this.loginEmployee;
	}

	public void setLoginEmployee(final Employee loginEmployee) {
		this.loginEmployee = loginEmployee;
	}

	public Student getLoginStudent() {
		return this.loginStudent;
	}

	public void setLoginStudent(final Student loginStudent) {
		this.loginStudent = loginStudent;
	}

	public Relation getLoginRelation() {
		return this.loginRelation;
	}

	public void setLoginRelation(final Relation loginRelation) {
		this.loginRelation = loginRelation;
	}

	/**
	 * Return true if date supplied is holiday.
	 * 
	 * @param suppliedDate
	 *            date for which holiday has to be checked.
	 * @return true if date supplied is holiday.
	 */
	public boolean isHoliday(final Calendar suppliedDate) {
		boolean result = false;
		for (final AcademicYearHoliday academicYearHoliday : this.getAcademicYearHolidays()) {
			final Calendar academicYearHolidayStartDate = Calendar.getInstance();
			academicYearHolidayStartDate.setTime(academicYearHoliday.getStartDate());
			DateUtil.clearTimeInfo(academicYearHolidayStartDate);
			final Calendar academicYearHolidayEndDate = Calendar.getInstance();
			academicYearHolidayEndDate.setTime(academicYearHoliday.getEndDate());
			DateUtil.clearTimeInfo(academicYearHolidayEndDate);
			if (suppliedDate.equals(academicYearHolidayStartDate) || suppliedDate.equals(academicYearHolidayEndDate)
					|| suppliedDate.after(academicYearHolidayStartDate) && suppliedDate.before(academicYearHolidayEndDate)) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * @return the academicYearHolidays
	 */
	public Collection<AcademicYearHoliday> getAcademicYearHolidays() {
		return this.academicYearHolidays;
	}

	/**
	 * @param academicYearHolidays
	 *            the academicYearHolidays to set
	 */
	public void setAcademicYearHolidays(final Collection<AcademicYearHoliday> academicYearHolidays) {
		this.academicYearHolidays = academicYearHolidays;
	}

	public void loadAcademicYearHolidays(final AcademicYear academicYear) {
		if (this.isLoadacademicYearHolidaysFlag()) {
			this.academicYearHolidays = this.academicYearHolidayService.findAcademicYearHolidaysByAcademicYearId(academicYear.getId());
		}
	}

	/**
	 * @return the loadacademicYearHolidaysFlag
	 */
	public boolean isLoadacademicYearHolidaysFlag() {
		return this.loadacademicYearHolidaysFlag;
	}

	/**
	 * @param loadacademicYearHolidaysFlag
	 *            the loadacademicYearHolidaysFlag to set
	 */
	public void setLoadacademicYearHolidaysFlag(final boolean loadacademicYearHolidaysFlag) {
		this.loadacademicYearHolidaysFlag = loadacademicYearHolidaysFlag;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isBranchHavingApplicationFormFee() {

		final Collection<BuildingBlock> buildingBlocks = this.buildingBlockService.findFeeTypeBuildingBlocksbyBranchIdAndFeeType(this.getCurrentBranch()
				.getId(), FeeTypeConstant.APPLICATION_FEE);
		if (buildingBlocks != null && !buildingBlocks.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * @return the currentBranchRule
	 */
	public BranchRule getCurrentBranchRule() {
		if (this.currentBranchRule == null) {
			this.currentBranchRule = this.branchRuleService.findBranchRuleByBranchId(this.currentBranch.getId());
		}
		return this.currentBranchRule;
	}

	/**
	 * @param currentBranchRule
	 *            the currentBranchRule to set
	 */
	public void setCurrentBranchRule(final BranchRule currentBranchRule) {
		this.currentBranchRule = currentBranchRule;
	}

	/**
	 * @return the allKlasses
	 */
	public Collection<Klass> getAllKlasses() {
		return this.allKlasses;
	}

	/**
	 * @param allKlasses
	 *            the allKlasses to set
	 */
	public void setAllKlasses(final Collection<Klass> allKlasses) {
		this.allKlasses = allKlasses;
	}
}
