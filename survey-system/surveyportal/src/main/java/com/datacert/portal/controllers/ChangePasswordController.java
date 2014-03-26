package com.datacert.portal.controllers;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.datacert.portal.dto.ChangePasswordDto;
import com.datacert.portal.util.SurveyUtils;
import com.datacert.surveysystem.UserService;
import com.datacert.surveysystem.dto.UserDto;

@Controller
public class ChangePasswordController {

  @Resource
  private UserService userService;

  @Resource
  private SurveyUtils surveyUtils;

  private MessageSourceAccessor messageSourceAccessor;

  @Resource
  public void setMessageSource(MessageSource messageSource) {
	this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
  }

  private final String CHANGE_PASSWORD_JSP = "user/changepassword/changepassword";
  private final String CHANGE_PASSWORD_SUCCESS_JSP = "user/changepassword/changepasswordsuccess";

  @RequestMapping(value = "/changepassword", method = RequestMethod.GET)
  public String changePasswordView(@RequestParam("id") String userid, @RequestParam("urlIdentifier") String urlIdentifier,
		  ModelMap model) {
	if (userService.isChangePasswordRequestValid(userid, urlIdentifier)) {
	  UserDto userRegister = new UserDto();
	  model.addAttribute("urlIdentifier", urlIdentifier);
	  model.addAttribute("command", userRegister);
	  model.addAttribute("userid", userid);
	  return CHANGE_PASSWORD_JSP;
	} else {
	  return "login";
	}
  }

  @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
  public String changePassword(@ModelAttribute("userRegister") UserDto userRegister, ModelMap model) {
	model.addAttribute("passwordUnMatched", messageSourceAccessor.getMessage("message.passwordPolicy"));
	model.addAttribute("urlIdentifier", userRegister.getUrlIdentifier());
	model.addAttribute("userid", userRegister.getUserId());
	model.addAttribute("command", userRegister);
	if (!surveyUtils.checkPasswordPolicy(userRegister.getPassword()))
	  return CHANGE_PASSWORD_JSP;
	if (userService.changePassword(userRegister.getUserId(), userRegister.getUrlIdentifier(), userRegister.getPassword()))
	  return CHANGE_PASSWORD_SUCCESS_JSP;
	else
	  return CHANGE_PASSWORD_JSP;
  }

  @RequestMapping(value = "/changeexistingpassword", method = RequestMethod.GET)
  public String changeExistingPasswordView(Model model) {
	model.addAttribute("cpDto", new ChangePasswordDto());
	return "user/changepassword/changeExistingPassword";
  }

  @RequestMapping(value = "/changeexistingpassword", method = RequestMethod.POST)
  public String changeExistingPassword(@Valid ChangePasswordDto cpDto, BindingResult result, Model model) {
	String username = surveyUtils.getLoggedInUser().getUsername();
	if (result.hasErrors())
	  return "user/changepassword/changeExistingPassword";
	if (!surveyUtils.checkPasswordPolicy(cpDto.getPassword()))
	  model.addAttribute("error", messageSourceAccessor.getMessage("message.passwordPolicy"));
	else if (cpDto.getOldpassword().equals(cpDto.getPassword()))
	  model.addAttribute("error", messageSourceAccessor.getMessage("message.oldNewPasswordAresame"));
	else if (cpDto.getConfirmpassword().equals(cpDto.getPassword())) {
	  if (userService.changeExistingPassword(username, cpDto.getOldpassword(), cpDto.getPassword()))
		model.addAttribute("success", messageSourceAccessor.getMessage("message.passwordChangedSuccessfully"));
	  else
		model.addAttribute("error", messageSourceAccessor.getMessage("message.incorrectOldPassword"));
	} else
	  model.addAttribute("error", messageSourceAccessor.getMessage("message.passwordUnMatched"));
	return "user/changepassword/changeExistingPassword";
  }
}