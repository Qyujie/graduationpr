var $modalTip = $('<div id="ModalTip"></div>');
$("body").append($modalTip);
$("#ModalTip").css(
		{"margin": "auto",
		"position": "fixed",
		"top": "35%",
		"left": "0",
		"right": "0",
		"display": "none",
		"width": "250px",
		"min-height":"40px",
		"padding":"15px",
		"text-align": "center",
		"font-size": "120%",
		"background-color": "rgba(20, 185, 200, 1)",
		"letter-spacing":"5px",
		"border": "none",
		"border-radius": "4px",
		"cursor":"default"}
);


function ModalTip(txt) {
	$("#ModalTip").text(txt);
	$("#ModalTip").fadeIn(1000);
	setTimeout(function() {
		$("#ModalTip").fadeOut(1000);
	}, 3000);
}
