package com.apeironsol.need.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.apeironsol.need.procurement.model.PurchaseOrder;
import com.apeironsol.need.procurement.service.PurchaseOrderService;

@FacesConverter("purchaseOrderConverter")
public class PurchaseOrderConverter implements Converter {

	private final PurchaseOrderService	purchaseOrderService;

	public PurchaseOrderConverter() {
		WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		this.purchaseOrderService = ctx.getBean(PurchaseOrderService.class);
	}

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try {
			if (value != null && !StringUtils.isEmpty(value)) {
				return this.purchaseOrderService.findPurchaseOrderById(Long.valueOf(value));
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value != null && value instanceof PurchaseOrder) {
			return ((PurchaseOrder) value).getId().toString();
		} else {
			return null;
		}
	}

}
