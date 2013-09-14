package com.apeironsol.nexed.util.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.nexed.util.constants.ViewContentConstant;

@FacesConverter(value = "viewContentConverter")
public class ViewContentConverter extends EnumConverter {

	public ViewContentConverter() {
		super(ViewContentConstant.class);
	}

}
