package com.datacert.portal.infrastructure;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.datacert.surveysystem.UserService;
import com.datacert.surveysystem.db.util.DaoUtility;
import com.datacert.surveysystem.dto.User;

public class WebServiceSecurityInterceptor implements HandlerInterceptor {

  private static Logger logger = LoggerFactory.getLogger(WebServiceSecurityInterceptor.class);

  @Resource
  UserService userService;

  @Resource
  DaoUtility daoUtils;

  private MessageSourceAccessor messageSourceAccessor;

  @Resource
  public void setMessageSource(MessageSource messageSource) {
	this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
  }

  @Value(value = "${webService.requireAuthentication}")
  private String authenticationFlag;

  @SuppressWarnings("static-access")
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	if (authenticationFlag == null) {
	  logger.error("Please Specify value of webService.authentication in surveyportal.properties");
	} else {
	  if (Boolean.valueOf(authenticationFlag).equals(true)) {
		logger.info("Web Service Call Checking User Authorization ");
		if (request.getHeader("userName") != null && request.getHeader("password") != null) {
		  try {
			User user = userService.getUser(request.getHeader("userName"), null, null);
			if (user != null) {
			  if (user.getPassword().equals(daoUtils.convertStringPasswordToShaPassword(request.getHeader("password"))))
				return true;
			} else {
			  response.setStatus(response.SC_UNAUTHORIZED);
			  response.getWriter().write(messageSourceAccessor.getMessage("message.SC_UNAUTHORIZED"));
			}
		  } catch (Exception e) {
			response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("");
		  }
		} else {
		  response.setStatus(response.SC_UNAUTHORIZED);
		  response.getWriter().write(messageSourceAccessor.getMessage("message.SC_UNAUTHORIZED"));
		}
	  }else{
		return true;
	  }
	}
	return false;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
		  throws Exception {
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
		  throws Exception {
  }

}
