package com.apeironsol.need.core.portal.util;

import java.io.Serializable;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.apeironsol.need.util.constants.ViewContentConstant;

@Named
@Scope(value = "session")
public class ViewContentHandlerBean implements Serializable {

	/**
	 * Unique serial version id for this class.
	 */
	private static final long	serialVersionUID	= 2111371710088478791L;

	private ViewContentConstant	currentViewContent;

	public ViewContentConstant getCurrentViewContent() {
		return this.currentViewContent;
	}

	public void setCurrentViewContent(final ViewContentConstant currentViewContent) {
		this.currentViewContent = currentViewContent;
	}

	public boolean isViewContentOrganization() {
		return ViewContentConstant.ORGANIZATION_ORGANIZATION.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentOrganizationBranches() {
		return ViewContentConstant.ORGANIZATION_BRANCHES.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentOrganizationBuildingBlocks() {
		return ViewContentConstant.ORGANIZATION_BUILDING_BLOCKS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentOrganizationUserManagementUsers() {
		return ViewContentConstant.ORGANIZATION_USER_ACCOUNTS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentOrganizationUserManagementUserGroups() {
		return ViewContentConstant.ORGANIZATION_USER_GROUPS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentOrganizationSettings() {
		return ViewContentConstant.ORGANIZATION_SETTINGS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentOrganizationFinanceDetails() {
		return ViewContentConstant.ORGANIZATION_FINANCES_DETAILS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchDashboard() {
		return ViewContentConstant.BRANCH_DASHBOARD.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchAcademicYears() {
		return ViewContentConstant.BRANCH_ACADEMIC_YEARS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchFinancialYears() {
		return ViewContentConstant.BRANCH_FINANCIAL_YEARS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchEmployees() {
		return ViewContentConstant.BRANCH_EMPLOYEES.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchKlasses() {
		return ViewContentConstant.BRANCH_KLASSES.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchKlassFee() {
		return ViewContentConstant.BRANCH_KLASS_FEE.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchSections() {
		return ViewContentConstant.BRANCH_SECTIONS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchExamTypes() {
		return ViewContentConstant.BRANCH_EXAM_TYPES.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchExams() {
		return ViewContentConstant.BRANCH_EXAMS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchAdmissions() {
		return ViewContentConstant.BRANCH_ADMISSIONS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchStudents() {
		return ViewContentConstant.BRANCH_STUDENTS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchTransportationPickupPoints() {
		return ViewContentConstant.BRANCH_TRANSPORTATION_PICKUP_POINTS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchTransportationRoutes() {
		return ViewContentConstant.BRANCH_TRANSPORTATION_ROUTS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchTransportationVehicals() {
		return ViewContentConstant.BRANCH_TRANSPORTATION_VEHICLES.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentMyProfile() {
		return ViewContentConstant.MY_PROFILE.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchFinancesAssertsAndLiabilities() {
		return ViewContentConstant.BRANCH_FINANCES_ASSERTS_LIABILITIES.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchFinancesFeesAndSalariesDetails() {
		return ViewContentConstant.BRANCH_FINANCES_FEES_AND_SALARIES_DETAILS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchFinancesIncomeAndExpenses() {
		return ViewContentConstant.BRANCH_FINANCES_INCOME_EXPENSES.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchFinancesPurchases() {
		return ViewContentConstant.BRANCH_FINANCES_PURCHASES.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchFinancesInvoiceAndSuppliers() {
		return ViewContentConstant.BRANCH_FINANCES_INVOICE_SUPPLIERS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchFinancesBankAccounts() {
		return ViewContentConstant.BRANCH_FINANCES_BANK_ACCOUNTS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchFinancesCreditAccounts() {
		return ViewContentConstant.BRANCH_FINANCES_CREDIT_ACCOUNTS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchFinancesBalanceSheet() {
		return ViewContentConstant.BRANCH_FINANCES_BALANCE_SHEET.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentApproveRefundAndDeductFee() {
		return ViewContentConstant.APPROVE_REFUND_DEDUCT_FEE.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentStudentPortalPersonal() {
		return ViewContentConstant.STUDENT_PORTAL_PERSONAL.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentStudentPortalParents() {
		return ViewContentConstant.STUDENT_PORTAL_PARENTS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentStudentPortalMedical() {
		return ViewContentConstant.STUDENT_PORTAL_MEDICAL.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentEmployeePortalPersonal() {
		return ViewContentConstant.EMPLOYEE_PORTAL_PERSONAL.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchBatches() {
		return ViewContentConstant.BRANCH_BATCHES.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchPromoteTransferDemoteStudents() {
		return ViewContentConstant.BRANCH_PROMOTE_TRANSFER_DEMOTE_STUDENTS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentFinancialBranchLevelFee() {
		return ViewContentConstant.FINANCIAL_BRANCH_LEVEL_FEES.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentFinancialKlassLevelFee() {
		return ViewContentConstant.FINANCIAL_KLASS_LEVEL_FEES.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchSalaries() {
		return ViewContentConstant.BRANCH_SALARIES.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentSMSProviders() {
		return ViewContentConstant.SMS_PROVIDERS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchImport() {
		return ViewContentConstant.BRANCH_IMPORT.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchExport() {
		return ViewContentConstant.BRANCH_EXPORT.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchReportsStudentsGeneral() {
		return ViewContentConstant.BRANCH_REPORTS_STUDENTS_GENERAL.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchReportsFinancial() {
		return ViewContentConstant.BRANCH_REPORTS_FINANCIAL.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchStudentsNotifications() {
		return ViewContentConstant.BRANCH_STUDENTS_NOTIFICATIONS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchAdmissionsNotifications() {
		return ViewContentConstant.BRANCH_ADMISSIONS_NOTIFICATIONS.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchChangePassword() {
		return ViewContentConstant.BRANCH_CHANGE_PASSWORD.equals(this.getCurrentViewContent());
	}

	public boolean isViewContentBranchHostelRoom() {
		return ViewContentConstant.BRANCH_HOSTEL_ROOMS.equals(this.getCurrentViewContent());
	}

}
