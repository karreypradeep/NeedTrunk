package com.apeironsol.need.util.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.need.util.constants.AccessControlTypeConstant;

@FacesConverter(value = "accessControlTypeConverter")
public class AccessControlTypeConverter extends EnumConverter {

	public AccessControlTypeConverter() {
		super(AccessControlTypeConstant.class);
	}

}
