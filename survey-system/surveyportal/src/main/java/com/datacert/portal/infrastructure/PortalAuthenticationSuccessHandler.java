package com.datacert.portal.infrastructure;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class PortalAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

//		@SuppressWarnings("unchecked")
//		List<GrantedAuthority> list = (List<GrantedAuthority>) authentication.getAuthorities();
//
//		if (list.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
//			logger.info("Login by Admin Credentials");
//			setDefaultTargetUrl("/admin");
//		} else {
//			setDefaultTargetUrl("/");
//		}
		HttpSession session = request.getSession();
		String username = authentication.getName();
		if (session.getAttribute(username) != null) {
			session.removeAttribute(username);
		}

		super.onAuthenticationSuccess(request, response, authentication);
	}
}
