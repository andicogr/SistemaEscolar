
function abrirFormularioDocumento(idExpediente, idDocumento){
	
	$.get("expediente/formularioDocumento", function(respuesta){
		$("#largeModal").html(respuesta);
		$("#largeModal").modal('show');
	});
	
}
