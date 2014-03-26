package com.datacert.surveysystem.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "policyAudiences")
@XmlAccessorType(XmlAccessType.FIELD)
public class SyncPolicyAudiencesXml {
  
  @XmlElement(name="policyAudience")
  List<SyncPolicyAudienceXml > policyAudiences = new ArrayList<SyncPolicyAudienceXml>();

  public List<SyncPolicyAudienceXml> getPolicyAudiences() {
    return policyAudiences;
  }

  public void setPolicyAudiences(List<SyncPolicyAudienceXml> policyAudiences) {
    this.policyAudiences = policyAudiences;
  }

  
}
