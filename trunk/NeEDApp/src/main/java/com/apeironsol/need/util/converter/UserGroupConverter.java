/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.need.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.apeironsol.need.security.model.UserGroup;
import com.apeironsol.need.security.portal.UserAccountBean;

@FacesConverter(value = "userGroupConverter")
public class UserGroupConverter implements Converter {

	@Override
	public Object getAsObject(final FacesContext facesContext, final UIComponent uiComponent, final String arg1) {
		return this.getUserGroupById(Long.valueOf(arg1));
	}

	@Override
	public String getAsString(final FacesContext arg0, final UIComponent arg1, final Object arg2) {
		String str = "";
		if (arg2 != null && arg2 instanceof UserGroup) {
			str = "" + ((UserGroup) arg2).getId().toString();
		}
		return str;
	}

	private UserGroup getUserGroupById(final Long userId) {
		UserAccountBean userAccountBean = (UserAccountBean) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("userAccountBean");
		UserGroup usergroup = userAccountBean.getUserGroupById(userId);
		return usergroup;
	}

}
