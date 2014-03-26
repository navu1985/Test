package com.datacert.surveysystem.policyresponse;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "policiesAccessCount")
@XmlAccessorType(XmlAccessType.FIELD)
public class PolicyAccessedResponseXml {

	@XmlElement(name = "policyId")
	private Long policyId;

	@XmlElement(name = "email")
	private String username;

	@XmlElement(name = "audienceId")
	private Long audienceId;

	@XmlElement(name = "accessedCount")
	private long accessedCount;
	
	@XmlElement(name = "acknowledgeDate")
	private Date acknowledgeDate;
	
	@XmlElement(name = "syncStatus")
	private Boolean syncStatus;

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getAccessedCount() {
		return accessedCount;
	}

	public void setAccessedCount(long accessedCount) {
		this.accessedCount = accessedCount;
	}

	public Long getAudienceId() {
		return audienceId;
	}

	public void setAudienceId(Long audienceId) {
		this.audienceId = audienceId;
	}

	public Date getAcknowledgeDate() {
		return acknowledgeDate;
	}

	public void setAcknowledgeDate(Date acknowledgeDate) {
		this.acknowledgeDate = acknowledgeDate;
	}

	public Boolean getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Boolean syncStatus) {
		this.syncStatus = syncStatus;
	}

}
