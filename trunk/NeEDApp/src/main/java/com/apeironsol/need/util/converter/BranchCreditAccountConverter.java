package com.apeironsol.need.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.apeironsol.need.financial.model.BranchCreditAccount;
import com.apeironsol.need.financial.service.BranchCreditAccountService;

@FacesConverter("branchCreditAccountConverter")
public class BranchCreditAccountConverter implements Converter {

	private final BranchCreditAccountService	branchCreditAccountService;

	public BranchCreditAccountConverter() {
		final WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		this.branchCreditAccountService = ctx.getBean(BranchCreditAccountService.class);
	}

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try {
			if (value != null && !StringUtils.isEmpty(value)) {
				return this.branchCreditAccountService.findBranchCreditAccountById(Long.valueOf(value));
			} else {
				return null;
			}
		} catch (final Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value != null && value instanceof BranchCreditAccount) {
			return ((BranchCreditAccount) value).getId().toString();
		} else {
			return null;
		}
	}

}
