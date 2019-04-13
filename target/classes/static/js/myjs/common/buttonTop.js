$(function(){
var $img = $('<img alt="Top_arrow" class="top_arrow" id="top_arrow" src="/img/topButton.png" />');
$("body").append($img);
    $(window).scroll(
      function(){
         $(window).scrollTop() > 20 ? $img.fadeIn(400) : $img.fadeOut(400)
     });
     $("body, html").scroll(
      function(){
         $("body,html").scrollTop() > 20 ? $img.fadeIn(400) : $img.fadeOut(400)
      });
      $img.click(
       function(){
         $("body,html").animate({scrollTop:0},400);
       });

    $("#top_arrow").hide(),
    $(window).scroll(
        function(){
            $(window).scrollTop() > 20 ? $("#top_arrow").fadeIn(400) : $("#top_arrow").fadeOut(400)
        }),
    $("body, html").scroll(
        function(){
            $("body,html").scrollTop() > 20 ? $("#top_arrow").fadeIn(400) : $("#top_arrow").fadeOut(400)
        }),
    $("#top_arrow").click(
        function(){
            $("body,html").animate({scrollTop:0},400);
        })
    
});