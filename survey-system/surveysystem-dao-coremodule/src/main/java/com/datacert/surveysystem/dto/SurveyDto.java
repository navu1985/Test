package com.datacert.surveysystem.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.datacert.surveysystem.dto.Question;
import com.datacert.surveysystem.dto.Survey;
import com.datacert.surveysystem.dto.User;

@XmlRootElement(name = "survey")
public class SurveyDto implements Survey {

	private long surveyId;
	private String surveyName;
	private String surveyAssignStringDate;
	private String surveyDueStringDate;
	private Set<Question> questions;
	private Set<User> users;

	@XmlElement(name = "surveyId")
	public long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(long surveyId) {
		this.surveyId = surveyId;
	}

	@XmlElement(name = "surveyDescription")
	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public Date getSurveyAssignDate() {
		Date d = null;
		try {
			d = (Date) new SimpleDateFormat("yyyy-MM-dd hh:ss:mm.SSS").parse(surveyAssignStringDate);
		} catch (ParseException e) {}
		return d;
	}

	public Date getSurveyDueDate() {
		Date d = null;
		try {
			d = (Date) new SimpleDateFormat("yyyy-MM-dd hh:ss:mm.SSS").parse(surveyDueStringDate);
		} catch (ParseException e) {}
		return d;
	}

	@XmlElementWrapper(name = "questionList")
	@XmlElementRefs({ @XmlElementRef(type = QuestionDto.class) })
	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	@XmlElementWrapper(name = "respondentList")
	@XmlElementRefs({ @XmlElementRef(type = UserDto.class) })
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@XmlElement(name = "assignDate")
	public String getSurveyAssignStringDate() {
		return surveyAssignStringDate;
	}

	public void setSurveyAssignStringDate(String surveyAssignStringDate) {
		this.surveyAssignStringDate = surveyAssignStringDate;
	}

	@XmlElement(name = "dueDate")
	public String getSurveyDueStringDate() {
		return surveyDueStringDate;
	}

	public void setSurveyDueStringDate(String surveyDueStringDate) {
		this.surveyDueStringDate = surveyDueStringDate;
	}

	public Application getPassport() {
	  return null;
	}

}
