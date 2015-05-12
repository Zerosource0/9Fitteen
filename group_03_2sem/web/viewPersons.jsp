<%@page import="Data.Person"%>
<%@page import="Data.Partner"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Data.Project"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--Authors: Marc  -->
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="9 Fitteen">
        <link rel="shortcut icon" href="http://i.dell.com/images/global/branding/dellecomicon.ico" type="image/x-icon">

        <title>Dell User Dashboard</title>

        <link href="res/css/bootstrap.min.css" rel="stylesheet">

        <link href="res/css/dashboard.css" rel="stylesheet">
        <link href="res/css/signin.css" rel="stylesheet">

    </head>

    <body>
        <% ArrayList<Project> projects = (ArrayList<Project>) request.getAttribute("projects");
            ArrayList<Partner> partnerInfo = (ArrayList<Partner>) request.getAttribute("partnerInfo");
            ArrayList<Person> persons = (ArrayList<Person>) request.getAttribute("persons");
            
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
                        <li><a href="Dashboard?command=view">Overview <span class="sr-only">(current)</span></a></li>

                    </ul>
                    <ul class="nav nav-sidebar">
                        <%
                         Integer a=0;
                         a=(Integer) request.getAttribute("rights");
                        %>
                        <%if (a!=5) 
                        {%>
                        
                        <li><a href="Dashboard?command=showCreate">Create New Project</a></li>
                        <li class="active"><a href="Dashboard?command=showPersons">Show Users</a></li>
                        <li><a href="Dashboard?command=showPartners">Show Partners</a></li>
                        <li><a href="Dashboard?command=showReport">See report</a></li>
                        <%}%>
                    </ul>
                </div>
                <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

                   

                    <h2  class="sub-header">Users</h2> 
                    
                    <div class="table-responsive">
                        <table  class="table table-striped" id="example">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>User Type</th>
                                    <th>Partner</th>
                                    <th>Phone Number</th>
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
                                    for (Person person : persons) {

                                %> <tr> <%
                                    %> <td> <a href="Dashboard?personID=<%=person.getID()%>" ><%= person.getID()%></a> </td> <%
                                    %> <td> <%= person.getName()%> </td> <%
                                    %> <td> <%= person.getPersonTypeName()%> </td> <%
                                    %> <td> <%= partnerInfo.get(person.getFkPartnerID() - 1).getPartnerName()%> </td> <%
                                    %> <td> <%= person.getPhoneNumber()%> </td> <%
                                        }
                                    %>
                            </tbody>
                      </table>
                          <%--  <form class="form-signin" action="Dashboard" method="POST">
                        <snapToRight><button class="btn btn-block btn-primary btn-lg" >Add Person</button><input type="hidden" name="command" value="addPerson" required/> </snaptoright>     --%>                    
                      </form>
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
