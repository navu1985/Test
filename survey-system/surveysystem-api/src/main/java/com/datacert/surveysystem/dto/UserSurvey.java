package com.datacert.surveysystem.dto;

import java.util.Date;

public interface UserSurvey {
  public Survey getSurvey();

  public User getUser();

  public Date getLastError();
}
