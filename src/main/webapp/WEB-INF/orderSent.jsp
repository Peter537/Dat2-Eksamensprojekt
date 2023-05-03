<%--
  Created by IntelliJ IDEA.
  User: yusef
  Date: 03/05/2023
  Time: 11.09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>

    <jsp:attribute name="title">
        Your order has been sent to Fog.
    </jsp:attribute>

    <jsp:attribute name="footer">
        Order has been placed
    </jsp:attribute>

    <jsp:body>

        <p>An employee will contact you soon.</p>


    </jsp:body>

</t:pagetemplate>
