package com.datacert.surveysystem.dto;

import java.util.List;

public interface Question {

  public String getQuestionDescription();

  public long getQuestionId();

  public long getPassportQuestionId();

  public AnswerType getAnswerType();

  public GenericAnswer getAnswer();

  public long getSequence();

  public boolean getRequired();

  public List<QuestionResponse> getQuestionResponses();
  
  public boolean getQuestionContainsDocument();
  
  public String getQuestionDocumentFilename();
}
