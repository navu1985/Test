package com.datacert.surveysystem.db.dao;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Filter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.datacert.surveysystem.db.domain.PassportDescriptor;
import com.datacert.surveysystem.db.domain.PolicyDescriptor;
import com.datacert.surveysystem.db.domain.PolicyDocumentDescriptor;
import com.datacert.surveysystem.db.domain.PolicyDocumentInfoDescriptor;
import com.datacert.surveysystem.db.domain.UserDescriptor;
import com.datacert.surveysystem.db.domain.UserPolicy;
import com.datacert.surveysystem.db.domain.UserProfileInfoDescriptor;
import com.datacert.surveysystem.db.util.SurveyPortalExternalSqlQueries;
import com.datacert.surveysystem.dto.DaoCoreConstant;
import com.datacert.surveysystem.dto.Policy;
import com.datacert.surveysystem.dto.SyncPolicyAudienceXml;
import com.datacert.surveysystem.dto.SyncPolicyAudiencesXml;
import com.datacert.surveysystem.dto.SyncPolicyRelationXml;
import com.datacert.surveysystem.dto.SyncPolicyRelationsXml;
import com.datacert.surveysystem.dto.User;
import com.datacert.surveysystem.policyresponse.PolicyAccessedResponseXml;
import com.datacert.surveysystem.policyresponse.PolicyAccessedResponsesXml;

@Repository("policyDao")
@Transactional
public class PolicyDaoImpl implements PolicyDao {

  @Resource
  private SessionFactory sessionFactory;

  @Resource
  private SurveyDao surveyDao;

  @Resource
  private UserDao userDao;
  
  @Resource
  @Qualifier("externalQueries")
  private SurveyPortalExternalSqlQueries  externalSqlQueries;

  private static Logger logger = LoggerFactory.getLogger(PolicyDaoImpl.class);

  @Override
  public PolicyDocumentDescriptor getPolicyDocument(Long policyDocId) {
	Session session = sessionFactory.getCurrentSession();
	return (PolicyDocumentDescriptor) session.get(PolicyDocumentDescriptor.class, policyDocId);
  }

  @Override
  public void addPolicyDocument(PolicyDocumentDescriptor policyDocument) {
	Session session = sessionFactory.getCurrentSession();
	session.saveOrUpdate(policyDocument);
	session.createQuery(
			"update PolicyDescriptor set policyStatus=:policyStatus where policyDocumentDescriptor.policyDocumentId=:docId")
			.setParameter("policyStatus", DaoCoreConstant.policyStatus.ACTIVE.name())
			.setParameter("docId", policyDocument.getPolicyDocumentId()).executeUpdate();
  }

  @Override
  public PolicyDescriptor getPolicy(Long policyId) {
	return (PolicyDescriptor) sessionFactory.getCurrentSession().get(PolicyDescriptor.class, policyId);
  }

  public PolicyDescriptor getPolicyfromApplicationPolicyId(Long applicationPolicyId, String applicationId) {
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PolicyDescriptor.class);
	criteria.setFetchMode("relatedPolicies", FetchMode.JOIN);
	criteria.add(Restrictions.eq("applicationPolicyId", applicationPolicyId));
	criteria.createAlias("passport", "application");
	criteria.add(Restrictions.eq("application.applicationIdentifier", applicationId));
	return (PolicyDescriptor) criteria.uniqueResult();
  }

  public UserPolicy isUserAssociatedPolicy(PolicyDescriptor policy, UserDescriptor user, Session session) {
	String hql = "from UserPolicy userpolicy " + "where userpolicy.pk.policy=:policy and userpolicy.pk.user=:user";
	@SuppressWarnings("unchecked")
	List<UserPolicy> list = (List<UserPolicy>) session.createQuery(hql).setParameter("user", user).setParameter("policy", policy)
			.list();
	if (list.size() == 0) {
	  return null;
	}
	return list.get(0);
  }

  public void updateUserPolicyStatus(PolicyDescriptor policy, UserDescriptor user, Session session, String status) {
	String hql = "update UserPolicy set status=:status " + "where pk.policy=:policy and pk.user=:user";
	session.createQuery(hql).setParameter("user", user).setParameter("status", status).setParameter("policy", policy)
			.executeUpdate();
  }

  @Override
  public PolicyDescriptor addPolicy(Policy policy, String applicationId) {
	PolicyDescriptor policyDescriptor = getPolicyfromApplicationPolicyId(policy.getApplicationPolicyId(), applicationId);
	if (policyDescriptor == null) {
	  policyDescriptor = new PolicyDescriptor();
	}
	policyDescriptor.setApplicationPolicyId(policy.getApplicationPolicyId());
	policyDescriptor.setPolicyEffectiveDate(policy.getPolicyEffectiveDate());
	policyDescriptor.setPolicyName(policy.getPolicyName());
	policyDescriptor.setPolicyTopic(policy.getPolicyTopic());
	policyDescriptor.setPolicyType(policy.getPolicyType());
	policyDescriptor.setPolicyStatus(DaoCoreConstant.policyStatus.DISABLE.name());
	policyDescriptor.setIssuingDepartment(policy.getIssuingDepartment());
	policyDescriptor.setPassport((PassportDescriptor) surveyDao.getPassport(null, applicationId));
	policyDescriptor.setNoOfDueDays(policy.getNoOfDueDays());
	policyDescriptor.setAttestationRequired(policy.getAttestationRequired());

	/*
	 * Add Audience Member to policy
	 */
	Set<UserPolicy> userPolicies = policyDescriptor.getUserPolicies();
	List<? extends User> users = policy.getAudienceMembers();
	for (User user : users) {
	  userPolicies.add(addPolicyUser(user, policyDescriptor));
	}
	policyDescriptor.getUserPolicies().addAll(userPolicies);

	/*
	 * Add Policy Document Information
	 */

	if (policy.getPolicyDocument() != null) {
	  PolicyDocumentInfoDescriptor policyDoc = policyDescriptor.getPolicyDocumentDescriptor();
	  if (policyDoc == null) {
		policyDoc = new PolicyDocumentInfoDescriptor();
	  }
	  policyDoc.setPolicyDocumentLastModified(new Date());
	  policyDoc.setPolicyDocumentName(policy.getPolicyDocument().getPolicyDocumentName());
	  policyDoc.setPolicyDocumentSize(policy.getPolicyDocument().getPolicyDocumentSize());
	  policyDoc.setPolicyDescriptor(policyDescriptor);
	  policyDescriptor.setPolicyDocumentDescriptor(policyDoc);
	}
	/*
	 * Realted Policies to Policies
	 */
	List<Policy> relatedpolicies = policy.getRelatedPolicies();
	for (Policy relatedPolicyIdObject : relatedpolicies) {
	  if (relatedPolicyIdObject.getApplicationPolicyId().equals(policy.getApplicationPolicyId())) {
		policyDescriptor.addRelatedPolicy(policyDescriptor);
	  } else {
		PolicyDescriptor relatedPolicy = getPolicyfromApplicationPolicyId(relatedPolicyIdObject.getApplicationPolicyId(),
				applicationId);
		if (relatedPolicy != null) {
		  policyDescriptor.addRelatedPolicy(relatedPolicy);
		  relatedPolicy.addRelatedPolicy(policyDescriptor);
		}
	  }
	}
	sessionFactory.getCurrentSession().saveOrUpdate(policyDescriptor);
	return policyDescriptor;
  }

  public UserPolicy addPolicyUser(User userObj, PolicyDescriptor policy) {
	UserPolicy userPolicy = null;
	String userName = userObj.getUsername();
	long policyUserID = userObj.getContactId();
	UserDescriptor user = userDao.getUser(userName, null, null);
	if (user == null) {
	  user = new UserDescriptor(userName, "");
	  userPolicy = new UserPolicy(user, policy, policyUserID, Boolean.FALSE);
	} else
	  userPolicy = new UserPolicy(user, policy, policyUserID, Boolean.FALSE);

	/*
	 * Add No of days to Current Days
	 */
	if (policy.getAttestationRequired()) {
	  Calendar c = Calendar.getInstance();
	  c.setTime(new Date());
	  c.add(Calendar.DATE, policy.getNoOfDueDays().intValue());
	  userPolicy.setPolicyDueDate(c.getTime());
	}
	userPolicy.setIsAcknowledge(!policy.getAttestationRequired());

	/*
	 * Add Profile Info from Policy Request
	 */
	UserProfileInfoDescriptor userProfileInfoDescriptor = user.getUserProfileInfoDescriptor();
	if (userProfileInfoDescriptor == null) {
	  userProfileInfoDescriptor = new UserProfileInfoDescriptor();
	}
	userProfileInfoDescriptor.setFirstName(userObj.getFirstName());
	userProfileInfoDescriptor.setLastName(userObj.getLastName());
	userProfileInfoDescriptor.setUser(user);

	sessionFactory.getCurrentSession().saveOrUpdate(userProfileInfoDescriptor);
	user.setUserProfileInfoDescriptor(userProfileInfoDescriptor);
	userDao.saveUser(user);
	return userPolicy;
  }

  @Override
  public SyncPolicyRelationsXml updatePolicyRelations(SyncPolicyRelationsXml policyRelations, String applicationId) {
	PolicyDescriptor policy = null, relatedPolicy = null;
	Session session = sessionFactory.getCurrentSession();
	List<SyncPolicyRelationXml> policyRelationsList = policyRelations.getPolicyRealtions();
	for (SyncPolicyRelationXml policyRelation : policyRelationsList) {

	  policy = getPolicyfromApplicationPolicyId(Long.valueOf(policyRelation.applicationPolicyId), applicationId);
	  relatedPolicy = getPolicyfromApplicationPolicyId(Long.valueOf(policyRelation.applicationRelatedPolicyId), applicationId);
	  if (policy != null && relatedPolicy != null) {
		try {
		  if (policyRelation.realtionStatus.equals(DaoCoreConstant.POLICY_RELATION_STATUS_ADDED)) {
			policy.addRelatedPolicy(relatedPolicy);
			relatedPolicy.addRelatedPolicy(policy);
		  } else if (policyRelation.realtionStatus.equals(DaoCoreConstant.POLICY_RELATION_STATUS_REMOVED)) {
			policy.removeRelatedPolicy(relatedPolicy);
			relatedPolicy.removeRelatedPolicy(policy);
		  }
		  session.saveOrUpdate(policy);
		  session.saveOrUpdate(relatedPolicy);
		  policyRelation.setRealtionStatus(DaoCoreConstant.POLICY_SYNC_SUCCESS_RESPONSE);
		} catch (Exception e) {
		  policyRelation.setRealtionStatus(DaoCoreConstant.POLICY_SYNC_ERROR_RESPONSE);
		}
	  } else {
		policyRelation.setRealtionStatus(DaoCoreConstant.POLICY_SYNC_ERROR_RESPONSE);
	  }
	}
	return policyRelations;
  }

  @Override
  public SyncPolicyAudiencesXml updatePolicyAudiences(SyncPolicyAudiencesXml policyAudiences, String applicationId) {
	UserPolicy userPolicy = null;
	Session session = sessionFactory.getCurrentSession();
	for (SyncPolicyAudienceXml syncPolicyAudienceXml : policyAudiences.getPolicyAudiences()) {
	  try {
		PolicyDescriptor policyDescriptor = getPolicyfromApplicationPolicyId(
				Long.valueOf(syncPolicyAudienceXml.applicationPolicyId), applicationId);
		UserDescriptor user = userDao.getUser(syncPolicyAudienceXml.getUserName(), null, null);
		user = updateUserProfile(user, syncPolicyAudienceXml);
		userPolicy = isUserAssociatedPolicy(policyDescriptor, user, session);
		if (syncPolicyAudienceXml.realtionStatus.equals(DaoCoreConstant.POLICY_AUDIENCE_ADDED_STATUS)) {
		  if (userPolicy == null && user != null && policyDescriptor != null) {
			userPolicy = new UserPolicy(user, policyDescriptor, Long.valueOf(syncPolicyAudienceXml.getAudienceId()), Boolean.TRUE);
			if (policyDescriptor.getAttestationRequired()) {
			  Calendar c = Calendar.getInstance();
			  c.setTime(new Date());
			  c.add(Calendar.DATE, policyDescriptor.getNoOfDueDays().intValue());
			  userPolicy.setPolicyDueDate(c.getTime());
			  userPolicy.setIsAcknowledge(!policyDescriptor.getAttestationRequired());
			  syncPolicyAudienceXml.setDueDate(userPolicy.getPolicyDueDate());
			}
			syncPolicyAudienceXml.setNewlyAddedAudience(Boolean.TRUE);
		  } else {
			userPolicy.setStatus(DaoCoreConstant.userPolicyStatus.ACTIVE.name());
		  }
		  session.saveOrUpdate(userPolicy);
		} else {
		  updateUserPolicyStatus(policyDescriptor, user, session, DaoCoreConstant.userPolicyStatus.DISABLE.name());
		}

		syncPolicyAudienceXml.setRealtionStatus(DaoCoreConstant.POLICY_SYNC_SUCCESS_RESPONSE);
	  } catch (Exception e) {
		syncPolicyAudienceXml.setRealtionStatus(DaoCoreConstant.POLICY_SYNC_ERROR_RESPONSE);
	  }
	}
	return policyAudiences;
  }

  private UserDescriptor updateUserProfile(UserDescriptor user, SyncPolicyAudienceXml syncPolicyAudienceXml) {
	if (user == null) {
	  user = new UserDescriptor(syncPolicyAudienceXml.getUserName(), "");
	}
	UserProfileInfoDescriptor userProfileInfoDescriptor = user.getUserProfileInfoDescriptor();
	if (userProfileInfoDescriptor == null) {
	  userProfileInfoDescriptor = new UserProfileInfoDescriptor();
	}
	userProfileInfoDescriptor.setFirstName(syncPolicyAudienceXml.getFirstName());
	userProfileInfoDescriptor.setLastName(syncPolicyAudienceXml.getLastName());
	userProfileInfoDescriptor.setUser(user);
	sessionFactory.getCurrentSession().saveOrUpdate(userProfileInfoDescriptor);
	user.setUserProfileInfoDescriptor(userProfileInfoDescriptor);
	userDao.saveUser(user);
	return user;
  }

  @Override
  public void retirePolicy(String policyId, String applicationId) {
	Session session = sessionFactory.getCurrentSession();
	PolicyDescriptor policy = getPolicyfromApplicationPolicyId(Long.valueOf(policyId), applicationId);
	if (policy != null) {
	  policy.setPolicyStatus(DaoCoreConstant.policyStatus.RETIRE.name());
	  session.save(policy);
	}
  }

  @Override
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<? extends Policy> getUnAckPolicies(Long userId) {
	Session session = sessionFactory.getCurrentSession();
	Filter filter = session.enableFilter("relatedPolicyStatusFilter");
	filter.setParameter("relatedPolicyStatus", DaoCoreConstant.policyStatus.ACTIVE.name());
	Criteria criteria = session.createCriteria(PolicyDescriptor.class);
	criteria.add(Restrictions.eq("policyStatus", DaoCoreConstant.policyStatus.ACTIVE.name()));
	criteria.setFetchMode("userPolicies", FetchMode.JOIN);
	criteria.createAlias("userPolicies", "userpolicy", org.hibernate.sql.JoinFragment.LEFT_OUTER_JOIN);
	criteria.add(Restrictions.eq("userpolicy.pk.user.userId", userId));
	criteria.add(Restrictions.eq("userpolicy.isAcknowledge", Boolean.FALSE));
	criteria.add(Restrictions.eq("userpolicy.status", DaoCoreConstant.userPolicyStatus.ACTIVE.name()));
	criteria.setFetchMode("relatedPolicies", FetchMode.JOIN);
	criteria.createAlias(
			"relatedPolicies.userPolicies",
			"relatedpolicyuser",
			org.hibernate.sql.JoinFragment.LEFT_OUTER_JOIN,
			Restrictions.and(Restrictions.eq("relatedpolicyuser.pk.user.userId", userId),
					Restrictions.eq("relatedpolicyuser.status", DaoCoreConstant.userPolicyStatus.ACTIVE.name())));
	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	return criteria.list();
  }

  
  @Override
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<? extends Policy> getAckPolicies(long userId) {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(PolicyDescriptor.class);
	Filter filter = session.enableFilter("relatedPolicyStatusFilter");
	filter.setParameter("relatedPolicyStatus", DaoCoreConstant.policyStatus.ACTIVE.name());
	criteria.add(Restrictions.eq("policyStatus", DaoCoreConstant.policyStatus.ACTIVE.name()));
	criteria.setFetchMode("userPolicies", FetchMode.JOIN);
	criteria.createAlias("userPolicies", "userpolicy"
			, org.hibernate.sql.JoinFragment.LEFT_OUTER_JOIN
			);
	criteria.add(Restrictions.eq("userpolicy.pk.user.userId", userId));
	criteria.add(Restrictions.eq("userpolicy.isAcknowledge", Boolean.TRUE));
	criteria.add(Restrictions.eq("userpolicy.status", DaoCoreConstant.userPolicyStatus.ACTIVE.name()));
	criteria.setFetchMode("relatedPolicies", FetchMode.JOIN);
	criteria.createAlias(
			"relatedPolicies.userPolicies",
			"relatedpolicyuser",
			org.hibernate.sql.JoinFragment.LEFT_OUTER_JOIN,
			Restrictions.and(Restrictions.eq("relatedpolicyuser.pk.user.userId", userId),
					Restrictions.eq("relatedpolicyuser.status", DaoCoreConstant.userPolicyStatus.ACTIVE.name())));
	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	return criteria.list();
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<PolicyDescriptor> getRelatedPolicies(long userId, long policyId) {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(PolicyDescriptor.class);
	criteria.add(Restrictions.eq("policyStatus", DaoCoreConstant.policyStatus.ACTIVE.name()));
	criteria.setFetchMode("userPolicies", FetchMode.JOIN);
	criteria.createAlias("userPolicies", "userpolicy", org.hibernate.sql.JoinFragment.LEFT_OUTER_JOIN);
	criteria.add(Restrictions.eq("userpolicy.pk.user.userId", userId));
	criteria.add(Restrictions.eq("userpolicy.status", DaoCoreConstant.userPolicyStatus.ACTIVE.name()));
	criteria.setFetchMode("relatedPolicies", FetchMode.DEFAULT);
	criteria.createAlias("relatedPolicies", "relatedpolicy");
	criteria.add(Restrictions.eq("relatedpolicy.policyId", policyId));
	return criteria.list();
  }

  @Transactional(readOnly = true)
  public Long getAckPoliciesCount(long userId) {
	Session session = sessionFactory.getCurrentSession();
	return (Long) session
			.createQuery(
					"select count(*) from PolicyDescriptor policy join policy.userPolicies userpolicy with userpolicy.status=:userPolicyStatus where policy.policyStatus=:policyStatus and userpolicy.pk.user.userId=:userId and userpolicy.isAcknowledge=:isAcknowledge")
			.setParameter("policyStatus", DaoCoreConstant.policyStatus.ACTIVE.name())
			.setParameter("userPolicyStatus", DaoCoreConstant.userPolicyStatus.ACTIVE.name())
			.setParameter("isAcknowledge", Boolean.TRUE).setParameter("userId", userId).list().get(0);
  }

  @Transactional(readOnly = true)
  public Long getUnAckPoliciesCount(long userId) {
	Session session = sessionFactory.getCurrentSession();
	return (Long) session
			.createQuery(
					"select count(*) from PolicyDescriptor policy join policy.userPolicies userpolicy with userpolicy.status=:userPolicyStatus where policy.policyStatus=:policyStatus and userpolicy.pk.user.userId=:userId and userpolicy.isAcknowledge=:isAcknowledge")
			.setParameter("policyStatus", DaoCoreConstant.policyStatus.ACTIVE.name())
			.setParameter("userPolicyStatus", DaoCoreConstant.userPolicyStatus.ACTIVE.name())
			.setParameter("isAcknowledge", Boolean.FALSE).setParameter("userId", userId).list().get(0);
  }

  @Override
  public void acknowledgepolicy(String policyId, long userId) {
	Session session = sessionFactory.getCurrentSession();
	session.createQuery(
			"update UserPolicy set isAcknowledge=:isAcknowledge, acknowledgeDate=:acknowledgeDate where pk.user.userId=:userId and pk.policy.policyId=:policyId")
			.setParameter("isAcknowledge", Boolean.TRUE).setParameter("acknowledgeDate", new Date())
			.setParameter("userId", userId).setParameter("policyId", Long.valueOf(policyId)).executeUpdate();
  }

  @Override
  public boolean hasPolicyDocumentAccess(String policyDocumentId, long userId) {
	Session session = sessionFactory.getCurrentSession();
	return session
			.createQuery(
					"select count(*) from PolicyDescriptor policy join policy.userPolicies userpolicy where policy.policyStatus=:policyStatus and userpolicy.pk.user.userId=:userId and policy.policyDocumentDescriptor.policyDocumentId=:policyDocumentId")
			.setParameter("policyStatus", DaoCoreConstant.policyStatus.ACTIVE.name()).setParameter("userId", userId)
			.setParameter("policyDocumentId", Long.valueOf(policyDocumentId)).list().get(0).equals(1l);
  }

  @Override
  public void policyAccessed(String policyId, long userId) {
	Session session = sessionFactory.getCurrentSession();
	Query query = session
			.createQuery("update UserPolicy userpolicy  set userpolicy.noOfTimesAccessed=userpolicy.noOfTimesAccessed+1, userpolicy.accessedCountSyncStatus=:accessedCountSyncStatus  where userpolicy.pk.policy.policyId=:policyId and userpolicy.pk.user.userId=:userId");
	query.setParameter("policyId", Long.valueOf(policyId));
	query.setParameter("userId", userId);
	query.setParameter("accessedCountSyncStatus", Boolean.FALSE);
	query.executeUpdate();
  }

  /*
   * Need to change for different passport
   */
  @SuppressWarnings("unchecked")
  @Override
  public PolicyAccessedResponsesXml policyAccessedByUsers() {
	Session session = sessionFactory.getCurrentSession();
	Query query = session
			.createQuery("Select pk.policy.applicationPolicyId as policyId, pk.user.username as username, audienceId as audienceId,noOfTimesAccessed as accessedCount, acknowledgeDate as acknowledgeDate ,accessedCountSyncStatus as syncStatus from UserPolicy where noOfTimesAccessed>:noOfTimesAccessed and accessedCountSyncStatus=:accessedCountSyncStatus");
	// Accessed more than once
	query.setParameter("noOfTimesAccessed", 0l);
	query.setParameter("accessedCountSyncStatus", Boolean.FALSE);
	PolicyAccessedResponsesXml policyAccessedResponsesXml = new PolicyAccessedResponsesXml();
	policyAccessedResponsesXml.setPolicyAccessedResponse(query.setResultTransformer(
			Transformers.aliasToBean(PolicyAccessedResponseXml.class)).list());
	return policyAccessedResponsesXml;
  }

  // @Override
  public List<Long> fullTextSearch(String searchData, final Long userId) {
	final String fulltextSearchString = searchData.replace("\"", "\"\"").replace("'", "''").replace("-", " ");
	Session session = sessionFactory.getCurrentSession();
	final List<Long> policyIdsList = new ArrayList<Long>();
	session.doWork(new Work() {
	  @Override
	  public void execute(Connection connection) throws SQLException {
		CallableStatement callableStatement = null;
		String callProcedure = "{call policy_fts_result(?,?,?,?)}";
		callableStatement = connection.prepareCall(callProcedure);
		callableStatement.setString(1, fulltextSearchString);
		callableStatement.setString(2, DaoCoreConstant.policyStatus.ACTIVE.name());
		callableStatement.setString(3, DaoCoreConstant.userPolicyStatus.ACTIVE.name());
		callableStatement.setString(4, String.valueOf(userId));
		ResultSet resultSet = callableStatement.executeQuery();
		while (resultSet.next()) {
		  policyIdsList.add(resultSet.getLong(1));
		}
	  }
	});
	return policyIdsList;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<? extends Policy> policyFullTextSearch(String policyName, String policyTopic, String policyApplicationId,
		  String policyType, String policyIssuedBy, String policyVersionDate, String policyRelatedDoc, String searchText,
		  Long userId) {

	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(PolicyDescriptor.class);
	criteria.add(Restrictions.eq("policyStatus", DaoCoreConstant.policyStatus.ACTIVE.name()));
	criteria.setFetchMode("userPolicies", FetchMode.JOIN);
	criteria.createAlias("userPolicies", "userpolicy");
	criteria.add(Restrictions.eq("userpolicy.pk.user.userId", userId));
	criteria.add(Restrictions.eq("userpolicy.isAcknowledge", Boolean.TRUE));
	criteria.add(Restrictions.eq("userpolicy.status", DaoCoreConstant.userPolicyStatus.ACTIVE.name()));
	criteria.setFetchMode("relatedPolicies", FetchMode.JOIN);
	criteria.createAlias(
			"relatedPolicies.userPolicies",
			"relatedpolicyuser",
			org.hibernate.sql.JoinFragment.LEFT_OUTER_JOIN,
			Restrictions.and(Restrictions.eq("relatedpolicyuser.pk.user.userId", userId),
					Restrictions.eq("relatedpolicyuser.status", DaoCoreConstant.userPolicyStatus.ACTIVE.name())));
	if (policyName != null && !policyName.isEmpty()) {
	  criteria.add(Restrictions.like("policyName", "%" + policyName + "%"));
	}

	if (policyTopic != null && !policyTopic.isEmpty()) {
	  criteria.add(Restrictions.like("policyTopic", "%" + policyTopic + "%"));
	}

	if (policyType != null && !policyType.isEmpty()) {
	  criteria.add(Restrictions.like("policyType", "%" + policyType + "%"));
	}

	if (policyIssuedBy != null && !policyIssuedBy.isEmpty()) {
	  criteria.add(Restrictions.like("issuingDepartment", "%" + policyIssuedBy + "%"));
	}

	if (policyVersionDate != null && !policyVersionDate.isEmpty()) {
	  criteria.add(Restrictions.sqlRestriction("CONVERT(VARCHAR, effective_from_date, 120) like '%" + policyVersionDate + "%'"));
	}

	if (policyApplicationId != null && !policyApplicationId.isEmpty()) {
	  criteria.add(Restrictions.sqlRestriction("{alias}.passport_policy_id LIKE '%" + policyApplicationId + "%'"));
	}

	List<Long> ids = fullTextSearch(searchText, userId);
	if (!searchText.isEmpty() && ids.size() > 0) {
	  criteria.add(Restrictions.in("policyId", ids));
	} else {
	  return new ArrayList<Policy>();
	}
	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	return criteria.list();
  }

  @Override
  public void getPolicyThumbnail(final Long policyId, final OutputStream out) {
	Session session = sessionFactory.getCurrentSession();
	session.doWork(new Work() {
	  @Override
	  public void execute(Connection connection) throws SQLException {
		String sql = externalSqlQueries.selectPolicyThumbnailDocument;
		PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
		stm.setFloat(1, policyId);
		ResultSet rset = stm.executeQuery();
		try {
		  if (rset.next()) {
			try {
			  IOUtils.copyLarge(rset.getAsciiStream("policy_thumbnail"), out);
			} catch (IOException e) {
			  e.printStackTrace();
			  logger.error(e.toString());
			}
		  }
		} finally {
		  rset.close();
		  stm.close();
		}

	  }
	});
  }

  @Override
  public void updatePolicyAcknowledgement(PolicyAccessedResponsesXml policyAccessedResponsesXmls) {
	String hql = "update UserPolicy set accessedCountSyncStatus=:accessedCountSyncStatus where audienceId=:audienceId";
	for (PolicyAccessedResponseXml policyAccessedResponseXml : policyAccessedResponsesXmls.getPolicyAccessedResponse()) {
	  sessionFactory.getCurrentSession().createQuery(hql)
			  .setParameter("accessedCountSyncStatus", policyAccessedResponseXml.getSyncStatus())
			  .setParameter("audienceId", policyAccessedResponseXml.getAudienceId()).executeUpdate();
	}
  }

  @Override
  public void getPolicyDocument(final Long policyDocumentId, final OutputStream out) {
	Session session = sessionFactory.getCurrentSession();
	session.doWork(new Work() {
	  public void execute(Connection connection) throws SQLException {
		String sql = externalSqlQueries.selectPolicyDocumentQuery;
		PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
		stm.setFloat(1, policyDocumentId);
		ResultSet rset = stm.executeQuery();
		try {
		  while (rset.next()) {
			try {
			  IOUtils.copyLarge(rset.getAsciiStream("doc_data"), out);
			} catch (IOException e) {
			  e.printStackTrace();
			  logger.error(e.toString());
			}
		  }
		} finally {
		  rset.close();
		  stm.close();
		}
	  }
	});

  }

  @Override
  public void updatePolicy(Policy policy, String applicationId) throws Exception {
	Session session = sessionFactory.getCurrentSession();
	PolicyDescriptor policyDesc = getPolicyfromApplicationPolicyId(policy.getApplicationPolicyId(), applicationId);
	if (policyDesc == null)
	  throw new Exception("Policy Not Found");
	policyDesc.setIssuingDepartment(policy.getIssuingDepartment());
	policyDesc.setPolicyName(policy.getPolicyName());
	policyDesc.setPolicyEffectiveDate(policy.getPolicyEffectiveDate());
	policyDesc.setPolicyTopic(policy.getPolicyTopic());
	policyDesc.setPolicyType(policy.getPolicyType());
	if (policy.getAttestationRequired() != null)
	  policyDesc.setAttestationRequired(policy.getAttestationRequired());
	if (policy.getNoOfDueDays() != null)
	  policyDesc.setNoOfDueDays(policy.getNoOfDueDays());
	session.saveOrUpdate(policyDesc);
  }

  @Override
  public Long getRetiredPoliciesCount(long userId) {
	Session session = sessionFactory.getCurrentSession();
	return (Long) session
			.createQuery(
					"select count(*) from PolicyDescriptor policy join policy.userPolicies userpolicy where policy.policyStatus=:policyStatus and userpolicy.pk.user.userId=:userId")
			.setParameter("policyStatus", DaoCoreConstant.policyStatus.RETIRE.name()).setParameter("userId", userId).list()
			.get(0);
  }
}
