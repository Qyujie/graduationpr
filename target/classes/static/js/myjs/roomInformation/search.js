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
	facility[0] = "";
	policy[0] = "";

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
			facility[i] = $(text[i]).text();
		}
		if (typeof facility[0] == "undefined") {
			facility[0] = "";
		}
		console.log(facility[0]);
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
			policy[i] = $(text[i]).text();
		}
		if (typeof policy[0] == "undefined") {
			policy[0] = "";
		}
		console.log(facility[0]);
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
			success : function(model) {
				console.log(model);
				}
		});
	}

});