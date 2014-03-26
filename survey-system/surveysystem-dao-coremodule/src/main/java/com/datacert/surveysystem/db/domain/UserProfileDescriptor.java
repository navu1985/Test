package com.datacert.surveysystem.db.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.datacert.surveysystem.dto.UserProfile;

@Entity
@Table(name = "ps_user_profile")
public class UserProfileDescriptor implements UserProfile, Serializable {

    private static final long serialVersionUID = 3653023185257463308L;

	public UserProfileDescriptor(UserDescriptor user, byte[] userPolicyImage) {
		this.user = user;
		this.userPolicyImage = userPolicyImage;
	}
	
	public UserProfileDescriptor() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private long userProfileId;

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserDescriptor user;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "profile_image")
	private byte[] userPolicyImage;

	public long getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(long userProfileId) {
		this.userProfileId = userProfileId;
	}

	public byte[] getUserPolicyImage() {
		return userPolicyImage;
	}

	public void setUserPolicyImage(byte[] userPolicyImage) {
		this.userPolicyImage = userPolicyImage;
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
