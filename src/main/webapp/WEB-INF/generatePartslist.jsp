<%--
  Created by IntelliJ IDEA.
  User: yusef
  Date: 03/05/2023
  Time: 11.10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>

    <jsp:attribute name="title">
        partlist:
    </jsp:attribute>

    <jsp:attribute name="footer">
        Partlist generator
    </jsp:attribute>

    <jsp:body>

<button class="btn btn-primary text-center" formaction="">Partlist</button>


        <iframe src="generatedPartslist.jsp"></iframe>


    </jsp:body>

</t:pagetemplate>
