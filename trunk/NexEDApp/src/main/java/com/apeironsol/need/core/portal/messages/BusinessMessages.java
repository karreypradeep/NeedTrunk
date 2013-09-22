package com.apeironsol.need.core.portal.messages;

public final class BusinessMessages {

	// User account messages
	public static final String	MSG_USER_ACCOUNT_USER_NAME_ALREADY_EXISTS														= "user_account_user_name_already_exists";

	// Branch messages
	public static final String	MSG_BRANCH_WITH_NAME_ALREADY_EXISTS																= "branch_with_name_already_exist";

	public static final String	MSG_MAXIMUM_FEE_CANNOT_BE_LESSTHEN_MINIMUM_FEE													= "maximum_fee_cannot_be_less_then_minimum_fee";

	public static final String	MSG_AMOUNT_CANNOT_NEGATIVE																		= "amount_cannot_negative";

	public static final String	MSG_START_DATE_SHOULD_BE_BEFORE_END_DATE														= "start_date_should_be_before_end_date";

	public static final String	MSG_START_DATE_MANDATORY																		= "start_date_mandatory";

	public static final String	MSG_BUILDBLOCK_IS_NOT_ASSEMBLED_TO_BRANCH														= "buildblock_is_not_assembled_to_branch";

	public static final String	MSG_BUILDBLOCK_IS_ASSEMBLED_TO_BRANCH															= "buildblock_is_assembled_to_branch";

	// Academic Years
	public static final String	MSG_CANNOT_DELETE_ACADEMIC_YEAR_SECTIONS_ASSOCIATED												= "cannot_delete_academic_year_sections_associated";

	public static final String	MSG_CANNOT_DELETE_ACADEMIC_YEAR_PICKPUP_POINTS_FEES_ASSOCIATED									= "cannot_delete_academic_year_pick_point_fees_associated";

	public static final String	MSG_NO_CLASS_DEFINED_FOR_BRANCH_TO_ACTIVATE_ACADEMIC_YEAR										= "no_classes_defind_for_branch_to_activate_academic_year";

	public static final String	MSG_ACADEMIC_YEAR_CANNOT_NOT_BE_ACTIVATED_KLASS_FEES_NOT_DEFINED								= "academic_year_cannot_be_activated_klass_fee_not_defined";

	public static final String	MSG_ACADEMIC_YEAR_CANNOT_NOT_BE_ACTIVATED_KLASS_FEES_PAYMENTS_NOT_DEFINED						= "academic_year_cannot_be_activated_klass_fee_payments_not_defined";

	public static final String	MSG_ACADEMIC_YEAR_CANNOT_NOT_BE_ACTIVATED_KLASS_SUBJECTS_NOT_DEFINED							= "academic_year_cannot_be_activated_klass_subjects_not_defined";

	public static final String	MSG_ACADEMIC_YEAR_CANNOT_NOT_BE_ACTIVATED_SECTIONS_ARE_NOT_DEFINED_FOR_KLASS_FOR_ACADEMIC_YEAR	= "academic_year_cannot_be_activated_sections_are_not_defined_for_klass_for_academic_year";

	public static final String	MSG_ACADEMIC_YEAR_CANNOT_NOT_BE_ACTIVATED_SECTIONS_SUBJECTS_ARE_NOT_DEFINED						= "academic_year_cannot_be_activated_sections_subjects_are_not_defined";

	// Class
	public static final String	MSG_CANNOT_DELETE_KLASS_SECTIONS_ASSOCIATED														= "cannot_delete_klass_sections_associated";

	public static final String	MSG_CANNOT_DELETE_KLASS_KLASS_FEES_ASSOCIATED													= "cannot_delete_klass_klass_fee_associated";

	public static final String	MSG_CANNOT_DELETE_KLASS_SUBJECTS_ASSOCIATED														= "cannot_delete_klass_subjects_associated";

	public static final String	MSG_KLASS_CANNOT_BE_ACTIVATED_ADD_KLASS_FEES													= "klass_cannot_be_activated_klass_fees_missing";

	public static final String	MSG_KLASS_CANNOT_NOT_BE_ACTIVATED_KLASS_FEES_PAYMENTS_NOT_DEFINED								= "klass_cannot_be_activated_klass_fee_payments_not_defined";

	public static final String	MSG_KLASS_CANNOT_NOT_BE_ACTIVATED_KLASS_SUBJECTS_NOT_DEFINED									= "klass_cannot_be_activated_klass_subjects_not_defined";

	public static final String	MSG_KLASS_CANNOT_NOT_BE_ACTIVATED_SECTIONS_ARE_NOT_DEFINED										= "klass_cannot_be_activated_as_sections_are_not_defined";

	// Section
	public static final String	MSG_CANNOT_DELETE_SECTION_STUDENTS_ASSOCIATED													= "cannot_delete_section_students_associated";

	public static final String	MSG_CANNOT_DELETE_SECTION_SUBJECTS_ASSOCIATED													= "cannot_delete_section_subjects_associated";

	public static final String	MSG_SECTION_CANNOT_BE_ACTIVATED_SECTIONS_SUBJECTS_ARE_NOT_DEFINED								= "section_cannot_be_activated_section_subjects_are_not_defined";

	public static final String	MSG_SECTION_CANNOT_BE_ACTIVATED_SECTION_TEACHERS_NOT_DEFINED									= "section_cannot_be_activated_section_teachers_not_defined";

	public static final String	MSG_CANNOT_DELETE_SECTION_TEACHERS_ASSOCIATED													= "cannot_delete_section_teachers_associated";

	public static final String	MSG_CANNOT_DELETE_SECTION_TIMETABLE_ASSOCIATED													= "cannot_delete_section_timetable_associated";

	public static final String	MSG_CANNOT_DELETE_SECTION_EXAMS_ASSOCIATED														= "cannot_delete_section_exams_associated";

	// Build block
	public static final String	MSG_CODE_ALREADY_EXIST_FOR_BUILDING_BLOCK_TYPE													= "code_already_exist_for_building_block_type";

	// Transportation
	public static final String	MSG_CODE_ALREADY_EXIST_FOR_PICKUP_POINT															= "code_already_exist_for_pickup_point";

	public static final String	MSG_NAME_ALREADY_EXIST_FOR_PICKUP_POINT															= "name_already_exist_for_pickup_point";

	public static final String	MSG_VEHICLE_NUMER_ALREADY_EXIST_FOR_VEHICLE														= "vehicle_numer_already_exist_for_vehicle";

	public static final String	MSG_ACADEMIC_YEAR_IS_OVERLAPPING																= "academic_year_is_overlapping";

	// Payments
	public static final String	MSG_PAYMENT_AMOUNT_SHOULD_NOT_BE_GREATER_THAN_THE_DUE_AMOUNT									= "payment_amount_should_not_be_greaterthan_the_due_amount";

	public static final String	MSG_REFUND_AMOUNT_SHOULD_NOT_BE_GREATER_THAN_THE_TOTOAL_AMOUNT									= "refund_amount_should_not_be_greaterthan_the_total_amount";

	public static final String	MSG_DEDUCTING_AMOUNT_SHOULD_NOT_BE_GREATER_THAN_THE_TOTOAL_AMOUNT								= "deducting_amount_should_not_be_greaterthan_the_total_amount";

	// Transportation
	public static final String	MSG_PICKUPPOINT_CANNOT_BE_ACTIVATED_ADD_PICKUPPOINT_FEES										= "pickuppoint_cannot_be_activated_pickuppoint_fees_missing";

	public static final String	MSG_PICKUPPOINT_CANNOT_NOT_BE_ACTIVATED_PICKUPPOINT_FEES_PAYMENTS_NOT_DEFINED					= "pickuppoint_cannot_be_activated_pickuppoint_fee_payments_not_defined";

	public static final String	MSG_CANNOT_DELETE_PICKUPPOINT_ROUTES_ASSOCIATED													= "cannot_delete_pickuppoint_routes_associated";

	public static final String	MSG_CANNOT_DELETE_ROUTE_VEHICLES_ASSOCIATED														= "cannot_delete_route_vehicles_associated";

	// Section timetable
	/**
	 * Message for start date and end date cannot be empty.
	 */
	public static final String	MSG_START_DATE_END_DATE_CANNOT_BE_EMPTY															= "start_date_end_date_cannot_be_empty";

	/** Message for start date and end date cannot be empty. */
	public static final String	MSG_START_TIME_END_TIME_CANNOT_BE_EMPTY															= "start_time_end_time_cannot_be_empty";

	/** Message for schedule end time cannot be before start time. */
	public static final String	MSG_SCHEDULE_END_TIME_CANNOT_BE_BEFORE_START_TIME												= "schedule_end_time_cannot_be_before_start_time";

	/** Message for schedule end date cannot be before start date. */
	public static final String	MSG_SCHEDULE_END_DATE_CANNOT_BE_BEFORE_START_DATE												= "schedule_end_date_cannot_be_before_start_date";

	/** Message for overlapping schedule for section. */
	public static final String	MSG_OVERLAPPING_SCHEDULE_FOR_SECTION															= "overlapping_schedule_for_section";

	/** Message for overlapping schedule for teacher. */
	public static final String	MSG_OVERLAPPING_SCHEDULE_FOR_TEACHER															= "overlapping_schedule_for_teacher";

	/** Message for overlapping schedule for teacher. */
	public static final String	NON_NULL_ARGUMENT_CONTAINS_NULL																	= "non_null_argument_contains_null";

	/** Message for overlapping schedule for teacher. */
	public static final String	MSG_AMOUNT_SHOULD_BE_GREATER_THAN_ZERO															= "msg_amount_should_be_greater_than_zero";

	/** Message for overlapping schedule for teacher. */
	public static final String	MSG_INSUFFICIENT_POCKET_MONEY_TO_WITH_DRAW														= "msg_insufficient_pocket_money_to_with_draw";

	/** Message for no sections defined for academic year. */
	public static final String	MSG_NO_SECTIONS_DEFINED_FOR_ACADEMIC_YEAR														= "msg_no_sections_defined_for_academic_year";

	public static final String	MSG_CANNOT_DELETE_STUDENT_LEVEL_FEE_AS_TRANSACTION_FOUND										= "cannot_delete_student_level_fee_as_transaction_found";

	/**
	 * This class only contains labels in resource bundles and should therefore
	 * not be instantiated.
	 */
	private BusinessMessages() {
	}

	/**
	 * Returns the name of this resource bundle messages class.
	 * 
	 * @return The name of this resource bundle messages class.
	 */
	public static String getResourceBundleName() {
		return "businessmsg";
	}

}
