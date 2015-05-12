<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--Authors: Andreas, Adam , Jonas-->
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="Dell Login Frontpage">
    <meta name="author" content="9Fitteen">
    <link rel="icon" href="../../favicon.ico">

    <title>Dell Login</title>

    <link href="res/css/bootstrap.min.css" rel="stylesheet">

    <link href="res/css/signin.css" rel="stylesheet">

    
  </head>

  <body>

    <div class="container">

        <form class="form-signin" action="Dashboard" method="POST">
        <h2 class="form-signin-heading">Please sign in</h2>
        <label for="inputEmail" class="sr-only">Email address</label>
        <input name="userName" type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
        <div class="checkbox">
          <%--<label>
            <input type="checkbox" value="remember-me"> Remember me
          </label> --%>
        </div>
        <% Boolean success = (Boolean) request.getAttribute("success"); 
            if (success != null) {
                if (!success) {
                    %>
                    <p id="wrongPass">Wrong username or password</p>
                    <%
                }
            }%>
            
             <% Boolean loggedOut = (Boolean) request.getAttribute("loggedOut"); 
            if (loggedOut != null) {
                if (loggedOut) {
                    %>
                    <p id="loggedOut">You're now logged out</p>
                    <%
                }
            }%>
            <% Boolean access = (Boolean) request.getAttribute("access"); 
            if (access != null) {
                if (!access) {
                    %>
                    <p id="loggedOut">You need to login to view this page</p>
                    <%
                }
            }%>
            
        
        <input type="hidden" name="command" value="login"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>

    </div>
    

  </body>
</html>
