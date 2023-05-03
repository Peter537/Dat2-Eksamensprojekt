<%--
  Created by IntelliJ IDEA.
  User: yusef
  Date: 03/05/2023
  Time: 11.08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>


    <jsp:attribute name="title">
        Create user-page
    </jsp:attribute>
    <jsp:attribute name="footer">
        Create user-page
    </jsp:attribute>

    <jsp:body>

        <form action="create-user" method="post">
            <label for="username">Username: </label>
            <input type="text" id="username" name="username"/>
            <label for="password">Password: </label>
            <input type="password" id="password" name="password"/>
            <label for="confirmPassword">Confirm password: </label>
            <input type="password" id="confirmPassword" name="confirmPassword"/>
            <input type="submit"  value="Sign up"/>
        </form>

    </jsp:body>

</t:pagetemplate>
