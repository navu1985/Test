package com.datacert.surveysystem.db.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ps_user_security_question_answer")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class UserSecurityAnswer {

  @Id
  @GeneratedValue
  @Column(name = "id")
  private long id;

  @Column(name = "answer")
  private String answer;

  @ManyToOne(cascade = CascadeType.ALL)
  private SecurityQuestion question;

  @ManyToOne(fetch = FetchType.LAZY)
  private UserDescriptor user;

  public UserSecurityAnswer() {
  }

  public UserSecurityAnswer(SecurityQuestion question, String answer, UserDescriptor user) {
	super();
	this.answer = answer;
	this.question = question;
	this.user = user;
  }

  public SecurityQuestion getQuestion() {
	return question;
  }

  public void setQuestion(SecurityQuestion question) {
	this.question = question;
  }

  public long getId() {
	return id;
  }

  public void setId(long id) {
	this.id = id;
  }

  public String getAnswer() {
	return answer;
  }

  public void setAnswer(String answer) {
	this.answer = answer;
  }

  public UserDescriptor getUser() {
	return user;
  }

  public void setUser(UserDescriptor user) {
	this.user = user;
  }

}
