package com.apeironsol.need.reporting.model;

import java.io.Serializable;
import java.util.Collection;

import com.apeironsol.need.core.model.Student;
import com.apeironsol.need.util.dataobject.StudentFinancialDO;

public class StudentReportBean implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5560885396943629920L;

	private String				studentName;

	private String				admissionNr;

	private Integer				totalAttendanceDays;

	private Double				attendanceFactor;

	private Integer				daysPresent;

	private Double				totalFee;

	private Double				waivedFee;

	private Double				feePaid;

	private Double				feeDue;

	private Double				refundFee;

	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return this.studentName;
	}

	/**
	 * @param studentName
	 *            the studentName to set
	 */
	public void setStudentName(final String studentName) {
		this.studentName = studentName;
	}

	/**
	 * @return the admissionNr
	 */
	public String getAdmissionNr() {
		return this.admissionNr;
	}

	/**
	 * @param admissionNr
	 *            the admissionNr to set
	 */
	public void setAdmissionNr(final String admissionNr) {
		this.admissionNr = admissionNr;
	}

	/**
	 * @return the totalDays
	 */
	public Integer getTotalAttendanceDays() {
		return this.totalAttendanceDays;
	}

	/**
	 * @param totalDays
	 *            the totalDays to set
	 */
	public void setTotalAttendanceDays(final Integer totalAttendanceDays) {
		this.totalAttendanceDays = totalAttendanceDays;
	}

	/**
	 * @return the attendanceFactor
	 */
	public Double getAttendanceFactor() {
		return this.attendanceFactor;
	}

	/**
	 * @param attendanceFactor
	 *            the attendanceFactor to set
	 */
	public void setAttendanceFactor(final Double attendanceFactor) {
		this.attendanceFactor = attendanceFactor;
	}

	/**
	 * @return the daysPresent
	 */
	public Integer getDaysPresent() {
		return this.daysPresent;
	}

	/**
	 * @param daysPresent
	 *            the daysPresent to set
	 */
	public void setDaysPresent(final Integer daysPresent) {
		this.daysPresent = daysPresent;
	}

	/**
	 * @return the totalFee
	 */
	public Double getTotalFee() {
		return this.totalFee;
	}

	/**
	 * @param totalFee
	 *            the totalFee to set
	 */
	public void setTotalFee(final Double totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * @return the waivedFee
	 */
	public Double getWaivedFee() {
		return this.waivedFee;
	}

	/**
	 * @param waivedFee
	 *            the waivedFee to set
	 */
	public void setWaivedFee(final Double waivedFee) {
		this.waivedFee = waivedFee;
	}

	/**
	 * @return the feePaid
	 */
	public Double getFeePaid() {
		return this.feePaid;
	}

	/**
	 * @param feePaid
	 *            the feePaid to set
	 */
	public void setFeePaid(final Double feePaid) {
		this.feePaid = feePaid;
	}

	/**
	 * @return the feeDue
	 */
	public Double getFeeDue() {
		return this.feeDue;
	}

	/**
	 * @param feeDue
	 *            the feeDue to set
	 */
	public void setFeeDue(final Double feeDue) {
		this.feeDue = feeDue;
	}

	/**
	 * @return the refundFee
	 */
	public Double getRefundFee() {
		return this.refundFee;
	}

	/**
	 * @param refundFee
	 *            the refundFee to set
	 */
	public void setRefundFee(final Double refundFee) {
		this.refundFee = refundFee;
	}

	/**
	 * Convert to StudentReportBean for financial report from student and
	 * StudentFinancialDO.
	 * 
	 * @param student
	 *            student.
	 * @param studentFinancialDOs
	 *            student financial data objects.
	 * @return StudentReportBean from student and StudentFinancialDO.
	 */
	public static StudentReportBean convertToStudentReportBeanForFinancialReport(final Student student,
			final Collection<StudentFinancialDO> studentFinancialDOs) {
		StudentReportBean studentReportBean = new StudentReportBean();
		studentReportBean.setAdmissionNr(student.getAdmissionNr());
		studentReportBean.setStudentName(student.getDisplayName());
		Double fee = 0.0d;
		Double feePaid = 0.0d;
		Double feeDue = 0.0d;
		Double feeDeducted = 0.0d;
		Double reFundFee = 0.0d;
		for (StudentFinancialDO studentFinancialDO : studentFinancialDOs) {
			fee += studentFinancialDO.getFee();
			feePaid += studentFinancialDO.getTotalFeePaymentAmount();
			feeDue += studentFinancialDO.getNetFeeDue();
			feeDeducted += studentFinancialDO.getTotalFeeDeductedAmount();
			reFundFee += studentFinancialDO.getTotalFeeRefundAmount();
		}
		studentReportBean.setFeeDue(feeDue);
		studentReportBean.setFeePaid(feePaid);
		studentReportBean.setTotalFee(fee);
		studentReportBean.setWaivedFee(feeDeducted);
		studentReportBean.setRefundFee(reFundFee);
		return studentReportBean;
	}

	/**
	 * Convert to StudentReportBean for financial report from student and
	 * StudentFinancialDO.
	 * 
	 * @param student
	 *            student.
	 * @param studentFinancialDOs
	 *            student financial data objects.
	 * @return StudentReportBean from student and StudentFinancialDO.
	 */
	public static StudentReportBean convertToStudentReportBeanForAttendanceReport(final Student student,
			final int totalNoOfDays, final int noOfAbsents) {
		StudentReportBean studentReportBean = new StudentReportBean();
		studentReportBean.setAdmissionNr(student.getAdmissionNr());
		studentReportBean.setStudentName(student.getDisplayName());
		studentReportBean.setTotalAttendanceDays(totalNoOfDays);
		studentReportBean.setDaysPresent(totalNoOfDays - noOfAbsents);
		studentReportBean.setAttendanceFactor((double) studentReportBean.getDaysPresent() / (double) totalNoOfDays);
		return studentReportBean;
	}
}
