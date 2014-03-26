package com.datacert.surveysystem.dto;

import java.util.Date;
import java.util.List;

public interface Policy {

  public String getPolicyName();

  public Long getPolicyId();
  
  public Long getApplicationPolicyId();
  
  public String getIssuingDepartment();

  public String getPolicyType();

  public Date getPolicyEffectiveDate();
  
  public String getPolicyStatus();

  public String getPolicyTopic();
  
  public PolicyDocument getPolicyDocument();

  public List<? extends User> getAudienceMembers();
  
  public List<Policy> getRelatedPolicies();
  
  public Long getNoOfDueDays();
  
  public Boolean getAttestationRequired();
  
}
