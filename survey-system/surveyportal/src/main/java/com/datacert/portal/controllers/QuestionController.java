package com.datacert.portal.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.datacert.portal.util.SurveyUtils;
import com.datacert.surveysystem.SurveyService;
import com.datacert.surveysystem.dto.AnswerType;
import com.datacert.surveysystem.dto.Answers;
import com.datacert.surveysystem.dto.Survey;

@Controller
public class QuestionController {

  @Resource
  SurveyService surveyService;

  @Resource
  private SurveyUtils surveyUtils;

  @InitBinder
  public void initBinder(WebDataBinder binder) {
	CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true);
	binder.registerCustomEditor(Date.class, editor);
  }

  @RequestMapping(method = { RequestMethod.GET }, value = "/surveys/{surveyId}/questions/{page}")
  public String questions(@PathVariable String surveyId, @PathVariable String page, Model model) {
	try {
	  if (surveyService.isSurveyAssigned(Long.valueOf(surveyId), surveyUtils.getLoggedInUser().getUserId())) {
		Survey survey = surveyService.getSurveyQuestion(Long.valueOf(surveyId), surveyUtils.getLoggedInUser().getUserId());
		model.addAttribute("survey", survey);
		model.addAttribute("currrentPage", page);
		model.addAttribute("DATE",AnswerType.DATE);
		model.addAttribute("DATE_RANGE",AnswerType.DATE_RANGE);
		model.addAttribute("DECIMAL",AnswerType.DECIMAL);
		model.addAttribute("DECIMAL_RANGE",AnswerType.DECIMAL_RANGE);
		model.addAttribute("DOCUMENT_UPLOAD",AnswerType.DOCUMENT_UPLOAD);
		model.addAttribute("INTEGER",AnswerType.INTEGER);
		model.addAttribute("INTEGER_RANGE",AnswerType.INTEGER_RANGE);
		model.addAttribute("LONG_TEXT",AnswerType.LONG_TEXT);
		model.addAttribute("SHORT_TEXT",AnswerType.SHORT_TEXT);
		model.addAttribute("LOOKUP_LIST",AnswerType.LOOKUP_LIST);
		model.addAttribute("MONEY",AnswerType.MONEY);
		model.addAttribute("MONEY_RANGE",AnswerType.MONEY_RANGE);
		return "questions";
	  } else {
		return "redirect:/surveys";
	  }
	} catch (Exception e) {
	  if(e instanceof GenericJDBCException){
		return "redirect:/surveys/timeoutSurvey";
	  }
	  return "redirect:/surveys";
	}
  }

  @RequestMapping(method = { RequestMethod.POST }, value = "/submitSurveyAnswer")
  public String submitanswer(@ModelAttribute Answers answers, HttpServletRequest request, Model model) {
	long userId = surveyUtils.getLoggedInUser().getUserId();
	surveyService.saveSurveyAnswer(request, answers, userId);
	Survey survey = surveyService.getSurveyQuestion(Long.valueOf(answers.getSurveyId()), userId);
	model.addAttribute("survey", survey);
	model.addAttribute("currrentPage", answers.getCurrrentPage());
	model.addAttribute("DATE",AnswerType.DATE);
	model.addAttribute("DATE_RANGE",AnswerType.DATE_RANGE);
	model.addAttribute("DECIMAL",AnswerType.DECIMAL);
	model.addAttribute("DECIMAL_RANGE",AnswerType.DECIMAL_RANGE);
	model.addAttribute("DOCUMENT_UPLOAD",AnswerType.DOCUMENT_UPLOAD);
	model.addAttribute("INTEGER",AnswerType.INTEGER);
	model.addAttribute("INTEGER_RANGE",AnswerType.INTEGER_RANGE);
	model.addAttribute("LONG_TEXT",AnswerType.LONG_TEXT);
	model.addAttribute("SHORT_TEXT",AnswerType.SHORT_TEXT);
	model.addAttribute("LOOKUP_LIST",AnswerType.LOOKUP_LIST);
	model.addAttribute("MONEY",AnswerType.MONEY);
	model.addAttribute("MONEY_RANGE",AnswerType.MONEY_RANGE);
	return "questions";
  }

  @RequestMapping(value = "/pullSurveyDocument/{id}", method = RequestMethod.GET, headers = "Accept=application/octet-stream")
  public void pullSurveyDocument(HttpServletResponse response, @PathVariable String id) throws IOException {
	surveyService.getsurveyDocument(Long.valueOf(id),response);
	response.setContentType("application/octet-stream");
  }

  @RequestMapping(method = { RequestMethod.POST }, value = "/submitSurvey/{surveyId}")
  public @ResponseBody
  String submitSurvey(@ModelAttribute("answer") Answers answers, HttpServletRequest request, @PathVariable String surveyId) {
	long userId = surveyUtils.getLoggedInUser().getUserId();
	surveyService.saveSurveyAnswer(request, answers, userId);
	surveyService.submitSurvey(surveyId, userId);
	return "Sucess";
  }

  @RequestMapping(value = "/question/{questionId}/document", method = RequestMethod.GET)
  public void downloadQuestionHelpDocument(HttpServletResponse response, @PathVariable String questionId) throws IOException {
	surveyService.downloadQuestionHelpDocument(Long.valueOf(questionId),response);
  }
  
//  
}
