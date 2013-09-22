package com.apeironsol.need.util.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.need.util.constants.SectionReportsConstant;

@FacesConverter(value = "sectionReportTypeConstantConverter")
public class SectionReportTypeConstantConverter extends EnumConverter {

	public SectionReportTypeConstantConverter() {
		super(SectionReportsConstant.class);
	}

}
