package com.apeironsol.nexed.util.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.nexed.util.constants.NotificationTypeConstant;

@FacesConverter(value = "notificationTypeConstantConverter")
public class NotificationTypeConstantConverter extends EnumConverter {

	public NotificationTypeConstantConverter() {
		super(NotificationTypeConstant.class);
	}

}
