package com.datacert.portal.controllers;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.datacert.portal.util.SurveyUtils;
import com.datacert.surveysystem.UserService;
import com.datacert.surveysystem.dto.RegisterUserDto;
import com.datacert.surveysystem.dto.User;
import com.datacert.surveysystem.dto.UserDto;

@Controller
@RequestMapping("/signup")
public class SignUpController {

  @Resource
  UserService userService;

  @Resource
  SurveyUtils surveyUtils;

  private MessageSourceAccessor messageSourceAccessor;
  
  private final String REGISTER_JSP ="user/register";
  private final String REGISTER_SUCCESS_JSP ="user/registerSuccess";
  private final String REGISTER_SETPASSWORD_JSP ="user/setpassword";
  private final String CHANGE_PASSWORD_SUCCESS_JSP="user/changepassword/changepasswordsuccess";

  @Resource
  public void setMessageSource(MessageSource messageSource) {
	this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
  }

  @RequestMapping(method = RequestMethod.GET)
  public String signupView(ModelMap model , @ModelAttribute RegisterUserDto registerUserDto) {
	registerUserDto.setSecurityQuestionsList(userService.getAllquestionList());
	model.addAttribute("registerUserDto",registerUserDto);
	return REGISTER_JSP;
  }

  @RequestMapping(method = RequestMethod.POST)
  public String signup(@Valid RegisterUserDto registerUserDto,BindingResult result,HttpServletRequest request) {
	if (!userService.isUnique(registerUserDto.getUserName())) {
	  FieldError error= new FieldError("registerUserDto","userName",messageSourceAccessor.getMessage("message.emailAlreadyExists"));
	  result.addError(error);
	}
	if(result.hasErrors()){
	  registerUserDto.setSecurityQuestionsList(userService.getAllquestionList());
	  return REGISTER_JSP;
	}
	User user = userService.addUser(registerUserDto);
	surveyUtils.sendMail(user.getUsername(), String.valueOf(user.getUserId()), user.getPassword(),request.getRequestURL().toString());
	return REGISTER_SUCCESS_JSP;
  }

  @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
  public String changePasswordView(@RequestParam("id") String userid, @RequestParam("temppass") String temppass, ModelMap model) {
	long tempuserid = Long.valueOf(userid);
	if (userService.isPasswordAlreadyChanged(userid, temppass)) {
	  UserDto user = new UserDto();
	  user.setTempPassword(temppass);
	  model.addAttribute("userid", tempuserid);
	  model.addAttribute("command", user);
	  return REGISTER_SETPASSWORD_JSP;
	} else {
	  model.addAttribute("message", messageSourceAccessor.getMessage("message.alreadyActivated"));
	  return "login";
	}
  }

  @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
  public String changePassword(@ModelAttribute("user") UserDto user, ModelMap model) {

	if (!surveyUtils.checkPasswordPolicy(user.getPassword())) {
	  model.addAttribute("userid", user.getUserId());
	  model.addAttribute("command", user);
	  model.addAttribute("passwordUnMatched", messageSourceAccessor.getMessage("message.passwordPolicy"));
	  return REGISTER_SETPASSWORD_JSP;
	}
	if (user.getConfirmpassword().equals(user.getPassword())) {
	  userService.activateUser(user.getUserId(), user.getPassword());
	  return CHANGE_PASSWORD_SUCCESS_JSP;
	} else {
	  model.addAttribute("userid", user.getUserId());
	  model.addAttribute("command", user);
	  model.addAttribute("passwordUnMatched", messageSourceAccessor.getMessage("message.passwordUnMatched"));
	  return REGISTER_SETPASSWORD_JSP;
	}
  }

}
