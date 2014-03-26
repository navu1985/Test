package com.datacert.portal.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.datacert.portal.util.SurveyUtils;
import com.datacert.surveysystem.SurveyService;

@Controller
public class FileUploadController implements HandlerExceptionResolver {

  @Resource
  private SurveyUtils surveyUtils;

  @Resource
  SurveyService surveyService;

  @Value(value = "${surveyportal.validFileExtensions}")
  private String validFiles;

  @RequestMapping(method = { RequestMethod.POST }, value = "/fileUpload")
  public void fileUpload(MultipartHttpServletRequest request, HttpServletResponse response) throws IllegalStateException,
		  IOException {
	Long docId = 0L;
	response.setContentType("text/plain");
	long userId = surveyUtils.getLoggedInUser().getUserId();
	MultipartFile file = request.getFile("docFile");
	if (!isFileValid(file.getOriginalFilename())) {
	  response.getWriter().append("portal-inValidFileType");
	} else if (file != null) {
	  docId = surveyService.saveSurveyAnswerDocument(file, Long.valueOf(request.getParameter("questionId")), userId);
	  response.getWriter().append("{'" + docId.toString() + "'}");
	}
  }

  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
	if (ex instanceof MaxUploadSizeExceededException) {
	  return new ModelAndView("redirect: fileMaxError");
	} else if (ex instanceof FileNotFoundException) {
	  return new ModelAndView("redirect: fileUploadError");
	}
	return null;
  }

  @RequestMapping(method = { RequestMethod.GET }, value = "/fileMaxError")
  public void fileMaxError(HttpServletResponse response) throws IOException {
	response.setContentType("text/plain");
	response.getWriter().append("portal-inValidFileSize");
  }

  @RequestMapping(method = { RequestMethod.GET }, value = "/fileUploadError")
  public void fileUploadError(HttpServletResponse response) throws IOException {
	response.setContentType("text/plain");
	response.getWriter().append("portal-fileUploadError");
  }

  public boolean isFileValid(String fileName) {
	String fileExt = "";
	if (fileName.contains(".")) {
	  fileExt = fileName.substring(fileName.lastIndexOf("."));
	}
	String validFilesArray[] = validFiles.split(",");
	if (Arrays.asList(validFilesArray).contains(fileExt))
	  return true;
	else
	  return false;
  }

}
