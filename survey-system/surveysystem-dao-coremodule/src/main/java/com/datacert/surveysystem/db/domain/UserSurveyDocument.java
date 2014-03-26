package com.datacert.surveysystem.db.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.datacert.surveysystem.dto.UserResponseDocument;

@Entity
@Table(name = "p_user_survey_doc_response")
public class UserSurveyDocument implements Serializable, UserResponseDocument {

	private static final long serialVersionUID = 895321048240082267L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long docId;

	@Column(name = "doc_name")
	private String docName;
	
	@Column(name = "doc_size")
	private Long docSize;

	@Column(name = "doc")
	private byte[] doc;
	
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "question_id")
	private Long questionId;

	@Column(name = "last_modified")
	private Date lastModified;

	public UserSurveyDocument(Long id,String docName,Long docSize) {
		this.docId=id;
		this.docName=docName;
		this.docSize=docSize;
	}
	public UserSurveyDocument() {
	}
	
	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public byte[] getDoc() {
		return doc;
	}

	public void setDoc(byte[] doc) {
		this.doc = doc;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public Long getDocSize() {
		return docSize;
	}
	public void setDocSize(Long docSize) {
		this.docSize = docSize;
	}
}
