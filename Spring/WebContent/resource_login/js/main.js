(function ($) {
    "use strict";

    /*==================================================================
    [ Validate after type ]*/
    $('.validate-input .input100').each(function(){
        $(this).on('blur', function(){
            if(validate(this) == false){
                showValidate(this);
            }
            else {
                $(this).parent().addClass('true-validate');
            }
            
        		
        })    
    })
  
  
    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

    $('.validate-form').on('submit',function(){
        var check = true;

        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) == false){
                showValidate(input[i]);
                check=false;
            }
        }

        return check;
    });


    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
           $(this).parent().removeClass('true-validate');
        });
    });

     function validate (input) {
    	 
    	 if($(input).val().trim() == ''){
         	$(input).parent().attr("data-validate","Enter "+$(input).attr("placeholder"));
         	return false;
         }
    	 
    	 if($(input).attr('name') == 'id') {
             if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
             	$("#userId").parent().attr("data-validate","Check Email Format");
             	return false;
            }else{
            	$("#userId").parent().attr("data-validate","Enter email");
            	return true;
            }
    	 }
    	 
        if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
            	$("#userId").parent().attr("data-validate","Check Email Format");
            	return false;
            }else if($(input).val().trim() == ''){
            	$("#userId").parent().attr("data-validate","Enter Email");
            	return false;
            }else{
            	var query = {
                	memberId : $("#userId").val()
                };
            	var possible = 'Y';
        		$.ajax({
        			url : "idCheck.do",
        			type : "post",
        			data : query,
        			async: false,
        			success : function(data) {
        				if (data == 1) {
        					$("#userId").parent().attr("data-validate",'Used Email');
        				} else {
        					$("#userId").parent().attr("data-validate","Enter Email");
        				}
        			}
        		}); // ajax ???
        		
        		if($("#userId").parent().attr("data-validate") == 'Used Email'){
        			return false;
        		}
        		
            }
            
        }
        
        if($(input).attr('name') == 'pwd'){
        	
        	var pw = document.getElementById("newPassWord").value; //????????????

			var pattern1 = /[0-9]/;

			var pattern2 = /[a-zA-Z]/;

			var pattern3 = /[~!@\#$%<>^&*]/; // ????????? ???????????? ?????? ??????

			var SamePass_0 = 0; //???????????? ?????????

			if (!pattern1.test(pw) || !pattern2.test(pw)
					|| !pattern3.test(pw) || pw.length<8||pw.length>50) {
				$("#newPassWord").parent().attr("data-validate","?????? 6???, ??????+??????+????????????");
				return false;
			} else {
				$("#newPassWord").parent().attr("data-validate","Enter password");
				return true;
			}

			

        	
//        	var pwd_length = $('#newPassWord').val().length;
//
//			if ( pwd_length < 6 || pwd_length >50) {
//				$("#newPassWord").parent().attr("data-validate","At least 6");
//				return false;
//			}else{
//				$("#newPassWord").parent().attr("data-validate","Enter password");
//				return true;
//			}

			
        }
        
        if($(input).attr('name') == 'pwd_check'){
        	var pw = document.getElementById("newPassWord").value; //????????????

    		var pw2 = document.getElementById("passWordCheck").value; // ?????? ????????????

    		if (pw != '' && pw2 == '') {
    			
    		} else if (pw != "" || pw2 != "") {
    			if (pw == pw2) {
    				$("#passWordCheck").parent().attr("data-validate","Enter password");
    				return true;
    			} else {
    				$("#passWordCheck").parent().attr("data-validate","Passwords do not match");
    				return false;
    			}
    		}
        }
    }
     

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');

        $(thisAlert).append('<span class="btn-hide-validate">&#xf135;</span>')
        $('.btn-hide-validate').each(function(){
            $(this).on('click',function(){
               hideValidate(this);
            });
        });
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();
        $(thisAlert).removeClass('alert-validate');
        $(thisAlert).find('.btn-hide-validate').remove();
    }
    
    function getTextLength(str) {
        var len = 0;
        for (var i = 0; i < str.length; i++) {
            if (escape(str.charAt(i)).length == 6) {
                len++;
            }
            len++;
        }
        return len;
    }
    

})(jQuery);
