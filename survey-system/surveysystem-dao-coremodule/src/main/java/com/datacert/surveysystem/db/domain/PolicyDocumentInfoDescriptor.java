package com.datacert.surveysystem.db.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.datacert.surveysystem.dto.PolicyDocument;

/**
 * @author parora
 * 
 */

@Entity
@Table(name = "pp_policy_document")
public class PolicyDocumentInfoDescriptor implements Serializable, PolicyDocument {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long policyDocumentId;

  @Column(name = "doc_name")
  private String policyDocumentName;
  
  @Column(name = "doc_size")
  private Long policyDocumentSize;

  @Column(name = "last_modified")
  public Date policyDocumentLastModified;
  
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "policy_id")
  private PolicyDescriptor policyDescriptor;
  
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

  public Date getPolicyDocumentLastModified() {
	return policyDocumentLastModified;
  }

  public void setPolicyDocumentLastModified(Date policyDocumentLastModified) {
	this.policyDocumentLastModified = policyDocumentLastModified;
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

  public PolicyDescriptor getPolicyDescriptor() {
		return policyDescriptor;
  }
	
  public void setPolicyDescriptor(PolicyDescriptor policyDescriptor) {
		this.policyDescriptor = policyDescriptor;
  }
}