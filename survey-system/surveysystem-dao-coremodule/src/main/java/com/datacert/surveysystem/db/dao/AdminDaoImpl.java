package com.datacert.surveysystem.db.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.datacert.surveysystem.db.domain.AdminSettings;
import com.datacert.surveysystem.db.domain.PassportDescriptor;
import com.datacert.surveysystem.dto.Admin;

/**
 * @author DiamondDev parora
 *
 */

@Repository("adminDao")
@Transactional
public class AdminDaoImpl implements AdminDao {

  @Resource
  private SessionFactory sessionFactory;

  /**
   * Get all Passport List from Database. 
   */
  @Override
  @Transactional(readOnly = true)
  public List<PassportDescriptor> getConnectedApplication() {
	@SuppressWarnings("unchecked")
	List<PassportDescriptor> passportList = sessionFactory.getCurrentSession().createQuery("from PassportDescriptor").list();
	return passportList;
  }

  /**
   * Change Communication Status of Passport having id equal to applicationId
   */
  @Override
  public void stopApplicationCommunication(String applicationId,String applicationStatus) {
	Session session=sessionFactory.getCurrentSession();
	PassportDescriptor passsport =(PassportDescriptor) session.get(PassportDescriptor.class, Long.valueOf(applicationId));
	passsport.setConnectionStatus(applicationStatus);
	session.saveOrUpdate(passsport);
  }

  /**
   * Delete Entry of Passport from DB and delete all survey associated with this application.
   */
  @Override
  public void deleteCommunication(String applicationId) {
	sessionFactory.getCurrentSession().createQuery("delete from PassportDescriptor where id=:id")
	.setParameter("id", Long.valueOf(applicationId)).executeUpdate();
  }

  /**
   * Add Entry of Passport in Application.
   * 
   */
  @Override
  public boolean addApplication(PassportDescriptor passport) {
	PassportDescriptor checkPassport=getApplicationbyUniqueID(passport.getApplicationIdentifier());
	if (checkPassport == null) {
	  if(isdbGuidExists(passport.getDbGuid()))
		return false;
	  else
		sessionFactory.getCurrentSession().saveOrUpdate(passport);
	}else if (checkPassport.getDbGuid()==null){
	  checkPassport.setDbGuid(passport.getDbGuid());
	  sessionFactory.getCurrentSession().saveOrUpdate(checkPassport);
	} else if(checkPassport.getDbGuid().equals(passport.getDbGuid())){
	  return true;
	}else {
	  return false;
	}
	return true;
  }
  
  @Override
  public void updateApplication(PassportDescriptor passport) {
	 sessionFactory.getCurrentSession().saveOrUpdate(passport);
  }

  @Override
  @Transactional(readOnly = true)
  public PassportDescriptor getApplication(String applicationId) {
	return (PassportDescriptor)sessionFactory.getCurrentSession().get(PassportDescriptor.class,Long.valueOf(applicationId));
  }
  
  @Transactional(readOnly = true)
  public boolean isdbGuidExists(String dbGuid) {
	Long count=(Long)sessionFactory.getCurrentSession().createQuery("select count(*) from PassportDescriptor where dbGuid=:dbGuid")
			.setParameter("dbGuid", dbGuid)
			.uniqueResult();
	return count.intValue()!=0;
	
  }
  
  @Transactional(readOnly = true)
  public PassportDescriptor getApplicationbyUniqueID(String applicationIdentifier) {
	return (PassportDescriptor)sessionFactory.getCurrentSession().createQuery("from PassportDescriptor where applicationIdentifier=:applicationIdentifier").setParameter("applicationIdentifier", applicationIdentifier)
			.uniqueResult();
  }
  
  @Override
  public void updateAdminSetting(Admin settings) {
	sessionFactory.getCurrentSession().saveOrUpdate((AdminSettings)settings);
  }
}