function resetguid(element){
	var appId =$(element).attr('resetPassportId');
	$.get('admin/resetApplication/'+appId,{"_": $.now()}).success(function() {
		$("div[data-dbGuid="+appId+"]").hide();
	});
}

 	
function disable(element){
	var appId =$(element).attr('stopPassportId');
		$.post('admin/connectionStop', {'applicationId':appId}).success(function(data) {
		if (data.indexOf("data-webApp-loginPage") != -1) {
			window.location = "login";
		} else {
			$("div[statusContentId="+appId+"]").hide().html(data).fadeIn('fast');
		}
	});
} 

function enable(element){
	var appId =$(element).attr('startPassportId');
	 $.post('admin/connectionStart', {'applicationId':appId}).success(function(data) {
		if (data.indexOf("data-webApp-loginPage") != -1) {
			window.location = "login";
		} else {
			$("div[statusContentId="+appId+"]").hide().html(data).fadeIn('fast');
		}
	});
}

function addPassport(){
		$.post('admin/saveApplication', $('#application').serialize()).success(function(data) {
			if (data.indexOf("data-webApp-loginPage") != -1) {
			window.location = "login";
		} else {
			$('#content-view').html(data);
			if(data.indexOf("data-applicationDto-valid")==-1){
				app.navigate('#admin',true);
			}
		}
	});
}