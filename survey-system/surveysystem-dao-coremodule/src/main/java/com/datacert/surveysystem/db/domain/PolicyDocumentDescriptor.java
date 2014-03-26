package com.datacert.surveysystem.db.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.datacert.surveysystem.dto.PolicyDocument;

/**
 * @author parora
 * 
 */

@Entity
@Table(name = "pp_policy_document")
public class PolicyDocumentDescriptor implements PolicyDocument {

  @Id
  @Column(name = "id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long policyDocumentId;

  @Column(name = "doc_name")
  private String policyDocumentName;
  
  @Lob
  @Column(name = "doc_data")
  @Basic(fetch=FetchType.LAZY)
  private byte[] policyDocumentContent;

  @Column(name = "doc_size")
  private Long policyDocumentSize;

  @Column(name = "last_modified")
  public Date policyDocumentLastModified;
  
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "policy_id")
  private PolicyDescriptor policyDescriptor;
  
  @Column(name = "policy_thumbnail")
  @Basic(fetch=FetchType.LAZY)
  private byte[] policyThumbnail;

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

  public byte[] getPolicyDocumentContent() {
    return policyDocumentContent;
  }

  public void setPolicyDocumentContent(byte[] policyDocumentContent) {
    this.policyDocumentContent = policyDocumentContent;
  }

  public Long getPolicyDocumentId() {
    return policyDocumentId;
  }

  public void setPolicyDocumentId(Long policyDocumentId) {
    this.policyDocumentId = policyDocumentId;
  }

public PolicyDescriptor getPolicyDescriptor() {
	return policyDescriptor;
}

public void setPolicyDescriptor(PolicyDescriptor policyDescriptor) {
	this.policyDescriptor = policyDescriptor;
}

public byte[] getPolicyThumbnail() {
	return policyThumbnail;
}

public void setPolicyThumbnail(byte[] policyThumbnail) {
	this.policyThumbnail = policyThumbnail;
}
}