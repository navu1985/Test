package com.datacert.portal.dto;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.datacert.surveysystem.dto.Application;
import com.datacert.surveysystem.dto.DaoCoreConstant;

public class ApplicationDto implements Application {
  
  private Long id;
  private String connectionStatus;
  
  @Length(max = 255)
  private String applicationName = "";
  
  private String applicationIdentifier;
  
  private String dbGuid;
  
  @Length(max = 255)
  private String contactPerson;
  
  @Length(max = 255)
  @Pattern(regexp=DaoCoreConstant.EMAIL_OR_EMPTY_PATTERN)
  private String contactEmail;
  
  private String contactPhone;
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getConnectionStatus() {
    return connectionStatus;
  }
  public void setConnectionStatus(String connectionStatus) {
    this.connectionStatus = connectionStatus;
  }
  public String getApplicationName() {
    return applicationName;
  }
  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }
  public String getApplicationIdentifier() {
    return applicationIdentifier;
  }
  public void setApplicationIdentifier(String applicationIdentifier) {
    this.applicationIdentifier = applicationIdentifier;
  }
  public String getDbGuid() {
    return dbGuid;
  }
  public void setDbGuid(String dbGuid) {
    this.dbGuid = dbGuid;
  }
  public String getContactPerson() {
    return contactPerson;
  }
  public void setContactPerson(String contactPerson) {
    this.contactPerson = contactPerson;
  }
  public String getContactEmail() {
    return contactEmail;
  }
  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }
  public String getContactPhone() {
    return contactPhone;
  }
  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }
  
}