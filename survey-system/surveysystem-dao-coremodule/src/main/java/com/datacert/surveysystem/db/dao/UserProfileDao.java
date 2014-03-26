package com.datacert.surveysystem.db.dao;

import java.io.OutputStream;

import com.datacert.surveysystem.db.domain.UserProfileDescriptor;
import com.datacert.surveysystem.db.domain.UserProfileInfoDescriptor;

public interface UserProfileDao {

	public UserProfileDescriptor getUserProfileDescriptor(Long profileId);
	
	public Boolean getUserProfileDescriptor(Long profileId,OutputStream out);

	public void saveProfileImage(byte[] bytes,Long userId);
	
	public UserProfileInfoDescriptor getUserProfileInfo(Long userId);
	
	public UserProfileDescriptor loadUserProfileDescriptorByUserId(Long userId);

	public void saveUserProfile(UserProfileInfoDescriptor userProfileInfoDescriptor);

}
