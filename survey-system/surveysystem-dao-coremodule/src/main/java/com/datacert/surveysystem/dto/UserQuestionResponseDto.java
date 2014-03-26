package com.datacert.surveysystem.dto;

import java.util.Date;

import com.datacert.surveysystem.dto.UserResponse;

public class UserQuestionResponseDto implements UserResponse {

	private long Id;
	private long userId;
	private String responseText;
	private Date reponseDate;
	private byte[] file;
	private long sequence;
	private long questionId;
	private long docId;
	
	public UserQuestionResponseDto() {
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public Date getReponseDate() {
		return reponseDate;
	}

	public void setReponseDate(Date reponseDate) {
		this.reponseDate = reponseDate;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public long getDocId() {
		return docId;
	}

	public void setDocId(long docId) {
		this.docId = docId;
	}




}
