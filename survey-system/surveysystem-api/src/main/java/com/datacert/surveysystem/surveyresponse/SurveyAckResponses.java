package com.datacert.surveysystem.surveyresponse;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ackResponseList")
public class SurveyAckResponses {

  List<SurveyAckResponse> surveyAckResponseList;

  public SurveyAckResponses() {
  }

  @XmlElement(name = "ackResponse")
  public List<SurveyAckResponse> getSurveyAckResponseList() {
	return surveyAckResponseList;
  }

  public void setSurveyAckResponseList(List<SurveyAckResponse> surveyAckResponseList) {
	this.surveyAckResponseList = surveyAckResponseList;
  }
}
