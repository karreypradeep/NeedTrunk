package com.apeironsol.need.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.apeironsol.need.academics.model.SectionExamSubject;
import com.apeironsol.need.academics.service.SectionExamSubjectService;

@FacesConverter("sectionExamSubjectConverter")
public class SectionExamSubjectConverter implements Converter {

	private final SectionExamSubjectService	sectionExamSubjectService;

	public SectionExamSubjectConverter() {
		WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
		this.sectionExamSubjectService = ctx.getBean(SectionExamSubjectService.class);
	}

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try {
			if (value != null && !StringUtils.isEmpty(value)) {
				return this.sectionExamSubjectService.findSectionExamSubjectById(Long.valueOf(value));
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value != null && value instanceof SectionExamSubject) {
			return ((SectionExamSubject) value).getId().toString();
		} else {
			return null;
		}
	}

}
