$(function() {
    $(".upload").click(function() {
        return $(this).next().click();
    });

    $("input[type='file']").change(function() {
        var page = "rtImgUpload";
        var formData = new FormData();
        var $this = $(this);
        var roomtypeName = $this.parent().siblings(".roomtype").text();
        formData.append('file', $this[0].files[0]);
        formData.append('name', roomtypeName);
        $.ajax({
            url: page,
            type: "POST",
            data: formData,
            contentType: false, //必须false才会避开jQuery对 formdata 的默认处理 XMLHttpRequest会对 formdata 进行正确的处理 
            processData: false, //必须false才会自动加上正确的Content-Type
            success: function(result) {
            	if(result != "-1" && result != "-2" && result != "-3"){
            		$this.siblings(":first").attr("href",result);
            		ModalTip("上传成功");
            	}else{
            		ModalTip("上传失败");
            	}
            }
        });
    });
});