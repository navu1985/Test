$(function() {
	   var pageSize=10;
	   var surveyPagerOptions = {
				container: $(".surveypager"),
				output: 'showing: {startRow} - {endRow} / {totalRows}',
				fixedHeight: true,
				removeRows: false,
				size : pageSize,
				cssGoto:	 '.gotoPage'
		};
	   $.tablesorter.customPagerControls({
			table          : $('#surveytable'),
			pager          : $('.surveypager'),                   // pager wrapper (string or jQuery object)
			pageSize       : '.left a',                // container for page sizes
			currentPage    : '.right a',               // container for page selectors
			ends           : 1,                        // number of pages to show of either end
			aroundCurrent  : 3,                        // number of pages surrounding the current page
			link           : '<a href="javascript:void(0)" class="tablepagination" data-page="{page}"><span>{page}</span></a>', // page element; use {page} to include the page number
			currentClass   : 'current',                // current page class name
			adjacentSpacer : '',                    // spacer for page numbers next to each other
			distanceSpacer : ''               // spacer for page numbers away from each other (ellipsis &amp;hellip;)
		});
	   
	  $("#surveytable").tablesorter({
		  theme : 'bootstrap',
		    emptyTo: 'none',
		    headerTemplate : '{content} {icon}',
		    widgets : ['uitheme', 'zebra'],
		    widgetOptions : {
		    	zebra : [ "normal-row", "alt-row" ]
		    }
	 })
	 .bind('filterEnd', function() {
		  //Ignoring header and filters tr
		  $('.last').attr('data-page',($('#surveytable tr:visible').length)-2);
	 })
	 .tablesorterPager(surveyPagerOptions);
	  
	  /* Policy My Pending Items*/
	  
	  
	  $.tablesorter.customPagerControls({
			table          : $('#policytable'),
			pager          : $('.policypager'),                   // pager wrapper (string or jQuery object)
			pageSize       : '.left a',                // container for page sizes
			currentPage    : '.right a',               // container for page selectors
			ends           : 1,                        // number of pages to show of either end
			aroundCurrent  : 3,                        // number of pages surrounding the current page
			link           : '<a href="javascript:void(0)" class="tablepagination" data-page="{page}"><span>{page}</span></a>', // page element; use {page} to include the page number
			currentClass   : 'current',                // current page class name
			adjacentSpacer : '',                    // spacer for page numbers next to each other
			distanceSpacer : ''               // spacer for page numbers away from each other (ellipsis &amp;hellip;)
		});
	  var policyPagerOptions = {
				container: $(".policypager"),
				output: 'showing: {startRow} - {endRow} / {totalRows}',
				fixedHeight: true,
				removeRows: false,
				size : pageSize,
				cssGoto:	 '.gotoPage'
	  };
	  $("#policytable").tablesorter({
		    theme : 'bootstrap',
		    emptyTo: 'none',
		    headerTemplate : '{content} {icon}',
		    widgets : ['uitheme'],
		    widgetOptions : {
		      zebra : [ "normal-row", "alt-row" ]
		    }
		  }).bind('filterEnd', function() {
			  //Ignoring header and filters tr
			  $('.last').attr('data-page',($('#policytable tr:visible').length)-2);
			})
		  .tablesorterPager(policyPagerOptions);
	
	  
	  $('.policy').click(function(){
		  	$(this).parent().css("font-weight", "normal");
	    	$.post('policies/'+$(this).attr('policyId-data')+'/openPolicy/'+$(this).attr('policydocId-data'),{"policyAckFlag":$(this).attr('policyId-ack'),"_": $.now()}).success(function(data) {
			 	if (data.indexOf("data-webApp-loginPage") != -1) {
					window.location = "login";
				} else {
					$('#content-view').append("<div id='content-view-modal'></div>");
					$('#content-view-modal').html(data);
				}
			});
	   });
	  
	  var paginationHelpOptions={
	    		content :"showing From -To /Filtered (Total)",
	    		placement:'top',
	    		trigger: "hover"
	    };
	    $('.help-pagination').popover(paginationHelpOptions);
}); 