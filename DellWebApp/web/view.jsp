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
        <meta name="description" content="">
        <meta name="author" content="9 Fitteen">
        <link rel="shortcut icon" href="http://i.dell.com/images/global/branding/dellecomicon.ico" type="image/x-icon">

        <title>Dell Project Dashboard</title>

        <link href="res/css/bootstrap.min.css" rel="stylesheet">

        <link href="res/css/dashboard.css" rel="stylesheet">
        <link href="res/css/signin.css" rel="stylesheet">

    </head>

    <body>
        <% ArrayList<Project> projects = (ArrayList<Project>) request.getAttribute("projects");
            ArrayList<String> stateNames = (ArrayList<String>) request.getAttribute("stateNames");
            ArrayList<Partner> partnerInfo = (ArrayList<Partner>) request.getAttribute("partnerInfo");
        %> 
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">

                    <a class="navbar-brand" href="Dashboard?command=view"></a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="#">Settings</a></li>
                        <li><a href="Dashboard?command=logout">(Logged in as: <%
                            int firstSpace = ((String) request.getAttribute("personName")).indexOf(' ');
                                String firstName = ((String) request.getAttribute("personName")).substring(0,firstSpace);%>
                                <%=firstName%>)Log Out</a></li>
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
                    <h1 class="page-header">Dashboard</h1>

                    <div class="row placeholders">
                        <div class="col-xs-6 col-sm-4 placeholder">
                            <img src="res/file148.png" class="img-responsive" alt="test">
                            <h4><%= projects.size()%></h4>
                            <span class="text-muted">Number of Projects</span>
                        </div>
                        <div class="col-xs-6 col-sm-4 placeholder">
                            <img src="res/network11.png" class="img-responsive" alt="">
                            <h4><%= request.getAttribute("numberOfUsers")%></h4>
                            <span class="text-muted">Number of Users</span>
                        </div>
                        <div class="col-xs-6 col-sm-4 placeholder">
                            <img src="res/people8.png" class="img-responsive" alt="">
                            <h4><%= request.getAttribute("numberOfPartners")%></h4>
                            <span class="text-muted">Number of Partners</span>
                        </div>
                    </div>

                    <h2 class="sub-header">Recent Projects</h2>
                    <div class="table-responsive">
                        <table  class="table table-striped" id="example">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Project State</th>
                                    <th>Partner</th>
                                    <th>Date Created</th>
                                    <th>Last Edited</th>
                                </tr>
                            </thead>
                            <tbody>

                                <%
                                    Enumeration<String> atr = request.getAttributeNames();
                                    while (atr.hasMoreElements()) {
                                        if (atr.nextElement().equals("pro")) {

                                %>
                                <%=request.getAttribute("pro")%>
                                <%      }
                                    }
                                %>
                                <%
                                    for (Project p : projects) {

                                %> <tr> <%
                                    %> <td> <a href="Dashboard?id=<%=p.getId()%>" ><%= p.getProjectName()%></a> </td> <%
                                    %> <td> <%= stateNames.get(p.getFkProjetStateID() - 1)%> </td> <%
                                    %> <td> <%= partnerInfo.get(p.getFkPartnerId() - 1).getPartnerName()%> </td> <%
                                    %> <td> <%= p.getDateCreated()%> </td> <%
                                    %> <td> <%= p.getDateLastEdit()%> </td> </tr> <%
                                        }
                                    %>
                            </tbody>
                        </table>
                    </div>
                </div>
                <a href="../src/java/servlet/SqlServlet.java"></a>
            </div>
        </div>

        <!-- Bootstrap core JavaScript-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="res/js/bootstrap.min.js"></script>
        <script src="res/js/script.js"></script>

    </body>
</html>
