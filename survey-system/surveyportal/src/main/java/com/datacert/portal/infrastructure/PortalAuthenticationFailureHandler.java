package com.datacert.portal.infrastructure;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.datacert.surveysystem.UserService;

@Component
public class PortalAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private static Logger logger = LoggerFactory.getLogger(PortalAuthenticationFailureHandler.class);

	@Resource
	UserService userService;

	private String portalLoginTries;
		
	public void setPortalLoginTries(String portalLoginTries) {
			this.portalLoginTries = portalLoginTries;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
					throws IOException, ServletException {
		String username = request.getParameter("j_username");
		Integer retrylogin = new Integer(portalLoginTries);
		HttpSession session = request.getSession();
		Integer count = (Integer) session.getAttribute(username);
		if (count == null) {
			count = 1;
			session.setAttribute(username, Integer.valueOf(count));
		} else {
			++count;
			session.setAttribute(username, Integer.valueOf(count));
			if (count.equals(retrylogin)) {
				userService.lockUser(username);
				session.removeAttribute(username);
			}
		}
		if (exception instanceof org.springframework.security.authentication.LockedException) {
			logger.debug(exception.getMessage());
			session.removeAttribute(username);
			setDefaultFailureUrl("/login?disabled=true");
		} else {
			logger.debug(exception.getMessage());
			if (count.equals(retrylogin)) {
				setDefaultFailureUrl("/login?disabled=true");
			} else {
				setDefaultFailureUrl("/login?error=true");
			}
		}
		logger.debug("username===================" + username + "====" + session.getAttribute(username));

		super.onAuthenticationFailure(request, response, exception);
	}
}
