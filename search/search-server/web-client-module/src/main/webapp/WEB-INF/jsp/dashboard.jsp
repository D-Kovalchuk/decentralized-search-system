<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Dashboard</title>

    <spring:url value="/resources" var="resourcesUrl"/>
    <link href="${resourcesUrl}/bootstrap.min.css" rel="stylesheet">
    <link href="${resourcesUrl}/dashboard.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="${resourcesUrl}/ie8-responsive-file-warning.js"></script><![endif]-->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <style>
        @media (min-width: 768px) {

            .navbar-form .form-control {
                width: 350px;
            }

        }

        .btn {
            padding: 6px 13px;
            font-size: 15px;
        }

        .navbar-form.navbar-right:last-child {
            margin-right: 60px;
        }

        @media (min-width: 1200px) {
            .col-lg-6 {
                width: 100%;
            }
        }

    </style>
</head>
<spring:url value="/profile" var="profileUrl"/>
<spring:url value="/logout" var="logoutUrl"/>
<spring:url value="/artifacts" var="artifactsUrl"/>
<spring:url value="/artifacts/online" var="artifactsOnlineUrl"/>
<spring:url value="/artifacts/shared" var="artifactsSharedUrl"/>
<spring:url value="/artifacts/downloaded" var="artifactsDownloadedUrl"/>
<spring:url value="/artifacts/pending" var="artifactsPendingUrl"/>
<spring:url value="/users" var="usersUrl"/>
<spring:url value="/users/online" var="usersOnlineUrl"/>
<body>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Fly house</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Artifacts <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="${artifactsUrl}">All</a></li>
                        <li><a href="${artifactsOnlineUrl}">Online only</a></li>
                        <li class="divider"></li>
                        <li><a href="${artifactsDownloadedUrl}">Downloaded</a></li>
                        <li><a href="${artifactsSharedUrl}">Shared</a></li>
                        <li><a href="${artifactsPendingUrl}">Pending</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Users <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="${usersUrl}">All</a></li>
                        <li><a href="${usersOnlineUrl}">Online only</a></li>
                    </ul>
                </li>
                <li><a href="${profileUrl}">Profile</a></li>
                <li><a href="${logoutUrl}">Logout</a></li>
            </ul>
        </div>
    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">

            <h3>The most popular files</h3>
            <ul class="nav nav-sidebar">
                <li><a href="">Nav item</a></li>
                <li><a href="">Nav item again</a></li>
                <li><a href="">One more nav</a></li>
                <li><a href="">Another nav item</a></li>
                <li><a href="">More navigation</a></li>
                <li><a href="">More navigation</a></li>
                <li><a href="">More navigation</a></li>
            </ul>
            <h3 class="page-header">Service statistic</h3>

            <p href="#">Users: <span class="badge">42</span></p>

            <p href="#">Files: <span class="badge">1230</span></p>

            <p href="#">Size: <span class="badge">58GB</span></p>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <form class="navbar-form">
                <div class="col-lg-6">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search...">
      <span class="input-group-btn">
          <button class="btn btn-lg btn-primary" type="submit">Go!</button>
      </span>
                    </div>
                    <!-- /input-group -->
                </div>
                <!-- /.col-lg-6 -->


            </form>
            <div style="clear: both"></div>


            <h2 class="sub-header">Matched documents</h2>

            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Title</th>
                        <th>Category</th>
                        <th>Online users</th>
                        <th>Reference</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>3.3.1. Infinispan Directory configuration</td>
                        <td>Lorem</td>
                        <td>ipsum</td>
                        <td>dolor</td>
                    </tr>
                    <tr>
                        <td>1,002</td>
                        <td>amet</td>
                        <td>consectetur</td>
                        <td>adipiscing</td>
                    </tr>
                    <tr>
                        <td>1,003</td>
                        <td>Integer</td>
                        <td>nec</td>
                        <td>odio</td>
                    </tr>
                    <tr>
                        <td>1,003</td>
                        <td>libero</td>
                        <td>Sed</td>
                        <td>cursus</td>
                    </tr>
                    <tr>
                        <td>1,004</td>
                        <td>dapibus</td>
                        <td>diam</td>
                        <td>Sed</td>
                    </tr>
                    <tr>
                        <td>1,005</td>
                        <td>Nulla</td>
                        <td>quis</td>
                        <td>sem</td>
                    </tr>
                    <tr>
                        <td>1,006</td>
                        <td>nibh</td>
                        <td>elementum</td>
                        <td>imperdiet</td>
                    </tr>
                    <tr>
                        <td>1,007</td>
                        <td>sagittis</td>
                        <td>ipsum</td>
                        <td>Praesent</td>
                    </tr>
                    <tr>
                        <td>1,008</td>
                        <td>Fusce</td>
                        <td>nec</td>
                        <td>tellus</td>
                    </tr>
                    <tr>
                        <td>1,009</td>
                        <td>augue</td>
                        <td>semper</td>
                        <td>porta</td>
                    </tr>
                    <tr>
                        <td>1,010</td>
                        <td>massa</td>
                        <td>Vestibulum</td>
                        <td>lacinia</td>
                    </tr>
                    <tr>
                        <td>1,011</td>
                        <td>eget</td>
                        <td>nulla</td>
                        <td>Class</td>
                    </tr>
                    <tr>
                        <td>1,012</td>
                        <td>taciti</td>
                        <td>sociosqu</td>
                        <td>ad</td>
                    </tr>
                    <tr>
                        <td>1,013</td>
                        <td>torquent</td>
                        <td>per</td>
                        <td>conubia</td>
                    </tr>
                    <tr>
                        <td>1,014</td>
                        <td>per</td>
                        <td>inceptos</td>
                        <td>himenaeos</td>
                    </tr>
                    <tr>
                        <td>1,015</td>
                        <td>sodales</td>
                        <td>ligula</td>
                        <td>in</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="${resourcesUrl}/bootstrap.min.js"></script>
<script src="${resourcesUrl}/docs.min.js"></script>
</body>
</html>
