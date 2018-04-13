<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--@ page session="false" --%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Gentelella Alela! | </title>

    <!-- Bootstrap -->
    <link href="resources/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="resources/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="resources/vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- Animate.css -->
    <link href="resources/vendors/animate.css/animate.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="resources/build/css/custom.min.css" rel="stylesheet">
  </head>

  <body class="login">
    <div>
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>
      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form id="loginform" action="/login" method="post" target="_parent">
              <h1>Login Form</h1>
              <div>
                <input type="text" id="username" name="username" class="form-control" placeholder="Username" required=""/>
              </div>
              <div>
                <input type="password" id="password" name="password" class="form-control" placeholder="Password" required="" />
              </div>
              <div>
              	<button type="submit" class="btn btn-default submit">Log in</button>
                <a class="reset_pass" href="#">Lost your password?</a>
              </div>

				<p class="message" style="color:#F00">
			        <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
			        	<c:if test="${SPRING_SECURITY_LAST_EXCEPTION.message=='User is disabled'}">
				            ${fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'User is disabled', 'El usuario esta deshabilitado')}
				        </c:if>
				        <c:if test="${SPRING_SECURITY_LAST_EXCEPTION.message=='Bad credentials'}">
				            ${fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'Bad credentials', 'Usuario/Clave son incorrectos')}
				        </c:if>
				        <c:if test="${SPRING_SECURITY_LAST_EXCEPTION.message=='Maximum sessions of 1 for this principal exceeded'}">
				            ${fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'Maximum sessions of 1 for this principal exceeded', 'Esta cuenta ya esta siendo usada por alguien.')}
				        </c:if>
				        <c:if test="${SPRING_SECURITY_LAST_EXCEPTION.message=='User account has expired'}">
				            ${fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'User account has expired', 'La cuenta de usuario ha expirado.')}
				        </c:if>
				        <c:if test="${SPRING_SECURITY_LAST_EXCEPTION.message=='User credentials have expired'}">
				            ${fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'User credentials have expired', 'Las credenciales de usuario han caducado.')}
				        </c:if>
				        <c:if test="${SPRING_SECURITY_LAST_EXCEPTION.message=='User account is locked'}">
				            ${fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'User account is locked', 'La cuenta de usuario está bloqueada.')}
				        </c:if>
				        <c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>
			        </c:if>   
				</p>

              <div class="clearfix"></div>

              <div class="separator">
                <p class="change_link">New to site?
                  <a href="#signup" class="to_register"> Create Account </a>
                </p>

                <div class="clearfix"></div>
                <br />

                <div>
                  <h1><i class="fa fa-paw"></i> Gentelella Alela!</h1>
                  <p>©2016 All Rights Reserved. Gentelella Alela! is a Bootstrap 3 template. Privacy and Terms</p>
                </div>
              </div>
            </form>
          </section>
        </div>

        <div id="register" class="animate form registration_form">
          <section class="login_content">
            <form>
              <h1>Create Account</h1>
              <div>
                <input type="text" class="form-control" placeholder="Username" required="" />
              </div>
              <div>
                <input type="email" class="form-control" placeholder="Email" required="" />
              </div>
              <div>
                <input type="password" class="form-control" placeholder="Password" required="" />
              </div>
              <div>
                <a class="btn btn-default submit" href="index.html">Submit</a>
              </div>

              <div class="clearfix"></div>

              <div class="separator">
                <p class="change_link">Already a member ?
                  <a href="#signin" class="to_register"> Log in </a>
                </p>

                <div class="clearfix"></div>
                <br />

                <div>
                  <h1><i class="fa fa-paw"></i> Gentelella Alela!</h1>
                  <p>©2016 All Rights Reserved. Gentelella Alela! is a Bootstrap 3 template. Privacy and Terms</p>
                </div>
              </div>
            </form>
          </section>
        </div>
      </div>
    </div>
  </body>
</html>
