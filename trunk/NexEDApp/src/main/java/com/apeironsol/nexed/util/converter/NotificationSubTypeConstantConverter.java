package com.apeironsol.nexed.util.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.nexed.util.constants.NotificationSubTypeConstant;

@FacesConverter(value = "notificationSubTypeConstantConverter")
public class NotificationSubTypeConstantConverter extends EnumConverter {

	public NotificationSubTypeConstantConverter() {
		super(NotificationSubTypeConstant.class);
	}

}
