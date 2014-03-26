package com.datacert.surveysystem.db.domain;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class UserSurveyId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2572696327857328804L;

	@ManyToOne
	private UserDescriptor user;

	@ManyToOne
	private SurveyDescriptor survey;

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserSurveyId that = (UserSurveyId) o;

		if (survey != null ? !survey.equals(that.survey) : that.survey != null)
			return false;
		if (user != null ? !user.equals(that.user) : that.user != null)
			return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = (survey != null ? survey.hashCode() : 0);
		result = 31 * result + (user != null ? user.hashCode() : 0);
		return result;
	}

	public UserDescriptor getUser() {
		return user;
	}

	public void setUser(UserDescriptor user) {
		this.user = user;
	}

	public SurveyDescriptor getSurvey() {
		return survey;
	}

	public void setSurvey(SurveyDescriptor survey) {
		this.survey = survey;
	}
}
