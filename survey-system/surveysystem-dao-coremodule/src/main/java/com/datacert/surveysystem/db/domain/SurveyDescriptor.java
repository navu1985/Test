package com.datacert.surveysystem.db.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.datacert.surveysystem.dto.Question;
import com.datacert.surveysystem.dto.Survey;
import com.datacert.surveysystem.dto.User;


/**
 * @author parora
 *
 */
@NamedQueries({
	@NamedQuery(
	name = "getPendingSurveys",
	query = "Select surveyDesc.surveyId as surveyId, surveyDesc.surveyName as surveyName, surveyDesc.surveyAssignDate as surveyAssignDate, surveyDesc.surveyDueDate as surveyDueDate, userSurvey.status as status , surveyDesc.passport as passport from SurveyDescriptor as surveyDesc join surveyDesc.userSurveys as userSurvey " +
				"where userSurvey.pk.user.userId=:userId and surveyDesc.status=:status and (userSurvey.status=:userSaveLaterStatus or  userSurvey.status=:userOpenStatus)" +
				"order by  surveyDesc.surveyDueDate",
	cacheable=true)
})

@Entity
@Table(name = "pp_survey_request")
public class SurveyDescriptor implements Serializable, Survey {

	private static final long serialVersionUID = 895321048240082267L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long surveyId;

	@Column(name = "name")
	private String surveyName;
	
	@Column(name = "passport_surveyid")
	private long passportSurveyId;
	
	@Column(name = "assigned_date")
	private Date surveyAssignDate;

	@Column(name = "due_date")
	private Date surveyDueDate;

	@Column(name = "status")
	private String status;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "survey" )
	@OrderBy("sequence")
	private Set<QuestionDescriptor> surveyQuestions;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.survey", cascade = CascadeType.ALL)
	private Set<UserSurvey> userSurveys;
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "passport_id")
	private PassportDescriptor passport;

	public long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(long surveyId) {
		this.surveyId = surveyId;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public Date getSurveyAssignDate() {
		return surveyAssignDate;
	}

	public void setSurveyAssignDate(Date surveyAssignDate) {
		this.surveyAssignDate = surveyAssignDate;
	}

	public Date getSurveyDueDate() {
		return surveyDueDate;
	}

	public void setSurveyDueDate(Date surveyDueDate) {
		this.surveyDueDate = surveyDueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<UserSurvey> getUserSurveys() {
		return userSurveys;
	}

	public void setUserSurveys(Set<UserSurvey> userSurveys) {
		this.userSurveys = userSurveys;
	}

	public Set<Question> getQuestions() {
		Set<QuestionDescriptor> question = getSurveyQuestions();
		Set<Question> questionList = new TreeSet<Question>();
		for (QuestionDescriptor questionDescriptor : question) {
			questionList.add((Question) questionDescriptor);
		}
		return questionList;
	}

	@Override
	public Set<User> getUsers() {
		return null;
	}

	public Set<QuestionDescriptor> getSurveyQuestions() {
		return surveyQuestions;
	}

	public void setSurveyQuestions(Set<QuestionDescriptor> surveyQuestions) {
		this.surveyQuestions = surveyQuestions;
	}

	public PassportDescriptor getPassport() {
	  return passport;
	}
	
	public void setPassport(PassportDescriptor passport) {
	  this.passport = passport;
	}

	public long getPassportSurveyId() {
	  return passportSurveyId;
	}

	public void setPassportSurveyId(long passportSurveyId) {
	  this.passportSurveyId = passportSurveyId;
	}

	
}