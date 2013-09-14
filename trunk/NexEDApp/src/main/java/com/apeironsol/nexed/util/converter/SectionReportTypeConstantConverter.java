package com.apeironsol.nexed.util.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.nexed.util.constants.SectionReportsConstant;

@FacesConverter(value = "sectionReportTypeConstantConverter")
public class SectionReportTypeConstantConverter extends EnumConverter {

	public SectionReportTypeConstantConverter() {
		super(SectionReportsConstant.class);
	}

}
