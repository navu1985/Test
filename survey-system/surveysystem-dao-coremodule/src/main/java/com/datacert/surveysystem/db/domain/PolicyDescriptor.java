package com.datacert.surveysystem.db.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import com.datacert.surveysystem.dto.Policy;
import com.datacert.surveysystem.dto.PolicyDocument;
import com.datacert.surveysystem.dto.User;

/**
 * @author parora
 * 
 */

@Entity
@Table(name = "pp_policy_request")
@FilterDef(name="relatedPolicyStatusFilter",parameters=@ParamDef( name="relatedPolicyStatus", type="string" ) )
public class PolicyDescriptor implements Serializable, Policy {

	private static final long serialVersionUID = 895321048240082267L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long policyId;

	@Column(name = "name")
	private String policyName;

	@Column(name = "passport_policy_id")
	private Long applicationPolicyId;

	@Column(name = "status")
	private String policyStatus;

	@Column(name = "artifact_type")
	private String policyType;

	@Column(name = "issuing_department")
	public String issuingDepartment;

	@Column(name = "effective_from_date")
	public Date policyEffectiveDate;

	@Column(name = "topic")
	public String policyTopic;
	
	@Column(name = "attestation_required")
	public Boolean attestationRequired;
	
	@Column(name = "due_days")
	public Long noOfDueDays;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "policy_document_id")
	private PolicyDocumentInfoDescriptor policyDocumentDescriptor;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.policy", cascade = CascadeType.ALL)
	private Set<UserPolicy> userPolicies = new  HashSet<UserPolicy>();

	@ManyToMany(mappedBy = "relatedPolicies")
	private Set<PolicyDescriptor> associatedPolicies = new HashSet<PolicyDescriptor>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ps_relatedpolicy_policy", joinColumns = { @JoinColumn(name = "policy_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "related_policy_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false) })
	@Filter(
			name = "relatedPolicyStatusFilter",
			condition="status = :relatedPolicyStatus"
		)
	private List<PolicyDescriptor> relatedPolicies = new ArrayList<PolicyDescriptor>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "passport_id")
	private PassportDescriptor passport;

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public Long getApplicationPolicyId() {
		return applicationPolicyId;
	}

	public void setApplicationPolicyId(Long applicationPolicyId) {
		this.applicationPolicyId = applicationPolicyId;
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

	public PassportDescriptor getPassport() {
		return passport;
	}

	public void setPassport(PassportDescriptor passport) {
		this.passport = passport;
	}

	public List<? extends User> getAudienceMembers() {
		return null;
	}

	public String getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(String policyStatus) {
		this.policyStatus = policyStatus;
	}

	public PolicyDocumentInfoDescriptor getPolicyDocumentDescriptor() {
		return policyDocumentDescriptor;
	}

	public void setPolicyDocumentDescriptor(
			PolicyDocumentInfoDescriptor policyDocumentDescriptor) {
		this.policyDocumentDescriptor = policyDocumentDescriptor;
	}

	@Override
	public PolicyDocument getPolicyDocument() {
		return (PolicyDocument) policyDocumentDescriptor;
	}

	public List<Policy> getRelatedPolicies() {
		List<Policy> temprelatedPolicies = new ArrayList<Policy>();
		for (PolicyDescriptor policy : relatedPolicies) {
			temprelatedPolicies.add(policy);
		}
		return temprelatedPolicies;
	}

	public void setRelatedPolicies(List<PolicyDescriptor> relatedPolicies) {
		this.relatedPolicies = relatedPolicies;
	}

	public PolicyDescriptor addRelatedPolicy(PolicyDescriptor policy) {
		if (!relatedPolicies.contains(policy)) {
			relatedPolicies.add(policy);
			policy.getRelatedPolicies().add(this);
		}
		return policy;
	}

	public void removeRelatedPolicy(PolicyDescriptor policy) {
		if (relatedPolicies.contains(policy)) {
			relatedPolicies.remove(policy);
			policy.getRelatedPolicies().remove(this);
		}
	}

	public void addAudienceMember(UserPolicy userPolicy) {
		userPolicies.add(userPolicy);
	}

	public void removeAudienceMember(UserPolicy userPolicy) {
		userPolicies.remove(userPolicy);
	}

	public Set<PolicyDescriptor> getAssociatedPolicies() {
		return associatedPolicies;
	}

	public void setAssociatedPolicies(Set<PolicyDescriptor> associatedPolicies) {
		this.associatedPolicies = associatedPolicies;
	}

	public Set<UserPolicy> getUserPolicies() {
		return userPolicies;
	}

	public void setUserPolicies(Set<UserPolicy> userPolicies) {
		this.userPolicies = userPolicies;
	}

	public Boolean getAttestationRequired() {
		return attestationRequired;
	}

	public void setAttestationRequired(Boolean attestationRequired) {
		this.attestationRequired = attestationRequired;
	}

	public Long getNoOfDueDays() {
		return noOfDueDays;
	}

	public void setNoOfDueDays(Long noOfDueDays) {
		this.noOfDueDays = noOfDueDays;
	}

}
