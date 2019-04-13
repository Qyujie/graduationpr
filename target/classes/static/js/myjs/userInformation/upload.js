$(function() {
    $("#div-headPortrait span").click(function() {
        return $("#div-headPortrait input").click();
    });

    $("#div-headPortrait input").change(function() {
        var page = "upload";
        var formData = new FormData();
        formData.append('file', $("#div-headPortrait input")[0].files[0]);
        $("#div-headPortrait").css("background-image", "url(" + $("#div-headPortrait input").val() + ")");
        $.ajax({
            url: page,
            type: "POST",
            data: formData,
            contentType: false, //必须false才会避开jQuery对 formdata 的默认处理 XMLHttpRequest会对 formdata 进行正确的处理 
            processData: false, //必须false才会自动加上正确的Content-Type
            success: function(result) {
                $("#div-headPortrait").css("background-image", "url(" + result + ")");
                $("#div-navtop-headPortrait div").css("background-image", "url(" + result + ")");
            }
        });
    });
});