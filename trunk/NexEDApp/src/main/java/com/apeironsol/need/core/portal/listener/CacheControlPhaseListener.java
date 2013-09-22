package com.apeironsol.need.core.portal.listener;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CacheControlPhaseListener implements PhaseListener {
	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	@Override
	public void afterPhase(final PhaseEvent event) {
	}

	@Override
	public void beforePhase(final PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP
																					// 1.1.
		response.setDateHeader("Expires", 0); // Proxies.

		response.addHeader("Pragma", "no-cache");

		response.addHeader("Cache-Control", "no-cache");

		// Stronger according to blog comment below that references HTTP spec
		response.addHeader("Cache-Control", "no-store");

		response.addHeader("Cache-Control", "must-revalidate");
	}
}