package com.datacert.surveysystem.surveyresponse;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PolicyResponseList")
@XmlAccessorType(XmlAccessType.FIELD)
public class PolicyResponseList {
  @XmlElement(name = "policyResponse")
  List<PolicyResponse> policyResponse;

  public PolicyResponseList() {
  }
  
  public PolicyResponseList(List<PolicyResponse> policyResponse) {
    this.policyResponse = policyResponse;
  }
  
  public List<PolicyResponse> getPolicyResponse() {
    return policyResponse;
  }

  public void setPolicyResponse(List<PolicyResponse> policyResponse) {
    this.policyResponse = policyResponse;
  }

}
