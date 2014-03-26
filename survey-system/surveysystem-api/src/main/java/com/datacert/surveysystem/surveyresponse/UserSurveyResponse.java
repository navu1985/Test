package com.datacert.surveysystem.surveyresponse;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class UserSurveyResponse {
  private long questionId;
  private String questionResp;
  private String questionRespStart;
  private String questionRespEnd;
  private String questionRespFile;
  private Long questionRespFileSize;
  private Long questionRespFileID;

  @XmlElement(name = "questionId")
  public long getQuestionId() {
	return questionId;
  }

  public void setQuestionId(long questionId) {
	this.questionId = questionId;
  }

  @XmlElement(name = "questionResponse")
  public String getQuestionResp() {
	return questionResp;
  }

  public void setQuestionResp(String questionResp) {
	this.questionResp = questionResp;
  }

  public void setQuestionResp(Date questionResp) {
	this.questionResp = questionResp.toString();
  }

  @XmlElement(name = "questionResponseStart")
  public String getQuestionRespStart() {
	return questionRespStart;
  }

  public void setQuestionRespStart(String questionRespStart) {
	this.questionRespStart = questionRespStart;
  }

  public void setQuestionRespStart(Date questionRespStart) {
	this.questionRespStart = questionRespStart.toString();
  }

  public void setQuestionRespEnd(Date questionRespEnd) {
	this.questionRespEnd = questionRespEnd.toString();
  }

  @XmlElement(name = "questionResponseEnd")
  public String getQuestionRespEnd() {
	return questionRespEnd;
  }

  public void setQuestionRespEnd(String questionRespEnd) {
	this.questionRespEnd = questionRespEnd;
  }

  @XmlElement(name = "questionResponseFile")
  public String getQuestionRespFile() {
	return questionRespFile;
  }

  public void setQuestionRespFile(String questionRespFile) {
	this.questionRespFile = questionRespFile;
  }

  @XmlElement(name = "questionResponseFileId")
  public Long getQuestionRespFileID() {
	return questionRespFileID;
  }

  public void setQuestionRespFileID(Long questionRespFileID) {
	this.questionRespFileID = questionRespFileID;
  }

  @XmlElement(name = "questionResponseFileSize")
  public Long getQuestionRespFileSize() {
	return questionRespFileSize;
  }

  public void setQuestionRespFileSize(Long questionRespFileSize) {
	this.questionRespFileSize = questionRespFileSize;
  }
}
