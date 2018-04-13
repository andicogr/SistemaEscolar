
var aoColumns = [ 
                 { "width": "1px" },
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

$("#btnImprimirRegistro").click(function(){
    var selectedIds = obtenerCheckBoxSeleccionadosDeDataTable();
    console.log(selectedIds);
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
