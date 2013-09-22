package com.apeironsol.need.security.portal;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.security.service.UserService;
import com.apeironsol.need.util.constants.UserAccountTypeConstant;
import com.apeironsol.need.util.portal.ViewExceptionHandler;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.ApplicationException;

@Named
@Scope("session")
public class RegistrationBean {

	private String					username;

	private String					password;

	private String					confirmPassword;

	private UserAccountTypeConstant	registrationType;

	private String					admissionNumber;

	private String					employeeNumber;

	private String					firstName;

	private String					lastName;

	private Date					dateOfBirth;

	private Date					childDateOfBirth;

	private boolean					confirmation;

	@Resource
	private UserService				userService;

	@PostConstruct
	public void init() {

	}

	public void reset() {

		this.username = null;

		this.password = null;

		this.confirmPassword = null;

		this.registrationType = null;

		this.admissionNumber = null;

		this.employeeNumber = null;

		this.firstName = null;

		this.lastName = null;

		this.dateOfBirth = null;

		this.confirmation = false;

	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public UserAccountTypeConstant getRegistrationType() {
		return this.registrationType;
	}

	public void setRegistrationType(final UserAccountTypeConstant registrationType) {
		this.registrationType = registrationType;
	}

	public String getAdmissionNumber() {
		return this.admissionNumber;
	}

	public void setAdmissionNumber(final String admissionNumber) {
		this.admissionNumber = admissionNumber;
	}

	public String getEmployeeNumber() {
		return this.employeeNumber;
	}

	public void setEmployeeNumber(final String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(final Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getChildDateOfBirth() {
		return this.childDateOfBirth;
	}

	public void setChildDateOfBirth(final Date childDateOfBirth) {
		this.childDateOfBirth = childDateOfBirth;
	}

	public void createNewAccount() {

		final String uresponse = ViewUtil.getHttpServletRequest().getParameter("recaptcha_response_field");

		if (uresponse == null || StringUtils.isEmpty(uresponse)) {
			ViewUtil.addMessage("Prove that you are human, fill captcha.", FacesMessage.SEVERITY_ERROR);
			return;
		}

		if (this.validateReCaptcha()) {

			try {

				if (UserAccountTypeConstant.STUDENT.equals(this.registrationType)) {
					this.registerStudent();
				} else if (UserAccountTypeConstant.EMPLOYEE.equals(this.registrationType)) {
					this.registerEmployee();
				} else if (UserAccountTypeConstant.PARENT.equals(this.registrationType)) {
					this.registerParent();
				} else {
					// Unexpected
					ViewUtil.addMessage("Unexpected user account type.", FacesMessage.SEVERITY_ERROR);
				}

				ViewUtil.addMessage("User registration is sucessfull.", FacesMessage.SEVERITY_INFO);

				this.confirmation = true;

			} catch (final ApplicationException e) {
				ViewExceptionHandler.handle(e);
				this.confirmation = false;

			} catch (final Exception e) {

				ViewExceptionHandler.handle(e);
				this.confirmation = false;

			}
		} else {
			ViewUtil.addMessage("Captcha does not match, please refill and submit the registration.", FacesMessage.SEVERITY_ERROR);
		}

	}

	private boolean validateReCaptcha() {

		final String remoteAddr = ViewUtil.getHttpServletRequest().getRemoteAddr();
		final ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
		reCaptcha.setPrivateKey("6LfKU94SAAAAAJf6lmoXHGP6hcPEfKWuTr-jiduW");

		final String challenge = ViewUtil.getHttpServletRequest().getParameter("recaptcha_challenge_field");

		final String uresponse = ViewUtil.getHttpServletRequest().getParameter("recaptcha_response_field");

		final ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

		return reCaptchaResponse.isValid();

	}

	private void registerStudent() {
		this.userService.creteUserAccountForStudent(this.username, this.confirmPassword, this.firstName, this.lastName, this.dateOfBirth, this.admissionNumber);
	}

	private void registerEmployee() {
		this.userService.creteUserAccountForEmployee(this.username, this.confirmPassword, this.firstName, this.lastName, this.dateOfBirth, this.employeeNumber);
	}

	private void registerParent() {
		this.userService.creteUserAccountForParent(this.username, this.confirmPassword, this.firstName, this.lastName, this.dateOfBirth, this.admissionNumber);
	}

	public boolean isConfirmation() {
		return this.confirmation;
	}

	public void setConfirmation(final boolean confirmation) {
		this.confirmation = confirmation;
	}

	public String getRecaptca() {

		final ReCaptcha recaptcha = ReCaptchaFactory.newReCaptcha("6LfKU94SAAAAABFpvRO7WOV93gISjlEq1hPVYn5C", "6LfKU94SAAAAAJf6lmoXHGP6hcPEfKWuTr-jiduW", true);

		return recaptcha.createRecaptchaHtml(null, null);
	}
}
