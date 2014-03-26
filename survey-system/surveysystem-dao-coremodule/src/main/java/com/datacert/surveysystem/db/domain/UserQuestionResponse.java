package com.datacert.surveysystem.db.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name = "p_user_survey_response")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class UserQuestionResponse implements Serializable, com.datacert.surveysystem.dto.UserResponse,
		Comparable<UserQuestionResponse> {

	private static final long serialVersionUID = 895321048240082267L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private long Id;

	@Column(name = "user_id")
	private long userId;

	@Column(name = "response_text")
	private String responseText;

	@Column(name = "reponse_date")
	private Date reponseDate;

	@Column(name = "response_doc")
	private long docId;

	@Column(name = "sequence")
	private long sequence;

	@Column(name = "question_id")
	private long questionId;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	
	public void setResponseText(Double responseText) {
		if(responseText!=null)
			this.responseText = responseText.toString();
	}

	public void setResponseText(Long responseText) {
		if(responseText!=null)
			this.responseText = responseText.toString();
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public int compareTo(UserQuestionResponse userAnswer) {
		if (this.equals(userAnswer)) {
			return 0;
		} else {
			return 1;
		}
	}

	public long getDocId() {
		return docId;
	}

	public void setDocId(long docId) {
		this.docId = docId;
	}
}