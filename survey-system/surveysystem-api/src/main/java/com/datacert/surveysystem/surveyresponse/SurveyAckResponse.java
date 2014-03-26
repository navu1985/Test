package com.datacert.surveysystem.surveyresponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ackResponse")
public class SurveyAckResponse {

  
  private String surveyStatus;
  private String responseId;
  private String userContactId;
  private String surveyId;


  @XmlElement(name = "surveyStatus")
  public String getSurveyStatus() {
    return surveyStatus;
  }

  public void setSurveyStatus(String surveyStatus) {
    this.surveyStatus = surveyStatus;
  }

  @XmlElement(name = "respondentId")
  public String getUserContactId() {
	return userContactId;
  }

  public void setUserContactId(String userContactId) {
	this.userContactId = userContactId;
  }

  @XmlElement(name = "surveyId")
  public String getSurveyId() {
    return surveyId;
  }

  public void setSurveyId(String surveyId) {
    this.surveyId = surveyId;
  }

 
  @XmlElement(name = "responseId")
  public String getResponseId() {
    return responseId;
  }

  public void setResponseId(String responseId) {
    this.responseId = responseId;
  }
}


