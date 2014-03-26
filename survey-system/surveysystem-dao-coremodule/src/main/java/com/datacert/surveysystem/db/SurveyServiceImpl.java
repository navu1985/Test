package com.datacert.surveysystem.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.datacert.surveysystem.SurveyService;
import com.datacert.surveysystem.db.dao.SurveyDao;
import com.datacert.surveysystem.db.dao.UserDao;
import com.datacert.surveysystem.db.dao.UserProfileDao;
import com.datacert.surveysystem.db.domain.ExpectedQuestionResponse;
import com.datacert.surveysystem.db.domain.PassportDescriptor;
import com.datacert.surveysystem.db.domain.QuestionDescriptor;
import com.datacert.surveysystem.db.domain.SurveyDescriptor;
import com.datacert.surveysystem.db.domain.UserDescriptor;
import com.datacert.surveysystem.db.domain.UserProfileInfoDescriptor;
import com.datacert.surveysystem.db.domain.UserQuestionResponse;
import com.datacert.surveysystem.db.domain.UserSurvey;
import com.datacert.surveysystem.dto.AnswerType;
import com.datacert.surveysystem.dto.Answers;
import com.datacert.surveysystem.dto.Application;
import com.datacert.surveysystem.dto.DaoCoreConstant.surveyStatus;
import com.datacert.surveysystem.dto.DaoCoreConstant.userSurveyStatus;
import com.datacert.surveysystem.dto.GenericAnswer;
import com.datacert.surveysystem.dto.Question;
import com.datacert.surveysystem.dto.QuestionDto;
import com.datacert.surveysystem.dto.QuestionResponse;
import com.datacert.surveysystem.dto.Survey;
import com.datacert.surveysystem.dto.SurveyDto;
import com.datacert.surveysystem.dto.User;
import com.datacert.surveysystem.dto.UserResponseDocument;
import com.datacert.surveysystem.surveyresponse.SurveyPullResponse;
import com.datacert.surveysystem.surveyresponse.UserSurveyResponse;

/**
 * @author parora
 * 
 */
@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

  @Resource
  private SurveyDao surveyDao;

  @Resource
  private UserDao userDao;

  @Resource
  private UserProfileDao userProfileDao;

  // Get all Pending Surveys of Users
  @Override
  public List<Survey> getPendingSurveys(Long userId) {
	List<SurveyDescriptor> surveys = surveyDao.getPendingSurveys(userId);
	List<Survey> userSurveys = new ArrayList<Survey>();
	for (SurveyDescriptor survey : surveys) {
	  userSurveys.add((Survey) survey);
	}
	return (List<Survey>) userSurveys;
  }

  // Get all Questions of Survey and Saved Answer for a User
  @Override
  public Survey getSurveyQuestion(Long surveyId, Long userId) {
	SurveyDescriptor surveyDescriptor = surveyDao.getSurvey(surveyId, userId);
	SurveyDto survey = new SurveyDto();
	survey.setSurveyId(surveyDescriptor.getSurveyId());
	survey.setSurveyName(surveyDescriptor.getSurveyName());
	Set<Question> questionsDto = new TreeSet<Question>();
	Set<Question> questions = surveyDescriptor.getQuestions();
	for (Question question : questions) {
	  QuestionDto questionDto = new QuestionDto();
	  questionDto.setSequence(question.getSequence());
	  questionDto.setRequired(question.getRequired());
	  questionDto.setAnswerType(question.getAnswerType());
	  questionDto.setQuestionContainsDocument(question.getQuestionContainsDocument());
	  questionDto.setQuestionId(question.getQuestionId());
	  questionDto.setQuestionDescription(question.getQuestionDescription());
	  if(question.getQuestionContainsDocument())
		questionDto.setQuestionDocumentFilename(question.getQuestionDocumentFilename());
	  questionDto.setQuestionResponses(question.getQuestionResponses());
	  GenericAnswer answer = null;
	  AnswerType answerType = question.getAnswerType();
	  Set<UserQuestionResponse> userResponses = ((QuestionDescriptor) question).getUserQuestionResponse();
	  if (!userResponses.isEmpty()) {
		answer = new GenericAnswer();
		for (UserQuestionResponse userQuestionResponse : userResponses) {
		  switch (answerType) {
		  case SHORT_TEXT:
			answer.setAnswerId(userQuestionResponse.getId());
			answer.setResponseShort(userQuestionResponse.getResponseText());
			break;
		  case LONG_TEXT:
			answer.setAnswerId(userQuestionResponse.getId());
			answer.setResponseLong(userQuestionResponse.getResponseText());
			break;
		  case DATE:
			answer.setAnswerId(userQuestionResponse.getId());
			answer.setResponseDate(userQuestionResponse.getReponseDate());
			break;
		  case DATE_RANGE:
			if (userQuestionResponse.getSequence() == 1L) {
			  answer.setResponseDateStart(userQuestionResponse.getReponseDate());
			  answer.setAnswerStartId(userQuestionResponse.getId());
			} else {
			  answer.setResponseDateEnd(userQuestionResponse.getReponseDate());
			  answer.setAnswerEndId(userQuestionResponse.getId());
			}
			break;
		  case MONEY:
			answer.setAnswerId(userQuestionResponse.getId());
			if (userQuestionResponse.getResponseText() != null)
			  answer.setResponseMoney(userQuestionResponse.getResponseText());
			break;
		  case MONEY_RANGE:
			if (userQuestionResponse.getSequence() == 1L) {
			  if (userQuestionResponse.getResponseText() != null)
				answer.setResponseMoneyStart(userQuestionResponse.getResponseText());
			  answer.setAnswerStartId(userQuestionResponse.getId());
			} else {
			  if (userQuestionResponse.getResponseText() != null)
				answer.setResponseMoneyEnd(userQuestionResponse.getResponseText());
			  answer.setAnswerEndId(userQuestionResponse.getId());
			}
			break;
		  case DECIMAL:
			answer.setAnswerId(userQuestionResponse.getId());
			if (userQuestionResponse.getResponseText() != null)
			  answer.setResponseDecimal(userQuestionResponse.getResponseText());
			break;
		  case DECIMAL_RANGE:
			if (userQuestionResponse.getSequence() == 1L) {
			  if (userQuestionResponse.getResponseText() != null)
				answer.setResponseDecimalStart(userQuestionResponse.getResponseText());
			  answer.setAnswerStartId(userQuestionResponse.getId());
			} else {
			  if (userQuestionResponse.getResponseText() != null)
				answer.setResponseDecimalEnd(userQuestionResponse.getResponseText());
			  answer.setAnswerEndId(userQuestionResponse.getId());
			}
			break;
		  case INTEGER:
			answer.setAnswerId(userQuestionResponse.getId());
			if (userQuestionResponse.getResponseText() != null)
			  answer.setResponseInteger(userQuestionResponse.getResponseText());
			break;
		  case INTEGER_RANGE:
			if (userQuestionResponse.getSequence() == 1L) {
			  if (userQuestionResponse.getResponseText() != null)
				answer.setResponseIntegerStart(userQuestionResponse.getResponseText());
			  answer.setAnswerStartId(userQuestionResponse.getId());
			} else {
			  if (userQuestionResponse.getResponseText() != null)
				answer.setResponseIntegerEnd(userQuestionResponse.getResponseText());
			  answer.setAnswerEndId(userQuestionResponse.getId());
			}
			break;
		  case DOCUMENT_UPLOAD:
			answer.setAnswerId(userQuestionResponse.getId());
			if (userQuestionResponse.getDocId() > 0) {
			  UserResponseDocument userResponseDocument = surveyDao.getsurveyDocumentDetail(userQuestionResponse.getDocId());
			  answer.setResponseDocName(userResponseDocument.getDocName());
			  answer.setResponseDocId(userResponseDocument.getDocId());
			}
			break;
		  case LOOKUP_LIST:
			answer.setAnswerId(userQuestionResponse.getId());
			if (userQuestionResponse.getResponseText() != null) {
			  answer.setResponseLookup(userQuestionResponse.getResponseText());
			}
			break;
		  default:
			break;
		  }
		}
	  }
	  questionDto.setAnswer(answer);
	  questionsDto.add(questionDto);
	}
	survey.setQuestions(questionsDto);
	return survey;
  }

  // Add Survey to Portal Coming from Passport System
  @Override
  @Transactional(rollbackFor={Exception.class})
  public void addSurvey(Survey surveyTemp, Application passport) throws Exception {
	// public void addSurvey(Survey surveyTemp ) throws Exception {
	SurveyDescriptor survey = new SurveyDescriptor();
	survey.setStatus(surveyStatus.ACTIVE.name());
	survey.setSurveyAssignDate(surveyTemp.getSurveyAssignDate());
	survey.setSurveyDueDate(surveyTemp.getSurveyDueDate());
	survey.setPassportSurveyId(surveyTemp.getSurveyId());
	survey.setSurveyName(surveyTemp.getSurveyName());
	survey.setPassport((PassportDescriptor) passport);
	survey=surveyDao.addSurvey(survey);
	Set<QuestionDescriptor> surveyQuestions = new HashSet<QuestionDescriptor>();
	Set<Question> questions = surveyTemp.getQuestions();
	for (Question question : questions) {
	  QuestionDescriptor questionDesc = new QuestionDescriptor();
	  questionDesc.setPassportQuestionId(question.getQuestionId());
	  questionDesc.setAnswerType(question.getAnswerType());
	  questionDesc.setDescription(question.getQuestionDescription());
	  questionDesc.setisListReponse(question.getAnswerType().equals(AnswerType.LOOKUP_LIST));
	  questionDesc.setRequired(question.getRequired());
	  questionDesc.setSequence(question.getSequence());
	  if(question.getQuestionDocumentFilename()!=null)
		questionDesc.setQuestionDocumentFilename(question.getQuestionDocumentFilename());
	  questionDesc.setSurvey(survey);
	  if (question.getAnswerType().equals(AnswerType.LOOKUP_LIST)) {
		Set<ExpectedQuestionResponse> expectedQuestionResponse = new HashSet<ExpectedQuestionResponse>();
		List<QuestionResponse> questionResponses = question.getQuestionResponses();
		for (QuestionResponse questionResponse : questionResponses) {
		  ExpectedQuestionResponse expectedQuestionAnswer = new ExpectedQuestionResponse();
		  expectedQuestionAnswer.setExpectedResponseText(questionResponse.getResponseDescription());
		  expectedQuestionAnswer.setSequence(questionResponse.getResponseSequence());
		  expectedQuestionAnswer.setQuestion(questionDesc);
		  expectedQuestionResponse.add(expectedQuestionAnswer);
		}
		questionDesc.setExpectedQuestionResponse(expectedQuestionResponse);
	  }
	  questionDesc=surveyDao.addQuestionQuestionDescriptor(questionDesc);
	  surveyQuestions.add(questionDesc);
	}
	Set<UserSurvey> surveyUsers = new HashSet<UserSurvey>();
	Set<User> users = surveyTemp.getUsers();
	for (User user : users) {
	  UserSurvey userSurvey = new UserSurvey();
	  UserDescriptor userDesc = null;
	  UserDescriptor checkUser = userDao.getUser(user.getUsername(), null, null);
	  if (checkUser == null) {
		userDesc = new UserDescriptor(user.getUsername(), "");
		userDesc.setUserPassportId(user.getContactId());
		try {
		  userDao.addUpdateUser(userDesc);
		} catch (org.hibernate.NonUniqueObjectException e) {
		  throw e;
		}
		userSurvey.setSurvey(survey);
		userSurvey.setUser(userDesc);
		userSurvey.setUserPassportId(user.getContactId());
		userSurvey.setSurveyResponseId(user.getSurveyResponseId());
		userSurvey.setStatus(userSurveyStatus.OPEN.name());
	  } else {
		userDesc = checkUser;
		userDao.updateUserContactId(user.getContactId(), checkUser.getUserId());
		userSurvey.setSurvey(survey);
		userSurvey.setUserPassportId(user.getContactId());
		userSurvey.setUser(checkUser);
		userSurvey.setSurveyResponseId(user.getSurveyResponseId());
		userSurvey.setStatus(userSurveyStatus.OPEN.name());
	  }
	  surveyUsers.add(userSurvey);
	  UserProfileInfoDescriptor userProfileInfoDescriptor = userDesc.getUserProfileInfoDescriptor();
	  if (userProfileInfoDescriptor == null) {
		userProfileInfoDescriptor = new UserProfileInfoDescriptor();
		userDesc.setUserProfileInfoDescriptor(userProfileInfoDescriptor);
		userProfileInfoDescriptor.setUser(userDesc);
	  }
	  userProfileInfoDescriptor.setFirstName(user.getFirstName());
	  userProfileInfoDescriptor.setLastName(user.getLastName());
	  userProfileDao.saveUserProfile(userProfileInfoDescriptor);
	}
	survey.setUserSurveys(surveyUsers);
	survey.setSurveyQuestions(surveyQuestions);
	surveyDao.addSurvey(survey);
  }

  // Check Survey Already exists before adding to portal application
  @Override
  public boolean isSurveyExists(long surveyId, Application passport) {
	// public boolean isSurveyExists(long surveyId) {
	return surveyDao.isSurveyExists(surveyId, passport);
	// return surveyDao.isSurveyExists(surveyId);
  }

  // Is Communication between Portal and Passport is On or Off
  @Override
  public boolean isPortalConnected() {
	return surveyDao.isPortalConnected();
  }

  // Change Status of survey for a particular User (OPEN/SAVELATER to SUBMITTED)
  @Override
  public void submitSurvey(String surveyId, long userId) {
	surveyDao.submitSurvey(surveyId, userId);
  }

  // Process the Acknowledgement coming from Passport for Responses
  @Override
  public void processSurveyAck(com.datacert.surveysystem.dto.UserSurvey userSurvey, String status) {
	surveyDao.updateSurveyStatus((UserSurvey) userSurvey, status);
  }

  /*
   * @Override public void processSurveyAck(SurveyAckResponse surveyAckResponse,
   * String status) { Long contactId =
   * Long.valueOf(surveyAckResponse.getUserContactId());
   * surveyDao.updateSurveyStatus(Long.valueOf(surveyAckResponse.getSurveyId()),
   * contactId, userDao.getUser(null, null, contactId) .getUserId(), status); }
   */

  // Change Status of survey for a particular User (SUBMITTED to SAVE_LATER)
  @Override
  public void resetUserSurvey(long surveyId, long userId) {
	surveyDao.resetUserSurvey(surveyId, userId);
  }

  // Save Document type Answer of Survey Question to DB.
  @Override
  public long saveSurveyAnswerDocument(MultipartFile file, long questionId, long userId) throws IOException {
	long docAnswerId = 0;
	docAnswerId = surveyDao.uploadDocument(file, questionId, userId);
	return docAnswerId;
  }

  // Fetch Saved Document Submitted by User
  @Override
  public UserResponseDocument getsurveyDocument(Long docId) {
	return surveyDao.getsurveyDocument(docId);
  }

  @Override
  public void getsurveyDocument(Long docId, HttpServletResponse response) {
	surveyDao.getsurveyDocument(docId, response);
  }

  // Get all Completed Survey in Pull Response Format to Submit at Passport end
  @Override
  public List<SurveyPullResponse> getUserCompletedSurvey(int batchSize, Application passport) {
	// public List<SurveyPullResponse> getUserCompletedSurvey(int batchSize) {
	List<UserSurvey> userCompletedSurvey = surveyDao.getUserCompletedSurvey(batchSize, passport);
	// List<UserSurvey> userCompletedSurvey =
	// surveyDao.getUserCompletedSurvey(batchSize);
	List<SurveyPullResponse> surveyPullResponseList = new ArrayList<SurveyPullResponse>();
	for (UserSurvey userSurvey : userCompletedSurvey) {
	  SurveyPullResponse surveyPullResponse = new SurveyPullResponse();
	  surveyPullResponse.setResponseId(userSurvey.getSurveyResponseId());

	  // Change Done for MultiPle Passport Asscoiate (Added )
	  surveyPullResponse.setSurveyId(userSurvey.getSurvey().getSurveyId());
	  surveyPullResponse.setUserContactId(userSurvey.getUser().getContactId());

	  SurveyDescriptor surveyDescriptor = surveyDao.getSurvey(userSurvey.getSurvey().getSurveyId(), userSurvey.getUser()
			  .getUserId());

	  List<UserSurveyResponse> userSurveyResponseList = new ArrayList<UserSurveyResponse>();

	  Set<Question> questions = surveyDescriptor.getQuestions();
	  for (Question question : questions) {
		UserSurveyResponse userSurveyResponse = new UserSurveyResponse();
		userSurveyResponse.setQuestionId(question.getPassportQuestionId());

		AnswerType answerType = question.getAnswerType();
		Set<UserQuestionResponse> userResponses = ((QuestionDescriptor) question).getUserQuestionResponse();
		if (!userResponses.isEmpty()) {

		  for (UserQuestionResponse userQuestionResponse : userResponses) {
			switch (answerType) {
			case SHORT_TEXT:
			  if (userQuestionResponse.getResponseText() != null)
				userSurveyResponse.setQuestionResp(userQuestionResponse.getResponseText());
			  break;
			case LONG_TEXT:
			  if (userQuestionResponse.getResponseText() != null)
				userSurveyResponse.setQuestionResp(userQuestionResponse.getResponseText());
			  break;
			case DATE:
			  userSurveyResponse.setQuestionResp(userQuestionResponse.getReponseDate());
			  break;
			case DATE_RANGE:
			  if (userQuestionResponse.getSequence() == 1L) {
				userSurveyResponse.setQuestionRespStart(userQuestionResponse.getReponseDate());
			  } else {
				userSurveyResponse.setQuestionRespEnd(userQuestionResponse.getReponseDate());
			  }
			  break;
			case MONEY:
			  if (userQuestionResponse.getResponseText() != null)
				userSurveyResponse.setQuestionResp(userQuestionResponse.getResponseText());
			  break;
			case MONEY_RANGE:
			  if (userQuestionResponse.getSequence() == 1L) {
				if (userQuestionResponse.getResponseText() != null)
				  userSurveyResponse.setQuestionRespStart(userQuestionResponse.getResponseText());
			  } else {
				if (userQuestionResponse.getResponseText() != null)
				  userSurveyResponse.setQuestionRespEnd(userQuestionResponse.getResponseText());
			  }
			  break;
			case DECIMAL:
			  if (userQuestionResponse.getResponseText() != null)
				userSurveyResponse.setQuestionResp(userQuestionResponse.getResponseText());
			  break;
			case DECIMAL_RANGE:
			  if (userQuestionResponse.getResponseText() != null)
				if (userQuestionResponse.getSequence() == 1L) {
				  userSurveyResponse.setQuestionRespStart(userQuestionResponse.getResponseText());
				} else {
				  userSurveyResponse.setQuestionRespEnd(userQuestionResponse.getResponseText());
				}
			  break;
			case INTEGER:
			  if (userQuestionResponse.getResponseText() != null)
				userSurveyResponse.setQuestionResp(userQuestionResponse.getResponseText());
			  break;
			case INTEGER_RANGE:
			  if (userQuestionResponse.getResponseText() != null)
				if (userQuestionResponse.getSequence() == 1L) {
				  userSurveyResponse.setQuestionRespStart(userQuestionResponse.getResponseText());
				} else {
				  userSurveyResponse.setQuestionRespEnd(userQuestionResponse.getResponseText());
				}
			  break;
			case DOCUMENT_UPLOAD:
			  if (userQuestionResponse.getDocId() > 0) {
				UserResponseDocument userResponseDocument = surveyDao.getsurveyDocumentDetail(userQuestionResponse.getDocId());
				userSurveyResponse.setQuestionRespFile(userResponseDocument.getDocName());
				userSurveyResponse.setQuestionRespFileID(userResponseDocument.getDocId());
				userSurveyResponse.setQuestionRespFileSize(userResponseDocument.getDocSize());
			  }
			  break;
			case LOOKUP_LIST:
			  if (userQuestionResponse.getResponseText() != null) {
				userSurveyResponse.setQuestionResp(userQuestionResponse.getResponseText());
			  }
			  break;
			default:
			  break;
			}
		  }
		}
		userSurveyResponseList.add(userSurveyResponse);
	  }
	  surveyPullResponse.setUserSurveyResponse(userSurveyResponseList);
	  surveyPullResponseList.add(surveyPullResponse);
	}
	return surveyPullResponseList;
  }

  // Save Answer of Survey Question Submitted by User
  @Override
  public void saveSurveyAnswer(HttpServletRequest request, Answers answers, long userId) {
	UserQuestionResponse response = null, startResponse = null, endResponse = null, docResponse = null;
	for (GenericAnswer answer : answers.getAnswers()) {
	  switch (answer.getAnswerType()) {
	  case SHORT_TEXT:
		if (!answer.getResponseShort().isEmpty()) {
		  response = new UserQuestionResponse();
		  response.setId(answer.getAnswerId());
		  response.setQuestionId(answer.getQuestionId());
		  response.setResponseText(answer.getResponseShort());
		  response.setSequence(1);
		  response.setUserId(userId);
		  surveyDao.saveSurveyAnswer(response);
		} else if (answer.getAnswerId() > 0) {
		  surveyDao.deleteSurveyAnswer(answer.getAnswerId());
		}
		break;
	  case DATE:
		if (answer.getResponseDate() != null) {
		  response = new UserQuestionResponse();
		  response.setId(answer.getAnswerId());
		  response.setQuestionId(answer.getQuestionId());
		  response.setReponseDate(answer.getResponseDate());
		  response.setSequence(1);
		  response.setUserId(userId);
		  surveyDao.saveSurveyAnswer(response);
		} else if (answer.getAnswerId() > 0) {
		  surveyDao.deleteSurveyAnswer(answer.getAnswerId());
		}
		break;
	  case DATE_RANGE:
		if (answer.getResponseDateStart() != null) {
		  startResponse = new UserQuestionResponse();
		  startResponse.setId(answer.getAnswerStartId());
		  startResponse.setQuestionId(answer.getQuestionId());
		  startResponse.setReponseDate(answer.getResponseDateStart());
		  startResponse.setSequence(1);
		  startResponse.setUserId(userId);
		  surveyDao.saveSurveyAnswer(startResponse);
		} else if (answer.getAnswerStartId() > 0) {
		  surveyDao.deleteSurveyAnswer(answer.getAnswerStartId());
		}
		if (answer.getResponseDateEnd() != null) {
		  endResponse = new UserQuestionResponse();
		  endResponse.setId(answer.getAnswerEndId());
		  endResponse.setQuestionId(answer.getQuestionId());
		  endResponse.setReponseDate(answer.getResponseDateEnd());
		  endResponse.setSequence(2);
		  endResponse.setUserId(userId);
		  surveyDao.saveSurveyAnswer(endResponse);
		} else if (answer.getAnswerEndId() > 0) {
		  surveyDao.deleteSurveyAnswer(answer.getAnswerEndId());
		}
		break;
	  case DOCUMENT_UPLOAD:
		if (answer.getResponseDocId() != null) {
		  docResponse = new UserQuestionResponse();
		  docResponse.setId(answer.getAnswerId());
		  docResponse.setQuestionId(answer.getQuestionId());
		  docResponse.setDocId(answer.getResponseDocId());
		  docResponse.setSequence(1);
		  docResponse.setUserId(userId);
		  // surveyDao.saveSurveyDocumentAnswer(docResponse);
		  surveyDao.saveSurveyDocumentAnswer(docResponse, userId);
		} else if (answer.getAnswerId() > 0) {
		  surveyDao.deleteSurveyAnswer(answer.getAnswerId());
		}
		break;
	  case LONG_TEXT:
		if (!answer.getResponseLong().isEmpty()) {
		  response = new UserQuestionResponse();
		  response.setId(answer.getAnswerId());
		  response.setQuestionId(answer.getQuestionId());
		  response.setResponseText(answer.getResponseLong());
		  response.setSequence(1);
		  response.setUserId(userId);
		  surveyDao.saveSurveyAnswer(response);
		} else if (answer.getAnswerId() > 0) {
		  surveyDao.deleteSurveyAnswer(answer.getAnswerId());
		}
		break;
	  case MONEY:
		if (answer.getResponseMoney() != null) {
		  response = new UserQuestionResponse();
		  response.setId(answer.getAnswerId());
		  response.setQuestionId(answer.getQuestionId());
		  response.setResponseText(answer.getResponseMoney());
		  response.setSequence(1);
		  response.setUserId(userId);
		  surveyDao.saveSurveyAnswer(response);
		} else if (answer.getAnswerId() > 0) {
		  surveyDao.deleteSurveyAnswer(answer.getAnswerId());
		}
		break;
	  case MONEY_RANGE:
		if (answer.getResponseMoneyStart() != null) {
		  startResponse = new UserQuestionResponse();
		  startResponse.setId(answer.getAnswerStartId());
		  startResponse.setQuestionId(answer.getQuestionId());
		  startResponse.setResponseText(answer.getResponseMoneyStart());
		  startResponse.setSequence(1);
		  startResponse.setUserId(userId);
		  surveyDao.saveSurveyAnswer(startResponse);
		} else if (answer.getAnswerStartId() > 0) {
		  surveyDao.deleteSurveyAnswer(answer.getAnswerStartId());
		}
		if (answer.getResponseMoneyEnd() != null) {
		  endResponse = new UserQuestionResponse();
		  endResponse.setId(answer.getAnswerEndId());
		  endResponse.setQuestionId(answer.getQuestionId());
		  endResponse.setResponseText(answer.getResponseMoneyEnd());
		  endResponse.setSequence(2);
		  endResponse.setUserId(userId);
		  surveyDao.saveSurveyAnswer(endResponse);
		} else if (answer.getAnswerEndId() > 0) {
		  surveyDao.deleteSurveyAnswer(answer.getAnswerEndId());
		}
		break;
	  case INTEGER:
		if (answer.getResponseInteger() != null) {
		  response = new UserQuestionResponse();
		  response.setId(answer.getAnswerId());
		  response.setQuestionId(answer.getQuestionId());
		  response.setResponseText(answer.getResponseInteger());
		  response.setSequence(1);
		  response.setUserId(userId);
		  surveyDao.saveSurveyAnswer(response);
		} else if (answer.getAnswerId() > 0) {
		  surveyDao.deleteSurveyAnswer(answer.getAnswerId());
		}
		break;
	  case INTEGER_RANGE:
		if (answer.getResponseIntegerStart() != null) {
		  startResponse = new UserQuestionResponse();
		  startResponse.setId(answer.getAnswerStartId());
		  startResponse.setQuestionId(answer.getQuestionId());
		  startResponse.setResponseText(answer.getResponseIntegerStart());
		  startResponse.setSequence(1);
		  startResponse.setUserId(userId);
		  surveyDao.saveSurveyAnswer(startResponse);
		} else if (answer.getAnswerStartId() > 0) {
		  surveyDao.deleteSurveyAnswer(answer.getAnswerStartId());
		}
		if (answer.getResponseIntegerEnd() != null) {
		  endResponse = new UserQuestionResponse();
		  endResponse.setId(answer.getAnswerEndId());
		  endResponse.setQuestionId(answer.getQuestionId());
		  endResponse.setResponseText(answer.getResponseIntegerEnd());
		  endResponse.setSequence(2);
		  endResponse.setUserId(userId);
		  surveyDao.saveSurveyAnswer(endResponse);
		} else if (answer.getAnswerEndId() > 0) {
		  surveyDao.deleteSurveyAnswer(answer.getAnswerEndId());
		}
		break;
	  case DECIMAL:
		if (answer.getResponseDecimal() != null) {
		  response = new UserQuestionResponse();
		  response.setId(answer.getAnswerId());
		  response.setQuestionId(answer.getQuestionId());
		  response.setResponseText(answer.getResponseDecimal());
		  response.setSequence(1);
		  response.setUserId(userId);
		  surveyDao.saveSurveyAnswer(response);
		} else if (answer.getAnswerId() > 0) {
		  surveyDao.deleteSurveyAnswer(answer.getAnswerId());
		}
		break;
	  case DECIMAL_RANGE:
		if (answer.getResponseDecimalStart() != null) {
		  startResponse = new UserQuestionResponse();
		  startResponse.setId(answer.getAnswerStartId());
		  startResponse.setQuestionId(answer.getQuestionId());
		  startResponse.setResponseText(answer.getResponseDecimalStart());
		  startResponse.setSequence(1);
		  startResponse.setUserId(userId);
		  surveyDao.saveSurveyAnswer(startResponse);
		} else if (answer.getAnswerStartId() > 0) {
		  surveyDao.deleteSurveyAnswer(answer.getAnswerStartId());
		}
		if (answer.getResponseDecimalEnd() != null) {
		  endResponse = new UserQuestionResponse();
		  endResponse.setId(answer.getAnswerEndId());
		  endResponse.setQuestionId(answer.getQuestionId());
		  endResponse.setResponseText(answer.getResponseDecimalEnd());
		  endResponse.setSequence(2);
		  endResponse.setUserId(userId);
		  surveyDao.saveSurveyAnswer(endResponse);
		} else if (answer.getAnswerEndId() > 0) {
		  surveyDao.deleteSurveyAnswer(answer.getAnswerEndId());
		}
		break;
	  case LOOKUP_LIST:
		if (answer.getResponseLookup() != null) {
		  response = new UserQuestionResponse();
		  response.setId(answer.getAnswerId());
		  response.setQuestionId(answer.getQuestionId());
		  response.setResponseText(answer.getResponseLookup());
		  response.setSequence(1);
		  response.setUserId(userId);
		  surveyDao.saveSurveyAnswer(response);
		} else if (answer.getAnswerId() > 0) {
		  surveyDao.deleteSurveyAnswer(answer.getAnswerId());
		}
		break;
	  default:
		break;
	  }
	}
	surveyDao.updateSurveyStatusToSaveLater(answers.getSurveyId(), userId);
  }

  // To check Survey is fetched by valid User(Means Survey is assigned to
  // particular user or not??)
  @Override
  public boolean isSurveyAssigned(Long surveyId, long userId) {
	return surveyDao.isSurveyAssigned(surveyId, userId);
  }

  @Override
  public String userSurveyResponse(String responseId, Application passport) {
	return surveyDao.userSurveyResponse(responseId, (PassportDescriptor) passport);
  }

  /*
   * public String userSurveyResponse(String responseId) { return
   * surveyDao.userSurveyResponse(responseId); }
   */

  @Override
  public Application getPassport(Long passportId, String passportIPAddress) {
	return surveyDao.getPassport(passportId, passportIPAddress);
  }

  /**/

  @Override
  public com.datacert.surveysystem.dto.UserSurvey getUserSurvey(String responseId, Application passport) {
	return (com.datacert.surveysystem.dto.UserSurvey) surveyDao.getUserSurvey(responseId, (PassportDescriptor) passport);
  }

  @Override
  public HashMap<String, Long> getSurveyCompletedCount(Long userId) {
	return surveyDao.getSurveyCompletedCount(userId);
  }

  @Override
  public void addQuestionDocument(InputStream inputStream, String surveyID, String questionID, Application passport) throws IOException {
	surveyDao.addQuestionDocument(inputStream,surveyID,questionID,passport);
  }

  @Override
  public void downloadQuestionHelpDocument(Long questionID, HttpServletResponse response) {
	surveyDao.downloadQuestionHelpDocument(questionID,response);
  }

  /*
   * @Override public com.datacert.surveysystem.dto.UserSurvey
   * getUserSurvey(String responseId) { return
   * (com.datacert.surveysystem.dto.UserSurvey
   * )surveyDao.getUserSurvey(responseId); }
   */
}
