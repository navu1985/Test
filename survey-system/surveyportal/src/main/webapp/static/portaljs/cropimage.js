$(function(){
	var profileImageFileName=$("#profileImageFileName").val();
	var globalSelection="";
	$(".modal").modal('hide');
	$("#cropImage").modal(); 
	var ias =$("#photo").imgAreaSelect({ 
		aspectRatio: '4:3' ,
		instance: true, 
		handles: true,
		onSelectEnd: function (img, selection) {
	        globalSelection=selection;
	    }
	});
	$("#cropImage").on('hide', function () {
    	ias.setOptions({ hide: true });
    	ias.update();
    });
	var selectAll=true;
	$("#saveProfile").on('click', function () {
		if(globalSelection=="")	selectAll=true;
		else {
			selectAll=false;
		}
		$.post('profile/cropImage',{ 
			"fileName" : profileImageFileName ,
			"xStart" : globalSelection.x1,
			"yStart": globalSelection.y1,
			"xEnd" : globalSelection.x2,
			"yEnd" : globalSelection.y2,
			"selectAll" : selectAll,
			"_": $.now() 
			}
		).success(function(data) {
			$(".modal").modal('hide');
	    	$("#content-view-modal").hide().html(data).fadeIn('fast');
	    	profilepage();
		});
		globalSelection="";
    });
	
	$("#cancel").on('click', function () {
		$.get('profile',{"mode":"edit","_": $.now() }).success(function(data) {
			$(".modal").modal('hide');
			$("#content-view-modal").hide().html(data).fadeIn('fast');
		});
    });
});