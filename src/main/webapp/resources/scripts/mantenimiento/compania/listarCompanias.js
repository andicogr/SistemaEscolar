
var aoColumns = [ 
                 { "width": "1px" },
                 { sClass: "center"},
                 { sClass: "center"},
                 { sClass: "center"},
                 { sClass: "center"},
             ];

cargarConfiguracionDataTable("dataTableLista", "mantenimiento/compania/listaJson", aoColumns, "mantenimiento/compania/ver?id=");

//$("#paginacionDiv").append($(".dataTables_paginate"));

$("#btnCrearRegistro").click(function() {
	cargarDivContenidoPrincipal("mantenimiento/compania/ver");
});

function btnEliminarRegistro(){
	eliminarRegistrosSeleccionadosDeDataTable("dataTableLista", "mantenimiento/compania/eliminar?ids=", "mantenimiento/compania/listar");
}

$("#btnImprimirRegistro").click(function(){
    var selectedIds = obtenerCheckBoxSeleccionadosDeDataTable();
    console.log(selectedIds);
});



