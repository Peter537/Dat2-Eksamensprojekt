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
<%--<%@page errorPage="error.jsp" isErrorPage="false" %>--%>

<t:pagetemplate>

    <jsp:attribute name="title">
        Customer orders overview
    </jsp:attribute>

    <jsp:attribute name="footer">
        See all orders
    </jsp:attribute>

    <jsp:body>

        <div style="margin-bottom: 2%">
            <div class="row">

                <div class="col-sm-11">
                    <h1>Se alle ordrer</h1>
                </div>

                <div class="col-sm-1">
                    <a href="ToProfileSite" class="btn btn-primary" style="margin-top: 10%;">Tilbage</a>
                </div>
            </div>
        </div>

        <div style="text-align: center; margin-left: 5%" class="text-center">
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Kundens navn</th>
                    <th>Kundens adresse</th>
                    <th>Medarbejderens navn</th>
                    <th>Status</th>
                    <th>Pris</th>
                    <th>Mere info</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="order" items="${requestScope.carportOrders}">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.customer.name}</td>
                    <td>${order.address.address}</td>
                    <c:choose>
                        <c:when test="${order.employee.present}">
                            <td>${order.employee.get().name}</td>
                        </c:when>
                        <c:otherwise>
                            <td>Ikke tildelt</td>
                        </c:otherwise>
                    </c:choose>
                    <td>${order.orderStatus.displayName}</td>
                    <c:choose>
                        <c:when test="${order.price.present}">
                            <td>${order.price.get()}</td>
                        </c:when>
                        <c:otherwise>
                            <td>Ikke beregnet</td>
                        </c:otherwise>
                    </c:choose>
                    <td>
                        <form action="DetailedOrderInfo" method="post">
                            <input type="hidden" name="orderId" value="${order.id}">
                            <input type="hidden" name="fromJsp" value="employee">
                            <input type="submit" value="Se mere om ordren">
                        </form>
                    </td>
                </tr>
                </c:forEach>
            </table>
        </div>

        <c:if test="${requestScope.load != null}">

            <div class="popup" id="popup">

                <table class="table table-striped table-bordered table-hover" style="overflow: auto">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>ordrestatus</th>
                        <th>Kundens adresse</th>
                        <th>Postnummer</th>
                        <th>Medarbejderens navn</th>
                        <th>Medarbejderens email</th>
                        <th>Kundens email</th>
                        <th>Kundens navn</th>
                        <th>Carport bredde</th>
                        <th>Carport længde</th>
                        <th>Carport minimum-højde</th>
                        <th>Tagets varenummer</th>
                        <th>Redskabsskur bredde</th>
                        <th>Redskabsskur længde</th>
                        <th>pris</th>
                        <th>bemærkninger</th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="carportOrder" value="${requestScope.carportOrder}">
                    </c:set>

                    <tr>
                        <td>${carportOrder.id}</td>
                        <td>${carportOrder.orderStatus.displayName}</td>
                        <td>${carportOrder.address.address}</td>
                        <td>${carportOrder.address.zip.zipCode}</td>
                        <c:choose>
                            <c:when test="${carportOrder.employee.present}">
                                <td>${carportOrder.employee.get().name}</td>
                                <td>${carportOrder.employee.get().email}</td>
                            </c:when>
                            <c:otherwise>
                                <td>Ikke tildelt</td>
                                <td>Ikke tildelt</td>
                            </c:otherwise>
                        </c:choose>

                        <td>${carportOrder.customer.email}</td>
                        <td>${carportOrder.customer.name}</td>
                        <td>${carportOrder.width}</td>
                        <td>${carportOrder.length}</td>
                        <td>${carportOrder.minHeight}</td>
                        <td>${carportOrder.roof.id}</td>


                        <c:choose>
                            <c:when test="${carportOrder.toolRoom.present}">
                                <td>${carportOrder.toolRoom.get().width}</td>
                                <td>${carportOrder.toolRoom.get().length}</td>
                            </c:when>
                            <c:otherwise>
                                <td>Ikke tilføjet</td>
                                <td>Ikke tilføjet</td>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${carportOrder.price.present}">
                                <td>${carportOrder.price.get()}</td>
                            </c:when>
                            <c:otherwise>
                                <td>Ikke beregnet</td>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${carportOrder.remarks.present}">
                                <td>${carportOrder.remarks.get()}</td>
                            </c:when>
                            <c:otherwise>
                                <td>Ikke tilføjet</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </table>


                <c:choose>
                    <c:when test="${!carportOrder.employee.present}">
                        <div>
                            <form action="EmployeeClaimOrder" method="post">
                                <input type="hidden" name="orderId" value="${carportOrder.id}">
                                <input type="submit" value="Tag ordre">
                            </form>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div>
                            <form action="" method="post">
                                <input class="form-control" type="number" name="makeOfferPrice" value="0">
                                <br>
                                <input type="hidden" name="orderId" value="${carportOrder.id}">
                                <input class="btn" type="submit" name="orderId" value="Lav tilbud">
                                <br>
                            </form>
                        </div>
                    </c:otherwise>
                </c:choose>
                <br>

                <div class="row">
                    <a type="button" class="btn" value="Luk" href="see-all-orders">Close</a>

                </div>

            </div>


        </c:if>

        <style>
            .popup {
                opacity: 95%;
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                width: 80%;
                height: 80%;
                padding: 20px;
                background-color: white;
                z-index: 10;
                border: 1px solid black;
                border-radius: 10px;
                visibility: visible;
            }

        </style>


    </jsp:body>

</t:pagetemplate>
