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

      My Orders
    </jsp:attribute>

    <jsp:attribute name="footer">
        My Orders
    </jsp:attribute>

    <jsp:body>

        <div style="margin-bottom: 2%">
            <div class="row">

                <div class="col-sm-11">
                    <h1>Mine ordrer</h1>
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
                    <th>Ordrenummer</th>
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
                            <input type="submit" value="Se mere om ordren">
                        </form>
                    </td>
                </tr>
                </c:forEach>
            </table>
        </div>

        <c:if test="${requestScope.load != null}">

            <div class=" row popup" id="popup" style="z-index: 6">

                <div class="row" style="width: 25%; height: 5%; background-color: white; border-left: black 1px solid; border-top: black 1px solid; border-right: black 1px solid; transform: translate(-2.3%, -130%); border-radius: 5px; position: absolute">
                    <p style="margin-top: 3%; margin-bottom: 5%; text-align: left">Ordrenummer: ${requestScope.carportOrder.id}   |  Status: ${requestScope.carportOrder.orderStatus.displayName}</p>
                </div>

                <div id="Seller" class="col-4 text-center">

                    <div class="row">
                        <h2>Sælger Information</h2>
                        <img style="display: block; margin: 0 auto; max-width: 35%; height: auto;" class="card-img-top" src="${pageContext.request.contextPath}/images/DefaultProfilePic.png" alt="SellerProfile">

                    </div>

                    <div class="row">
                        <c:choose>
                            <c:when test="${requestScope.carportOrder.employee.present}">
                                <p>Medarbejder: ${requestScope.carportOrder.employee.get().name}</p>
                            </c:when>
                            <c:otherwise>
                                <h1>Medarbejder: Ikke tildelt</h1>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="row">
                        <c:choose>
                            <c:when test="${requestScope.carportOrder.employee.present}">
                                <p>Medarbejder: ${requestScope.carportOrder.employee.get().email}</p>
                            </c:when>
                            <c:otherwise>
                                <h1>Medarbejder: Ikke tildelt</h1>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="row" style="margin-right: 1%; margin-top: 50%">
                    <h2>Kontakt sælger</h2>

                    <textarea id="messageToSeller" name="CustomerMessage" class="form-control"
                              rows="5" cols="33" placeholder="En besked til sælgeren"></textarea>

                    <button type="button" class="btn btn-primary" style="margin-top: 2%">Send til sælger</button>

                    </div>
                </div>

                <div id="Customer" class="col-4 text-center" style="border-left: grey dashed 1px">
                    <h2>Din Information</h2>
                    <img style="padding-bottom: 1%; display: block; margin: 0 auto; max-width: 35%; height: auto;" class="card-img-top" src="${pageContext.request.contextPath}/images/DefaultProfilePic.png" alt="SellerProfile">
                    <div class="row" id="userInfo">
                        <p>${sessionScope.user.name}</p>
                        <p>${sessionScope.user.email}</p>
                        <p>${sessionScope.user.personalPhoneNumber.get()}</p>
                        <p>${requestScope.carportOrder.address.address}</p>

                    </div>
                </div>

                <div id="CarportInfo" class="col-4" style="border-left: grey dashed 1px">

                    <h2>Carport Information</h2>


                    <p>Bredde: ${requestScope.carportOrder.width}</p>
                    <p>Længde: ${requestScope.carportOrder.length}</p>
                    <p>Højde: ${requestScope.carportOrder.minHeight}</p>
                    <p>Tag-type: ${requestScope.carportOrder.roof.type}</p>

                    <h2>Redskabs skur</h2>
                    <p>Bredde: ${requestScope.carportOrder.toolRoom.get().width} cm</p>
                    <p>Længde: ${requestScope.carportOrder.toolRoom.get().length} cm</p>
                    <p>Højde: ${requestScope.carportOrder.price.get()}</p>

                    <div class="row" style="margin-top: 40%; margin-left: 1%">
                    <h2>Remarks</h2>

                    <textarea name="CustomerMessage" class="form-control"
                              rows="5" cols="33" placeholder="Ingen bemærkelser" readonly>${requestScope.carportOrder.remarks.get()}</textarea>

                    </div>


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
                width: 90%;
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