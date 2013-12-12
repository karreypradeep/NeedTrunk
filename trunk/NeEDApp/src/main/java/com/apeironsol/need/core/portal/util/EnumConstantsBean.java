/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.core.portal.util;

import java.io.Serializable;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.util.constants.AccessControlTypeConstant;
import com.apeironsol.need.util.constants.AdmissionStatusConstant;
import com.apeironsol.need.util.constants.AlertTypeConstant;
import com.apeironsol.need.util.constants.AttendanceTypeConstant;
import com.apeironsol.need.util.constants.BankAccountTransactionTypeConstant;
import com.apeironsol.need.util.constants.BranchAccountTypeConstant;
import com.apeironsol.need.util.constants.BranchTypeConstant;
import com.apeironsol.need.util.constants.CreditAccountTransactionTypeConstant;
import com.apeironsol.need.util.constants.DesignationConstant;
import com.apeironsol.need.util.constants.DueDateConstant;
import com.apeironsol.need.util.constants.DurationTypeConstants;
import com.apeironsol.need.util.constants.EmployeeCTCCategoryTypeConstant;
import com.apeironsol.need.util.constants.EmployeeCTCDefinitionTypeConstant;
import com.apeironsol.need.util.constants.EmploymentTypeConstant;
import com.apeironsol.need.util.constants.FeeClassificationLevelConstant;
import com.apeironsol.need.util.constants.FeeTypeConstant;
import com.apeironsol.need.util.constants.FrequencyConstant;
import com.apeironsol.need.util.constants.GenderConstant;
import com.apeironsol.need.util.constants.MediumConstant;
import com.apeironsol.need.util.constants.MonthConstant;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;
import com.apeironsol.need.util.constants.NotificationTypeConstant;
import com.apeironsol.need.util.constants.PaymentFrequencyConstant;
import com.apeironsol.need.util.constants.PaymentMethodConstant;
import com.apeironsol.need.util.constants.PurchaseOrderStatusConstant;
import com.apeironsol.need.util.constants.PurchaseOrderTypeConstant;
import com.apeironsol.need.util.constants.RegistrationTypeConstant;
import com.apeironsol.need.util.constants.RelationTypeConstant;
import com.apeironsol.need.util.constants.ResidenceConstant;
import com.apeironsol.need.util.constants.SalaryDeductionTypeConstant;
import com.apeironsol.need.util.constants.SalaryFrequencyConstant;
import com.apeironsol.need.util.constants.SalaryTypeConstant;
import com.apeironsol.need.util.constants.SchoolTypeConstant;
import com.apeironsol.need.util.constants.SectionReportsConstant;
import com.apeironsol.need.util.constants.StudentAdditionalFeeTypeConstant;
import com.apeironsol.need.util.constants.StudentExamSubjectStatusConstant;
import com.apeironsol.need.util.constants.StudentFeeTransactionStatusConstant;
import com.apeironsol.need.util.constants.StudentFeeTransactionTypeConstant;
import com.apeironsol.need.util.constants.StudentPocketMoneyTransactionTypeConstant;
import com.apeironsol.need.util.constants.StudentReportNamesConstant;
import com.apeironsol.need.util.constants.StudentSectionStatusConstant;
import com.apeironsol.need.util.constants.StudentStatusConstant;
import com.apeironsol.need.util.constants.UserAccountTypeConstant;
import com.apeironsol.need.util.constants.WeekDayConstant;

@Named
@Scope(value = "application")
public class EnumConstantsBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= 7346025627485709946L;

	public DesignationConstant[] getDesignations() {
		return DesignationConstant.values();
	}

	public GenderConstant[] getGender() {
		return GenderConstant.values();
	}

	public SchoolTypeConstant[] getSchoolTypes() {
		return SchoolTypeConstant.values();
	}

	public RegistrationTypeConstant[] getRegistrationTypes() {
		return RegistrationTypeConstant.values();
	}

	public MonthConstant[] getMonths() {
		return MonthConstant.values();
	}

	public MediumConstant[] getMediams() {
		return MediumConstant.values();
	}

	public RelationTypeConstant[] getRelationType() {
		return RelationTypeConstant.values();
	}

	public ResidenceConstant[] getResidences() {
		return ResidenceConstant.values();
	}

	public FeeTypeConstant[] getFeesTypes() {
		return FeeTypeConstant.values();
	}

	public FrequencyConstant[] getFrequencies() {
		return FrequencyConstant.values();
	}

	public AlertTypeConstant[] getAlertTypes() {
		return AlertTypeConstant.values();
	}

	public AttendanceTypeConstant[] getAttendanceTypes() {
		return AttendanceTypeConstant.values();
	}

	public AdmissionStatusConstant[] getAdmissionStatusTypes() {
		return AdmissionStatusConstant.values();
	}

	public PaymentFrequencyConstant[] getPaymentFrequencies() {
		return PaymentFrequencyConstant.values();
	}

	public DueDateConstant[] getDueDates() {
		return DueDateConstant.values();
	}

	public AccessControlTypeConstant[] getAccessControlTypes() {
		return AccessControlTypeConstant.values();
	}

	public EmploymentTypeConstant[] getEmploymentTypes() {
		return EmploymentTypeConstant.values();
	}

	public DurationTypeConstants[] getDurationTypeConstants() {
		return DurationTypeConstants.values();
	}

	public SectionReportsConstant[] getSectionReportsConstants() {
		return SectionReportsConstant.values();
	}

	public NotificationTypeConstant[] getNotificationTypeConstants() {
		return NotificationTypeConstant.values();
	}

	public NotificationSubTypeConstant[] getNotificationSubTypeConstants() {
		return NotificationSubTypeConstant.values();
	}

	public NotificationSubTypeConstant[] getNotificationSubTypeConstantsGroup() {
		return NotificationSubTypeConstant.getBrachGroupNotifications();
	}

	public WeekDayConstant[] getWeekDayConstants() {
		return WeekDayConstant.values();
	}

	public SalaryTypeConstant[] getSalaryTypeConstants() {
		return SalaryTypeConstant.getSalaryTypeConstantsForPayment();
	}

	public UserAccountTypeConstant[] getLoginTypeConstants() {
		return UserAccountTypeConstant.getAllowedlogintypes().toArray(new UserAccountTypeConstant[UserAccountTypeConstant.getAllowedlogintypes().size()]);
	}

	public PurchaseOrderTypeConstant[] getPurchaseOrderTypeConstants() {
		return PurchaseOrderTypeConstant.values();
	}

	public StudentPocketMoneyTransactionTypeConstant[] getStudentPocketMoneyTransactionTypeConstants() {
		return StudentPocketMoneyTransactionTypeConstant.values();
	}

	public PurchaseOrderStatusConstant[] getPurchaseOrderStatusConstants() {
		return PurchaseOrderStatusConstant.values();
	}

	public PaymentMethodConstant[] getPaymentMethodsConstants() {
		return PaymentMethodConstant.values();
	}

	public BranchAccountTypeConstant[] getBranchAccountTypeConstants() {
		return BranchAccountTypeConstant.values();
	}

	public StudentStatusConstant[] getStudentStatusConstants() {
		return StudentStatusConstant.values();
	}

	public BranchTypeConstant[] getBranchTypeConstants() {
		return BranchTypeConstant.values();
	}

	public StudentFeeTransactionTypeConstant[] getStudentFeeTransactionTypeConstants() {
		return StudentFeeTransactionTypeConstant.values();
	}

	public StudentFeeTransactionStatusConstant[] getStudentFeeTransactionStatusConstantsForRequestOrPending() {
		return StudentFeeTransactionStatusConstant.getAllStatusForRequestAndPending().toArray(
				new StudentFeeTransactionStatusConstant[StudentFeeTransactionStatusConstant.getAllStatusForRequestAndPending().size()]);
	}

	public StudentFeeTransactionStatusConstant[] getStudentFeeTransactionStatusConstants() {
		return StudentFeeTransactionStatusConstant.values();
	}

	public StudentStatusConstant[] getStudentStatusConstantsForSearch() {
		final StudentStatusConstant[] result = new StudentStatusConstant[] { StudentStatusConstant.ACTIVE, StudentStatusConstant.ALMUNUS,
				StudentStatusConstant.ACCEPT_FOR_DROPOUT, StudentStatusConstant.DROPOUT };
		return result;

	}

	public StudentSectionStatusConstant[] getStudentSectionStatusConstants() {
		return StudentSectionStatusConstant.values();
	}

	public StudentAdditionalFeeTypeConstant[] getStudentAdditionalFeeTypes() {
		return StudentAdditionalFeeTypeConstant.values();
	}

	public FeeClassificationLevelConstant[] getFeeClassificationLevels() {
		return FeeClassificationLevelConstant.values();
	}

	public FeeTypeConstant[] getKlassFeeTypes() {
		return new FeeTypeConstant[] { FeeTypeConstant.HOSTEL_FEE, FeeTypeConstant.TUITION_FEE };
	}

	public EmployeeCTCDefinitionTypeConstant[] getEmployeeCTCDefinitionTypes() {
		return EmployeeCTCDefinitionTypeConstant.values();
	}

	public EmployeeCTCCategoryTypeConstant[] getEmployeeCTCCategoryTypes() {
		return EmployeeCTCCategoryTypeConstant.values();
	}

	public SalaryFrequencyConstant[] getSalaryFrequencies() {
		return SalaryFrequencyConstant.values();
	}

	public SalaryDeductionTypeConstant[] getSalaryDeductionTypes() {
		return SalaryDeductionTypeConstant.values();
	}

	public StudentExamSubjectStatusConstant[] getStudentExamSubjectStatus() {
		return StudentExamSubjectStatusConstant.values();
	}

	public BankAccountTransactionTypeConstant[] getBankAccountTransactionTypes() {
		return BankAccountTransactionTypeConstant.values();
	}

	public CreditAccountTransactionTypeConstant[] getCreditAccountTransactionTypes() {
		return CreditAccountTransactionTypeConstant.values();
	}

	public StudentReportNamesConstant[] getJasperReportNames() {
		return StudentReportNamesConstant.getValuesSortedByLabels();
	}

}