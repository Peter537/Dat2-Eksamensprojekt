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
<%--        TODO: create customer object and set it in session--%>
        <h1>Welcome, ${sessionScope.customer.getName()}</h1>
    </jsp:attribute>

    <jsp:attribute name="footer">
        My profile
    </jsp:attribute>

    <jsp:body>


    </jsp:body>

</t:pagetemplate>
