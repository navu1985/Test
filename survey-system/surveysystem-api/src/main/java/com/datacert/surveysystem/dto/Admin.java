package com.datacert.surveysystem.dto;

public interface Admin {

  public long getId();

  public boolean isConnectionFlag();

  public String getPageName();
  
  public String getNoDataMessage();
  
  public Boolean getShowReportAnIssue();
  
  public Boolean getShowSubmitAnInquiry();
  
}
