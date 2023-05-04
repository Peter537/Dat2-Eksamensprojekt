<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="title">
       This is the frontpage
    </jsp:attribute>

    <jsp:attribute name="footer">
        Welcome to the frontpage
    </jsp:attribute>

    <jsp:body>

        This is the frontpage
        <br>

        <a href="login.jsp">Login</a> <br>
<%--//TODO: change "login.jsp" to login serlvet and have it handle the user--%>
        <a href="login.jsp">Carport</a> <br>
        not a member already?
        <a href="createCustomer.jsp">Sign up!</a>

        <a href="ToProfileSite">Profile</a>


    </jsp:body>

</t:pagetemplate>