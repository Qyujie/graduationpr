$(function() {
    $("#user-value").bind('input propertychange', function() {
        $(this).nextAll().css("display", "none"); //输入账号时隐藏账号框下的提示
    });

    //验证密码长度
    $("#password-check-value").bind('input propertychange', function() {
        if (this.value.length < 6 && this.value.length > 0) {
            $(this).next().css("display", "inline");
        } else {
            $(this).next().css("display", "none");
        }
    });

    //提交
    $("#rSubmit").click(function() {
        var page = "repasswordSubmit";

        var uservalue = $("#user-value").val();
        var passwordvalue = $("#password-value").val();
        var passwordcheckvalue = $("#password-check-value").val();
        if (uservalue == "") {
            $("#user-value").nextAll().last().css("display", "inline");
        } else if (passwordvalue == "") {
            $("#password-value").next().css("display", "inline");
        } else {
            $.post(
                page, {
                    "name": uservalue,
                    "password": passwordcheckvalue,
                    "phone": passwordvalue
                },
                function(result) {
                    if (result == "-1") {
                        $('#password-value').next().css("display", "inline");
                    } else {
                        window.location.href = "login";
                    }
                }
            );
        }
    });
});