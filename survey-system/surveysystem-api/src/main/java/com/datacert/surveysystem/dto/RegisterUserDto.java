package com.datacert.surveysystem.dto;

import java.util.List;

import javax.validation.GroupSequence;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@GroupSequence( { EmptyChecks.class, RegisterUserDto.class } )
public class RegisterUserDto {

  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
  
  @NotBlank(groups=EmptyChecks.class)
  @Pattern(regexp=EMAIL_PATTERN)
  @Length(max = 255)
  public String userName;// message.validEmail
  
  @NotBlank(groups=EmptyChecks.class)
  public String questionOne;

  @NotBlank(groups=EmptyChecks.class)
  public String questionTwo;

  @NotBlank(groups=EmptyChecks.class)
  public String questionThree;

  @NotBlank(groups=EmptyChecks.class)
  @Length(max = 250)
  public String answerOne;

  @NotBlank(groups=EmptyChecks.class)
  @Length(max = 250)
  public String answerTwo;

  @NotBlank(groups=EmptyChecks.class)
  @Length(max = 250)
  public String answerThree;
  
  List<? extends com.datacert.surveysystem.dto.SecurityQuestion> securityQuestionsList;
  
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getQuestionOne() {
    return questionOne;
  }

  public void setQuestionOne(String questionOne) {
    this.questionOne = questionOne;
  }

  public String getQuestionTwo() {
    return questionTwo;
  }

  public void setQuestionTwo(String questionTwo) {
    this.questionTwo = questionTwo;
  }

  public String getQuestionThree() {
    return questionThree;
  }

  public void setQuestionThree(String questionThree) {
    this.questionThree = questionThree;
  }

  public String getAnswerOne() {
    return answerOne;
  }

  public void setAnswerOne(String answerOne) {
    this.answerOne = answerOne;
  }

  public String getAnswerTwo() {
    return answerTwo;
  }

  public void setAnswerTwo(String answerTwo) {
    this.answerTwo = answerTwo;
  }

  public String getAnswerThree() {
    return answerThree;
  }

  public void setAnswerThree(String answerThree) {
    this.answerThree = answerThree;
  }

  public List<? extends com.datacert.surveysystem.dto.SecurityQuestion> getSecurityQuestionsList() {
    return securityQuestionsList;
  }

  public void setSecurityQuestionsList(List<? extends com.datacert.surveysystem.dto.SecurityQuestion> securityQuestionsList) {
    this.securityQuestionsList = securityQuestionsList;
  }

}
interface EmptyChecks {}