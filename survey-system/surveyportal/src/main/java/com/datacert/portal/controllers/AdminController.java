package com.datacert.portal.controllers;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.datacert.portal.custom.validation.ApplicationDtoValidator;
import com.datacert.portal.dto.ApplicationDto;
import com.datacert.surveysystem.SurveyService;
import com.datacert.surveysystem.db.AdminServiceImpl;
import com.datacert.surveysystem.db.domain.AdminSettings;
import com.datacert.surveysystem.dto.Admin;
import com.datacert.surveysystem.dto.Application;
import com.datacert.surveysystem.dto.DaoCoreConstant;
import com.datacert.surveysystem.dto.UserSurvey;

@Controller
@RequestMapping("/admin")
public class AdminController {

  @Resource
  private AdminServiceImpl adminService;

  private final String JSP_SETTINGS = "admin/adminsettings";
  private final String JSP_EDITSETTINGS = "admin/updateadminsettings";
  private final String JSP_ERRORSURVEYS = "errorSurveys";
  private final String JSP_CONNECTEDAPPS = "admin/connectedApps";
  private final String JSP_STOPCHANGESTATUS = "admin/stopChangeStatus";
  private final String JSP_STARTCHANGESTATUS = "admin/startChangeStatus";
  private final String JSP_APPLICATION = "admin/application";
  private final String REDIRECT_CONNECTED_APPLICATIONS = "redirect: connectedApplications";

  @Resource
  private SurveyService surveyService;

  @Resource
  ApplicationDtoValidator validator;

  @RequestMapping("/adminSettings")
  public String getAdminSettings(Model model) {
	Admin settings = adminService.getAdminSettings();
	model.addAttribute("settings", settings);
	model.addAttribute("command", settings);
	return JSP_SETTINGS;
  }

  @RequestMapping(value = "/updateSetting", method = { RequestMethod.POST })
  public ResponseEntity<String> updateAdminSetting(@ModelAttribute AdminSettings settings, Model model) {
	adminService.updateAdminSetting(settings);
	return new ResponseEntity<String>(HttpStatus.OK);
  }

  @RequestMapping("/editSettings")
  public String editAdminSettingView(Model model) {
	Admin settings = adminService.getAdminSettings();
	model.addAttribute("settings", settings);
	model.addAttribute("command", settings);
	return JSP_EDITSETTINGS;
  }

  @RequestMapping("/startConnection")
  public @ResponseBody
  String startConnection(@ModelAttribute AdminSettings settings, Model model) {
	adminService.updateConnectionFlag(true);
	settings.setConnectionFlag(true);
	return "Connected";
  }

  @RequestMapping("/stopConnection")
  public @ResponseBody
  String stopConnection(@ModelAttribute AdminSettings settings, Model model) {
	adminService.updateConnectionFlag(false);
	return "Success";
  }

  @RequestMapping("/errorSurveys")
  public String errorSurveys(Model model) {
	List<? extends UserSurvey> surveys = adminService.getErrorSurveys();
	model.addAttribute("errorSurveys", surveys);
	return JSP_ERRORSURVEYS;
  }

  @RequestMapping("/changepasswordview")
  public String changepasswordview(Model model) {
	List<? extends UserSurvey> surveys = adminService.getErrorSurveys();
	model.addAttribute("errorSurveys", surveys);
	return JSP_ERRORSURVEYS;
  }

  @RequestMapping(method = { RequestMethod.GET }, value = "/resetSurveys/{surveyId}/user/{userId}")
  public String resetSurveys(@PathVariable String surveyId, @PathVariable String userId, Model model) {
	surveyService.resetUserSurvey(Long.valueOf(surveyId), Long.valueOf(userId));
	List<? extends UserSurvey> surveys = adminService.getErrorSurveys();
	model.addAttribute("errorSurveys", surveys);
	return JSP_ERRORSURVEYS;
  }

  @RequestMapping(method = { RequestMethod.GET }, value = "/connectedApplications")
  public String viewConnectedApplications(Model model) {
	model.addAttribute("appList", adminService.getConnectedApplication());
	model.addAttribute("connectionStatus", DaoCoreConstant.APPLICATION_STATUS_CONNECTED);
	return JSP_CONNECTEDAPPS;
  }

  @RequestMapping("/connectionStart")
  public String connectionStart(@RequestParam String applicationId, Model model) {
	adminService.stopApplicationCommunication(applicationId, DaoCoreConstant.APPLICATION_STATUS_CONNECTED);
	model.addAttribute("applicationId", applicationId);
	return JSP_STARTCHANGESTATUS;
  }

  @RequestMapping("/connectionStop")
  public String connectionStop(@RequestParam String applicationId, Model model) {
	adminService.stopApplicationCommunication(applicationId, DaoCoreConstant.APPLICATION_STATUS_DISCONNECTED);
	model.addAttribute("applicationId", applicationId);
	return JSP_STOPCHANGESTATUS;
  }

  @SuppressWarnings("static-access")
  @RequestMapping(method = { RequestMethod.POST }, value = "/deleteApplication")
  public void deleteApplication(@RequestParam String applicationId, HttpServletResponse response) {
	adminService.deleteCommunication(applicationId);
	response.setStatus(response.SC_OK);
  }

  @RequestMapping(method = { RequestMethod.GET }, value = "/getApplication/{applicationId}")
  public String viewaddApplications(@PathVariable String applicationId, Model model) {
	if (!(applicationId.equals(null) || applicationId == null || applicationId.isEmpty())) {
	  model.addAttribute("applicationDto", adminService.getApplication(applicationId));
	}
	return JSP_APPLICATION;
  }

  @RequestMapping(method = { RequestMethod.POST }, value = "/saveApplication")
  public String addApplication(@Valid ApplicationDto applicationDto, BindingResult result) {
	result = validator.validatePhoneContact(applicationDto, result);
	if (result.hasErrors()) {
	  return JSP_APPLICATION;
	}
	adminService.addApplication((Application) applicationDto);
	return REDIRECT_CONNECTED_APPLICATIONS;
  }

  @RequestMapping(method = { RequestMethod.GET }, value = "/resetApplication/{applicationId}")
  public String resetApplication(@PathVariable String applicationId, Model model) {
	if (!(applicationId.equals(null) || applicationId == null || applicationId.isEmpty())) {
	  adminService.resetApplication(applicationId);
	  model.addAttribute("applicationDto", adminService.getApplication(applicationId));
	}
	return JSP_APPLICATION;
  }
}
