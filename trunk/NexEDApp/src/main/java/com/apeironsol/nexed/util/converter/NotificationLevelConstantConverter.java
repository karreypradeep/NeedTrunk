package com.apeironsol.nexed.util.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.nexed.util.constants.NotificationLevelConstant;

@FacesConverter(value = "notificationLevelConstantConverter")
public class NotificationLevelConstantConverter extends EnumConverter {

	public NotificationLevelConstantConverter() {
		super(NotificationLevelConstant.class);
	}

}
