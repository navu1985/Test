$(function(){
	
	leftSideView();
	profilepage();
	
	/*
	 * Ask user to save data on close or refresh of browser
	*/
	$(window).bind('beforeunload', function(event) {
		if($.DirtyForms.isDirty()){
		   return "Please save data";
		}
	});
	
	/*
	 * left panel active tab code
	*/
	$('.nav-stacked li a').on('click', function(e){
        var $thisLi = $(this).parent('li');
        var $ul = $thisLi.parent('ul');
        if (!$thisLi.hasClass('active')){
            $ul.find('li.active').removeClass('active');
            $thisLi.addClass('active');
        }
    });
	
	var popoverOption={
    		placement:wheretoplace,
    		trigger: "hover"
    };
	
	if( !(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) )) {
		$('.data-popover').popover(popoverOption);
	}
	
});

function leftSideView(){
	/*
	 * Get Count of left view
	*/
	$.get('leftpanelcount',{ "_": $.now() }).success(function(data) {
		
		/*
		 * Hide/Show My Policies and Procedures 
		*/
		if (data.ackPoliciesCount<=0){
			$('#acknowledgedpolicies').hide();
		}else{
			$('#acknowledgedpolicies').show();
			$('#acknowledgedpoliciescount').html(data.ackPoliciesCount);
		}
		
		/*
		 * Submit An Inquiry And Report an Issue 
		*/
		if(!data.displaySubmitAnInquiry)
			$('#submitInquiry').hide();
		if(!data.displayReportAnIssue)
			$('#reportanissue').hide();
		
		/*
		 * Pending Item Count
		*/
		$('#pendingcount').html(data.pendingItemCount);
	});
}


/*
 * Get Content of Profile  
*/
function profilepage(){
	$.get('mainmenuprofile',{ "_": $.now() }).success(function(data) {
		$('#profile-view').hide().html(data).fadeIn('fast');
		$('.profile-popover').popover('show');
	});
}

var paginationHelpOptions={
		content :"<b>a - b / x (y)</b> <br> <b>x</b> = the number of policies that match your current filters. <br> <b>y</b> = the total number of policies available to you, regardless of your current filters.",
		html : true,
		placement:'top',
		trigger: "hover"
};

function displayPopOver(element){
	$(element).popover(paginationHelpOptions);
	if( !(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) )) {
		$(element).popover('show');
	}
}

function wheretoplace(){
	var width = window.innerWidth;
    if (width<970) return 'bottom';
    else return 'right'; 
}

function updatepassword(){
	$.post('changeexistingpassword',$("#changeexistingpassword").serialize()).success(function(data) {
		if (data.indexOf("data-webApp-loginPage") != -1) {
			window.location = "login";
		} else {
			$("#content-view").hide().html(data).fadeIn('fast');
		}
	});	
}

function goBack(){
	history.go(-1);
}
