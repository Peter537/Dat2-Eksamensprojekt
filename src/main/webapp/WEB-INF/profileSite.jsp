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
        Min side
    </jsp:attribute>

    <jsp:attribute name="footer">
        My profile
    </jsp:attribute>

    <jsp:body>

        <button class="btn btn-primary text-center" value="vis historik">Vis historik</button>

        <div class="container" id="formula">

            <input type="text" placeholder="Length">
            <input type="text" placeholder="Width">
            <input type="text" placeholder="Height">




        </div>


    </jsp:body>

</t:pagetemplate>
