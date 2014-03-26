$(function(){
	var selectedItemQ1 = "";
	var selectedItemQ1value = "";
	var selectedItemQ2 = "";
	var selectedItemQ2value = "";
	var selectedItemQ3 = "";
	var selectedItemQ3value = "";
	$('#questionOne').change(	function() {
		if (selectedItemQ1 != "") {
		$("#questionThree").append("<option value='" + selectedItemQ1+ "'>"	+ selectedItemQ1value + "</option>");
		$("#questionTwo").append("<option value='" + selectedItemQ1+ "'>"+ selectedItemQ1value+ "</option>");
		}
		selectedItemQ1 = $('#questionOne').val();
		if (selectedItemQ1 != "") {
			selectedItemQ1value = $('#questionOne').find('option:selected').text();
			$("#questionTwo option[value='" + selectedItemQ1 + "']").remove();
			$("#questionThree option[value='" + selectedItemQ1 + "']").remove();
		}
	});

	$('#questionTwo').change(function() {
		if (selectedItemQ2 != "") {
			$("#questionThree").append("<option value='" + selectedItemQ2+ "'>"+ selectedItemQ2value+ "</option>");
			$("#questionOne").append("<option value='" + selectedItemQ2+ "'>"+ selectedItemQ2value+ "</option>");
		}
		selectedItemQ2 = $('#questionTwo').val();
		if (selectedItemQ2 != "") {
			selectedItemQ2value = $('#questionTwo').find('option:selected').text();
			$("#questionThree option[value='" + selectedItemQ2 + "']").remove();
			$("#questionOne option[value='" + selectedItemQ2 + "']").remove();
		}
	});

	$('#questionThree').change(	function() {
		if (selectedItemQ3 != "") {
			$("#questionTwo").append("<option value='" + selectedItemQ3+ "'>"	+ selectedItemQ3value+ "</option>");
			$("#questionOne").append("<option value='" + selectedItemQ3+ "'>"+ selectedItemQ3value+ "</option>");
		}
		selectedItemQ3 = $('#questionThree').val();
		if (selectedItemQ3 != "") {
			selectedItemQ3value = $('#questionThree').find('option:selected').text();
			$("#questionTwo option[value='" + selectedItemQ3 + "']").remove();
			$("#questionOne option[value='" + selectedItemQ3 + "']").remove();
		}
	});
	
	$("#registerUser").click(function(){
		$("#registrationForm").submit();
	});
	
	if ($.browser.msie){
		$('select')	.bind('mousedown', function() {$(this).removeClass('selectdefault').addClass('selectAutoExpand');})
		.bind('focus', function() {$(this).removeClass('selectdefault').addClass('selectAutoExpand');})
		.bind('focusout', function() {$(this).removeClass('selectAutoExpand').addClass('selectdefault');})
		.bind('change', function() {$(this).removeClass('selectAutoExpand').addClass('selectdefault');});
	}
});