deshabilitarKeyPress();

capturarDatosInicialesDelFormulario();

var reglasValidacion = {};

var mensajesValidacion = {};

aplicarReglasDeValidacionFormulario(reglasValidacion, mensajesValidacion);

$("#botonRegistrar").click(function() {
	enviarFormulario("ajustesConfiguracion/guardar");
});

$("#botonActualizar").click(function() {
	enviarFormulario("ajustesConfiguracion/actualizar");
});

function enviarFormulario(url){
	if(seRealizaronCambiosEnElFormulario()){

		if($form.valid()){
			$.ajax({
				type: "POST",
				url: baseURL + url,
				data: new FormData($form[0]),
				async: false,
				cache: false,
				contentType: false,
				processData: false,
				success: function(retorno){

					if(retorno['notificacion'] != null){
						new PNotify(retorno['notificacion']);
					}
						
					if(eval(retorno['estado']) == true){
						cargarDivContenidoPrincipal("ajustesConfiguracion/ver?id=" + retorno['id'])
					}
				}
			})
		}

	}else{
		mostrarNotificacionNingunCambioFormulario();
	}
}
