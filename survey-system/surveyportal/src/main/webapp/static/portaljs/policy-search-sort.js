$(function() {
	   var pageSize=10;
	   var policyPagerOptions = {
				container: $(".policypager"),
				output: '<i onmouseover="displayPopOver(this)" class="icon-large icon-question-sign"></i> showing: {startRow} - {endRow} / {filteredRows} ('+$("#acknowledged-policies-count").html()+')',
				fixedHeight: true,
				removeRows: false,
				size : pageSize,
				cssGoto:	 '.gotoPage'
		};
	   
	  // call the tablesorter plugin and apply the ui theme widget
	  $("#policytable").tablesorter({
	    theme : 'bootstrap',
	    emptyTo: 'none',
	    headerTemplate : '{content} {icon}',
	    widgets : ['uitheme','filter'],
	    textExtraction: {
	        0: function(node, table, cellIndex){ return $(node).find("a").text(); }
	    },
	    widgetOptions : {
		      filter_functions : {
		    	  0 : function(e, n, f, i) {
		    		  return n.toLowerCase().indexOf($.trim(f).toLowerCase()) !=-1;
		    	  }
		      }
	    }
	  }).bind('filterEnd', function() {
		  //Ignoring header and filters tr
		  $('.last').attr('data-page',($('#policytable tr:visible').length)-2);
		})
	  .tablesorterPager(policyPagerOptions);
	  
	   $.tablesorter.customPagerControls({
			table          : $("#policytable"),
			pager          : $(".policypager"),                   // pager wrapper (string or jQuery object)
			pageSize       : '.left a',                // container for page sizes
			currentPage    : '.right a',               // container for page selectors
			ends           : 1,                        // number of pages to show of either end
			aroundCurrent  : 3,                        // number of pages surrounding the current page
			link           : '<a href="javascript:void(0)" class="tablepagination" data-page="{page}"><span>{page}</span></a>', // page element; use {page} to include the page number
			currentClass   : 'current',                // current page class name
			adjacentSpacer : '',                    // spacer for page numbers next to each other
			distanceSpacer : ''               // spacer for page numbers away from each other (ellipsis &amp;hellip;)
		});
	   
    $('.policy').click(function(){
    	$.post('policies/'+$(this).attr('policyId-data')+'/openPolicy/'+$(this).attr('policydocId-data'),{"policyAckFlag":$(this).attr('policyId-ack'),"_": $.now()}).success(function(data) {
		 	if (data.indexOf("data-webApp-loginPage") != -1){
				window.location = "login";
			} else {
				$('#content-view').append("<div id='content-view-modal'></div>");
				$('#content-view-modal').html(data);
			}
		});
   });
    
  //on keyup, start the countdown
    $('#policyFullTextInput').keyup(function(e){
      if (!e) e = window.event; // needed for cross browser compatibility
      if (e.keyCode == 13){
		fullTextSearch();
	  }
    });
    $("#policyFullTextSearch").click(function(){
    	fullTextSearch();
    });
    function fullTextSearch () {
  	  var savedFilters =$('#policytable').find('.tablesorter-filter').map(function(){
            return this.value || '';
        }).get();
  	  if(savedFilters !=""){
  		previousSavedFilters=savedFilters; 
  	  }
    	var inputData ={
    			"policyName":savedFilters[0],
    			"policyApplicationId" : savedFilters[1],
    	      	"policyType" : savedFilters[2],
    	      	"policyIssuedBy" : savedFilters[3],
    	      	"policyTopic" : savedFilters[4],
    	      	"policyVersionDate" : savedFilters[5],
    	      	"policyRelatedDoc" :savedFilters[6],
    	        "searchText" :$("#policyFullTextInput").val(),
    			"_": $.now()
    	};
    	if($("#policyFullTextInput").val() ==""){
    		$.get('policies',{ "_": $.now() }).success(function(data) {
				if (data.indexOf("data-webApp-loginPage") != -1) {
					window.location = "login";
				} else {
					$('#content-view').hide().html(data).fadeIn('slow');
					if(previousSavedFilters!= ""){
        				$('table').find('.tablesorter-filter').each(function(i){
              		      $(this).val( previousSavedFilters [i] );
              		  })
              		  .trigger('search');	
        			}
				}
			});
    	}else{
    		$.post('policies/search/fulltext',inputData).success(function(data) {
        	 	if (data.indexOf("data-webApp-loginPage") != -1) {
        			window.location = "login";
        		} else {
        			$('#content-view').hide().html(data).fadeIn('fast');
        			$("#policyFullTextInput").val(inputData.searchText);
        			if(previousSavedFilters!= ""){
        				$('table').find('.tablesorter-filter').each(function(i){
              		      $(this).val( previousSavedFilters [i] );
              		  })
              		  .trigger('search');	
        			}
        		}
        	 });
    	}
    }
    
    $("#policytable").find('.first').removeClass('disabled').click();
}); 