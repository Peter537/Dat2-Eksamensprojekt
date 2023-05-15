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

            <div class=" row popup" id="popup">

                <div  id="Seller" class="col-4 text-center">

                    <div class="row" style="margin-bottom: 2%">
                        <h1>Sælger</h1>
                        <img style="height: 250px; width: 250px; text-align: center" class="card-img-top"
                             src="${pageContext.request.contextPath}/images/DefaultProfilePic.png" alt="SellerProfile">
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

                    <h2>Kontakt sælger</h2>

                    <textarea id="story" name="remarks" class="form-control"
                              rows="5" cols="33" placeholder="En besked til sælgeren">
                    </textarea>

                    <button type="button" class="btn btn-primary" style="margin-top: 2%">Send til sælger</button>



                </div>

                <div  id="Customer" class="col-4">

                </div>

                <div id="CarportInfo" class="col-4">

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