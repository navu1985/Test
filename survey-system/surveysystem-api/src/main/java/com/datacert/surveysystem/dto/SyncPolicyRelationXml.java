package com.datacert.surveysystem.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "policyRelation")
@XmlAccessorType(XmlAccessType.FIELD)
public class SyncPolicyRelationXml {

  @XmlElement(name = "syncId")
  public String syncId;
  
  @XmlElement(name = "policyId")
  public String applicationPolicyId;
  
  @XmlElement(name = "relatedPolicyId")
  public String applicationRelatedPolicyId;
  
  @XmlElement(name = "policyRealtionStatus")
  public String realtionStatus;

  public String getApplicationPolicyId() {
    return applicationPolicyId;
  }

  public void setApplicationPolicyId(String applicationPolicyId) {
    this.applicationPolicyId = applicationPolicyId;
  }

  public String getApplicationRelatedPolicyId() {
    return applicationRelatedPolicyId;
  }

  public void setApplicationRelatedPolicyId(String applicationRelatedPolicyId) {
    this.applicationRelatedPolicyId = applicationRelatedPolicyId;
  }

  public String getRealtionStatus() {
    return realtionStatus;
  }

  public void setRealtionStatus(String realtionStatus) {
    this.realtionStatus = realtionStatus;
  }

  public String getSyncId() {
    return syncId;
  }

  public void setSyncId(String syncId) {
    this.syncId = syncId;
  }
}
