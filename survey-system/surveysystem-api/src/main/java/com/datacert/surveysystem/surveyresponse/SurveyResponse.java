package com.datacert.surveysystem.surveyresponse;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "surveyResponseList")
public class SurveyResponse {

  List<SurveyPullResponse> surveyPullResponse;

  @XmlElement(name = "surveyPullResponse")
  public List<SurveyPullResponse> getSurveyPullResponse() {
	return surveyPullResponse;
  }

  public void setSurveyPullResponse(List<SurveyPullResponse> surveyPullResponse) {
	this.surveyPullResponse = surveyPullResponse;
  }
}
