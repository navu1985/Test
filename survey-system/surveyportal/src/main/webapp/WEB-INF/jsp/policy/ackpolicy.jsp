<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<input id="policyId" type="hidden" value="${policyId}"/>
<div id="navigate-top"></div>
<div id="policy-modal" class="modal hide fade in modal-iframe-height" data-backdrop="true">
	<div id="iframe-content" style="height: 500PX; ">
		<c:if test="${policyAckFlag eq  true}">
			<div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	      	</div>
      	</c:if>
		<!-- <div class="modal-body modal-body-costumize" > -->
		<div class="modal-body" style="height:400px">
			<div id="dociframediv" style="height: 95%;">
				<object type='application/pdf' id='hello' scrolling='yes' data-cross-domain='data-cross-domain' onload='onLoadIframe()' src='policies/document/${policyDocumentId}' data='policies/document/${policyDocumentId}' width='100%' height='100%' style='-webkit-overflow-scrolling:touch !important;'></object>
			</div>
			
		</div>
		<div class="modal-footer">
				<div style="line-height: 3;"> 
					<a data-ipad-tab="iphone-ipad-policy-document-tab" role="button" target="_blank" href="policies/document/${policyDocumentId}" class="btn close-ack-btn hide">Open Policy in new tab</a>
					<c:if test="${policyAckFlag eq  false}">
						<a id="ackclose" role="button" class="btn close-ack-btn" data-toggle="modal"><img src="static/portalimages/tick.png"/>  Acknowledge and Close</a>
						<a id="closewithoutack" role="button" class="btn close-ack-btn" data-toggle="modal"><img src="static/portalimages/delete.png"/>  Close without Acknowledging</a>
					</c:if>
				</div>
		</div>
	</div>
	<div id="ackclose-content" style="display: none;">
			<div class="modal-header" style="text-align: center; color: #ED9C28">
				<h3 id="myModalLabel">Acknowledge and Close</h3>
			</div>
			<div class="modal-body" style="text-align: left;">
				<p>By clicking 'Continue', you agree that your acknowledgement will be recorded, and this item will move from your "My Pending Items" screen to your "My Policies and Procedures", where you can refer to it at any time.</p>
			</div>
			<div class="modal-footer">
				<button id="acknowledgepolicy" class="btn btn-custom" data-dismiss="modal" aria-hidden="true">Continue</button>
				<button class="btn" id="ackclosemodal_close" aria-hidden="true">Cancel</button>
			</div>
	</div>
	<div id="closewithoutack-content" style="display: none;">
			<div class="modal-header" style="text-align: center; color: #ED9C28">
				<h3 id="myModalLabel">Close without Acknowledging</h3>
			</div>
			<div class="modal-body" style="text-align: left;">
				<p>If you're not ready to acknowledge yet, click "Continue" to close this item. It will remain on your "My Pending Items" screen until you have acknowledged it.</p>
			</div>
			<div class="modal-footer">
				<button class="btn btn-custom" data-dismiss="modal">Continue</button>
				<button class="btn" id="closewithoutackmodal_close">Cancel</button>
			</div>
	</div>
</div>

<script>
$(function (){
	if((/Android|webOS|iPhone|iPad|iPod|Opera Mini/i.test(navigator.userAgent))){
		$('a[data-ipad-tab="iphone-ipad-policy-document-tab"]').removeClass('hide');
	} 
	$("#policy-modal").modal();
	var ackCloseClickHandler = function(){
		$("#iframe-content").hide();
		$("#ackclose-content").show();
		$("#policy-modal").removeClass("modal-iframe-height");
	 };
	 
	 var closeWithOutAckHandler=function(){
		$("#iframe-content").hide();
		$("#closewithoutack-content").show();
		$("#policy-modal").removeClass("modal-iframe-height");
	};
	 
	$('#ackclose').click(ackCloseClickHandler);
	$('#closewithoutack').click(closeWithOutAckHandler);
	
	
	/*  click close on "ack and close"*/
	$('#ackclosemodal_close').click(function(){
		$("#iframe-content").show();
		$("#ackclose-content").hide();
		$("#policy-modal").addClass("modal-iframe-height");
	}); 
	
	$('#acknowledgepolicy').click(function(){
		var policyId=$('#policyId').val();
		var oldUrl=window.location.hash;
		app.navigate('#mypendingpolicy',false);
		$.post('policies/acknowledgepolicy/'+policyId,{"_": $.now() }).success(function(data) {
			app.navigate(oldUrl,true);
			leftSideView();
		});		
	});
	
	$('#closewithoutackmodal_close').click(function(){
		$("#iframe-content").show();
		$("#closewithoutack-content").hide();
		$("#policy-modal").addClass("modal-iframe-height");
	});
	var info = getAcrobatInfo();
	if(info.acrobat){
		$("#dociframediv").html("<object type='application/pdf' scrolling='yes' data-cross-domain='data-cross-domain' src='policies/document/${policyDocumentId}' data='policies/document/${policyDocumentId}' width='100%' height='100%' style='-webkit-overflow-scrolling:touch !important;'></object>");
	}
	else{
		$("#iframe-content").html("<div class='modal-header' style='text-align: center; color: #ED9C28'>	<h3 id='myModalLabel'>Install Acrobat pdf plugin to view Policy</h3></div>");
	}
});

function getAcrobatInfo() {
 function getBrowserName() {
	alert("Browser Name");
 return this.name = this.name || function() {
   var userAgent = navigator ? navigator.userAgent.toLowerCase() : "other";
	alert(userAgent);   
   if(userAgent.indexOf("chrome") > -1)        return "chrome";
   else if(userAgent.indexOf("safari") > -1)   return "safari";
   else if(userAgent.indexOf("msie") > -1 ||  userAgent.indexOf("trident") > -1) return "ie";
   else if(userAgent.indexOf("firefox") > -1)  return "firefox";
   return userAgent;
 }();
};

 function getActiveXObject(name) {
	 alert(new ActiveXObject(name));
 	try { return new ActiveXObject(name); } catch(e) {}
 };

function getNavigatorPlugin(name) {
 for(key in navigator.plugins) {
   var plugin = navigator.plugins[key];
   if(plugin.name == name) return plugin;
 }
};

function getPDFPlugin() {
 return this.plugin = this.plugin || function() {
   if(getBrowserName() == 'ie') {
     return getActiveXObject('AcroPDF.PDF') || getActiveXObject('PDF.PdfCtrl');
   }
   else {
     return getNavigatorPlugin('Adobe Acrobat') || getNavigatorPlugin('Chrome PDF Viewer') || getNavigatorPlugin('WebKit built-in PDF');
   }
 }();
};

function isAcrobatInstalled() {
 return !!getPDFPlugin();
};

function getAcrobatVersion() {
 try {
   var plugin = getPDFPlugin();
   if(getBrowserName() == 'ie') {
     var versions = plugin.GetVersions().split(',');
     var latest   = versions[0].split('=');
     return parseFloat(latest[1]);
   }
   if(plugin.version) return parseInt(plugin.version);
   return plugin.name;
 }
 catch(e) {
   return null;
 }
}
return {
 browser:        getBrowserName(),
 acrobat:        isAcrobatInstalled() ? true : getIfMobile(),
 acrobatVersion: getAcrobatVersion()
};

function getIfMobile(){
	return (/Android|webOS|iPhone|iPad|iPod|Opera Mini/i.test(navigator.userAgent));
}

};

</script>