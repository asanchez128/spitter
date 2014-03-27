<%-- 
    Document   : speets
    Created on : Mar 9, 2012, 4:57:31 PM
    Author     : fmccown
--%>

<%@page import="edu.harding.comp431.Speets"%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.harding.comp431.Database"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="user"
             class="edu.harding.comp431.SpitterUser"
             scope="session" />

<h1>Your Speets go here!</h1>
<%
    // Get a reference to the database
    Database spitterDatabase = (Database) application.getAttribute("spitterDatabase");
    if (spitterDatabase == null) {
        response.sendRedirect("ActionServlet?action=view");
        return;
    }

    // Query the database for all the speets of this user.
    ArrayList<Speets> speets = spitterDatabase.getAllSpeets(user.getUsername());

    // Display all speets
%>
<ol>
    <%
        for (Speets item : speets) {
        request.setAttribute("speet", item);
        System.out.println(item.getMessage());
    %>
    <my:speet/>
    <%
      }
    %>
</ol>
