/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 */
package com.apeironsol.need.core.portal;

/**
 * View student class.
 * 
 * @author Pradeep
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageInputStream;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;

import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.model.ProfilePicture;
import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAbsent;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.service.BuildingBlockService;
import com.apeironsol.need.core.service.ProfilePictureService;
import com.apeironsol.need.core.service.StudentAttendanceService;
import com.apeironsol.need.financial.service.StudentFinancialService;
import com.apeironsol.need.hostel.model.StudentAcademicYearHostelRoom;
import com.apeironsol.need.hostel.service.StudentAcademicYearHostelRoomService;
import com.apeironsol.need.security.model.UserAccount;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.ResidenceConstant;
import com.apeironsol.need.util.constants.UserAccountTypeConstant;
import com.apeironsol.need.util.constants.ViewContentConstant;
import com.apeironsol.need.util.portal.StudentTabModel;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.StudentSearchCriteria;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

@Named
@Scope("session")
public class StudentBean extends AbstractStudentBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long						serialVersionUID		= 5977372257551033633L;

	@Resource
	protected StudentFinancialService				studentFinancialService;

	@Resource
	protected BuildingBlockService					buildingBlockService;

	@Resource
	protected ProfilePictureService					profilePictureService;

	private boolean									editStudentPersonalDetails;

	private boolean									editStudentFatherDetails;

	private boolean									editStudentMotherDetails;

	private boolean									editStudentGuardianDetails;

	private boolean									editStudentMedicalHistory;

	private byte[]									profilePicture;

	private boolean									loadProfilePictureFlag;

	// private StudentAcademicYear studentAcademicYear;

	private Collection<StudentAcademicYear>			studentAcademicYears;

	private boolean									loadAcademicYearsFlag;

	@Resource
	private StudentAttendanceService				studentAttendanceService;

	private StudentSearchCriteria					studentSearchCriteria	= null;

	private Collection<StudentSection>				studentSectionsBySearchCriteria;

	private boolean									loadStudentByUserIdFlag;

	private StudentTabModel							studentTabModel			= new StudentTabModel();

	private Collection<Section>						sectionsForSearhCriteriaByKlass;

	@Resource
	private StudentAcademicYearHostelRoomService	studentAcademicYearHostelRoomService;

	Collection<StudentAcademicYearHostelRoom>		studentAcademicYearHostelRooms;

	public enum StudentAction {

		PROMOTE_STUDENT("promoteStudent"),
		TRANSFER_SECTION("transferSection"),
		DROPOUT_STUDENT("dropoutStudent"),
		TERMINATE_STUDENT("terminateStudent"),
		ROLLBACK_DROPOUT_STUDENT("rollbackdropoutStudent"), ;

		private String	key;

		StudentAction(final String key) {
			this.key = key;
		}

		public String getKey() {
			return this.key;
		}

		public void setKey(final String key) {
			this.key = key;
		}
	};

	private StudentAction	studentCurrentAction;

	private boolean			outstandingFeeDue;

	private String			actionComment;

	public boolean isLoadStudentByUserIdFlag() {
		return this.loadStudentByUserIdFlag;
	}

	public boolean isLoadLatestAcademicYearDetailsFlag() {
		return this.loadLatestAcademicYearDetailsFlag;
	}

	public void setLoadLatestAcademicYearDetailsFlag(final boolean loadLatestAcademicYearDetailsFlag) {
		this.loadLatestAcademicYearDetailsFlag = loadLatestAcademicYearDetailsFlag;
	}

	public void setLoadStudentByUserIdFlag(final boolean loadStudentByUserIdFlag) {
		this.loadStudentByUserIdFlag = loadStudentByUserIdFlag;
	}

	/**
	 * @return the editStudentMedicalHistory
	 */
	public boolean isEditStudentMedicalHistory() {
		return this.editStudentMedicalHistory;
	}

	/**
	 * @param editStudentMedicalHistory
	 *            the editStudentMedicalHistory to set
	 */
	public void setEditStudentMedicalHistory(final boolean editStudentMedicalHistory) {
		this.editStudentMedicalHistory = editStudentMedicalHistory;
	}

	/**
	 * Stores student attendance for today.
	 */
	private StudentAbsent	studentAttendance;

	/**
	 * @return the studentAcademicYear
	 */
	public StudentAcademicYear getStudentAcademicYear() {

		if (this.getCurrentOrMostRecentAcademicYear() == null) {
			this.setCurrentOrMostRecentAcademicYear(this.studentAcademicYearService.findStudentCurrentOrMostRecentAcademicYearByStudentId(this.student.getId()));
		}
		return this.getCurrentOrMostRecentAcademicYear();
	}

	/**
	 * @param studentAcademicYear
	 *            the studentAcademicYear to set
	 */
	public void setStudentAcademicYear(final StudentAcademicYear studentAcademicYear) {
		this.setCurrentOrMostRecentAcademicYear(studentAcademicYear);
	}

	/**
	 * @return the editStudentFatherDetails
	 */
	public boolean isEditStudentFatherDetails() {
		return this.editStudentFatherDetails;
	}

	/**
	 * @param editStudentFatherDetails
	 *            the editStudentFatherDetails to set
	 */
	public void setEditStudentFatherDetails(final boolean editStudentFatherDetails) {
		this.editStudentFatherDetails = editStudentFatherDetails;
	}

	/**
	 * @return the editStudentMotherDetails
	 */
	public boolean isEditStudentMotherDetails() {
		return this.editStudentMotherDetails;
	}

	/**
	 * @param editStudentMotherDetails
	 *            the editStudentMotherDetails to set
	 */
	public void setEditStudentMotherDetails(final boolean editStudentMotherDetails) {
		this.editStudentMotherDetails = editStudentMotherDetails;
	}

	/**
	 * @return the editStudentGuardianDetails
	 */
	public boolean isEditStudentGuardianDetails() {
		return this.editStudentGuardianDetails;
	}

	/**
	 * @param editStudentGuardianDetails
	 *            the editStudentGuardianDetails to set
	 */
	public void setEditStudentGuardianDetails(final boolean editStudentGuardianDetails) {
		this.editStudentGuardianDetails = editStudentGuardianDetails;
	}

	private boolean	renderTakePhoto;

	@PostConstruct
	public void init() {
		this.student = new Student();
		this.relation = new Relation();
		this.relationAddress = new Address();
		this.initializeSearchCriteria();
	}

	/**
	 * @return the editStudentPersonalDetails
	 */
	public boolean isEditStudentPersonalDetails() {
		return this.editStudentPersonalDetails;
	}

	/**
	 * @param editStudentPersonalDetails
	 *            the editStudentPersonalDetails to set
	 */
	public void setEditStudentPersonalDetails(final boolean editStudentPersonalDetails) {
		this.editStudentPersonalDetails = editStudentPersonalDetails;
	}

	public String saveStudent() {
		try {
			this.student = this.studentService.saveStudent(this.student);
			this.editStudentPersonalDetails = false;
		} catch (final ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (final Throwable e) {
			ViewExceptionHandler.handle(e);
		}
		return null;
	}

	public void loadInitStudentProfileData() {
		this.editStudentPersonalDetails = false;
		this.editStudentAddressDetails = false;
	}

	@Override
	public void onTabChange() {

	}

	public void onWebcam() {
		this.renderTakePhoto = true;
	}

	public void offWebcam() {
		this.renderTakePhoto = false;
	}

	public boolean isRenderTakePhoto() {
		return this.renderTakePhoto;
	}

	public void setRenderTakePhoto(final boolean renderTakePhoto) {
		this.renderTakePhoto = renderTakePhoto;
	}

	public void handleFileUpload(final FileUploadEvent event) {

		try {
			final InputStream input = event.getFile().getInputstream();

			final byte photoBytes[] = IOUtils.toByteArray(input);

			final ProfilePicture profilePicture = this.profilePictureService.saveStudentProfilePicture(this.student.getAdmissionNr(), photoBytes);

			this.profilePicture = profilePicture.getProfilePicture();

			ViewUtil.addMessage("Succesfully uploaded.", FacesMessage.SEVERITY_INFO);
		} catch (final IOException e) {

		}
	}

	public StreamedContent getProfilePictureStreamedContent() {
		return new DefaultStreamedContent(new ByteArrayInputStream(this.profilePicture), "image/jpeg");
	}

	public void loadProfilePicture() {

		if (this.loadProfilePictureFlag) {

			final ProfilePicture profilePicture = this.profilePictureService.findStudentProfilePicture(this.student.getAdmissionNr());

			if (profilePicture == null) {

				final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

				final String profilePictureCatoon = servletContext.getRealPath("") + File.separator + "images" + File.separator + "profile-photo.jpeg";

				try {

					this.profilePicture = this.readImageToByteArray(profilePictureCatoon);

				} catch (final FileNotFoundException e) {

				} catch (final IOException e) {

				}
			} else {
				this.profilePicture = profilePicture.getProfilePicture();
			}
			this.loadStudentAttendanceStatus();
			this.loadProfilePictureFlag = false;
		}

	}

	private byte[] readImageToByteArray(final String imagePath) throws FileNotFoundException, IOException {

		FileImageInputStream fiStream = null;

		ByteArrayOutputStream output = null;
		byte[] buf = null;
		byte[] data = null;

		fiStream = new FileImageInputStream(new File(imagePath));

		output = new ByteArrayOutputStream();
		buf = new byte[512];
		data = null;

		int numBytesRead = 0;
		while ((numBytesRead = fiStream.read(buf)) != -1) {
			output.write(buf, 0, numBytesRead);
		}

		data = output.toByteArray();
		output.close();
		fiStream.close();

		return data;
	}

	public byte[] getProfilePicture() {
		return this.profilePicture;
	}

	public void setProfilePicture(final byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public boolean isLoadProfilePictureFlag() {
		return this.loadProfilePictureFlag;
	}

	public void setLoadProfilePictureFlag(final boolean loadProfilePictureFlag) {
		this.loadProfilePictureFlag = loadProfilePictureFlag;
	}

	public void loadStudentAttendanceStatus() {
		final Date today = new Date();
		DateUtil.clearTimeInfo(today);
		this.studentAttendance = this.studentAttendanceService
				.findStudentAttendanceByStudentAcademicYearIdAndDate(this.getStudentAcademicYear().getId(), today);
	}

	public String getStudentTodayAttandanceStatus() {
		return this.studentAttendance == null ? "Present in school" : "Absent";
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

	public void getStudentsbySeacrhCriteria() {

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

	public String resetSearchCriteria() {
		this.studentSearchCriteria.resetSeacrhCriteria();
		return null;
	}

	public void initializeSearchCriteria() {
		if (this.studentSearchCriteria == null) {
			this.studentSearchCriteria = new StudentSearchCriteria(this.sessionBean.getCurrentBranch());
		}
	}

	public void loadStudentAcademicYears() {
		if (this.loadAcademicYearsFlag) {
			if (this.student.getId() != null) {
				this.studentAcademicYears = this.studentAcademicYearService.findStudentAcademicYearsByStudentId(this.student.getId());
			}
			this.loadAcademicYearsFlag = false;
		}
	}

	public Collection<StudentAcademicYear> getStudentAcademicYears() {
		return this.studentAcademicYears;
	}

	public void setStudentAcademicYears(final Collection<StudentAcademicYear> studentAcademicYears) {
		this.studentAcademicYears = studentAcademicYears;
	}

	public boolean isLoadAcademicYearsFlag() {
		return this.loadAcademicYearsFlag;
	}

	public void setLoadAcademicYearsFlag(final boolean loadAcademicYearsFlag) {
		this.loadAcademicYearsFlag = loadAcademicYearsFlag;
	}

	public void loadStudentByUserId() {
		if (this.loadStudentByUserIdFlag) {
			final UserAccount userAccount = this.sessionBean.getCurrentUserAccount();
			if (UserAccountTypeConstant.STUDENT.equals(userAccount.getUserAccountType())) {
				this.student = this.studentService.findStudentByUsername(userAccount.getUsername());
			}
			this.loadStudentByUserIdFlag = false;
		}
	}

	public void preProcessPDF(final Object document) throws IOException, BadElementException, DocumentException {
		final Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);
		final ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		try {
			final String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "school_logo.png";
			pdf.add(Image.getInstance(logo));
		} catch (final IOException e) {

		}
		pdf.addTitle(this.sessionBean.getCurrentBranch().getName());
		pdf.addHeader("Header", "Fee collected");
	}

	public void studentAcademicYearChangeListner() {
		this.setStudentSection(this.studentSectionService.findLatestStudentSectionByStudentAcademicYearId(this.getStudentAcademicYear().getId()));
		this.preStudentLoad();
	}

	/**
	 * This method is called before we need to display student information.
	 */
	public void loadStudentsDetailsBeforeDisplayInformation(final StudentSection studentSection) {
		this.setStudentSection(studentSection);
		this.setStudentAcademicYear(studentSection.getStudentAcademicYear());
		this.preStudentLoad();
	}

	private void preStudentLoad() {
		this.getStudentTabModel().setActiveTab(this.getStudentTabModel().getPersonalTab());
		this.setViewAction(true);
		this.setLoadRelationsFlag(true);
		this.setLoadProfilePictureFlag(true);
		this.setLoadAcademicYearsFlag(true);
		this.setLoadStudentLatestAcademicYearDetailsInd(true);
		this.setStudent(this.getStudentAcademicYear().getStudent());
		this.loadStudentLatestAcademicYearDetails();
	}

	/**
	 * @return the loadStudentLatestAcademicYearDetailsInd
	 */
	public boolean isLoadStudentLatestAcademicYearDetailsInd() {
		return this.loadStudentLatestAcademicYearDetailsInd;
	}

	/**
	 * @param loadStudentLatestAcademicYearDetailsInd
	 *            the loadStudentLatestAcademicYearDetailsInd to set
	 */
	public void setLoadStudentLatestAcademicYearDetailsInd(final boolean loadStudentLatestAcademicYearDetailsInd) {
		this.loadStudentLatestAcademicYearDetailsInd = loadStudentLatestAcademicYearDetailsInd;
	}

	/**
	 * @return the currentOrMostRecentStudentSection
	 */
	public StudentSection getCurrentOrMostRecentStudentSection() {
		return this.currentOrMostRecentStudentSection;
	}

	public StudentTabModel getStudentTabModel() {
		return this.studentTabModel;
	}

	public void setStudentTabModel(final StudentTabModel studentTabModel) {
		this.studentTabModel = studentTabModel;
	}

	public void handleStudentTabModel() {

		this.getStudentTabModel().getStatusHistoryTab().setRendered(this.grantedAuthorityBean.isUserAllowedToAccessStudentStatusHistory());

		this.getStudentTabModel().getAttendanceTab().setRendered(this.grantedAuthorityBean.isUserAllowedToAccessStudentAttendance());

		this.getStudentTabModel().getNotificationTab().setRendered(this.grantedAuthorityBean.isUserAllowedToAccessStudentNotifications());

		this.getStudentTabModel().getTransportationTab().setRendered(this.grantedAuthorityBean.isUserAllowedToAccessStudentTransportation());

		this.getStudentTabModel().getAcademicsTab().setRendered(this.grantedAuthorityBean.isUserAllowedToAccessStudentAcademics());

		this.getStudentTabModel().getHostelRoomTab().setRendered(this.grantedAuthorityBean.isUserAllowedToAccessStudentHostelRoom());

		final boolean accessStudentFee = this.grantedAuthorityBean.isUserAllowedToAccessStudentFees();

		this.getStudentTabModel().getStudentPocketMoneyTab().setRendered(this.getStudent().getResidence().equals(ResidenceConstant.HOSTEL) && accessStudentFee);

		this.getStudentTabModel().getPaymentsTab().setRendered(accessStudentFee);

		this.getStudentTabModel().getStudentAdditionalFeeTab().setRendered(accessStudentFee);

	}

	/**
	 * @return the sectionsForSearhCriteriaByKlass
	 */
	public Collection<Section> getSectionsForSearhCriteriaByKlass() {
		return this.sectionsForSearhCriteriaByKlass;
	}

	/**
	 * @param sectionsForSearhCriteriaByKlass
	 *            the sectionsForSearhCriteriaByKlass to set
	 */
	public void setSectionsForSearhCriteriaByKlass(final Collection<Section> sectionsForSearhCriteriaByKlass) {
		this.sectionsForSearhCriteriaByKlass = sectionsForSearhCriteriaByKlass;
	}

	public void handleFromKlassChange() {
		this.studentSearchCriteria.setSection(null);
		if (this.studentSearchCriteria.getKlass() != null) {
			if (this.studentSearchCriteria.getAcademicYear() != null) {
				this.setSectionsForSearhCriteriaByKlass(this.sectionService.findActiveSectionsByKlassIdAndAcademicYearId(this.studentSearchCriteria.getKlass()
						.getId(), this.studentSearchCriteria.getAcademicYear().getId()));
			} else {
				this.setSectionsForSearhCriteriaByKlass(this.sectionService.findActiveSectionsByKlassId(this.studentSearchCriteria.getKlass().getId()));
			}
		} else {
			this.setSectionsForSearhCriteriaByKlass(null);
		}
	}

	public StudentAction getStudentCurrentAction() {
		return this.studentCurrentAction;
	}

	public void setStudentCurrentAction(final StudentAction studentCurrentAction) {
		this.studentCurrentAction = studentCurrentAction;
	}

	public void promoteStudentAction() {
		this.studentCurrentAction = StudentAction.PROMOTE_STUDENT;
	}

	public void transferSectionAction() {
		this.studentCurrentAction = StudentAction.TRANSFER_SECTION;
	}

	public void rollbackDropoutStudentAction() {
		this.studentCurrentAction = StudentAction.ROLLBACK_DROPOUT_STUDENT;
	}

	public void dropoutStudentAction() {
		this.studentCurrentAction = StudentAction.DROPOUT_STUDENT;

		this.outstandingFeeDue = this.studentFinancialService.hasOutstandingFeeDue(this.student.getId(), this.getCurrentOrMostRecentAcademicYear()
				.getAcademicYear().getId());

	}

	public void terminateStudentAction() {
		this.studentCurrentAction = StudentAction.TERMINATE_STUDENT;
	}

	public void resetStudentAction() {
		this.studentCurrentAction = null;
	}

	public void processDropoutStudent() {
		try {
			this.student = this.studentService.processDropoutStudent(this.student.getId(), this.actionComment);

			ViewUtil.addMessage("Student satus successfully changed to 'Accept for dropout'. "
					+ "At this stage it is requried to settle all the financials before student status status change to complete 'Dropout'",
					FacesMessage.SEVERITY_INFO);
			this.resetStudentAction();
		} catch (final ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (final Exception e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void dropoutStudent() {
		try {
			this.student = this.studentService.dropoutStudent(this.student.getId(), this.actionComment);

			ViewUtil.addMessage("Student satus successfully changed to 'Dropout'. ", FacesMessage.SEVERITY_INFO);
			this.resetStudentAction();
		} catch (final ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (final Exception e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void rollBackDropoutStudent() {
		try {
			this.student = this.studentService.rollBackDropoutStudent(this.student.getId(), this.actionComment);

			ViewUtil.addMessage("Student satus rollbacked successfully to active state. ", FacesMessage.SEVERITY_INFO);
			this.resetStudentAction();
		} catch (final ApplicationException e) {
			ViewExceptionHandler.handle(e);
		} catch (final Exception e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public boolean isOutstandingFeeDue() {
		return this.outstandingFeeDue;
	}

	public void setOutstandingFeeDue(final boolean outstandingFeeDue) {
		this.outstandingFeeDue = outstandingFeeDue;
	}

	public String getActionComment() {
		return this.actionComment;
	}

	public void setActionComment(final String actionComment) {
		this.actionComment = actionComment;
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

	public void loadStudentAcademicYearHostelRooms() {

		if (this.isLoadStudentAcademicYearHostelRooms()) {
			this.studentAcademicYearHostelRooms = this.studentAcademicYearHostelRoomService.findAllHostelRoomsByStudentAcademicYear(this
					.getCurrentOrMostRecentAcademicYear().getId());
			this.setLoadStudentAcademicYearHostelRooms(false);
		}

	}

	public void viewStudent() {
		this.studentTabModel.setActiveTab(this.studentTabModel.getPersonalTab());
		this.setViewAction(true);
		this.loadRelationsFlag = true;
		this.setLoadProfilePictureFlag(true);
		this.setLoadAcademicYearsFlag(true);
		this.setLoadSubmittedDocumentsMedicalFlag(true);
		this.setLoadStudentLatestAcademicYearDetailsInd(true);
		this.loadLatestAcademicYearDetailsForStudent();
		this.loadStudentLatestAcademicYearDetails();
		this.setLoadStudentAcademicYearHostelRooms(true);
		this.loadStudentLatestAcademicYearDetails();
		this.loadStudentAcademicYearHostelRooms();
	}

	/**
	 * To use this method set parameter loadLatestAcademicYearDetailsFlag to
	 * true.
	 */
	public void viewStudentFromOtherViews() {
		this.viewContentHandlerBean.setCurrentViewContent(ViewContentConstant.BRANCH_STUDENTS);
		this.viewStudent();
	}
}
