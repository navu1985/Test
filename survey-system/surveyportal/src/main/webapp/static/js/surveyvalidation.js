//surveyvalidation.js
iePatch();
function navigate(varCurrentPage,totalPages){
	$('#next').show();
	$('#back').show();
	$('#surveySubmit').hide();
	if(varCurrentPage==totalPages){
		$('#next').hide();
		$('#back').show();
		$('#surveySubmit').show();
	}
	if(varCurrentPage==1){
		$('#next').show();
		$('#back').hide();
	}
	if(1==totalPages){
		$('#next').hide();
		$('#back').hide();
	}
}
	
function iePatch(){
	if ($.browser.msie)	
		$('select')
		.bind('mousedown', function() {$(this).removeClass('selectdefault').addClass('selectAutoExpand');})
		.bind('focus', function() {$(this).removeClass('selectdefault').addClass('selectAutoExpand');})
		.bind('focusout', function() {$(this).removeClass('selectAutoExpand').addClass('selectdefault');})
		.bind('change', function() {$(this).removeClass('selectAutoExpand').addClass('selectdefault');});
}


function setErrorMsgRange(element,message){
	$("span[alt="+element.attr('alt')+"]").remove();
	element.append("<span alt='"+element.attr('alt')+"' class='alert-error  validate-error'>"+message+"</span>");
}
function setErrorMsg(element,message){
	$("span[alt="+element.attr('alt')+"]").remove();
	element.after("&nbsp;<span alt='"+element.attr('alt')+"' class='validate-error alert-error'>"+message+"</span>");
}


function validSubmitSurveyAnswer($, messageBundle,status) {
	var requiredAnswer=messageBundle["requiredAnswer"];
	var notMoreThan2000char=messageBundle["notMoreThan2000char"];
	var notMoreThan255char=messageBundle["notMoreThan255char"];
	var selectAnswer=messageBundle["selectAnswer"];
	var provideRemoveValue=messageBundle["provideRemoveValue"];
	var invalidAmount=messageBundle["invalidAmount"];
	var toGreaterThanFrom=messageBundle["toGreaterThanFrom"];
	var invalidInteger=messageBundle["invalidInteger"];
	var invalidDecimal=messageBundle["invalidDecimal"];

	var resultHandller=true;
	$('.alert-error').remove();

	var shortText=$('[data-validate=SHORT_TEXT]');
	$.each(shortText, function(index) {
		
		
		var len=$.trim($(this).val()).length;
		
		var isRequired=$(this).attr('required');

		if($(this).val().length>0 && len===0){
			setErrorMsg($(this),"Spaces are not allowed");
			resultHandller=false;
		}
		
		if(len >255){
			setErrorMsg($(this),notMoreThan255char);
			resultHandller=false;
		}
		if(status==="submit"){
			if(len===0 && isRequired !== undefined){
				setErrorMsg($(this),requiredAnswer);
				resultHandller=false;
			}
		}
		
	});
	
	var lookupList=$('.'+'LOOKUP_LIST');
	$.each(lookupList, function(index) {
		var isRequired=$(this).attr('required');
		if(status=="submit"){
			if($(this).val().length==0 && isRequired != undefined)
			{
				setErrorMsg($(this),selectAnswer);
				resultHandller=false;
			}
		}
		
	});
	
	var longText=$('[data-validate=LONG_TEXT]');
	$.each(longText, function(index) {
		
		var len=$.trim($(this).val()).length;
		var isRequired=$(this).attr('required');
		
		if($(this).val().length>0 && len==0){
			setErrorMsg($(this),"Spaces are not allowed");
			resultHandller=false;
		}
		
		if(len >2000){
			setErrorMsg($(this),notMoreThan2000char);
			resultHandller=false;
		}
		if(status=="submit" ){
			if(len==0 && isRequired != undefined){
				setErrorMsg($(this),requiredAnswer);
				resultHandller=false;
			}
		}
	});
	
	
	var decimal=$('[data-validate=DECIMAL]');
	$.each(decimal, function(index) {
		var len=$(this).val().length;
		var isRequired=$(this).attr('required');
		if((!(/^[-+]?\d*\.?\d+$/.test($(this).val()))) && len>0){ 
			resultHandller=false;
			setErrorMsg($(this),invalidDecimal);
		}else if(status=="submit" ){
			if(len==0 && isRequired != undefined){
				setErrorMsg($(this),requiredAnswer);
				resultHandller=false;
			}
		}
	});
	
	var decimalRange=$('.'+'DECIMAL_RANGE');
	$.each(decimalRange, function(index) {
		var start=$(this).find(".Start");
		var end=$(this).find(".End");
		var isRequired=$(start).attr('required');
		if(!(/^[-+]?\d*\.?\d+$/.test(end.val())) &&  end.val().length >0){ 
			resultHandller=false;
			setErrorMsgRange($(this),invalidDecimal);
		}else if(!(/^[-+]?\d*\.?\d+$/.test(start.val())) &&  start.val().length >0){ 
				resultHandller=false;
				setErrorMsgRange($(this),invalidDecimal);
		}else if(status=="submit"){
			if((start.val().length ==0 || end.val().length ==0) && isRequired != undefined){
				setErrorMsgRange($(this),requiredAnswer);
				resultHandller=false;
			}else if(((start.val().length ==0 && end.val().length>0) || (start.val().length >0 && end.val().length==0)) && isRequired == undefined){
				setErrorMsgRange($(this),provideRemoveValue);
				resultHandller=false;
			}else if(parseFloat(end.val()) < parseFloat(start.val())){
				setErrorMsgRange($(this),toGreaterThanFrom);
				resultHandller=false;
			}
		}
	});
	
	var number=$('[data-validate=INTEGER]');
	$.each(number, function(index) {
		var len=$(this).val().length;
		var isRequired=$(this).attr('required');
		if((!(/^-?\d+$/.test($(this).val()))) && len>0){ 
			resultHandller=false;
			setErrorMsg($(this),invalidInteger);
		}else if(status=="submit" ){
			if(len==0 && isRequired != undefined){
				setErrorMsg($(this),requiredAnswer);
				resultHandller=false;
			}
		}
	});
	
	var integerRange=$('.'+'INTEGER_RANGE');
	$.each(integerRange, function(index) {
		var start=$(this).find(".Start");
		var end=$(this).find(".End");
		var isRequired=$(start).attr('required');
		if(!(/^-?\d+$/.test(end.val())) &&  end.val().length >0){ 
			resultHandller=false;
			setErrorMsgRange($(this),invalidInteger);
		}else if(!(/^-?\d+$/.test(start.val())) &&  start.val().length >0){ 
				resultHandller=false;
				setErrorMsgRange($(this),invalidInteger);
		}else if(status=="submit"){
			if((start.val().length ==0 || end.val().length ==0) && isRequired != undefined){
				setErrorMsgRange($(this),requiredAnswer);
				resultHandller=false;
			}else if(((start.val().length ==0 && end.val().length>0) || (start.val().length >0 && end.val().length==0)) && isRequired == undefined){
				setErrorMsgRange($(this),provideRemoveValue);
				resultHandller=false;
			}else if(parseInt($(start).val())> parseInt($(end).val())){
				setErrorMsgRange($(this),toGreaterThanFrom);
				resultHandller=false;
			}
		}
	});
	
	
	var money=$('[data-validate=MONEY]');
	$.each(money, function(index) {
		var len=$(this).val().length;
		var isRequired=$(this).attr('required');
		if((!(/^[+]?\d*\.?\d+$/.test($(this).val()))) && len>0){ 
			resultHandller=false;
			setErrorMsg($(this),invalidAmount);
		}else if(status=="submit" ){
			if(len==0 && isRequired != undefined){
				setErrorMsg($(this),requiredAnswer);
				resultHandller=false;
			}
		}
	});
	
	var moneyRange=$('.'+'MONEY_RANGE');
	$.each(moneyRange, function(index) {
		var start=$(this).find(".Start");
		var end=$(this).find(".End");
		var isRequired=$(start).attr('required');
		if(!(/^[+]?\d*\.?\d+$/.test(end.val())) &&  end.val().length >0){ 
			resultHandller=false;
			setErrorMsgRange($(this),invalidAmount);
		}else if(!(/^[+]?\d*\.?\d+$/.test(start.val())) &&  start.val().length >0){ 
				resultHandller=false;
				setErrorMsgRange($(this),invalidAmount);
		}else if(status=="submit"){
			if((start.val().length ==0 || end.val().length ==0) && isRequired != undefined){
				setErrorMsgRange($(this),requiredAnswer);
				resultHandller=false;
			}else if(((start.val().length ==0 && end.val().length>0) || (start.val().length >0 && end.val().length==0)) && isRequired == undefined){
				setErrorMsgRange($(this),provideRemoveValue);
				resultHandller=false;
			}else if(parseFloat(end.val()) < parseFloat(start.val())){
				setErrorMsgRange($(this),toGreaterThanFrom);
				resultHandller=false;
			}
		}
	});
	
	
	var dateValid=$("input[dateClass='DATE']");
	$.each(dateValid, function(index) {
		var len=$(this).val().length;
		var isRequired=$(this).attr('required');
		if(status=="submit" ){
			if(len==0 && isRequired != undefined){
				setErrorMsg($(this),requiredAnswer);
				resultHandller=false;
			}
		}
	});
	
	
	var dateRange=$('.'+'DATE_RANGE');
	$.each(dateRange, function(index) {
		var start=$(this).find("input[question-range='Start']");
		var end=$(this).find("input[question-range='End']");
		var isRequired=$(start).attr('required');
		if(status=="submit"){
			if((start.val().length ==0 || end.val().length ==0) && isRequired != undefined){
				setErrorMsgRange($(this),requiredAnswer);
				resultHandller=false;
			}else if(((start.val().length ==0 && end.val().length>0) || (start.val().length >0 && end.val().length==0)) && isRequired == undefined){
				setErrorMsgRange($(this),provideRemoveValue);
				resultHandller=false;
			}else if(end.val() < start.val()){
				setErrorMsgRange($(this),toGreaterThanFrom);
				resultHandller=false;
			}
		}
	});

	
	//DOCUMENT_UPLOAD
	var document=$('.'+'DOCUMENT_UPLOAD');
	$.each(document, function(index) {
		var isRequired=$(this).attr('required');
		if(status=="submit"){
				if($(this).val().length==0 && isRequired != undefined){
					setErrorMsg($(this),requiredAnswer);
					resultHandller=false;
				}
		}
	});
	return resultHandller;
}

function adminUpdatesettings($, messageBundle) {
	var requiredbatchInterval = messageBundle["requiredbatchInterval"];
	var requiredpoolSize = messageBundle["requiredpoolSize"];
	var requiredretryAttempts= messageBundle["requiredretryAttempts"];
	var requiredretryIntervalInMin= messageBundle["requiredretryIntervalInMin"];
	var shouldNumeric = messageBundle["shouldNumeric"]; 
	
	 $("#editSetting").validate({
		rules : {
			username:{
				required: true
			},
			pageName:{
				required: true
			},
			password:{
				required: true
			},
			url:{
				required: true,
				url: true
			},
			batchIntervalInMin : {
				required: true,
				number: true
			},
			poolSize:{
				required: true,
				number: true
			},
			retryAttempts:{
				required: true,
				number: true
			},
			retryIntervalInMin:{
				required: true,
				number: true
			}
		},
		messages : {
			batchIntervalInMin : {
				required : requiredbatchInterval,
				number :  shouldNumeric
			},
			poolSize : {
				required : requiredpoolSize,
				number :  shouldNumeric
			},
			retryAttempts : {
				required : requiredretryAttempts,
				number :  shouldNumeric
			},
			retryIntervalInMin : {
				required : requiredretryIntervalInMin,
				number :  shouldNumeric
			}
		},
		errorClass : "error",
		errorElement : "span",
		onkeyup : false,
		highlight : function(element, errorClass, validClass) {
			$(element).parents('.control-group').addClass('error');
		},
		unhighlight : function(element, errorClass, validClass) {
			$(element).parents('.control-group').removeClass('error');
		}
	});
}


function changeExistingPassword($, messageBundle) {
	var requiredOldPassword = messageBundle["requiredOldPassword"];
	var requiredNewPassword = messageBundle["requiredNewPassword"];
	var requiredConfirmPassword = messageBundle["requiredConfirmPassword"];
	
	 $("#changeexistingpassword").validate({
		rules : {
			oldpassword : {
				required: true
			},
			password:{
				required: true
			},
			confirmpassword:{
				required: true
			}
		},
		messages : {
			oldpassword : {
				required : requiredOldPassword
			},
			password : {
				required : requiredNewPassword
			},
			confirmpassword : {
				required : requiredConfirmPassword
			}
		},
		errorClass : "error",
		errorElement : "span",
		onkeyup : false,
		highlight : function(element, errorClass, validClass) {
			$(element).parents('.control-group').addClass('error');
		},
		unhighlight : function(element, errorClass, validClass) {
			$(element).parents('.control-group').removeClass('error');
		}
	});
}


function changePasswordnew() {
	$("#changepasswordnew").validate({
		rules : {
			password : {
				required: true,
				regex:true
			},
			confirmpassword : {
				required : true,
				equalTo : "#password"
			}
		},
		messages : {
			password : {
				required : "Password can't be Blank",
				regex:"Password should be minimum 8 characters long, with 1 letter, 1 numeric and 1 special character (@#$!&%*)"
			},
			confirmpassword : {
				required : "Confirm Password can't be Blank",
				equalTo  : "Password and Confirm Password must match"
			}
		},
		errorClass : "error",
		errorElement : "span",
		errorLabelContainer: "#errorMessage",
		onkeyup : false,
		highlight : function(element, errorClass, validClass) {
			$(element).parents('.control-group').addClass('error');
		},
		unhighlight : function(element, errorClass, validClass) {
			$(element).parents('.control-group').removeClass('error');
		}
	});
	
	$.validator.addMethod("regex", function(value, element) {
		var validLength = /.{8}/.test(value);
		var hasNums = /\d/.test(value);
		var hasSpecials = /[@#$!&%*]/.test(value);
		var hasalpha = /[A-z]/.test(value);
		return validLength && hasNums && hasSpecials && hasalpha;
	}, "");
}

function forgetPassword($, messageBundle) {
	var requiredEmail = messageBundle["requiredEmail"];
	var validEmail = messageBundle["validEmail"];

	$("#forgetPasswordForm").validate({
		rules : {
			username : {
				required : true,
				email : true
			}
		},
		messages : {
			username : {
				required : requiredEmail,
				email : validEmail
			}
		},
		errorClass : "error",
		errorElement : "span",
		onkeyup : false,
		highlight : function(element, errorClass, validClass) {
			$(element).parents('.control-group').addClass('error');
		},
		unhighlight : function(element, errorClass, validClass) {
			$(element).parents('.control-group').removeClass('error');
		}
	});
}

function newPassword($, messageBundle) {
	var requiredPassword = messageBundle["requiredPassword"];

	$("#updatePassword").validate({
		rules : {
			password : "required",
			confirmpassword : "required"
		},
		messages : {
			password : requiredPassword,
			confirmpassword:requiredPassword
		},
		errorClass : "error",
		errorElement : "span",
		onkeyup : false,
		highlight : function(element, errorClass, validClass) {
			$(element).parents('.control-group').addClass('error');
		},
		unhighlight : function(element, errorClass, validClass) {
			$(element).parents('.control-group').removeClass('error');
		}
	});
}

function forgetSecurityQuestions($, messageBundle) {

	var requiredAnswer = messageBundle["requiredAnswer"];

	$('#forgetsecurityquestion').validate({
		rules : {
			answer1 : {
				required : true
			},
			answer2 : {
				required : true
			},
			answer3 : {
				required : true
			}
		},
		messages : {
			answer1 : {
				required : requiredAnswer
			},
			answer2 : {
				required : requiredAnswer
			},
			answer3 : {
				required : requiredAnswer
			}
		},
		errorClass : "error",
		errorElement : "span",
		onkeyup : false,
		highlight : function(element, errorClass, validClass) {
			$(element).parents('.control-group').addClass('error');
		},
		unhighlight : function(element, errorClass, validClass) {
			$(element).parents('.control-group').removeClass('error');
		}
	});
}
