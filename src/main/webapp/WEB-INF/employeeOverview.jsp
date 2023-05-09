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
        Medarbejder side
    </jsp:attribute>

    <jsp:attribute name="footer">
        Medarbejder side
    </jsp:attribute>

    <jsp:body>

        <h2>Medarbejder info</h2>
        <p>navn: ${sessionScope.employee.name}</p>
        <p>email: ${sessionScope.employee.email}</p>
        <p>medarbejder position: ${sessionScope.employee.position.positionName}</p>
        <p>department name: ${sessionScope.employee.department.departmentName}</p>
        <c:choose>
            <c:when test="${sessionScope.employee.personalPhoneNumber.present}">
                <p>personligt nummer: ${sessionScope.employee.personalPhoneNumber.get()}</p>
            </c:when>
            <c:otherwise>
                <p>personligt nummer: ikke sat</p>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${sessionScope.employee.workPhoneNumber.present}">
                <p>arbejdsnummer: ${sessionScope.employee.workPhoneNumber.get()}</p>
            </c:when>
            <c:otherwise>
                <p>arbejdsnummer: ikke sat</p>
            </c:otherwise>
        </c:choose>

        <input class="btn btn-primary" type="button" value="Se 'mine' ordre">

        <input class="btn btn-primary" type="button" value="Se alle Ordre">

        <br>
        <br>

        <form action="change-employee-phonenumber" method="post">
            <label for="newPersonalPhoneNumber" style="padding: 10px">Nyt Personligt nummer</label>
            <div class="mb-3 text-center"><input style="height: 40px" class="form-control" id="newPersonalPhoneNumber" type="text" name="newPersonalPhoneNumber" placeholder="Nyt Personligt Nummer"></div>
            <input class="btn btn-primary" type="submit" value="Skift Personligt nummer">
        </form>
        <br>
        <br>

        <form action="change-employee-phonenumber" method="post">
            <label for="newWorkPhoneNumber" style="padding: 10px">Nyt Arbejdsnummer</label>
            <div class="mb-3 text-center"><input style="height: 40px" class="form-control" id="newWorkPhoneNumber" type="text" name="newWorkPhoneNumber" placeholder="Nyt Arbejdsnummer"></div>
            <input class="btn btn-primary" type="submit" value="Skift Arbejdsnummer">
        </form>

        <!-- ADMIN DEL -->
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <input class="btn btn-primary" type="button" value="Ændre materiale-katalog">

        <h1>Nyheder</h1>
        <p>NYHED 1</p>
        <p>NYHED 2</p>
        <p>NYHED 3</p>
    </jsp:body>

</t:pagetemplate>
