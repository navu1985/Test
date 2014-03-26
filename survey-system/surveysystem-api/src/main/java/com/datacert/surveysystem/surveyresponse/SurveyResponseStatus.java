package com.datacert.surveysystem.surveyresponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "surveyResponseStatus")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyResponseStatus {

  @XmlElement(name = "surveyId")
  public String surveyId;

  @XmlElement(name = "userId")
  public String contactId;

  @XmlElement(name = "responseId")
  public String responseId;

  public String getSurveyId() {
	return surveyId;
  }

  public void setSurveyId(String surveyId) {
	this.surveyId = surveyId;
  }

  public String getContactId() {
	return contactId;
  }

  public void setContactId(String contactId) {
	this.contactId = contactId;
  }

  public String getResponseId() {
	return responseId;
  }

  public void setResponseId(String responseId) {
	this.responseId = responseId;
  }

}
