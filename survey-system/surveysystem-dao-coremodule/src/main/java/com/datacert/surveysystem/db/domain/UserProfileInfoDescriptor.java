package com.datacert.surveysystem.db.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.datacert.surveysystem.dto.UserProfile;

@Entity
@Table(name = "ps_user_profile")
public class UserProfileInfoDescriptor implements UserProfile,Serializable {

    private static final long serialVersionUID = -4354119394344077947L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private long userProfileId;

	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(value=CascadeType.ALL)
	private UserDescriptor user;
	
	public long getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(long userProfileId) {
		this.userProfileId = userProfileId;
	}

	@Override
	public byte[] getUserPolicyImage() {
		return null;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserDescriptor getUser() {
		return user;
	}

	public void setUser(UserDescriptor user) {
		this.user = user;
	}


}
