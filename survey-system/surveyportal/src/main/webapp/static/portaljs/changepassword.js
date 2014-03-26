$(function(){
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
		errorClass : "alert alert-error",
		errorLabelContainer: "#errorMessage",
		onkeyup : false
	});
	
	$.validator.addMethod("regex", function(value, element) {
		var validLength = /.{8}/.test(value);
		var hasNums = /\d/.test(value);
		var hasSpecials = /[@#$!&%*]/.test(value);
		var hasalpha = /[A-z]/.test(value);
		return validLength && hasNums && hasSpecials && hasalpha;
	}, "");
	
	$("#updatePassword").validate({
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
			errorClass : "alert alert-error",
			errorLabelContainer: "#errorMessage",
			onkeyup : false
		});
});