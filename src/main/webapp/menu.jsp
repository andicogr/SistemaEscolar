<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
           
<div class="menu_section">
	<h3>General</h3>
	<sec:authorize access="hasAnyAuthority('PRIV_ADMIN', 'PRIV_USER')">
	  	<ul class="nav side-menu">
	  		<sec:authorize access="hasAnyAuthority('PRIV_ADMIN','MENU_USUARIOS')">
		    	<li>
		    		<a>
		    			<i class="fa fa-users"></i> Usuarios <span class="fa fa-chevron-down"></span>
		    		</a>
		    		<sec:authorize access="hasAnyAuthority('PRIV_ADMIN','USUARIOS_VER', 'USUARIOS_ROLES')">
			      		<ul class="nav child_menu">
			      			<sec:authorize access="hasAnyAuthority('PRIV_ADMIN','USUARIOS_VER')">
			      				<li><a href="#" id="usuarioMenuListar">Usuarios</a></li>
			      			</sec:authorize>
			        		<sec:authorize access="hasAnyAuthority('PRIV_ADMIN','USUARIOS_ROLES')">
					        	<li><a href="#" id="rolMenuListar">Roles</a></li>
			        		</sec:authorize>
			      		</ul>
			      	</sec:authorize>
		    	</li>
		    </sec:authorize>

		    <sec:authorize access="hasAnyAuthority('PRIV_ADMIN','MENU_CONFIGURACION')">
			<li>
				<a>
					<i class="fa fa-cogs"></i> Configuraci&oacute;n <span class="fa fa-chevron-down"></span>
				</a>
				<sec:authorize access="hasAnyAuthority('PRIV_ADMIN','MENU_CONFIGURACION')">
		      		<ul class="nav child_menu">
		      			<sec:authorize access="hasAnyAuthority('PRIV_ADMIN','MENU_CONFIGURACION_SUB_MENU_COMPANIA')">
		        			<li><a href="#" id="companiaMenuListar">Compañia</a></li>
		        		</sec:authorize>
		        		<sec:authorize access="hasAnyAuthority('PRIV_ADMIN', 'MENU_CONFIGURACION_SUB_MENU_AJUSTES_CONFIG')">
		        			<li><a href="#" id="ajustesDeConfiguracionMenu">Ajustes de Configuraci&oacute;n</a></li>
		        		</sec:authorize>
		      		</ul>
		      	</sec:authorize>
	   		</li>
	   		</sec:authorize>
	   		
		    <sec:authorize access="hasAnyAuthority('PRIV_ADMIN','MENU_TRAMITE_DOC')">
			<li>
				<a>
					<i class="fa fa-cogs"></i> Tramite Documentario <span class="fa fa-chevron-down"></span>
				</a>
				<sec:authorize access="hasAnyAuthority('PRIV_ADMIN','MENU_TRAMITE_DOC')">
		      		<ul class="nav child_menu">
		      			<sec:authorize access="hasAnyAuthority('PRIV_ADMIN','MENU_TRAMITE_DOC_SUB_MENU_EXPEDIENTE')">
		        			<li><a href="#" id="registrarExpedienteMenu">Expediente</a></li>
		        		</sec:authorize>
		      		</ul>
		      	</sec:authorize>
	   		</li>
	   		</sec:authorize>
	  	</ul>
  	</sec:authorize>
</div>