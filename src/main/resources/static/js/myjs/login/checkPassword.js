$(function() {
    //切换密码的可见性
    $('.password-toggle').click(function() {
        console.log('click');
        if ($('#password-value').attr("type") == "password") {
            $('#password-value').attr("type", "text");
            $(this).css("color", "#5bc0de");
        } else {
            $('#password-value').attr("type", "password");
            $(this).css("color", "#ccc");
        }
    });
});