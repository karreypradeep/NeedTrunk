package com.apeironsol.need.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "SMS_PROVIDER")
public class SMSProvider extends BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 1447572358507574855L;

	@NotEmpty(message = "model.sms_provider_name_mandatory")
	@Column(name = "SMS_PROVIDER_NAME", nullable = false, length = 225, unique = true)
	private String				smsProviderName;

	@NotEmpty(message = "model.schema_mandatory")
	@Column(name = "SCHEME", nullable = false, length = 20)
	private String				scheme;

	@NotEmpty(message = "model.host_mandatory")
	@Column(name = "HOST", nullable = false, length = 225)
	private String				host;

	@NotEmpty(message = "model.path_mandatory")
	@Column(name = "PATH", nullable = false, length = 225)
	private String				path;

	@NotEmpty(message = "model.username_key_mandatory")
	@Column(name = "USERNAME_KEY", nullable = false, length = 225)
	private String				userNameKey;

	@NotEmpty(message = "model.username_mandatory")
	@Column(name = "USERNAME", nullable = false, length = 225)
	private String				userName;

	@NotEmpty(message = "model.PASSWORD_KEY_mandatory")
	@Column(name = "PASSWORD_KEY", nullable = false, length = 225)
	private String				passwordKey;

	@NotEmpty(message = "model.password_mandatory")
	@Column(name = "PASSWORD", nullable = false, length = 225)
	private String				password;

	@NotEmpty(message = "model.sender_id_key_mandatory")
	@Column(name = "SENDER_ID_KEY", nullable = false, length = 225)
	private String				senderIdKey;

	@NotEmpty(message = "model.sender_id_key_mandatory")
	@Column(name = "SENDER_ID", nullable = false, length = 225)
	private String				senderId;

	@NotEmpty(message = "model.to_key_mandatory")
	@Column(name = "TO_KEY", nullable = false, length = 225)
	private String				toKey;

	@NotEmpty(message = "model.message_key_mandatory")
	@Column(name = "MESSAGE_KEY", nullable = false, length = 225)
	private String				messageKey;

	@NotEmpty(message = "model.route_key_mandatory")
	@Column(name = "ROUTE_KEY", nullable = false, length = 225)
	private String				routeKey;

	@NotEmpty(message = "model.success_string")
	@Column(name = "SUCCESS_STRING", nullable = false, length = 225)
	private String				successString;

	@NotEmpty(message = "model.route_mandatory")
	@Column(name = "ROUTE", nullable = false, length = 225)
	private String				route;

	@Column(name = "ADDI_KEY1", length = 225)
	private String				additionalParameter1Key;

	@Column(name = "ADDI_VALUE1", length = 225)
	private String				additionalParameter1Value;

	@Column(name = "USE_ADD1_IND")
	private boolean				useAdditionalParameter1;

	@Column(name = "ADDI_KEY2", length = 225)
	private String				additionalParameter2Key;

	@Column(name = "ADDI_VALUE2", length = 225)
	private String				additionalParameter2Value;

	@Column(name = "USE_ADD2_IND")
	private boolean				useAdditionalParameter2;

	@Column(name = "MAX_NO_OF_CHARS_PER_SMS")
	private Integer				maximumNoOfCharactersPerSMS;

	public String getSmsProviderName() {
		return this.smsProviderName;
	}

	public void setSmsProviderName(final String smsProviderName) {
		this.smsProviderName = smsProviderName;
	}

	public String getUserNameKey() {
		return this.userNameKey;
	}

	public void setUserNameKey(final String userNameKey) {
		this.userNameKey = userNameKey;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getPasswordKey() {
		return this.passwordKey;
	}

	public void setPasswordKey(final String passwordKey) {
		this.passwordKey = passwordKey;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getSenderIdKey() {
		return this.senderIdKey;
	}

	public void setSenderIdKey(final String senderIdKey) {
		this.senderIdKey = senderIdKey;
	}

	public String getSenderId() {
		return this.senderId;
	}

	public void setSenderId(final String senderId) {
		this.senderId = senderId;
	}

	public String getToKey() {
		return this.toKey;
	}

	public void setToKey(final String toKey) {
		this.toKey = toKey;
	}

	public String getMessageKey() {
		return this.messageKey;
	}

	public void setMessageKey(final String messageKey) {
		this.messageKey = messageKey;
	}

	public String getRouteKey() {
		return this.routeKey;
	}

	public void setRouteKey(final String routeKey) {
		this.routeKey = routeKey;
	}

	public String getRoute() {
		return this.route;
	}

	public void setRoute(final String route) {
		this.route = route;
	}

	public String getScheme() {
		return this.scheme;
	}

	public void setScheme(final String scheme) {
		this.scheme = scheme;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(final String host) {
		this.host = host;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	/**
	 * @return the additionalParameter1Key
	 */
	public String getAdditionalParameter1Key() {
		return this.additionalParameter1Key;
	}

	/**
	 * @param additionalParameter1Key
	 *            the additionalParameter1Key to set
	 */
	public void setAdditionalParameter1Key(final String additionalParameter1Key) {
		this.additionalParameter1Key = additionalParameter1Key;
	}

	/**
	 * @return the additionalParameter1Value
	 */
	public String getAdditionalParameter1Value() {
		return this.additionalParameter1Value;
	}

	/**
	 * @param additionalParameter1Value
	 *            the additionalParameter1Value to set
	 */
	public void setAdditionalParameter1Value(final String additionalParameter1Value) {
		this.additionalParameter1Value = additionalParameter1Value;
	}

	/**
	 * @return the useAdditionalParameter1
	 */
	public boolean isUseAdditionalParameter1() {
		return this.useAdditionalParameter1;
	}

	/**
	 * @param useAdditionalParameter1
	 *            the useAdditionalParameter1 to set
	 */
	public void setUseAdditionalParameter1(final boolean useAdditionalParameter1) {
		this.useAdditionalParameter1 = useAdditionalParameter1;
	}

	/**
	 * @return the additionalParameter2Key
	 */
	public String getAdditionalParameter2Key() {
		return this.additionalParameter2Key;
	}

	/**
	 * @param additionalParameter2Key
	 *            the additionalParameter2Key to set
	 */
	public void setAdditionalParameter2Key(final String additionalParameter2Key) {
		this.additionalParameter2Key = additionalParameter2Key;
	}

	/**
	 * @return the additionalParameter2Value
	 */
	public String getAdditionalParameter2Value() {
		return this.additionalParameter2Value;
	}

	/**
	 * @param additionalParameter2Value
	 *            the additionalParameter2Value to set
	 */
	public void setAdditionalParameter2Value(final String additionalParameter2Value) {
		this.additionalParameter2Value = additionalParameter2Value;
	}

	/**
	 * @return the useAdditionalParameter2
	 */
	public boolean isUseAdditionalParameter2() {
		return this.useAdditionalParameter2;
	}

	/**
	 * @param useAdditionalParameter2
	 *            the useAdditionalParameter2 to set
	 */
	public void setUseAdditionalParameter2(final boolean useAdditionalParameter2) {
		this.useAdditionalParameter2 = useAdditionalParameter2;
	}

	/**
	 * @return the successString
	 */
	public String getSuccessString() {
		return this.successString;
	}

	/**
	 * @param successString
	 *            the successString to set
	 */
	public void setSuccessString(final String successString) {
		this.successString = successString;
	}

	/**
	 * @return the maximumNoOfCharactersPerSMS
	 */
	public Integer getMaximumNoOfCharactersPerSMS() {
		return this.maximumNoOfCharactersPerSMS;
	}

	/**
	 * @param maximumNoOfCharactersPerSMS
	 *            the maximumNoOfCharactersPerSMS to set
	 */
	public void setMaximumNoOfCharactersPerSMS(final Integer maximumNoOfCharactersPerSMS) {
		this.maximumNoOfCharactersPerSMS = maximumNoOfCharactersPerSMS;
	}

}
