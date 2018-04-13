
function abrir(ruta) {
		window.open(ruta,"abrir","scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,resizable=yes,top=60,left=100");
}

$(function(){

	var baseURL = $("#baseURL").val();

	/* CORE DEL SISTEMA */
	abrirMenuPrincipal("companiaMenuListar", "mantenimiento/compania/listar");

	abrirMenuPrincipal("usuarioMenuListar", "mantenimiento/usuario/listar");

	abrirMenuPrincipal("rolMenuListar", "mantenimiento/rol/listar");

	abrirMenuPrincipal("ajustesDeConfiguracionMenu", "ajustesConfiguracion/ver");
	
	/* TRAMITE DOCUMENTARIO */

	abrirMenuPrincipal("registrarExpedienteMenu", "expediente/formulario");
	
});