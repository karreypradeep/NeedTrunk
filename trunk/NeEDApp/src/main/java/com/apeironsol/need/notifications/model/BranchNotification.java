/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.notifications.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.apeironsol.framework.BaseEntity;
import com.apeironsol.need.core.model.Branch;
import com.apeironsol.need.util.constants.NotificationSubTypeConstant;

/**
 * Entity class for branch notifications.
 * 
 * @author Pradeep
 */
@Entity
@Table(name = "BRANCH_NOTIFICATION")
public class BranchNotification extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class
	 */
	private static final long			serialVersionUID	= -256342062702481019L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private Branch						branch;

	@Basic
	@Column(name = "NOTIFI_SUB_TYPE", length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private NotificationSubTypeConstant	notificationSubType;

	@Column(name = "EMAIL_IND")
	private Boolean						emailIndicator;

	@Column(name = "SMS_IND")
	private Boolean						smsIndicator;

	@Column(name = "MIN_AMOUNT")
	private Double						minimumAmount;

	@Column(name = "CONTACT_NUMBERS", length = 225)
	private String						contactNumbers;

	@Column(name = "EMAIL_IDS", length = 225)
	private String						emailIDs;

	/**
	 * Schedule date.
	 */
	@Column(name = "SCHEDULE_DATE_SMS")
	@Temporal(TemporalType.DATE)
	private Date						scheduleDateSMSNotification;

	/**
	 * Schedule date.
	 */
	@Column(name = "FREQUENCY_MINUTES_SMS")
	private Integer						frequencyInMinutesSMSNotification;

	/**
	 * Schedule date.
	 */
	@Column(name = "SCHEDULE_DATE_EMAIL")
	@Temporal(TemporalType.DATE)
	private Date						scheduleDateEmailNotification;

	/**
	 * Schedule date.
	 */
	@Column(name = "FREQUENCY_MINUTES_EMAIL")
	private Integer						frequencyInMinutesEmailNotification;

	/**
	 * @return the notificationSubType
	 */
	public NotificationSubTypeConstant getNotificationSubType() {
		return this.notificationSubType;
	}

	/**
	 * @param notificationSubType
	 *            the notificationSubType to set
	 */
	public void setNotificationSubType(final NotificationSubTypeConstant notificationSubType) {
		this.notificationSubType = notificationSubType;
	}

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(final Branch branch) {
		this.branch = branch;
	}

	/**
	 * @return the emailIndicator
	 */
	public Boolean getEmailIndicator() {
		return this.emailIndicator;
	}

	/**
	 * @param emailIndicator
	 *            the emailIndicator to set
	 */
	public void setEmailIndicator(final Boolean emailIndicator) {
		this.emailIndicator = emailIndicator;
	}

	/**
	 * @return the smsIndicator
	 */
	public Boolean getSmsIndicator() {
		return this.smsIndicator;
	}

	/**
	 * @param smsIndicator
	 *            the smsIndicator to set
	 */
	public void setSmsIndicator(final Boolean smsIndicator) {
		this.smsIndicator = smsIndicator;
	}

	/**
	 * @return the minimumAmount
	 */
	public Double getMinimumAmount() {
		return this.minimumAmount;
	}

	/**
	 * @param minimumAmount
	 *            the minimumAmount to set
	 */
	public void setMinimumAmount(final Double minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	/**
	 * @return the contactNumbers
	 */
	public String getContactNumbers() {
		return this.contactNumbers;
	}

	/**
	 * @param contactNumbers
	 *            the contactNumbers to set
	 */
	public void setContactNumbers(final String contactNumbers) {
		this.contactNumbers = contactNumbers;
	}

	/**
	 * @return the emailIDs
	 */
	public String getEmailIDs() {
		return this.emailIDs;
	}

	/**
	 * @param emailIDs
	 *            the emailIDs to set
	 */
	public void setEmailIDs(final String emailIDs) {
		this.emailIDs = emailIDs;
	}

	/**
	 * @return the scheduleDateSMSNotification
	 */
	public Date getScheduleDateSMSNotification() {
		return this.scheduleDateSMSNotification;
	}

	/**
	 * @param scheduleDateSMSNotification
	 *            the scheduleDateSMSNotification to set
	 */
	public void setScheduleDateSMSNotification(final Date scheduleDateSMSNotification) {
		this.scheduleDateSMSNotification = scheduleDateSMSNotification;
	}

	/**
	 * @return the frequencyInMinutesSMSNotification
	 */
	public Integer getFrequencyInMinutesSMSNotification() {
		return this.frequencyInMinutesSMSNotification;
	}

	/**
	 * @param frequencyInMinutesSMSNotification
	 *            the frequencyInMinutesSMSNotification to set
	 */
	public void setFrequencyInMinutesSMSNotification(final Integer frequencyInMinutesSMSNotification) {
		this.frequencyInMinutesSMSNotification = frequencyInMinutesSMSNotification;
	}

	/**
	 * @return the scheduleDateEmailNotification
	 */
	public Date getScheduleDateEmailNotification() {
		return this.scheduleDateEmailNotification;
	}

	/**
	 * @param scheduleDateEmailNotification
	 *            the scheduleDateEmailNotification to set
	 */
	public void setScheduleDateEmailNotification(final Date scheduleDateEmailNotification) {
		this.scheduleDateEmailNotification = scheduleDateEmailNotification;
	}

	/**
	 * @return the frequencyInMinutesEmailNotification
	 */
	public Integer getFrequencyInMinutesEmailNotification() {
		return this.frequencyInMinutesEmailNotification;
	}

	/**
	 * @param frequencyInMinutesEmailNotification
	 *            the frequencyInMinutesEmailNotification to set
	 */
	public void setFrequencyInMinutesEmailNotification(final Integer frequencyInMinutesEmailNotification) {
		this.frequencyInMinutesEmailNotification = frequencyInMinutesEmailNotification;
	}

}
