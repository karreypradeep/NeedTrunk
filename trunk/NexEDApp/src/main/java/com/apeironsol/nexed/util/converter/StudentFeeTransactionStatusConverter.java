/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.util.converter;

/**
 * class for StudentFeeTransactionTypeConverter
 * 
 * @author Pradeep
 */
import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.nexed.util.constants.StudentFeeTransactionStatusConstant;

@FacesConverter(value = "studentFeeTransactionStatusConverter")
public class StudentFeeTransactionStatusConverter extends EnumConverter {

	public StudentFeeTransactionStatusConverter() {
		super(StudentFeeTransactionStatusConstant.class);
	}

}