$(function() {
	countAll();
	
	$("tbody").delegate('.div-btn-add','click',function (){
		var page = "addShopping";
		var rid = $(this).attr("value"); 
		$.ajax({
			url : page,
			type : "POST",
			data : {
				"rid" : rid ,
			},
			success : function(message) {
				if(message[0]==-1){
					console.log("未登录");
				}else if(message[0]==1){
					$("#resultData ul").append(
							'<li class="clearfix" value="'+ message[1] +'">'+
							'<input type="checkbox" class="checkbox_c " name="checkbox_c_Name" data-url="" checked="checked"/>'+
							'<span class="check"></span>'+
							'<div class="img_con">'+
							'	<img src="'+ message[2].imgurl +'" alt=""/>'+
							'</div>'+
							'<div class="product_name">'+
							'	<span>'+ message[2].name + message[1] +'</span>'+
							'	<a class="del_pro_btn">删除</a>'+
							'</div>'+
							'<div class="amount_btn clearfix">'+
							'	<div class="spinner">'+
							'		<button class="decrease" disabled="disabled">-</button>'+
							'		<input type="text" value="1" class="spinnerExample value"  maxlength="2"/>'+
							'		<button class="increase">+</button>'+
							'	</div>'+
							'</div>'+
							'</li>'
					);
				}else{
					var num = $(".clearfix[value='"+ message[1] +"'] .spinnerExample").val();
					console.log(num);
					num++;
					$(".clearfix[value='"+ message[1] +"'] .spinnerExample").val(num);
					$(".clearfix[value='"+ message[1] +"'] .spinnerExample").siblings('.decrease').prop("disabled",false);
				}

				countAll();
			}
		});
	});

	//全选
    $(".select_all").click(function() {
        $("input.checkbox_c").prop("checked", $(this).prop("checked"));
        countAll();
        
    });
    
    //删除选中
    $(".del_all_pro_btn").click(function() {
    	var cs = $(".checkbox_c");//已经加入购物车的物品
		for(var i=0;i<cs.length;i++){
			if($(cs[i]).prop("checked")){
				deleteajax($(cs[i]));
			}
		}
        countAll();
    });
    
	$("#resultData ul").delegate('.checkbox_c ','click',function (){
		if(countAll()){
			 $("input.select_all").prop("checked",true);
		}else{
			$("input.select_all").prop("checked",false);
		}
	});
	
	//-
	$("#resultData ul").delegate('.decrease ','click',function (){
		var num = $(this).next().val();
		num--;
		$(this).next().val(num);
		if(num<=1){
			$(this).prop("disabled",true);
		}
		ajax(num,$(this));
	});
	
	//+
	$("#resultData ul").delegate('.increase ','click',function (){
		var num = $(this).prev().val();
		num++;
		$(this).prev().val(num);
		$(this).siblings('.decrease').prop("disabled",false);
		ajax(num,$(this));
	});
	
	//input
	$("#resultData ul").delegate('.spinnerExample ','input propertychange',function (){
		var num = $(this).val();
		if(num<=1){
			$(this).prev().prop("disabled",true);
		}else{
			$(this).prev().prop("disabled",false);
		}
		ajax(Number(num),$(this));
	});
	
	//ajax
	function ajax(num,object) {
		if(typeof num == "number"){
			var page = "updateShopping";
			var rid = object.parents("li").attr("value");
			$.ajax({
				url : page,
				type : "GET",
				data : {
					"num" : num ,
					"rid" : rid,
				},
				success : function(message) {
					if(message==-1){
						console.log("未登录");
					}
				}
			});
		}
	}
	
	//删除
	$("#resultData ul").delegate('.del_pro_btn ','click',function (){
		deleteajax($(this));
	});
	
	function deleteajax(object){
		var rid = object.parents("li").attr("value");
		var page = "deleteShopping";
		$.ajax({
			url : page,
			type : "GET",
			data : {
				"rid" : rid,
			},
			success : function(message) {
				if(message==-1){
					console.log("未登录");
				}
			}
		});
		object.parents("li").remove();
		countAll();
	}
	
	//结算
	$(".div-btn-Settlement").click(function() {
		var rid = [];
		for (var i = 0; i < $("#resultData li").length; i++) {
			if($($("#resultData li").children(".checkbox_c")[i]).prop("checked")){
				rid.push($($("#resultData li")[i]).attr("value")); 
			}
		}
		if(rid.length>0){
			var page = "checkedShopping";
			$.ajax({
				url : page,
				type : "GET",
				data : {
					"rid" : rid,
				},
				traditional : true,// 这里设置为true，不然传不了数组
				success : function(message) {
					if(message==-1){
						console.log("未登录");
					}
					if(message==-2){
						console.log("未选择");
					}
					if(message==0){
						console.log("跳转");
					}
				}
			});
		}else{
			console.log("未选择");
		}
    });
	
	//计算选中的物品以及总共有多少物品
	function countAll(){
		var num = 0;
		var cs = $(".checkbox_c");//已经加入购物车的物品
		for(var i=0;i<cs.length;i++){
			if($(cs[i]).prop("checked")){
				num++;
			}
		}
		$(".count_info p span").text(num);

		//购物车图标
		if(cs.length==0){
			$("#lmliCount").css("display","none");
		}else{
			$("#lmliCount").text(cs.length);
			$("#lmliCount").css("display","block");
		}
		
		//判断是否全部选中
		if(num==cs.length){
			return true;
		}else{
			return false;
		}
		
	}

});