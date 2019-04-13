$(function() {
	var curIndex = 0;
	//时间间隔(单位毫秒)，每20秒钟显示一张，数组共有imgSize张图片放在img文件夹下
	var timeInterval = 20000;
	//定义一个存放照片位置的数组，可以放任意个，在这里放imgSize个
	var arr = new Array();
	var imgSize = 11;
	for(var i=0;i<imgSize;i++){
		arr[i] = "../img/backgroundImg/img_"+i+".jpg";
	}
	var tf = true;
	
	setInterval(changeImg, timeInterval);
	$("#bodydiv02").css("background-image", "url(" + arr[Math.floor(Math.random()*arr.length)] + ")");
	function changeImg() {
		curIndex = Math.floor(Math.random()*arr.length);
		if (tf) {
			$("#bodydiv01").css("background-image", "url(" + arr[curIndex] + ")"); //显示对应的图片
			$("#bodydiv02").animate({
				opacity: '0'
			}, 2000);
			tf = false;
		} else {
			$("#bodydiv02").css("background-image", "url(" + arr[curIndex] + ")");
			$("#bodydiv02").animate({
				opacity: '1'
			}, 2000);
			tf = true;
		}
	}
});
