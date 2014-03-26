package com.datacert.surveysystem.db.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.datacert.surveysystem.db.domain.Role;
import com.datacert.surveysystem.db.domain.SecurityQuestion;
import com.datacert.surveysystem.db.domain.UserDescriptor;
import com.datacert.surveysystem.db.domain.UserSecurityAnswer;
import com.datacert.surveysystem.db.util.DaoUtility;

@Repository("userDao")
@Transactional
public class UserDaoImpl implements UserDao {

  @Resource
  private SessionFactory sessionFactory;

  @Resource
  private DaoUtility daoUtills;

  @Override
  public UserDescriptor getUserByUserName(String username) {
	UserDescriptor user = (UserDescriptor) sessionFactory.getCurrentSession().getNamedQuery("getUserWithRole")
			.setParameter("username", username).uniqueResult();
	return user;
  }

  @Override
  public SecurityQuestion getSecurityQuestion(Integer questionId) {
	return (SecurityQuestion) sessionFactory.getCurrentSession().get(SecurityQuestion.class, questionId);
  }

  @Override
  public UserDescriptor getUser(String userName, Long userId, Long contactId) {
	Query query = null;
	if (userName != null) {
	  query = sessionFactory.getCurrentSession().getNamedQuery("getUserByUserName").setParameter("username", userName);
	} else if (userId != null) {
	  query = sessionFactory.getCurrentSession().getNamedQuery("getUserByID").setParameter("userId", userId);
	} else if (contactId != null) {
	  query = sessionFactory.getCurrentSession().getNamedQuery("getUserByContactId").setParameter("userPassportId", contactId);
	} else {
	  return null;
	}
	@SuppressWarnings("unchecked")
	List<UserDescriptor> userList = query.list();
	if (userList.isEmpty() || userList == null) {
	  return null;
	}
	return userList.get(0);
  }

  @Override
  @Transactional(propagation=Propagation.REQUIRED)
  public UserDescriptor addUpdateUser(UserDescriptor user) {
	String hql = "FROM UserDescriptor WHERE username = :username and registered=0";
	UserDescriptor alreadyUser = (UserDescriptor) sessionFactory.getCurrentSession().createQuery(hql).setParameter("username", user.getUsername()).uniqueResult();
	if (alreadyUser != null) {
	  alreadyUser.setPassword(user.getPassword());
	  alreadyUser.setUserSecurityAnswer(user.getUserSecurityAnswer());
	  alreadyUser.setRegistered(user.getRegistered());
	  HashSet<Role> authority = new HashSet<Role>();
	  authority.add(getMemberRole());
	  alreadyUser.setAuthority(authority);
	  sessionFactory.getCurrentSession().saveOrUpdate(alreadyUser);
	  return alreadyUser;
	} else {
	  sessionFactory.getCurrentSession().saveOrUpdate(user);
	  return user;
	}
  }

  @Override
  public UserSecurityAnswer addUserSecurityAnswer(UserSecurityAnswer userSecurityAnswer) {
	sessionFactory.getCurrentSession().saveOrUpdate(userSecurityAnswer);
	return userSecurityAnswer;
  }

  @Override
  public Role getMemberRole() {
	Role authority = (Role) sessionFactory.getCurrentSession().createQuery("from Role where roleName=?")
			.setParameter(0, "ROLE_USER").list().get(0);
	return authority;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<? extends com.datacert.surveysystem.dto.SecurityQuestion> getAllQuestionList() {
	return sessionFactory.getCurrentSession().createQuery("from SecurityQuestion").list();
  }

  @Override
  @Transactional(readOnly = true)
  public List<SecurityQuestion> getUserQuestionList(String userid) {
	ArrayList<SecurityQuestion> userqlist = new ArrayList<SecurityQuestion>();

	UserDescriptor user = (UserDescriptor) sessionFactory.getCurrentSession().get(UserDescriptor.class, Long.valueOf(userid));
	Set<UserSecurityAnswer> userSecurityAnswer = user.getUserSecurityAnswer();

	for (Iterator<UserSecurityAnswer> iterator = userSecurityAnswer.iterator(); iterator.hasNext();) {
	  UserSecurityAnswer userSecurityAnswer2 = (UserSecurityAnswer) iterator.next();
	  userqlist.add(userSecurityAnswer2.getQuestion());
	}
	return userqlist;
  }


  @Override
  @SuppressWarnings("unchecked")
  public boolean checkSecurityQuestion(long userid, String question, String answer) {
	List<UserSecurityAnswer> list = sessionFactory.getCurrentSession()
			.createQuery("select id from UserSecurityAnswer where question.questionId=? and answer =? and user.userId= ?")
			.setParameter(0, Integer.valueOf(question)).setParameter(1, answer).setParameter(2, userid).list();
	if (list.size() > 0)
	  return true;
	else
	  return false;
  }

  @Override
  public String getUsername(Long userId, Long userPassportId) {
	if (!userPassportId.equals(-1L)) {
	  String hql = "SELECT username FROM UserDescriptor WHERE userPassportId = :userPassportId and enable=:enable ";
	  String username = (String) sessionFactory.getCurrentSession().createQuery(hql)
			  .setParameter("userPassportId", userPassportId).setParameter("enable", 1).setMaxResults(1).uniqueResult();
	  return username;
	} else {
	  String hql = "SELECT username FROM UserDescriptor WHERE userId = :userId";
	  String username = (String) sessionFactory.getCurrentSession().createQuery(hql).setParameter("userId", userId)
			  .uniqueResult();
	  return username;
	}
  }

  @Override
  public void updateUserContactId(long contactId, long userId) {
	String hql = "update UserDescriptor set userPassportId = :contactId where userId = :userId";
	Session session = sessionFactory.getCurrentSession();
	Query query = session.createQuery(hql);
	query.setLong("contactId", contactId);
	query.setLong("userId", userId);
	query.executeUpdate();
  }


  @Override
  public void saveUser(UserDescriptor user) {
	sessionFactory.getCurrentSession().saveOrUpdate(user);
  }

  @Override
  public void loginedOnce(Long userId) {
	
  }
}
