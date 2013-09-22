/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */

package com.apeironsol.need.util.portal;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.transaction.RollbackException;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.util.Assert;

import com.apeironsol.framework.BaseEntity;
import com.apeironsol.framework.exception.ApplicationException;
import com.apeironsol.framework.exception.BusinessException;
import com.apeironsol.framework.exception.InvalidArgumentException;
import com.apeironsol.framework.exception.SystemException;

/**
 * Utility class for exception handling.
 * 
 * @author Pradeep
 * @author Pradeep
 */
public final class ViewExceptionHandler {

	private static final Logger	log	= Logger.getLogger(ViewExceptionHandler.class);

	/**
	 * Handles the given portal exception.
	 * 
	 * @param applicationException
	 *            The exception to handle, may be <code>null</code> .
	 */
	public static final void handle(final ApplicationException applicationException) {
		handle(applicationException, FacesMessage.SEVERITY_ERROR);
	}

	/**
	 * 
	 * @param applicationException
	 * @param locale
	 */
	public static final void handle(final ApplicationException applicationException, final Locale locale) {
		handle(applicationException, locale, FacesMessage.SEVERITY_ERROR);
	}

	/**
	 * 
	 * @param applicationException
	 * @param severity
	 */
	public static final void handle(final ApplicationException applicationException, final Severity severity) {
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		handle(applicationException, locale, severity);
	}

	/**
	 * 
	 * @param exception
	 * @param locale
	 * @param severity
	 */
	private static final void handle(final ApplicationException exception, final Locale locale, final Severity severity) {
		Assert.notNull(exception);
		Assert.notNull(locale);
		Assert.notNull(severity);

		if (exception instanceof BusinessException) {

			log.info(exception.getMessage(), exception);

			ViewUtil.addMessage(exception.getMessage(locale), severity);

		} else if (exception instanceof InvalidArgumentException) {

			log.error(exception.getMessage(), exception);

			ViewUtil.addMessage(exception.getMessage(locale), severity);

		} else if (exception instanceof SystemException) {

			log.error(exception.getMessage(), exception);

			ViewUtil.addMessage(exception.getMessage(locale), severity);
		} else {

			log.error(exception.getMessage(), exception);

			ViewUtil.addMessage(exception.getMessage(locale), severity);
		}
	}

	/**
	 * 
	 * @param exception
	 * @param locale
	 * @param severity
	 */
	public static final void handle(final Throwable exception) {
		Assert.notNull(exception);

		log.error(exception.getMessage(), exception);

		if (exception instanceof OptimisticLockException) {

			OptimisticLockException ex = (OptimisticLockException) exception;

			String klassName = ex.getEntity().getClass().getName();

			klassName = klassName.substring(klassName.lastIndexOf(".") + 1, klassName.length());

			String message = klassName + ": Row " + ((BaseEntity) ex.getEntity()).getId()
					+ " was updated or deleted by another transaction.";

			ViewUtil.addMessage(message, FacesMessage.SEVERITY_ERROR);

		} else if (exception instanceof UnexpectedRollbackException) {

			Throwable e = exception.getCause();

			if (e != null && e instanceof RollbackException) {

				e = e.getCause();

				if (e != null && e instanceof PersistenceException) {

					Throwable e1 = e.getCause();

					if (e1 != null && e1 instanceof ConstraintViolationException) {

						ViewUtil.addMessage(e1.getMessage(), FacesMessage.SEVERITY_ERROR);
					} else {
						ViewUtil.addMessage(e.getMessage(), FacesMessage.SEVERITY_ERROR);
					}

				} else {
					ViewUtil.addMessage(exception.getStackTrace().toString(), FacesMessage.SEVERITY_ERROR);
				}
			} else {
				ViewUtil.addMessage(exception.getStackTrace().toString(), FacesMessage.SEVERITY_ERROR);
			}
		} else {
			ViewUtil.addMessage(exception.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
}
