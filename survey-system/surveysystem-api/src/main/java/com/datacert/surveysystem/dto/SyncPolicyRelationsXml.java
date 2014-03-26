package com.datacert.surveysystem.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "policyRelations")
@XmlAccessorType(XmlAccessType.FIELD)
public class SyncPolicyRelationsXml {
  
  @XmlElement(name="policyRelation")
  List<SyncPolicyRelationXml> policyRealtions = new ArrayList<SyncPolicyRelationXml>();

  public List<SyncPolicyRelationXml> getPolicyRealtions() {
    return policyRealtions;
  }

  public void setPolicyRealtions(List<SyncPolicyRelationXml> policyRealtions) {
    this.policyRealtions = policyRealtions;
  }
  
}
