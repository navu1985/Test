package com.datacert.surveysystem.db.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.datacert.surveysystem.dto.AnswerType;
import com.datacert.surveysystem.dto.GenericAnswer;
import com.datacert.surveysystem.dto.Question;
import com.datacert.surveysystem.dto.QuestionResponse;

@Entity
@Table(name = "pp_survey_response_question")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class QuestionDescriptor implements Serializable, Question, Comparable<QuestionDescriptor> {

  private static final long serialVersionUID = 895321048240082267L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long questionId;

  @Column(name = "pp_question_id")
  private long passportQuestionId;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name = "survey_id", referencedColumnName = "id")
  private SurveyDescriptor survey;

  @Column(name = "description")
  private String description;

  @Column(name = "answer_type")
  @Enumerated(EnumType.STRING)
  private AnswerType answerType;

  @Column(name = "sequence")
  private long sequence;

  @Column(name = "is_list_reponse")
  private boolean isListReponse;

  @Column(name = "isRequired")
  private boolean isRequired;
  
  @Column(name = "is_question_contains_document")
  private boolean questionContainsDocument;

  @Column(name = "document_filename")
  private String questionDocumentFilename;
  
  @OneToMany(targetEntity = ExpectedQuestionResponse.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "question_id", referencedColumnName = "id")
  @OrderBy("sequence")
  private Set<ExpectedQuestionResponse> expectedQuestionResponse;

  @OneToMany(targetEntity = UserQuestionResponse.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "question_id", referencedColumnName = "id")
  private Set<UserQuestionResponse> userQuestionResponse;

  public long getQuestionId() {
	return questionId;
  }

  public void setQuestionId(long questionId) {
	this.questionId = questionId;
  }

  public String getDescription() {
	return description;
  }

  public void setDescription(String description) {
	this.description = description;
  }

  public AnswerType getAnswerType() {
	return answerType;
  }

  public void setAnswerType(AnswerType answerType) {
	this.answerType = answerType;
  }

  public long getSequence() {
	return sequence;
  }

  public void setSequence(long sequence) {
	this.sequence = sequence;
  }

  public boolean getisListReponse() {
	return isListReponse;
  }

  public void setisListReponse(boolean isListReponse) {
	this.isListReponse = isListReponse;
  }

  public Set<ExpectedQuestionResponse> getExpectedQuestionResponse() {
	return expectedQuestionResponse;
  }

  public void setExpectedQuestionResponse(Set<ExpectedQuestionResponse> expectedQuestionResponse) {
	this.expectedQuestionResponse = expectedQuestionResponse;
  }

  public boolean isListReponse() {
	return isListReponse;
  }

  public void setListReponse(boolean isListReponse) {
	this.isListReponse = isListReponse;
  }

  public String getQuestionText() {
	return description;
  }

  public SurveyDescriptor getSurvey() {
	return survey;
  }

  public void setSurvey(SurveyDescriptor survey) {
	this.survey = survey;
  }

  public boolean isRequired() {
	return isRequired;
  }

  public boolean getRequired() {
	return isRequired;
  }

  public void setRequired(boolean isRequired) {
	this.isRequired = isRequired;
  }

  @Override
  public String getQuestionDescription() {
	return description;
  }

  @Override
  public List<QuestionResponse> getQuestionResponses() {
	List<QuestionResponse> questionResponse = new ArrayList<QuestionResponse>();
	for (ExpectedQuestionResponse expectedAnswer : expectedQuestionResponse) {
	  questionResponse.add((QuestionResponse) expectedAnswer);
	}
	return questionResponse;
  }

  @Override
  public int compareTo(QuestionDescriptor question) {
	if (this.equals(question)) {
	  return 0;
	} else {
	  return 1;
	}
  }

  public Set<UserQuestionResponse> getUserQuestionResponse() {
	return userQuestionResponse;
  }

  public void setUserQuestionResponse(Set<UserQuestionResponse> userQuestionResponse) {
	this.userQuestionResponse = userQuestionResponse;
  }

  public long getPassportQuestionId() {
	return passportQuestionId;
  }

  public void setPassportQuestionId(long passportQuestionId) {
	this.passportQuestionId = passportQuestionId;
  }

  @Override
  public GenericAnswer getAnswer() {
	return null;
  }

  public boolean getQuestionContainsDocument() {
    return questionContainsDocument;
  }

  public void setQuestionContainsDocument(boolean questionContainsDocument) {
    this.questionContainsDocument = questionContainsDocument;
  }

  public String getQuestionDocumentFilename() {
    return questionDocumentFilename;
  }

  public void setQuestionDocumentFilename(String questionDocumentFilename) {
    this.questionDocumentFilename = questionDocumentFilename;
  }
}
