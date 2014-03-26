package com.datacert.portal.controllers;

import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.datacert.portal.dto.PolicyDto;
import com.datacert.portal.util.SurveyPortalConstants;
import com.datacert.surveysystem.AdminService;
import com.datacert.surveysystem.PolicyService;
import com.datacert.surveysystem.dto.SyncPolicyAudiencesXml;
import com.datacert.surveysystem.dto.SyncPolicyRelationsXml;
import com.datacert.surveysystem.policyresponse.PolicyAccessedResponsesXml;
import com.datacert.surveysystem.surveyresponse.PolicyResponseList;

@Controller
@RequestMapping("/surveyServer")
public class PolicyServer {

  @Resource
  AdminService adminService;

  @Resource
  PolicyService policyService;

  static final ResponseEntity<String> portalNotConnectedResponseEntity=new ResponseEntity<String>(SurveyPortalConstants.PORTAL_NOT_CONNCTED, HttpStatus.PRECONDITION_FAILED);
  static final ResponseEntity<String> successResponseEntity= new ResponseEntity<String>(SurveyPortalConstants.RESPONSE_OK, HttpStatus.OK);

  @RequestMapping(value = "/addPolicy", method = RequestMethod.POST, consumes = { "application/xml" })
  public ResponseEntity<?> addPolicy(@RequestBody PolicyDto policy, @RequestParam String applicationId) throws Exception {
	if (!adminService.isApplicationActive(applicationId)) {
	  return portalNotConnectedResponseEntity;
	}
	return new ResponseEntity<PolicyResponseList>(policyService.setAddPolicyResponse(policyService.addPolicy(policy,applicationId)), HttpStatus.OK);
  }

  @RequestMapping(value = "/updatePolicy", method = RequestMethod.POST, consumes={"application/xml"})
  public ResponseEntity<String> updatePolicy(@RequestBody PolicyDto policy, @RequestParam String applicationId) throws Exception {
	if (!adminService.isApplicationActive(applicationId)) {
	 return portalNotConnectedResponseEntity;
	}
	policyService.updatePolicy(policy, applicationId);
	return successResponseEntity;
  }

  @RequestMapping(value = "/addPolicyDocument/{policyDocID}", method = RequestMethod.POST, consumes = { "application/octet-stream" })
  public ResponseEntity<String> addPolicyDocument(@PathVariable String policyDocID, HttpServletRequest request) throws Exception {
	ServletInputStream inputStream = request.getInputStream();
	policyService.addPolicyDocument(Long.valueOf(policyDocID), (InputStream) inputStream);
	return successResponseEntity;
  }

  @RequestMapping(value = "/updatePolicyRelations", method = RequestMethod.POST, consumes={"application/xml"})
  public ResponseEntity<?> updatePolicyRelations(@RequestBody SyncPolicyRelationsXml policyRelations, @RequestParam String applicationId) throws Exception {
	if (!adminService.isApplicationActive(applicationId)) {
	  return portalNotConnectedResponseEntity;
	}
	return new ResponseEntity<SyncPolicyRelationsXml>(policyService.updatePolicyRelations(policyRelations, applicationId),HttpStatus.OK);
  }
  
  @RequestMapping(value = "/updatePolicyAudience", method = RequestMethod.POST, consumes={"application/xml"})
  public ResponseEntity<?> updatePolicyAudience(@RequestBody SyncPolicyAudiencesXml policyRelations, @RequestParam String applicationId) throws Exception {
	if (!adminService.isApplicationActive(applicationId)) {
	  return portalNotConnectedResponseEntity;
	}
	return new ResponseEntity<SyncPolicyAudiencesXml>(policyService.updatePolicyAudiences(policyRelations, applicationId),HttpStatus.OK);
  }
  
  @RequestMapping(value = "/retirePolicy/{policyId}", method = RequestMethod.GET)
  public ResponseEntity<String> retirePolicy(@PathVariable String policyId, @RequestParam String applicationId) throws Exception {
	if (!adminService.isApplicationActive(applicationId)) {
	  return portalNotConnectedResponseEntity;
	}
	policyService.retirePolicy(policyId,applicationId);
	return successResponseEntity;
  }
  
  
  @RequestMapping(value = "/policyAccessedCount", method = RequestMethod.GET)
  public ResponseEntity<?> policyAccessedCount(@RequestParam String applicationId) throws Exception {
	if (!adminService.isApplicationActive(applicationId)) {
	  return portalNotConnectedResponseEntity;
	}
	PolicyAccessedResponsesXml policyAccessedResponsesXml=policyService.policyAccessedByUsers();
	if(policyAccessedResponsesXml.getPolicyAccessedResponse().isEmpty()){
		return new ResponseEntity<String>("No Data Pending",HttpStatus.OK);
	}
	return new ResponseEntity<PolicyAccessedResponsesXml>(policyAccessedResponsesXml,HttpStatus.OK);
  }
  
  @RequestMapping(value = "/ackPolicyAccessedCount", method = RequestMethod.POST)
  public ResponseEntity<?> ackPolicyAccessedCount(@RequestBody PolicyAccessedResponsesXml policyAccessedResponsesXml, @RequestParam String applicationId) throws Exception {
	if (!adminService.isApplicationActive(applicationId)) {
	  return portalNotConnectedResponseEntity;
	}
	policyService.updatePolicyAcknowledgement(policyAccessedResponsesXml);
	return new ResponseEntity<String>(HttpStatus.OK);
  }
  
  @ExceptionHandler({Exception.class })
  public ResponseEntity<?> excptionHandler(Exception exception) {
	exception.printStackTrace();
	return new ResponseEntity<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
