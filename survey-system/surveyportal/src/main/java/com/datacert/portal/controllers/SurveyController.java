package com.datacert.portal.controllers;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.datacert.portal.util.SurveyUtils;
import com.datacert.surveysystem.SurveyService;
import com.datacert.surveysystem.dto.DaoCoreConstant;
import com.datacert.surveysystem.dto.Survey;

@Controller
@RequestMapping("/surveys")
public class SurveyController {

  @Resource
  private SurveyUtils surveyUtils;

  @Resource
  ServletContext context;

  @Resource
  SurveyService surveyService;
  
  private final String SURVEYS="surveys"; 
  private final String MESSAGE="message";
  private final String MESSAGE_QUERY_TIMED_OUT="message.queryTimedOut";
  
  private MessageSourceAccessor messageSourceAccessor;

  @Resource
  public void setMessageSource(MessageSource messageSource) {
	this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
  }

  @RequestMapping(method = { RequestMethod.GET })
  public String surveys(Model model) {
	List<Survey> surveys = surveyService.getPendingSurveys(surveyUtils.getLoggedInUser().getUserId());
	model.addAttribute(SURVEYS, surveys);
	return SURVEYS;
  };

  @RequestMapping("/timeoutSurvey")
  public String timeoutSurvey(HttpServletRequest request, Model model) {
	List<Survey> surveys = surveyService.getPendingSurveys(surveyUtils.getLoggedInUser().getUserId());
	model.addAttribute(MESSAGE, messageSourceAccessor.getMessage(MESSAGE_QUERY_TIMED_OUT, new String[]{context.getAttribute(DaoCoreConstant.SURVEY_ADMIN_EMAIL).toString()}));
	model.addAttribute(SURVEYS, surveys);
	return SURVEYS;
  };
  
  @RequestMapping("/isfirstLogin")
  public String  isfirstLogin(HttpServletResponse response) {
	return SURVEYS;
  }
}
