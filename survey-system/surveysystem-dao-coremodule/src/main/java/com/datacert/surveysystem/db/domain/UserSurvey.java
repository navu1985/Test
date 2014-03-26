package com.datacert.surveysystem.db.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.AssociationOverride;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

@Entity
@Table(name = "pp_user_survey_request")
@AssociationOverrides({ @AssociationOverride(name = "pk.survey", joinColumns = @JoinColumn(name = "survey_id")),
		@AssociationOverride(name = "pk.user", joinColumns = @JoinColumn(name = "user_id")) })
public class UserSurvey implements Serializable, com.datacert.surveysystem.dto.UserSurvey {

	private static final long serialVersionUID = 895321048240082267L;

	@EmbeddedId
	private UserSurveyId pk = new UserSurveyId();

	@Column(name = "completed_on")
	private Date completedOn;

	@Column(name = "contact_id")
	private long userPassportId;

	@Column(name = "status")
	private String status;

	@Column(name = "last_error")
	private Date lastError;

	@Column(name = "surveyResponseId")
	private String surveyResponseId;

	public UserSurvey() {
	}

	@Transient
	public SurveyDescriptor getSurvey() {
		return getPk().getSurvey();
	}

	public void setSurvey(SurveyDescriptor survey) {
		getPk().setSurvey(survey);
	}

	@Transient
	public UserDescriptor getUser() {
		return getPk().getUser();
	}

	public void setUser(UserDescriptor user) {
		getPk().setUser(user);
	}

	public UserSurveyId getPk() {
		return pk;
	}

	public void setPk(UserSurveyId pk) {
		this.pk = pk;
	}

	public Date getCompletedOn() {
		return completedOn;
	}

	public void setCompletedOn(Date completedOn) {
		this.completedOn = completedOn;
	}

	public long getUserPassportId() {
		return userPassportId;
	}

	public void setUserPassportId(long userPassportId) {
		this.userPassportId = userPassportId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserSurvey that = (UserSurvey) o;
		if (getPk() != null ? !getPk().equals(that.getPk()) : that.getPk() != null)
			return false;
		return true;
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}

	public Date getLastError() {
		return lastError;
	}

	public void setLastError(Date lastError) {
		this.lastError = lastError;
	}

	public String getSurveyResponseId() {
	  return surveyResponseId;
	}

	public void setSurveyResponseId(String surveyResponseId) {
	  this.surveyResponseId = surveyResponseId;
	}

}
