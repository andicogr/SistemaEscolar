<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<div class="page-title" id="contenidoTitulo">
	<div class="title_left">
		<h3 id="tituloPagina">Alertas del Sistema</h3>
	</div>
</div>

<div class="row">
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="x_panel">
			<div class="x_title">
			</div>
			<div class="x_content">
                
				<ul class="list-unstyled msg_list full_width">
					<c:forEach items="${alertList}" var="alert">
						<li onclick="openAlert(${alert.id})" <c:if test="${not alert.read}">style="background: #ffffa2"</c:if>>
							<a>
								<!-- span class="image"><img src="resources/images/img.jpg" alt="Profile Image" /></span-->
								<span>
									<span>${alert.from}</span>
									<c:if test="${alert.showDate}">
										<span class="time">${alert.date}</span>
									</c:if>
									<c:if test="${alert.showTime}">
										<span class="time" data-livestamp="${alert.timeSeconds}"></span>
									</c:if>
								</span>
								<span class="message">
									${alert.message}
								</span>
							</a>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>
