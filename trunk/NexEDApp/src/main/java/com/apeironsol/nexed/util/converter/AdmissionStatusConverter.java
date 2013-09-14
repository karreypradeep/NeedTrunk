package com.apeironsol.nexed.util.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.nexed.util.constants.AdmissionStatusConstant;

@FacesConverter(value = "admissionStatusConverter")
public class AdmissionStatusConverter extends EnumConverter {

	public AdmissionStatusConverter() {
		super(AdmissionStatusConstant.class);
	}

}
