$(function() {
    $("#user-value").bind('input propertychange', function() {
        $(this).next().css("display", "none"); //隐藏错误时的提示
    });

    $("#password-value").bind('input propertychange', function() {
        $("#user-value").next().css("display", "none"); //隐藏错误时的提示
    });

    $("#lSubmit").click(function() {
        var page = "verificationUser";
        var uservalue = $("#user-value").val();
        var passwordvalue = $("#password-value").val();
        var rememberme = $(".checkbox input[type='checkbox']").is(':checked');
        $.post(
            page, {
                "name": uservalue,
                "password": passwordvalue,
                "rememberme": rememberme
            },
            function(result) {
                console.log(result);
                if (result == "登录成功"){
                    window.location.href = "home";
                }else{
                	$('.tip-failed').css("display", "inline");
                    $('#user-value').css("border-bottom", "1px solid #fb7299");
                }
            }
        );
    });
});