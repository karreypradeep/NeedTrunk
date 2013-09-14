/**
 * Alter columns
 */
ALTER TABLE ADDRESS CHANGE COLUMN MOBILE_NR MOBILE_NR VARCHAR(20) NULL;

drop table ADMISSION_RESERVATION_FEE;

drop table STUDENT_EXAM_SUBJECT;

drop table SECTION_EXAM_SUBJECT;

drop table SECTION_EXAM;

drop table EXAM;

drop table EXAM_TYPE;


/**
 * Schems updates for transportation.
 */
drop table STUDENT_TRANS_FEE_TXN;
drop table STUDENT_TRANSPORTATION_FEE;
drop table PICK_UP_POINT_FEE_PAYMENT;






