deshabilitarKeyPress();

$("#btnVerCambiarRol").click(function(){
	$.get(baseURL + "configuracionUsuario/verCambiarRol", function(respuesta) {
		$("#smallModal").html(respuesta);
	});
});

var reglasValidacion = {
	'passwordActual': {required: true, maxlength: 20, minlength: 5},
	'nuevoPassword': {required: true, maxlength: 20, minlength: 5},
	'confirmarPassword': {required: true, maxlength: 20, minlength: 5, equalTo: "#nuevoPassword"}
};

var mensajesValidacion = {
	'passwordActual': {
		required: "Este valor es requerido",
		maxlength: "El campo debe tener como maximo {0} characteres",
		minlength: "El campo debe tener al menos {0} characteres"
	},
	'nuevoPassword': {
		required: "Este valor es requerido",
		maxlength: "El campo debe tener como maximo {0} characteres",
		minlength: "El campo debe tener al menos {0} characteres"
	},
	'confirmarPassword': {
		required: "Este valor es requerido",
		maxlength: "El campo debe tener como maximo {0} characteres",
		minlength: "El campo debe tener al menos {0} characteres",
		equalTo: "Las claves no son iguales"
	}
};

$("#formularioConfiguracionCambiarPassword").validate({
	onkeyup: function (element) {
         this.element(element);
	},
	rules : reglasValidacion,
	messages : mensajesValidacion
});

$("#btnCambiarPassword").click(function() {
	if($("#formularioConfiguracionCambiarPassword").valid()){
		$.ajax({
			type: "POST",
			url: baseURL + "configuracionUsuario/cambiarPassword",
			data: new FormData($("#formularioConfiguracionCambiarPassword")[0]),
			async: false,
			cache: false,
			contentType: false,
			processData: false,
			success: function(retorno){
				if(retorno['notificacion'] != null){
					new PNotify(retorno['notificacion']);
				}

				if(eval(retorno['estado']) == true){
					$('#smallModal').modal('toggle');
				}
			}
		})
	}
});