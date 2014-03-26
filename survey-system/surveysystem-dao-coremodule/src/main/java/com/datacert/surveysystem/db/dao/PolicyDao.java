package com.datacert.surveysystem.db.dao;

import java.io.OutputStream;
import java.util.List;

import com.datacert.surveysystem.db.domain.PolicyDescriptor;
import com.datacert.surveysystem.db.domain.PolicyDocumentDescriptor;
import com.datacert.surveysystem.dto.Policy;
import com.datacert.surveysystem.dto.PolicyThumbnail;
import com.datacert.surveysystem.dto.SyncPolicyAudiencesXml;
import com.datacert.surveysystem.dto.SyncPolicyRelationsXml;
import com.datacert.surveysystem.policyresponse.PolicyAccessedResponsesXml;

/**
 * @author parora (2013-08-20 20:12:14.837)
 * 
 */

public interface PolicyDao {

  public PolicyDescriptor addPolicy(Policy policy, String applicationId);

  public PolicyDescriptor getPolicy(Long policyId);

  public List<? extends Policy> getUnAckPolicies(Long userId);

  public List<? extends Policy> getAckPolicies(long userId);

  public PolicyDocumentDescriptor getPolicyDocument(Long policyDocId);

  public void addPolicyDocument(PolicyDocumentDescriptor policyDocument);

  public PolicyDescriptor getPolicyfromApplicationPolicyId(Long applicationPolicyId, String applicationId);

  public SyncPolicyRelationsXml updatePolicyRelations(SyncPolicyRelationsXml policyRelations, String applicationId);

  public SyncPolicyAudiencesXml updatePolicyAudiences(SyncPolicyAudiencesXml policyAudiences, String applicationId);

  public void retirePolicy(String policyId, String applicationId);

  public Long getAckPoliciesCount(long userId);

  public Long getUnAckPoliciesCount(long userId);

  public void acknowledgepolicy(String policyId, long userId);

  public boolean hasPolicyDocumentAccess(String policyDocumentId, long userId);

  public void policyAccessed(String policyId, long userId);

  public PolicyAccessedResponsesXml policyAccessedByUsers();

//  public PolicyThumbnail getPolicyThumbnail(Long policyId);
  public void getPolicyThumbnail(final Long policyId ,final OutputStream out);

  public List<? extends Policy> policyFullTextSearch(String policyName, String policyTopic, String policyApplicationId,
		  String policyType, String policyIssuedBy, String policyVersionDate, String policyRelatedDoc, String searchText,
		  Long userId);

  public void updatePolicyAcknowledgement(PolicyAccessedResponsesXml policyAccessedResponsesXml);

  // Test
  public List<PolicyDescriptor> getRelatedPolicies(long userId, long policyId);

  public void getPolicyDocument(Long policyDocumentId, OutputStream out);

  public void updatePolicy(Policy policy, String applicationId) throws Exception;

  public Long getRetiredPoliciesCount(long userId);
  
//  public List<Long>  fullTextSearch(String searchData, final Long userId);

}