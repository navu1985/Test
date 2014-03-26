<form id="formFileUpload" name="formFileUpload" method="post" action="edit/image" autocomplete="off" enctype="multipart/form-data">
    <input type="file" id="formFileUploadInputFile" name="partnersLogo" onchange="profileImageSubmit();" />
</form>

<script>
function profileImageSubmit(){
	document.getElementById('formFileUpload').submit();
}
</script>