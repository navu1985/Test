package com.datacert.surveysystem.db.util;


public class SurveyPortalExternalSqlQueries {

  public String selectPolicyDocumentQuery;
  
  public String selectPolicyThumbnailDocument;
  
  public String selectProfileImage;
  
  public String selectSurveyDocument;
  
  public String selectQuestionDocument;
  
  public SurveyPortalExternalSqlQueries(String selectPolicyDocumentQuery,String selectPolicyThumbnailDocument,String selectProfileImage,String selectSurveyDocument,String selectQuestionDocument) {
	this.selectPolicyDocumentQuery=selectPolicyDocumentQuery;
	this.selectPolicyThumbnailDocument=selectPolicyThumbnailDocument;
	this.selectProfileImage=selectProfileImage;
	this.selectSurveyDocument=selectSurveyDocument;
	this.selectQuestionDocument=selectQuestionDocument;
  }
  
  public String getSelectPolicyDocumentQuery() {
	return selectPolicyDocumentQuery;
  }

  public void setSelectPolicyDocumentQuery(String selectPolicyDocumentQuery) {
	this.selectPolicyDocumentQuery = selectPolicyDocumentQuery;
  }

  public String getSelectPolicyThumbnailDocument() {
    return this.selectPolicyThumbnailDocument;
  }

  public  void setSelectPolicyThumbnailDocument(String selectPolicyThumbnailDocument) {
    this.selectPolicyThumbnailDocument = selectPolicyThumbnailDocument;
  }

  public  String getSelectProfileImage() {
    return selectProfileImage;
  }

  public  void setSelectProfileImage(String selectProfileImage) {
    this.selectProfileImage = selectProfileImage;
  }

  public  String getSelectSurveyDocument() {
    return selectSurveyDocument;
  }

  public  void setSelectSurveyDocument(String selectSurveyDocument) {
    this.selectSurveyDocument = selectSurveyDocument;
  }

  public String getSelectQuestionDocument() {
    return selectQuestionDocument;
  }

  public void setSelectQuestionDocument(String selectQuestionDocument) {
    this.selectQuestionDocument = selectQuestionDocument;
  }

}
