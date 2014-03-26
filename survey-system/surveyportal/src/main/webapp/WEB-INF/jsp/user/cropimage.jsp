<jsp:useBean id="now" class="java.util.Date"/>
<div id="cropImage" class="modal hide fade in" style="width: 530px; height: 500px">
			 <div class="modal-header">
			    <button type="button" class="close" style="color: white;" data-dismiss="modal">&times;</button>
			 </div>
			<div class="crop-modal-body">
				<div>
					<form id="formFileUpload" name="formFileUpload" method="post" action="edit/image" autocomplete="off" enctype="multipart/form-data">
						<img id="photo"  src="profile/${profileImage}/tempimage?_=${now}" alt="profile">
						<input id="profileImageFileName" type="hidden" value="${profileImage}">
					</form>
					Click and drag to crop the image 
					<button id="saveProfile" class="btn btn-custom">Save Profile</button>
					&nbsp;
					<button id="cancel" class="btn btn-custom">Cancel</button>
				</div>
			</div>
</div>
<script type="text/javascript" src="static/portaljs/cropimage.js"></script>