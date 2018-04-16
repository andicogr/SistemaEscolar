$("#botonAtras").click(function() {
	cargarDivContenidoPrincipal("alerts/viewAlerts");
});


$("#botonNoLeido").click(function() {
	
	var alertId = $("#alertId").val();
	$("#contenidoPrincipal").html("Cargando . . .");
	$.get(baseURL + "alerts/mark/unread?id=" + alertId, function(respuesta) {
		$("#contenidoPrincipal").html(respuesta);
		loadAlertNotification();
	}).fail(function() {
		$("#contenidoPrincipal").html("No se ha podido visualizar esta pagina");
	});

});