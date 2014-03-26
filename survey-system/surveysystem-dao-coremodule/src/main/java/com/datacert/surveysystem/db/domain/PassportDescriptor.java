package com.datacert.surveysystem.db.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.datacert.surveysystem.dto.Application;

@Entity
@Table(name = "ps_passport_details")
public class PassportDescriptor implements Application {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "connection_status")
  private String connectionStatus;

  @Column(name = "client_name")
  private String applicationName = "";

  @Column(name = "application_id")
  private String applicationIdentifier;
  
  @Column(name = "db_guid",unique=true)
  private String dbGuid;
  
  @Column(name = "contact_person")
  private String contactPerson;
  
  @Column(name = "contact_email")
  private String contactEmail;
  
  @Column(name = "contact_phone")
  private String contactPhone;
  
  @OneToMany(mappedBy="passport")
  @Cascade({CascadeType.DELETE})
  private Set<SurveyDescriptor> surveys;


  public String getConnectionStatus() {
	return connectionStatus;
  }

  public void setConnectionStatus(String connectionStatus) {
	this.connectionStatus = connectionStatus;
  }


  public Set<SurveyDescriptor> getSurveys() {
    return surveys;
  }

  public void setSurveys(Set<SurveyDescriptor> surveys) {
    this.surveys = surveys;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getApplicationIdentifier() {
    return applicationIdentifier;
  }

  public void setApplicationIdentifier(String applicationIdentifier) {
    this.applicationIdentifier = applicationIdentifier;
  }

  public String getApplicationName() {
    return applicationName;
  }

  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
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