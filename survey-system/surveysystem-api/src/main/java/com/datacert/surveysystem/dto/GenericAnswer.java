package com.datacert.surveysystem.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenericAnswer {

  private String responseShort;
  private String responseLong;
  private String responseLookup;

  private Long responseDocId;
  private String responseDocName;

  private String responseMoney;
  private String responseMoneyStart;
  private String responseMoneyEnd;

  private String responseInteger;
  private String responseIntegerStart;
  private String responseIntegerEnd;

  private String responseDecimal;
  private String responseDecimalStart;
  private String responseDecimalEnd;

  private Date responseDate;
  private Date responseDateStart;
  private Date responseDateEnd;

  private long answerId;
  private long answerStartId;
  private long answerEndId;

  private Long questionId;
  private AnswerType answerType;

  public String getResponseShort() {
	return responseShort;
  }

  public void setResponseShort(String responseShort) {
	this.responseShort = responseShort;
  }

  public String getResponseLong() {
	return responseLong;
  }

  public void setResponseLong(String responseLong) {
	this.responseLong = responseLong;
  }

  public String getResponseMoney() {
	return responseMoney;
  }

  public void setResponseMoney(String responseMoney) {
	this.responseMoney = responseMoney;
  }

  public Date getResponseDate() {
	return responseDate;
  }

  public void setResponseDate(String responseDate) {
	
	if (responseDate.equals("")){
		this.responseDate = null;
		return;
	}
	DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	Date date = null;
	try { date = (Date) formatter.parse(responseDate);} catch (Exception e) {
		e.printStackTrace();
	}
	this.responseDate = date;
  }

  public void setResponseDate(Date responseDate) {
	this.responseDate = responseDate;
  }

  public Date getResponseDateStart() {
	return responseDateStart;
  }

  public void setResponseDateStart(String responseDateStart) {
	if (responseDateStart.equals("")){
		this.responseDateStart = null;
		return;
	}
	DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	Date date = null;
	try {
	  date = (Date) formatter.parse(responseDateStart);
	} catch (Exception e) { e.printStackTrace(); 
		
	}
	this.responseDateStart = date;
  }

  public void setResponseDateStart(Date responseDateStart) {
	this.responseDateStart = responseDateStart;
  }

  public Date getResponseDateEnd() {
	return responseDateEnd;
  }

  public void setResponseDateEnd(String responseDateEnd) {
	if (responseDateEnd.equals("")){
		this.responseDateEnd = null;
		return;
	}
	DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	Date date = null;
	try {
	  date = (Date) formatter.parse(responseDateEnd);
	} catch (Exception e) {
		e.printStackTrace();
	}
	this.responseDateEnd = date;
  }

  public void setResponseDateEnd(Date responseDateEnd) {
	this.responseDateEnd = responseDateEnd;
  }

  public Long getQuestionId() {
	return questionId;
  }

  public void setQuestionId(Long questionId) {
	this.questionId = questionId;
  }

  public AnswerType getAnswerType() {
	return answerType;
  }

  public void setAnswerType(AnswerType answerType) {
	this.answerType = answerType;
  }

  public String getResponseMoneyStart() {
	return responseMoneyStart;
  }

  public void setResponseMoneyStart(String responseMoneyStart) {
	this.responseMoneyStart = responseMoneyStart;
  }

  public String getResponseMoneyEnd() {
	return responseMoneyEnd;
  }

  public void setResponseMoneyEnd(String responseMoneyEnd) {
	this.responseMoneyEnd = responseMoneyEnd;
  }

  public Long getResponseDocId() {
	return responseDocId;
  }

  public void setResponseDocId(Long responseDocId) {
	this.responseDocId = responseDocId;
  }

  public String getResponseDocName() {
	return responseDocName;
  }

  public void setResponseDocName(String responseDocName) {
	this.responseDocName = responseDocName;
  }

  public long getAnswerId() {
	return answerId;
  }

  public void setAnswerId(long answerId) {
	this.answerId = answerId;
  }

  public long getAnswerStartId() {
	return answerStartId;
  }

  public void setAnswerStartId(long answerStartId) {
	this.answerStartId = answerStartId;
  }

  public long getAnswerEndId() {
	return answerEndId;
  }

  public void setAnswerEndId(long answerEndId) {
	this.answerEndId = answerEndId;
  }

  public String getResponseLookup() {
	return responseLookup;
  }

  public void setResponseLookup(String responseLookup) {
	this.responseLookup = responseLookup;
  }

  public String getResponseDecimal() {
	return responseDecimal;
  }

  public void setResponseDecimal(String responseDecimal) {
	this.responseDecimal = responseDecimal;
  }

  public String getResponseDecimalStart() {
	return responseDecimalStart;
  }

  public void setResponseDecimalStart(String responseDecimalStart) {
	this.responseDecimalStart = responseDecimalStart;
  }

  public String getResponseDecimalEnd() {
	return responseDecimalEnd;
  }

  public void setResponseDecimalEnd(String responseDecimalEnd) {
	this.responseDecimalEnd = responseDecimalEnd;
  }

  public String getResponseInteger() {
    return responseInteger;
  }

  public void setResponseInteger(String responseInteger) {
    this.responseInteger = responseInteger;
  }

  public String getResponseIntegerStart() {
    return responseIntegerStart;
  }

  public void setResponseIntegerStart(String responseIntegerStart) {
    this.responseIntegerStart = responseIntegerStart;
  }

  public String getResponseIntegerEnd() {
    return responseIntegerEnd;
  }

  public void setResponseIntegerEnd(String responseIntegerEnd) {
    this.responseIntegerEnd = responseIntegerEnd;
  }

}
