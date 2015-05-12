<%@page import="java.text.NumberFormat"%>
<%@page import="Data.Partner"%>
<%@page import="Data.Report"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Data.Project"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--Authors: Jonas, Adam -->
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
            ArrayList<String> countries = (ArrayList<String>) request.getAttribute("countries");
            ArrayList<String> stateNames = (ArrayList<String>) request.getAttribute("stateNames");
            ArrayList<Partner> partnerInfo = (ArrayList<Partner>) request.getAttribute("partnerInfo");
            Report report = (Report)request.getAttribute("report");
        %> 
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">

                    <a class="navbar-brand" href="Dashboard?command=view"></a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="Dashboard?command=settings">Settings</a></li>
                        <li><a href="Dashboard?command=logout">(Logged in as: <%
                            int firstSpace = ((String) request.getAttribute("personName")).indexOf(' ');
                            String firstName = ((String) request.getAttribute("personName")).substring(0, firstSpace);%>
                                <%=firstName%>)Log Out</a></li>
                    </ul>
                    
                </div>
            </div>
        </nav>

        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-3 col-md-2 sidebar">
                    <ul class="nav nav-sidebar">
                        <li><a href="Dashboard?command=view">Overview <span class="sr-only">(current)</span></a></li>

                    </ul>
                    <ul class="nav nav-sidebar">
                        <%
                            Integer a = 0;
                            a = (Integer) request.getAttribute("rights");
                        %>
                        <%if (a != 5) {%>

                        <li><a href="Dashboard?command=showCreate">Create New Project</a></li>
                        <li><a href="Dashboard?command=showPersons">Show Users</a></li>
                        <li><a href="Dashboard?command=showPartners">Show Partners</a></li>
                        <li class="active"><a href="Dashboard?command=showReport">See report</a></li>
                            <%}%>
                    </ul>
                    <ul class="nav nav-sidebar">


                    </ul>
                </div>
                <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                    <h1 class="page-header">Report</h1>

                   

                            
                            
                    <div class="table-responsive">
                        <table  class="table table-striped" id="example">
                            <thead>
                                <tr>
                                    <th>Countries:</th>
                                    <th>Number of partners</th>
                                    <th>Total projects done</th>
                                    <th>Money spent</th>
                                    <th>Average money spent pr. partner</th>

                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for(int i = 0;i<report.getCountries().size();i++) {

                                %> 
                                <tr>
                                    
                                    <td width="10%" > <%= report.getCountries().get(i) %></td> 
                                    <td> <%= report.getNoPartners().get(i) %></td>
                                    <td> <%= report.getNoProjectDone().get(i) %></td>
                                    <td> <%= report.getMoneySpent().get(i) %> </td>
                                    <td> <%= report.getAvgSpentPartner().get(i) %></td>
                                     <%   }
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
