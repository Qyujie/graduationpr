/*------------------------------------------------------
    Author : www.webthemez.com
    License: Commons Attribution 3.0
    http://creativecommons.org/licenses/by/3.0/
---------------------------------------------------------  */

(function ($) {
    "use strict";
    var mainApp = {
        initFunction: function () {
            $('#main-menu').metisMenu();
        },
        initialization: function () {
            mainApp.initFunction();
        }
    }
    mainApp.initFunction(); 
	$("#sideNav").click(function(){
		if($(this).hasClass('closed')){
			$('.navbar-side').animate({left: '0px'});
			$(this).removeClass('closed');
			$('#page-wrapper').animate({'margin-left' : '260px'});
			
		}
		else{
		    $(this).addClass('closed');
			$('.navbar-side').animate({left: '-260px'});
			$('#page-wrapper').animate({'margin-left' : '0px'}); 
		}
	});
       
    
}(jQuery));
