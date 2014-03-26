package com.datacert.surveysystem.policyresponse;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "policiesAccessCounts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PolicyAccessedResponsesXml {

	@XmlElement(name="policiesAccessCount")
	private List<PolicyAccessedResponseXml> policyAccessedResponse;

	public List<PolicyAccessedResponseXml> getPolicyAccessedResponse() {
		return policyAccessedResponse;
	}

	public void setPolicyAccessedResponse(
			List<PolicyAccessedResponseXml> policyAccessedResponse) {
		this.policyAccessedResponse = policyAccessedResponse;
	}  

}
