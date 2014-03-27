<%--
    Document   : profile
    Created on : Mar 2, 2012, 1:42:41 PM
    Author     : fmccown
--%>

<%@page import="edu.harding.comp431.Database"%>
<%@page import="edu.harding.comp431.ImageUtilities"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="user"
             class="edu.harding.comp431.SpitterUser"
             scope="session" />

<%    
    // TODO: Make sure the user is logged in by checking "login" in session.
	// If not logged in, redirect to login.jsp
  
    // Get database from the servlet context
    Database spitterDatabase = (Database)application.getAttribute("spitterDatabase");
    if (spitterDatabase == null) {
        response.sendRedirect("ActionServlet?action=view");
        return;
    }
    
    if (session.getAttribute("login") == null)
        response.sendRedirect("login.jsp");
    
    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Spitter</title>
        
        <link rel="stylesheet" type="text/css" href="styles.css" />
        
        <script type="text/javascript" src="http://cs.harding.edu/fmccown/jquery.js"></script>
        <script type="text/javascript" src="main.js"></script>
    </head>
    <body>
        <%@ include file="heading.jspf" %>
        
        // Two ways to pull out information from database
        <div class="section profile">
            <img src="<%= "images/" + user.getProfilePic() %>"
                 class="profile-image" width="50" height="50" alt="Profile Pic"/>
            <span class="profile-username"><%= user.getUsername() %></span>
            <br />
            <span class="profile-about">
                <jsp:getProperty name="user" property="about" />
            </span>
            <a class="edit-profile-link" href="edit-account.jsp">Edit</a>
            
            <table>
                <tr>
                    <td width="70">
                        <span id="speets-total" class="profile-stats-num">
                           ???
                        </span><br />
                        <a href="speets.jsp?limit" id="speets-link" class="profile-stats-label">Speets</a>
                    </td>
                    <td width="70">
                        <span class="profile-stats-num">
                            <%= spitterDatabase.getFollowingTotal(user.getUsername()) %>
                        </span><br />
                        <a href="following.jsp" id="following-link" class="profile-stats-label">Following</a>
                    </td>
                    <td width="70">
                        <span class="profile-stats-num">
                            ???
                        </span><br />
                        <a href="followers.jsp" id="followers-link" class="profile-stats-label">Followers</a>
                    </td>
                </tr>
            </table>
            <br />
            <form method="post" action="ActionServlet?action=new-speet">
                <textarea name="speet" rows="5" cols="28" maxlength="145"
                          id="speet-text"></textarea>
                <br />
                <span class="chars-remaining">145</span> &nbsp; &nbsp;
                <input type="submit" id="submit-speet" value="Speet" />
            </form>
        </div>
        
        <div class="section speets" id="main">
            Loading...
        </div>        
        
    </body>
</html>
