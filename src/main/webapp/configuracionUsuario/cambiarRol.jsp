<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>            	


<div class="modal-dialog modal-sm">
	<div class="modal-content">
		<form id="formularioConfiguracionCambiarRol" method="post" action="<c:url value = "/"/>">
			<input type="checkbox" name="actualizar" checked="checked" style="display: none;"/>
			<div class="modal-header">
				<button type="button" id="btnCloseSmallModal" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
	      		</button>
	      		<h4 class="modal-title" id="smallModalTitulo">
	      			Configracion de Usuario
				</h4>
			</div>

			<div class="modal-body" id="smallModalContenido">

				<label for="idRol">
					Rol:
				</label>
				<select name="idRol" id="idRol" class="form-control" onchange="cambiarRol()">
					<c:forEach var="rol" items="${listaRolesActivos}">
						
						<option value="${rol.id}" <c:if test="${rolActualId == rol.id}">selected="selected"</c:if> >
							${rol.nombre}
						</option>
					</c:forEach>
				</select>

				<div class="checkbox">
					<label>
						<input type="checkbox" name="porDefecto" class="flat"> Por Defecto
					</label>
				</div>
				
				<button type="button" id="btnVerCambiarPassword" class="btn btn-xs btn-primary">Cambiar Contrase&ntilde;a</button>

			</div>

			<div class="modal-footer">
				
	      		<button type="button" id="btnCloseSmallModal" class="btn btn-default" data-dismiss="modal">Cerrar</button>
	      		<button type="submit" class="btn btn-primary">Aplicar</button>
	    	</div>
		</form>
	</div>
</div>

<script src="resources/scripts/configuracionUsuario/cambiarRol.js"></script>