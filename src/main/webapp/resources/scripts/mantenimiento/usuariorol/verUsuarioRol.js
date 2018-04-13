deshabilitarKeyPress();
capturarDatosInicialesDelFormulario();

var reglasValidacion = {
				'rol.id': {required: true},
			};

var mensajesValidacion = {
		'rol.id': {
				//required: "Este valor es requerido",
		}
};

aplicarReglasDeValidacionFormulario(reglasValidacion, mensajesValidacion);

$("#botonRegistrar").click(function() {
	enviarFormulario("mantenimiento/usuariorol/guardar");
});

$("#botonActualizar").click(function() {
	enviarFormulario("mantenimiento/usuariorol/actualizar");
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
						cargarDivContenidoPrincipal("mantenimiento/usuario/ver?id=" + retorno['idUsuario'])
					}
				}
			})
		}

	}else{
		mostrarNotificacionNingunCambioFormulario();
	}
}

$("#botonAtras").click(function() {
	validarCambiosFormularioAlCargarContenidoPrincipal("mantenimiento/usuario/ver?id=" + $("#idUsuario").val());
});

function btnEliminarRegistro(){
	if(mensajeDeConfirmacion("Esta seguro que  quiere eliminar este registro?")){
	    eliminarRegistros("mantenimiento/usuariorol/eliminar?ids="+ [$("#id").val()], 
	    		"mantenimiento/usuario/ver?id=" + $("#idUsuario").val());
	}
}
