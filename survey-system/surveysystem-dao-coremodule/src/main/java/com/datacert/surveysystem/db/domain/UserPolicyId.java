package com.datacert.surveysystem.db.domain;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class UserPolicyId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2572696327857328804L;

	@ManyToOne
	private UserDescriptor user;

	@ManyToOne
	private PolicyDescriptor policy;

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserPolicyId that = (UserPolicyId) o;

		if (policy != null ? !policy.equals(that.policy) : that.policy != null)
			return false;
		if (user != null ? !user.equals(that.user) : that.user != null)
			return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = (policy != null ? policy.hashCode() : 0);
		result = 31 * result + (user != null ? user.hashCode() : 0);
		return result;
	}

	public UserDescriptor getUser() {
		return user;
	}

	public void setUser(UserDescriptor user) {
		this.user = user;
	}

	public PolicyDescriptor getPolicy() {
		return policy;
	}

	public void setPolicy(PolicyDescriptor policy) {
		this.policy = policy;
	}

}
