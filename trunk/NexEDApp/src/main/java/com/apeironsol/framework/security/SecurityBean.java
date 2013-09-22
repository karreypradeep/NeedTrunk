package com.apeironsol.framework.security;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.SystemEvent;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.inject.Named;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;

import com.apeironsol.need.util.portal.ViewUtil;

@Named
@Scope(value = "session")
public class SecurityBean implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5047620381542708624L;

	private static final Logger	log					= Logger.getLogger(SecurityBean.class);

	public void checkListener(final SystemEvent event) {

		FacesContext fc = ViewUtil.getFacesContext();

		String loginPage = (String) fc.getExternalContext().getRequestMap().get("web.secfilter.authenticator.showLogin");
		if (StringUtils.isNotBlank(loginPage)) {
			this.doRedirect(fc, loginPage);

		}
	}

	/**
	 * Does a regular or AJAX redirect.
	 */
	public void doRedirect(final FacesContext fc, final String redirectPage) throws FacesException {
		ExternalContext ec = fc.getExternalContext();

		try {
			if (ec.isResponseCommitted()) {
				// redirect is not possible
				return;
			}

			// fix for renderer kit (Mojarra's and PrimeFaces's ajax redirect)
			if ((RequestContext.getCurrentInstance().isAjaxRequest() || fc.getPartialViewContext().isPartialRequest()) && fc.getResponseWriter() == null
					&& fc.getRenderKit() == null) {
				ServletResponse response = (ServletResponse) ec.getResponse();
				ServletRequest request = (ServletRequest) ec.getRequest();
				response.setCharacterEncoding(request.getCharacterEncoding());

				RenderKitFactory factory = (RenderKitFactory) FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);

				RenderKit renderKit = factory.getRenderKit(fc, fc.getApplication().getViewHandler().calculateRenderKitId(fc));

				ResponseWriter responseWriter = renderKit.createResponseWriter(response.getWriter(), null, request.getCharacterEncoding());
				fc.setResponseWriter(responseWriter);
			}

			ec.redirect(ec.getRequestContextPath() + (redirectPage != null ? redirectPage : ""));
		} catch (IOException e) {
			log.error("Redirect to the specified page '" + redirectPage + "' failed");
			throw new FacesException(e);
		}
	}

}
