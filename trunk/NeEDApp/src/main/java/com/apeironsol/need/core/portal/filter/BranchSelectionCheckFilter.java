package com.apeironsol.need.core.portal.filter;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.apeironsol.need.core.portal.SessionBean;
import com.apeironsol.need.security.portal.BranchesAppStatusBean;
import com.apeironsol.need.util.DeviceDetectorBean;

@Repository
public class BranchSelectionCheckFilter extends GenericFilterBean {

	private static final Logger	logger	= Logger.getLogger(BranchSelectionCheckFilter.class);

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;

		// Getting web application context.
		final WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());

		// Get Filter security intercepter
		final FilterSecurityInterceptor fsi = wac.getBean(FilterSecurityInterceptor.class);

		// Get config attributes.
		final FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
		final Collection<ConfigAttribute> attributes = fsi.getSecurityMetadataSource().getAttributes(filterInvocation);

		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (attributes != null) {
			final Collection<GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
			final boolean isSecured = this.isSecured(attributes, grantedAuthorities);
			if (isSecured && (filterInvocation.getRequestUrl().contains("pages/branch/"))) {
				final DeviceDetectorBean deviceDetBean = wac.getBean(DeviceDetectorBean.class);
				final SessionBean sessionBean = wac.getBean(SessionBean.class);
				final BranchesAppStatusBean branchesAppStatusBean = wac.getBean(BranchesAppStatusBean.class);
				if ((sessionBean == null) || (sessionBean.getCurrentBranch() == null) || (sessionBean.getCurrentBranch().getId() == null)
						|| (sessionBean.getCurrentBranch().getId() == 0)
						|| !branchesAppStatusBean.getBranchesStatus().containsKey(sessionBean.getCurrentBranch().getId())
						|| !branchesAppStatusBean.getBranchesStatus().get(sessionBean.getCurrentBranch().getId())) {

					logger.info("Branch selection is Requried.");
					final PortResolver portResolver = new PortResolverImpl();
					final DefaultSavedRequest defaultSavedRequest = new DefaultSavedRequest(request, portResolver);
					request.getSession().setAttribute(WebAttributes.SAVED_REQUEST, defaultSavedRequest);
					if (deviceDetBean.getIsMobile()) {
						response.sendRedirect(request.getContextPath() + "/mobile_login.html");
					} else {
						response.sendRedirect(request.getContextPath() + "/login.html");
					}

				}

			}
		}
		chain.doFilter(request, response);
	}

	private boolean isSecured(final Collection<ConfigAttribute> attributes, final Collection<GrantedAuthority> grantedAuthorities) {
		for (final ConfigAttribute attribute : attributes) {

			// Attempt to find a matching granted authority
			for (final GrantedAuthority authority : grantedAuthorities) {
				if (attribute.getAttribute().equals(authority.getAuthority())) {
					return true;
				}
			}
		}
		return false;
	}
}
