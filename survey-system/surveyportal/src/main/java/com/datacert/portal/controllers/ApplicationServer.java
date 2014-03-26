package com.datacert.portal.controllers;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.datacert.portal.util.SurveyPortalConstants;
import com.datacert.surveysystem.AdminService;
import com.datacert.surveysystem.dto.Application;
import com.datacert.surveysystem.dto.DaoCoreConstant;

@Controller
@RequestMapping("/surveyServer")
public class ApplicationServer {

  @Resource
  ServletContext context;

  @Resource
  AdminService adminService;

  private final ResponseEntity<String> successResponseEntity = new ResponseEntity<String>(SurveyPortalConstants.RESPONSE_OK, HttpStatus.OK);
  private final ResponseEntity<String> badRequestResponseEntity = new ResponseEntity<String>(SurveyPortalConstants.RESPONSE_BAD_REQUEST, HttpStatus.BAD_REQUEST);

  /*
   * Add Survey Email to Portal from Passport
  */
  @RequestMapping(value = "/addAdminEmail", method = RequestMethod.POST, headers = "Accept=text/html")
  public ResponseEntity<String> addAdminEmail(@RequestParam String adminEmail) {
	context.setAttribute(DaoCoreConstant.SURVEY_ADMIN_EMAIL, adminEmail);
	adminService.setAdminEmail(adminEmail);
	return successResponseEntity;
  }

  /*
   * Adding Passport Application to Portal 
  */
  @RequestMapping(value = "/addApplication", method = RequestMethod.POST, headers = "Accept=text/html")
  public ResponseEntity<String> addApplication(@RequestParam String applicationId, @RequestParam String dbGuid,@RequestParam String adminEmail) {
	context.setAttribute(DaoCoreConstant.SURVEY_ADMIN_EMAIL, adminEmail);
	adminService.setAdminEmail(adminEmail);
	if (adminService.addApplication(applicationId, dbGuid))
	  return successResponseEntity;
	else
	  return badRequestResponseEntity;
  }

  /*
   * Check Aplication Id and Database Global Unique Id   
  */
  @RequestMapping(value = "/checkAppIDDbGuid", method = RequestMethod.POST, headers = "Accept=text/html")
  public ResponseEntity<String> checkAppIDDbGuid(@RequestParam String applicationId, @RequestParam String dbGuid,HttpServletResponse response) {
	Application application = adminService.getApplicationByUniqueid(applicationId);
	if (dbGuid.equalsIgnoreCase("null"))
	  return badRequestResponseEntity;
	else if (application == null || application.getDbGuid() == null) {
	  adminService.addApplication(applicationId, dbGuid);
	  return successResponseEntity;
	} else if (application.getDbGuid().equals(dbGuid))
	  return successResponseEntity;
	return badRequestResponseEntity;
  }
}
