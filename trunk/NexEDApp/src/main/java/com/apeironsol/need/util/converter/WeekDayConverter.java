package com.apeironsol.need.util.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.need.util.constants.WeekDayConstant;

@FacesConverter(value = "weekDayConverter")
public class WeekDayConverter extends EnumConverter {

	public WeekDayConverter() {
		super(WeekDayConstant.class);
	}

}
