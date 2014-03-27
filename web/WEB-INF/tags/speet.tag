<%-- 
    Document   : speet
    Created on : Mar 26, 2014, 11:26:44 PM
    Author     : asanchez
--%>

<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>

<%-- any content can be specified here e.g.: --%>
<jsp:useBean id="speet" class="edu.harding.comp431.Speets" scope="request" />
 
 
<p><strong>${speet.username}</strong><br>
<p>${speet.message}</p></br>
<p>${speet.timestamp}</p></br>
