package com.datacert.portal.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.datacert.surveysystem.db.domain.UserDescriptor;
import com.datacert.surveysystem.surveyresponse.SurveyAckResponse;


@Component
public class SurveyUtils {

	@Resource
	private JavaMailSender mailSender;

	@Resource
	private VelocityEngine velocityEngine;

	@Value(value = "${mail.smtp.host}")
	private String smtphost;

	@Value(value = "${mail.login.username}")
	private String smtpfrom;
	
	@Resource
	PropertyPlaceholderConfigurer propertyPlaceholderConfigurer; 
	
	private final String to="TO";
	private final String from="FROM";
	private final String subject="SUBJECT";
	private Pattern pattern;
	private Matcher matcher;
	private Properties props; 
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static final String PASSWORD_POLICY ="((?=.*\\d)(?=.*[A-Za-z])(?=.*[@#$!&%*]).{8,500})";
	
	public SurveyUtils() throws IOException {
		pattern = Pattern.compile(EMAIL_PATTERN);
		props = PropertiesLoaderUtils.loadAllProperties("surveyportal.properties");
	}
	
	private static Logger logger = LoggerFactory.getLogger(SurveyUtils.class);

	private MessageSourceAccessor messageSourceAccessor;
	
	@Resource
	public void setMessageSource(MessageSource messageSource) {
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}

	private void sendMailVM(final String templateName, final Map<String, Object> model,
			final Map<String, Object> datamodel) throws Exception {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo((String) model.get(to));
				message.setFrom((String) model.get(from));
				message.setSubject((String) model.get(subject));
				String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, datamodel);
				message.setText(body, true);
			}
		};
		mailSender.send(preparator);
	}

	@Async
	public void sendMail(String userEmail, String userId, String temppass,String siteRootUrl) {
		if(props.getProperty("mail.siteRootUrl")!=null)
			siteRootUrl=props.getProperty("mail.siteRootUrl");
		String link = siteRootUrl + "/signup/changePassword?id=" + userId + "&temppass=" + temppass;
		try {
			logger.debug(link);
			Map<String, Object> mailModel = new HashMap<String, Object>();
			mailModel.put(to, userEmail);
			mailModel.put(from, smtpfrom);
			mailModel.put(subject, messageSourceAccessor.getMessage("emailSubject.activationSubject"));
			Map<String, Object> dataModel = new HashMap<String, Object>();
			dataModel.put("email", userEmail);
			dataModel.put("link", link);
			sendMailVM("registration.vm", mailModel, dataModel);
			logger.debug("Mail Registration sent ");
		} catch (Exception e) {
			logger.error("Exception while sending mail", e);
		}
	}

	@Async
	public void sendMailforgetpassword(String userEmail, String userId, String password,String siteRootUrl) {
		if(props.getProperty("mail.siteRootUrl")!=null)
			siteRootUrl=props.getProperty("mail.siteRootUrl");
		String link = siteRootUrl + "/changepassword?id=" + userId+"&urlIdentifier="+password;
		try {
			Map<String, Object> mailModel = new HashMap<String, Object>();
			mailModel.put(to, userEmail);
			mailModel.put(from, smtpfrom);
			mailModel.put(subject, messageSourceAccessor.getMessage("emailSubject.changePasswordSubject"));
			Map<String, Object> dataModel = new HashMap<String, Object>();
			dataModel.put("email", userEmail);
			dataModel.put("link", link);
			logger.debug(link);
			sendMailVM("forgetpassword.vm", mailModel, dataModel);
		} catch (Exception e) {
			logger.error("Exception while sending forget password email", e);
		}
	}

	@Async
	public void sendSuccessSurvey(String userEmail, String adminEmail) {
		try {
			Map<String, Object> mailModel = new HashMap<String, Object>();
			mailModel.put(to, userEmail);
			mailModel.put(from, smtpfrom);
			mailModel.put(subject, messageSourceAccessor.getMessage("emailSubject.successSurvey"));
			Map<String, Object> dataModel = new HashMap<String, Object>();
			dataModel.put("adminEmail", adminEmail);
			sendMailVM("successSurvey.vm", mailModel, dataModel);
			logger.info("Survey Success Email Sent to " + userEmail);
		} catch (Exception e) {
			logger.error("Exception while sending survey Success email", e);
		}
	}

	public boolean checkPasswordPolicy(String password) {
		return Pattern.compile(PASSWORD_POLICY).matcher(password).matches();
	}

	public String getCurrrentDate() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
	}

	public static boolean contains(HashMap<Long, String> list, String obj) {
		return list.containsValue(obj);
	}

	public UserDescriptor getLoggedInUser() {
		return (UserDescriptor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public void sendErrorSurvey(ArrayList<SurveyAckResponse> errorList, String adminEmail) {
		try {
			Map<String, Object> mailModel = new HashMap<String, Object>();
			mailModel.put(to, adminEmail);
			mailModel.put(from, smtpfrom);
			mailModel.put(subject, messageSourceAccessor.getMessage("emailSubject.failureSurvey"));
			Map<String, Object> dataModel = new HashMap<String, Object>();
			dataModel.put("errorList", errorList);
			sendMailVM("failureSurvey.vm", mailModel, dataModel);
			logger.info("Survey failure Email Sent to " + adminEmail);
		} catch (Exception e) {
			logger.error("Exception while sending survey Success email", e);
		}
	}

	public boolean validUserName(String userName) {
		matcher = pattern.matcher(userName);
		return matcher.matches();
	}

	public boolean validContactPhone(String userName) {
		matcher = pattern.matcher(userName);
		return matcher.matches();
	}

}
