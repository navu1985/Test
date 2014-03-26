var _messageBundle;
var surveyName;
$("form").submit(function() { return false; });
function questionsJs($,messageBundle,varCurrentPage,varPageSize,totalQuestion) {
	 _messageBundle=messageBundle;
	 iePatch();
	 surveyName=">"+$('#surveyNameQuestion').html();
	 $("#surveyName").html(surveyName);
	 $('.datepicker').datepicker({ changeYear: true});
	 var totalPages=0;
	 var surveyId=$('#surveyId').val();

		
	 if(totalQuestion%varPageSize==0)
		 totalPages=Math.floor(totalQuestion/varPageSize);
	 else
		 totalPages=Math.floor(totalQuestion/varPageSize)+1;
	 if(varCurrentPage>totalPages){
		 varCurrentPage=totalPages;
	 }else if(varCurrentPage<1){
		 varCurrentPage=1;
	 }
	 gotoPage(varCurrentPage, varPageSize,totalQuestion);
	 navigate(varCurrentPage,totalPages);
	 
	 
	 $('#submitSurveyAnswer').dirtyForms();
		
	 $('#next').click(function() {
		 if (varCurrentPage < totalPages){
			varCurrentPage = varCurrentPage + 1;
			$('#currrentPage').val(varCurrentPage);
			gotoPage(varCurrentPage, varPageSize,totalQuestion);
		}
		navigate(varCurrentPage,totalPages);
		app.navigate('surveys/'+surveyId+'/questions/'+varCurrentPage,false);
		$("#content-view-info").hide();
	});
	 
	$('#back').click(function() {
		if (varCurrentPage > 1){
			varCurrentPage = varCurrentPage - 1;
			$('#currrentPage').val(varCurrentPage);
			gotoPage(varCurrentPage, varPageSize,totalQuestion);
			navigate(varCurrentPage,totalPages);
			app.navigate('surveys/'+surveyId+'/questions/'+varCurrentPage,false);
			$("#content-view-info").hide();
		}
	});
		
	$('#surveySubmit').click(function() {
		$("#content-view-info").hide();
		$('#submitSurveyAnswer').dirtyForms('setClean');
 		var result = validSubmitSurveyAnswer(jQuery,_messageBundle,"submit");
 		if(result){
 			$.post('submitSurvey/'+surveyId, $("#submitSurveyAnswer").serialize()).success(function(data) {
 				app.navigate('',true);
 				$("#surveyName").hide();
 				leftSideView();
	 		});
 		}else{
 			varCurrentPage=Math.ceil(($('#submitSurveyAnswer').find('.alert-error').attr('alt'))/varPageSize);
 			if(isNaN(varCurrentPage)){
 				varCurrentPage=1;
 			}
 			gotoPage(varCurrentPage, varPageSize,totalQuestion);
 			navigate(varCurrentPage,totalPages);
 			app.navigate('surveys/'+surveyId+'/questions/'+varCurrentPage,false);
 		}
	});
	
	$('#surveySaveLater').click(function() {
	 	if($.DirtyForms.isDirty()){
	 		var result = validSubmitSurveyAnswer(jQuery,_messageBundle,"save");
	 		if(result){
	 			$.post('submitSurveyAnswer', $("#submitSurveyAnswer").serialize()).success(function(data) {
	 				if (data.indexOf("data-webApp-loginPage") != -1) {
	 					window.location = "login";
	 				}else {
	 					$('#content-view').hide().html(data).fadeIn('fast');
	 					var msg="<span class='alert alert-success'>"+messageBundle["saveSuccessfully"]+"</span>";
		 				$("#survey-content-info").hide().html(msg).fadeIn('fast');
	 				}
	 				$('#submitSurveyAnswer').dirtyForms('setClean');
	 			});
		 	}else{
	 			varCurrentPage=Math.ceil(($('#submitSurveyAnswer').find('.alert-error').attr('alt'))/varPageSize);
	 			if(isNaN(varCurrentPage)){
	 				varCurrentPage=1;
	 			}
	 			gotoPage(varCurrentPage, varPageSize,totalQuestion);
	 			navigate(varCurrentPage,totalPages);
	 			app.navigate('surveys/'+surveyId+'/questions/'+varCurrentPage,false);
	 		}	
		}else {
	 		var data="<span  class='alert alert-warning validate-error'>" +messageBundle["formNotChanged"]+"</span>";
	 		$("#survey-content-info").hide().html(data).fadeIn('fast');
	 	}
	});
	
	$.each($('.uploadDocument'), function(index) { 
		 var btnUpload=$(this);
		 var fileToUpload= $(this).attr('id');
		 var spanNode=$('a[documentName="'+fileToUpload+'"]');
		 
		upload= new AjaxUpload(btnUpload, {
				action: 'fileUpload',
				name: "docFile",
				data:{"questionId":fileToUpload},
				onSubmit: function(file, ext){
					this.disable();
					$(spanNode).parent().after("<img id="+fileToUpload+" alt='uploading' src='static/images/ajax-loader.gif' />");
				},
				onComplete: function(file, response){
					$(btnUpload).parent().find(".alert-error").remove();
					if (response.indexOf("portal-inValidFileSize") != -1){
						$(spanNode).parent().hide();
						setErrorMsg($(spanNode).parent(),"Not able to upload more than 50 MB");
					}else if (response.indexOf("portal-inValidFileType") != -1){
						$(spanNode).parent().hide();
						setErrorMsg($(spanNode).parent(),"File Type Not Supported");
					}else if (response.indexOf("portal-fileUploadError") != -1){
						$(spanNode).parent().hide();
						setErrorMsg($(spanNode).parent(),"Not able to Upload File");
					}
					else{
						$(spanNode).parent().show();
						var docId=parseResponse(response);
						$("span[alt="+$(spanNode).parent().attr('alt')+"]").remove();
						btnUpload.attr('file-value',file);
						spanNode.html('<b>'+file+'</b>');
						spanNode.attr('href','pullSurveyDocument/'+docId);
						$('input[documentName="'+fileToUpload+'"]').val(docId);
						$('#submitSurveyAnswer').dirtyForms('setDirty');
					}
					this.enable();
					$('img[id='+fileToUpload+']').remove();
				}
			});
	});
	 
	 
	$('.remove').click(function(){
		var element=$(this);
		var questionId=$(this).attr('documentName');
		var docId=$('input[documentName='+questionId+']').val();
		$('input[documentName='+questionId+']').val("");
		$(element).parent().hide();
		$('#submitSurveyAnswer').dirtyForms('setDirty');
	});
	 
	
}

function gotoPage(varCurrentPage, varPageSize,totalQuestion) {
	$("#survey-content-info").hide();
	$('.question-wrapper').hide();
	var temp = 0;
	var upperLimit=varCurrentPage * varPageSize;
	
	if(upperLimit>totalQuestion){
		upperLimit=totalQuestion;
	}
	
	
	for (temp = (varCurrentPage - 1) * varPageSize; temp < upperLimit; temp++) {
		var divname = 'question-wrapper-' + temp;
		document.getElementById(divname).style.display = 'block';
	};
	var lowerLimit=(varCurrentPage-1) * varPageSize;
	$('#lowerLimit').html(lowerLimit+1);
	$('#upperLimit').html(upperLimit);
};



 function  parseResponse(response,dataFetch){
	 return response.substring(response.indexOf("{'")+2,response.indexOf("'}"));
 }
 
function checkDirty (){
	if($.DirtyForms.isDirty()){
		if(confirm("<spring:message code='message.saveChanges'/>")){
			surveyQuestionHandler(_messageBundle);
			return false;
		}
		else{
			$('#submitSurveyAnswer').dirtyForms('setClean');
			app.navigate('',true);
		}
	}
}

