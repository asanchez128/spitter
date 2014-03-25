<%-- 
    Document   : followers
    Created on : Mar 8, 2012, 5:03:36 PM
    Author     : fmccown
--%>

<%@page import="edu.harding.comp431.SpitterUser"%>
<%@page import="edu.harding.comp431.Database"%>
<%@page import="edu.harding.comp431.ImageUtilities"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:useBean id="user"
             class="edu.harding.comp431.SpitterUser"
             scope="session" />

<%
    // Get database from the application context
    Database spitterDatabase = (Database)application.getAttribute("spitterDatabase");
    if (spitterDatabase == null) {
        response.sendRedirect("ActionServlet");
        return;
    }
    
    // Make sure the user is logged in
    if (session.getAttribute("login") == null)
        response.sendRedirect("login.jsp");
%>


<span class="main-heading">Followers</span>
<br /><br />

<%
    ArrayList<SpitterUser> list = spitterDatabase.getFollowers(user.getUsername());

    for (SpitterUser u : list) {                             
        String imageFile = u.getUsername() + ".jpg"; 
        String fullPath = this.getServletContext().getRealPath(
                    "images\\" + imageFile);
        if (!ImageUtilities.imageExists(fullPath))
            imageFile = "default.jpg";                
%>

<table>
    <tr>
        <td valign="top">
            <img src="images/<%= imageFile %>" class="following-profile-image"/>
            <br />
            <% if (u.isFollowing()) { %>    
                &nbsp;<a class="unfollow-link" 
                href="ActionServlet?action=unfollow&user=<%= u.getUsername() %>">Unfollow</a>
            <% } else { %>
                &nbsp;  <a class="follow-link" 
                href="ActionServlet?action=follow&user=<%= u.getUsername() %>">Follow</a>
            <% } %>   
        </td>
        <td width="100%">
            <span class="following-username"><%= u.getUsername() %></span><br />
            <span class="following-about"><%= u.getAbout() %></span>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <hr />
        </td>
    </tr>
</table>

<% } %>        
