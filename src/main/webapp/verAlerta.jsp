<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="page-title" id="contenidoTitulo">
	<div class="title_left">
		<h3 id="tituloPagina">Alertas del Sistema</h3>
	</div>
</div>

<div class="row">
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
			
				<div class="row rowTopBotonera">
					<div class="col-md-4 col-sm-4 col-xs-4" >
						<c:if test="${alert.read}">
							<button id="botonNoLeido" type="button" class="btn btn-default">Marcar como no leido</button>
						</c:if>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-4 text-center">
					</div>
					<div class="col-md-4 col-sm-4 col-xs-4 text-right">
						<button id="botonAtras" type="button" class="btn btn-success">Atras</button>
					</div>
				</div>
			
			</div>
			<div class="x_content">
				<input type="hidden" id="alertId" value="${alert.id}"/>
				<div class="inbox-body">
					<div class="mail_heading row">
						<div class="col-md-8">
							<p>
								<b>De:</b> 
								<c:if test="${empty alert.fromUsuario}">
									Sistema
								</c:if>
								<c:if test="${not empty alert.fromUsuario}">
									${alert.fromUsuario.username}
								</c:if>
							</p>
						</div>
						<div class="col-md-4 text-right">
							<p class="date"> <fmt:formatDate pattern = "dd-MM-yyyy HH:mm:ss" value = "${alert.date}" /></p>
						</div>
						<!-- div class="col-md-12">
 							<h4> </h4>
						</div-->
					</div>
					<hr>
					<div class="view-mail">
                    	${alert.message}
					</div>

				</div>

			</div>
		</div>
	</div>
</div>

<script src="resources/scripts/verAlerta.js"></script>   
