package com.apeironsol.need.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.apeironsol.need.financial.model.BranchBankAccount;
import com.apeironsol.need.financial.service.BranchBankAccountService;

@FacesConverter("branchBankAccountConverter")
public class BranchBankAccountConverter implements Converter {

	private final BranchBankAccountService	branchBankAccountService;

	public BranchBankAccountConverter() {
		WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		this.branchBankAccountService = ctx.getBean(BranchBankAccountService.class);
	}

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try {
			if (value != null && !StringUtils.isEmpty(value)) {
				return this.branchBankAccountService.findBranchBankAccountById(Long.valueOf(value));
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value != null && value instanceof BranchBankAccount) {
			return ((BranchBankAccount) value).getId().toString();
		} else {
			return null;
		}
	}

}
