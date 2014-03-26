package com.datacert.portal.util;


public interface SurveyPortalConstants {
  
  static final String RESPONSE_FORMAT="{\"reason\":\" %s\"}";
  static final String RESPONSE_OK = String.format(RESPONSE_FORMAT, "Request Successfully Completed");
  static final String PORTAL_NOT_CONNCTED = String.format(RESPONSE_FORMAT,"Portal is not connected with Passport Please check Connection between portal and passport on Portal Setting Page");
  static final String PORTAL_EXCEPTION = String.format(RESPONSE_FORMAT, "SomeThing wrong in Portal");
  static final String RESPONSE_BAD_REQUEST = String.format(RESPONSE_FORMAT, "BAD REQUEST");
  static final String TEMP_DIR = "temp";
}
