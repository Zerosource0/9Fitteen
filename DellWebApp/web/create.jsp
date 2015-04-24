<%@page import="java.util.ArrayList"%>
<%@page import="Data.Partner"%>
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
                    <form class="form-signin" action="Dashboard" method="POST">
                        <h1 class="form-signin-heading">Create New Project</h1>
                        <div><h3>Project Name *</h3></div>
                        <input class="form-control" type="text" name="name" pattern="\b.*[a-zA-Z]+.*\b" title="Project name cannot consist solely of numbers." maxlength="50" required autofocus>
                        <div></div>
                        <div><h3>Partner</h3></div>
                        <div></div>
                        <select name="partnerID" class="form-control">
                            <option value="" disabled selected>Please Select a Partner...</option>
                            <% ArrayList<Partner> partnerInfo = (ArrayList<Partner>) request.getAttribute("partnerInfo");
                            
                            for (Partner part : partnerInfo){
                            %>
                            
                             <option value="<%= part.getPartnerID()%>" > <%=part.getPartnerName()%> </option>
                            
                            <% } %> 
                        </select>
                        <div></div>
                        <div><h3>Project Description</h3></div>
                        <div></div>
                        <textarea class="form-control" name="description" rows="5" cols="30"></textarea>
                        <div></div>
                        <div><h3>Funds Allocated</h3></div>
                        <input class="form-control" type="number" name="funds">
                        <div></div>
                        <div><h3></h3></div>
                        <input type="hidden" name="command" value="create" required/>
                        <button class="btn btn-block btn-primary btn-lg" >Create</button>
                         <% Boolean enoughmoney = (Boolean) request.getAttribute("enoughmoney"); 
            if (enoughmoney != null) {
                if (!enoughmoney) {
                    %>
                    <p id="loggedOut">Not enough money for that project!</p>
                    <%
                }
            }%>
                    </form>
                </div>
            </div>
        </div>

    </body>
</html>
