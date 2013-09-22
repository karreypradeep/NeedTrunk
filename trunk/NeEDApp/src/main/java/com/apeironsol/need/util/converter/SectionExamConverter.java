package com.apeironsol.need.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.apeironsol.need.academics.model.SectionExam;
import com.apeironsol.need.academics.service.SectionExamService;

@FacesConverter("sectionExamConverter")
public class SectionExamConverter implements Converter {

	private final SectionExamService	sectionExamService;

	public SectionExamConverter() {
		WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		this.sectionExamService = ctx.getBean(SectionExamService.class);
	}

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try {
			if (value != null && !StringUtils.isEmpty(value)) {
				return this.sectionExamService.findSectionExamById(Long.valueOf(value));
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value != null && value instanceof SectionExam) {
			return ((SectionExam) value).getId().toString();
		} else {
			return null;
		}
	}

}
