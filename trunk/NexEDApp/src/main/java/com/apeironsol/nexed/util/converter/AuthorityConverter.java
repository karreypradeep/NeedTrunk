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

import com.apeironsol.nexed.util.constants.AuthorityConstant;

@FacesConverter(value = "authorityConverter")
public class AuthorityConverter extends EnumConverter {

	public AuthorityConverter() {
		super(AuthorityConstant.class);
	}

}