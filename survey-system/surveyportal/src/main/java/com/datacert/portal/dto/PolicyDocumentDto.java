package com.datacert.portal.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.datacert.surveysystem.dto.PolicyDocument;

@XmlRootElement(name = "policyDocument")
@XmlAccessorType(XmlAccessType.FIELD)
public class PolicyDocumentDto implements PolicyDocument {

  @XmlElement(name = "documentName")
  public String policyDocumentName;

  @XmlElement(name = "documentSize")
  public Long policyDocumentSize;

  public Long policyDocumentId;

  public String getPolicyDocumentName() {
	return policyDocumentName;
  }

  public void setPolicyDocumentName(String policyDocumentName) {
	this.policyDocumentName = policyDocumentName;
  }

  public Long getPolicyDocumentSize() {
	return policyDocumentSize;
  }

  public void setPolicyDocumentSize(Long policyDocumentSize) {
	this.policyDocumentSize = policyDocumentSize;
  }

  public Long getPolicyDocumentId() {
	return policyDocumentId;
  }

  public void setPolicyDocumentId(Long policyDocumentId) {
	this.policyDocumentId = policyDocumentId;
  }

  public byte[] getPolicyDocumentContent() {
	return null;
  }

}
