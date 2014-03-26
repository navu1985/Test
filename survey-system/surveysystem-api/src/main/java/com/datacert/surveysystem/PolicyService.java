package com.datacert.surveysystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.datacert.surveysystem.dto.Policy;
import com.datacert.surveysystem.dto.PolicyDocument;
import com.datacert.surveysystem.dto.SyncPolicyAudiencesXml;
import com.datacert.surveysystem.dto.SyncPolicyRelationsXml;
import com.datacert.surveysystem.policyresponse.PolicyAccessedResponsesXml;
import com.datacert.surveysystem.surveyresponse.PolicyResponseList;


public interface PolicyService {
 
  public Policy addPolicy(Policy policy,String applicationId);
  
  public void addPolicyDocument(Long policyDocId,InputStream inputStream) throws IOException;

  public PolicyResponseList setAddPolicyResponse(Policy policy);

  public List<Policy> getUnAckPolicies(long userId);
  
  public List<Policy> getAckPolicies(long userId);
  
  public Long getAckPoliciesCount(long userId);
  
  public Long getUnAckPoliciesCount(long userId);

  public void updatePolicy(Policy policy, String applicationId) throws Exception;
  
  public SyncPolicyRelationsXml updatePolicyRelations(SyncPolicyRelationsXml policyRelations, String applicationId);
  
  public SyncPolicyAudiencesXml updatePolicyAudiences(SyncPolicyAudiencesXml policyAudiences, String applicationId);

  public PolicyDocument getPolicyDocument(Long policyDocumentId);

  public void retirePolicy(String policyId, String applicationId);

  public void acknowledgepolicy(String policyId,long userId);

  public boolean hasPolicyDocumentAccess(String policyDocumentId,long userId);

  public void policyAccessed(String policyId, long userId);
  
  public PolicyAccessedResponsesXml policyAccessedByUsers();

  public List<Policy> policyFullTextSearch(String policyName, String policyTopic,
		String policyApplicationId, String policyType, String policyIssuedBy,
		String policyVersionDate, String policyRelatedDoc, String searchText,Long userId);

  public byte[] createThumbnailOfPdfFirstPage(Long policyDocId, byte[] pdfFilebuffer) throws IOException;
  
  //public PolicyThumbnail getPolicyThumbnail(Long policyId);
  
  public void getPolicyThumbnail(final Long policyId ,final OutputStream out);

  public void updatePolicyAcknowledgement(PolicyAccessedResponsesXml policyAccessedResponsesXml);

  public void getPolicyDocument(Long policyDocumentId, OutputStream out);

  public Long getRetiredPoliciesCount(long userId);
  
}
