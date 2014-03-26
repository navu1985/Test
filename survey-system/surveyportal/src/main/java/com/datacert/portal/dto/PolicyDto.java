package com.datacert.portal.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.datacert.surveysystem.dto.Policy;
import com.datacert.surveysystem.dto.PolicyDocument;
import com.datacert.surveysystem.dto.User;
import com.datacert.surveysystem.dto.UserDto;

@XmlRootElement(name = "policy")
@XmlAccessorType(XmlAccessType.FIELD)
public class PolicyDto implements Policy {

  @XmlElement(name = "policyName")
  public String policyName;

  @XmlElement(name = "policyId")
  public Long applicationPolicyId;

  @XmlElement(name = "artifactType")
  public String policyType;
  
  @XmlElement(name = "issuingDepartment")
  public String issuingDepartment;

  @XmlElement(name = "effectiveFrom")
  public String policyEffectiveDate;

  @XmlElement(name = "topic")
  public String policyTopic;

  @XmlElementRef(name = "policyDocument", type = PolicyDocumentDto.class)
  public PolicyDocument policyDocument;

  @XmlElementWrapper(name = "audienceMembers")
  @XmlElementRefs({ @XmlElementRef(type = UserDto.class) })
  public List<User> audienceMembers;

  @XmlElement(name = "attestationRequired",required=true)
  public Boolean attestationRequired;

  @XmlElement(name = "dueDays")
  public Long noOfDueDays;
  
  public Long policyId;

  public String policyStatus;
  
  @XmlElementWrapper(name = "relatedPolicyList")
  @XmlElementRefs({ @XmlElementRef(type = PolicyDto.class) })
  public List<Policy> relatedPolicies = new ArrayList<Policy>();

  public String getPolicyName() {
	return policyName;
  }

  public void setPolicyName(String policyName) {
	this.policyName = policyName;
  }

  public Long getPolicyId() {
	return policyId;
  }

  public void setPolicyId(Long policyId) {
	this.policyId = policyId;
  }

  public String getPolicyType() {
	return policyType;
  }

  public void setPolicyType(String policyType) {
	this.policyType = policyType;
  }
  
  public String getIssuingDepartment() {
		return issuingDepartment;
	}

	public void setIssuingDepartment(String issuingDepartment) {
		this.issuingDepartment = issuingDepartment;
	}


  public Date getPolicyEffectiveDate() {
	Date d = null;
	try {
	  d = (Date) new SimpleDateFormat("yyyy-MM-dd hh:ss:mm.SSS").parse(policyEffectiveDate);
	} catch (ParseException e) {
	}
	return d;
  }

  public void setPolicyEffectiveDate(String policyEffectiveDate) {
	this.policyEffectiveDate = policyEffectiveDate;
  }

  public String getPolicyTopic() {
	return policyTopic;
  }

  public void setPolicyTopic(String policyTopic) {
	this.policyTopic = policyTopic;
  }

  public Long getApplicationPolicyId() {
	return applicationPolicyId;
  }

  public void setApplicationPolicyId(Long applicationPolicyId) {
	this.applicationPolicyId = applicationPolicyId;
  }

  public void setAudienceMembers(List<User> audienceMembers) {
	this.audienceMembers = audienceMembers;
  }

  public String getPolicyStatus() {
	return policyStatus;
  }

  public void setPolicyStatus(String policyStatus) {
	this.policyStatus = policyStatus;
  }

  public PolicyDocument getPolicyDocument() {
	return policyDocument;
  }

  public void setPolicyDocument(PolicyDocument policyDocument) {
	this.policyDocument = policyDocument;
  }

  public List<Policy> getRelatedPolicies() {
	return relatedPolicies;
  }

  public void setRelatedPolicies(List<Policy> relatedPolicies) {
	this.relatedPolicies = relatedPolicies;
  }

  @Override
  public List<? extends User> getAudienceMembers() {
	return audienceMembers;
  }

  public Boolean getAttestationRequired() {
 	return attestationRequired;
  }

  public void setAttestationRequired(Boolean attestationRequired) {
	this.attestationRequired = attestationRequired;
  }

  public Long getNoOfDueDays() {
	return noOfDueDays;
  } 

  public void setNoOfDueDays(Long noOfDueDays) {
	this.noOfDueDays = noOfDueDays;
  }

}
