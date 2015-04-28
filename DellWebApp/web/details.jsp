<%@page import="Data.PoeFile"%>
<%@page import="Data.Comment"%>
<%@page import="Data.Partner"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Data.Project"%>
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
                <div class="col-sm-6 col-sm-offset-3 col-md-5 col-md-offset-2 main">
                    <% Project project = (Project) request.getAttribute("project");
                        ArrayList<String> stateNames = (ArrayList<String>) request.getAttribute("stateNames");
                        ArrayList<Partner> partnerInfo = (ArrayList<Partner>) request.getAttribute("partnerInfo");
                        Integer projectID=project.getId(), projectStateID=project.getFkProjetStateID();
                        ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("comments");
                        ArrayList<String> poeFiles = (ArrayList<String>) request.getAttribute("poeFiles");
                        
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
                    
                    <% if (project.getFkProjetStateID() > 1) {%>
                    <a href="Dashboard?command=back&id=<%=project.getId()%>" ><input type="button" value="Previous State - <%= stateNames.get(project.getFkProjetStateID()-2) %>"></a>
                    <% } %> 
                    
                    <% if (project.getFkProjetStateID() < 9) {%>
                        <% if (a!=5 || (a==5 && project.getFkProjetStateID()==3)  
                                || (a==5 && project.getFkProjetStateID()==5) 
                                || (a==5 && project.getFkProjetStateID()==6)) 
                                 {%>
                                 <a href="Dashboard?command=next&id=<%=project.getId()%>" ><input type="button" value="Next State - <%= stateNames.get(project.getFkProjetStateID()) %>"></a>
                            <%} %>
                    <% } %>
                    
                                 
                                 <div class="">
                                         <% for(int i = 0; i < poeFiles.size(); i++) { %>
                                         
                                         <a href="Dashboard?command=getFile&id=<%=project.getId()%>&file=<%=i%>" >
                                             <p><%= poeFiles.get(i)%> </p>
                                         </a>
                                 
                                 <% } %>
                                 </div>
                     
                    <h2>Upload</h2>
                    <form method="POST" action="Dashboard" enctype="multipart/form-data">
                        <p>Select file to upload: <input type="file" name="file" size="60" required /> </p>
                        <input type="hidden" name="projectID" value="<%=projectID%>" />
                        <input type="hidden" name="projectStateID" value="<%=projectStateID%>" />
                        <input type="hidden" name="command" value="upload" />
                        <input type="submit" value="Upload"/>
                    </form>
                </div>
                
                 
                
                <div class="col-sm-3  col-md-5  main">
                    <h1 class="page-header">Comments</h1>
                    <form action="Dashboard" method="POST">
                        <textarea name="comment" rows="5" cols="30" ></textarea>
                        <input type="hidden" name="command" value="comment" />
                        <input type="hidden" name="projectID" value="<%=project.getId()%>" />
                        <br>
                        <input type="submit" value="Submit" />
                    </form>
                    <% for (Comment com : comments) {
                            String b = com.getPersonName()+" @ " + com.getTimeStamp()+": ";
                            String c = com.getComment();
                        %>
                        <p><%= b %></p>
                        <p><%= c %></p>
                        <%
                    } %>
                </div>
            </div>
        </div>

        <!-- Bootstrap core JavaScript-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="res/js/bootstrap.min.js"></script>
        <script src="res/js/script.js"></script>

    </body>
</html>
