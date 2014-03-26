package com.datacert.portal.controllers;

import java.io.StringReader;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.datacert.portal.util.SurveyPortalConstants;
import com.datacert.portal.util.SurveyUtils;
import com.datacert.surveysystem.AdminService;
import com.datacert.surveysystem.SurveyService;
import com.datacert.surveysystem.UserService;
import com.datacert.surveysystem.db.domain.UserDescriptor;
import com.datacert.surveysystem.dto.Application;
import com.datacert.surveysystem.dto.DaoCoreConstant;
import com.datacert.surveysystem.dto.ReNotify;
import com.datacert.surveysystem.dto.ReNotifyList;
import com.datacert.surveysystem.dto.SurveyDto;
import com.datacert.surveysystem.dto.User;
import com.datacert.surveysystem.dto.UserResponseDocument;
import com.datacert.surveysystem.dto.UserSurvey;
import com.datacert.surveysystem.surveyresponse.PullSurveys;
import com.datacert.surveysystem.surveyresponse.SurveyAckResponse;
import com.datacert.surveysystem.surveyresponse.SurveyAckResponses;
import com.datacert.surveysystem.surveyresponse.SurveyResponse;
import com.datacert.surveysystem.surveyresponse.SurveyResponseStatus;

@Controller
@RequestMapping("/surveyServer")
public class SurveyServer {

  @Resource
  SurveyService surveyService;

  @Resource
  UserService userService;

  @Resource
  ServletContext context;

  @Resource
  SurveyUtils suUtil;
  
  @Resource
  AdminService adminService;


  /**
   * @param surveyStr
   * @param request
   * @param response
   * @throws Exception
   *           Check is request coming from valid Passport or not??? if yes then
   *           add passport Id in survey table
   */

  @RequestMapping(value = "/add", method = RequestMethod.POST, consumes={"application/xml"})
  public ResponseEntity<String> addPassportSurvey(@RequestBody SurveyDto survey, @RequestParam String applicationId) throws Exception {
	if (!adminService.isApplicationActive(applicationId)) {
	  return new ResponseEntity<String>(SurveyPortalConstants.PORTAL_NOT_CONNCTED, HttpStatus.PRECONDITION_FAILED);
	}
	Application passport = surveyService.getPassport(null, applicationId);
	if (surveyService.isSurveyExists(survey.getSurveyId(), passport)) {
	  return new ResponseEntity<String>("Survey already exists with this Id", HttpStatus.CONFLICT);
	}
	surveyService.addSurvey(survey, passport);
	return new ResponseEntity<String>(SurveyPortalConstants.RESPONSE_OK, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/{surveyID}/add/{questionID}/questionDocument", method = RequestMethod.POST, consumes = { "application/octet-stream" })
  public ResponseEntity<String> addQuestionDocument(@PathVariable String surveyID,@PathVariable String questionID,@RequestParam String applicationId,HttpServletRequest request) throws Exception {
	if (!adminService.isApplicationActive(applicationId)) {
	  return new ResponseEntity<String>(SurveyPortalConstants.PORTAL_NOT_CONNCTED, HttpStatus.PRECONDITION_FAILED);
	}
	Application passport = surveyService.getPassport(null, applicationId);
	ServletInputStream inputStream = request.getInputStream();
	surveyService.addQuestionDocument(inputStream,surveyID,questionID,passport);
	
	return  new ResponseEntity<String>(HttpStatus.OK);
  }
  
  @RequestMapping(value = "/pullSurveys", method = RequestMethod.POST, consumes = { "application/xml" })
  public ResponseEntity<?> pullSurveys(@RequestBody PullSurveys pullSurveys, @RequestParam String applicationId) throws Exception {
	if (!adminService.isApplicationActive(applicationId)) {
	  return new ResponseEntity<String>(SurveyPortalConstants.PORTAL_NOT_CONNCTED, HttpStatus.PRECONDITION_FAILED);
	}
	Application passport = surveyService.getPassport(null, applicationId);
	SurveyResponse survey = new SurveyResponse();
	survey.setSurveyPullResponse(surveyService.getUserCompletedSurvey(Integer.valueOf(pullSurveys.getBatchSize()), passport));
	return new ResponseEntity<SurveyResponse>(survey, HttpStatus.OK);
  }

  @SuppressWarnings("static-access")
  @RequestMapping(value = "/ackPullSurveys", method = RequestMethod.POST, headers = "Accept=text/html")
  public void ackPullSurveys(@RequestBody SurveyAckResponses survey, @RequestParam String applicationId,
		  HttpServletRequest request, HttpServletResponse response) throws Exception {

	String adminEmail = (String) context.getAttribute(DaoCoreConstant.SURVEY_ADMIN_EMAIL);
	String completeStatus = DaoCoreConstant.userSurveyStatus.COMPLETED.name(), submittedErrorStatus = DaoCoreConstant.userSurveyStatus.SUBMITTED_ERROR
			.name(), finalStatus = null;

	Application passport = surveyService.getPassport(null, applicationId);
	if (passport == null) {
	  response.setStatus(response.SC_UNAUTHORIZED);
	  return;
	}

	ArrayList<SurveyAckResponse> errorList = new ArrayList<SurveyAckResponse>();
	for (SurveyAckResponse surveyAckResponse : survey.getSurveyAckResponseList()) {
	  UserSurvey userSurvey = surveyService.getUserSurvey(surveyAckResponse.getResponseId(), passport);
	  User user = userSurvey.getUser();

	  if (surveyAckResponse.getSurveyStatus().equalsIgnoreCase(DaoCoreConstant.ACKNOWEDELGE_ERROR)) {
		errorList.add(surveyAckResponse);
		finalStatus = submittedErrorStatus;
	  } else {
		suUtil.sendSuccessSurvey(user.getUsername(), adminEmail);
		finalStatus = completeStatus;
	  }
	  surveyService.processSurveyAck(userSurvey, finalStatus);
	}

	if (!errorList.isEmpty()) {
	  suUtil.sendErrorSurvey(errorList, adminEmail);
	}

	response.setStatus(HttpServletResponse.SC_OK);
  }

  @RequestMapping(value = "/renotify", method = RequestMethod.POST, headers = "Accept=text/html")
  public void renotify(@RequestBody String requestStr, @RequestParam String applicationId, HttpServletResponse response)
		  throws Exception {
	
	if (!adminService.isApplicationActive(applicationId)) {
	  response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
	  response.getWriter().append("Portal is not connected with Passport Please check Connection between portal and passport on Portal Setting Page");
	  return;
	}
	
	JAXBContext jaxbContext = JAXBContext.newInstance(ReNotifyList.class);
	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	ReNotifyList reNotifyList = (ReNotifyList) unmarshaller.unmarshal(new StreamSource(new StringReader(requestStr)));
	reNotifyList.getReNotifyList();
	for (ReNotify reNotify : reNotifyList.getReNotifyList()) {
	  UserDescriptor user = (UserDescriptor) userService.getUser(null, null, reNotify.getUserPassportId());
	  user.setUsername(reNotify.getUserName());
	  userService.updateUser(user);
	}
	response.setStatus(HttpServletResponse.SC_OK);
  }
  
  @RequestMapping(value = "/pullSurveyDocument/{id}", method = RequestMethod.GET, headers = "Accept=application/octet-stream")
  public void pullSurveyDocument(HttpServletRequest request, HttpServletResponse response, @PathVariable String id)
		  throws Exception {
	UserResponseDocument userDoc = surveyService.getsurveyDocument(Long.valueOf(id));
	ServletOutputStream out = null;
	try {
	  out = response.getOutputStream();
	  response.setContentType("application/octet-stream");
	  response.setHeader("Content-Disposition", "filename=\"" + userDoc.getDocName() + "\"");
	  out.write(userDoc.getDoc());
	} finally {
	  out.close();
	}
  }

  @SuppressWarnings("static-access")
  @RequestMapping(value = "/surveyResponseStatus", method = RequestMethod.POST, headers = "Accept=text/html, application/json,application/octet-stream,application/xml")
  public @ResponseBody  String responseStatus(@RequestBody SurveyResponseStatus status, @RequestParam String applicationId, HttpServletRequest request,
		  HttpServletResponse response) throws Exception {
	if (!adminService.isApplicationActive(applicationId)) {
	  response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
	  response.getWriter().append("Portal is not connected with Passport Please check Connection between portal and passport on Portal Setting Page");
	  return null;
	}
	Application passport = surveyService.getPassport(null, applicationId);
	if (passport == null) {
	  response.setStatus(response.SC_UNAUTHORIZED);
	  return null;
	}
	return "<status>" + surveyService.userSurveyResponse(status.responseId, passport) + "</status>";
  }
  
  @ExceptionHandler({Exception.class })
  public ResponseEntity<String> excptionHandler(Exception exception) {
	exception.printStackTrace();
	return new ResponseEntity<String>(SurveyPortalConstants.PORTAL_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
