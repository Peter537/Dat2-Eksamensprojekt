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
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>

    <jsp:attribute name="title">
        Here is the catalog
    </jsp:attribute>

    <jsp:attribute name="footer">
        See catalog
    </jsp:attribute>

    <jsp:body>

        <div style="margin-bottom: 2%">
            <div class="row">

                <div class="col-sm-11">
                    <h1>Se kataloget</h1>
                </div>

                <div class="col-sm-1">
                    <a href="ToEmployeeSite" class="btn btn-primary" style="margin-top: 10%;">Tilbage</a>
                </div>
            </div>
        </div>

        <div style="width: 80%; text-align: center; margin-left: 5%" class="text-center">
            <h3>Tag</h3>
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Kvadratmeterpris</th>
                    <th>Type</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="roof" items="${requestScope.roofs}">
                    <tr>
                        <form action="save-catalog-item" method="post">
                            <td>${roof.id}</td>
                            <td><input type="number" name="squareMeterPrice" value="${roof.squareMeterPrice}">
                            </td>
                            <td>${roof.type}</td>
                            <td>
                                <input type="hidden" name="id" value="${roof.id}">
                                <input type="hidden" name="catalogItemType" value="roof">
                                <input type="hidden" name="roofType" value="PLASTIC_ROOF">
                                <input style="color: var(--color-light);" type="submit" value="Gem vare">
                        </form>
                        <form action="delete-catalog-item" method="post">
                            <input type="hidden" name="id" value="${roof.id}">
                            <input type="hidden" name="catalogItemType" value="roof">
                            <input style="color: var(--color-light);" type="submit" value="Slet vare">
                        </form>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <form action="save-catalog-item" method="post">
                        <td></td>
                        <td><input type="number" name="squareMeterPrice" placeholder="Kvadratmeterpris"></td>
                        <td>PLASTIC_ROOF</td>
                        <input type="hidden" name="catalogItemType" value="roof">
                        <input type="hidden" name="roofType" value="PLASTIC_ROOF">
                        <td>
                            <input style="color: var(--color-light);" type="submit" value="Tilføj vare">
                        </td>
                    </form>
                </tr>
                </tbody>
            </table>
            <br>
            <br>
            <br>

            <h3>Stolper</h3>
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Tykkelse</th>
                    <th>Bredde</th>
                    <th>Længde</th>
                    <th>Meter Price</th>
                    <th>Antal</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="pole" items="${requestScope.poles}">
                    <tr>
                        <form action="save-catalog-item" method="post">
                            <td>${pole.id}</td>
                            <td><input type="number" name="poleThickness" value="${pole.lumberType.thickness}"></td>
                            <td><input type="number" name="poleWidth" value="${pole.lumberType.width}"></td>
                            <td><input type="number" name="poleLength" value="${pole.length}"></td>
                            <td><input type="number" name="poleMeterPrice" value="${pole.lumberType.meterPrice}"></td>
                            <td><input type="number" name="amount" value="${pole.amount}"></td>
                            <td>
                                <input type="hidden" name="id" value="${pole.id}">
                                <input type="hidden" name="catalogItemType" value="pole">
                                <input type="hidden" name="lumberType" value="POLE">
                                <input style="color: var(--color-light);" type="submit" value="Gem vare">
                        </form>
                        <form action="delete-catalog-item" method="post">
                            <input type="hidden" name="id" value="${pole.id}">
                            <input type="hidden" name="catalogItemType" value="pole">
                            <input style="color: var(--color-light);" type="submit" value="Slet katalog vare">
                            </td>
                        </form>
                    </tr>
                </c:forEach>
                <tr>
                    <form action="save-catalog-item" method="post">
                        <td></td>
                        <td><input type="number" name="poleThickness" placeholder="Tykkelse"></td>
                        <td><input type="number" name="poleWidth" placeholder="Bredde"></td>
                        <td><input type="number" name="poleLength" placeholder="Længde"></td>
                        <td><input type="number" name="poleMeterPrice" placeholder="Meterpris"></td>
                        <td><input type="number" name="amount" placeholder="Antal"></td>
                        <input type="hidden" name="catalogItemType" value="pole">
                        <input type="hidden" name="lumberType" value="POLE">
                        <td>
                            <input style="color: var(--color-light);" type="submit" value="Tilføj vare">
                        </td>
                    </form>
                </tr>
                </tbody>
            </table>
            <br>
            <br>
            <br>

            <h3>Spærtræ</h3>
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Tykkelse</th>
                    <th>Bredde</th>
                    <th>Længde</th>
                    <th>Meter Price</th>
                    <th>Antal</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="rafter" items="${requestScope.rafters}">
                    <tr>
                        <form action="save-catalog-item" method="post">
                            <td>${rafter.id}</td>
                            <td><input type="number" name="poleThickness" value="${rafter.lumberType.thickness}"></td>
                            <td><input type="number" name="poleWidth" value="${rafter.lumberType.width}"></td>
                            <td><input type="number" name="poleLength" value="${rafter.length}"></td>
                            <td><input type="number" name="poleMeterPrice" value="${rafter.lumberType.meterPrice}"></td>
                            <td><input type="number" name="amount" value="${rafter.amount}"></td>
                            <td>
                                <input type="hidden" name="id" value="${rafter.id}">
                                <input type="hidden" name="catalogItemType" value="rafter">
                                <input type="hidden" name="lumberType" value="RAFTER">
                                <input style="color: var(--color-light);" type="submit" value="Gem vare">
                        </form>
                        <form action="delete-catalog-item" method="post">
                            <input type="hidden" name="id" value="${rafter.id}">
                            <input type="hidden" name="catalogItemType" value="rafter">
                            <input style="color: var(--color-light);" type="submit" value="Slet katalog vare">
                        </form>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <form action="save-catalog-item" method="post">
                        <td></td>
                        <td><input type="number" name="poleThickness" placeholder="Tykkelse"></td>
                        <td><input type="number" name="poleWidth" placeholder="Bredde"></td>
                        <td><input type="number" name="poleLength" placeholder="Længde"></td>
                        <td><input type="number" name="poleMeterPrice" placeholder="Meterpris"></td>
                        <td><input type="number" name="amount" placeholder="Antal"></td>
                        <input type="hidden" name="catalogItemType" value="rafter">
                        <input type="hidden" name="lumberType" value="RAFTER">
                        <td>
                            <input style="color: var(--color-light);" type="submit" value="Tilføj vare">
                        </td>
                    </form>
                </tr>
                </tbody>
            </table>
            <br>
            <br>
            <br>
        </div>
    </jsp:body>

</t:pagetemplate>