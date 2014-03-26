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
		errorClass : "alert-error nowrap",
		errorElement : "span",
		onkeyup : false
	});
}
