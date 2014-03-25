<%-- 
    Document   : edit-account
    Created on : Mar 6, 2012, 3:14:26 PM
    Author     : fmccown
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="user"
             class="edu.harding.comp431.SpitterUser"
             scope="session" />

<%
    // Make sure the user is logged in
    if (session.getAttribute("login") == null)
        response.sendRedirect("login.jsp");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Spitter: Edit Account</title>
        
        <link rel="stylesheet" type="text/css" href="styles.css" />
        
        <script type="text/javascript">
            
            window.addEventListener('load', load, false);
            
            function load()
            {               
                // Make Cancel button redirect user to main page
                var cancelButton = document.getElementById("cancelButton");  
                cancelButton.addEventListener('click', function() { 
                    location="index.jsp" }, false);                
            }
            
        </script>
    </head>
    <body>
        <h1>Edit Account</h1>
        
        <%
        
        String error = (String)session.getAttribute("error");
        if (error != null && error.length() > 0) {
        %>        
        <p style="color:red"><%= error %></p>
        <%
        }
        %>
        
        <form method="post" action="ActionServlet" enctype="multipart/form-data">
            <input type="hidden" name="action" value="edit-account" />
            <table>
                <tr>
                    <td>Username</td>
                    <td><jsp:getProperty name="user" property="username" />
                    </td>                
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" maxlength="15" name="password" value="" />
                    </td>                
                </tr>
                <tr>
                    <td>Short Bio</td>
                    <td><textarea name="about" rows="3" cols="25" 
                       maxlength="100"><jsp:getProperty name="user" 
                       property="about" /></textarea>
                    </td>                
                </tr>
                <tr>
                    <td>Profile Pic<br />
                        <span style="font-size: small">(JPG only)</span></td>
                    <td><input type="file" name="image"></td>
                </tr>
                <tr>
                    <td><br /><input type="submit" value="Edit Account" />
                    </td>
                    <td><br /><input type="button" id="cancelButton" value="Cancel" />
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
