//Eliminar archivo general.js se debe divir las responsabilidades agrupando los metodos en varios archivos

baseURL = $("#baseURL").val();

DataTableObject = null;
filtrosDeBusqueda = [];

function inicializarVariablesPorMenu(){
	DataTableObject = null;
	filtrosDeBusqueda = [];
}

function deshabilitarKeyPress(){
	$('#formmularioMantenimiento').on('keyup keypress', function(e) {
		  var keyCode = e.keyCode || e.which;
		  if (keyCode === 13) { 
		    e.preventDefault();
		    return false;
		  }
		});
}

function getValueBooleanButtonById(idBtnGroup){
	return eval($('input[id=' + idBtnGroup + ']:checked').val());
}

function getValueBooleanButtonByName(nameBtnGroup){
	return eval($('input[name=' + nameBtnGroup + ']:checked').val());
}

function getValueBooleanButton(inputArray){
	for (var i = 0, length = inputArray.length; i < length; i++){
		if(inputArray[i].checked){
			return radios[i].value;
		}
	}
}

function abrirMenuPrincipal(idMenu, urlContenido){

	$("#" + idMenu).click(function() {
		inicializarVariablesPorMenu();
		
		$("#contenidoPrincipal").html("Cargando . . .");
		$.get(baseURL + urlContenido, function(contenido) {
			$("#contenidoPrincipal").html(contenido);

		}).fail(function() {
			$("#contenidoPrincipal").html("No se ha podido visualizar esta pagina");
		});
	});
}

function cargarDivContenidoPrincipal(urlContenido){
	$("#contenidoPrincipal").html("Cargando . . .");
	$.get(baseURL + urlContenido, function(respuesta) {
		$("#contenidoPrincipal").html(respuesta);
	}).fail(function() {
		$("#contenidoPrincipal").html("No se ha podido visualizar esta pagina");
	});
}

function mensajeDeAlertaPersonalizado(mensaje){
	alert(mensaje);
}

function mensajeDeConfirmacion(mensaje){
	return confirm(mensaje);
}

function capturarDatosInicialesDelFormulario(){
	$form = $('#formmularioMantenimiento');
	origForm = $form.serialize();
}

function seRealizaronCambiosEnElFormulario(){
	return ($form.serialize() !== origForm);
}

function capturarValoresInicialesDelFormulario(){
	$form.find(':input').each(function(i, elem) {
      $(this).data("previous-value", $(this).val());
    });
}

function restaurarValoresInicialesDelFormulario() {
	$form.find(':input').each(function(i, elem) {
        $(this).val($(this).data("previous-value"));
    });
	$form.parsley().validate();
}

function mostrarNotificacionNingunCambioFormulario(){
	new PNotify({
	    title: 'Informacion',
	    text: 'No se realizo ningun cambio en el formulario',
	    type: 'info',
	    styling: 'bootstrap3'
	});
	/*new PNotify({
	    title: 'Regular Success',
	    text: 'That thing that you were trying to do worked!',
	    type: 'success',
	    styling: 'bootstrap3'
	});*/
}

function validarCambiosFormularioAlCargarContenidoPrincipal(url){

	var descartarCambios = true;
	if(seRealizaronCambiosEnElFormulario()){
		if(!mensajeDeConfirmacion("Desea descartar los cambios?")){
			descartarCambios = false;
		}
	}

	if(descartarCambios){
		cargarDivContenidoPrincipal(url);
	}
}

function eliminarRegistros(urlEliminar, urlContenido){
    $.post(baseURL + urlEliminar,function(retorno) {

		if(retorno['notificacion'] != null){
			new PNotify(retorno['notificacion']);
		}

		if(eval(retorno['estado']) == true){
			cargarDivContenidoPrincipal(urlContenido)
		}

    }).fail(function() {
  		$("#contenidoPrincipal").html("No se ha podido visualizar esta pagina");
  	});
}

function filtrarDataTable(urlData){
	DataTableObject.ajax.url(urlData).load();
}

/**
 * Autor: Andres Gonzales - 29-06-2017
 * cargarConfiguracionDataTable convierte una tabla en DataTable JS, activa el atributo click de cada fila para abrir
 * un formulario con el contenido de cada fila, y desactiva este atributo para la primera columna, donde estan los
 * checkbox para seleccionar una o mas filas y eliminar o realizar otras acciones en los registros.
 * @param dataTableId - Id de la tabla en la vista
 * @param urlData - Url para obtener los datos Json del controlar y cargarlos en la tabla
 * @param aoColumns - Colummnas de la tabla 
 * @param urlVerRegistro - Url para abrir el conteindo de la fila al hacer clic en ella, con el parametro id < ?id= >
 */
function cargarConfiguracionDataTable(dataTableId, urlData, aoColumns, urlVerRegistro){


	DataTableObject = $('#' + dataTableId).DataTable({
	    columnDefs: [ {
	        orderable: false,
	        className: 'select-checkbox',
	        targets:   0
	    } ],
	    select: {
	        style:    'os',
	        selector: 'td:first-child'
	    },
	    "order": [[ 1, "asc" ]],
	    "bProcessing": true,
	    "bServerSide": true,
	    "sAjaxSource": baseURL + urlData,
        "fnServerParams": function ( aoData ) {
        	for (var i = 0; i < filtrosDeBusqueda.length; i++) {
        		aoData.push( filtrosDeBusqueda[i] );
			}
        },
	    "bFilter" : false,
	    "aoColumns" : aoColumns,
	    "fnDrawCallback": function(){
	    	$('#' + dataTableId + ' tr td:not(:first-child)').click(function () {
	    	    var id = $(this).closest('tr').find('td:eq(0) input').val();
	    	    cargarDivContenidoPrincipal(urlVerRegistro + id);
	    	});

	    	$( "input[name=checkBoxRow]" ).on( "click", function(){
	    		if($('#' + dataTableId + ' :checked').length > 0){
	    	    	$("#botoneraCentro").show();
	    	    }else{
	    	    	$("#botoneraCentro").hide();
	    	    }
	    	});
	    	
	    	$( "#dataTableLista_length" ).on('change', function() {
	    		$("#checkBoxAll").prop('checked',false);
	    	});
	    },

		"sPaginationType" : "full_numbers",
		"iDisplayLength": 30,
		"language" : {
			"paginate" : {
				"sPrevious" : "<",
				"sNext" : ">",
				"sLast" : ">|",
				"sFirst" : "|<"
			},

			"lengthMenu" : 'Mostrar <select name="dataTableLista_length" id="dataTableLista_length" aria-controls="dataTableLista" class="form-control input-md dataTableLengthMenu">'
				+ '<option value="30">30</option>'
				+ '<option value="80">80</option>'
				+ '<option value="200">200</option>'
				+ '<option value="500">500</option>'
				+ '<option value="-1">Todos</option>'
				+ '</select> registros',

			"info" : "Mostrando _START_ a _END_ (Total: _TOTAL_ resultados)",

			"infoFiltered" : " - filtrados de _MAX_ registros",

			"infoEmpty" : "No hay resultados de busqueda",

			"zeroRecords" : "No hay registros a mostrar",

			"processing" : "Espere, por favor...",

			"search" : "Buscar:"
		}
	});	

}

function seleccionarTodosLosCehckBoxDeDataTable(){
	if($("#checkBoxAll").is(":checked")){
		$(":checkbox[name='checkBoxRow']").prop('checked', true);
		$("#botoneraCentro").show();
	}else{
		$(":checkbox[name='checkBoxRow']").prop('checked', false);
		$("#botoneraCentro").hide();
	}
}

function obtenerCheckBoxSeleccionadosDeDataTable(){
	var selectedIds = [];

    $('input[name=checkBoxRow]:checked').each(function(){
    	selectedIds.push(parseInt(this.value));
    });
    
    return selectedIds;
}

function eliminarRegistrosSeleccionadosDeDataTable(dataTableId, urlEliminar, urlContenido){
	var mensaje = "Esta seguro que quiere eliminar este registro?";
	if($('#' + dataTableId + ' :checked').length > 1){
		mensaje = "Esta seguro que quiere eliminar estos registros?";
	}
	if(mensajeDeConfirmacion(mensaje)){
		var selectedIds = obtenerCheckBoxSeleccionadosDeDataTable();

	    eliminarRegistros(urlEliminar + selectedIds, urlContenido);
	}
}

function aplicarReglasDeValidacionFormulario(reglasValidacion, mensajesValidacion){
	$form.validate({
		onkeyup: function (element) {
	         this.element(element);
		},
		rules : reglasValidacion,
		messages : mensajesValidacion
	});
}
