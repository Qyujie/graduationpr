var pageGet = "/getHeadPortrait";
var pageExit = "/exit";
$.get(pageGet, function(result) {
	$("#login").click(function() { // 给注销绑定点击事件
		window.location.href = pageExit;
	});
	if (result == "nullHeadPortrait") { // 登录但本来就没有设置头像

	} else {
		$("#div-navtop-headPortrait div").css("background-image","url(" + result + ")");

	}
});

$(".home").click(function() {
	window.location.href = "/home";
});

$(".userInformation").click(function() {
	window.location.href = "/user/userInformation";
});

$(".userOrder").click(function() {
	window.location.href = "/user/userOrder";
});

$(".contribute").click(function() {
	window.location.href = "contribute";
});
