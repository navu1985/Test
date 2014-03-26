package com.datacert.surveysystem.dto;

import java.math.BigInteger;
import java.util.Date;



public class PolicyFullTextDto{

	private Integer policyId;
	private String policyName;
	private BigInteger applicationPolicyId;
	private String policyStatus;
	private String policyType;
	public String issuingDepartment;
	public Date policyEffectiveDate;
	public String policyTopic;
	private BigInteger policyDocumentId;
	private String policyDocumentName;
	private BigInteger policyDocumentSize;
	public Date policyDocumentLastModified;
	public Integer getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public BigInteger getApplicationPolicyId() {
		return applicationPolicyId;
	}
	public void setApplicationPolicyId(BigInteger applicationPolicyId) {
		this.applicationPolicyId = applicationPolicyId;
	}
	public String getPolicyStatus() {
		return policyStatus;
	}
	public void setPolicyStatus(String policyStatus) {
		this.policyStatus = policyStatus;
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
		return policyEffectiveDate;
	}
	public void setPolicyEffectiveDate(Date policyEffectiveDate) {
		this.policyEffectiveDate = policyEffectiveDate;
	}
	public String getPolicyTopic() {
		return policyTopic;
	}
	public void setPolicyTopic(String policyTopic) {
		this.policyTopic = policyTopic;
	}
	public BigInteger getPolicyDocumentId() {
		return policyDocumentId;
	}
	public void setPolicyDocumentId(BigInteger policyDocumentId) {
		this.policyDocumentId = policyDocumentId;
	}
	public String getPolicyDocumentName() {
		return policyDocumentName;
	}
	public void setPolicyDocumentName(String policyDocumentName) {
		this.policyDocumentName = policyDocumentName;
	}
	public BigInteger getPolicyDocumentSize() {
		return policyDocumentSize;
	}
	public void setPolicyDocumentSize(BigInteger policyDocumentSize) {
		this.policyDocumentSize = policyDocumentSize;
	}
	public Date getPolicyDocumentLastModified() {
		return policyDocumentLastModified;
	}
	public void setPolicyDocumentLastModified(Date policyDocumentLastModified) {
		this.policyDocumentLastModified = policyDocumentLastModified;
	}
}
