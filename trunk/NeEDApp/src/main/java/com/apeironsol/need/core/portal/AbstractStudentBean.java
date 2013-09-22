package com.apeironsol.need.core.portal;

import java.util.Collection;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;

import com.apeironsol.need.core.dao.StudentSectionDao;
import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.model.MedicalHistory;
import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.model.StudentStatusHistory;
import com.apeironsol.need.core.service.AdmissionService;
import com.apeironsol.need.core.service.MedicalHistoryService;
import com.apeironsol.need.core.service.RelationService;
import com.apeironsol.need.core.service.StudentAcademicYearService;
import com.apeironsol.need.core.service.StudentSectionService;
import com.apeironsol.need.core.service.StudentService;
import com.apeironsol.need.core.service.StudentStatusHistoryService;
import com.apeironsol.need.util.constants.RelationTypeConstant;
import com.apeironsol.need.util.constants.StudentSectionStatusConstant;
import com.apeironsol.need.util.constants.StudentStatusConstant;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.BusinessException;

public abstract class AbstractStudentBean extends AbstractTabbedBean {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long					serialVersionUID					= 248344160755249983L;

	@Resource
	protected CountryBean						countryBean;

	@Resource
	protected StudentService					studentService;

	@Resource
	protected AdmissionService					admissionService;

	@Resource
	protected RelationService					relationService;

	@Resource
	protected StudentStatusHistoryService		studentStatusHistoryService;

	protected boolean							loadRelationsFlag					= true;

	protected boolean							loadSubmittedDocumentsMedicalFlag	= true;

	protected Relation							relation;

	protected Collection<Relation>				relations;

	protected Address							relationAddress;

	protected boolean							loadBranchStudentsFlag;

	protected Address							primaryAddress;

	private Collection<Section>					sectionsForKlass;

	private boolean								loadSectionForKlassFlag;

	private Long								admitForSectionId;

	private boolean								loadStudentStatusHistoryFlag;

	private Collection<StudentStatusHistory>	studentStatusHistories;

	private StudentStatusHistory				studentStatusHistory;

	private Relation							studentFather;

	private Relation							studentMother;

	private Relation							studentGuardian;

	private Collection<StudentSection>			activeStudentSectionsForAcadmicYear;

	private StudentSection						studentSection;

	protected Student							student;

	private MedicalHistory						medicalHistory;

	@Resource
	private MedicalHistoryService				medicalHistoryService;

	private boolean								loadExamsFlag;

	public Long getAdmitForSectionId() {
		return this.admitForSectionId;
	}

	public void setAdmitForSectionId(final Long admitForSectionId) {
		this.admitForSectionId = admitForSectionId;
	}

	/*
	 *
	 */
	private boolean	loadActiveClassesForCurrentBranchIndicator	= true;

	protected boolean	editStudentAddressDetails;

	@Resource
	protected StudentSectionService	studentSectionService;

	@Resource
	protected StudentAcademicYearService	studentAcademicYearService;

	protected boolean	loadLatestAcademicYearDetailsFlag;

	@Resource
	private StudentSectionDao	studentSectionDao;

	protected StudentSection	currentOrMostRecentStudentSection;

	private StudentAcademicYear	currentOrMostRecentAcademicYear;

	protected boolean	loadStudentLatestAcademicYearDetailsInd;

	public MedicalHistory getMedicalHistory() {
		return this.medicalHistory;
	}

	public void setMedicalHistory(final MedicalHistory medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public boolean isLoadSubmittedDocumentsMedicalFlag() {
		return this.loadSubmittedDocumentsMedicalFlag;
	}

	public void setLoadSubmittedDocumentsMedicalFlag(final boolean loadSubmittedDocumentsMedicalFlag) {
		this.loadSubmittedDocumentsMedicalFlag = loadSubmittedDocumentsMedicalFlag;
	}

	/**
	 * @return the studentFather
	 */
	public Relation getStudentFather() {
		if (this.studentFather == null) {
			this.studentFather = new Relation();
			this.studentFather.setRelationType(RelationTypeConstant.FATHER);
			this.studentFather.setAddress(new Address());
		}
		return this.studentFather;
	}

	/**
	 * @param studentFather
	 *            the studentFather to set
	 */
	public void setStudentFather(final Relation studentFather) {
		this.studentFather = studentFather;
	}

	/**
	 * @return the studentMother
	 */
	public Relation getStudentMother() {
		if (this.studentMother == null) {
			this.studentMother = new Relation();
			this.studentMother.setRelationType(RelationTypeConstant.MOTHER);
			this.studentFather.setAddress(new Address());
		}
		return this.studentMother;
	}

	/**
	 * @param studentMother
	 *            the studentMother to set
	 */
	public void setStudentMother(final Relation studentMother) {
		this.studentMother = studentMother;
	}

	/**
	 * @return the studentGuardian
	 */
	public Relation getStudentGuardian() {
		if (this.studentGuardian == null) {
			this.studentGuardian = new Relation();
			this.studentGuardian.setRelationType(RelationTypeConstant.GUARDIAN);
			this.studentFather.setAddress(new Address());
		}
		return this.studentGuardian;
	}

	/**
	 * @param studentGuardian
	 *            the studentGuardian to set
	 */
	public void setStudentGuardian(final Relation studentGuardian) {
		this.studentGuardian = studentGuardian;
	}

	public boolean isLoadRelationsFlag() {
		return this.loadRelationsFlag;
	}

	public void setLoadRelationsFlag(final boolean loadRelations) {
		this.loadRelationsFlag = loadRelations;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(final Student student) {
		this.student = student;
	}

	public void loadBranchActiveStudents() {
		if (this.loadBranchStudentsFlag) {
			if (this.sessionBean.getCurrentAcademicYear() != null) {
				this.setActiveStudentSectionsForAcadmicYear(this.studentService.findActiveStudentSectionsForAcademicYearId(this.sessionBean
						.getCurrentAcademicYear().getId()));
				this.loadBranchStudentsFlag = false;
			}
		}
	}

	public void loadStudentStatusHistories() {

		if (this.isLoadStudentStatusHistoryFlag()) {
			this.studentStatusHistories = this.studentStatusHistoryService.findStudentStatusHistoryByStudentId(this.student.getId());
			this.loadStudentStatusHistoryFlag = false;
		}

	}

	public Relation getRelation() {
		return this.relation;
	}

	public void setRelation(final Relation relation) {
		this.relation = relation;
	}

	public Address getRelationAddress() {
		return this.relationAddress;
	}

	public void setRelationAddress(final Address relationAddress) {
		this.relationAddress = relationAddress;
	}

	public Collection<Relation> getRelations() {
		return this.relations;
	}

	public void setRelations(final Collection<Relation> relations) {
		this.relations = relations;
	}

	public void loadRelations() {
		if (this.loadRelationsFlag) {
			this.relations = this.relationService.findRelationsDetailedByStudentId(this.student.getId());
			if (this.relations != null && !this.relations.isEmpty()) {
				for (final Relation relation : this.relations) {
					if (relation.getRelationType().equals(RelationTypeConstant.FATHER)) {
						this.studentFather = relation;
						this.studentFather.setAddress(relation.getAddress());
					} else if (relation.getRelationType().equals(RelationTypeConstant.MOTHER)) {
						this.studentMother = relation;
						this.studentMother.setAddress(relation.getAddress());
					} else if (relation.getRelationType().equals(RelationTypeConstant.GUARDIAN)) {
						this.studentGuardian = relation;
						this.studentGuardian.setAddress(relation.getAddress());
					}
				}
			}
			this.loadRelationsFlag = false;
		}
	}

	public void loadSubmittedDocumentsMedicalDetails() {
		if (this.loadSubmittedDocumentsMedicalFlag) {
			this.medicalHistory = this.medicalHistoryService.findMedicalHistoryByStudentId(this.student.getId());
			this.loadSubmittedDocumentsMedicalFlag = false;
		}
	}

	public boolean isLoadBranchStudentsFlag() {
		return this.loadBranchStudentsFlag;
	}

	public void setLoadBranchStudentsFlag(final boolean loadBranchStudentsFlag) {
		this.loadBranchStudentsFlag = loadBranchStudentsFlag;
	}

	public void saveRelation() {
		this.relation = this.relationService.saveRelation(this.relation, this.student);
		this.loadRelationsFlag = true;
		this.loadRelations();
		this.clearRelation();
	}

	public void clearRelation() {
		this.relation = new Relation();
		this.relationAddress = new Address();
	}

	public String viewRelation() {
		this.relationAddress = this.relationService.findRelationAddressByRelationId(this.relation.getId());
		if (this.relationAddress == null) {
			this.relationAddress = new Address();
		}
		return null;
	}

	public String removeRelation() {
		this.relationService.removeRelation(this.relation, this.student);
		this.relation = new Relation();
		this.loadRelationsFlag = true;
		this.loadRelations();
		return null;
	}

	public Address getPrimaryAddress() {
		return this.primaryAddress;
	}

	public void setPrimaryAddress(final Address primaryAddress) {
		this.primaryAddress = primaryAddress;
	}

	public void savePrimaryAddress() {

		try {
			this.primaryAddress = this.addressService.saveAddress(this.primaryAddress);

			this.student = this.studentService.findStudentById(this.student.getId());

			ViewUtil.addMessage("Primary address details saved sucessfully.", FacesMessage.SEVERITY_INFO);

			this.editStudentAddressDetails = false;


		} catch (final BusinessException e) {
			ViewExceptionHandler.handle(e);
		} catch (final Throwable e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void generateAdmissionReport() {
		// final AdmissionReportParameterBean jReportParameters = new
		// AdmissionReportParameterBean();
		// jReportParameters.setAdmissionNumber("001");
		// jReportParameters.setRegistrationDate(new Date());
		// jReportParameters.setReportOutPutFileName("001");
		// JReportGenerator.generateReportUsingParameters(jReportParameters,
		// JReportType.PDF_REPORT);
	}

	public Collection<Section> getSectionsForKlass() {
		return this.sectionsForKlass;
	}

	public void setSectionsForKlass(final Collection<Section> sectionsForKlass) {
		this.sectionsForKlass = sectionsForKlass;
	}

	public boolean isLoadSectionForKlassFlag() {
		return this.loadSectionForKlassFlag;
	}

	public void setLoadSectionForKlassFlag(final boolean loadSectionForKlassFlag) {
		this.loadSectionForKlassFlag = loadSectionForKlassFlag;
	}

	public void loadSectionsForClass() {
		if (this.loadSectionForKlassFlag && this.student.getAcceptedForKlass() != null) {

			this.sectionsForKlass = this.sectionService.findActiveSectionsByKlassIdAndAcademicYearId(this.student.getAcceptedForKlass().getId(), this.student
					.getAppliedForAcademicYear().getId());
			this.loadSectionForKlassFlag = false;
		}
	}

	public boolean isLoadStudentStatusHistoryFlag() {
		return this.loadStudentStatusHistoryFlag;
	}

	public void setLoadStudentStatusHistoryFlag(final boolean loadStudentStatusHistoryFlag) {
		this.loadStudentStatusHistoryFlag = loadStudentStatusHistoryFlag;
	}

	public Collection<StudentStatusHistory> getStudentStatusHistories() {
		return this.studentStatusHistories;
	}

	public void setStudentStatusHistories(final Collection<StudentStatusHistory> studentStatusHistories) {
		this.studentStatusHistories = studentStatusHistories;
	}

	public StudentStatusHistory getStudentStatusHistory() {
		return this.studentStatusHistory;
	}

	public void setStudentStatusHistory(final StudentStatusHistory studentStatusHistory) {
		this.studentStatusHistory = studentStatusHistory;
	}

	public boolean isStudentStatusActive() {
		return StudentStatusConstant.ACTIVE.equals(this.student.getStudentStatus());
	}

	/**
	 * @return the activeStudentSectionsForAcadmicYear
	 */
	public Collection<StudentSection> getActiveStudentSectionsForAcadmicYear() {
		return this.activeStudentSectionsForAcadmicYear;
	}

	/**
	 * @param activeStudentSectionsForAcadmicYear
	 *            the activeStudentSectionsForAcadmicYear to set
	 */
	public void setActiveStudentSectionsForAcadmicYear(final Collection<StudentSection> activeStudentSectionsForAcadmicYear) {
		this.activeStudentSectionsForAcadmicYear = activeStudentSectionsForAcadmicYear;
	}

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

	public void saveMedicalHistory() {
		try {
			this.medicalHistory.setStudent(this.student);
			this.medicalHistoryService.saveMedicalHistory(this.medicalHistory);
			ViewUtil.addMessage("Medical History saved sucessfully.", FacesMessage.SEVERITY_INFO);
		} catch (final BusinessException e) {
			ViewExceptionHandler.handle(e);
		} catch (final Throwable e) {
			ViewExceptionHandler.handle(e);
		}
	}

	public void loadActiveClassesForCurrentBranch() {
		if (this.loadActiveClassesForCurrentBranchIndicator) {
			this.sessionBean.setLoadActiveKlassesFlag(true);
			this.sessionBean.loadActiveKlasses();
			this.loadActiveClassesForCurrentBranchIndicator = false;
			this.sessionBean.setLoadActiveKlassesFlag(false);
		}
	}

	/**
	 * @return the loadActiveClassesForCurrentBranchIndicator
	 */
	public boolean isLoadActiveClassesForCurrentBranchIndicator() {
		return this.loadActiveClassesForCurrentBranchIndicator;
	}

	/**
	 * @param loadActiveClassesForCurrentBranchIndicator
	 *            the loadActiveClassesForCurrentBranchIndicator to set
	 */
	public void setLoadActiveClassesForCurrentBranchIndicator(final boolean loadActiveClassesForCurrentBranchIndicator) {
		this.loadActiveClassesForCurrentBranchIndicator = loadActiveClassesForCurrentBranchIndicator;
	}

	/**
	 * @return the loadExamsFlag
	 */
	public boolean isLoadExamsFlag() {
		return this.loadExamsFlag;
	}

	/**
	 * @param loadExamsFlag
	 *            the loadExamsFlag to set
	 */
	public void setLoadExamsFlag(final boolean loadExamsFlag) {
		this.loadExamsFlag = loadExamsFlag;
	}

	public String getStudentParentOrGuardianName() {
		String result = "";
		if (this.studentFather != null) {
			result = this.studentFather.getDisplayName();
		} else if (this.studentMother != null) {
			result = this.studentMother.getDisplayName();
		} else if (this.studentGuardian != null) {
			result = this.studentGuardian.getDisplayName();
		}
		return result;
	}

	/**
	 * @return the editStudentAddressDetails
	 */
	public boolean isEditStudentAddressDetails() {
		return this.editStudentAddressDetails;
	}

	/**
	 * @param editStudentAddressDetails
	 *            the editStudentAddressDetails to set
	 */
	public void setEditStudentAddressDetails(final boolean editStudentAddressDetails) {
		this.editStudentAddressDetails = editStudentAddressDetails;
	}

	public void viewStudent() {
		this.loadStudentLatestAcademicYearDetails();
	}

	public void loadLatestAcademicYearDetailsForStudent() {
		if (this.loadLatestAcademicYearDetailsFlag) {
			if (this.student.getId() != null) {
				final StudentAcademicYear studentCurrentOrMostRecentAcademicYear = this.studentAcademicYearService
						.findStudentCurrentOrMostRecentAcademicYearByStudentId(this.student.getId());
				this.academicYearId = studentCurrentOrMostRecentAcademicYear.getId();
				this.setStudentSection(this.studentSectionDao.findStudentSectionByStudentAcademicYearIdAndStatus(this.academicYearId,
						StudentSectionStatusConstant.ACTIVE));
			}
			this.loadLatestAcademicYearDetailsFlag = false;
		}
	}

	public void loadStudentLatestAcademicYearDetails() {
		if (this.loadStudentLatestAcademicYearDetailsInd) {
			this.setCurrentOrMostRecentAcademicYear(this.studentAcademicYearService.findStudentCurrentOrMostRecentAcademicYearByStudentId(this.getStudent()
					.getId()));
			this.setCurrentOrMostRecentStudentSection(this.studentSectionService.findLatestStudentSectionByStudentAcademicYearId(this
					.getCurrentOrMostRecentAcademicYear().getId()));
			this.loadStudentLatestAcademicYearDetailsInd = false;
		}
	}

	/**
	 * @param currentOrMostRecentStudentSection
	 *            the currentOrMostRecentStudentSection to set
	 */
	public void setCurrentOrMostRecentStudentSection(final StudentSection currentOrMostRecentStudentSection) {
		this.currentOrMostRecentStudentSection = currentOrMostRecentStudentSection;
	}

	/**
	 * @return the currentOrMostRecentAcademicYear
	 */
	public StudentAcademicYear getCurrentOrMostRecentAcademicYear() {
		return this.currentOrMostRecentAcademicYear;
	}

	/**
	 * @param currentOrMostRecentAcademicYear
	 *            the currentOrMostRecentAcademicYear to set
	 */
	public void setCurrentOrMostRecentAcademicYear(final StudentAcademicYear currentOrMostRecentAcademicYear) {
		this.currentOrMostRecentAcademicYear = currentOrMostRecentAcademicYear;
	}

}
