package com.datacert.surveysystem.db.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.datacert.surveysystem.dto.DaoCoreConstant;

@Entity
@Table(name = "ps_user_policy")
@AssociationOverrides({ @AssociationOverride(name = "pk.policy", joinColumns = @JoinColumn(name = "policy_id")),
		@AssociationOverride(name = "pk.user", joinColumns = @JoinColumn(name = "user_id")) })
public class UserPolicy implements Serializable{

	private static final long serialVersionUID = 895321048240082267L;

	@EmbeddedId
	private UserPolicyId pk = new UserPolicyId();
	
	@Column(name = "acknowledge")
	public Boolean isAcknowledge;
	
	@Column(name = "acknowledge_on_date")
	public Date acknowledgeDate;

	@Column(name = "times_accessed")
	public long noOfTimesAccessed;
	
	@Column(name = "accessed_count_sync_status")
	public Boolean accessedCountSyncStatus;
	
	@Column(name = "audience_id")
	public long audienceId;
	
	@Column(name = "status")
	public String status;
	
	@Column(name = "due_date")
	public Date policyDueDate;
	
	public Boolean getAccessedCountSyncStatus() {
		return accessedCountSyncStatus;
	}

	public void setAccessedCountSyncStatus(Boolean accessedCountSyncStatus) {
		this.accessedCountSyncStatus = accessedCountSyncStatus;
	}

	public long getNoOfTimesAccessed() {
		return noOfTimesAccessed;
	}

	public void setNoOfTimesAccessed(long noOfTimesAccessed) {
		this.noOfTimesAccessed = noOfTimesAccessed;
	}

	public UserPolicy() {
	}
	
	public UserPolicy(UserDescriptor user , PolicyDescriptor policy,long audienceId, Boolean isAcknowledge) {
		setPolicy(policy);
		setUser(user);
		this.audienceId=audienceId;
		this.noOfTimesAccessed=0l;
		this.accessedCountSyncStatus=Boolean.TRUE;
		this.isAcknowledge=isAcknowledge;
		this.status=DaoCoreConstant.userPolicyStatus.ACTIVE.name();
	}

	@Transient
	public PolicyDescriptor getPolicy() {
		return getPk().getPolicy();
	}

	public void setPolicy(PolicyDescriptor policy) {
		getPk().setPolicy(policy);
	}

	@Transient
	public UserDescriptor getUser() {
		return getPk().getUser();
	}

	public void setUser(UserDescriptor user) {
		getPk().setUser(user);
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserPolicy that = (UserPolicy) o;
		if (getPk() != null ? !getPk().equals(that.getPk()) : that.getPk() != null)
			return false;
		return true;
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}

	public UserPolicyId getPk() {
		return pk;
	}

	public void setPk(UserPolicyId pk) {
		this.pk = pk;
	}

	public Boolean getIsAcknowledge() {
		return isAcknowledge;
	}

	public void setIsAcknowledge(Boolean isAcknowledge) {
		this.isAcknowledge = isAcknowledge;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getAcknowledgeDate() {
		return acknowledgeDate;
	}

	public void setAcknowledgeDate(Date acknowledgeDate) {
		this.acknowledgeDate = acknowledgeDate;
	}

	public long getAudienceId() {
		return audienceId;
	}

	public void setAudienceId(long audienceId) {
		this.audienceId = audienceId;
	}

	public Date getPolicyDueDate() {
	  return policyDueDate;
	}

	public void setPolicyDueDate(Date policyDueDate) {
	  this.policyDueDate = policyDueDate;
	}

}
