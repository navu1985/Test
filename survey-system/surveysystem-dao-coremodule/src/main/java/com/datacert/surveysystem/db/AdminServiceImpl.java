package com.datacert.surveysystem.db;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.datacert.surveysystem.AdminService;
import com.datacert.surveysystem.db.dao.AdminDao;
import com.datacert.surveysystem.db.dao.SurveyDao;
import com.datacert.surveysystem.db.domain.PassportDescriptor;
import com.datacert.surveysystem.dto.Admin;
import com.datacert.surveysystem.dto.Application;
import com.datacert.surveysystem.dto.DaoCoreConstant;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

  @Resource
  SurveyDao surveyDao;

  @Resource
  AdminDao adminDao;

  
  @Override
  public Admin getAdminSettings() {
	return (Admin) surveyDao.getAdminSettings();
  }

  @Override
  public void updateAdminSetting(Admin settings) {
	  adminDao.updateAdminSetting(settings);
  }

  @Override
  public void connect(String restUrl) throws Exception {
  }

  public void updateConnectionFlag(boolean flag) {
	surveyDao.updateConnectionFlag(flag);
  }

  public String getAdminEmail() {
	return surveyDao.getAdminEmail();
  }

  @Override
  public void setAdminEmail(String adminEmail) {
	surveyDao.setAdminEmail(adminEmail);
  }

  public List<? extends com.datacert.surveysystem.dto.UserSurvey> getErrorSurveys() {
	return surveyDao.getErrorSurveys();
  }

  @Override
  public List<? extends Application> getConnectedApplication() {
	return adminDao.getConnectedApplication();
  }

  public void stopApplicationCommunication(String applicationId ,String Status) {
	 adminDao.stopApplicationCommunication(applicationId,Status);
  }

  public void deleteCommunication(String applicationId) {
	adminDao.deleteCommunication(applicationId); 
  }

  public void addApplication(Application application) {
	PassportDescriptor passport=  adminDao.getApplication(String.valueOf(application.getId()));
	passport.setApplicationName(application.getApplicationName());
	passport.setContactEmail(application.getContactEmail());
	passport.setContactPerson(application.getContactPerson());
	passport.setContactPhone(application.getContactPhone());
	adminDao.updateApplication(passport);
  }

  public Application getApplicationByUniqueid(String applicationId) {
	return (Application)adminDao.getApplicationbyUniqueID(applicationId);
  }
  
  public Application getApplication(String applicationId) {
	return (Application)adminDao.getApplication(applicationId);
  }
  
  public void resetApplication(String applicationId) {
	PassportDescriptor passport = adminDao.getApplication(applicationId);
	passport.setDbGuid(null);
	adminDao.updateApplication(passport);
  }
  
  public boolean addApplication(String applicationId,String dbGUID) {
	if(applicationId!=null){
	  	PassportDescriptor passportDescriptor = new PassportDescriptor();
    	passportDescriptor.setApplicationIdentifier(applicationId);
    	passportDescriptor.setConnectionStatus(DaoCoreConstant.APPLICATION_STATUS_CONNECTED);
    	passportDescriptor.setDbGuid(dbGUID);
    	return adminDao.addApplication(passportDescriptor);
	}
	return false;
  }
  
  @Override
  public boolean isApplicationActive(String applicationId) {
	PassportDescriptor application =adminDao.getApplicationbyUniqueID(applicationId);
	if(application== null)
	  return false;
	else if(application.getConnectionStatus().equals(DaoCoreConstant.APPLICATION_STATUS_DISCONNECTED))
	  return false;
	else
	return true;
  }

}
