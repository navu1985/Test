package com.datacert.surveysystem.db;

import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.datacert.surveysystem.UserService;
import com.datacert.surveysystem.db.dao.SurveyDao;
import com.datacert.surveysystem.db.dao.UserDao;
import com.datacert.surveysystem.db.dao.UserProfileDao;
import com.datacert.surveysystem.db.domain.Role;
import com.datacert.surveysystem.db.domain.SecurityQuestion;
import com.datacert.surveysystem.db.domain.UserDescriptor;
import com.datacert.surveysystem.db.domain.UserSecurityAnswer;
import com.datacert.surveysystem.db.util.DaoUtility;
import com.datacert.surveysystem.dto.RegisterUserDto;
import com.datacert.surveysystem.dto.User;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
  @Resource
  private UserDao userDao;

  @Resource
  private DaoUtility daoUtills;

  @Resource
  private SurveyDao surveyDao;
  
  @Resource
  private UserProfileDao userProfileDao;
  

  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	return (UserDetails) userDao.getUserByUserName(userName);
  }

  public User getUser(String userName, Long userId, Long contactId) {
	return (User) userDao.getUser(userName, userId, contactId);
  }

  public boolean isUnique(String uniqueIdentifier) {
	UserDescriptor userDescriptor = userDao.getUser(uniqueIdentifier, null, null);
	if (userDescriptor == null)
	  return true;
	return userDescriptor.getRegistered() != 1;
  }

  public User addUser(RegisterUserDto registerUserDto) {
	UserDescriptor userDescriptor = new UserDescriptor(registerUserDto.getUserName(),Long.toHexString(Double.doubleToLongBits(Math.random())));
	
	/*
	 * Set true value to regiter 
	*/
	userDescriptor.setRegistered(1);
	/*
	 * Set roles to users 
	*/
	HashSet<Role> authority = new HashSet<Role>();
	authority.add(userDao.getMemberRole());
	userDescriptor.setAuthority(authority);
	userDescriptor = userDao.addUpdateUser(userDescriptor);
	
	/*
	 * Save Security Answers
	*/
	userDao.addUserSecurityAnswer(new UserSecurityAnswer(userDao.getSecurityQuestion(Integer.valueOf(registerUserDto.getQuestionOne())), registerUserDto.getAnswerOne(), userDescriptor));
	userDao.addUserSecurityAnswer(new UserSecurityAnswer(userDao.getSecurityQuestion(Integer.valueOf(registerUserDto.getQuestionTwo())), registerUserDto.getAnswerTwo(), userDescriptor));
	userDao.addUserSecurityAnswer(new UserSecurityAnswer(userDao.getSecurityQuestion(Integer.valueOf(registerUserDto.getQuestionThree())), registerUserDto.getAnswerThree(), userDescriptor));
	return (User)userDescriptor;
  }

  
  public void updateUser(User user) {
	if (user instanceof UserDescriptor) {
	  userDao.saveUser((UserDescriptor) user);
	}
  }

  public void activateUser(Long userId, String password) {
	UserDescriptor user = userDao.getUser(null, userId, null);
	user.setPassword(daoUtills.convertStringPasswordToShaPassword(password));
	user.setEnable(1);
	user.setUrlIdentifier(String.valueOf(Calendar.getInstance().getTimeInMillis()));
	userDao.saveUser(user);
  }

  public String generateTempPassword() {
	return Long.toHexString(Double.doubleToLongBits(Math.random()));
  }

  public List<? extends com.datacert.surveysystem.dto.SecurityQuestion> getAllquestionList() {
	return userDao.getAllQuestionList();
  }

  public boolean isPasswordAlreadyChanged(String userId, String tempPassword) {
	UserDescriptor userDescriptor = userDao.getUser(null, Long.valueOf(userId), null);
	if (userDescriptor == null) {
	  return false;
	} else if (userDescriptor.getEnable() == 1) {
	  return false;
	} else if (!userDescriptor.getPassword().equals(tempPassword)) {
	  return false;
	} else {
	  return true;
	}
  }

  public TreeMap<Integer, String> getUserQuestionList(String email) {

	List<SecurityQuestion> qlist = userDao.getUserQuestionList(email);
	TreeMap<Integer, String> questionsList = new TreeMap<Integer, String>();
	for (Iterator<SecurityQuestion> iterator = qlist.iterator(); iterator.hasNext();) {
	  SecurityQuestion question = (SecurityQuestion) iterator.next();
	  questionsList.put(question.getQuestionId(), question.getQuestionText());
	}
	return questionsList;
  }

  public long isExists(String username) {
	UserDescriptor user = userDao.getUser(username, null, null);
	if (user == null) {
	  return -1;
	} else if (user.getRegistered() != 1) {
	  return -1;
	} else {
	  return user.getUserId();
	}
  }

  public boolean checkSecurityQuestion(long userid, String question, String answer) {
	return userDao.checkSecurityQuestion(userid, question, answer);
  }

  public boolean changePassword(Long userId, String urlIdentifier, String newPassword) {
	UserDescriptor user = userDao.getUser(null, userId, null);
	if (user == null || user.getUrlIdentifier() == null) {
	  return false;
	} else if (!user.getUrlIdentifier().equals(urlIdentifier)) {
	  return false;
	}
	user.setPassword(daoUtills.convertStringPasswordToShaPassword(newPassword));
	user.setUrlIdentifier(String.valueOf(Calendar.getInstance().getTimeInMillis()));
	userDao.saveUser(user);
	return true;
	// return userDao.changePassword(username, oldPassword, newPassword);
  }

  public boolean isChangePasswordRequestValid(String userId, String urlIdentifier) {
	UserDescriptor user = userDao.getUser(null, Long.valueOf(userId), null);
	if (user.equals(null)) {
	  return false;
	} else if (!user.getUrlIdentifier().equalsIgnoreCase(urlIdentifier)) {
	  return false;
	}
	return true;
  }

  public boolean changeExistingPassword(String username, String oldPassword, String newPassword) {
	UserDescriptor user = userDao.getUser(username, null, null);
	if (!oldPassword.isEmpty()) {
	  if (!user.getPassword().equals(daoUtills.convertStringPasswordToShaPassword(oldPassword)))
		return false;
	}
	user.setPassword(daoUtills.convertStringPasswordToShaPassword(newPassword));
	userDao.saveUser(user);
	return true;
  }

  public String getUsername(Long userId, Long contactId) {
	return userDao.getUsername(userId, contactId);
  }

  public void lockUser(String username) {
	UserDescriptor user = userDao.getUser(username, null, null);
	user.setLocked(1);
	userDao.saveUser(user);
  }

  public boolean isRegister(User user) {
	if (user.getEnable() == 1)
	  return true;
	else
	  return false;
  }

  public void loginedOnce(Long userId) {
	 userDao.loginedOnce(userId);
  }

  @Override
  public BufferedImage getUserProfileImage(long userId) {
		return null;
  }

}
