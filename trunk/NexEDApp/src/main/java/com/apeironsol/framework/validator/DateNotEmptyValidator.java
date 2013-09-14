/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.framework.validator;

/**
 * validator for date not empty .
 * 
 * @author pradeep
 * 
 */
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateNotEmptyValidator implements ConstraintValidator<DateNotEmpty, Date> {

	@Override
	public void initialize(final DateNotEmpty constraintAnnotation) {

	}

	@Override
	public boolean isValid(final Date value, final ConstraintValidatorContext context) {
		if (value != null) {
			return true;
		}
		return false;
	}

}
