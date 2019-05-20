$(function() {
    var $topNav = $('<div id="menu"></div>');
    $("body").prepend($topNav);
    $("#menu").load("/common/menu.html");
});