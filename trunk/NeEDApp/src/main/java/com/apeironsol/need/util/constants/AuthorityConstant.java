/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.constants;

import java.util.ArrayList;
import java.util.List;

import com.apeironsol.need.util.portal.ViewUtil;

/**
 * Enums for Authority constants
 * 
 * @author Pradeep
 */
public enum AuthorityConstant implements Labeled {

	// User Authorities
	ROLE_APEIRONSOL("role_apeironsol", AuthorityCategoryConstant.SECURITY, AuthoritySubCategoryConstant.ROLE),
	ROLE_ADMIN("role_admin", AuthorityCategoryConstant.SECURITY, AuthoritySubCategoryConstant.ROLE),
	ROLE_EMPLOYEE("role_employee", AuthorityCategoryConstant.SECURITY, AuthoritySubCategoryConstant.ROLE),
	ROLE_STUDENT("role_student", AuthorityCategoryConstant.SECURITY, AuthoritySubCategoryConstant.ROLE),
	ROLE_PARENT("role_parent", AuthorityCategoryConstant.SECURITY, AuthoritySubCategoryConstant.ROLE),

	// Access Core
	ACCESS_BRANCHS("auth_access_branches", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_BUILDING_BLOCKS("access_building_blocks", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_SETTINGS("access_settings", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_BRANCH("access_branch", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_FEE_DEFINITIONS("access_fee_definitions", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.ACCESS),

	ACCESS_ADMISSIONS("access_admissions", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_BRANCH_DEFINITIONS("access_branch_definitons", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_ACADEMIC_YEARS("access_academic_years", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_BATCHS("access_batches", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_CLASSES("access_classes", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_SECTIONS("access_sections", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_BRANCH_SETTINGS("access_branch_settings", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_HOSTEL_ROOMS("access_hostel_rooms", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_GRADE_SYSTEM("access_grade_system", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_REPORT_CARD("access_report_card", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACCESS),

	// Access Security
	ACCESS_SECURITY("access_security", AuthorityCategoryConstant.SECURITY, AuthoritySubCategoryConstant.ACCESS),

	// Access financials
	ACCESS_FINANCIALS("access_financials", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_STUDENT_FEE("access_student_fee", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_FEE_COLLECTED("access_fee_collected", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.ACCESS),

	ACCESS_FINANCIALS_ASSERTS_AND_LIABILITIES("access_financials_asserts_liabilities",
			AuthorityCategoryConstant.FINANCIALS,
			AuthoritySubCategoryConstant.ACCESS),

	ACCESS_FINANCIALS_PROCESS_FEE_TRANSACTIONS("access_financials_process_fee_transactions",
			AuthorityCategoryConstant.FINANCIALS,
			AuthoritySubCategoryConstant.ACCESS),

	ACCESS_FINANCIALS_FEE_DETAILS("access_financials_fee_details", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_FINANCIALS_INCOME_EXXPENSES("access_financials_income_expense", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_FINANCIALS_PURCHASES("access_financials_purchases", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_FINANCIALS_INVOICE_SUPPLIERS("access_financials_invoice_suppliers", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_FINANCIALS_BANK_ACCOUNTS("access_financials_bank_accounts", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_FINANCIALS_CREDIT_ACCOUNTS("access_financials_credit_accounts", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_FINANCIALS_BALANCE_SHEET("access_financials_balance_sheets", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.ACCESS),

	// Access for Human Resource.
	ACCESS_HRM_INFO("access_hrm_info", AuthorityCategoryConstant.HUMAN_RESOURCE, AuthoritySubCategoryConstant.ACCESS),

	// Access for Academics
	ACCESS_ACADEMICS("access_academics", AuthorityCategoryConstant.ACADEMICS, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_EXAMS("access_exams", AuthorityCategoryConstant.ACADEMICS, AuthoritySubCategoryConstant.ACCESS),

	// Access transportation
	ACCESS_TRANSPORTATION("access_transportation", AuthorityCategoryConstant.TRANSPORTATION, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_PICKUP_POINTS("access_pickup_points", AuthorityCategoryConstant.TRANSPORTATION, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_ROUTES("access_routes", AuthorityCategoryConstant.TRANSPORTATION, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_VEHICLES("access_vehicles", AuthorityCategoryConstant.TRANSPORTATION, AuthoritySubCategoryConstant.ACCESS),

	// Access Import
	ACCESS_IMPORTS("access_imports", AuthorityCategoryConstant.IMPORTS, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_IMPORT_ADMISSIONS("access_import_admissions", AuthorityCategoryConstant.IMPORTS, AuthoritySubCategoryConstant.ACCESS),

	// Access Report
	ACCESS_REPORTS("access_reports", AuthorityCategoryConstant.REPORTS, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_STUDENT_INFO_REPORTS("access_student_info_reports", AuthorityCategoryConstant.REPORTS, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_FINANCIAL_REPORTS("access_financial_reports", AuthorityCategoryConstant.REPORTS, AuthoritySubCategoryConstant.ACCESS),

	// Access student details
	ACCESS_STUDENTS("access_students", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_STUDENT_ADDRESS("access_student_address", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_STUDENT_MEDICAL_HISTORY("access_student_medical_history", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_STUDENT_PARENT_DETAILS("access_student_parent_details", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_STUDENT_STATUS_HISTORY("access_student_status_history", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_STUDENT_NOTIFICATIONS("access_student_notifications", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_STUDENT_ATTENDANCE("access_student_attendance", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_STUDENT_ACADEMICS("access_student_academics", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_STUDENT_TRANSPORTATION("access_student_transportation", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_STUDENT_ACTIONS("access_student_actions", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.ACCESS),
	ACCESS_STUDENT_HOSTEL_ROOM("access_student_hostel_room", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.ACCESS),

	// Update student details
	UPDATE_STUDENT_PERSONAL_DETAILS("update_student_personal_details", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.STUDENT),
	UPDATE_STUDENT_ADDRESS("update_student_address", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.STUDENT),
	UPDATE_STUDENT_MEDICAL_HISTORY("update_student_medical_history", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.STUDENT),
	UPDATE_STUDENT_PARENT_DETAILS("update_student_parent_details", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.STUDENT),

	// Student Actions details
	PROMOTE_STUDENT("promote_student", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.STUDENT),
	TRANSFER_STUDENT("transfer_student", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.STUDENT),
	ACCEPT_FOR_DROPOUT_STUDENT("accept_for_dropout_student", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.STUDENT),
	DROPOUT_STUDENT("dropout_student", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.STUDENT),
	ROLLBACK_DROPOUT_STUDENT("rollback_dropout_student", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.STUDENT),
	TERMINATE_STUDENT("terminate_student", AuthorityCategoryConstant.STUDENT, AuthoritySubCategoryConstant.STUDENT),

	// Attendance
	ACCESS_ATTENDANCE("access_attendance", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACCESS),

	// Notifications
	ACCESS_NOTIFICATIONS("access_notifications", AuthorityCategoryConstant.NOTIFICATIONS, AuthoritySubCategoryConstant.ACCESS),

	// Organization
	CREATE_ORGANIZATION("create_organization", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ORGANIZATION),
	LOCK_ORGANIZATION("lock_organization", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ORGANIZATION),
	UNLOCK_ORGANIZATION("unlock_organization", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ORGANIZATION),

	// Branch
	CREATE_BRANCH("create_branch", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.BRANCH),
	REMOVE_BRANCH("remvoe_branch", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.BRANCH),
	ACTIVATE_BRANCH("activate_branch", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.BRANCH),
	DEACTIVATE_BRANCH("deactive_branch", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.BRANCH),

	// Building blocks
	CREATE_BUILDING_BLOCK("create_building_block", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.BUILDING_BLOCK),
	REMOVE_BUILDING_BLOCK("remove_building_block", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.BUILDING_BLOCK),

	// User Management
	CREATE_USER_GROUP("create_user_group", AuthorityCategoryConstant.SECURITY, AuthoritySubCategoryConstant.USER_GROUP),
	REMOVE_USER_GROUP("remove_user_group", AuthorityCategoryConstant.SECURITY, AuthoritySubCategoryConstant.USER_GROUP),

	CREATE_USER("create_user", AuthorityCategoryConstant.SECURITY, AuthoritySubCategoryConstant.USER_ACCOUNT),
	REMOVE_USER("remove_user", AuthorityCategoryConstant.SECURITY, AuthoritySubCategoryConstant.USER_ACCOUNT),

	// Academic years
	CREATE_ACADEMIC_YEAR("create_academic_year", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACADEMIC_YEAR),
	REMOVE_ACADEMIC_YEAR("remove_acdemic_year", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACADEMIC_YEAR),
	ACTIVATE_ACADEMIC_YEAR("activate_academic_year", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACADEMIC_YEAR),
	DEACTIVATE_ACADEMIC_YEAR("deactive_academic_year", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACADEMIC_YEAR),
	CREATE_WEEKEND("create_week_end", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACADEMIC_YEAR),
	REMOVE_WEEKEND("remove_week_end", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACADEMIC_YEAR),
	CREATE_HOLIDAY("create_holiday", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACADEMIC_YEAR),
	REMOVE_HOLIDAY("remove_holiday", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ACADEMIC_YEAR),

	// Batches
	CREATE_BATCH("create_batch", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.BATCH),
	REMOVE_BATCH("remove_batch", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.BATCH),
	ACTIVATE_BATCH("active_batch", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.BATCH),
	DEACTIVATE_BATCH("deactive_batch", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.BATCH),

	// Classes
	CREATE_KLASS("create_klass", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.KLASS),
	REMOVE_KLASS("remvoe_klass", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.KLASS),
	ACTIVATE_KLASS("active_klass", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.KLASS),
	DEACTIVATE_KLASS("deactive_klass", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.KLASS),

	// Sections
	CREATE_SECTION("create_section", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.SECTION),
	REMOVE_SECTION("remvoe_section", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.SECTION),
	ACTIVATE_SECTION("active_section", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.SECTION),
	DEACTIVATE_SECTION("deactive_section", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.SECTION),
	CREATE_TIMETABLE("create_timetable", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.SECTION),
	REMOVE_TIMETABLE("remove_timetable", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.SECTION),
	CREATE_ATTENDANCE("create_attendance", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.SECTION),
	REMOVE_ATTENDANCE("remove_attendance", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.SECTION),

	// Hostel
	CREATE_HOSTEL_ROOM("create_hostel_room", AuthorityCategoryConstant.HOSTEL, AuthoritySubCategoryConstant.HOSTEL_ROOMS),
	REMOVE_HOSTEL_ROOM("remove_hostel_room", AuthorityCategoryConstant.HOSTEL, AuthoritySubCategoryConstant.HOSTEL_ROOMS),

	// Admissions
	CREATE_ADMISSION("create_admission", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ADMISSION),
	REVIEW_ADMISSION("review_admission", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ADMISSION),
	ACCEPT_ADMISSION("accept_admission", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ADMISSION),
	ADMIT_ADMISSION("admit_admission", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ADMISSION),
	REJECT_ADMISSION("reject_admission", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ADMISSION),
	ROLLBACK_ADMISSION("rollback_admission", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.ADMISSION),

	// Employees
	CREATE_EMPLOYEE("create_employee", AuthorityCategoryConstant.HUMAN_RESOURCE, AuthoritySubCategoryConstant.EMPLOYEE),
	UPDATE_EMPLOYEE("update_employee", AuthorityCategoryConstant.HUMAN_RESOURCE, AuthoritySubCategoryConstant.EMPLOYEE),
	REMOVE_EMPLOYEE("remove_employee", AuthorityCategoryConstant.HUMAN_RESOURCE, AuthoritySubCategoryConstant.EMPLOYEE),

	CREATE_EMPLOYEE_CTC("create_employee_ctc", AuthorityCategoryConstant.HUMAN_RESOURCE, AuthoritySubCategoryConstant.EMPLOYEE),
	REMOVE_EMPLOYEE_CTC("remove_employee_ctc", AuthorityCategoryConstant.HUMAN_RESOURCE, AuthoritySubCategoryConstant.EMPLOYEE),

	// Fee Definitions
	CREATE_FEE_DEFINITION("create_fee_definition", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.FEE_DEFINITION),
	REMOVE_FEE_DEFINITION("remove_fee_definition", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.FEE_DEFINITION),

	// Fee Payment Authorities
	PROCESS_FEE_PAYMENT("process_fee_payment", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.FEE_PAYMENT),
	PROCESS_FEE_REFUND("process_fee_refund", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.FEE_PAYMENT),
	PROCESS_FEE_DEDUCTION("process_fee_deduction", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.FEE_PAYMENT),
	PROCESS_FEE_CANCELLATION("process_fee_cancellation", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.FEE_PAYMENT),
	PROCESS_STUDENT_POCKET_MONEY_WITHDRAW("process_student_pocket_money_withdraw",
			AuthorityCategoryConstant.FINANCIALS,
			AuthoritySubCategoryConstant.FEE_PAYMENT),
	PROCESS_STUDENT_POCKET_MONEY_DEPOSIT("process_student_pocket_money_deposit", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.FEE_PAYMENT),

	// Fee Refund Authorities
	CREATE_FEE_REFUND_REQUEST("create_fee_refund_request", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.FEE_REFUND),
	ACCEPT_FEE_REFUND_REQUEST("accept_fee_refund_request", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.FEE_REFUND),
	REJECT_FEE_REFUND_REQUEST("reject_fee_refund_request", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.FEE_REFUND),

	// Fee Deduction Authorities
	CREATE_FEE_DEDUCTION_REQUEST("create_fee_deduction_request", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.FEE_DEDUCTION),
	ACCEPT_FEE_DEDUCTION_REQUEST("accept_fee_deduction_request", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.FEE_DEDUCTION),
	REJECT_FEE_DEDUCTION_REQUEST("reject_fee_deduction_request", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.FEE_DEDUCTION),

	// Purchase order
	CREATE_PURCHASE_ORDER("create_purchase_order", AuthorityCategoryConstant.PROCUREMENT, AuthoritySubCategoryConstant.PURCHASE_ORDER),
	UPDATE_PURCHASE_ORDER("update_purchase_order", AuthorityCategoryConstant.PROCUREMENT, AuthoritySubCategoryConstant.PURCHASE_ORDER),
	REMOVE_PURCHASE_ORDER("remove_purchase_order", AuthorityCategoryConstant.PROCUREMENT, AuthoritySubCategoryConstant.PURCHASE_ORDER),
	APPROVE_PURCHASE_ORDER("approve_purchase_order", AuthorityCategoryConstant.PROCUREMENT, AuthoritySubCategoryConstant.PURCHASE_ORDER),

	// Investment
	CREATE_INVOICE("create_investment", AuthorityCategoryConstant.PROCUREMENT, AuthoritySubCategoryConstant.PURCHASE_ORDER),
	REMOVE_INVOICE("remove_investment", AuthorityCategoryConstant.PROCUREMENT, AuthoritySubCategoryConstant.PURCHASE_ORDER),

	// Investment
	CREATE_SUPPLIER("create_investment", AuthorityCategoryConstant.PROCUREMENT, AuthoritySubCategoryConstant.PURCHASE_ORDER),
	REMOVE_SUPPLIER("remove_investment", AuthorityCategoryConstant.PROCUREMENT, AuthoritySubCategoryConstant.PURCHASE_ORDER),

	// Assert
	CREATE_ASSERT("create_assert", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.ASSERT),
	REMOVE_ASSERT("remove_assert", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.ASSERT),

	// Assert
	CREATE_LIABILITY("create_liability", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.LIABILITY),
	REMOVE_LIABILITY("remove_liability", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.LIABILITY),

	// Income
	CREATE_INCOME("create_income", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.INCOME),
	REMOVE_INCOME("remove_income", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.INCOME),

	// Investment
	CREATE_INVESTMENT("create_investment", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.INVESTMENT),
	REMOVE_INVESTMENT("remove_investment", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.INVESTMENT),

	// Expenses
	CREATE_EXPENSES("create_expenses", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.EXPENSES),
	REMOVE_EXPENSES("remove_expenses", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.EXPENSES),

	// Expenses
	CREATE_BANK_ACCOUNTS("create_bank_accounts", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.BANK_ACCOUNTS),
	REMOVE_BANK_ACCOUNTS("remove_bank_accounts", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.BANK_ACCOUNTS),

	// Expenses
	CREATE_BALANCE_SHEET("create_balance_sheet", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.BALANCE_SHEET),
	REMOVE_BALANCE_SHEET("remove_balance_sheet", AuthorityCategoryConstant.FINANCIALS, AuthoritySubCategoryConstant.BALANCE_SHEET),

	// Academics
	// Exams
	CREATE_EXAM("create_exam", AuthorityCategoryConstant.ACADEMICS, AuthoritySubCategoryConstant.EXAM),
	REMOVE_EXAM("remove_exam", AuthorityCategoryConstant.ACADEMICS, AuthoritySubCategoryConstant.EXAM),
	SCHEDULE_EXAM("schedule_exam", AuthorityCategoryConstant.ACADEMICS, AuthoritySubCategoryConstant.EXAM),

	CREATE_GRADE_SYSTEM("create_grade_system", AuthorityCategoryConstant.ACADEMICS, AuthoritySubCategoryConstant.GRADE_SYSTEM),
	REMOVE_GRADE_SYSTEM("remove_grade_system", AuthorityCategoryConstant.ACADEMICS, AuthoritySubCategoryConstant.GRADE_SYSTEM),

	CREATE_REPORT_CARD("create_report_card", AuthorityCategoryConstant.ACADEMICS, AuthoritySubCategoryConstant.GRADE_SYSTEM),
	REMOVE_REPORT_CARD("remove_report_card", AuthorityCategoryConstant.ACADEMICS, AuthoritySubCategoryConstant.GRADE_SYSTEM),

	NOTIFICATIONS_BRANCH_LEVEL("send_notifications_branch", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.NOTIFICATIONS),
	NOTIFICATIONS_CLASS_LEVEL("send_notifications_class", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.NOTIFICATIONS),
	NOTIFICATIONS_SECTION_LEVEL("send_notifications_section", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.NOTIFICATIONS),
	NOTIFICATIONS_STUDENT_LEVEL("send_notifications_student", AuthorityCategoryConstant.CORE, AuthoritySubCategoryConstant.NOTIFICATIONS);

	private static List<AuthorityConstant>	allRoles		= new ArrayList<AuthorityConstant>();

	private static List<AuthorityConstant>	allAuthorities	= new ArrayList<AuthorityConstant>();

	private String							label;

	private AuthorityCategoryConstant		authorityCategory;

	private AuthoritySubCategoryConstant	authoritySubCategory;

	static {
		// User Roles
		getAllRoles().add(ROLE_APEIRONSOL);
		getAllRoles().add(ROLE_ADMIN);
		getAllRoles().add(ROLE_EMPLOYEE);
		getAllRoles().add(ROLE_STUDENT);

		// Access
		getAllAuthorities().add(ACCESS_BRANCHS);
		getAllAuthorities().add(ACCESS_BUILDING_BLOCKS);
		getAllAuthorities().add(ACCESS_SECURITY);
		getAllAuthorities().add(ACCESS_SETTINGS);
		getAllAuthorities().add(ACCESS_BRANCH);

		// Students
		getAllAuthorities().add(ACCESS_ADMISSIONS);
		getAllAuthorities().add(ACCESS_STUDENTS);

		// Branch definitions
		getAllAuthorities().add(ACCESS_BRANCH_DEFINITIONS);
		getAllAuthorities().add(ACCESS_ACADEMIC_YEARS);
		getAllAuthorities().add(ACCESS_BATCHS);
		getAllAuthorities().add(ACCESS_CLASSES);
		getAllAuthorities().add(ACCESS_SECTIONS);

		// Financial's
		getAllAuthorities().add(ACCESS_FEE_DEFINITIONS);
		getAllAuthorities().add(ACCESS_FINANCIALS);
		getAllAuthorities().add(ACCESS_HRM_INFO);
		getAllAuthorities().add(ACCESS_STUDENT_FEE);
		getAllAuthorities().add(ACCESS_FEE_COLLECTED);
		getAllAuthorities().add(ACCESS_FINANCIALS_ASSERTS_AND_LIABILITIES);
		getAllAuthorities().add(ACCESS_FINANCIALS_PROCESS_FEE_TRANSACTIONS);
		getAllAuthorities().add(ACCESS_FINANCIALS_FEE_DETAILS);
		getAllAuthorities().add(ACCESS_FINANCIALS_INCOME_EXXPENSES);
		getAllAuthorities().add(ACCESS_FINANCIALS_PURCHASES);
		getAllAuthorities().add(ACCESS_FINANCIALS_INVOICE_SUPPLIERS);

		getAllAuthorities().add(ACCESS_FINANCIALS_BANK_ACCOUNTS);
		getAllAuthorities().add(ACCESS_FINANCIALS_CREDIT_ACCOUNTS);
		getAllAuthorities().add(ACCESS_FINANCIALS_BALANCE_SHEET);

		// Access Academics
		getAllAuthorities().add(ACCESS_ACADEMICS);
		getAllAuthorities().add(ACCESS_EXAMS);
		getAllAuthorities().add(ACCESS_REPORT_CARD);
		getAllAuthorities().add(CREATE_REPORT_CARD);
		getAllAuthorities().add(REMOVE_REPORT_CARD);

		// Access Transportation
		getAllAuthorities().add(ACCESS_TRANSPORTATION);
		getAllAuthorities().add(ACCESS_PICKUP_POINTS);
		getAllAuthorities().add(ACCESS_ROUTES);
		getAllAuthorities().add(ACCESS_VEHICLES);

		// Access Imports
		getAllAuthorities().add(ACCESS_IMPORTS);
		getAllAuthorities().add(ACCESS_IMPORT_ADMISSIONS);

		// Access Reports
		getAllAuthorities().add(ACCESS_REPORTS);

		// Access students
		getAllAuthorities().add(ACCESS_STUDENT_ADDRESS);
		getAllAuthorities().add(ACCESS_STUDENT_MEDICAL_HISTORY);
		getAllAuthorities().add(ACCESS_STUDENT_PARENT_DETAILS);
		getAllAuthorities().add(ACCESS_STUDENT_STATUS_HISTORY);
		getAllAuthorities().add(ACCESS_STUDENT_ATTENDANCE);
		getAllAuthorities().add(ACCESS_STUDENT_NOTIFICATIONS);
		getAllAuthorities().add(ACCESS_STUDENT_TRANSPORTATION);
		getAllAuthorities().add(ACCESS_STUDENT_ACADEMICS);
		getAllAuthorities().add(ACCESS_STUDENT_ACTIONS);

		// Update student details
		getAllAuthorities().add(UPDATE_STUDENT_PERSONAL_DETAILS);
		getAllAuthorities().add(UPDATE_STUDENT_ADDRESS);
		getAllAuthorities().add(UPDATE_STUDENT_MEDICAL_HISTORY);
		getAllAuthorities().add(UPDATE_STUDENT_PARENT_DETAILS);

		getAllAuthorities().add(PROMOTE_STUDENT);
		getAllAuthorities().add(TRANSFER_STUDENT);
		getAllAuthorities().add(ACCEPT_FOR_DROPOUT_STUDENT);
		getAllAuthorities().add(DROPOUT_STUDENT);
		getAllAuthorities().add(ROLLBACK_DROPOUT_STUDENT);
		getAllAuthorities().add(TERMINATE_STUDENT);

		// Access Attendance
		getAllAuthorities().add(ACCESS_ATTENDANCE);

		getAllAuthorities().add(ACCESS_NOTIFICATIONS);

		// Organization Authorities
		getAllAuthorities().add(CREATE_ORGANIZATION);
		getAllAuthorities().add(LOCK_ORGANIZATION);
		getAllAuthorities().add(UNLOCK_ORGANIZATION);

		// Branch
		getAllAuthorities().add(CREATE_BRANCH);
		getAllAuthorities().add(REMOVE_BRANCH);
		getAllAuthorities().add(ACTIVATE_BRANCH);
		getAllAuthorities().add(DEACTIVATE_BRANCH);

		getAllAuthorities().add(CREATE_BUILDING_BLOCK);
		getAllAuthorities().add(REMOVE_BUILDING_BLOCK);

		// User Management Authorities
		getAllAuthorities().add(CREATE_USER);
		getAllAuthorities().add(REMOVE_USER);

		getAllAuthorities().add(CREATE_USER_GROUP);
		getAllAuthorities().add(REMOVE_USER_GROUP);

		// Branch Level Authorities

		getAllAuthorities().add(CREATE_ACADEMIC_YEAR);
		getAllAuthorities().add(REMOVE_ACADEMIC_YEAR);
		getAllAuthorities().add(ACTIVATE_ACADEMIC_YEAR);
		getAllAuthorities().add(DEACTIVATE_ACADEMIC_YEAR);

		getAllAuthorities().add(CREATE_BATCH);
		getAllAuthorities().add(REMOVE_BATCH);
		getAllAuthorities().add(ACTIVATE_BATCH);
		getAllAuthorities().add(DEACTIVATE_BATCH);

		getAllAuthorities().add(CREATE_KLASS);
		getAllAuthorities().add(REMOVE_KLASS);
		getAllAuthorities().add(ACTIVATE_KLASS);
		getAllAuthorities().add(DEACTIVATE_KLASS);

		getAllAuthorities().add(CREATE_SECTION);
		getAllAuthorities().add(REMOVE_SECTION);
		getAllAuthorities().add(ACTIVATE_SECTION);
		getAllAuthorities().add(DEACTIVATE_SECTION);
		getAllAuthorities().add(CREATE_TIMETABLE);
		getAllAuthorities().add(REMOVE_TIMETABLE);
		getAllAuthorities().add(CREATE_ATTENDANCE);
		getAllAuthorities().add(REMOVE_ATTENDANCE);

		getAllAuthorities().add(CREATE_EMPLOYEE);
		getAllAuthorities().add(UPDATE_EMPLOYEE);
		getAllAuthorities().add(REMOVE_EMPLOYEE);
		getAllAuthorities().add(CREATE_EMPLOYEE_CTC);
		getAllAuthorities().add(REMOVE_EMPLOYEE_CTC);

		getAllAuthorities().add(CREATE_ADMISSION);
		getAllAuthorities().add(REVIEW_ADMISSION);
		getAllAuthorities().add(ACCEPT_ADMISSION);
		getAllAuthorities().add(ADMIT_ADMISSION);
		getAllAuthorities().add(REJECT_ADMISSION);
		getAllAuthorities().add(ROLLBACK_ADMISSION);
		getAllAuthorities().add(CREATE_WEEKEND);
		getAllAuthorities().add(REMOVE_WEEKEND);
		getAllAuthorities().add(CREATE_HOLIDAY);
		getAllAuthorities().add(REMOVE_HOLIDAY);

		// Fee Authorities
		getAllAuthorities().add(CREATE_FEE_DEFINITION);
		getAllAuthorities().add(REMOVE_FEE_DEFINITION);

		// Payments
		getAllAuthorities().add(PROCESS_FEE_PAYMENT);
		getAllAuthorities().add(PROCESS_FEE_REFUND);
		getAllAuthorities().add(PROCESS_FEE_DEDUCTION);
		getAllAuthorities().add(PROCESS_FEE_CANCELLATION);
		getAllAuthorities().add(PROCESS_STUDENT_POCKET_MONEY_WITHDRAW);
		getAllAuthorities().add(PROCESS_STUDENT_POCKET_MONEY_DEPOSIT);

		// Refund
		getAllAuthorities().add(CREATE_FEE_REFUND_REQUEST);
		getAllAuthorities().add(ACCEPT_FEE_REFUND_REQUEST);
		getAllAuthorities().add(REJECT_FEE_REFUND_REQUEST);

		// Deduction
		getAllAuthorities().add(CREATE_FEE_DEDUCTION_REQUEST);
		getAllAuthorities().add(ACCEPT_FEE_DEDUCTION_REQUEST);
		getAllAuthorities().add(REJECT_FEE_DEDUCTION_REQUEST);

		// Purchase order
		getAllAuthorities().add(CREATE_PURCHASE_ORDER);
		getAllAuthorities().add(UPDATE_PURCHASE_ORDER);
		getAllAuthorities().add(REMOVE_PURCHASE_ORDER);
		getAllAuthorities().add(APPROVE_PURCHASE_ORDER);

		// Supplier
		getAllAuthorities().add(CREATE_SUPPLIER);
		getAllAuthorities().add(REMOVE_SUPPLIER);

		// Supplier
		getAllAuthorities().add(CREATE_INVOICE);
		getAllAuthorities().add(REMOVE_INVOICE);

		// Assert
		getAllAuthorities().add(CREATE_ASSERT);
		getAllAuthorities().add(REMOVE_ASSERT);

		// liability
		getAllAuthorities().add(CREATE_LIABILITY);
		getAllAuthorities().add(REMOVE_LIABILITY);

		// Income
		getAllAuthorities().add(CREATE_INCOME);
		getAllAuthorities().add(REMOVE_INCOME);

		// Investment
		getAllAuthorities().add(CREATE_INVESTMENT);
		getAllAuthorities().add(REMOVE_INVESTMENT);

		// Expenses
		getAllAuthorities().add(CREATE_EXPENSES);
		getAllAuthorities().add(REMOVE_EXPENSES);

		// Expenses
		getAllAuthorities().add(CREATE_BANK_ACCOUNTS);
		getAllAuthorities().add(REMOVE_BANK_ACCOUNTS);

		// Expenses
		getAllAuthorities().add(CREATE_BALANCE_SHEET);
		getAllAuthorities().add(REMOVE_BALANCE_SHEET);

		getAllAuthorities().add(ACCESS_HOSTEL_ROOMS);
		getAllAuthorities().add(CREATE_HOSTEL_ROOM);
		getAllAuthorities().add(REMOVE_HOSTEL_ROOM);

		// Exams
		getAllAuthorities().add(CREATE_EXAM);
		getAllAuthorities().add(REMOVE_EXAM);
		getAllAuthorities().add(SCHEDULE_EXAM);

		getAllAuthorities().add(ACCESS_GRADE_SYSTEM);
		getAllAuthorities().add(REMOVE_GRADE_SYSTEM);
		getAllAuthorities().add(CREATE_GRADE_SYSTEM);

		// Notifications
		getAllAuthorities().add(NOTIFICATIONS_BRANCH_LEVEL);
		getAllAuthorities().add(NOTIFICATIONS_CLASS_LEVEL);
		getAllAuthorities().add(NOTIFICATIONS_SECTION_LEVEL);
		getAllAuthorities().add(NOTIFICATIONS_STUDENT_LEVEL);

		// Reports
		getAllAuthorities().add(ACCESS_STUDENT_INFO_REPORTS);
		getAllAuthorities().add(ACCESS_FINANCIAL_REPORTS);
	}

	AuthorityConstant(final String label, final AuthorityCategoryConstant authorityCategory, final AuthoritySubCategoryConstant authoritySubCategory) {
		this.label = label;
		this.authorityCategory = authorityCategory;
		this.setAuthoritySubCategory(authoritySubCategory);
	}

	@Override
	public String getLabel() {
		return ViewUtil.getEnumLabel(this.label);
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

	public static List<AuthorityConstant> getAllRoles() {
		return allRoles;
	}

	public static List<AuthorityConstant> getAllAuthorities() {
		return allAuthorities;
	}

	public AuthorityCategoryConstant getAuthorityCategory() {
		return this.authorityCategory;
	}

	public void setAuthorityCategory(final AuthorityCategoryConstant authorityCategory) {
		this.authorityCategory = authorityCategory;
	}

	public AuthoritySubCategoryConstant getAuthoritySubCategory() {
		return this.authoritySubCategory;
	}

	public void setAuthoritySubCategory(final AuthoritySubCategoryConstant authoritySubCategory) {
		this.authoritySubCategory = authoritySubCategory;
	}

	public static boolean isValidAuthorityConstant(final String authorityConstant) {

		for (final AuthorityConstant value : values()) {
			if (value.toString().equals(authorityConstant)) {
				return true;
			}
		}
		return false;

	}

}
