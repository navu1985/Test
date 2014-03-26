package com.datacert.portal.custom.validation;

import java.util.Stack;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

public class PhoneConstraintValidator implements ConstraintValidator<Phone, String> {
  
  @Override
  public void initialize(Phone phone) { }

  @Override
  public boolean isValid(String phoneNumber, ConstraintValidatorContext cxt) {
	boolean inValid=false; 
	char validCharacters[]= {'0','1','2','3','4','5','6','7','8','9','(',')','-','.'};
	if(!(phoneNumber.equals(null)|| phoneNumber.isEmpty())){
//	  only contains numeric and .,-,(,)
	  if (!StringUtils.containsOnly(phoneNumber, validCharacters)){
		inValid=true;
		cxt.buildConstraintViolationWithTemplate("Only contains0-9");
	  }
//	  No Start with '-' and '.' and No end with '-' & '.'
	  else if (StringUtils.startsWithIgnoreCase(phoneNumber, "-") || StringUtils.startsWithIgnoreCase(phoneNumber, ".")
			  || StringUtils.endsWithIgnoreCase(phoneNumber, ".") || StringUtils.endsWithIgnoreCase(phoneNumber, "-")){
		inValid=true;
	  }
//	  Not contains [.-] [--] [..] [-.] 
	  else if(phoneNumber.indexOf(".-")==-1 || phoneNumber.indexOf("-.")==-1 || phoneNumber.indexOf("..")==-1 || phoneNumber.indexOf("--")==-1)
	  {
		inValid=true;
	  }
//	  () complete
	  else if(!isBracesComplete(phoneNumber)){
		inValid=true;
	  }
	}
	return !inValid;
  }
  
  private boolean isBracesComplete(String phoneNumber){
 	boolean unvalid=false;
 	int length= StringUtils.countMatches(phoneNumber, "(")+StringUtils.countMatches(phoneNumber, "("), traversingindex=-1 , openingBracesIndex=-1;
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
 		  System.out.println("traversingindex :"+traversingindex +"\t openingBracesIndex: "+openingBracesIndex+"\t indexStack:"+indexStack);
 		}else if(phoneNumber.indexOf(')',traversingindex+1)!=-1){
 		  traversingindex = phoneNumber.indexOf(')',traversingindex+1);
 		  if (indexStack.isEmpty()){
 			unvalid=true;
 		  }
 		  else{
 			indexStack.pop();
 		  }
 		 System.out.println("traversingindex :"+traversingindex +"\t indexStack:"+indexStack);
 		}
 	  }
 	} 
 	if (!indexStack.isEmpty()){
 		unvalid=true;
 	}
 	return !unvalid;
   }
}