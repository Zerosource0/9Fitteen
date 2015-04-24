<%@page import="Data.Person"%>
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

        <title>Dell User Details</title>

        <link href="res/css/bootstrap.min.css" rel="stylesheet">
        <link href="res/css/dashboard.css" rel="stylesheet">
        <link href="res/css/signin.css" rel="stylesheet">

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
                    <a class="navbar-brand" href="Dashboard?command=view"></a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="Dashboard?command=settings">Settings</a></li>
                        <li><a href="Dashboard?command=logout">(Logged in as: <%
                            int firstSpace = ((String) request.getAttribute("personName")).indexOf(' ');
                                String firstName = ((String) request.getAttribute("personName")).substring(0,firstSpace);%>
                                <%=firstName%>)Log Out</a></li>
                    </ul>
                    
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
                        <%
                         Integer a=0;
                         a=(Integer) request.getAttribute("rights");
                        %>
                        <%if (a!=5) 
                        {%>
                        
                        <li><a href="Dashboard?command=showCreate">Create New Project</a></li>
                        <li><a href="Dashboard?command=showPersons">Show Users</a></li>
                        <li><a href="Dashboard?command=showPartners">Show Partners</a></li>
                        <li><a href="Dashboard?command=showReport">See report</a></li>
                        <%}%>
                    </ul>
                </div>
                <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                    <% Person person = (Person) request.getAttribute("person");
                       ArrayList<Partner> partnerInfo = (ArrayList<Partner>) request.getAttribute("partnerInfo");
                    %>
                    <h1 class="page-header"><%= person.getName() %></h1>

                    <p> <b>User ID: </b><%= person.getID() %></p>
                    
                    <p> <b>User Type: </b><%= person.getPersonTypeName() %></p>
                    
                    <p> <b>Partner: </b> <%= partnerInfo.get(person.getFkPartnerID() - 1).getPartnerName() %></p>                    
                    
                    <p> <b>Phone Number: </b><%= person.getPhoneNumber() %></p>
                    
                    <p> <b>Email: </b><%= person.getEmail() %></p>
                    
                    <a href="Dashboard?command=editPerson&personID=<%=person.getID()%>" ><input type="button" value="Edit"></a>
                    <a href="Dashboard?command=deletePerson&personID=<%=person.getID()%>" ><input type="button" onclick="return comfirm('Do you really want to delete'<%= person.getName()%>);" value="Delete"></a>
                </div>
            </div>
        </div>

        <!-- Bootstrap core JavaScript-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="res/js/bootstrap.min.js"></script>
        <script src="res/js/script.js"></script>

    </body>
</html>
