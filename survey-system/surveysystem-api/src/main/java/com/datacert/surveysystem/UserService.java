package com.datacert.surveysystem;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.TreeMap;

import com.datacert.surveysystem.dto.RegisterUserDto;
import com.datacert.surveysystem.dto.User;

public interface UserService {
//  public User getUser(String userName);

  public User getUser(String userName, Long userId, Long contactId);

  public boolean isUnique(String uniqueIdentifier);

  public String generateTempPassword();

//  public User addUser(User user);
  
  public User addUser(RegisterUserDto registerUserDto);

  public void updateUser(User user);

  public void activateUser(Long userId, String password);

//  public TreeMap<Integer, String> getAllquestionList();
  
  public List<? extends com.datacert.surveysystem.dto.SecurityQuestion> getAllquestionList();

  public boolean isPasswordAlreadyChanged(String userId, String tempPassword);

  public TreeMap<Integer, String> getUserQuestionList(String email);

  public long isExists(String username);

  public boolean checkSecurityQuestion(long userid, String question, String answer);

  public boolean changePassword(Long username, String oldPassword, String newPassword);

  public boolean changeExistingPassword(String username, String oldPassword, String newPassword);

  public String getUsername(Long userId, Long contactId);

  public void lockUser(String username);

  // public boolean isRegister(String username);

  public boolean isRegister(User user);

  public boolean isChangePasswordRequestValid(String userId, String urlIdentifier);

  public BufferedImage getUserProfileImage(long userId);
  
  // For testing purpose
  public void loginedOnce(Long userId);
  
}
