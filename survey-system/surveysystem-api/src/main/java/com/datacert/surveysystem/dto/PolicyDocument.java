package com.datacert.surveysystem.dto;

public interface PolicyDocument {

  public Long getPolicyDocumentId();
  
  public String getPolicyDocumentName();

  public Long getPolicyDocumentSize();
  
  public byte[] getPolicyDocumentContent();

}
