package com.datacert.surveysystem.db.dao;

import java.util.List;

import com.datacert.surveysystem.db.domain.PassportDescriptor;
import com.datacert.surveysystem.dto.Admin;

public interface AdminDao {

  /**
   * Get all Passport List from Database.
   */
	
  // update Admin Setting
  public void updateAdminSetting(Admin settings);	
	
  public List<PassportDescriptor> getConnectedApplication();

  public void stopApplicationCommunication(String applicationId,String applicationStatus);

  public void deleteCommunication(String applicationId);

  public boolean addApplication(PassportDescriptor passport);

  public void updateApplication(PassportDescriptor passport);
  
  public PassportDescriptor getApplication(String applicationId);

  public PassportDescriptor getApplicationbyUniqueID(String applicationIdentifier);
}

