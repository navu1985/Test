package com.datacert.surveysystem.db.dao;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.datacert.surveysystem.db.domain.AdminSettings;
import com.datacert.surveysystem.db.domain.PassportDescriptor;
import com.datacert.surveysystem.db.domain.QuestionDescriptor;
import com.datacert.surveysystem.db.domain.SurveyDescriptor;
import com.datacert.surveysystem.db.domain.UserQuestionResponse;
import com.datacert.surveysystem.db.domain.UserSurvey;
import com.datacert.surveysystem.dto.Application;
import com.datacert.surveysystem.dto.UserResponseDocument;

public interface SurveyDao {
  // Get Survey To Display
  public SurveyDescriptor getSurvey(long surveyId, long userId);

  // List of user pending Surveys
//  public List<SurveyDescriptor> getPendingSurveys(long userId);
  public List<SurveyDescriptor> getPendingSurveys(long userId);

  // get admin settings
  public AdminSettings getAdminSettings();

  // List of Completed Surveys
  public List<UserSurvey> getUserCompletedSurvey(int batchSize, Application passport);
//  public List<UserSurvey> getUserCompletedSurvey(int batchSize);

  // Connection between portal and passport
  public void updateConnectionFlag(boolean flag);

  // Adding new Survey to Portal
  public SurveyDescriptor addSurvey(SurveyDescriptor survey);

  // Adding new Question to Survey
  public void addQuestion(QuestionDescriptor question);

  // Is Survey Already Exists
  public boolean isSurveyExists(long surveyId, Application passport);
//  public boolean isSurveyExists(long surveyId);

  // Is Admin Allows Portal Connected to Passport
  public boolean isPortalConnected();

  // User Completed Survey
  public void submitSurvey(String surveyId, long userId);

  // get Global Admin Email
  public String getAdminEmail();

  // update Global Admin Email
  public void setAdminEmail(String surveyStr);

  // List of Error Surveys
  public List<UserSurvey> getErrorSurveys();

  // update to resubmit again
  public void resetUserSurvey(long surveyId, long userId);

  // Update User Survey Status
  public void updateSurveyStatus(UserSurvey userSurvey, String status);
//  public void updateSurveyStatus(long surveyId, long contactId, long userId, String status);

  public UserResponseDocument getsurveyDocument(Long docId);
  public void getsurveyDocument(Long docId, HttpServletResponse response);

  public void saveSurveyAnswer(UserQuestionResponse answer);

  public long uploadDocument(MultipartFile file, long questionId, long userId) throws IOException;

  public void saveSurveyDocumentAnswer(UserQuestionResponse answer, Long userId);

  public UserResponseDocument getsurveyDocumentDetail(Long docId);

  public void deleteSurveyAnswer(Long answerId);

  public void updateSurveyStatusToSaveLater(long surveyId, long userId);

  public boolean isSurveyAssigned(Long surveyId, long userId);

  public long uploadDocument_test(File file, String fileName, long questionId, long userId) throws IOException;

  public String userSurveyResponse(String responseId, PassportDescriptor passport);
//  public String userSurveyResponse(String responseId);

  public Application getPassport(Long passportId, String passportUniqueIdentifier);

  public UserSurvey getUserSurvey(String responseId, PassportDescriptor passport);
//  public UserSurvey getUserSurvey(String responseId);
  
  public HashMap<String,Long> getSurveyCompletedCount(Long userId);

  public void addQuestionDocument(InputStream inputStream, String surveyID, String questionID, Application passport) throws IOException;

  public void downloadQuestionHelpDocument(Long questionID, HttpServletResponse response);

  public QuestionDescriptor addQuestionQuestionDescriptor(QuestionDescriptor questionDesc);

  
}
