package com.apeironsol.need.util.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.need.util.constants.ViewContentConstant;

@FacesConverter(value = "viewContentConverter")
public class ViewContentConverter extends EnumConverter {

	public ViewContentConverter() {
		super(ViewContentConstant.class);
	}

}
