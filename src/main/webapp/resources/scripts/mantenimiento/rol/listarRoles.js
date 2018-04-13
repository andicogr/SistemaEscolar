
var aoColumns = [ 
                 { "width": "1px" },
                 { sClass: "center"},
                 { sClass: "center"},
             ];

cargarConfiguracionDataTable("dataTableLista", "mantenimiento/rol/listaJson", aoColumns, "mantenimiento/rol/ver?id=");

//$("#paginacionDiv").append($(".dataTables_paginate"));

$("#btnCrearRegistro").click(function() {
	cargarDivContenidoPrincipal("mantenimiento/rol/ver");
});

function btnEliminarRegistro(){
	eliminarRegistrosSeleccionadosDeDataTable("dataTableLista", "mantenimiento/rol/eliminar?ids=", "mantenimiento/rol/listar");
}

$("#btnImprimirRegistro").click(function(){
    var selectedIds = obtenerCheckBoxSeleccionadosDeDataTable();
    console.log(selectedIds);
});

$("#btnBuscarRegistros").click(function(){

	filtrosDeBusqueda = [];
	var filtroNombre = $("#filtroNombre").val();
	var filtroEstado = $("#filtroEstado").val();

	if(filtroNombre != null){
		filtrosDeBusqueda.push({"name": "nombre", "value": filtroNombre})
	}

	if(filtroEstado != null && filtroEstado != ""){
		filtrosDeBusqueda.push({"name": "estado", "value": filtroEstado})
	}

	filtrarDataTable("mantenimiento/rol/listaJson");
});


