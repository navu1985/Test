package com.datacert.surveysystem.surveyresponse;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "surveyPullResponse")
public class SurveyPullResponse {
  private String responseId;
  private long userContactId;
  private long surveyId;
  List<UserSurveyResponse> userSurveyResponse;

  @XmlElement(name = "userContactId")
  public long getUserContactId() {
	return userContactId;
  }

  public void setUserContactId(long userContactId) {
	this.userContactId = userContactId;
  }

  @XmlElement(name = "surveyId")
  public long getSurveyId() {
	return surveyId;
  }

  public void setSurveyId(long surveyId) {
	this.surveyId = surveyId;
  }
  
  @XmlElementWrapper(name = "responseList")
  @XmlElement(name = "response")
  public List<UserSurveyResponse> getUserSurveyResponse() {
	return userSurveyResponse;
  }

  public void setUserSurveyResponse(List<UserSurveyResponse> userSurveyResponse) {
	this.userSurveyResponse = userSurveyResponse;
  }

  @XmlElement(name = "responseId")
  public String getResponseId() {
    return responseId;
  }

  public void setResponseId(String responseId) {
    this.responseId = responseId;
  }
}
