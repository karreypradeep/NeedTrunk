/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.converter;

/**
 * class for StudentFeeTransactionTypeConverter
 * 
 * @author Pradeep
 */
import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.need.util.constants.StudentFeeTransactionTypeConstant;

@FacesConverter(value = "studentFeeTransactionTypeConverter")
public class StudentFeeTransactionTypeConverter extends EnumConverter {

	public StudentFeeTransactionTypeConverter() {
		super(StudentFeeTransactionTypeConstant.class);
	}

}