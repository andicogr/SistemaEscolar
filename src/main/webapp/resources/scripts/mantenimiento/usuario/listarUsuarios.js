
var aoColumns = [ 
                 { "width": "1px" },
                 { sClass: "center" },
                 { sClass: "center" },
                 { sClass: "center" },
                 { sClass: "center" },
                 { sClass: "center" },
             ];

cargarConfiguracionDataTable("dataTableLista", "mantenimiento/usuario/listaJson", aoColumns, "mantenimiento/usuario/ver?id=");

$("#btnCrearRegistro").click(function() {
	cargarDivContenidoPrincipal("mantenimiento/usuario/ver");
});

function btnEliminarRegistro(){
	eliminarRegistrosSeleccionadosDeDataTable("dataTableLista", "mantenimiento/usuario/eliminar?ids=", "mantenimiento/usuario/listar");
}

$("#btnDesbloquearUsuario").click(function(){
    var selectedIds = obtenerCheckBoxSeleccionadosDeDataTable();
    console.log(selectedIds);
	$.get(baseURL + "mantenimiento/usuario/desbloquearUsuario?ids=" + selectedIds, 
			function(retorno){
				if(retorno['notificacion'] != null){
					new PNotify(retorno['notificacion']);
				}
					
				if(eval(retorno['estado']) == true){
					cargarDivContenidoPrincipal("mantenimiento/usuario/listar");
				}
		});
});


$("#btnBuscarRegistros").click(function(){

	filtrosDeBusqueda = [];
	var filtroUsername = $("#filtroUsername").val();
	var filtroEstado = $("#filtroEstado").val();

	if(filtroUsername != null){
		filtrosDeBusqueda.push({"name": "username", "value": filtroUsername})
	}

	if(filtroEstado != null && filtroEstado != ""){
		filtrosDeBusqueda.push({"name": "estado", "value": filtroEstado})
	}

	filtrarDataTable("mantenimiento/usuario/listaJson");
});
