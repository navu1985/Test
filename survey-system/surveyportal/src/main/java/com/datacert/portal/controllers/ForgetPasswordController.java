package com.datacert.portal.controllers;

import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.datacert.portal.util.SurveyUtils;
import com.datacert.surveysystem.UserService;
import com.datacert.surveysystem.dto.User;
import com.datacert.surveysystem.dto.UserDto;

@Controller
@RequestMapping("/forgetpassword")
public class ForgetPasswordController {

  @Resource
  UserService userService;

  @Resource
  SurveyUtils surveyUtils;

  private MessageSourceAccessor messageSourceAccessor;
  
  private final String FORGOT_PASSWORD_VIEW_JSP="user/forgotpassword/forgotpassword";
  private final String FORGOT_PASSWORD_SECURITY_JSP="user/forgotpassword/forgotsecurityquestion";
  private final String FORGOT_PASSWORD_SUCCESS_JSP="user/forgotpassword/forgotpasswordsuccess";

  @Resource
  public void setMessageSource(MessageSource messageSource) {
	this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
  }

  @RequestMapping(method = RequestMethod.GET)
  public String forgetPasswordView() {
	return FORGOT_PASSWORD_VIEW_JSP;
  }

  @RequestMapping(method = RequestMethod.POST)
  public String forgetPassword(@RequestParam String username, ModelMap model) {
	if(username.trim().isEmpty()){
		model.addAttribute("error", "Email cannot be blank");
		return FORGOT_PASSWORD_VIEW_JSP;
	}
	User user = (User) userService.getUser(username, null, null);
	if (user == null) {
	  model.addAttribute("error", messageSourceAccessor.getMessage("message.emailnotExists"));
	  return FORGOT_PASSWORD_VIEW_JSP;
	} else if (!userService.isRegister(user)) {
	  model.addAttribute("error", messageSourceAccessor.getMessage("message.userNotactivated"));
	  return FORGOT_PASSWORD_VIEW_JSP;
	} else {
	  return "redirect:forgetpassword/" + String.valueOf(user.getUserId()) + "/getSecurityQuestion";
	}
  }
  
  @RequestMapping(value = "/{userid}/getSecurityQuestion", method = RequestMethod.GET)
  public String getSecurityQuestion(@PathVariable String userid, ModelMap model) {
	/*
	 * Added to remove Zap Security Tool Warning
	*/
	try{ Integer.parseInt(userid);}
	catch(NumberFormatException ex){return "login";}
	/*
	*/
	TreeMap<Integer, String> list = userService.getUserQuestionList(userid);
	model.addAttribute("qlist", list);
	model.addAttribute("userid", userid);
	return FORGOT_PASSWORD_SECURITY_JSP;
  }

  @RequestMapping(value = "/sendPassword")
  public String sendPassword(@ModelAttribute("userRegister") UserDto userDto, ModelMap model, HttpServletRequest request) {
	if (userDto.getUserId() == -1) {
	  return "login";
	}
	if (userService.checkSecurityQuestion(userDto.getUserId(), userDto.getQuestion1(), userDto.getAnswer1())) {
	  if (userService.checkSecurityQuestion(userDto.getUserId(), userDto.getQuestion2(), userDto.getAnswer2())) {
		if (userService.checkSecurityQuestion(userDto.getUserId(), userDto.getQuestion3(), userDto.getAnswer3())) {
		  User user = userService.getUser(null, userDto.getUserId(), null);
		  String baseUrl=String.format("%s://%s:%d/%s/",request.getScheme(),  request.getServerName(), request.getServerPort(),request.getContextPath());
		  surveyUtils.sendMailforgetpassword(user.getUsername(), String.valueOf(userDto.getUserId()), user.getUrlIdentifier(),baseUrl);
		  model.addAttribute("emailmessage", messageSourceAccessor.getMessage("message.emailSuccess"));
		  return FORGOT_PASSWORD_SUCCESS_JSP;
		} else {
		  model.addAttribute("message", messageSourceAccessor.getMessage("message.wrongsecurityanswer") + " 3 "
				  + messageSourceAccessor.getMessage("message.tryagain"));
		}
	  } else {
		model.addAttribute("message", messageSourceAccessor.getMessage("message.wrongsecurityanswer") + " 2 "
				+ messageSourceAccessor.getMessage("message.tryagain"));
	  }
	} else {
	  model.addAttribute("message", messageSourceAccessor.getMessage("message.wrongsecurityanswer") + " 1 "
			  + messageSourceAccessor.getMessage("message.tryagain"));
	}
	return "redirect:../forgetpassword/" + userDto.getUserId() + "/getSecurityQuestion";
  }
}
