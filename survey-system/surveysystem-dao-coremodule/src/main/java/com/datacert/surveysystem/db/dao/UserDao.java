package com.datacert.surveysystem.db.dao;

import java.util.List;
import com.datacert.surveysystem.db.domain.Role;
import com.datacert.surveysystem.db.domain.SecurityQuestion;
import com.datacert.surveysystem.db.domain.UserDescriptor;
import com.datacert.surveysystem.db.domain.UserSecurityAnswer;

public interface UserDao {
	public UserDescriptor getUserByUserName(String userName);

	public UserDescriptor addUpdateUser(UserDescriptor user);

	public Role getMemberRole();

	public List<? extends com.datacert.surveysystem.dto.SecurityQuestion> getAllQuestionList();

	public List<SecurityQuestion> getUserQuestionList(String username);
	
	public SecurityQuestion getSecurityQuestion(Integer questionId);
	
	public boolean checkSecurityQuestion(long userid, String question, String answer);
	
//	=Need To Check
	public String getUsername(Long userid, Long contactId);
	
	public void updateUserContactId(long contactId, long userId);
	
	public UserDescriptor getUser(String userName,Long userId,Long contactId);
	
	public UserSecurityAnswer addUserSecurityAnswer (UserSecurityAnswer userSecurityAnswer);

	public void saveUser(UserDescriptor user);
	
	public void loginedOnce(Long userId);
	
}
