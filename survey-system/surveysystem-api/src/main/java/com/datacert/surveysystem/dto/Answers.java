package com.datacert.surveysystem.dto;

import java.util.List;

public class Answers {

  private int currrentPage = 1;

  private long surveyId;

  private List<GenericAnswer> answers;

  public List<GenericAnswer> getAnswers() {
	return answers;
  }

  public void setAnswers(List<GenericAnswer> answers) {
	this.answers = answers;
  }

  public int getCurrrentPage() {
	return currrentPage;
  }

  public void setCurrrentPage(int currrentPage) {
	this.currrentPage = currrentPage;
  }

  public long getSurveyId() {
	return surveyId;
  }

  public void setSurveyId(long surveyId) {
	this.surveyId = surveyId;
  }

}
