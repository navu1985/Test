package com.datacert.surveysystem.dto;

public interface UserResponseDocument {
  public Long getDocId();

  public String getDocName();

  public Long getDocSize();

  public byte[] getDoc();

}
