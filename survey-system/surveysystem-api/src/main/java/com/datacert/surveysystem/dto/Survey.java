package com.datacert.surveysystem.dto;

import java.util.Date;
import java.util.Set;

public interface Survey {
  public long getSurveyId();

  public String getSurveyName();

  public Date getSurveyAssignDate();

  public Date getSurveyDueDate();
  
  public Application getPassport();

  public Set<Question> getQuestions();

  public Set<User> getUsers();
  
}
