<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!-- Datatables CSS-->
<link href="resources/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
<link href="resources/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
<!-- Datatables CSS-->

<div class="page-title" id="contenidoTitulo">
	<div class="title_left">
		<h3 id="tituloPagina">Roles</h3>
	</div>
</div>

<div class="row">
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<h2>Filtros</h2>
				<ul class="nav navbar-right panel_toolbox">
					<li>
						<a class="collapse-link">
							<i class="fa fa-chevron-down"></i>
						</a>
					</li>
				</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content" style="display: none">

				<form id="demo-form2">
				
					<div class="row">
					
						<div class="col-md-3 col-sm-3 col-xs-12">

							<label for="filtroNombre">
								Nombre
                        	</label>

                          	<input type="text" id="filtroNombre" class="form-control">

						</div>

						<div class="col-md-3 col-sm-3 col-xs-12">
						
							<label for="filtroEstado">
								Estado
							</label>

							<select class="form-control" id="filtroEstado">
	                            <option value=""></option>
	                            <option value="activo">Activo</option>
	                            <option value="inactivo">Inactivo</option>
                         	</select>

						</div>

					</div>

					<div class="ln_solid"></div>
	
					<div class="form-group">
						<div class="col-md-12 col-sm-12 col-xs-12 text-right">
	                          <button id="btnBuscarRegistros" type="button" class="btn btn-success">Buscar</button>
						</div>
					</div>

				</form>
			</div>
		</div>
	</div>
</div>

<div class="row">

	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
				<div class="row rowTopBotonera">
					<div class="col-sm-8" >
						<div class="row">
							<div class="col-md-6 col-sm-6 col-xs-6" >
								<sec:authorize access="hasAnyAuthority('PRIV_ADMIN','SUB_MENU_ROL_CREAR')">
									<button class="btn btn-success" id="btnCrearRegistro">Crear</button>
								</sec:authorize>
							</div>
							<div class="col-md-6 col-sm-6 col-xs-6 text-center">
								<div id="botoneraCentro" style="display: none">
									<button class="btn btn-default" id="btnImprimirRegistro">Imprimir</button>
				                    <div class="btn-group botonOpcionesMantenimiento">
				                    	<button data-toggle="dropdown" class="btn btn-default dropdown-toggle " type="button" aria-expanded="false">
				                    		Opciones 
				                    		<span class="caret"></span>
				                    	</button>
				                    	<ul role="menu" class="dropdown-menu">
				                      		<sec:authorize access="hasAnyAuthority('PRIV_ADMIN','SUB_MENU_ROL_ELIMINAR')">
					                      		<li>
					                      			<a href="javascript:;" onclick="btnEliminarRegistro()">Eliminar</a>
					                      		</li>
					                      	</sec:authorize>
				                    	</ul>
				                    </div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-4 text-right" id="paginacionDiv">
					
					</div>
				</div>
			</div>
			<div class="x_content">
				<table id="dataTableLista" class="table table-striped table-bordered">
					<thead>
						<tr>
							<th><input type="checkbox" id="checkBoxAll" onclick="seleccionarTodosLosCehckBoxDeDataTable();" class="flat"/></th>
							<th>Nombre</th>
							<th>Estado</th>
                        </tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</div>
		</div>
	</div>

</div>



<!-- Data Tables JS-->
<script src="resources/vendors/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="resources/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<!-- Data Tables JS-->


<script src="resources/scripts/mantenimiento/rol/listarRoles.js"></script>    
<script src="resources/build/js/custom2.js"></script>