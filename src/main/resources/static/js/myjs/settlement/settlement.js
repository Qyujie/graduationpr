$(function() {
	var dateId = "";//需要购物车id
	var number = 0;
	var arrival = $(".from").val();// 入住时间
	var depature = $(".to").val();// 退房时间
	
	//修改日期
	$(".f-to-btn").click(function (){
		dateId = $(this).parents(".shoppingId").attr("value");
		number = $(this).parents(".shoppingId").find(".spinnerExample").val();
		
		$(".from").val($(this).parents(".shoppingId").find(".f-to-arrival").text());
		$(".to").val($(this).parents(".shoppingId").find(".f-to-depature").text());
	});
	
	//ok
	$(".ok").click(function (){
		var page = "updateShoppingDate";
		arrival = $(".from").val();
		depature = $(".to").val();
		$.ajax({
			url : page,
			type : "GET",
			data : {
				"arrival" : arrival ,
				"depature" : depature ,
				"number" : number,
				"id" : dateId,
			},
			success : function(message) {
				if(message[0]==-1){
					console.log("未登录");
				}else if(message[0]==-2||message[0]==0||message[0]==1){
					$(".shoppingId[value='"+ dateId +"']").find(".f-to-arrival").text(arrival);
					$(".shoppingId[value='"+ dateId +"']").find(".f-to-depature").text(depature);
					if(message[0]==-2||message[0]==1){
						$(".remain").text(message[1]);
						$(".tip-failed").css("display","inline-block");
						remain(message[1],$(".shoppingId[value='"+ dateId +"']").find(".spinnerExample"));
					}
				}
			}
		});
	});
	
	//-
	$(".box-bd-item-cont").delegate('.decrease ','click',function (){
		//获取改变之前的数量以此来计算单价
		var $price = $(this).parents(".box-bd-item").next().find(".span-price");
		var unitPrice = $price.attr("value");

		var num =Number($(this).next().val());
		if(isNaN(num)){
			num = 1;
		}else{
			num--;
		}
		$(this).next().val(num);
		if(num<=1){
			$(this).prop("disabled",true);
		}
		
		//超过剩余不做或大于6处理
		num = remain(num,$(this));
		//设置数量改变后的总价
		$price.text(unitPrice*num);
		updateAjax(num,$(this));
		changePeopleInput(num,$(this));
		
	});
	
	//+
	$(".box-bd-item-cont").delegate('.increase ','click',function (){
		//获取改变之前的数量以此来计算单价
		var $price = $(this).parents(".box-bd-item").next().find(".span-price");
		var unitPrice = $price.attr("value");

		var num =Number($(this).prev().val());
		if(isNaN(num)){
			num = 1;
		}else{
			num++;
		}
		$(this).prev().val(num);
		$(this).siblings('.decrease').prop("disabled",false);
		
		//超过剩余或大于6不做处理
		num = remain(num,$(this));
		//设置数量改变后的总价
		$price.text(unitPrice*num);
		updateAjax(num,$(this));
		changePeopleInput(num,$(this));
		
	});
	
	//input
	$(".box-bd-item-cont").delegate('.spinnerExample ','input propertychange',function (){
		//获取改变之前的数量以此来计算单价
		var $price = $(this).parents(".box-bd-item").next().find(".span-price");
		var unitPrice = $price.attr("value");

		var num =Number($(this).val());
		if(isNaN(num)){
			num = 1;
			$(".spinnerExample").val(num);
			$(this).prev().prop("disabled",true);
		}else if(num<=1){
			$(this).prev().prop("disabled",true);
		}else{
			$(this).prev().prop("disabled",false);
		}

		//超过剩余不做或大于6处理
		num = remain(num,$(this));
		//设置数量改变后的总价
		$price.text(unitPrice*num);
		updateAjax(num,$(this));
		changePeopleInput(num,$(this));
		
	});

	 $(".J_optionInvoice").children(".item").on("click", function() {
		 var text = $(this).attr("value");
		 if(text=="电子发票"){
			 $("#checkoutInvoiceElectronic").css("display","block");
			 $("#checkoutInvoiceDetail").css("display","none");
			 $(".invoiceGroupItem1 input").prop("checked",true);
			 $(".invoiceGroupItem2 input").prop("checked",false);
			 $(".invoice-title input").val("");
			 
		 }else if(text=="普通发票"){
			 $("#checkoutInvoiceDetail").css("display","block");
			 $("#checkoutInvoiceElectronic").css("display","none");
		 }
		 $(this).addClass("selected");
		 $(this).siblings().removeClass("selected");

		 $(this).find("input").prop("checked",true);
		 $(this).siblings().find("input").prop("checked",false);
     });
	 
	 $(".J_invoiceType").children("li").on("click", function() {
		 $(this).find("input").prop("checked",true);
		 $(this).siblings().find("input").prop("checked",false);
     });
	 
	 $(".type li").on("click", function() {
		 var text = $(this).attr("value");
		 if(text=="个人"){
			 $("#CheckoutInvoiceTitle").addClass("hide");
		 }else if(text=="单位"){
			 $("#CheckoutInvoiceTitle").removeClass("hide");
		 }
		 $(this).addClass("selected");
		 $(this).siblings().removeClass("selected");
     });
	 
	 function updateAjax(number,object) {
		 if(typeof number == "number"){
				var page = "updateShoppingNumber";
				var id = object.parents(".shoppingId").attr("value");
				console.log(number);
				$.ajax({
					url : page,
					type : "GET",
					data : {
						"number" : number ,
						"id" : id,
					},
					success : function(message) {
						if(message==-1){
							console.log("未登录");
						}
						console.log(message[1].number);
					}
				});
			}
		}
	 
	 function remain(num,object) {
		 var remain = Number(object.parents(".box-bd-item-cont").find(".remain").text());
		 if(num>remain || num>6){
			 if(remain>6){
				 object.parents(".spinner").find(".spinnerExample").val(6);
				 num = 6;
			 }else{
				 object.parents(".spinner").find(".spinnerExample").val(remain);
				 num = remain;
			 }
		 }
			 return num;
	 }
	 
	 function changePeopleInput(num,object) {
		 if(num>0){
			 var id = object.parents(".shoppingId").attr("value");
			 while(true){
				 var $boxBdPsById = $(".box-bd-people input[value='"+ id +"']").parent();
				 var $boxBdP = $boxBdPsById.last();
				 var length = $boxBdPsById.length;
				 if(num>length){
					 var $newBoxBdP = $boxBdP.clone(true);
					 $boxBdP.after($newBoxBdP);
				 }else if(num<length){
					 $boxBdP.remove();
				 }else{
					 break;
				 }
			 }
			 
			 var $numbers = $boxBdP.parents(".xm-box").children(".box-bd").find(".box-bd-tit span");
			 for(var i=0; i<$numbers.length;i++){
				 $($numbers[i]).text(i+1);
			 }
		 }
	 }
	 
	 $("[data-toggle='tooltip']").tooltip();

});