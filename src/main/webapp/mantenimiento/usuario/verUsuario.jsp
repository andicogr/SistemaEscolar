<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<div class="page-title" id="contenidoTitulo">
	<div class="title_left">
		<c:if test="${empty nombreMostrar}">
			<h3 id="tituloPagina">Usuarios</h3>
		</c:if>
		<c:if test="${not empty nombreMostrar}">
			<h3 id="tituloPagina">Usuarios / ${nombreMostrar}</h3>
		</c:if>
		
	</div>
</div>

<div class="row">

	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<div class="row rowTopBotonera">
					<div class="col-md-4 col-sm-4 col-xs-4" >
						<sec:authorize access="hasAnyAuthority('PRIV_ADMIN','USUARIOS_CREAR')">
							<c:if test="${empty usuario}">
								<button id="botonRegistrar" type="button" class="btn btn-success">
									Registrar
								</button>
							</c:if>
						</sec:authorize>
						<sec:authorize access="hasAnyAuthority('PRIV_ADMIN','USUARIOS_EDITAR')">
							<c:if test="${not empty usuario}">
								<button id="botonActualizar" type="button" class="btn btn-success">
									Actualizar
								</button>
							</c:if>
						</sec:authorize>
						<sec:authorize access="hasAnyAuthority('PRIV_ADMIN','USUARIOS_CREAR')">
							<c:if test="${not empty usuario}">
								<button class="btn btn-default" id="btnCrearRegistro">Crear</button>
							</c:if>
						</sec:authorize>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-4 text-center">
						<c:if test="${not empty usuario}">
							<button class="btn btn-default" data-toggle="confirmation" id="btnImprimirRegistro">Imprimir</button>
							<sec:authorize access="hasAnyAuthority('PRIV_ADMIN', 'USUARIOS_DESBLOQUEAR', 'USUARIOS_ELIMINAR')">
			                    <div class="btn-group botonOpcionesMantenimiento">
			                    	<button data-toggle="dropdown" class="btn btn-default dropdown-toggle " type="button" aria-expanded="false">
			                    		Opciones 
			                    		<span class="caret"></span>
			                    	</button>
			                    	<ul role="menu" class="dropdown-menu">
			                    		<sec:authorize access="hasAnyAuthority('PRIV_ADMIN', 'USUARIOS_DESBLOQUEAR')">
				                      		<li>
				                      			<a href="#">Desbloquear Usuario</a>
				                      		</li>
				                      	</sec:authorize>
				                      	<sec:authorize access="hasAnyAuthority('PRIV_ADMIN', 'USUARIOS_ELIMINAR')">
				                      		<li class="divider"></li>
				                      		<li>
				                      			<a href="javascript:;" onclick="btnEliminarRegistro()">Eliminar</a>
				                      		</li>
				                      	</sec:authorize>
			                    	</ul>
			                    </div>
							</sec:authorize>
						</c:if>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-4 text-right">
						<button id="botonAtras" type="button" class="btn btn-success">Atras</button>
					</div>
				</div>
			</div>

            <div class="x_content">
            	<form id="formmularioMantenimiento" class="form-horizontal form-label-left">
            		<c:if test="${not empty usuario}">
            			<input type="hidden" id="id" name="id" value="${usuario.id}">
            		</c:if>

            		<input style="display:none" type="checkbox" name="updatePassword" id="updatePassword">

            		<div class="row">
						<div class="col-md-3 col-sm-3 col-xs-12 form-div-general">
							<label for="usuario">
								Usuario <span class="required">*</span>
	                        </label>
	
	                       	<input type="text" id="username" name="username" class="form-control" 
	                       		value="${usuario.username}" autocomplete="off">
						</div>

						<div class="col-md-3 col-sm-3 col-xs-12 form-div-general">
							<label for="clave">
								Clave <span class="required">*</span>
	                        </label>
							<input type="password" id="password" name="password" class="form-control"
								value="${usuario.password}" autocomplete="off">
						</div>

						<div class="col-md-3 col-sm-3 col-xs-12 form-div-general">

							<label>Estado</label>
							<div>
							  <div class="btn-group" data-toggle="buttons">
								<label class="btn btn-default <c:if test="${usuario.activo == true || empty usuario}">active</c:if>"
									data-toggle-class="btn-primary" data-toggle-passive-class="btn-default">
									<input type="radio" name="estado" value="true" 
									<c:if test="${usuario.activo == true || empty usuario}">checked</c:if>> &nbsp; Activo &nbsp;
								</label>
								<label class="btn btn-danger <c:if test="${usuario.activo == false}">active</c:if>" 
									data-toggle-class="btn-danger" data-toggle-passive-class="btn-default">
								<input type="radio" name="estado" value="false"
								  	<c:if test="${usuario.activo == false}">checked</c:if>> Inactivo
								</label>
							  </div>
							</div>

						</div>

						<div class="col-md-3 col-sm-3 col-xs-12 form-div-general">
						
							<label>Bloqueado</label>
							<div>
							  <div class="btn-group" data-toggle="buttons">
								<label class="btn btn-default <c:if test="${usuario.bloqueado == true}">active</c:if>"
									data-toggle-class="btn-primary" data-toggle-passive-class="btn-default">
									<input type="radio" name="bloqueado" value="true"
									<c:if test="${usuario.bloqueado == true}">checked</c:if>> &nbsp; Si &nbsp;
								</label>
								<label class="btn btn-danger <c:if test="${usuario.bloqueado == false || empty usuario}">active</c:if>" 
									data-toggle-class="btn-danger" data-toggle-passive-class="btn-default">
									<input type="radio" name="bloqueado" value="false"
									<c:if test="${usuario.bloqueado == false || empty usuario}">checked</c:if>> &nbsp; No &nbsp;
								</label>
							  </div>
							</div>

						</div>
            		</div>
            		
            		<div class="row">

						<div class="col-md-3 col-sm-3 col-xs-12 form-div-general">
						
							<label>¿Expirar Usuario?</label>
							<div>
							  <div class="btn-group" data-toggle="buttons">
								<label class="btn btn-default <c:if test="${usuario.expirarUsuario == true}">active</c:if>"
									data-toggle-class="btn-primary" data-toggle-passive-class="btn-default">
									<input type="radio" name="expirarUsuario" value="true"
									<c:if test="${usuario.expirarUsuario == true}">checked</c:if>> &nbsp; Si &nbsp;
								</label>
								<label class="btn btn-danger <c:if test="${usuario.expirarUsuario == false || empty usuario}">active</c:if>" 
									data-toggle-class="btn-danger" data-toggle-passive-class="btn-default">
									<input type="radio" name="expirarUsuario" value="false"
									<c:if test="${usuario.expirarUsuario == false || empty usuario}">checked</c:if>> &nbsp; No &nbsp;
								</label>
							  </div>
							</div>
						
						</div>

						<div class="col-md-3 col-sm-3 col-xs-12 form-div-general" id="divFechaExpiracionUsuario">

	                        <label for="fechaExpiracion">
								Fecha de Expiraci&oacute;n
	                        </label>
	                        <div>
	                        	<div class="control-group">
	                            	<div class="controls">
	                              		<div class="xdisplay_inputx has-feedback">
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

						<div class="col-md-3 col-sm-3 col-xs-12 form-div-general">
					
					
						
						</div>

						<div class="col-md-3 col-sm-3 col-xs-12 form-div-general">
						
						
						
						</div>
					</div>

					<br>

					<c:if test="${not empty usuario}">
						<sec:authorize access="hasAnyAuthority('PRIV_ADMIN', 'USUARIOS_VER_ROLES')">
		                    <div class="" role="tabpanel" data-example-id="togglable-tabs">
		                    	<sec:authorize access="hasAnyAuthority('PRIV_ADMIN', 'USUARIOS_VER_ROLES')">
			                    	<ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
			                        	<li role="presentation" class="active">
			                        		<a href="#tab_content1" id="tab-roles" role="tab" data-toggle="tab" aria-expanded="true">
			                        			Roles
			                        		</a>
			                        	</li>
			                      	</ul>
			                      	<div id="myTabContent" class="tab-content">
			                        	<div role="tabpanel" class="tab-pane fade active in" id="tab_content1" aria-labelledby="tab-roles">
			                          		<div class="panel-body">
			                          			<div class="row">
			                          				<div class="col-md-12 col-sm-12 col-xs-12">
			                          					<sec:authorize access="hasAnyAuthority('PRIV_ADMIN', 'USUARIOS_AGREGAR_ROLES')">
															<button type="button" class="btn btn-primary" 
																onclick="abrirFormularioAgregarUsuarioRol(${usuario.id})">
																Agregar
															</button>
														</sec:authorize>
							                            <table id="tablaListaUsuarioRol" class="table table-bordered tableSubDetalle">
							                              	<thead>
							                                	<tr>
							                                  		<th>Nombre Rol</th>
							                                  		<th>Estado</th>
							                                  		<th width="1 px"></th>
							                                	</tr>
							                              	</thead>
							                              	<tbody>
							                              		<c:forEach items="${usuario.roles}" var="usuarioRol">
								                             		<tr>
								                                		<td>
								                                			${usuarioRol.getNombreRol()}
								                                			<input type="hidden" value="${usuarioRol.id}">
								                                		</td>
								                                		<td>${usuarioRol.getEstadoUsuarioRol()}</td>
								                                		<td>
								                                			<sec:authorize access="hasAnyAuthority('PRIV_ADMIN', 'USUARIOS_ELIMINAR_ROLES')">
									                                			<a class="close-link eliminar-subRegistro"
									                                				href="#" onclick="btnEliminarUsuarioRol(${usuarioRol.id}, ${usuario.id})"
									                                			>
									                                				<i class="fa fa-trash"></i>
									                                			</a>
									                                		</sec:authorize>
								                                		</td>
								                              		</tr>
							                              		</c:forEach>
							                              	</tbody>
							                            </table>
			                            			</div>
			                            		</div>
			                          		</div>
			                        	</div>
			                      	</div>
		                      	</sec:authorize>
		                    </div>
						</sec:authorize>
					</c:if>
				</form>
			</div>
		</div>
	</div>

</div>       


<script src="resources/vendors/validate/jquery.validate.js"></script>
<script src="resources/vendors/validate/localization/messages_es_PE.js"></script>
<script src="resources/scripts/mantenimiento/usuario/verUsuario.js"></script>    