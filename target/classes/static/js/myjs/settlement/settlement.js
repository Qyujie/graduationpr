$(function() {
	//-
	$(".box-bd-item-cont").delegate('.decrease ','click',function (){
		//获取改变之前的数量以此来计算单价
		var $price = $(this).parents(".box-bd-item").next().find(".span-price");
		var unitPrice = $price.attr("value");

		var num =Number($(this).next().val());
		if(isNaN(num)){
			num = 0;
		}else{
			num--;
		}
		$(this).next().val(num);
		if(num<=1){
			$(this).prop("disabled",true);
		}
		
		//设置数量改变后的总价
		$price.text(unitPrice*num);
		//ajax(num,$(this));
	});
	
	//+
	$(".box-bd-item-cont").delegate('.increase ','click',function (){
		//获取改变之前的数量以此来计算单价
		var $price = $(this).parents(".box-bd-item").next().find(".span-price");
		var unitPrice = $price.attr("value");

		var num =Number($(this).prev().val());
		if(isNaN(num)){
			num = 0;
		}else{
			num++;
		}
		console.log(num);
		$(this).prev().val(num);
		$(this).siblings('.decrease').prop("disabled",false);
		
		//设置数量改变后的总价
		$price.text(unitPrice*num);
		//ajax(num,$(this));
	});
	
	//input
	$(".box-bd-item-cont").delegate('.spinnerExample ','input propertychange',function (){
		//获取改变之前的数量以此来计算单价
		var $price = $(this).parents(".box-bd-item").next().find(".span-price");
		var unitPrice = $price.attr("value");

		var num =Number($(this).val());
		if(isNaN(num)){
			num = 0;
		}else if(num<=1){
			$(this).prev().prop("disabled",true);
		}else{
			$(this).prev().prop("disabled",false);
		}

		//设置数量改变后的总价
		$price.text(unitPrice*num);
		//ajax(Number(num),$(this));
	});

	 $(".J_optionInvoice").children(".item").on("click", function() {
		 var text = $(this).attr("value");
		 if(text=="电子发票"){
			 $("#checkoutInvoiceElectronic").css("display","block");
			 $("#checkoutInvoiceDetail").css("display","none");
			 
		 }else if(text=="普通发票"){
			 $("#checkoutInvoiceDetail").css("display","block");
			 $("#checkoutInvoiceElectronic").css("display","none");
		 }
		 $(this).addClass("selected");
		 $(this).siblings().removeClass("selected");
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
	 
	 function account(num) {
		 var text = $price.text() * num;
		 $price.text(text);
		}
	 
});