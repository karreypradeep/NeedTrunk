package com.apeironsol.need.util.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.need.util.constants.StudentReportNamesConstant;

@FacesConverter(value = "jasperReportNamesConstantConverter")
public class JasperReportNamesConstantConverter extends EnumConverter {

	public JasperReportNamesConstantConverter() {
		super(StudentReportNamesConstant.class);
	}

}
