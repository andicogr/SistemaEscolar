deshabilitarKeyPress();

$("#btnVerCambiarPassword").click(function(){
	$.get(baseURL + "configuracionUsuario/verCambiarPassword", function(respuesta) {
		$("#smallModal").html(respuesta);
	});
});