<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Gentelella Alela! | </title>

    <!-- Bootstrap -->
    <link href="<c:url value = "resources/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="resources/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="resources/vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- iCheck -->
    <link href="resources/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
	
    <!-- bootstrap-progressbar -->
    <link href="resources/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css" rel="stylesheet">
    <!-- JQVMap -->
    <link href="resources/vendors/jqvmap/dist/jqvmap.min.css" rel="stylesheet"/>
    <!-- bootstrap-daterangepicker -->
    <link href="resources/vendors/bootstrap-daterangepicker/daterangepicker.css" rel="stylesheet">
    
    <!-- PNotify -->
    <link href="resources/vendors/pnotify/dist/pnotify.css" rel="stylesheet">
    <link href="resources/vendors/pnotify/dist/pnotify.buttons.css" rel="stylesheet">
    <link href="resources/vendors/pnotify/dist/pnotify.nonblock.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="resources/build/css/custom.css" rel="stylesheet">
  </head>

  <body class="nav-md">
  	<input type="hidden" id="baseURL" value="<c:url value='/' />" />

    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
              <a href="index.html" class="site_title"><i class="fa fa-paw"></i> <span>Gentelella Alela!</span></a>
            </div>

            <div class="clearfix"></div>

            <!-- menu profile quick info -->
            <div class="profile clearfix">
              <div class="profile_pic">
                <img src="resources/images/img.jpg" alt="..." class="img-circle profile_img">
              </div>
              <div class="profile_info">
                <span>Welcome,</span>
                <h2>Andres Gonzales Rojas</h2>
              </div>
            </div>
            <!-- /menu profile quick info -->

            <br />

            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">

				<jsp:include page="menu.jsp"/>
				
            </div>
            <!-- /sidebar menu -->

            <!-- /menu footer buttons -->
            <div class="sidebar-footer hidden-small">
              <a data-toggle="tooltip" data-placement="top" title="Settings">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Lock">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Logout" href="logout">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
              </a>
            </div>
            <!-- /menu footer buttons -->
          </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">
			<jsp:include page="topNavBar.jsp"/>
        </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main" id="contenidoPrincipal">
			<jsp:include page="dashboard.jsp"/>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
          <div class="pull-right">
            Gentelella - Bootstrap Admin Template by <a href="https://colorlib.com">Colorlib</a>
          </div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
    </div>

	<jsp:include page="modals.jsp"/>
    <!-- jQuery -->
    <script src="resources/vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="resources/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="resources/vendors/bootstrap/dist/js/bootstrap-confirmation.js"></script>
    <!-- FastClick -->
    <script src="resources/vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="resources/vendors/nprogress/nprogress.js"></script>
    <!-- Chart.js -->
    <script src="resources/vendors/Chart.js/dist/Chart.min.js"></script>
    <!-- gauge.js -->
    <script src="resources/vendors/gauge.js/dist/gauge.min.js"></script>
    <!-- bootstrap-progressbar -->
    <script src="resources/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
    <!-- iCheck -->
    <script src="resources/vendors/iCheck/icheck.min.js"></script>
    <!-- Skycons -->
    <script src="resources/vendors/skycons/skycons.js"></script>
    <!-- Flot -->
    <script src="resources/vendors/Flot/jquery.flot.js"></script>
    <script src="resources/vendors/Flot/jquery.flot.pie.js"></script>
    <script src="resources/vendors/Flot/jquery.flot.time.js"></script>
    <script src="resources/vendors/Flot/jquery.flot.stack.js"></script>
    <script src="resources/vendors/Flot/jquery.flot.resize.js"></script>
    <!-- Flot plugins -->
    <script src="resources/vendors/flot.orderbars/js/jquery.flot.orderBars.js"></script>
    <script src="resources/vendors/flot-spline/js/jquery.flot.spline.min.js"></script>
    <script src="resources/vendors/flot.curvedlines/curvedLines.js"></script>
    <!-- DateJS -->
    <script src="resources/vendors/DateJS/build/date.js"></script>
    <!-- JQVMap -->
    <script src="resources/vendors/jqvmap/dist/jquery.vmap.js"></script>
    <script src="resources/vendors/jqvmap/dist/maps/jquery.vmap.world.js"></script>
    <script src="resources/vendors/jqvmap/examples/js/jquery.vmap.sampledata.js"></script>
    <!-- bootstrap-daterangepicker -->
    <script src="resources/vendors/moment/min/moment.min.js"></script>
    <script src="resources/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>

    <!-- PNotify -->
    <script src="resources/vendors/pnotify/dist/pnotify.js"></script>
    <script src="resources/vendors/pnotify/dist/pnotify.buttons.js"></script>
    <script src="resources/vendors/pnotify/dist/pnotify.nonblock.js"></script>
	
    <!-- Custom Theme Scripts -->
    <script src="resources/build/js/custom.js"></script>
	
	<!-- Scripts Personalizados del Proyecto -->
	              
	<script src="resources/scripts/general.js"></script>
    <script src="resources/scripts/menu.js"></script>
    <script src="resources/scripts/topNavBar.js"></script>
	<script src="resources/vendors/livestamp/livestamp.min.js"></script>
    
  </body>
</html>
