package com.datacert.surveysystem.db.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ps_security_question")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class SecurityQuestion {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int questionId;

	@Column(name = "question", nullable = false,insertable=false,updatable=false)
	private String questionText;

	@OneToMany(targetEntity = UserSecurityAnswer.class, cascade = CascadeType.ALL,fetch= FetchType.LAZY, mappedBy = "question")
	private Set<UserSecurityAnswer> userSecurityQuestion;

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public Set<UserSecurityAnswer> getUserSecurityQuestion() {
		return userSecurityQuestion;
	}

	public void setUserSecurityQuestion(Set<UserSecurityAnswer> userSecurityQuestion) {
		this.userSecurityQuestion = userSecurityQuestion;
	}
}
