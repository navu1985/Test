package com.datacert.surveysystem.db.dao;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.datacert.surveysystem.db.domain.UserDescriptor;
import com.datacert.surveysystem.db.domain.UserProfileDescriptor;
import com.datacert.surveysystem.db.domain.UserProfileInfoDescriptor;
import com.datacert.surveysystem.db.util.SurveyPortalExternalSqlQueries;

@Repository("userProfileDao")
@Transactional
public class UserProfileDaoImpl implements UserProfileDao {

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	private SurveyPortalExternalSqlQueries  externalSqlQueries;
	
	@Override
	public UserProfileDescriptor getUserProfileDescriptor(Long profileId) {
		return (UserProfileDescriptor) sessionFactory.getCurrentSession().get(UserProfileDescriptor.class, profileId);
	}
	
	@Override
	public Boolean getUserProfileDescriptor(final Long profileId,final OutputStream out) {
	  Session session= sessionFactory.getCurrentSession();
	  Long count= (Long) session.createQuery("select count(*) from UserProfileDescriptor where userProfileId=:userProfileId and userPolicyImage is not NULL")
	  .setParameter("userProfileId", profileId)
	  .uniqueResult();
	  if(count.longValue()==0l){
		return Boolean.FALSE;
	  }
		session.doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				String sql= externalSqlQueries.getSelectProfileImage();
				PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
				stm.setFloat(1,profileId);
				ResultSet rset =stm.executeQuery();
				try{
					if(rset.next()){
						try {
							IOUtils.copyLarge(rset.getAsciiStream("profile_image"), out);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}finally{
				   rset.close();
				   stm.close();
				}
			}
		});
		return Boolean.TRUE;
	}
	
	@Override
	public UserProfileDescriptor loadUserProfileDescriptorByUserId(Long userId) {
		Session session= sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(UserProfileDescriptor.class);
		criteria.setFetchMode("user", FetchMode.JOIN);
		criteria.add(Restrictions.eq("user.userId", userId));
		UserProfileDescriptor userProfileDescriptor =(UserProfileDescriptor)criteria.uniqueResult();
		return userProfileDescriptor;
	}
	
	@Override
	public UserProfileInfoDescriptor getUserProfileInfo(Long userId) {
		Session session= sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(UserProfileInfoDescriptor.class);
		criteria.setFetchMode("user", FetchMode.JOIN);
		criteria.add(Restrictions.eq("user.userId", userId));
		UserProfileInfoDescriptor userProfileDescriptor =(UserProfileInfoDescriptor)criteria.uniqueResult();
		return userProfileDescriptor;
	}
	
	@Override
	public void saveProfileImage(byte[] userPolicyImage, Long userId) {
		Session session=sessionFactory.getCurrentSession();
		int count =session.createQuery("update UserProfileDescriptor set userPolicyImage=:userPolicyImage where user.userId=:userId")
		.setParameter("userPolicyImage", userPolicyImage)
		.setParameter("userId", userId)
		.executeUpdate();
		if(count<=0){
			UserDescriptor user=(UserDescriptor)session.get(UserDescriptor.class, userId);
			UserProfileDescriptor userProfileDescriptor = new UserProfileDescriptor(user,userPolicyImage);
			session.saveOrUpdate("UserProfileDescriptor", userProfileDescriptor);
			user.setUserProfileInfoDescriptor((UserProfileInfoDescriptor)session.get(UserProfileInfoDescriptor.class, userProfileDescriptor.getUserProfileId()));
			session.saveOrUpdate(user);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveUserProfile(UserProfileInfoDescriptor userProfileInfoDescriptor) {
		sessionFactory.getCurrentSession().saveOrUpdate("UserProfileDescriptor", userProfileInfoDescriptor);
	}
}
