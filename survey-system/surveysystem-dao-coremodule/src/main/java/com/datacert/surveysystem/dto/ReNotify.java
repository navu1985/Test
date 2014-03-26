package com.datacert.surveysystem.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "renotify")
public class ReNotify{

	private long surveyId;
	private long surveyResponseId;
	private long userPassportId;
	private String userName;

	@XmlElement(name = "surveyId")
	public long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(long surveyId) {
		this.surveyId = surveyId;
	}
	
	@XmlElement(name = "surveyResponseId")
	public long getSurveyResponseId() {
		return surveyResponseId;
	}

	public void setSurveyResponseId(long surveyResponseId) {
		this.surveyResponseId = surveyResponseId;
	}

	@XmlElement(name = "contactId")
	public long getUserPassportId() {
		return userPassportId;
	}

	public void setUserPassportId(long userPassportId) {
		this.userPassportId = userPassportId;
	}

	@XmlElement(name = "userName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
