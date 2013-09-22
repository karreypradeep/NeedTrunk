/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */

package com.apeironsol.need.core.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apeironsol.need.core.dao.AddressDao;
import com.apeironsol.need.core.dao.AdmissionSubmittedDocumentsDao;
import com.apeironsol.need.core.dao.BranchDao;
import com.apeironsol.need.core.dao.EducationHistoryDao;
import com.apeironsol.need.core.dao.MedicalHistoryDao;
import com.apeironsol.need.core.dao.StudentAcademicYearDao;
import com.apeironsol.need.core.dao.StudentDao;
import com.apeironsol.need.core.dao.StudentSectionDao;
import com.apeironsol.need.core.dao.StudentStatusHistoryDao;
import com.apeironsol.need.core.model.AcademicYear;
import com.apeironsol.need.core.model.Address;
import com.apeironsol.need.core.model.AdmissionReservationFee;
import com.apeironsol.need.core.model.AdmissionSubmittedDocuments;
import com.apeironsol.need.core.model.Batch;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.core.model.BuildingBlock;
import com.apeironsol.need.core.model.EducationHistory;
import com.apeironsol.need.core.model.Klass;
import com.apeironsol.need.core.model.MedicalHistory;
import com.apeironsol.need.core.model.Relation;
import com.apeironsol.need.core.model.Section;
import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.core.model.StudentAcademicYear;
import com.apeironsol.need.core.model.StudentSection;
import com.apeironsol.need.core.model.StudentStatusHistory;
import com.apeironsol.need.financial.dao.StudentFeeDao;
import com.apeironsol.need.financial.dao.StudentFeeTransactionDao;
import com.apeironsol.need.financial.dao.StudentFeeTransactionDetailsDao;
import com.apeironsol.need.financial.model.BranchLevelFee;
import com.apeironsol.need.financial.model.BranchLevelFeeCatalog;
import com.apeironsol.need.financial.model.KlassLevelFee;
import com.apeironsol.need.financial.model.KlassLevelFeeCatalog;
import com.apeironsol.need.financial.model.StudentFee;
import com.apeironsol.need.financial.model.StudentFeeTransaction;
import com.apeironsol.need.financial.model.StudentFeeTransactionDetails;
import com.apeironsol.need.financial.model.StudentLevelFeeCatalog;
import com.apeironsol.need.financial.service.BranchLevelFeeService;
import com.apeironsol.need.financial.service.KlassLevelFeeService;
import com.apeironsol.need.financial.service.StudentFinancialService;
import com.apeironsol.need.financial.service.StudentLevelFeeService;
import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.constants.AdmissionStatusConstant;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.need.util.constants.PaymentMethodConstant;
import com.apeironsol.need.util.constants.ResidenceConstant;
import com.apeironsol.need.util.constants.StudentFeeTransactionStatusConstant;
import com.apeironsol.need.util.constants.StudentFeeTransactionTypeConstant;
import com.apeironsol.need.util.constants.StudentSectionStatusConstant;
import com.apeironsol.need.util.constants.StudentStatusConstant;
import com.apeironsol.need.util.dataobject.AdmissionFeeDO;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.need.util.searchcriteria.AdmissionSearchCriteria;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.SystemException;

/**
 * Service interface implementation for admissions.
 * 
 * @author Pradeep
 * 
 */
@Service("admissionService")
@Transactional
public class AdmissionServiceImpl implements AdmissionService {

	@Resource
	StudentDao								studentDao;

	@Resource
	BranchDao								branchDao;

	@Resource
	SequenceGeneratorService				sequenceGeneratorService;

	@Resource
	EducationHistoryDao						educationHistoryDao;

	@Resource
	RelationService							relationService;

	@Resource
	AddressDao								addressDao;

	@Resource
	StudentStatusHistoryDao					studentStatusHistoryDao;

	@Resource
	StudentSectionDao						studentSectionDao;

	@Resource
	StudentFeeDao							studentFeeDao;

	@Resource
	StudentAcademicYearDao					studentAcademicYearDao;

	@Resource
	KlassLevelFeeService					klassLevelFeeService;

	@Resource
	BranchLevelFeeService					branchLevelFeeService;

	@Resource
	StudentLevelFeeService					studentLevelFeeService;

	@Resource
	MedicalHistoryService					medicalHistoryService;

	@Resource
	MedicalHistoryDao						medicalHistoryDao;

	@Resource
	AdmissionSubmittedDocumentsDao			admissionSubmittedDocumentsDao;

	@Resource
	StudentFeeTransactionDao				studentFeeTransactionDao;

	@Resource
	StudentFeeTransactionDetailsDao			studentFeeTransactionDetailsDao;

	@Resource
	AdmissionReservationFeeService			admissionReservationFeeService;

	@Resource
	StudentFinancialService					studentFinancialService;

	@Resource
	StudentAcademicYearFeeSummaryService	studentAcademicYearFeeSummaryService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Student submitAdmission(final Student student, final Collection<Relation> relations, final Address primaryAddress,
			final Collection<EducationHistory> educationHistories, final Long branchId, final AdmissionReservationFee admissionReservationFee,
			final boolean applicationFeeExternalTransaction, final StudentStatusHistory studentStatusHistory) throws BusinessException, SystemException {
		try {
			final Branch branch = this.branchDao.findById(branchId);

			if (branch == null) {
				throw new BusinessException("Unable to find the branch with id : " + branchId);
			}
			if (student.getExternalAdmissionNr() != null) {
				final Student studentWithSameExAdNr = this.studentDao.findActiveStudentByExternalAdmissionNumberAndBranchId(student.getExternalAdmissionNr(),
						branchId);
				if (studentWithSameExAdNr != null) {
					throw new BusinessException("Student with same external admission number " + student.getExternalAdmissionNr()
							+ " already exists for branch : " + branch.getName());
				}
			}

			student.setBranch(branch);
			student.setAdmissionStatus(AdmissionStatusConstant.SUBMITTED);
			student.setStudentStatus(StudentStatusConstant.IN_ADMISSION);
			final String registrationNr = this.sequenceGeneratorService.getNextRegistrationNumber(branch.getCode());
			student.setRegistrationNr(registrationNr);
			student.setSubmittedDate(DateUtil.getSystemDate());
			student.setRelations(relations);
			student.setAddress(primaryAddress);

			// student.setStudentStatus(StudentStatusConstant.IN_ADMISSION);
			final Student result = this.studentDao.persist(student);

			// student application fee
			if (admissionReservationFee != null && admissionReservationFee.getApplicationFormFee() != null
					&& admissionReservationFee.getApplicationFormFee() > 0) {
				admissionReservationFee.setStudent(result);
				admissionReservationFee.setApplicationFeeAppliedToStudentFees(false);
				admissionReservationFee.setApplicationFeePaidDate(DateUtil.getSystemDate());
				this.admissionReservationFeeService.saveAdmissionReservationFee(admissionReservationFee);
			}

			// Education history
			if (educationHistories != null && !educationHistories.isEmpty()) {
				for (final EducationHistory educationHistory : educationHistories) {
					educationHistory.setStudent(result);
					this.educationHistoryDao.persist(educationHistory);
				}
			}

			// Log student state history
			this.saveStudentStatusHistory(studentStatusHistory, result, AdmissionStatusConstant.SUBMITTED.getLabel());
			return result;
		} catch (final Throwable e) {
			throw new SystemException(e);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Student anotherReviewAdmission(final Student student, final StudentStatusHistory studentStatusHistory) throws BusinessException, SystemException {
		try {
			final Student currentStudent = this.studentDao.findById(student.getId());

			if (AdmissionStatusConstant.SUBMITTED.equals(currentStudent.getAdmissionStatus())
					|| AdmissionStatusConstant.INREVIEW.equals(currentStudent.getAdmissionStatus())) {

				// Student
				currentStudent.setAdmissionStatus(AdmissionStatusConstant.INREVIEW);
				currentStudent.setStudentStatus(StudentStatusConstant.IN_ADMISSION);
				final Student result = this.studentDao.persist(currentStudent);

				// Log student state history
				this.saveStudentStatusHistory(studentStatusHistory, result, AdmissionStatusConstant.INREVIEW.getLabel());
				return result;
			} else {
				throw new BusinessException("Admission status can be changed to reviewed only if the current status of the admission is submitted or inreview.");
			}
		} catch (final Throwable e) {
			throw new SystemException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Student reviewAdmission(final Student student, final StudentStatusHistory studentStatusHistory) throws BusinessException, SystemException {
		try {

			final Student currentStudent = this.studentDao.findById(student.getId());

			if (AdmissionStatusConstant.SUBMITTED.equals(currentStudent.getAdmissionStatus())
					|| AdmissionStatusConstant.INREVIEW.equals(currentStudent.getAdmissionStatus())) {

				// Student
				currentStudent.setAdmissionStatus(AdmissionStatusConstant.REVIEWED);
				currentStudent.setStudentStatus(StudentStatusConstant.IN_ADMISSION);
				final Student result = this.studentDao.persist(currentStudent);

				// Log student state history
				this.saveStudentStatusHistory(studentStatusHistory, result, AdmissionStatusConstant.REVIEWED.getLabel());
				return result;
			} else {
				throw new BusinessException("Admission status can be changed to reviewed only if the current status of the admission is submitted or inreview.");
			}
		} catch (final Throwable e) {
			throw new SystemException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Student acceptAdmission(final Student student, final Klass acceptedForKlass, final AdmissionReservationFee admissionReservationFee,
			final StudentStatusHistory studentStatusHistory) throws BusinessException, SystemException {
		try {

			final Student currentStudent = this.studentDao.findById(student.getId());

			if (AdmissionStatusConstant.REVIEWED.equals(currentStudent.getAdmissionStatus())) {

				final Batch appliedForBatch = currentStudent.getAppliedForBatch();

				final AcademicYear appliedForAcademicYear = currentStudent.getAppliedForAcademicYear();

				// Create or find student academic year.
				StudentAcademicYear studentAcademicYear = this.studentAcademicYearDao.findStudentAcademicYearByStudentIdAndAcademicYearId(
						currentStudent.getId(), appliedForAcademicYear.getId());

				if (studentAcademicYear == null) {
					studentAcademicYear = new StudentAcademicYear();
					studentAcademicYear.setStudent(currentStudent);
					studentAcademicYear.setAcademicYear(appliedForAcademicYear);
					studentAcademicYear.setBatch(appliedForBatch);
					studentAcademicYear.setSequenceNr(Integer.valueOf(1));
					studentAcademicYear = this.studentAcademicYearDao.persist(studentAcademicYear);
				}

				currentStudent.setAdmissionStatus(AdmissionStatusConstant.ACCEPTED);
				currentStudent.setStudentStatus(StudentStatusConstant.ACCEPTED);
				currentStudent.setAcceptedForKlass(acceptedForKlass);
				final Student result = this.studentDao.persist(currentStudent);

				if (admissionReservationFee.getReservationFee() != null && admissionReservationFee.getReservationFee() > 0) {
					admissionReservationFee.setReservationFeeAppliedToStudentFees(false);
					admissionReservationFee.setReservationFeePaidDate(DateUtil.getSystemDate());
					admissionReservationFee.setStudent(result);
					this.admissionReservationFeeService.saveAdmissionReservationFee(admissionReservationFee);
				}

				// Log student state history
				this.saveStudentStatusHistory(studentStatusHistory, result, AdmissionStatusConstant.ACCEPTED.getLabel());

				return result;

			} else {
				throw new BusinessException("Admission status can be changed to accept only if the current status of the admission is reviewed.");
			}
		} catch (final Throwable e) {
			throw new SystemException(e);
		}
	}

	@Override
	public Student admitStudent(final Student student, final Section admitForSection, final MedicalHistory medicalHistory,
			final Collection<BuildingBlock> admissionSubmittedDocuments, final Collection<AdmissionFeeDO> admissionFeeDOs, final boolean deductReservationFee,
			final boolean skipApplicatinFee, final boolean skipReservationFee) throws BusinessException, SystemException {

		final Student currentStudent = this.studentDao.findById(student.getId());

		// Get new admission number.
		if (currentStudent.getAdmissionNr() == null) {
			final String admissionNr = this.sequenceGeneratorService.getNextAdmissionNumber(currentStudent.getBranch().getCode());
			student.setAdmissionNr(admissionNr);
		}

		student.setAdmissionStatus(AdmissionStatusConstant.ADMITTED);

		student.setStudentStatus(StudentStatusConstant.ACTIVE);

		final Student result = this.studentDao.persist(student);

		final AcademicYear appliedForAcademicYear = result.getAppliedForAcademicYear();

		// find student academic year.
		final StudentAcademicYear studentAcademicYear = this.studentAcademicYearDao.findStudentAcademicYearByStudentIdAndAcademicYearId(result.getId(),
				appliedForAcademicYear.getId());

		if (studentAcademicYear == null) {
			throw new BusinessException("Expected, student academic year , but not records found.");
		}

		// Assign student to class and section for the student.

		StudentSection studentSection = null;

		studentSection = this.studentSectionDao.findStudentSectionByStudentAcademicYearIdAndSectionId(studentAcademicYear.getId(), admitForSection.getId());

		if (studentSection == null) {
			studentSection = new StudentSection();
		}

		studentSection.setSection(admitForSection);

		studentSection.setSequenceNr(Integer.valueOf(1));

		studentSection.setStudentAcademicYear(studentAcademicYear);

		studentSection.setStudentSectionStatus(StudentSectionStatusConstant.ACTIVE);

		studentSection.setActionDate(DateUtil.getSystemDate());

		studentSection = this.studentSectionDao.persist(studentSection);

		final AcademicYear sectionForAcademicYear = studentAcademicYear.getAcademicYear();

		// Add branch level fee.
		final Collection<BranchLevelFee> branchLevelFees = this.branchLevelFeeService.findBranchLevelFeeByBranchIdAndAcademicYearId(studentAcademicYear
				.getStudent().getBranch().getId(), sectionForAcademicYear.getId());

		final Collection<StudentFee> studentFees = new ArrayList<StudentFee>();

		for (final BranchLevelFee branchLevelFee : branchLevelFees) {

			if (studentSection.getStudentAcademicYear().getStudent().getResidence().equals(ResidenceConstant.DAY_SCHOOLER)
					&& FeeTypeConstant.HOSTEL_FEE.equals(branchLevelFee.getBuildingBlock().getFeeType())) {
				// don't create hostel fee.
				continue;
			}

			if (FeeTypeConstant.APPLICATION_FEE.equals(branchLevelFee.getBuildingBlock().getFeeType()) && skipApplicatinFee) {
				// don't create application fee.
				continue;
			}

			if (FeeTypeConstant.RESERVATION_FEE.equals(branchLevelFee.getBuildingBlock().getFeeType()) && skipReservationFee) {
				// don't create application fee.
				continue;
			}

			StudentFee studentFee = new StudentFee();

			studentFee.setFeeClassificationLevel(FeeClassificationLevelConstant.BRANCH_LEVEL);

			studentFee.setBranchLevelFee(branchLevelFee);

			studentFee.setStudentAcademicYear(studentAcademicYear);

			studentFee = this.studentFeeDao.persist(studentFee);

			studentFees.add(studentFee);

		}

		// Generate student fee
		final Klass studentInKlass = studentSection.getSection().getKlass();

		final Collection<KlassLevelFee> klassLevelFees = this.klassLevelFeeService.findAllKlassFeesByKlassIdAndAcademicYearId(studentInKlass.getId(),
				sectionForAcademicYear.getId());

		// Create student hostel fee only if hostel is selected.
		// For now always not create fee for transportation.
		for (final KlassLevelFee klassLevelFee : klassLevelFees) {

			if (FeeTypeConstant.HOSTEL_FEE.equals(klassLevelFee.getBuildingBlock().getFeeType())
					&& studentSection.getStudentAcademicYear().getStudent().getResidence().equals(ResidenceConstant.DAY_SCHOOLER)) {
				// don't create hostel fee.
				continue;
			}

			StudentFee studentFee = new StudentFee();

			studentFee.setFeeClassificationLevel(FeeClassificationLevelConstant.KLASS_LEVEL);

			studentFee.setKlassFee(klassLevelFee);

			studentFee.setStudentAcademicYear(studentAcademicYear);

			studentFee = this.studentFeeDao.persist(studentFee);

			studentFees.add(studentFee);

		}

		medicalHistory.setStudent(student);
		this.medicalHistoryDao.persist(medicalHistory);

		for (final BuildingBlock buildingBlock : admissionSubmittedDocuments) {
			final AdmissionSubmittedDocuments admissionDocument = new AdmissionSubmittedDocuments();
			admissionDocument.setStudent(result);
			admissionDocument.setBuildingBlock(buildingBlock);
			this.admissionSubmittedDocumentsDao.persist(admissionDocument);
		}

		this.processPayments(studentFees, studentAcademicYear, admissionFeeDOs, deductReservationFee, skipApplicatinFee, skipReservationFee);

		// Log student state history
		final StudentStatusHistory studentStatusHistory = new StudentStatusHistory();
		studentStatusHistory.setComments("Student Admitted");
		this.saveStudentStatusHistory(studentStatusHistory, result, AdmissionStatusConstant.ADMITTED.getLabel());

		this.studentAcademicYearFeeSummaryService.createStudentAcademicYearFeeSummaryForStudentAcademicYearId(studentAcademicYear.getId());
		return result;
	}

	private void processPayments(final Collection<StudentFee> studentFees, final StudentAcademicYear studentAcademicYear,
			final Collection<AdmissionFeeDO> admissionFeeDOs, final boolean deductReservationFee, final boolean skipApplicatinFee,
			final boolean skipReservationFee) {

		final AdmissionReservationFee admissionReservationFee = this.admissionReservationFeeService.findAdmissionReservationFeeByStudentID(studentAcademicYear
				.getStudent().getId());
		if (admissionReservationFee != null) {
			if (admissionReservationFee.getApplicationFormFee() != null && admissionReservationFee.getApplicationFormFee() > 0d
					&& admissionReservationFee.getApplicationFeePaidDate() != null) {
				// Process Application fee
				this.processApplicationFee(studentFees, studentAcademicYear, skipApplicatinFee, admissionReservationFee);
			}
			if (admissionReservationFee.getReservationFee() != null && admissionReservationFee.getReservationFee() > 0d
					&& admissionReservationFee.getReservationFeePaidDate() != null) {
				// Process reservation fee
				this.processReservationFee(studentFees, studentAcademicYear, admissionFeeDOs, skipReservationFee, admissionReservationFee);
			}
		}

	}

	private void processApplicationFee(final Collection<StudentFee> studentFees, final StudentAcademicYear studentAcademicYear,
			final boolean skipApplicatinFee, final AdmissionReservationFee admissionReservationFee) {
		if (admissionReservationFee != null && admissionReservationFee.getApplicationFormFee() != null && skipApplicatinFee) {

			throw new BusinessException("Application fee payment conversion cannot be skipped, because application fee is paid");

		} else if (admissionReservationFee != null && admissionReservationFee.getApplicationFormFee() != null && !skipApplicatinFee) {

			final double amount = admissionReservationFee.getApplicationFormFee();

			final StudentFeeTransaction studentFeeTransaction = new StudentFeeTransaction();

			final String transactionNr = this.sequenceGeneratorService.getNextTransactionNumberForFeeDeposit();

			studentFeeTransaction.setAmount(admissionReservationFee.getApplicationFormFee());

			studentFeeTransaction.setStudentFeeTransactionType(StudentFeeTransactionTypeConstant.PAYMENT);

			studentFeeTransaction.setStudentFeeTransactionStatus(StudentFeeTransactionStatusConstant.PAYMENT_PROCESSED);

			studentFeeTransaction.setTransactionDate(DateUtil.getSystemDate());

			studentFeeTransaction.setPaymentMethod(PaymentMethodConstant.CASH);

			studentFeeTransaction.setStudentAcademicYear(studentAcademicYear);

			studentFeeTransaction.setTransactionNr(transactionNr);

			if (admissionReservationFee.getApplicationFeeExternalTransactionNr() != null
					&& admissionReservationFee.getApplicationFeeExternalTransactionDate() != null) {

				studentFeeTransaction.setExternalTransactionDate(admissionReservationFee.getApplicationFeeExternalTransactionDate());

				studentFeeTransaction.setExternalTransactionNr(admissionReservationFee.getApplicationFeeExternalTransactionNr());
			}

			studentFeeTransaction.setComments("Application fee transactions");

			final StudentFeeTransaction studentFeeTransactionLocal = this.studentFeeTransactionDao.persist(studentFeeTransaction);

			final double remainingAmount = amount;

			this.processFeePayment(studentFees, FeeTypeConstant.APPLICATION_FEE, studentFeeTransactionLocal, remainingAmount);

		}
	}

	private void processReservationFee(final Collection<StudentFee> studentFees, final StudentAcademicYear studentAcademicYear,
			final Collection<AdmissionFeeDO> admissionFeeDOs, final boolean skipReservationFee, final AdmissionReservationFee admissionReservationFee) {

		final double amount = admissionReservationFee.getReservationFee();

		final StudentFeeTransaction studentFeeTransaction = new StudentFeeTransaction();

		final String transactionNr = this.sequenceGeneratorService.getNextTransactionNumberForFeeDeposit();

		studentFeeTransaction.setTransactionNr(transactionNr);

		studentFeeTransaction.setAmount(amount);

		studentFeeTransaction.setStudentFeeTransactionType(StudentFeeTransactionTypeConstant.PAYMENT);

		studentFeeTransaction.setStudentFeeTransactionStatus(StudentFeeTransactionStatusConstant.PAYMENT_PROCESSED);

		studentFeeTransaction.setTransactionDate(admissionReservationFee.getReservationFeePaidDate());

		studentFeeTransaction.setPaymentMethod(PaymentMethodConstant.CASH);

		studentFeeTransaction.setStudentAcademicYear(studentAcademicYear);

		if (admissionReservationFee.getApplicationFeeExternalTransactionNr() != null
				&& admissionReservationFee.getApplicationFeeExternalTransactionDate() != null) {

			studentFeeTransaction.setExternalTransactionDate(admissionReservationFee.getReservationFeeExternalTransactionDate());

			studentFeeTransaction.setExternalTransactionNr(admissionReservationFee.getReservationFeeExternalTransactionNr());
		}

		studentFeeTransaction.setComments("Reservation fee transactions");

		final StudentFeeTransaction studentFeeTransactionLocal = this.studentFeeTransactionDao.persist(studentFeeTransaction);

		if (admissionReservationFee != null && admissionReservationFee.getReservationFee() != null && skipReservationFee) {

			for (final AdmissionFeeDO admissionFeeDO : admissionFeeDOs) {

				if (admissionFeeDO.getFeePaidDuringAdmission() != null && admissionFeeDO.getFeePaidDuringAdmission() > 0d) {

					for (final StudentFee studentFee : studentFees) {

						if (studentFee.getKlassFee() != null && studentFee.getKlassFee().equals(admissionFeeDO.getKlassLevelFee())) {

							double remainingAmount = admissionFeeDO.getFeePaidDuringAdmission();

							double payingAmount = 0d;

							final Collection<KlassLevelFeeCatalog> klassLevelFeeCatalogs = studentFee.getKlassFee().getKlassLevelFeeCatalogs();

							for (final KlassLevelFeeCatalog klassLevelFeeCatalog : klassLevelFeeCatalogs) {

								if (remainingAmount >= klassLevelFeeCatalog.getAmount() && remainingAmount > 0d) {

									payingAmount = klassLevelFeeCatalog.getAmount();

									remainingAmount = remainingAmount - klassLevelFeeCatalog.getAmount();
								} else {

									payingAmount = remainingAmount;
									remainingAmount = 0d;
								}

								final StudentFeeTransactionDetails studentFeeTransactionDetails = new StudentFeeTransactionDetails();

								studentFeeTransactionDetails.setAmount(payingAmount);

								studentFeeTransactionDetails.setKlassLevelFeeCatalog(klassLevelFeeCatalog);

								studentFeeTransactionDetails.setStudentFee(studentFee);

								studentFeeTransactionDetails.setStudentFeeTransaction(studentFeeTransactionLocal);

								this.studentFeeTransactionDetailsDao.persist(studentFeeTransactionDetails);

								if (remainingAmount == 0d) {
									break;
								}
							}

						} else if (studentFee.getBranchLevelFee() != null && studentFee.getBranchLevelFee().equals(admissionFeeDO.getBranchLevelFee())) {

							double remainingAmount = admissionFeeDO.getFeePaidDuringAdmission();

							double payingAmount = 0d;

							final Collection<BranchLevelFeeCatalog> branchLevelFeeCatalogs = studentFee.getBranchLevelFee().getBranchLevelFeeCatalogs();

							for (final BranchLevelFeeCatalog branchLevelFeeCatalog : branchLevelFeeCatalogs) {

								if (remainingAmount >= branchLevelFeeCatalog.getAmount() && remainingAmount > 0d) {

									payingAmount = branchLevelFeeCatalog.getAmount();

									remainingAmount = remainingAmount - branchLevelFeeCatalog.getAmount();
								} else {

									payingAmount = remainingAmount;
									remainingAmount = 0d;
								}

								final StudentFeeTransactionDetails studentFeeTransactionDetails = new StudentFeeTransactionDetails();

								studentFeeTransactionDetails.setAmount(payingAmount);

								studentFeeTransactionDetails.setBranchLevelFeeCatalog(branchLevelFeeCatalog);

								studentFeeTransactionDetails.setStudentFee(studentFee);

								studentFeeTransactionDetails.setStudentFeeTransaction(studentFeeTransactionLocal);

								this.studentFeeTransactionDetailsDao.persist(studentFeeTransactionDetails);

								if (remainingAmount == 0d) {
									break;
								}
							}

						}

					}

				}

			}

		} else if (admissionReservationFee != null && admissionReservationFee.getReservationFee() != null && !skipReservationFee) {

			final double remainingAmount = amount;

			this.processFeePayment(studentFees, FeeTypeConstant.RESERVATION_FEE, studentFeeTransactionLocal, remainingAmount);

		}
	}

	private void processFeePayment(final Collection<StudentFee> studentFees, final FeeTypeConstant feeType,
			final StudentFeeTransaction studentFeeTransactionLocal, double remainingAmount) {
		double payingAmount;

		for (final StudentFee studentFee : studentFees) {

			if (studentFee.getBranchLevelFee() != null && feeType.equals(studentFee.getBranchLevelFee().getBuildingBlock().getFeeType())) {

				final Collection<BranchLevelFeeCatalog> branchLevelFeeCatalogs = studentFee.getBranchLevelFee().getBranchLevelFeeCatalogs();

				for (final BranchLevelFeeCatalog branchLevelFeeCatalog : branchLevelFeeCatalogs) {

					if (remainingAmount >= branchLevelFeeCatalog.getAmount() && remainingAmount > 0) {

						payingAmount = branchLevelFeeCatalog.getAmount();

						remainingAmount = remainingAmount - branchLevelFeeCatalog.getAmount();

					} else {
						payingAmount = remainingAmount;
						remainingAmount = 0d;
					}

					final StudentFeeTransactionDetails studentFeeTransactionDetails = new StudentFeeTransactionDetails();

					studentFeeTransactionDetails.setAmount(payingAmount);

					studentFeeTransactionDetails.setBranchLevelFeeCatalog(branchLevelFeeCatalog);

					studentFeeTransactionDetails.setStudentFee(studentFee);

					studentFeeTransactionDetails.setStudentFeeTransaction(studentFeeTransactionLocal);

					this.studentFeeTransactionDetailsDao.persist(studentFeeTransactionDetails);

					if (remainingAmount == 0d) {
						break;
					}
				}

				break;

			} else if (studentFee.getStudentLevelFee() != null && feeType.equals(studentFee.getStudentLevelFee().getBuildingBlock().getFeeType())) {

				final Collection<StudentLevelFeeCatalog> studentLevelFeeCatalogs = studentFee.getStudentLevelFee().getStudentLevelFeeCatalogs();

				for (final StudentLevelFeeCatalog studentLevelFeeCatalog : studentLevelFeeCatalogs) {

					if (remainingAmount >= studentLevelFeeCatalog.getAmount() && remainingAmount > 0) {
						payingAmount = studentLevelFeeCatalog.getAmount();

						remainingAmount = remainingAmount - studentLevelFeeCatalog.getAmount();
					} else {
						payingAmount = remainingAmount;
						remainingAmount = 0d;
					}

					final StudentFeeTransactionDetails studentFeeTransactionDetails = new StudentFeeTransactionDetails();

					studentFeeTransactionDetails.setAmount(payingAmount);

					studentFeeTransactionDetails.setStudentLevelFeeCatalog(studentLevelFeeCatalog);

					studentFeeTransactionDetails.setStudentFee(studentFee);

					studentFeeTransactionDetails.setStudentFeeTransaction(studentFeeTransactionLocal);

					this.studentFeeTransactionDetailsDao.persist(studentFeeTransactionDetails);

					if (remainingAmount == 0d) {
						break;
					}
				}

				break;

			} else if (studentFee.getKlassFee() != null && feeType.equals(studentFee.getKlassFee().getBuildingBlock().getFeeType())) {

				final Collection<KlassLevelFeeCatalog> klassLevelFeeCatalogs = studentFee.getKlassFee().getKlassLevelFeeCatalogs();

				for (final KlassLevelFeeCatalog klassLevelFeeCatalog : klassLevelFeeCatalogs) {

					if (remainingAmount >= klassLevelFeeCatalog.getAmount() && remainingAmount > 0) {
						payingAmount = klassLevelFeeCatalog.getAmount();

						remainingAmount = remainingAmount - klassLevelFeeCatalog.getAmount();
					} else {
						payingAmount = remainingAmount;
						remainingAmount = 0d;
					}

					final StudentFeeTransactionDetails studentFeeTransactionDetails = new StudentFeeTransactionDetails();

					studentFeeTransactionDetails.setAmount(payingAmount);

					studentFeeTransactionDetails.setKlassLevelFeeCatalog(klassLevelFeeCatalog);

					studentFeeTransactionDetails.setStudentFee(studentFee);

					studentFeeTransactionDetails.setStudentFeeTransaction(studentFeeTransactionLocal);

					this.studentFeeTransactionDetailsDao.persist(studentFeeTransactionDetails);

					if (remainingAmount == 0d) {
						break;
					}
				}

				break;

			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @TODO Add a new admission status IN_REVIEW , and rollback feature.
	 */
	@Override
	public Student rollbackAdmissionCurrentState(final Student student, final StudentStatusHistory studentStatusHistory) throws BusinessException,
			SystemException {
		try {

			final Student currentStudent = this.studentDao.findById(student.getId());

			if (AdmissionStatusConstant.SUBMITTED.equals(currentStudent.getAdmissionStatus())) {
				throw new BusinessException("Admission state cannot be rollbacked as the state is submitted.");
			} else if (AdmissionStatusConstant.REJECTED.equals(currentStudent.getAdmissionStatus())) {

				studentStatusHistory.setAction("Rollback admission state from 'Rejected' to 'Submitted'");

				currentStudent.setAdmissionStatus(AdmissionStatusConstant.SUBMITTED);
				currentStudent.setStudentStatus(StudentStatusConstant.IN_ADMISSION);
			} else if (AdmissionStatusConstant.INREVIEW.equals(currentStudent.getAdmissionStatus())) {

				studentStatusHistory.setAction("Rollback admission state from 'InReview' to 'Submitted'");

				currentStudent.setAdmissionStatus(AdmissionStatusConstant.SUBMITTED);
				currentStudent.setStudentStatus(StudentStatusConstant.IN_ADMISSION);
			} else if (AdmissionStatusConstant.REVIEWED.equals(currentStudent.getAdmissionStatus())) {

				studentStatusHistory.setAction("Rollback admission state from 'Reviewed' to 'Submitted'");

				currentStudent.setAdmissionStatus(AdmissionStatusConstant.SUBMITTED);
				currentStudent.setStudentStatus(StudentStatusConstant.IN_ADMISSION);
			} else if (AdmissionStatusConstant.ACCEPTED.equals(currentStudent.getAdmissionStatus())) {
				throw new BusinessException("Admission state cannot be rollbacked as the state is accepted.");
			}

			// Persist student
			final Student result = this.studentDao.persist(currentStudent);

			// Log action
			studentStatusHistory.setActionTakenTime(new Timestamp(DateUtil.getSystemDate().getTime()));
			studentStatusHistory.setActionTakenBy(ViewUtil.getPrincipal());
			studentStatusHistory.setStudent(result);

			this.studentStatusHistoryDao.persist(studentStatusHistory);

			return result;
		} catch (final Throwable t) {
			throw new SystemException(t);
		}
	}

	/**
	 * {@inheritDoc} Admission can only be rejected if the admission state is
	 * SUBMITTED OT IN_REVIEW
	 */
	@Override
	public Student rejectAdmission(final Student student, final StudentStatusHistory studentStatusHistory) throws BusinessException, SystemException {
		try {

			final Student currentStudent = this.studentDao.findById(student.getId());

			if (AdmissionStatusConstant.SUBMITTED.equals(currentStudent.getAdmissionStatus())
					|| AdmissionStatusConstant.INREVIEW.equals(currentStudent.getAdmissionStatus())
					|| AdmissionStatusConstant.REVIEWED.equals(currentStudent.getAdmissionStatus())
					|| AdmissionStatusConstant.ACCEPTED.equals(currentStudent.getAdmissionStatus())) {

				// Class and section are assigned only when admission accepted.
				if (AdmissionStatusConstant.ACCEPTED.equals(currentStudent.getAdmissionStatus())) {

					final AcademicYear appliedForAcademicYear = currentStudent.getAppliedForAcademicYear();

					// Removing the record from student academic year.
					this.studentAcademicYearDao.removeStudentAcademicYearByStudendIdAndAcademicYearId(currentStudent.getId(), appliedForAcademicYear.getId());

					currentStudent.setAdmissionStatus(AdmissionStatusConstant.ARCHIVE);
					currentStudent.setStudentStatus(StudentStatusConstant.ARCHIVE);
				} else {
					currentStudent.setAdmissionStatus(AdmissionStatusConstant.REJECTED);
					currentStudent.setStudentStatus(StudentStatusConstant.REJECTED);
				}

				final Student result = this.studentDao.persist(currentStudent);

				// Log student state history
				this.saveStudentStatusHistory(studentStatusHistory, result, AdmissionStatusConstant.REJECTED.getLabel());

				return result;
			} else {
				throw new BusinessException("Admission cannot be rejected if the current status of the admission is admitted.");

			}
		} catch (final Throwable e) {
			throw new SystemException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveStudentStatusHistory(final StudentStatusHistory studentStatusHistory, final Student result, final String action) {

		studentStatusHistory.setActionTakenTime(new Timestamp(DateUtil.getSystemDate().getTime()));
		studentStatusHistory.setActionTakenBy(ViewUtil.getPrincipal());
		studentStatusHistory.setAction(action);
		studentStatusHistory.setStudent(result);
		this.studentStatusHistoryDao.persist(studentStatusHistory);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAdmission(final Student student) throws BusinessException {
		try {
			if (AdmissionStatusConstant.SUBMITTED.equals(student.getAdmissionStatus())) {

				final Collection<EducationHistory> educationHistories = this.educationHistoryDao.findEducationHistoriesByStudentId(student.getId());

				for (final EducationHistory educationHistory : educationHistories) {

					this.educationHistoryDao.remove(educationHistory);
				}

				this.relationService.removeAllRelations(student);

				final Collection<StudentStatusHistory> studentStatusHistories = this.studentStatusHistoryDao.findStudentStatusHistoryByStudentId(student
						.getId());

				for (final StudentStatusHistory studentStatusHistory : studentStatusHistories) {

					this.studentStatusHistoryDao.remove(studentStatusHistory);
				}

				this.studentDao.remove(student);

			} else {
				throw new BusinessException("Admission cannot be removed if the status is " + student.getAdmissionStatus().getLabel());
			}

		} catch (final Throwable e) {
			throw new SystemException(e);
		}

	}

	@Override
	public Collection<Student> findAdmissionsBySearchCriteria(final AdmissionSearchCriteria admissionSearchCriteria) throws BusinessException {
		return this.studentDao.findAdmissionsBySearchCriteria(admissionSearchCriteria);
	}

}
