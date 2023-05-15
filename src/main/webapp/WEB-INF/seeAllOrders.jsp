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

        <div style="width: 80%; text-align: center; margin-left: 5%" class="text-center">
        <table class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Kundens navn</th>
                    <th>Kundens adresse</th>
                    <th>Medarbejderens navn</th>
                    <th>Status</th>
                    <th>Pris</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="order" value="${requestScope.order}">
                </c:set>

                    <tr>
                        <td>${order.id}</td>
                        <td>${order.customer.name}</td>
                        <td>${order.address.address}</td>
                        <td>${order.employee.get().name}</td>
                        <td>${order.orderStatus.displayName}</td>
                        <td>${order.price.get()} DKK</td>
                        <td>
                            <form action="DetailedOrderInfo" method="post">
                                <input type="hidden" name="orderId" value="${order.id}">
                                <input style="color: white" type="submit" value="Se mere om ordren">
                            </form>
                        </td>
                    </tr>
        </table>
        </div>

        <c:if test="${requestScope.load != null}">

            <div class="popup" id="popup">

                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>ordrestatus</th>
                        <th>Kundens adresse</th>
                        <th>Postnummer</th>
                        <th>Medarbejderens email</th>
                        <th>Kundens email</th>
                        <th>Carport bredde</th>
                        <th>Carport længde</th>
                        <th>Carport minimum-højde</th>
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
                        <td>${carportOrder.employee.get().email}</td>
                        <td>${carportOrder.customer.email}</td>
                        <td>${carportOrder.width}</td>
                        <td>${carportOrder.length}</td>
                        <td>${carportOrder.minHeight}</td>
                        <td>${carportOrder.toolRoom.get().width} cm</td>
                        <td>${carportOrder.toolRoom.get().length} cm</td>
                        <td>${carportOrder.price.get()}</td>
                        <td>${carportOrder.remarks.get()}</td>
                    </tr>
                </table>


                <div class="row">
                    <a type="button" class="btn" value="Luk" href="ToSeeAllOrders" >Close</a>

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
                z-index: 9999; /* set z-index higher than other elements to display the pop-up on top */
                border: 1px solid black;
                border-radius: 10px;
                visibility: visible;
            }

        </style>






    </jsp:body>

</t:pagetemplate>
