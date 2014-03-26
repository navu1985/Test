package com.datacert.surveysystem;

import java.util.List;

import com.datacert.surveysystem.dto.Admin;
import com.datacert.surveysystem.dto.Application;

public interface AdminService {
  public Admin getAdminSettings();

  public void updateAdminSetting(Admin setting);

  public void connect(String url) throws Exception;

  public String getAdminEmail();

  public void setAdminEmail(String adminEmail);

  public List<? extends com.datacert.surveysystem.dto.UserSurvey> getErrorSurveys();
  
  public List<? extends Application> getConnectedApplication();
  
  public void deleteCommunication(String applicationId);
  
  public Application getApplication(String applicationId);
  
  public Application getApplicationByUniqueid(String applicationId);

  public boolean addApplication(String applicationId,String dbGUID);
  
  public boolean isApplicationActive(String applicationId);
  
}
