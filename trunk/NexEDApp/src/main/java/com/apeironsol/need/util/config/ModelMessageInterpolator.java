/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.config;

import java.util.Locale;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.validation.MessageInterpolator;

public class ModelMessageInterpolator extends
		org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator implements MessageInterpolator {

	@SuppressWarnings("el-syntax")
	@Override
	public String interpolate(final String message, final Context context, final Locale locale) {
		// evaluate el expression
		if (message != null && message.startsWith("model.")) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Application app = facesContext.getApplication();
			ExpressionFactory elFactory = app.getExpressionFactory();
			ELContext elContext = facesContext.getELContext();
			ValueExpression valueExp = elFactory.createValueExpression(elContext, "#{" + message + "}", Object.class);
			Object value = valueExp.getValue(elContext);
			if (value == null) {
				return message;
			} else {
				return value.toString();
			}
		} else {
			return super.interpolate(message, context, locale);
		}
	}

}
