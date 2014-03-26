package com.datacert.portal.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.datacert.portal.util.SurveyPortalConstants;
import com.datacert.portal.util.SurveyUtils;
import com.datacert.surveysystem.PolicyService;
import com.datacert.surveysystem.SurveyService;
import com.datacert.surveysystem.UserProfileService;
import com.datacert.surveysystem.dto.UserProfile;

@Controller
public class ProfileController {

	@Resource
	private SurveyUtils surveyUtils;
	
	@Resource
	PolicyService policyService;
	
	@Resource
	SurveyService surveyService;
	
	@Resource
	private UserProfileService userProfileService;
	
	@Resource
	ServletContext context;

	private String[] validImageExtensions = { ".jpg", ".png", ".gif" };

	private MessageSourceAccessor messageSourceAccessor;

	@Resource
	public void setMessageSource(MessageSource messageSource) {
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}

	@Value(value = "${surveyportal.maxFileUploadSizeForProfile}")
	private Long maxFileUploadSizeForProfile;

	@RequestMapping(value = "/profile", method = { RequestMethod.GET })
	public String userProfile(@RequestParam(required=false) String mode,Model model) {
		Long userId=surveyUtils.getLoggedInUser().getUserId();
		UserProfile userProfile =  userProfileService.getUserProfileInfo(userId);
		long unAckPoliciesCount=policyService.getUnAckPoliciesCount(userId);
		long ackPoliciesCount = policyService.getAckPoliciesCount(userId);
		HashMap<String, Long> map= surveyService.getSurveyCompletedCount(userId);
		model.addAttribute("totalSurveysCount",map.get("totalSurveysCount"));
		model.addAttribute("completedSurveysCount",map.get("completedSurveysCount"));
		model.addAttribute("unAckPoliciesCount",unAckPoliciesCount);
		model.addAttribute("retiredPoliciesCount",policyService.getRetiredPoliciesCount(userId));
		model.addAttribute("ackPoliciesCount",ackPoliciesCount);
		model.addAttribute("userProfile",userProfile);
		if(mode!=null && mode.equals("edit"))
		  model.addAttribute("editmode",Boolean.TRUE);
		return "user/profile";
	}
	
	
	@RequestMapping(value = "/loginedOnce", method = { RequestMethod.GET })
	public ResponseEntity<String> loginedOnce(Model model) {
		userProfileService.loginedOnce(surveyUtils.getLoggedInUser());
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/mainmenuprofile", method = { RequestMethod.GET })
	public String mainMenuProfile(Model model) {
		Long userId=surveyUtils.getLoggedInUser().getUserId();
		UserProfile userProfile =  userProfileService.getUserProfileInfo(userId);
		long unAckPoliciesCount=policyService.getUnAckPoliciesCount(userId);
		long ackPoliciesCount = policyService.getAckPoliciesCount(userId);
		model.addAttribute("userProfile",userProfile);
		model.addAttribute("unAckPoliciesCount",unAckPoliciesCount);
		model.addAttribute("retiredPoliciesCount",policyService.getRetiredPoliciesCount(userId));
		model.addAttribute("ackPoliciesCount",ackPoliciesCount);
		HashMap<String, Long> map= surveyService.getSurveyCompletedCount(userId);
		model.addAttribute("totalSurveysCount",map.get("totalSurveysCount"));
		model.addAttribute("completedSurveysCount",map.get("completedSurveysCount"));
		return "user/mainmenuprofile";
	}
	
	@RequestMapping(value = "/profile/{profileId}/image", method = { RequestMethod.GET })
	public void userProfileImage(@PathVariable String profileId, HttpServletResponse response) throws IOException {
	  ServletOutputStream out=response.getOutputStream();
	  Boolean isExists = userProfileService.getUserProfileDescriptor(Long.valueOf(profileId),out);
	  if(!isExists){
		FileInputStream fileInputStream =null;
		try{
			fileInputStream = new FileInputStream(new File(context.getRealPath("static/portalimages/unknown.gif")));
			IOUtils.copyLarge(fileInputStream,out);
		}finally{
			fileInputStream.close();
			out.flush();
		}
	  }
	}
	
	@RequestMapping(value = "/profile/edit/image", method = { RequestMethod.POST })
	public String changeProfileImage(Model model,MultipartHttpServletRequest request,RedirectAttributes redirectAttributes,@RequestParam Boolean isDevice) throws IOException {
		Long userId=surveyUtils.getLoggedInUser().getUserId();
		MultipartFile profileImage = request.getFile("profileImage");
		model.addAttribute("editmode",Boolean.TRUE);
		if(profileImage ==null){
		  	model.addAttribute("errorMessage","File not selected");
			UserProfile userProfile =  userProfileService.getUserProfileInfo(userId);
			long unAckPoliciesCount=policyService.getUnAckPoliciesCount(userId);
			long ackPoliciesCount = policyService.getAckPoliciesCount(userId);
			HashMap<String, Long> map= surveyService.getSurveyCompletedCount(userId);
			model.addAttribute("totalSurveysCount",map.get("totalSurveysCount"));
			model.addAttribute("retiredPoliciesCount",policyService.getRetiredPoliciesCount(userId));
			model.addAttribute("completedSurveysCount",map.get("completedSurveysCount"));
			model.addAttribute("unAckPoliciesCount",unAckPoliciesCount);
			model.addAttribute("ackPoliciesCount",ackPoliciesCount);
			model.addAttribute("userProfile",userProfile);
			return "user/profile";
		} else if (!(isImageExtValid(profileImage.getOriginalFilename()) && isImageSizeValid(profileImage.getSize()))) {
			model.addAttribute("errorMessage",messageSourceAccessor.getMessage("message.profileimage"));
			UserProfile userProfile =  userProfileService.getUserProfileInfo(userId);
			long unAckPoliciesCount=policyService.getUnAckPoliciesCount(userId);
			long ackPoliciesCount = policyService.getAckPoliciesCount(userId);
			HashMap<String, Long> map= surveyService.getSurveyCompletedCount(userId);
			model.addAttribute("totalSurveysCount",map.get("totalSurveysCount"));
			model.addAttribute("completedSurveysCount",map.get("completedSurveysCount"));
			model.addAttribute("retiredPoliciesCount",policyService.getRetiredPoliciesCount(userId));
			model.addAttribute("unAckPoliciesCount",unAckPoliciesCount);
			model.addAttribute("ackPoliciesCount",ackPoliciesCount);
			model.addAttribute("userProfile",userProfile);
			return "user/profile";
		} 
		File file = new File(userId+"_"+profileImage.getName());
		file=userProfileService.resizeImage(profileImage,"file_"+userId);
		model.addAttribute("profileImage",file.getName());
		
		if(isDevice){
		  redirectAttributes.addAttribute("fileName", file.getName());
		  redirectAttributes.addAttribute("selectAll", Boolean.TRUE);
		  return "redirect:../cropImage";
		}
		return "user/cropimage";
	}
	
	@RequestMapping(value = "/profile/{tempImageId}/tempimage", method = { RequestMethod.GET },produces="image/jpg" )
	@ResponseBody
	public byte[] getTempProfileImage(@PathVariable String tempImageId,HttpServletResponse response) throws IOException {
		File file = new File(SurveyPortalConstants.TEMP_DIR+"/"+tempImageId);
		byte[] bufferFile=null;
		FileInputStream fileInputStream =null;
		try{
			if(file.exists()){
				fileInputStream = new FileInputStream(file);
				bufferFile=IOUtils.toByteArray(fileInputStream);
			}
		}
		finally{
			fileInputStream.close();
		}
		return bufferFile;
	}
	
	@RequestMapping(value = "/profile/cropImage", method = { RequestMethod.POST,RequestMethod.GET })
	public String cropImage(@RequestParam String fileName,
			@RequestParam(required=false) String xStart,
			@RequestParam Boolean selectAll,
			@RequestParam(required=false) String yStart,
			@RequestParam(required=false) String xEnd,
			@RequestParam(required=false) String yEnd,Model model) throws Exception {
		Long userId=surveyUtils.getLoggedInUser().getUserId();
		File file = new File(SurveyPortalConstants.TEMP_DIR+"/"+fileName);
		if(!selectAll){
			file=userProfileService.cropImage(file,xStart,yStart,xEnd,yEnd);
		}
		userProfileService.saveProfileImage(file,userId);
		UserProfile userProfile =  userProfileService.getUserProfileInfo(userId);
		long unAckPoliciesCount=policyService.getUnAckPoliciesCount(userId);
		long ackPoliciesCount = policyService.getAckPoliciesCount(userId);
		HashMap<String, Long> map= surveyService.getSurveyCompletedCount(userId);
		model.addAttribute("totalSurveysCount",map.get("totalSurveysCount"));
		model.addAttribute("completedSurveysCount",map.get("completedSurveysCount"));
		model.addAttribute("unAckPoliciesCount",unAckPoliciesCount);
		model.addAttribute("retiredPoliciesCount",policyService.getRetiredPoliciesCount(userId));
		model.addAttribute("ackPoliciesCount",ackPoliciesCount);
		model.addAttribute("userProfile",userProfile);
		return "user/profile";
	}
	
	public boolean isImageExtValid(String fileName) {
		String fileExt = "";
		if (fileName.contains(".")) {
			fileExt = fileName.substring(fileName.lastIndexOf("."));
		}
		if (Arrays.asList(validImageExtensions).contains(fileExt.toLowerCase()))
			return true;
		else
			return false;
	}

	public boolean isImageSizeValid(Long fileSize) {
		if (fileSize <= maxFileUploadSizeForProfile)
			return true;
		else
			return false;
	}
}
