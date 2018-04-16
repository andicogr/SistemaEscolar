<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false">
	<i class="fa fa-envelope-o"></i>
	<c:if test="${cantidadAlertas > 0}">
		<span class="badge bg-green">${cantidadAlertas}</span>
	</c:if>
</a>
<ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">
	<c:forEach items="${alerts}" var="alert">
		<li onclick="openAlert(${alert.id})" <c:if test="${not alert.read}">style="background: #ffffa2"</c:if> >
			<a>
				<!-- span class="image"><img src="resources/images/img.jpg" alt="Profile Image" /></span-->
				<span>
					<span>${alert.from}</span>
					<c:if test="${not empty alert.showDate}">
						<span class="time">${alert.date}</span>
					</c:if>
					<c:if test="${not empty alert.showTime}">
						<span class="time" data-livestamp="${alert.timeSeconds}"></span>
					</c:if>
				</span>
				<span class="message">
					${alert.message}
				</span>
			</a>
		</li>
	</c:forEach>
	<li  onclick="openAllAlerts()">
		<div class="text-center">
			<a>
				<strong>Ver Todas las Alertas</strong>
				<i class="fa fa-angle-right"></i>
			</a>
		</div>
	</li>
</ul>

