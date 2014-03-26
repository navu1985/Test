package com.datacert.portal.custom.validation;

import java.util.Stack;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.datacert.portal.dto.ApplicationDto;


@Component
public class ApplicationDtoValidator {

  private final String onlyDigitsAndSpecialCharacters="message.validator.incorrect.format.onlyDigitsAndSpecialCharacters";
  private final String mustContainDigit="message.validator.incorrect.format.mustContainDigit";
  private final String noStartWithSpecialCharacter="message.validator.incorrect.format.noStartWithSpecialCharacter";
  private final String notAllowedTogether="message.validator.incorrect.format.notAllowedTogether";
  private final String inCompleteBraces="message.validator.incorrect.format.incompleteBraces";
  private final String objectName="applicationDto";
  private final String fieldName="contactPhone";
  private final char validCharacters[]= {'0','1','2','3','4','5','6','7','8','9','(',')','-','.'};
  
  
  private MessageSourceAccessor messageSourceAccessor;
  @Resource
  public void setMessageSource(MessageSource messageSource) {
	this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
  }
  
  public BindingResult validatePhoneContact(ApplicationDto applicationDto, BindingResult result) {
	String phoneNumber= applicationDto.getContactPhone();
	
	if(!(phoneNumber.equals(null)|| phoneNumber.isEmpty())){
//	  only contains numeric and .,-,(,)
	  if (!StringUtils.containsOnly(phoneNumber, validCharacters) && phoneNumber.matches(".*\\d.*")){
		FieldError error= new FieldError(objectName,fieldName,messageSourceAccessor.getMessage(onlyDigitsAndSpecialCharacters));
		result.addError(error);
		return result;
	  }
//	  Must contains numeric 
	  if (!phoneNumber.matches(".*\\d.*")){
		FieldError error= new FieldError(objectName,fieldName,messageSourceAccessor.getMessage(mustContainDigit));
		result.addError(error);
		return result;
	  }
	  
//	  No Start with '-' and '.' and No end with '-' & '.'
	  else if (StringUtils.startsWithIgnoreCase(phoneNumber, "-") || StringUtils.startsWithIgnoreCase(phoneNumber, ".")
			  || StringUtils.endsWithIgnoreCase(phoneNumber, ".") || StringUtils.endsWithIgnoreCase(phoneNumber, "-")){
		FieldError error= new FieldError(objectName,fieldName,messageSourceAccessor.getMessage(noStartWithSpecialCharacter));
		result.addError(error);
		return result;
	  }
//	  Not contains [.-] [--] [..] [-.] 
	  else if(phoneNumber.indexOf(".-")!=-1 || phoneNumber.indexOf("-.")!=-1 || phoneNumber.indexOf("..")!=-1 || phoneNumber.indexOf("--")!=-1)
	  {
		FieldError error= new FieldError(objectName,fieldName,messageSourceAccessor.getMessage(notAllowedTogether));
		result.addError(error);
		return result;
	  }
//	  () complete
	  else if(!isBracesComplete(phoneNumber)){
		FieldError error= new FieldError(objectName,fieldName,messageSourceAccessor.getMessage(inCompleteBraces));
		result.addError(error);
		return result;
	  }
	}
	return result;
  }
  
  private boolean isBracesComplete(String phoneNumber){
 	boolean unvalid=false;
 	int length= StringUtils.countMatches(phoneNumber, "(")+StringUtils.countMatches(phoneNumber, ")"), traversingindex=-1 , openingBracesIndex=-1;
 	Stack<Integer> indexStack = new Stack<Integer>();
 	boolean containsOpeningBraces= phoneNumber.contains("("),containsClosingBraces= phoneNumber.contains(")");
 	if(containsOpeningBraces && !containsClosingBraces){
 	  unvalid=true;
 	}else if(!containsOpeningBraces && containsClosingBraces){
 	  unvalid=true;
 	}else if(containsOpeningBraces && containsClosingBraces){
 	  for (int i = 0; i < length; i++) {
 		if(phoneNumber.indexOf('(',traversingindex+1)<phoneNumber.indexOf(')',traversingindex+1) && phoneNumber.indexOf('(',traversingindex+1)!=-1){
 		  traversingindex =openingBracesIndex =phoneNumber.indexOf('(',traversingindex+1);
 		  indexStack.push(Integer.valueOf(openingBracesIndex));
 		}else if(phoneNumber.indexOf(')',traversingindex+1)!=-1){
 		  traversingindex = phoneNumber.indexOf(')',traversingindex+1);
 		  if (indexStack.isEmpty()){
 			unvalid=true;
 		  }
 		  else{
 			indexStack.pop();
 		  }
 		}
 	  }
 	} 
 	if (!indexStack.isEmpty()){
 		unvalid=true;
 	}
 	return !unvalid;
   }
}
