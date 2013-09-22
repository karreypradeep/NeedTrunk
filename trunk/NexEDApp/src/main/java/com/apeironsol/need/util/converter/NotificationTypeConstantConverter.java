package com.apeironsol.need.util.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.need.util.constants.NotificationTypeConstant;

@FacesConverter(value = "notificationTypeConstantConverter")
public class NotificationTypeConstantConverter extends EnumConverter {

	public NotificationTypeConstantConverter() {
		super(NotificationTypeConstant.class);
	}

}
