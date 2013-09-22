/**
 * 
 */
package com.apeironsol.need.transportation.portal.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.apeironsol.need.transportation.model.Vehicle;
import com.apeironsol.need.transportation.service.VehicleService;

/**
 * @author Sunny
 * 
 */
@FacesConverter(value = "vehicleConverter")
public class VehicleConverter implements Converter {

	private final VehicleService	vehicleService;

	public VehicleConverter() {
		WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		this.vehicleService = ctx.getBean(VehicleService.class);
	}

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try {
			if (value != null && !StringUtils.isEmpty(value)) {
				return this.vehicleService.findVehicleById(Long.valueOf(value));
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value != null && value instanceof Vehicle) {
			return ((Vehicle) value).getId().toString();
		} else {
			return null;
		}
	}

}
