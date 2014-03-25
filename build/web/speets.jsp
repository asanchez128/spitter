<%-- 
    Document   : speets
    Created on : Mar 9, 2012, 4:57:31 PM
    Author     : fmccown
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="edu.harding.comp431.Database"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h1>Your Speets go here!</h1>
<%
    // Get a reference to the database
    Database spitterDatabase = (Database) application.getAttribute("spitterDatabase");
    if (spitterDatabase == null) {
        response.sendRedirect("ActionServlet?action=view");
        return;
    }

    // Query the database for all the speets of this user.
    ArrayList<String> speets = spitterDatabase.getAllSpeets("bsmith");

    // Display all speets
    for (String item : speets){        
%>
<p><%=item%></p>


<%
       
    }


%>

