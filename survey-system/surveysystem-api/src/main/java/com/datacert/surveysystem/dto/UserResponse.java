package com.datacert.surveysystem.dto;

import java.util.Date;

public interface UserResponse {
  public long getId();

  public long getUserId();

  public String getResponseText();

  public long getDocId();

  public Date getReponseDate();

  public long getSequence();

  public long getQuestionId();
}
