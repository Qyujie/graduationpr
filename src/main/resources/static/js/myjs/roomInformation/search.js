$(function() {
    var page = "searchRoomInformation";
    var arrive = null;//入住时间
    var departure = null;//退房时间
    var adults = null;//成人人数
    var children = null;//小孩人数
    var roomtype = "全部";
    var breakfast = "全部";
    var facility = [];
    var policy = [];
    facility[0] = "全部";
    policy[0] = "全部";
    $(".roomtype").click(function() {
    	roomtype=$(this).children("span").text();
    	ajax();
    });
    
    $(".breakfast").click(function() {
    	breakfast=$(this).children("span").text();
    	ajax();
    });
    
    $(".facility").click(function() {
    	if($(this).hasClass("Active")){
    		$(this).removeClass("Active");
    	}else{
        	$(this).addClass("Active");
    	}
    	var text=[];
    	text = $(this).parent().children(".Active").children("span");
    	for(var i=0;i<text.length;i++){
    		facility[i]=$(text[i]).text();
    	}
    	if(typeof facility[0] == "undefined"){
    		facility[0]="全部";
    	}
		console.log(facility[0]);
		ajax();
    });
    
    $(".policy").click(function() {
    	if($(this).hasClass("Active")){
    		$(this).removeClass("Active");
    	}else{
        	$(this).addClass("Active");
    	}
    	var text=[];
    	text = $(this).parent().children(".Active").children("span");
    	for(var i=0;i<text.length;i++){
    		policy[i]=$(text[i]).text();
    	}
    	if(typeof policy[0] == "undefined"){
    		policy[0]="全部";
    	}
		console.log(facility[0]);
    	ajax();
    });
    
    function ajax() {
    	$.ajax({
    		  url: page,
    		  type: "POST",
    		  data: {
    			  "arrive" : arrive,
                  "departure" : departure,
                  "adults" : adults,
                  "children" : children,
                  "roomtype" : roomtype,
              	  "breakfast" : breakfast,
               	  "facility" : facility,
               	  "policy" : policy,
    		  },
    		  traditional: true,//这里设置为true
    		  success: function(result) {
    			  if (result == "Success") {
                      console.log('修改成功');
                  } else {
                      console.log('修改失败');
                  }
    		  }
    		});
    }
    
});