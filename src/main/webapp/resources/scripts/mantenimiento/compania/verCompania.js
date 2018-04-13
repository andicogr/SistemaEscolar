deshabilitarKeyPress();

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('[name="imgLogo"]').attr('src', e.target.result).width(177).height(177);
            $('[name="imgLogo"]').attr('title', input.files[0].name);
            $("#guardarImagen").prop('checked', true);
        };

        reader.readAsDataURL(input.files[0]);
    }
}

$('[name="imgLogo"]').click(function(){
    $('[name="logo"]').click();
});

$("#eliminarImagen").click(function(){
	$.get(baseURL + "/imagenes/obtenerDefaultImagenPath", function(defaultPath){
		$('[name="imgLogo"]').attr('src', defaultPath).width(177).height(177);
	});
	$("[name='logo']").val("");
	$("[name='archivo.id']").val("");
	$("#guardarImagen").prop('checked', true);
});

capturarDatosInicialesDelFormulario();

var reglasValidacion = {
				'razonSocial': {required: true, maxlength: 200},
				'ruc': {required: true, maxlength: 11, minlength: 11},
				'direccion': {required: true, maxlength: 200},
				'telefono': {number: true, maxlength: 15},
				'correoContacto': {email: true}
			};

var mensajesValidacion = {
		'razonSocial': {
				//required: "Este valor es requerido",
				maxlength: $.validator.format("Please, at least {0} characters are necessary")
		},
		'ruc': {
				//required: "Este valor es requerido",
				maxlength: $.validator.format("Please, at least {0} characters are necessary"),
				minlength: $.validator.format("Please, at least {0} characters are necessary")
		},
		'direccion': {
				//required: "Este valor es requerido",
				maxlength: $.validator.format("Please, at least {0} characters are necessary")  
		 },
	  	'telefono': {
	  			//required: "Este valor es requerido",
	  			maxlength: $.validator.format("Please, at least {0} characters are necessary")
	  	}
};

aplicarReglasDeValidacionFormulario(reglasValidacion, mensajesValidacion);

$("#botonRegistrar").click(function() {
	enviarFormulario("mantenimiento/compania/guardar");
});

$("#botonActualizar").click(function() {
	enviarFormulario("mantenimiento/compania/actualizar");
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
						cargarDivContenidoPrincipal("mantenimiento/compania/ver?id=" + retorno['id'])
					}
				}
			})
		}

	}else{
		mostrarNotificacionNingunCambioFormulario();
	}
}

$("#btnCrearRegistro").click(function() {
	validarCambiosFormularioAlCargarContenidoPrincipal("mantenimiento/compania/ver");
});

$("#botonAtras").click(function() {
	validarCambiosFormularioAlCargarContenidoPrincipal("mantenimiento/compania/listar");
});	

function btnEliminarRegistro(){
	if(mensajeDeConfirmacion("Esta seguro que  quiere eliminar este registro?")){
	    eliminarRegistros("mantenimiento/compania/eliminar?ids="+ [$("#id").val()], 
	    		"mantenimiento/compania/listar");
	}
}

$("#btnImprimirRegistro").click(function(){
	console.log($("#id").val());
});	



init_validator();
