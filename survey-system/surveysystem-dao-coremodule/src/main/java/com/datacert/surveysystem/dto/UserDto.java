package com.datacert.surveysystem.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class UserDto implements User {

	private long userId = -1;
	private String tempPassword;
	private String password;
	private String confirmpassword;
	private String oldpassword;
	private String username;
	private String answer1;
	private String answer2;
	private String answer3;
	private String question1;
	private String question2;
	private String question3;
	private long contactId;
	private int enable;
	private String urlIdentifier;
	private String surveyResponseId; 
	private String firstName;
	private String lastName;

	public String getQuestion1() {
		return question1;
	}

	public void setQuestion1(String question1) {
		this.question1 = question1;
	}

	public String getQuestion2() {
		return question2;
	}

	public void setQuestion2(String question2) {
		this.question2 = question2;
	}

	public String getQuestion3() {
		return question3;
	}

	public void setQuestion3(String question3) {
		this.question3 = question3;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserid(long userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	@XmlElement(name = "email")
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@XmlElement(name = "contactId")
	public long getContactId() {
		return contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public String getUrlIdentifier() {
	  return urlIdentifier;
	}

	public void setUrlIdentifier(String urlIdentifier) {
	  this.urlIdentifier = urlIdentifier;
	}

	@XmlElement(name = "surveyResponseId")
	public String getSurveyResponseId() {
	  return surveyResponseId;
	}

	public void setSurveyResponseId(String surveyResponseId) {
	  this.surveyResponseId = surveyResponseId;
	}
	
	@XmlElement(name = "firstName")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlElement(name = "lastName")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
