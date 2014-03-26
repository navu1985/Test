package com.datacert.portal.controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.datacert.portal.util.SurveyUtils;
import com.datacert.surveysystem.UserService;

@Controller
@RequestMapping(value="/ssssssss")
public class PrePortalController {
  
  @Resource
  private UserService userService;
  
  @Resource 
 private  SurveyUtils surveyUtils; 

  
  
  @RequestMapping("/firstLoginComplsssssete")
  public void firstLoginComplete() {
	userService.loginedOnce(surveyUtils.getLoggedInUser().getUserId());
  }
  
}
