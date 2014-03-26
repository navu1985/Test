package com.datacert.portal.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.datacert.portal.util.SurveyUtils;
import com.datacert.surveysystem.AdminService;
import com.datacert.surveysystem.PolicyService;
import com.datacert.surveysystem.SurveyService;
import com.datacert.surveysystem.dto.Admin;
import com.datacert.surveysystem.dto.DaoCoreConstant;
import com.datacert.surveysystem.dto.Policy;
import com.datacert.surveysystem.dto.Survey;

@Controller
public class PendingItemController {

	@Resource
	private PolicyService policyService;
	@Resource
	private SurveyService surveyService;
	@Resource
	private SurveyUtils surveyUtils;
	@Resource
	private AdminService adminService;
	
	private final String PENDING_ITEM_JSP = "policy/pendingitems";
	private final String SURVEYS = "surveys";
	private final String POLICIES = "policies";

	@RequestMapping(value = "/pendingitems", method = { RequestMethod.GET })
	public String policies(Model model) {

		long userId = surveyUtils.getLoggedInUser().getUserId();
		List<Policy> policies = policyService.getUnAckPolicies(userId);
		model.addAttribute("activeStatus", DaoCoreConstant.policyStatus.ACTIVE.name());
		model.addAttribute(POLICIES, policies);
		
		List<Survey> surveys = surveyService.getPendingSurveys(userId);
		model.addAttribute(SURVEYS, surveys);
		
		model.addAttribute("noMessageData", adminService.getAdminSettings().getNoDataMessage());
		
		return PENDING_ITEM_JSP;
	}
	
	@RequestMapping(value = "/leftpanelcount", method = { RequestMethod.GET })
	public ResponseEntity<Model> leftpanelcount(Model model) {
		long userId = surveyUtils.getLoggedInUser().getUserId();
		model.addAttribute("ackPoliciesCount", policyService.getAckPoliciesCount(userId));
		model.addAttribute("pendingItemCount", surveyService.getPendingSurveys(userId).size()+policyService.getUnAckPoliciesCount(userId));
		Admin admin  =adminService.getAdminSettings();
		model.addAttribute("displaySubmitAnInquiry", admin.getShowSubmitAnInquiry());
		model.addAttribute("displayReportAnIssue", admin.getShowReportAnIssue());
		return new ResponseEntity<Model>(model,HttpStatus.OK);
	}
	
}
