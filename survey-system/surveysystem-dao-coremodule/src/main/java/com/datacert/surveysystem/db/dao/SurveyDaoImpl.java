package com.datacert.surveysystem.db.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.datacert.surveysystem.db.domain.AdminSettings;
import com.datacert.surveysystem.db.domain.PassportDescriptor;
import com.datacert.surveysystem.db.domain.QuestionDescriptor;
import com.datacert.surveysystem.db.domain.QuestionDocumentDescriptor;
import com.datacert.surveysystem.db.domain.SurveyDescriptor;
import com.datacert.surveysystem.db.domain.UserQuestionResponse;
import com.datacert.surveysystem.db.domain.UserSurvey;
import com.datacert.surveysystem.db.domain.UserSurveyDocument;
import com.datacert.surveysystem.db.util.SurveyPortalExternalSqlQueries;
import com.datacert.surveysystem.dto.Application;
import com.datacert.surveysystem.dto.DaoCoreConstant;
import com.datacert.surveysystem.dto.UserResponseDocument;

@Repository("surveyDao")
@Transactional
public class SurveyDaoImpl implements SurveyDao {

  @Resource
  private SessionFactory sessionFactory;
  
  @Resource
  @Qualifier("externalQueries")
  private SurveyPortalExternalSqlQueries  externalSqlQueries;
  
  @Override
  @Transactional(readOnly = true)
  public List<UserSurvey> getUserCompletedSurvey(int batchSize,Application passport) {
	String hql ="from UserSurvey userSurvey join fetch userSurvey.pk.survey as survey where userSurvey.status=:status and survey.passport=:passport order by userSurvey.completedOn asc";
	@SuppressWarnings("unchecked")
	List<UserSurvey> readyToSumitUserSurvey  = sessionFactory.getCurrentSession().createQuery(hql)
	.setParameter("status", DaoCoreConstant.userSurveyStatus.SUBMITTED.name())
	.setParameter("passport",passport)
	.setMaxResults(batchSize)
	.setFirstResult(0).list();
	return readyToSumitUserSurvey;
  }
  
  @Override
  @Transactional(readOnly = true)
  public SurveyDescriptor getSurvey(long surveyId, long userId) {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SurveyDescriptor.class);
	criteria.add(Restrictions.eq("surveyId", surveyId));
	criteria.setTimeout(10);
	criteria.setFetchMode("surveyQuestions", FetchMode.JOIN);
	criteria.createAlias("surveyQuestions.userQuestionResponse", "surveyUserResponse",
			org.hibernate.sql.JoinFragment.LEFT_OUTER_JOIN, Restrictions.eq("surveyUserResponse.userId", userId));
	SurveyDescriptor survey = (SurveyDescriptor) criteria.uniqueResult();
	return survey;
  }

  @SuppressWarnings("unchecked")
  @Override
  @Transactional(readOnly = true)
  public List<SurveyDescriptor> getPendingSurveys(long userId) {
	List<SurveyDescriptor> surveyList = sessionFactory.getCurrentSession().getNamedQuery("getPendingSurveys")
			.setParameter("userId", userId)
			.setParameter("userSaveLaterStatus", DaoCoreConstant.userSurveyStatus.SAVE_LATER.name())
			.setParameter("userOpenStatus", DaoCoreConstant.userSurveyStatus.OPEN.name())
			.setParameter("status", DaoCoreConstant.surveyStatus.ACTIVE.name())
			.setResultTransformer(Transformers.aliasToBean(SurveyDescriptor.class)).list();
	return surveyList;
  }

  @Override
  @Transactional(readOnly = true)
  public AdminSettings getAdminSettings() {
	String hql = "from AdminSettings";
	AdminSettings settings = new AdminSettings();
	@SuppressWarnings("unchecked")
	List<AdminSettings> settingsList = sessionFactory.getCurrentSession().createQuery(hql).list();
	if (settingsList.size() > 0) {
	  settings = settingsList.get(0);
	}
	return settings;
  }

  @Override
  public void updateConnectionFlag(boolean connectionFlag) {
	String hql = "update AdminSettings set connectionFlag=:connectionFlag";
	sessionFactory.getCurrentSession().createQuery(hql).setParameter("connectionFlag", connectionFlag).executeUpdate();
  }

  @Override
  @Transactional(propagation=Propagation.REQUIRED)
  public SurveyDescriptor addSurvey(SurveyDescriptor survey) {
	sessionFactory.getCurrentSession().saveOrUpdate(survey);
	return survey;
  }
  
  
  @Override
  public void addQuestion(QuestionDescriptor question) {
	sessionFactory.getCurrentSession().saveOrUpdate(question);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean isSurveyExists(long surveyId ,Application passport) {
	String hql = "Select count(*) from SurveyDescriptor where passportSurveyId=:surveyId and passport=:passport";
	return (Long) sessionFactory.getCurrentSession().createQuery(hql).setParameter("surveyId", surveyId)
			.setParameter("passport", passport)
			.list().get(0) > 0;
  }
  
  @Override
  @Transactional(readOnly = true)
  public boolean isPortalConnected() {
	String hql = "from AdminSettings";
	AdminSettings settings = (AdminSettings) sessionFactory.getCurrentSession().createQuery(hql).list().get(0);
	return settings.isConnectionFlag();
  }

  @Override
  public void submitSurvey(String surveyId, long userId) {
	String hql = "update UserSurvey set status=:status, completedOn=:completedOn where pk.survey.surveyId=:surveyId and pk.user.userId=:userId";
	sessionFactory.getCurrentSession().createQuery(hql).setParameter("status", DaoCoreConstant.userSurveyStatus.SUBMITTED.name())
			.setParameter("completedOn", new Date()).setParameter("surveyId", Long.valueOf(surveyId))
			.setParameter("userId", userId).executeUpdate();
  }

  @Override
  public void resetUserSurvey(long surveyId, long userId) {
	String hql = "update UserSurvey set status=:status where pk.survey.surveyId=:surveyId and pk.user.userId=:userId";
	sessionFactory.getCurrentSession().createQuery(hql).setParameter("status", DaoCoreConstant.userSurveyStatus.SAVE_LATER.name())
			.setParameter("surveyId", Long.valueOf(surveyId)).setParameter("userId", userId).executeUpdate();
  }

  @Override
  @Transactional(readOnly = true)
  public String getAdminEmail() {
	String hql = "Select adminEmail from AdminSettings";
	String adminEmail = (String) sessionFactory.getCurrentSession().createQuery(hql).list().get(0);
	return adminEmail;
  }

  @Override
  public void setAdminEmail(String adminEmail) {
	String hql = "update AdminSettings set adminEmail=:adminEmail";
	sessionFactory.getCurrentSession().createQuery(hql).setParameter("adminEmail", adminEmail).executeUpdate();
  }

  @Override
  public List<UserSurvey> getErrorSurveys() {
	String hql = "from UserSurvey where status =:submitError and  pk.survey.status=:active";
	@SuppressWarnings("unchecked")
	List<UserSurvey> surveyList = sessionFactory.getCurrentSession().createQuery(hql)
			.setParameter("submitError", DaoCoreConstant.userSurveyStatus.SUBMITTED_ERROR.name())
			.setParameter("active", DaoCoreConstant.surveyStatus.ACTIVE.name()).list();
	return surveyList;
  }

  @Override
  public void updateSurveyStatus(UserSurvey userSurvey,String status) {
	Session session= sessionFactory.getCurrentSession(); 
	if (status.equals(DaoCoreConstant.userSurveyStatus.SUBMITTED_ERROR.name())) {
		userSurvey.setLastError(Calendar.getInstance().getTime());
		userSurvey.setStatus(status);
		session.saveOrUpdate(userSurvey);
		return ;
	} else {

	  userSurvey.setStatus(status);
	  session.saveOrUpdate(userSurvey);
		
	  String deleteHql = "delete from UserQuestionResponse where questionId in (select questionId from QuestionDescriptor where survey.surveyId=:surveyId) and userId=:userId";
	  session.createQuery(deleteHql).setParameter("surveyId", userSurvey.getSurvey().getSurveyId())
			  .setParameter("userId", userSurvey.getUser().getUserId()).executeUpdate();
	  
	  String deleteDocumentHql = "delete from UserSurveyDocument where questionId in (select questionId from QuestionDescriptor where survey.surveyId=:surveyId) and userId=:userId";
	  session.createQuery(deleteDocumentHql).setParameter("surveyId", userSurvey.getSurvey().getSurveyId())
			  .setParameter("userId", userSurvey.getUser().getUserId()).executeUpdate();
	  return;
	}
  }
  
  @Override
  public UserResponseDocument getsurveyDocument(Long docId) {
	return (UserSurveyDocument) sessionFactory.getCurrentSession().get(UserSurveyDocument.class, docId);
  }
  
  @Override
  public void getsurveyDocument(final Long docId, final HttpServletResponse response) {
	Session session = sessionFactory.getCurrentSession();
	session.doWork(new Work() {
	  @Override
	  public void execute(Connection connection) throws SQLException {
		String sql= externalSqlQueries.selectSurveyDocument;
		PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
		stm.setFloat(1,docId);
		ResultSet rset =stm.executeQuery();
		try{
			if(rset.next()){
				try {
				  response.setHeader("Content-Disposition", "filename=\""+rset.getString("doc_name")+"\"");
				  IOUtils.copyLarge(rset.getAsciiStream("doc"), response.getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}finally{
		   rset.close();
		   stm.close();
		}
	  }
	});
  }

  @Override
  public UserResponseDocument getsurveyDocumentDetail(Long docId) {
	String hql = "select new UserSurveyDocument(docId,docName,docSize)  from UserSurveyDocument where docId=:docId";
	return (UserSurveyDocument) sessionFactory.getCurrentSession().createQuery(hql).setParameter("docId", docId).uniqueResult();
  }

  @Override
  public void saveSurveyAnswer(UserQuestionResponse answer) {
	sessionFactory.getCurrentSession().saveOrUpdate(answer);
  }

  @Transactional
  @Override
  public void saveSurveyDocumentAnswer(UserQuestionResponse answer, Long userId) {
	String hql = "delete from UserSurveyDocument where docId<>:docId and userId=:userId and questionId=:questionId";
	Session session=  sessionFactory.getCurrentSession();
	session.createQuery(hql).setParameter("docId", answer.getDocId()).setParameter("userId", userId).setParameter("questionId", answer.getQuestionId()).executeUpdate();
	session.saveOrUpdate(answer);
  }

  @Override
  @Transactional
  public long uploadDocument(MultipartFile file, long questionId, long userId) throws IOException {
	UserSurveyDocument userServeyDoc = new UserSurveyDocument();
	userServeyDoc.setDoc(file.getBytes());
	userServeyDoc.setDocName(file.getOriginalFilename());
	userServeyDoc.setDocSize(file.getSize());
	userServeyDoc.setQuestionId(questionId);
	userServeyDoc.setUserId(userId);
	userServeyDoc.setLastModified(new Date());
	sessionFactory.getCurrentSession().saveOrUpdate(userServeyDoc);
	return userServeyDoc.getDocId();
  }
  
  
  public long uploadDocument_test(File file, String fileName, long questionId, long userId) throws IOException {
	UserSurveyDocument userServeyDoc = new UserSurveyDocument();
	FileInputStream fileInputStream = new FileInputStream(file);
	try {
	  byte[] buffer = new byte[(int) file.length()];
	  fileInputStream.read(buffer);
	  userServeyDoc.setDoc(buffer);
	} finally {
	  fileInputStream.close();
	}
	userServeyDoc.setDocName(fileName);
	userServeyDoc.setDocSize(file.getTotalSpace());
	userServeyDoc.setQuestionId(questionId);
	userServeyDoc.setUserId(userId);
	userServeyDoc.setLastModified(new Date());
	sessionFactory.getCurrentSession().saveOrUpdate(userServeyDoc);
	return userServeyDoc.getDocId();
  }

  @Override
  public void deleteSurveyAnswer(Long answerId) {
	sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(UserQuestionResponse.class, answerId));
  }

  @Override
  public void updateSurveyStatusToSaveLater(long surveyId, long userId) {
	String hql = "update UserSurvey set status=:status where pk.user.userId=:userId and pk.survey.surveyId=:surveyId";
	sessionFactory.getCurrentSession().createQuery(hql).setParameter("userId", userId).setParameter("surveyId", surveyId)
			.setParameter("status", DaoCoreConstant.userSurveyStatus.SAVE_LATER.name()).executeUpdate();
  }

  @Override
  public boolean isSurveyAssigned(Long surveyId, long userId) {
	String hql = "select count(*) from UserSurvey where pk.user.userId=:userId and pk.survey.surveyId=:surveyId and (status=:saveLaterStatus or status=:openStatus )";
	Long count = (Long) sessionFactory.getCurrentSession().createQuery(hql).setParameter("userId", userId)
			.setParameter("saveLaterStatus", DaoCoreConstant.userSurveyStatus.SAVE_LATER.name())
			.setParameter("openStatus", DaoCoreConstant.userSurveyStatus.OPEN.name()).setParameter("surveyId", surveyId).list()
			.get(0);
	if (count.floatValue() > 0) {
	  return true;
	} else {
	  return false;
	}
  }

  @Override
  public String userSurveyResponse(String responseId,PassportDescriptor passport) {
	String hql = "select status from UserSurvey where surveyResponseId=:surveyResponseId and pk.survey.passport=:passport";
	return (String)sessionFactory.getCurrentSession().createQuery(hql)
			.setParameter("surveyResponseId", responseId)
			.setParameter("passport", passport)
			.uniqueResult().toString();
  }
  

  @Override
  public Application getPassport(Long passportId, String applicationIdentifier) {
	String hql = "from PassportDescriptor where applicationIdentifier=:applicationIdentifier";
	return (Application)sessionFactory.getCurrentSession().createQuery(hql).setParameter("applicationIdentifier", applicationIdentifier).uniqueResult();
  }

  @Override
  public UserSurvey getUserSurvey(String surveyResponseId ,PassportDescriptor passport) {
	String hql = "from UserSurvey where surveyResponseId=:surveyResponseId and pk.survey.passport=:passport";
	return (UserSurvey)sessionFactory.getCurrentSession().createQuery(hql)
			.setParameter("surveyResponseId", surveyResponseId)
			.setParameter("passport", passport)
			.uniqueResult();
  }
  
  @Override
  public HashMap<String,Long> getSurveyCompletedCount(Long userId) {
	long completedSurveysCount=0,totalSurveysCount=0;
	Session session =sessionFactory.getCurrentSession();
	String totalSurveyHql= "select count(*) from UserSurvey where pk.user.userId=:userId";
	String completedSurveyHql= "select count(*) from UserSurvey where pk.user.userId=:userId and (status=:statusCompleted or  status=:statusSubmitted)";
	totalSurveysCount = (Long) session.createQuery(totalSurveyHql).setParameter("userId", userId).list().get(0);
	completedSurveysCount = (Long) session.createQuery(completedSurveyHql)
			.setParameter("userId", userId)
			.setParameter("statusCompleted", DaoCoreConstant.userSurveyStatus.COMPLETED.name())
			.setParameter("statusSubmitted", DaoCoreConstant.userSurveyStatus.SUBMITTED.name())
			.list().get(0);
	HashMap< String, Long> map = new HashMap<String, Long>();
	map.put("totalSurveysCount", totalSurveysCount);
	map.put("completedSurveysCount", completedSurveysCount);
	return map;
  }

  @Override
  public void addQuestionDocument(InputStream inputStream, String passportSurveyId, String passportQuestionId, Application passport) throws IOException {
	Session session =sessionFactory.getCurrentSession();
	String hql = "select questionDescriptor from QuestionDescriptor questionDescriptor inner join questionDescriptor.survey survey where survey.passport=:passport and survey.passportSurveyId=:passportSurveyId and questionDescriptor.passportQuestionId=:passportQuestionId";
	QuestionDescriptor question =(QuestionDescriptor) session.createQuery(hql)
			.setParameter("passport", passport)
			.setParameter("passportSurveyId", Long.valueOf(passportSurveyId))
			.setParameter("passportQuestionId", Long.valueOf(passportQuestionId))
			.list().get(0);
	if(question!=null){
	  QuestionDocumentDescriptor questionDocument =(QuestionDocumentDescriptor) session.createQuery("from QuestionDocumentDescriptor where questionDescriptor=:questionDescriptor").setParameter("questionDescriptor", question).uniqueResult();
	  if(questionDocument==null)
		questionDocument = new QuestionDocumentDescriptor();
	  questionDocument.setQuestionDescriptor(question);
	  questionDocument.setPolicyDocumentContent(IOUtils.toByteArray(inputStream));
	  question.setQuestionContainsDocument(Boolean.TRUE);
	  session.saveOrUpdate(question);
	  session.saveOrUpdate(questionDocument);
	}
  }

  @Override
  public void downloadQuestionHelpDocument(final Long questionID, final HttpServletResponse response) {
	Session session = sessionFactory.getCurrentSession();
	session.doWork(new Work() {
	  @Override
	  public void execute(Connection connection) throws SQLException {
		String sql= externalSqlQueries.selectQuestionDocument;
		PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
		stm.setFloat(1,questionID);
		ResultSet rset =stm.executeQuery();
		try{
			if(rset.next()){
				try {
				  response.setHeader( "Content-Disposition", "attachment;filename=\""
					      + rset.getString("fileName")+"\"" );
				  response.setHeader( "Content-Type", "application/octet-stream" );
				  IOUtils.copyLarge(rset.getAsciiStream("doc_data"), response.getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}finally{
		   rset.close();
		   stm.close();
		}
	  }
	});
  }

  @Override
  @Transactional(propagation=Propagation.REQUIRED)
  public QuestionDescriptor addQuestionQuestionDescriptor(QuestionDescriptor questionDesc) {
	sessionFactory.getCurrentSession().saveOrUpdate(questionDesc);
	return questionDesc;
  }
}