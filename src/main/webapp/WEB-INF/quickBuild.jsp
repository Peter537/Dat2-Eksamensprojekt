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
        Quick build
    </jsp:attribute>

    <jsp:attribute name="footer">
       Quick build
    </jsp:attribute>

    <jsp:body>

        <h1>Udfyld nedenstående formular:</h1>

        <a href="orderSent.jsp">indsend formular</a>

    </jsp:body>

</t:pagetemplate>
