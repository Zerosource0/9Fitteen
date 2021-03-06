<%@page import="Data.Partner"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Data.Project"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--Authors: Andreas, Marc  -->
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
        <link href="res/css/signin.css" rel="stylesheet">
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
                    <ul class="nav nav-sidebar">


                    </ul>
                </div>
                <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                    <form class="form-signin" action="Dashboard" method="POST">
                    <%  Project project = (Project) request.getAttribute("project");
                        ArrayList<String> stateNames = (ArrayList<String>) request.getAttribute("stateNames");
                        ArrayList<Partner> partnerInfo = (ArrayList<Partner>) request.getAttribute("partnerInfo");
                    %>
                    <% if (a!=5) {%>
                    <h1 class="page-header"><input class="form-control0" type="text" name="name" value="<%= project.getProjectName() %>"></h1>
                    <% }
                    else { %>
                    <h1 class="page-header"><input disabled="" class="form-control0" type="text" name="name" value="<%= project.getProjectName() %>"></h1>
                    <input type="hidden" name="name" value="<%= project.getProjectName() %>"/>
                    <% } %>
                    <p> <b>Project State: </b><%= stateNames.get(project.getFkProjetStateID() - 1) %></p>
                    
                    <p> <b>Partner: </b> </p>
                    <% if (a!=5) {%>
                    <select name="partnerID" class="form-control">
                            <option value="" disabled>Please Select a Partner...</option>
                            <% 
                            
                            for (Partner part : partnerInfo){
                            %>
                            
                            <option value="<%= part.getPartnerID()%>" <% if((int) project.getFkPartnerId() == part.getPartnerID() ) { %> selected <% } %>  > <%=part.getPartnerName()%> </option>
                            
                            <% } %> 
                    </select>
                    <% }
                    else {%>
                    <select disabled  name="partnerID" class="form-control">
                        <option value=""  >Please Select a Partner...</option>
                            <% 
                            int partnerID=1;
                            for (Partner part : partnerInfo){
                            %>
                            
                            <option disabled value="<%= part.getPartnerID()%>" <% if((int) project.getFkPartnerId() == part.getPartnerID() ) { %> 
                            selected 
                            <%
                                
                                partnerID=part.getPartnerID();
                                
                            %>
                            <% } %>  > <%=part.getPartnerName()%> </option>
                            
                            <% } %> 
                            
                    </select>
                            <input type="hidden" name="partnerID" value="<%= partnerID %>"/>
                    <% } %>
                    <p> <b>Description: </b>
                        <textarea class="form-control" name="description" rows="5" cols="30"><%= project.getDescription() %></textarea> </p>
                    

                    <p> <b>Created: </b><%= project.getDateCreated() %></p>
                    
                    <p> <b>Last edited: </b><%= project.getDateLastEdit() %></p>
                    <% if(a!=5) {%>
                    <p> <b>Funds allocated: </b> <input class="form-control" type="number" name="funds" value="<%= project.getFundsAllocated() %>"> </p>
                    <% } 
                    else {%>
                    <p> <b>Funds allocated: </b> <input disabled class="form-control" type="number" name="funds" value="<%= project.getFundsAllocated() %>"> </p>
                    <input class type="hidden" name="funds" value="<%= project.getFundsAllocated() %>"/>
                    <% } %>
                    <input type="hidden" name="id" value="<%= project.getId() %>" required/>
                    <input type="hidden" name="command" value="save" required/>
                    <button class="btn btn-block btn-primary btn-lg" >Save</button>
                    <% Boolean enoughmoney = (Boolean) request.getAttribute("enoughmoney"); 
            if (enoughmoney != null) {
                if (!enoughmoney) {
                    %>
                    <p id="loggedOut">Not enough money for that change!</p>
                    <%
                }
            }%>
                    </form>
                </div>
            </div>
        </div>

        <!-- Bootstrap core JavaScript-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="res/js/bootstrap.min.js"></script>
        <script src="res/js/script.js"></script>

    </body>
</html>
