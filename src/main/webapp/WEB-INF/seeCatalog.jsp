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
                        <td>${roof.id}</td>
                        <td>${roof.squareMeterPrice}</td>
                        <td>${roof.type}</td>
                        <td>
                            <form action="delete-catalog-item" method="post">
                                <input type="hidden" name="id" value="${roof.id}">
                                <input type="hidden" name="catalogItemType" value="roof">
                                <input style="color: var(--color-light);" type="submit" value="Slet katalog vare">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <form action="create-catalog-item" method="post">
                <label>Kvadratmeterpris:
                    <input type="number" name="squareMeterPrice">
                </label>
                <input type="hidden" name="roofType" value="PLASTIC_ROOF">
                <input type="hidden" name="catalogItemType" value="roof">
                <input type="submit" value="Tilføj tag">
            </form>
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
                        <td>${pole.id}</td>
                        <td>${pole.length}</td>
                        <td>${pole.lumberType.thickness}</td>
                        <td>${pole.lumberType.width}</td>
                        <td>${pole.lumberType.meterPrice}</td>
                        <td>${pole.amount}</td>
                        <td>
                            <form action="delete-catalog-item" method="post">
                                <input type="hidden" name="id" value="${pole.id}">
                                <input type="hidden" name="catalogItemType" value="pole">
                                <input style="color: var(--color-light);" type="submit" value="Slet katalog vare">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <form action="create-catalog-item" method="post">
                <label>Length:
                    <input type="number" name="poleLength">
                </label>
                <label>Thickness:
                    <input type="number" name="poleThickness">
                </label>
                <label>Width:
                    <input type="number" name="poleWidth">
                </label>
                <label>Meterpris:
                    <input type="number" name="poleMeterPrice">
                </label>
                <label>Antal:
                    <input type="number" name="amount">
                </label>
                <input type="hidden" name="lumberType" value="POLE">
                <input type="hidden" name="catalogItemType" value="pole">
                <input type="submit" value="Tilføj stolpe">
            </form>
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
                        <td>${rafter.id}</td>
                        <td>${rafter.length}</td>
                        <td>${rafter.lumberType.thickness}</td>
                        <td>${rafter.lumberType.width}</td>
                        <td>${rafter.lumberType.meterPrice}</td>
                        <td>${rafter.amount}</td>
                        <td>
                            <form action="delete-catalog-item" method="post">
                                <input type="hidden" name="id" value="${rafter.id}">
                                <input type="hidden" name="catalogItemType" value="rafter">
                                <input style="color: var(--color-light);" type="submit" value="Slet katalog vare">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <form action="create-catalog-item" method="post">
                <label>Length:
                    <input type="number" name="poleLength">
                </label>
                <label>Thickness:
                    <input type="number" name="poleThickness">
                </label>
                <label>Width:
                    <input type="number" name="poleWidth">
                </label>
                <label>Meterpris:
                    <input type="number" name="poleMeterPrice">
                </label>
                <label>Antal:
                    <input type="number" name="amount">
                </label>
                <input type="hidden" name="lumberType" value="RAFTER">
                <input type="hidden" name="catalogItemType" value="rafter">
                <input type="submit" value="Tilføj spærtræ">
            </form>
            <br>
            <br>
            <br>
        </div>
    </jsp:body>

</t:pagetemplate>