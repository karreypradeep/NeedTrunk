/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.nexed.util.converter;

/**
 * class for Gender converter
 * 
 * @author Pradeep
 */
import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.nexed.util.constants.GenderConstant;

@FacesConverter(value = "genderConverter")
public class GenderConverter extends EnumConverter {

	public GenderConverter() {
		super(GenderConstant.class);
	}

}