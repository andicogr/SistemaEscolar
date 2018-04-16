deshabilitarKeyPress();

$('#fechaExpiracionUsuario').daterangepicker({
  singleDatePicker: true,
  singleClasses: "picker_2",
  locale: {
		"format": 'DD/MM/YYYY',
		"daysOfWeek": ["Do","Lu","Ma","Mi","Ju","Vi","Sa"],
        "monthNames": [
           "Enero",
           "Febrero",
           "Marzo",
           "Abril",
           "Mayo",
           "Junio",
           "Julio",
           "Agosto",
           "Septiembre",
           "Octubre",
           "Noviembre",
           "Diciembre"
       ],
	  }
}, function(start, end, label) {
	console.log(start.toISOString(), end.toISOString(), label);
});

capturarDatosInicialesDelFormulario();

$.validator.addMethod("regxUsername", function(value, element) {          
    return this.optional(element) || /^[A-Za-z0-9]+(?:[_-][A-Za-z0-9]+)*$/.test(value);
}, "Por favor usa letras (a-z), números o guion");

var reglasValidacion = {
				'username': {
					required: true, 
					maxlength: 20, 
					minlength: 5,
		            remote: {
		            	url: "/mantenimiento/usuario/exists/username",
		            	type: "GET",
		            	data: {
		            		id: function(){
		            			var idRegistro = $("#id").val();
		            			if(idRegistro == null){
		            				idRegistro = 0;
		            			}
		            			return idRegistro;
		            		}
		            	}
		            },
		            regxUsername: true
				},
				'password': {required: true, maxlength: 20, minlength: 5}
};

var mensajesValidacion = {
		'username': {
				required: "Este valor es requerido",
				maxlength: $.validator.format("Please, at least {0} characters are necessary"),
				minlength: $.validator.format("Please, at least {0} characters are necessary"),
				remote: "El nombre de usuario ya existe!!",
				regxUsername: "Por favor usa letras (a-z), números o guion"
		},
		'password': {
				required: "Este valor es requerido",
				maxlength: $.validator.format("Please, at least {0} characters are necessary"),
				minlength: $.validator.format("Please, at least {0} characters are necessary")
		}
};

aplicarReglasDeValidacionFormulario(reglasValidacion, mensajesValidacion);

$("#botonRegistrar").click(function() {
	enviarFormulario("mantenimiento/usuario/guardar");
});

$("#botonActualizar").click(function() {
	enviarFormulario("mantenimiento/usuario/actualizar");
});

$("#password").change(function () {
	$("#updatePassword").prop('checked', true);
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
						cargarDivContenidoPrincipal("mantenimiento/usuario/ver?id=" + retorno['id'])
					}
				}
			})
		}


	}else{
		mostrarNotificacionNingunCambioFormulario();
	}
}

$("#btnCrearRegistro").click(function() {
	validarCambiosFormularioAlCargarContenidoPrincipal("mantenimiento/usuario/ver");
});

$("#botonAtras").click(function() {
	validarCambiosFormularioAlCargarContenidoPrincipal("mantenimiento/usuario/listar");
});

function btnEliminarRegistro(){
	if(mensajeDeConfirmacion("Esta seguro que quiere eliminar este registro?")){
	    eliminarRegistros("mantenimiento/usuario/eliminar?ids=" + [$("#id").val()], 
	    		"mantenimiento/usuario/listar");
	}
}

$("#btnDesbloquearUsuario").click(function(){
	$.get(baseURL + "mantenimiento/usuario/desbloquearUsuario?ids=" + [$("#id").val()], 
		function(retorno){
			if(retorno['notificacion'] != null){
				new PNotify(retorno['notificacion']);
			}
				
			if(eval(retorno['estado']) == true){
				cargarDivContenidoPrincipal("mantenimiento/usuario/ver?id=" + retorno['id']);
			}
	});
});	

function showDivFechaExpiracionUsuario(){
	if(getValueBooleanButtonByName("expirarUsuario")){
		$('#divFechaExpiracionUsuario').show();
	}else{
		$('#divFechaExpiracionUsuario').hide();
	}
}

showDivFechaExpiracionUsuario();

$('input[type=radio][name=expirarUsuario]').change(function() {
	showDivFechaExpiracionUsuario();
});

function btnEliminarUsuarioRol(idUsuarioRol, idUsuario){
	if(mensajeDeConfirmacion("Esta seguro que quiere eliminar este rol?")){
	    eliminarRegistros("mantenimiento/usuariorol/eliminar?ids=" + [idUsuarioRol], 
	    		"mantenimiento/usuario/ver?id=" + idUsuario);
	}
}

function abrirFormularioAgregarUsuarioRol(idUsuario){
	cargarDivContenidoPrincipal("mantenimiento/usuariorol/ver?idUsuario=" + idUsuario);
}

$('#tablaListaUsuarioRol tr td:not(:last-child)').click(function () {
    var idUsuarioRol = $(this).closest('tr').find('td:eq(0) input').val();
    cargarDivContenidoPrincipal("mantenimiento/usuariorol/ver?id=" + idUsuarioRol + "&idUsuario=" + $("#id").val());
});
