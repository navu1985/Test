package com.datacert.surveysystem.surveyresponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pullSurveys")
public class PullSurveys {

  private String batchSize;

  @XmlElement(name = "batchSize")
  public String getBatchSize() {
	return batchSize;
  }

  public void setBatchSize(String batchSize) {
	this.batchSize = batchSize;
  }

}
