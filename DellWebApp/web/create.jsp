<%-- 
    Document   : Main
    Created on : 07-Apr-2015, 10:40:23
    Author     : marcj_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="9 Fitteen">
        <link rel="icon" href="/favicon.ico">

        <title>Dell Project Dashboard</title>

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
                        <li><a href="CreateProject">Dashboard</a></li>
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
                        <li class="active"><a href="#">Overview <span class="sr-only">(current)</span></a></li>
                        <li><a href="#">Reports</a></li>
                        <li><a href="#">Analytics</a></li>
                        <li><a href="#">Export</a></li>
                    </ul>
                    <ul class="nav nav-sidebar">
                        <li><a href="main.jsp">Create New Project</a></li>
                        <li><a href="">Nav item again</a></li>
                        <li><a href="">One more nav</a></li>
                        <li><a href="">Another nav item</a></li>
                        <li><a href="">More navigation</a></li>
                    </ul>
                    <ul class="nav nav-sidebar">
                        <li><a href="">Nav item again</a></li>
                        <li><a href="">One more nav</a></li>
                        <li><a href="">Another nav item</a></li>
                    </ul>
                </div>
                <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                    <h1 class="page-header">Create New Project</h1>
                    <form action="CreateProject" method="POST">
                        <div><h3>Project Name</h3></div>
                        <input type="text" name="name" required>
                        <div></div>
                        <div><h3>Partner</h3></div>
                        <div></div>
                        <select>
                            <option value="" name="partnerName" disabled selected>Please Select Partner..</option>
                            <option>partner1</option>
                            <option>partner2</option>
                        </select>
                        <div></div>
                        <div><h3>Project Description</h3></div>
                        <div></div>
                        <textarea name="description" rows="5" cols="30"></textarea>
                        <div></div>
                        <div><h3>Funds Allocated</h3></div>
                        <input type="number" name="funds">
                        <div></div>
                        <div><h3></h3></div>
                        <input type="hidden" name="command" value="create" required/>
                        <button>Create</button>
                    </form>
                </div>
            </div>
        </div>

    </body>
</html>
