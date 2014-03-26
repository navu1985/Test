package com.datacert.portal.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.datacert.portal.util.SurveyUtils;
import com.datacert.surveysystem.PolicyService;
import com.datacert.surveysystem.dto.DaoCoreConstant;
import com.datacert.surveysystem.dto.Policy;

@Controller
@RequestMapping("/policies")
public class PolicyController {

  @Resource
  PolicyService policyService;

  @Resource
  SurveyUtils surveyUtils;

  @Resource
  ServletContext context;

  private final String POLICIES_JSP = "policy/policies";

  @RequestMapping(method = { RequestMethod.GET })
  public String policies(Model model) {
	long userId = surveyUtils.getLoggedInUser().getUserId();
	List<Policy> policies = policyService.getAckPolicies(userId);
	model.addAttribute("policies", policies);
	model.addAttribute("activeStatus", DaoCoreConstant.policyStatus.ACTIVE.name());
	model.addAttribute("ackPoliciesCount", policyService.getAckPoliciesCount(userId));
	return POLICIES_JSP;
  };

  @RequestMapping(value = "{policyId}/openPolicy/{policyDocumentId}", method = RequestMethod.POST)
  public String openPolicy(HttpServletRequest request, @PathVariable String policyDocumentId, @PathVariable String policyId,
		  @RequestParam Boolean policyAckFlag, Model model) {
	policyService.policyAccessed(policyId, surveyUtils.getLoggedInUser().getUserId());
	model.addAttribute("policyId", policyId);
	model.addAttribute("policyDocumentId", policyDocumentId);
	model.addAttribute("policyAckFlag", policyAckFlag);
	return "policy/ackpolicy";
  };

  @RequestMapping(value = "/acknowledgepolicy/{policyId}", method = RequestMethod.POST)
  public ResponseEntity<String> acknowledgepolicy(@PathVariable String policyId, Model model) {
	policyService.acknowledgepolicy(policyId, surveyUtils.getLoggedInUser().getUserId());
	return new ResponseEntity<String>(HttpStatus.OK);
  };

  @RequestMapping(value = "/policy/{policyId}/thumbnail", method = RequestMethod.GET, produces = "application/image")
  public void policyThumbnail(@PathVariable String policyId, Model model,HttpServletResponse response) throws Exception {
	ServletOutputStream out = null;
	try {
	  response.setContentType("application/octet-stream");
	  out = response.getOutputStream();
	  policyService.getPolicyThumbnail(Long.valueOf(policyId),out);
	} finally {
	  out.flush();
	  out.close();
	}
  };

  @RequestMapping(value = "/document/{policyDocumentId}", method = RequestMethod.GET, headers = "Accept=application/octet-stream")
  public void policyDocument(@PathVariable String policyDocumentId, HttpServletResponse response) throws IOException {
	ServletOutputStream out = null;
	try {
	  out = response.getOutputStream();
	  response.setContentType("application/pdf");
	  policyService.getPolicyDocument(Long.valueOf(policyDocumentId), (OutputStream) out);
	} finally {
	  out.flush();
	  out.close();
	}
  }

  @RequestMapping(value = "/search/fulltext", method = RequestMethod.POST)
  public String policyFullTextSearch(@RequestParam(required = false) String policyName,
		  @RequestParam(required = false) String policyTopic, @RequestParam(required = false) String policyApplicationId,
		  @RequestParam(required = false) String policyType, @RequestParam(required = false) String policyIssuedBy,
		  @RequestParam(required = false) String policyVersionDate, @RequestParam(required = false) String policyRelatedDoc,
		  @RequestParam String searchText, Model model) {
	long userId=surveyUtils.getLoggedInUser().getUserId();
	List<Policy> policies = policyService.policyFullTextSearch(policyName, policyTopic, policyApplicationId, policyType,
			policyIssuedBy, policyVersionDate, policyRelatedDoc, searchText, userId);
	model.addAttribute("policies", policies);
	model.addAttribute("activeStatus", DaoCoreConstant.policyStatus.ACTIVE.name());
	model.addAttribute("ackPoliciesCount", policyService.getAckPoliciesCount(userId));
	return POLICIES_JSP;
  }
}
