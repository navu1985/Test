package com.datacert.surveysystem.surveyresponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "addPolicyResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class PolicyResponse {

  @XmlElement(name = "policyId")
  private String policyId;
  
  @XmlElement(name = "policyDocId")
  private String policyDocumentID;

  public String getPolicyId() {
    return policyId;
  }

  public void setPolicyId(String policyId) {
    this.policyId = policyId;
  }

  public String getPolicyDocumentID() {
    return policyDocumentID;
  }

  public void setPolicyDocumentID(String policyDocumentID) {
    this.policyDocumentID = policyDocumentID;
  }

}
