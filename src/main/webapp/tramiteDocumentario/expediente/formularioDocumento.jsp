<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>            	


<div class="modal-dialog modal-lg">
	<div class="modal-content">
		<form id="formularioConfiguracionUsuario" class="form-horizontal form-label-left" method="post" action="<c:url value = "documento/registrarDocumento"/>">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="largeModalTitulo">
					<c:if test="${empty documento}">
						Documento - Borrador
					</c:if>
					<c:if test="${not empty documento}">
						Documento - ${documento.numeroDocumento}
					</c:if>
				</h4>
    		</div>
    		<div class="modal-body" id="largeModalContenido">
				<input type="hidden" id="expedienteId" name="expediente.id" value="${expedienteId}"/>

				<div class="item form-group">
					<label class="control-label col-md-3 col-sm-3 col-xs-12" for="titulo">
						Titulo <span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                    	<input type="text" id="titulo" name="titulo" class="form-control col-md-7 col-xs-12" 
                    		value="${documento.titulo}">
                    </div>
				</div>
				
                <div class="item form-group">
	            	<label class="control-label col-md-2 col-sm-2 col-xs-12" for="activo">
						Fecha de Expiracion
	                </label>
		            <div class="col-md-3 col-sm-3 col-xs-3">
			            <div class="control-group">
			            	<div class="controls">
			                	<div class="col-md-11 xdisplay_inputx form-group has-feedback">
			                    	<input type="text" class="form-control has-feedback-left" id="fechaExpiracionUsuario" 
			                        	name="fechaExpiracionUsuario" placeholder="--/--/----" aria-describedby="inputSuccess2Status2"
			                       		value="${usuario.getFechaExpiracionUsuarioConFormato()}">
			                        	<span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
			                        	<span id="inputSuccess2Status2" class="sr-only">(success)</span>
			                	</div>
							</div>
						</div>
		            </div>
                </div>
                        
				<div class="item form-group">
					<label class="control-label col-md-3 col-sm-3 col-xs-12" for="descripcion">
						Descripci&oacute;n
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                    	<textarea id="descripcion" class="form-control" name="descripcion">${documento.descripcion}</textarea>
                    </div>
				</div>
				
				<div class="item form-group">
					<label class="control-label col-md-3 col-sm-3 col-xs-12" for="observacion">
						Observaci&oacute;n
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                    	<textarea id="observacion" class="form-control" name="observacion">${documento.observacion}</textarea>
                    </div>
				</div>
    		</div>
    		<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary">Save changes</button>
			</div>
		</form>
	</div>
</div>


<script src="resources/scripts/tramiteDocumentario/expediente/formularioDocumento.js"></script>