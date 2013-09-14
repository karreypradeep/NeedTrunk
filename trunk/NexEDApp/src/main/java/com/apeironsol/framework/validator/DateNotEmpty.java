/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.framework.validator;

/**
 * Validator for date not empty .
 * 
 * @author pradeep
 * 
 */
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = DateNotEmptyValidator.class)
@Documented
public @interface DateNotEmpty {

	String message() default "{Validation error: Date connot be null.}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
