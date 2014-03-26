var _messageBundle;
var app;
function indexJs($,messageBundle) {
	_messageBundle=messageBundle;
	$(window).bind('beforeunload', function() {
		  if($.DirtyForms.isDirty())
	    return 'Please Save your Data?';
	});
	
	var AppRouter = Backbone.Router.extend({
		routes : {
			"" : "list",
			"changepasswordview":"changepasswordview",
			"changeexistingpassword":"changeexistingpassword",
			"surveys/:id/questions/:page" : "questions",
			"policies":"policies"
		},
		list : function() {
			$("#content-view-info").hide();
			$(".taggerInfo").removeClass("strong").show();
			$("#pendingSurveys").addClass("strong");
			$("#ui-datepicker-div").hide();
			$("#surveyName").hide();
			
			$.get('surveys',{ "_": $.now() }).success(function(data) {
				if (data.indexOf("data-webApp-loginPage") != -1) {
					window.location = "login";
				} else {
					$("#content-view").hide().html(data).fadeIn('fast');
					if($("#isPolicyAvailable").text()=="true"){
						$('.policies').show();
					}
				}
			}); 
		},
		questions : function(id,page) {
			$("#surveyName").show();
			$.get('surveys/' + id + '/questions/'+ page,{ "_": $.now() }).success(function(data) {
				if (data.indexOf("data-webApp-loginPage") != -1) {
					window.location = "login";
				} else {
					$("#content-view").hide().html(data).fadeIn('slow');
					$("#taginner").attr("onclick", "return checkDirty();");
				}
			});
		},
		changepasswordview:function(){
			$("#content-view-info").hide();
			$.get('changeexistingpassword').success(function(data) {
				if (data.indexOf("data-webApp-loginPage") != -1) {
					window.location = "login";
				} else {
					$("#content-view").hide().html(data).fadeIn('fast');
					$("#taginner").hide();
					$("#pendingSurveys").html("Change Password").removeAttr("href");
				}
			});	
			$(".taggerInfo").removeClass("strong").hide();
			$("#changepassword").parent().show();
		},
		changeexistingpassword:function(){
	 		  new changeExistingPassword(jQuery,messageBundle);
			  if($("#changeexistingpassword").valid()){ 
				  $.post('changeexistingpassword', $("#changeexistingpassword").serialize()).success(function(data) {
					 	if (data.indexOf("data-webApp-loginPage") != -1) {
							window.location = "login";
						} else {
							$("#content-view").hide().html(data).fadeIn('fast');
						}
					}); 
			 }
			 app.navigate('changepasswordview');
		},
		policies:function(){
			$("#pendingSurveys").removeClass("strong").show();
			$("#policies").addClass("strong");
			$.get('policies',{ "_": $.now() }).success(function(data) {
				if (data.indexOf("data-webApp-loginPage") != -1) {
					window.location = "login";
				} else {
					$("#content-view").hide().html(data).fadeIn('fast');
					$('.policies').show();
				}
			});	
		},
		before: {
		    '' : function() {
		    	if($.DirtyForms.isDirty()){
		    		 if(!confirm("This page is asking you to confirm that you want to leave - data you have entered may not be saved")){
		    			if(surveyId != undefined && pageNo!=undefined){
		    				oldUrl="surveys/"+surveyId+"/questions/"+pageNo;
				    		app.navigate(oldUrl,false);
				    		return false;	
		    			}
		    		} 
		    	}
		    }
		},
		after: {
		    '' : function(_surveyId,_pageNo) {
		    	if(_surveyId != undefined && _pageNo!=undefined){
		    		surveyId=_surveyId;
		    		pageNo=_pageNo;
		    	}
		    }
		}
	});
	
	app = new AppRouter();
	Backbone.history.start();
	
}

function surveyQuestionHandler(messageBundle){
 	if($.DirtyForms.isDirty()){
 	var result = validSubmitSurveyAnswer(jQuery,_messageBundle,"save");
 	$("#content-view-info").hide();
 	if(result){
 			$.post('submitSurveyAnswer', $("#submitSurveyAnswer").serialize()).success(function(data) {
 				if (data.indexOf("data-webApp-loginPage") != -1) {
 				window.location = "login";
 			} else {
 				var msg="<h1 style='color:green;'>"+messageBundle["saveSuccessfully"]+"</h1>";
 	 			$("#content-view-info").html(msg).fadeIn('fast');
 				$("#content-view").html(data).fadeIn('slow');
 			}
  			$('#submitSurveyAnswer').dirtyForms('setClean');
 		});
 		}
 	}else{
 		var data="<h1 style='color:green;'>"+messageBundle["formNotChanged"]+"</h1>";
 		$("#content-view-info").hide().html(data).fadeIn('fast');
 	}
 }

