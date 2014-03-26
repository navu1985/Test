$(function(){
	var AppRouter = Backbone.Router.extend({
		routes : {
			"" : "pendingitems",
			"policies" : "policies",
			"admin" : "admin",
			"resetpassword" : "resetpasswordview",
			"editApplication/:applicationId":"editApplication",
			"editAdminSettings":"editAdminSettings",
			"updateAdminSettings":"editAdminSettings",
			"surveys/:id/questions/:page" : "questions",
			"profile" : "profile"
		},
		pendingitems : function(event) {
			$(".modal").modal('hide');
			$.get('pendingitems',{ "_": $.now() }).success(function(data) {
				if (data.indexOf("data-webApp-loginPage") != -1) {
					window.location = "login";
				} else {
					$('#content-view').hide().html(data).fadeIn('slow');
				}
			});
			$(".nav-stacked li").removeClass('active');
			$('#pendingitems').addClass('active');
			document.getElementById('content-view').scrollIntoView();
		},
		policies : function() {
			$('#content-view').hide().html('<img  alt="loading" src="static/images/ajax-loader.gif" />');
			$.get('policies',{ "_": $.now() }).success(function(data) {
				if (data.indexOf("data-webApp-loginPage") != -1) {
					window.location = "login";
				} else {
					$('#content-view').hide().html(data).fadeIn('slow');
				}
			});
			$(".nav-stacked li").removeClass('active');
			$('#acknowledgedpolicies').addClass('active');
			document.getElementById('content-view').scrollIntoView();
		},
		admin : function() {
			$('#content-view').empty().wrapInner( "<div id='content-view-admin'></div>" +
					"<div id='content-view-adminsettings'></div>" +
					"<div id='content-view-errorsurveys'></div>");
			$.get('admin/connectedApplications',{ "_": $.now() }).success(function(data) {
			 	if (data.indexOf("data-webApp-loginPage") != -1) {
					window.location = "login";
				} else {
					$('#content-view-admin').hide().html(data).fadeIn('slow');
				}
			});
			
			$.get('admin/adminSettings',{ "_": $.now() }).success(function(data) {
				if (data.indexOf("data-webApp-loginPage") != -1) {
					window.location = "login";
				} else {
					$('#content-view-adminsettings').hide().html(data).fadeIn('slow');
				}
			});
			
			$.get('admin/errorSurveys',{ "_": $.now() }).success(function(data) {
				if (data.indexOf("data-webApp-loginPage") != -1) {
					window.location = "login";
				} else {
					$('#content-view-errorsurveys').hide().html(data).fadeIn('slow');
				}
			});
			
			leftSideView();
			$(".nav-stacked li").removeClass('active');
			$('#admin').addClass('active');
		},
		questions : function(id,page) {
			$.get('surveys/' + id + '/questions/'+ page,{ "_": $.now() }).success(function(data) {
				if (data.indexOf("data-webApp-loginPage") != -1) {
					window.location = "login";
				} else {
					$("#content-view").hide().html(data).fadeIn('slow');
				}
			});
			$(".nav-stacked li").removeClass('active');
			$('#pendingitems').addClass('active');
			document.getElementById('content-view').scrollIntoView();
		},
		editApplication: function(applicationId) {
			$.get('admin/getApplication/'+applicationId,{"_": $.now() }).success(function(data) {
			 	if (data.indexOf("data-webApp-loginPage") != -1) {
					window.location = "login";
				} else {
					$('#content-view').hide().html(data).fadeIn('slow');
				}
			});
			$(".nav-stacked li").removeClass('active');
			$('#admin').addClass('active');
			 document.getElementById('content-view').scrollIntoView();
		},
		editAdminSettings: function() {
			$.get('admin/editSettings',{ "_": $.now() }).success(function(data) {
			 	if (data.indexOf("data-webApp-loginPage") != -1) {
					window.location = "login";
				} else {
					$('#content-view').hide().html(data).fadeIn('slow');
				}
			});
			$(".nav-stacked li").removeClass('active');
			$('#admin').addClass('active');
		},
		resetpasswordview: function() {
			$.get('changeexistingpassword').success(function(data) {
				if (data.indexOf("data-webApp-loginPage") != -1) {
					window.location = "login";
				} else {
					$("#content-view").hide().html(data).fadeIn('fast');
					 document.getElementById('content-view').scrollIntoView();
				}
			});	
		},
		profile: function() {
			$.get('profile').success(function(data) {
				if (data.indexOf("data-webApp-loginPage") != -1) {
					window.location = "login";
				} else {
					$("#content-view").hide().html(data).fadeIn('fast');
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
});