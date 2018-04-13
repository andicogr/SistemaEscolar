<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>            	


<div class="modal-dialog modal-sm">
	<div class="modal-content">
		<form id="formularioConfiguracionCambiarPassword">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
	      		</button>
	      		<h4 class="modal-title" id="smallModalTitulo">
	      			Configracion de Usuario
				</h4>
			</div>

			<div class="modal-body" id="smallModalContenido">

				<label for="passwordActual">
					Clave Actual:
				</label>
				<input type="password" name="passwordActual" class="form-control"/>
				
				<br>
				
				<label for="nuevoPassword">
					Nueva Clave:
				</label>
				<input type="password" name="nuevoPassword" id="nuevoPassword" class="form-control"/>
				
				<br>
				
				<label for="confirmarPassword">
					Confirmar Clave:
				</label>
				<input type="password" name="confirmarPassword" class="form-control"/>
				
				<br>
				
				<button type="button" id="btnVerCambiarRol" class="btn btn-xs btn-primary">Cambiar Rol</button>

			</div>

			<div class="modal-footer">
				
	      		<button type="button" id="btnCloseSmallModal" class="btn btn-default" data-dismiss="modal">Cerrar</button>
	      		<button type="button" id="btnCambiarPassword" class="btn btn-primary">Aplicar</button>
	    	</div>
		</form>
	</div>
</div>

<script src="resources/vendors/validate/jquery.validate.js"></script>
<script src="resources/scripts/configuracionUsuario/cambiarPassword.js"></script>
