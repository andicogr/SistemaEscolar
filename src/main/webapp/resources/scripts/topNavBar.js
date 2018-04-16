
function limpiarModal(sizeModal){
	$("#" + sizeModal + "ModalTitulo").html("");
	$("#" + sizeModal + "ModalContenido").html("");
}

$("#btnCloseSmallModal").click(function(){
	limpiarModal("small");
});

function abrirConfiguracionDeUsuarioEnModal(){
	$.get(baseURL + "configuracionUsuario/verCambiarRol", function(respuesta) {
		$("#smallModal").html(respuesta);
	});
	$("#smallModal").modal('show');
}


function openAlert(alertId){
	
	$("#contenidoPrincipal").html("Cargando . . .");
	$.get(baseURL + "alerts/viewAlert?id=" + alertId, function(respuesta) {
		$("#contenidoPrincipal").html(respuesta);
		loadAlertNotification();
	}).fail(function() {
		$("#contenidoPrincipal").html("No se ha podido visualizar esta pagina");
	});
	
}

function openAllAlerts(){

	$("#contenidoPrincipal").html("Cargando . . .");
	$.get(baseURL + "alerts/viewAlerts", function(respuesta) {
		$("#contenidoPrincipal").html(respuesta);
		loadAlertNotification();
	}).fail(function() {
		$("#contenidoPrincipal").html("No se ha podido visualizar esta pagina");
	});

}

function loadAlertNotification(){
	$.get(baseURL + "alerts/notifications", function(respuestaHTML){
		$("#alertNotificationLi").html(respuestaHTML);
	});
}