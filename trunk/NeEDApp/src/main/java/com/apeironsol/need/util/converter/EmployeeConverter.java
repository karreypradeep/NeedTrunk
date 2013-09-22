package com.apeironsol.need.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.apeironsol.need.hrms.model.Employee;
import com.apeironsol.need.hrms.service.EmployeeService;

@FacesConverter("employeeConverter")
public class EmployeeConverter implements Converter {

	private final EmployeeService	employeeService;

	public EmployeeConverter() {
		WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		this.employeeService = ctx.getBean(EmployeeService.class);
	}

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try {
			if (value != null && !StringUtils.isEmpty(value)) {
				return this.employeeService.findEmployeeById(Long.valueOf(value));
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value != null && value instanceof Employee) {
			return ((Employee) value).getId().toString();
		} else {
			return null;
		}
	}

}
