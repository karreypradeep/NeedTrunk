package com.apeironsol.need.util.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.need.util.constants.AdmissionStatusConstant;

@FacesConverter(value = "admissionStatusConverter")
public class AdmissionStatusConverter extends EnumConverter {

	public AdmissionStatusConverter() {
		super(AdmissionStatusConstant.class);
	}

}
