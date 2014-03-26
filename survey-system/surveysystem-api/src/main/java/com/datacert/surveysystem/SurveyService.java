package com.datacert.surveysystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.datacert.surveysystem.dto.Answers;
import com.datacert.surveysystem.dto.Application;
import com.datacert.surveysystem.dto.Survey;
import com.datacert.surveysystem.dto.UserResponseDocument;
import com.datacert.surveysystem.dto.UserSurvey;
import com.datacert.surveysystem.surveyresponse.SurveyPullResponse;

@Transactional
public interface SurveyService {

//  Get all Pending Surveys of Users
  public List<Survey> getPendingSurveys(Long userId);
//  public List<? extends SurveyDescriptor> getPendingSurveys(Long userId)

// Get all Questions of Survey and Saved Answer for a User
  public Survey getSurveyQuestion(Long surveyId, Long userId);

//Add Survey to Portal Coming from Passport System 
public void addSurvey(Survey survey,Application passport) throws Exception;
//  public void addSurvey(Survey survey) throws Exception;

//Check Survey Already exists before adding to portal application
public boolean isSurveyExists(long surveyId,Application passport);
//  public boolean isSurveyExists(long surveyId);

//If Communication between Portal and Passport is On or Off
  public boolean isPortalConnected();

// Change Status of survey for a particular User (OPEN/SAVELATER to SUBMITTED)
  public void submitSurvey(String surveyId, long userId);

//Change Status of survey for a particular User (SUBMITTED to SAVE_LATER)
  public void resetUserSurvey(long surveyId, long userId);

//Process the Acknowledgement coming from Passport for Responses
  public void processSurveyAck( UserSurvey userSurvey,String status);
//  public void processSurveyAck(SurveyAckResponse surveyAckResponse, String status);

  //Get all Completed Survey in Pull Response Format to Submit at Passport end
//  public List<SurveyPullResponse> getUserCompletedSurvey(int batchSize);
  public List<SurveyPullResponse> getUserCompletedSurvey(int batchSize,Application passport);

//Save Answer of Survey Question Submitted by User
  public void saveSurveyAnswer(HttpServletRequest request, Answers answers, long userId);

//To check Survey is fetched by valid User(Means Survey is assigned to particular user or not??)
  public boolean isSurveyAssigned(Long surveyId, long userId);

//Save Document type Answer of Survey Question to DB.
  public long saveSurveyAnswerDocument(MultipartFile file, long questionId, long userId) throws IOException;

//Fetch Saved Document Submitted by User
  public UserResponseDocument getsurveyDocument(Long docId);
  public void getsurveyDocument(Long docId,HttpServletResponse response);
  
  public String userSurveyResponse(String responseId,Application passport);
//  public String userSurveyResponse(String responseId);
  
  public Application getPassport(Long passportId,String passportIPAddress);
  
  public UserSurvey getUserSurvey(String responseId,Application passport);
//  public UserSurvey getUserSurvey(String responseId);
  
  public HashMap<String,Long> getSurveyCompletedCount(Long userId);

  public void addQuestionDocument(InputStream inputStream, String surveyID, String questionID, Application passport) throws IOException;

  public void downloadQuestionHelpDocument(Long questionID, HttpServletResponse response);
  
}
