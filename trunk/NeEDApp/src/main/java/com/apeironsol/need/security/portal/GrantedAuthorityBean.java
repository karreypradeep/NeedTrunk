package com.apeironsol.need.security.portal;

import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;

import com.apeironsol.need.util.constants.AuthorityConstant;
import com.apeironsol.need.util.portal.ViewUtil;

/**
 * JSF managed bean for granted authority.
 * 
 * @author Pradeep
 */
@Named
@Scope(value = "session")
public class GrantedAuthorityBean implements Serializable {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 8713918863451097432L;

	public boolean isUserHasAuthority(final AuthorityConstant authorityConstant) {
		final Collection<GrantedAuthority> grantedAuthorities = ViewUtil.getGrantedAuthorities();

		for (final GrantedAuthority grantedAuthority : grantedAuthorities) {

			if (AuthorityConstant.ROLE_APEIRONSOL.toString().equals(grantedAuthority.getAuthority())
					|| grantedAuthority.getAuthority().equals(authorityConstant.toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the current user has roles supplied to the method.
	 * 
	 * @param roles
	 *            roles to check if user is granted.
	 * @return true if user has roles supplied to this method.
	 */
	public boolean isUserHasAuthorities(final EnumSet<AuthorityConstant> userAuthorities) {
		boolean result = false;
		if (userAuthorities != null && !userAuthorities.isEmpty()) {
			final Collection<GrantedAuthority> grantedAuthorities = ViewUtil.getGrantedAuthorities();
			for (final AuthorityConstant UserAuthority : userAuthorities) {
				for (final GrantedAuthority grantedAuthority : grantedAuthorities) {
					if (AuthorityConstant.ROLE_APEIRONSOL.toString().equals(grantedAuthority.getAuthority())
							|| UserAuthority.toString().equals(grantedAuthority.getAuthority())) {
						result = true;
					}
				}
			}

		}
		return result;
	}

	// Organization
	public boolean isUserAllowedToCreateOrganization() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_ORGANIZATION);
	}

	public boolean isUserAllowedToLockOrganization() {
		return this.isUserHasAuthority(AuthorityConstant.LOCK_ORGANIZATION);
	}

	public boolean isUserAllowedToUnlockOrganization() {
		return this.isUserHasAuthority(AuthorityConstant.UNLOCK_ORGANIZATION);
	}

	// Employee
	public boolean isUserAllowedToCreateEmployee() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_EMPLOYEE);
	}

	public boolean isUserAllowedToUpdateEmployee() {
		return this.isUserHasAuthority(AuthorityConstant.UPDATE_EMPLOYEE);
	}

	public boolean isUserAllowedToRemoveEmployee() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_EMPLOYEE);
	}

	// Building Block
	public boolean isUserAllowedToCreateBuildingBlock() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_BUILDING_BLOCK);
	}

	public boolean isUserAllowedToRemoveBuildingBlock() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_BUILDING_BLOCK);
	}

	// Branch
	public boolean isUserAllowedToCreateBranch() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_BRANCH);
	}

	public boolean isUserAllowedToRemoveBranch() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_BRANCH);
	}

	public boolean isUserAllowedToActivateBranch() {
		return this.isUserHasAuthority(AuthorityConstant.ACTIVATE_BRANCH);
	}

	public boolean isUserAllowedToDeactivateBranch() {
		return this.isUserHasAuthority(AuthorityConstant.DEACTIVATE_BRANCH);
	}

	// Academic Year
	public boolean isUserAllowedToCreateAcademicYear() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_ACADEMIC_YEAR);
	}

	public boolean isUserAllowedToRemoveAcademicYear() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_ACADEMIC_YEAR);
	}

	public boolean isUserAllowedToActivateAcademicYear() {
		return this.isUserHasAuthority(AuthorityConstant.ACTIVATE_ACADEMIC_YEAR);
	}

	public boolean isUserAllowedToDeactivateAcademicYear() {
		return this.isUserHasAuthority(AuthorityConstant.DEACTIVATE_ACADEMIC_YEAR);
	}

	// Batch
	public boolean isUserAllowedToCreateBatch() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_BATCH);
	}

	public boolean isUserAllowedToRemoveBatch() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_BATCH);
	}

	public boolean isUserAllowedToActivateBatch() {
		return this.isUserHasAuthority(AuthorityConstant.ACTIVATE_BATCH);
	}

	public boolean isUserAllowedToDeactivateBatch() {
		return this.isUserHasAuthority(AuthorityConstant.DEACTIVATE_BATCH);
	}

	// Class
	public boolean isUserAllowedToCreateKlass() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_KLASS);
	}

	public boolean isUserAllowedToRemoveKlass() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_KLASS);
	}

	public boolean isUserAllowedToActivateKlass() {
		return this.isUserHasAuthority(AuthorityConstant.ACTIVATE_KLASS);
	}

	public boolean isUserAllowedToDeactivateKlass() {
		return this.isUserHasAuthority(AuthorityConstant.DEACTIVATE_KLASS);
	}

	// Section
	public boolean isUserAllowedToCreateSection() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_SECTION);
	}

	public boolean isUserAllowedToRemoveSection() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_SECTION);
	}

	public boolean isUserAllowedToActivateSection() {
		return this.isUserHasAuthority(AuthorityConstant.ACTIVATE_SECTION);
	}

	public boolean isUserAllowedToDeactivateSection() {
		return this.isUserHasAuthority(AuthorityConstant.DEACTIVATE_SECTION);
	}

	// Fee Definitions
	public boolean isUserAllowedToCreateFeeDefinitions() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_FEE_DEFINITION);
	}

	public boolean isUserAllowedToRemoveFeeDefinitions() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_FEE_DEFINITION);
	}

	// Fee Definitions
	public boolean isUserAllowedToProcessFeePayment() {
		return this.isUserHasAuthority(AuthorityConstant.PROCESS_FEE_PAYMENT);
	}

	public boolean isUserAllowedToProcessFeeRefund() {
		return this.isUserHasAuthority(AuthorityConstant.PROCESS_FEE_REFUND);
	}

	public boolean isUserAllowedToProcessFeeDeduction() {
		return this.isUserHasAuthority(AuthorityConstant.PROCESS_FEE_DEDUCTION);
	}

	public boolean isUserAllowedToProcessFeeCancellation() {
		return this.isUserHasAuthority(AuthorityConstant.PROCESS_FEE_CANCELLATION);
	}

	public boolean isUserAllowedToProcessStudentPocketMoneyWithdraw() {
		return this.isUserHasAuthority(AuthorityConstant.PROCESS_STUDENT_POCKET_MONEY_WITHDRAW);
	}

	public boolean isUserAllowedToProcessStudentPocketMoneyDeposit() {
		return this.isUserHasAuthority(AuthorityConstant.PROCESS_STUDENT_POCKET_MONEY_DEPOSIT);
	}

	// Fee Refund
	public boolean isUserAllowedToCreateFeeRefundRequest() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_FEE_REFUND_REQUEST);
	}

	public boolean isUserAllowedToAcceptFeeRefundRequest() {
		return this.isUserHasAuthority(AuthorityConstant.ACCEPT_FEE_REFUND_REQUEST);
	}

	public boolean isUserAllowedToRejectFeeRefundRequest() {
		return this.isUserHasAuthority(AuthorityConstant.REJECT_FEE_REFUND_REQUEST);
	}

	// Fee Deduction
	public boolean isUserAllowedToCreateFeeDeductionRequest() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_FEE_DEDUCTION_REQUEST);
	}

	public boolean isUserAllowedToAcceptFeeDeductionRequest() {
		return this.isUserHasAuthority(AuthorityConstant.ACCEPT_FEE_DEDUCTION_REQUEST);
	}

	public boolean isUserAllowedToRejectFeeDeductionRequest() {
		return this.isUserHasAuthority(AuthorityConstant.REJECT_FEE_DEDUCTION_REQUEST);
	}

	// Security
	// User
	public boolean isUserAllowedToCreateUserAccount() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_USER);
	}

	public boolean isUserAllowedToRemoveUserAccount() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_USER);
	}

	// Security
	// User Group
	public boolean isUserAllowedToCreateUserGroup() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_USER_GROUP);
	}

	public boolean isUserAllowedToRemoveUserGroup() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_USER_GROUP);
	}

	public boolean isUserAllowedToCreatePurchaseOrder() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_PURCHASE_ORDER);
	}

	// Security
	// User Group
	public boolean isUserAllowedToUdatePurchaseOrder() {
		return this.isUserHasAuthority(AuthorityConstant.UPDATE_PURCHASE_ORDER);
	}

	public boolean isUserAllowedToApprovePurchaseOrder() {
		return this.isUserHasAuthority(AuthorityConstant.APPROVE_PURCHASE_ORDER);
	}

	public boolean isUserAllowedToAccessFinancialData() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_FINANCIALS);
	}

	public boolean isUserAllowedToAccessAdmissions() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_ADMISSIONS);
	}

	public boolean isUserAllowedToAccessStudents() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_STUDENTS);
	}

	public boolean isUserAllowedToAccessHRMInfo() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_HRM_INFO);
	}

	public boolean isUserAllowedToAccessFeeDefinitions() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_FEE_DEFINITIONS);
	}

	public boolean isUserAllowedToAccessStudentFees() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_STUDENT_FEE);
	}

	public boolean isUserAllowedToAccessAcademics() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_ACADEMICS);
	}

	public boolean isUserAllowedToAccessFinancialsAssertsAndLiabilities() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_FINANCIALS_ASSERTS_AND_LIABILITIES);
	}

	public boolean isUserAllowedToAccessFinancialsProcessFeeTransactions() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_FINANCIALS_PROCESS_FEE_TRANSACTIONS);
	}

	public boolean isUserAllowedToAccessFinancialsFeeDetails() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_FINANCIALS_FEE_DETAILS);
	}

	public boolean isUserAllowedToAccessFinancialsIncomeAndExpenses() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_FINANCIALS_INCOME_EXXPENSES);
	}

	public boolean isUserAllowedToAccessFinancialsPurchases() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_FINANCIALS_PURCHASES);
	}

	public boolean isUserAllowedToAccessFinancialsInvoiceSuppliers() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_FINANCIALS_INVOICE_SUPPLIERS);
	}

	public boolean isUserAllowedToAccessFinancialsBankAccounts() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_FINANCIALS_BANK_ACCOUNTS);
	}

	public boolean isUserAllowedToAccessFinancialsCreditAccounts() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_FINANCIALS_CREDIT_ACCOUNTS);
	}

	public boolean isUserAllowedToAccessFinancialsBalanceSheet() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_FINANCIALS_BALANCE_SHEET);
	}

	public boolean isUserAllowedToAccessBranchDefinitions() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_BRANCH_DEFINITIONS);
	}

	public boolean isUserAllowedToAccessBranchSettings() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_BRANCH_SETTINGS);
	}

	public boolean isUserAllowedToAccessAcademicYears() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_ACADEMIC_YEARS);
	}

	public boolean isUserAllowedToAccessBatches() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_BATCHS);
	}

	public boolean isUserAllowedToAccessClasses() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_CLASSES);
	}

	public boolean isUserAllowedToAccessSections() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_SECTIONS);
	}

	public boolean isUserAllowedToAccessTransportation() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_TRANSPORTATION);
	}

	public boolean isUserAllowedToAccessPickupPoints() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_PICKUP_POINTS);
	}

	public boolean isUserAllowedToAccessRoutes() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_ROUTES);
	}

	public boolean isUserAllowedToAccessVehicals() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_VEHICLES);
	}

	public boolean isUserAllowedToAccessImports() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_IMPORTS);
	}

	public boolean isUserAllowedToAccessImportAdmissions() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_IMPORT_ADMISSIONS);
	}

	public boolean isUserAllowedToAccessReports() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_REPORTS);
	}

	public boolean isUserAllowedToAccessStudentAddress() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_STUDENT_ADDRESS);
	}

	public boolean isUserAllowedToAccessStudentMedicalHistory() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_STUDENT_MEDICAL_HISTORY);
	}

	public boolean isUserAllowedToAccessStudentParentDetails() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_STUDENT_PARENT_DETAILS);
	}

	public boolean isUserAllowedToAccessStudentStatusHistory() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_STUDENT_STATUS_HISTORY);
	}

	public boolean isUserAllowedToAccessStudentAttendance() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_STUDENT_ATTENDANCE);
	}

	public boolean isUserAllowedToAccessStudentHostelRoom() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_STUDENT_HOSTEL_ROOM);
	}

	public boolean isUserAllowedToAccessStudentNotifications() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_STUDENT_NOTIFICATIONS);
	}

	public boolean isUserAllowedToAccessStudentAcademics() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_STUDENT_ACADEMICS);
	}

	public boolean isUserAllowedToAccessStudentTransportation() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_STUDENT_TRANSPORTATION);
	}

	public boolean isUserAllowedToAccessAttendance() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_ATTENDANCE);
	}

	public boolean isUserAllowedToAccessNotifications() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_NOTIFICATIONS);
	}

	public boolean isUserAllowedToAccessStudentActions() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_STUDENT_ACTIONS);
	}

	public boolean isUserAllowedToPromoteStudent() {
		return this.isUserHasAuthority(AuthorityConstant.PROMOTE_STUDENT);
	}

	public boolean isUserAllowedToTransferStudent() {
		return this.isUserHasAuthority(AuthorityConstant.TRANSFER_STUDENT);
	}

	public boolean isUserAllowedToTerminateAction() {
		return this.isUserHasAuthority(AuthorityConstant.TERMINATE_STUDENT);
	}

	public boolean isUserAllowedToDropOutStudent() {
		return this.isUserHasAuthority(AuthorityConstant.DROPOUT_STUDENT);
	}

	public boolean isUserAllowedToAcceptForDropOutStudent() {
		return this.isUserHasAuthority(AuthorityConstant.ACCEPT_FOR_DROPOUT_STUDENT);
	}

	public boolean isUserAllowedToRollbackDropOutStudent() {
		return this.isUserHasAuthority(AuthorityConstant.ROLLBACK_DROPOUT_STUDENT);
	}

	public boolean isUserAllowedToCreateAdmission() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_ADMISSION);
	}

	public boolean isUserAllowedToReviewAdmission() {
		return this.isUserHasAuthority(AuthorityConstant.REVIEW_ADMISSION);
	}

	public boolean isUserAllowedToAcceptAdmission() {
		return this.isUserHasAuthority(AuthorityConstant.ACCEPT_ADMISSION);
	}

	public boolean isUserAllowedToAdmitAdmission() {
		return this.isUserHasAuthority(AuthorityConstant.ADMIT_ADMISSION);
	}

	public boolean isUserAllowedToRejectAdmission() {
		return this.isUserHasAuthority(AuthorityConstant.REJECT_ADMISSION);
	}

	public boolean isUserAllowedToRollbackAdmission() {
		return this.isUserHasAuthority(AuthorityConstant.ROLLBACK_ADMISSION);
	}

	public boolean isUserAllowedToCreateWeekend() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_WEEKEND);
	}

	public boolean isUserAllowedToRemoveWeekend() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_WEEKEND);
	}

	public boolean isUserAllowedToCreateHoliday() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_HOLIDAY);
	}

	public boolean isUserAllowedToRemoveHoliday() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_HOLIDAY);
	}

	public boolean isUserAllowedToCreateTimetable() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_TIMETABLE);
	}

	public boolean isUserAllowedToRemoveTimetable() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_TIMETABLE);
	}

	public boolean isUserAllowedToAccessFeeCollected() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_FEE_COLLECTED);
	}

	public boolean isUserAllowedToCreateAttendance() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_ATTENDANCE);
	}

	public boolean isUserAllowedToRemoveAttendance() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_ATTENDANCE);
	}

	public boolean isUserAllowedToCreateAssert() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_ASSERT);
	}

	public boolean isUserAllowedToRemoveAssert() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_ASSERT);
	}

	public boolean isUserAllowedToCreateLiability() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_LIABILITY);
	}

	public boolean isUserAllowedToRemoveLiability() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_LIABILITY);
	}

	public boolean isUserAllowedToCreateIncome() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_INCOME);
	}

	public boolean isUserAllowedToRemoveIncome() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_INCOME);
	}

	public boolean isUserAllowedToCreateInvestment() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_INVESTMENT);
	}

	public boolean isUserAllowedToRemoveInvestment() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_INVESTMENT);
	}

	public boolean isUserAllowedToCreateExpenses() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_EXPENSES);
	}

	public boolean isUserAllowedToRemoveExpenses() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_EXPENSES);
	}

	public boolean isUserAllowedToCreateSupplier() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_SUPPLIER);
	}

	public boolean isUserAllowedToRemoveSupplier() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_SUPPLIER);
	}

	public boolean isUserAllowedToCreateInvoice() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_INVOICE);
	}

	public boolean isUserAllowedToRemoveInvoice() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_INVOICE);
	}

	public boolean isUserAllowedToUpdatePurchaseOrder() {
		return this.isUserHasAuthority(AuthorityConstant.UPDATE_PURCHASE_ORDER);
	}

	public boolean isUserAllowedToAccessExams() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_EXAMS);
	}

	public boolean isUserAllowedToCreateExam() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_EXAM);
	}

	public boolean isUserAllowedToRemoveExam() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_EXAM);
	}

	public boolean isUserAllowedToScheduleExam() {
		return this.isUserHasAuthority(AuthorityConstant.SCHEDULE_EXAM);
	}

	// Batch
	public boolean isUserAllowedToCreateEmployeeCTC() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_EMPLOYEE_CTC);
	}

	public boolean isUserAllowedToRemoveEmployeeCTC() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_EMPLOYEE_CTC);
	}

	public boolean isUserAllowedToSendNotificationsBranch() {
		return this.isUserHasAuthority(AuthorityConstant.NOTIFICATIONS_BRANCH_LEVEL);
	}

	public boolean isUserAllowedToSendNotificationsClass() {
		return this.isUserHasAuthority(AuthorityConstant.NOTIFICATIONS_CLASS_LEVEL);
	}

	public boolean isUserAllowedToSendNotificationsSection() {
		return this.isUserHasAuthority(AuthorityConstant.NOTIFICATIONS_SECTION_LEVEL);
	}

	public boolean isUserAllowedToSendNotificationsStudent() {
		return this.isUserHasAuthority(AuthorityConstant.NOTIFICATIONS_STUDENT_LEVEL);
	}

	public boolean isUserAllowedToAccessStudentInfoReports() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_STUDENT_INFO_REPORTS);
	}

	public boolean isUserAllowedToAccessFinancialReports() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_FINANCIAL_REPORTS);
	}

	public boolean isUserAllowedToUpdateStudentPersonalDetails() {
		return this.isUserHasAuthority(AuthorityConstant.UPDATE_STUDENT_PERSONAL_DETAILS);
	}

	public boolean isUserAllowedToUpdateStudentAddress() {
		return this.isUserHasAuthority(AuthorityConstant.UPDATE_STUDENT_ADDRESS);
	}

	public boolean isUserAllowedToUpdateStudentParentDetails() {
		return this.isUserHasAuthority(AuthorityConstant.UPDATE_STUDENT_PARENT_DETAILS);
	}

	public boolean isUserAllowedToUpdateStudentMedicalHistory() {
		return this.isUserHasAuthority(AuthorityConstant.UPDATE_STUDENT_MEDICAL_HISTORY);
	}

	public boolean isUserAllowedToAccessHostelRooms() {
		return this.isUserHasAuthority(AuthorityConstant.ACCESS_HOSTEL_ROOMS);
	}

	public boolean isUserAllowedToCreateHostelRoom() {
		return this.isUserHasAuthority(AuthorityConstant.CREATE_HOSTEL_ROOM);
	}

	public boolean isUserAllowedToRemoveHostelRoom() {
		return this.isUserHasAuthority(AuthorityConstant.REMOVE_HOSTEL_ROOM);
	}

}
