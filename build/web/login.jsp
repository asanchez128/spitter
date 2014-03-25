<%-- 
    Document   : login
    Created on : Mar 2, 2012, 11:54:41 AM
    Author     : fmccown
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login to Spitter!</title>
        
        <link rel="stylesheet" type="text/css" href="styles.css" />
    </head>
    <body>
        <h1>Login to Spitter!</h1>
        
        <%
        
        String error = (String)session.getAttribute("error");
        if (error != null && error.length() > 0) {
        %>        
        <p style="color:red"><%= error %></p>
        <%
        }
        %>
        
        <form method="post" action="ActionServlet">
            <input type="hidden" name="action" value="validate-login">
            <table>
                <tr>
                    <td>Username:</td>
                    <td><input type="text" name="username" autofocus></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type="password" name="password"></td>
                </tr>
            </table>
            <input type="submit" value="Login" />
        </form>
        
        
        <br><br>
        <h3>Don't have a username yet?</h3>
        <a href="create-account.jsp">Create an Account</a>
        
    </body>
</html>
