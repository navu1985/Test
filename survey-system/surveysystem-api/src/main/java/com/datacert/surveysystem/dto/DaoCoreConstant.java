package com.datacert.surveysystem.dto;

public interface DaoCoreConstant {

  public enum surveyStatus {
	ACTIVE
  };

  public enum userSurveyStatus {
	OPEN, SUBMITTED, COMPLETED, SUBMITTED_ERROR, SAVE_LATER
  };

  public final String SURVEY_ADMIN_EMAIL = "surveyAdminEmail";
  public final String ACKNOWEDELGE_ERROR = "ERROR";
  
  public final String APPLICATION_STATUS_CONNECTED = "Enabled";
  public final String APPLICATION_STATUS_DISCONNECTED = "Disabled";
  
  public enum policyStatus {
 	ACTIVE,DISABLE,RETIRE
   };
   
   public enum userPolicyStatus {
	 	ACTIVE,DISABLE
  };
   
   public final int PAGE_SIZE=2;
  
   public final static String POLICY_RELATION_STATUS_ADDED="Added";
   public final static String POLICY_RELATION_STATUS_REMOVED="Removed";
   public final static String POLICY_AUDIENCE_ADDED_STATUS="Added";
   public final static String POLICY_AUDIENCE_INACTIVE_STATUS="Removed";
   
   public final static String POLICY_SYNC_SUCCESS_RESPONSE="Success";
   public final static String POLICY_SYNC_ERROR_RESPONSE="Error";
   public final String TEMP_DIR = "temp";
   
   public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
   
   public static final String EMAIL_OR_EMPTY_PATTERN = DaoCoreConstant.EMAIL_PATTERN+"|^$";
   
}
