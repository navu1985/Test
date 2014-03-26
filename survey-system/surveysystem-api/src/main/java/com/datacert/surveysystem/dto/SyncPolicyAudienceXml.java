package com.datacert.surveysystem.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "policyAudience")
@XmlAccessorType(XmlAccessType.FIELD)
public class SyncPolicyAudienceXml {

	@XmlElement(name = "syncId")
	public String syncId;

	@XmlElement(name = "policyId")
	public String applicationPolicyId;

	@XmlElement(name = "email")
	public String userName;

	@XmlElement(name = "firstName")
	public String firstName;

	@XmlElement(name = "lastName")
	public String lastName;

	@XmlElement(name = "audienceId")
	public String audienceId;

	@XmlElement(name = "policyAudienceStatus")
	public String realtionStatus;

	@XmlElement(name = "newlyAddedAudience")
	public Boolean newlyAddedAudience;
	
	@XmlElement(name = "dueDate")
	public Date dueDate;
	
	public String getSyncId() {
		return syncId;
	}

	public void setSyncId(String syncId) {
		this.syncId = syncId;
	}

	public String getApplicationPolicyId() {
		return applicationPolicyId;
	}

	public void setApplicationPolicyId(String applicationPolicyId) {
		this.applicationPolicyId = applicationPolicyId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealtionStatus() {
		return realtionStatus;
	}

	public void setRealtionStatus(String realtionStatus) {
		this.realtionStatus = realtionStatus;
	}

	public String getAudienceId() {
		return audienceId;
	}

	public void setAudienceId(String audienceId) {
		this.audienceId = audienceId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDueDate() {
	  return dueDate;
	}

	public void setDueDate(Date dueDate) {
	  this.dueDate = dueDate;
	}

	public Boolean getNewlyAddedAudience() {
	  return newlyAddedAudience;
	}

	public void setNewlyAddedAudience(Boolean newlyAddedAudience) {
	  this.newlyAddedAudience = newlyAddedAudience;
	}

}
