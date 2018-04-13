<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<div class="page-title" id="contenidoTitulo">
	<div class="title_left">
		<c:if test="${empty nombreMostrar}">
			<h3 id="tituloPagina">Expediente - Borrador</h3>
		</c:if>
		<c:if test="${not empty nombreMostrar}">
			<h3 id="tituloPagina">Expediente / ${nombreMostrar}</h3>
		</c:if>
		
	</div>
</div>


<div class="row">

	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<div class="row rowTopBotonera">
					<div class="col-md-4 col-sm-4 col-xs-4" >
						<sec:authorize access="hasAnyRole('PRIVILEGIO_ADMIN','SUB_MENU_EXPEDIENTE_CREAR')">
							<c:if test="${empty expediente}">
								<button id="botonRegistrar" type="button" class="btn btn-success">
									Registrar
								</button>
							</c:if>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('PRIVILEGIO_ADMIN','SUB_MENU_EXPEDIENTE_EDITAR')">
							<c:if test="${not empty expediente}">
								<button id="botonActualizar" type="button" class="btn btn-success">
									Actualizar
								</button>
							</c:if>
						</sec:authorize>
						<sec:authorize access="hasAnyRole('PRIVILEGIO_ADMIN','SUB_MENU_EXPEDIENTE_CREAR')">
							<c:if test="${not empty expediente}">
								<button class="btn btn-default" id="btnCrearRegistro">Crear</button>
							</c:if>
						</sec:authorize>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-4 text-center">
						<c:if test="${not empty expediente}">
							<button class="btn btn-default" id="btnImprimirRegistro">Imprimir</button>
		                    <div class="btn-group botonOpcionesMantenimiento">
		                    	<button data-toggle="dropdown" class="btn btn-default dropdown-toggle " type="button" aria-expanded="false">
		                    		Opciones 
		                    		<span class="caret"></span>
		                    	</button>
		                    	<ul role="menu" class="dropdown-menu">
		                      		<sec:authorize access="hasAnyRole('PRIVILEGIO_ADMIN','SUB_MENU_EXPEDIENTE_ELIMINAR')">
			                      		<li>
			                      			<a href="javascript:;" onclick="btnEliminarRegistro()">Eliminar</a>
			                      		</li>
			                      	</sec:authorize>
		                    	</ul>
		                    </div>
						</c:if>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-4 text-right">
						<button id="botonAtras" type="button" class="btn btn-success">Atras</button>
					</div>
				</div>
			</div>

            <div class="x_content">
            	<form id="formmularioMantenimiento" class="form-horizontal form-label-left">
            		<c:if test="${not empty expediente}">
            			<input type="hidden" id="id" name="id" value="${expediente.id}">
            		</c:if>

					<div class="item form-group">
						<label class="control-label col-md-3 col-sm-3 col-xs-12" for="titulo">
							Titulo <span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                        	<input type="text" id="titulo" name="titulo" class="form-control col-md-7 col-xs-12" 
                        		value="${expediente.titulo}">
                        </div>
					</div>
					<div class="item form-group">
						<label class="control-label col-md-3 col-sm-3 col-xs-12" for="observacion">
							Observaci&oacute;n
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                        	<textarea id="observacion" class="form-control" name="observacion">${expediente.observacion}</textarea>
                        </div>
					</div>
				</form>
			</div>
		</div>
	</div>

</div> 

<div class="row">
	<div class="col-md-6 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<div class="row">
					<div class="col-md-8 col-xs-8">
						<h2>Documentos</h2>
					</div>
					<div class="col-md-4 col-xs-4 text-right">
						<button id="botonRegistrar1" onclick="abrirFormularioDocumento(${1})" type="button" class="btn btn-success">
							Agregar
						</button>
					</div>
				</div>
			</div>
			 <div class="x_content">
	             <table id="tablaListaDocumentos" class="table table-bordered tableSubDetalle">
	               	<thead>
	                 	<tr>
	                   		<th>Titulo</th>
	                   		<th>N&uacute;mero</th>
	                   		<th width="1 px"></th>
	                 	</tr>
	               	</thead>
	               	<tbody>
						<tr>
							<td>Documento de firmas</td>
							<td>265-565</td>
							<td>
								<a class="close-link eliminar-subRegistro"
								href="#" onclick="btnEliminarUsuarioRol(${usuarioRol.id}, ${usuario.id})"
								>
									<i class="fa fa-trash"></i>
								</a>
							</td>
						</tr>
	               	</tbody>
	             </table>
			 </div>
		</div>
	</div>
	
	<div class="col-md-6 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<div class="row">
					<div class="col-md-8 col-xs-8">
						<h2>Archivos por Documento</h2>
					</div>
					<div class="col-md-4 col-xs-4 text-right">
						<button id="botonRegistrar1" type="button" class="btn btn-success">
							Agregar
						</button>
					</div>
				</div>
			</div>
			 <div class="x_content">
	             <table id="tablaListaArchivos" class="table table-bordered tableSubDetalle">
	               	<thead>
	                 	<tr>
	                   		<th>Archivo</th>
	                   		<th width="1 px"></th>
	                 	</tr>
	               	</thead>
	               	<tbody>
						<tr>
							<td>archivo.pdf</td>
							<td>
								<a class="close-link eliminar-subRegistro"
								href="#" onclick="btnEliminarUsuarioRol(${usuarioRol.id}, ${usuario.id})"
								>
									<i class="fa fa-trash"></i>
								</a>
							</td>
						</tr>
	               	</tbody>
	             </table>
			 </div>
		</div>
	</div>
</div>      


<script src="resources/vendors/validate/jquery.validate.js"></script>
<script src="resources/vendors/validate/localization/messages_es_PE.js"></script>
<script src="resources/scripts/tramiteDocumentario/expediente/formularioExpediente.js"></script>
<script type="text/javascript">
$('#tablaListaDocumentos tr td:not(:last-child)').click(function() {
	console.log("clic");
	 $('#tablaListaDocumentos tr').removeClass('filaSeleccionada');
	 $(this).toggleClass('filaSeleccionada'); 
}).dblclick(function() {
	console.log("DOS clics");
});
</script>

