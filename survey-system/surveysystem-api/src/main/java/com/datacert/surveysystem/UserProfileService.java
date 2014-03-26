package com.datacert.surveysystem;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

import com.datacert.surveysystem.dto.User;
import com.datacert.surveysystem.dto.UserProfile;

public interface UserProfileService {

	public UserProfile getUserProfileDescriptor(Long profileId);
	
	public Boolean getUserProfileDescriptor(Long profileId,OutputStream out);
	
	public UserProfile loadUserProfileDescriptorByUserId(Long userId);
	
	public UserProfile getUserProfileInfo(Long userId);

	public void saveProfileImage(File profileImage, Long userId) throws IOException;

	public void loginedOnce(User user);

	public File resizeImage(MultipartFile profileImage,String fileName) throws IOException;

	public File cropImage(File file, String xStart, String yStart, String xEnd,	String yEnd) throws Exception;
	
}
