<%@page import="Data.Partner"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Data.Project"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="Details View">
        <meta name="author" content="9 Fitteen">
        <link rel="icon" href="/favicon.ico">

        <title>Dell Project Details</title>

        <link href="res/css/bootstrap.min.css" rel="stylesheet">

        <link href="res/css/dashboard.css" rel="stylesheet">

    </head>

    <body>

        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">Project name</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="Dashboard?command=view">Dashboard</a></li>
                        <li><a href="#">Settings</a></li>
                        <li><a href="#">Profile</a></li>
                        <li><a href="index.html">Log Out</a></li>
                    </ul>
                    <form class="navbar-form navbar-right">
                        <input type="text" class="form-control" placeholder="Search...">
                    </form>
                </div>
            </div>
        </nav>

        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-3 col-md-2 sidebar">
                    <ul class="nav nav-sidebar">
                        <li class="active"><a href="Dashboard?command=view">Overview <span class="sr-only">(current)</span></a></li>

                    </ul>
                    <ul class="nav nav-sidebar">
                        <li><a href="Dashboard?command=showCreate">Create New Project</a></li>

                    </ul>
                    <ul class="nav nav-sidebar">


                    </ul>
                </div>
                <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                    <% Project project = (Project) request.getAttribute("project");
                        ArrayList<String> stateNames = (ArrayList<String>) request.getAttribute("stateNames");
                        ArrayList<Partner> partnerInfo = (ArrayList<Partner>) request.getAttribute("partnerInfo");
                    %>
                    <h1 class="page-header"><%= project.getProjectName() %></h1>

                    <p> <b>Project State: </b><%= stateNames.get(project.getFkProjetStateID() - 1) %></p>
                    
                    <p> <b>Partner: </b> <%= partnerInfo.get(project.getFkPartnerId() - 1).getPartnerName() %></p>
                    
                    <p> <b>Description: </b>
                     <%= project.getDescription() %></p>

                    <p> <b>Created: </b><%= project.getDateCreated() %></p>
                    
                    <p> <b>Last edited: </b><%= project.getDateLastEdit() %></p>
                    
                    <p> <b>Funds allocated: </b><%= project.getFundsAllocated() %></p>
                    <a href="Dashboard?command=edit&id=<%=project.getId()%>" ><input type="button" value="Edit"></a>
                </div>
            </div>
        </div>

        <!-- Bootstrap core JavaScript-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="res/js/bootstrap.min.js"></script>
        <script src="res/js/script.js"></script>

    </body>
</html>
