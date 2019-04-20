$(function() {
	var page = "searchRoomInformation";
	var arrive = null;// 入住时间
	var departure = null;// 退房时间
	var adults = null;// 成人人数
	var children = null;// 小孩人数
	var roomtype = "";
	var breakfast = "";
	var facility = [];
	var policy = [];

	/* 入住时间 */
	$(".from").change(function() {
		arrive = $(".from").val();
		console.log(arrive);
		ajax();
	});

	/* 退房时间 */
	$(".to").change(function() {
		departure = $(".to").val();
		console.log(departure);
		ajax();
	});

	/* 成人人数 */
	$(".awe-select[name='adults']").change(function() {
		adults = $(this).val();
		console.log(adults);
		ajax();
	});

	/* 小孩人数 */
	$(".awe-select[name='children']").change(function() {
		children = $(this).val();
		console.log(children);
		ajax();
	});

	/* 房型 */
	$(".roomtype").click(function() {
		roomtype = $(this).children("span").text();
		if (roomtype == "全部") {
			roomtype = "";
		}
		ajax();
	});

	/* 早餐 */
	$(".breakfast").click(function() {
		breakfast = $(this).children("span").text();
		if (breakfast == "全部") {
			breakfast = "";
		}
		ajax();
	});

	/* 设施 */
	$(".facility").click(function() {
		if ($(this).hasClass("Active")) {
			$(this).removeClass("Active");
		} else {
			$(this).addClass("Active");
		}
		var text = [];
		text = $(this).parent().children(".Active").children("span");
		for (var i = 0; i < text.length; i++) {
			facility[i] = $(text[i]).attr("value"); 
		}
		if (typeof text[0] == "undefined") {
			facility[0] = null;
		}
		if(facility.length>text.length){
			facility.splice(text.length,facility.length-text.length);
		}
		ajax();
	});

	/* 退订政策 */
	$(".policy").click(function() {
		if ($(this).hasClass("Active")) {
			$(this).removeClass("Active");
		} else {
			$(this).addClass("Active");
		}
		var text = [];
		text = $(this).parent().children(".Active").children("span");
		for (var i = 0; i < text.length; i++) {
			policy[i] = $(text[i]).attr("value"); 
		}
		if (typeof text[0] == "undefined") {
			policy[0] = null;
		}
		if(policy.length>text.length){
			policy.splice(text.length,policy.length-text.length);
		}

		console.log(text.length);
		console.log(policy.length);
		ajax();
	});

	function ajax() {
		$.ajax({
			url : page,
			type : "POST",
			data : {
				"arrive" : arrive,
				"departure" : departure,
				"adults" : adults,
				"children" : children,
				"roomtype" : roomtype,
				"breakfast" : breakfast,
				"facility" : facility,
				"policy" : policy,
			},
			traditional : true,// 这里设置为true，不然传不了数组
			success : function(list) {
				$("tbody").empty();
				
				for(var resource of list[0]){
					
					var imgurl = list[3][resource.id];
					
					var roomtype = resource.roomtype;
					
					var breakfast = resource.breakfast;
					
					var facilities = list[1][resource.id];
					var $facility = "";
					for(var facility of facilities){
						$facility += "<div>"+ facility.name +"</div>";
					}
					
					var policies = list[2][resource.id];
					var $policy = "";
					for(var policy of policies){
						$policy += "<div>"+ policy.name +"</div>";
					}
					var remain = resource.remain;
					
					var price = resource.price;
					
					
					$("tbody").append(
							'<tr>'+
							'<td>'+
							'	<img width="250px" src="'+ imgurl +'" alt=""/>'+
							'	<div>'+ roomtype +'</div>'+
							'</td> '+
							'<td>'+ breakfast +'</td>'+
							'<td>'+ $facility +'</td>'+
							'<td>'+ $policy +'</td>'+
		  					'<td>'+ remain +'</td>'+
							'<td>'+ price +'</td> '+
							'<td>'+
		                	'	<div class="btn-group-vertical" data-toggle="buttons">'+
							'			<div class="div-btn-reserve">预订</div>'+
							'			<div class="div-btn-add">添加</div>'+
		            		'	</div>'+
		            		'</td>'+
							'</tr>'
							);
				}
				
//				console.log('resources:');
//				console.log(list[0]);
//				console.log('resource_facilities:');
//				console.log(list[1]);
//				console.log('resource_policies:');
//				console.log(list[2]);
//				console.log('resource_imgurl:');
//				console.log(list[3]);
				}
		});
	}

});