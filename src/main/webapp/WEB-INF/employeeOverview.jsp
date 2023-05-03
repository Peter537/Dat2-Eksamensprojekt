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

<%--        TODO: create the employee object and set it in the session--%>
        <h1>Welcome, ${sessionScope.employee.getName()}</h1>
    </jsp:attribute>

    <jsp:attribute name="footer">
        My employee site
    </jsp:attribute>

    <jsp:body>

        <h1>Nyheder</h1>

        <h2>Medarbejder info</h2>
        <p>medarbejder position: lager/admin/sælger (skift denne ud)</p>


        <input class="btn btn-primary" type="button" value="Se alle Ordre">

        <input class="btn btn-primary" type="button" value="Ændre materiale-katalog">

    </jsp:body>

</t:pagetemplate>
