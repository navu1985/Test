package com.datacert.surveysystem.dto;


public interface User {
  public String getUsername();

  public String getPassword();

  public long getContactId();

  public long getUserId();

  public int getEnable();

  public String getUrlIdentifier();

  public String getSurveyResponseId();
  
  public String getFirstName();
  
  public String getLastName();
}
