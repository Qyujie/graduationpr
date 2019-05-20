
    var pageGet = "/getHeadPortrait";
    var pageExit = "/exit";
    var $liLogin = $(
        '<li><a href="/home">主页</a></li>' +
        '<li><a href="/user/userInformation">个人中心</a></li>' +
        '<li><a href="/user/userOrder">我的订单</a></li>' +
        '<li class="divider"></li>' +
        '<li><a href="/exit">注销</a></li>');
    var $liExit = $('<li ><a href="/login">登录</a></li>');
    $.get(
        pageGet,
        function(result) {
            if (result == "null") { //未登录
                $("#div-navtop-headPortrait div").removeAttr("background-image");
                $("#div-navtop-headPortrait ul").empty();
                $("#div-navtop-headPortrait ul").append($liExit);
            } else { //已登录 
                $("#div-navtop-headPortrait ul").empty();
                $("#div-navtop-headPortrait ul").append($liLogin);
                $("#login").click(function() { //给注销绑定点击事件
                    window.location.href = pageExit;
                });
                if (result == "nullHeadPortrait") { //登录但本来就没有设置头像

                } else {
                    $("#div-navtop-headPortrait div").css("background-image", "url(" + result + ")");

                }
            }
        }
    );

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
