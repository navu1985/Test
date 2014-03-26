package com.datacert.surveysystem.dto;

public interface Application {

  public Long getId();

  public String getApplicationName();

  public String getApplicationIdentifier();

  public String getDbGuid();

  public String getContactPerson();

  public String getContactEmail();

  public String getContactPhone();
}
