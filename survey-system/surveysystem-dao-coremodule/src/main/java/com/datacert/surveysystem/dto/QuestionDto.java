package com.datacert.surveysystem.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "question")
public class QuestionDto implements Question, Comparable<QuestionDto> {

  private String questionDescription;
  private long questionId;
  private AnswerType answerType;
  private long sequence;
  private long passportQuestionId;
  private List<QuestionResponse> questionResponses = new ArrayList<QuestionResponse>();
  private boolean required;
  private boolean questionContainsDocument;
  private GenericAnswer answer;
  private String questionDocumentFilename;
  
  @XmlElement(name = "questionDescription")
  public String getQuestionDescription() {
	return questionDescription;
  }

  public void setQuestionDescription(String questionDescription) {
	this.questionDescription = questionDescription;
  }

  @XmlElement(name = "questionId")
  public long getQuestionId() {
	return questionId;
  }

  public void setQuestionId(long questionId) {
	this.questionId = questionId;
  }

  @XmlElement(name = "answerType")
  public AnswerType getAnswerType() {
	return answerType;
  }

  public void setAnswerType(AnswerType answerType) {
	this.answerType = answerType;
  }

  @XmlElement(name = "sequence")
  public long getSequence() {
	return sequence;
  }

  public void setSequence(long sequence) {
	this.sequence = sequence;
  }

  @XmlElementRefs({ @XmlElementRef(type = QuestionResponseDto.class) })
  public List<QuestionResponse> getQuestionResponses() {
	return questionResponses;
  }

  public void setQuestionResponses(List<QuestionResponse> questionResponses) {
	this.questionResponses = questionResponses;
  }

  @XmlElement(name = "isRequired")
  public boolean getRequired() {
	return required;
  }

  public void setRequired(boolean required) {
	this.required = required;
  }

  public long getPassportQuestionId() {
	return passportQuestionId;
  }

  public void setAnswer(GenericAnswer answer) {
	this.answer = answer;
  }

  @Override
  public GenericAnswer getAnswer() {
	return answer;
  }

  public void setPassportQuestionId(long passportQuestionId) {
	this.passportQuestionId = passportQuestionId;
  }

  @Override
  public int compareTo(QuestionDto question) {
	if (this.equals(question)) {
	  return 0;
	} else {
	  return 1;
	}
  }

  public boolean getQuestionContainsDocument() {
    return questionContainsDocument;
  }

  public void setQuestionContainsDocument(boolean questionContainsDocument) {
    this.questionContainsDocument = questionContainsDocument;
  }

  @XmlElement(name = "associatedDocumentName")
  public String getQuestionDocumentFilename() {
    return questionDocumentFilename;
  }

  public void setQuestionDocumentFilename(String questionDocumentFilename) {
    this.questionDocumentFilename = questionDocumentFilename;
  }

}
