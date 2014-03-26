package com.datacert.surveysystem.db;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFImageWriter;
import org.springframework.stereotype.Service;

import com.datacert.surveysystem.PolicyService;
import com.datacert.surveysystem.db.dao.PolicyDao;
import com.datacert.surveysystem.db.dao.SurveyDao;
import com.datacert.surveysystem.db.dao.UserDao;
import com.datacert.surveysystem.db.domain.PolicyDocumentDescriptor;
import com.datacert.surveysystem.dto.Policy;
import com.datacert.surveysystem.dto.PolicyDocument;
import com.datacert.surveysystem.dto.SyncPolicyAudiencesXml;
import com.datacert.surveysystem.dto.SyncPolicyRelationsXml;
import com.datacert.surveysystem.policyresponse.PolicyAccessedResponsesXml;
import com.datacert.surveysystem.surveyresponse.PolicyResponse;
import com.datacert.surveysystem.surveyresponse.PolicyResponseList;

@Service("policyService")
public class PolicyServiceImpl implements PolicyService {

	@Resource
	PolicyDao policyDao;

	@Resource
	SurveyDao surveyDao;

	@Resource
	UserDao userDao;

	public Policy addPolicy(Policy policy, String applicationId) {
		return (Policy) policyDao.addPolicy(policy, applicationId);
	}

	public void addPolicyDocument(Long policyDocId, InputStream inputStream)throws IOException {
		byte[] bytes =IOUtils.toByteArray(inputStream);
		PolicyDocumentDescriptor policyDocumentDescriptor = policyDao.getPolicyDocument(policyDocId);
		policyDocumentDescriptor.setPolicyDocumentContent(bytes);
		byte[] policyThumbnailbytes = createThumbnailOfPdfFirstPage(policyDocId,bytes);
		policyDocumentDescriptor.setPolicyThumbnail(policyThumbnailbytes);
		policyDao.addPolicyDocument(policyDocumentDescriptor);
	}

	public byte[] createThumbnailOfPdfFirstPage(Long policyDocId, byte[] pdfFilebuffer) throws IOException {
		String password = "",outputPrefix = "policyThumbnail"+String.valueOf(new Date().getTime()),imageFormat = "png";
		/*
		 * resolution dots per inch
		*/
		int startPage = 1,endPage = 1,resolution = 13;
		File file = null,policyThumbnailFile=null;;
		FileOutputStream fileOuputStream = null;
		PDDocument document = null;
		FileInputStream fileInputStream =null;
		byte[] thumbnailByteBuffer=null;
		try {
			file = new File("policy" + String.valueOf(policyDocId) + ".pdf");
			fileOuputStream =new FileOutputStream(file); 
		    fileOuputStream.write(pdfFilebuffer);
			document = PDDocument.load(file.getName());
			if (document.isEncrypted()) {
			  document.decrypt("");
			  if (document.isEncrypted())
				return thumbnailByteBuffer;
			}
			PDFImageWriter imageWriter = new PDFImageWriter();
			
			imageWriter.writeImage(document, imageFormat,password, startPage, endPage, outputPrefix, BufferedImage.TYPE_INT_RGB,resolution);
			String  policyThumbnailFileName=outputPrefix+startPage+"."+imageFormat;
			policyThumbnailFile = new File(policyThumbnailFileName);
			thumbnailByteBuffer = new byte[(int) policyThumbnailFile.length()];
			fileInputStream = new FileInputStream(policyThumbnailFile);
        	fileInputStream.read(thumbnailByteBuffer);
		} catch (Exception ex) {
		  System.out.println("here"+ex.getMessage());
		  	ex.printStackTrace();
			return thumbnailByteBuffer;
		} finally {
		  if (fileOuputStream != null)
			fileOuputStream.close();
		  if (fileInputStream != null)
			fileInputStream.close();
			if (document != null) {
				document.close();
			}
			if(file.exists())
			  file.delete();
			if(policyThumbnailFile!=null && policyThumbnailFile.exists())
			  policyThumbnailFile.delete();
		}
		return thumbnailByteBuffer;
	}

	public PolicyResponseList setAddPolicyResponse(Policy policy) {
		List<PolicyResponse> addPolicyResponseList = new ArrayList<PolicyResponse>();
		PolicyResponse policyResponse = new PolicyResponse();
		policyResponse.setPolicyId(String.valueOf(policy
				.getApplicationPolicyId()));
		if (policy.getPolicyDocument() != null
				&& String.valueOf(policy.getPolicyDocument()
						.getPolicyDocumentId()) != null)
			policyResponse.setPolicyDocumentID(String.valueOf(policy
					.getPolicyDocument().getPolicyDocumentId()));
		if (!policy.getRelatedPolicies().isEmpty()) {
			for (Policy relatedPolicy : policy.getRelatedPolicies()) {
				if (relatedPolicy.getPolicyDocument() != null
						&& String.valueOf(relatedPolicy.getPolicyDocument()
								.getPolicyDocumentId()) != null) {
					PolicyResponse relatedPolicyResponse = new PolicyResponse();
					relatedPolicyResponse.setPolicyId(String
							.valueOf(relatedPolicy.getApplicationPolicyId()));
					relatedPolicyResponse.setPolicyDocumentID(String
							.valueOf(relatedPolicy.getPolicyDocument()
									.getPolicyDocumentId()));
					addPolicyResponseList.add(relatedPolicyResponse);
				}
			}
		}
		addPolicyResponseList.add(policyResponse);
		return new PolicyResponseList(addPolicyResponseList);
	}

	public void updatePolicy(Policy policy, String applicationId) throws Exception {
	  policyDao.updatePolicy(policy, applicationId);
	}

	public SyncPolicyRelationsXml updatePolicyRelations(
			SyncPolicyRelationsXml policyRelations, String applicationId) {
		return policyDao.updatePolicyRelations(policyRelations, applicationId);
	}

	public SyncPolicyAudiencesXml updatePolicyAudiences(
			SyncPolicyAudiencesXml policyAudiences, String applicationId) {
		return policyDao.updatePolicyAudiences(policyAudiences, applicationId);
	}

	public PolicyDocument getPolicyDocument(Long policyDocumentId) {
		return policyDao.getPolicyDocument(policyDocumentId);
	}

	public void retirePolicy(String policyId, String applicationId) {
		policyDao.retirePolicy(policyId, applicationId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Policy> getUnAckPolicies(long userId) {
		return (List<Policy>) policyDao.getUnAckPolicies(userId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Policy> getAckPolicies(long userId) {
		List<Policy> policies = (List<Policy>) policyDao.getAckPolicies(userId);
		return policies;
	}

	@Override
	public Long getAckPoliciesCount(long userId) {
		return policyDao.getAckPoliciesCount(userId);
	}

	@Override
	public Long getUnAckPoliciesCount(long userId) {
		return policyDao.getUnAckPoliciesCount(userId);
	}

	@Override
	public void acknowledgepolicy(String policyId, long userId) {
		policyDao.acknowledgepolicy(policyId, userId);
	}

	@Override
	public boolean hasPolicyDocumentAccess(String policyDocumentId, long userId) {
		return policyDao.hasPolicyDocumentAccess(policyDocumentId, userId);
	}

	@Override
	public void policyAccessed(String policyId, long userId) {
		policyDao.policyAccessed(policyId, userId);
	}

	@Override
	public PolicyAccessedResponsesXml policyAccessedByUsers() {
		return policyDao.policyAccessedByUsers();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Policy> policyFullTextSearch(String policyName,
			String policyTopic, String policyApplicationId, String policyType,
			String policyIssuedBy, String policyVersionDate,
			String policyRelatedDoc, String searchText, Long userId) {
		return (List<Policy>) policyDao.policyFullTextSearch(policyName,
				policyTopic, policyApplicationId, policyType, policyIssuedBy,
				policyVersionDate, policyRelatedDoc, searchText, userId);
	}

	@Override
	public void getPolicyThumbnail(Long policyId,OutputStream out) {
		 policyDao.getPolicyThumbnail(policyId,out);
	}

	@Override
	public void updatePolicyAcknowledgement(PolicyAccessedResponsesXml policyAccessedResponsesXml) {
		policyDao.updatePolicyAcknowledgement(policyAccessedResponsesXml);
	}

	@Override
	public void getPolicyDocument(Long policyDocumentId, OutputStream out) {
	  policyDao.getPolicyDocument(policyDocumentId,out);
	}

	@Override
	public Long getRetiredPoliciesCount(long userId) {
	  return policyDao.getRetiredPoliciesCount(userId);
	}
}
