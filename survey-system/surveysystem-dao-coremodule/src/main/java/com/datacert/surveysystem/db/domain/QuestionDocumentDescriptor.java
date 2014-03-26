package com.datacert.surveysystem.db.domain;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pp_survey_response_question_document")
public class QuestionDocumentDescriptor {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long questionDocumentId;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "question_id")
  private QuestionDescriptor questionDescriptor;

  @Lob
  @Column(name = "doc_data")
  @Basic(fetch = FetchType.LAZY)
  private byte[] policyDocumentContent;

  public long getQuestionDocumentId() {
	return questionDocumentId;
  }

  public void setQuestionDocumentId(long questionDocumentId) {
	this.questionDocumentId = questionDocumentId;
  }

  public QuestionDescriptor getQuestionDescriptor() {
	return questionDescriptor;
  }

  public void setQuestionDescriptor(QuestionDescriptor questionDescriptor) {
	this.questionDescriptor = questionDescriptor;
  }

  public byte[] getPolicyDocumentContent() {
	return policyDocumentContent;
  }

  public void setPolicyDocumentContent(byte[] policyDocumentContent) {
	this.policyDocumentContent = policyDocumentContent;
  }

}
