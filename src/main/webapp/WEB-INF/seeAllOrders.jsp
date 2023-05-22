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
                    <a href="${sessionScope.myhome}" class="btn btn-primary" style="margin-top: 10%;">Tilbage</a>
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

            <div class="row" id="popup" style="z-index: 6; position: sticky">
                <div class="popup-header row">
                    <div class="col-sm-8" style="border: 1px solid black; border-bottom: 0; border-radius: 4px 4px 0 0; background: white;">
                        <div class="row align-items-center">
                            <div class="col-sm-9">
                                <h4 style="margin-top: 1%; text-align: left">
                                    Ordrenummer: ${requestScope.carportOrder.id} |
                                    Status: ${requestScope.carportOrder.orderStatus.displayName}
                                </h4>
                            </div>
                            <div class="col-sm-2" style="float: left">
                                <c:choose>
                                    <c:when test="${!requestScope.carportOrder.employee.present}">
                                        <form action="EmployeeClaimOrder" method="post">
                                            <input type="hidden" name="orderId" value="${requestScope.carportOrder.id}">
                                            <input type="hidden" name="fromJsp" value="employee">
                                            <input class="btn btn-primary" type="submit" value="Tag ordre" style="width: 80%">
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="EmployeeClaimOrder" method="post">
                                        <input id="dealMaker" type="number" value="giv pris" placeholder="Giv Tilbud">
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                    <div class="col-3"></div>

                    <div class="col-1">
                        <a href="${requestScope.from}" type="button" class="closebtn" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </a>
                    </div>

                    <div class="row popup">
                    <div id="Customer" class="col-lg-4 col-md-12 text-center">
                        <h2>Kundens Information</h2>
                        <img style="padding-bottom: 1%; display: block; margin: 0 auto; max-width: 35%; height: auto;"
                             class="card-img-top" src="${pageContext.request.contextPath}/images/DefaultProfilePic.png"
                             alt="SellerProfile">
                        <div class="customer-info row" id="userInfo">
                            <p>${requestScope.carportOrder.customer.name}</p>
                            <p>${requestScope.carportOrder.customer.email}</p>
                            <p>${requestScope.carportOrder.customer.personalPhoneNumber.present ? requestScope.carportOrder.customer.personalPhoneNumber.get() : 'intet telefonnummer'}</p>
                            <p>${requestScope.carportOrder.address.address}</p>
                        </div>
                        <div class="customer-contact row" style="margin-right: 1%">
                            <h2>Kontakt kunde</h2>
                            <textarea id="messageToCustomer" name="SellerMessage" class="form-control" rows="5" cols="33"
                                      placeholder="En besked til kunden"></textarea>
                            <button type="button" class="btn btn-primary" style="margin-top: 2%">Send til kunde</button>
                        </div>
                        <br>
                    </div>

                    <div id="Seller" class="col-lg-5 col-md-12 text-center" style="border-left: 1px solid grey;">
                        <div class="seller-info row">
                            <h2>Din Information</h2>
                            <img style="display: block; margin: 0 auto; max-width: 35%; height: auto;" class="card-img-top"
                                 src="${pageContext.request.contextPath}/images/DefaultProfilePic.png" alt="SellerProfile">
                        </div>
                        <p>${sessionScope.user.name}</p>
                        <p>${sessionScope.user.email}</p>
                        <p>Privat telefonnummer: ${sessionScope.user.personalPhoneNumber.present ? sessionScope.user.personalPhoneNumber.get() : 'intet telefonnummer'}</p>
                        <p>Arbejds telefonnummer: ${sessionScope.user.workPhoneNumber.present ? sessionScope.user.workPhoneNumber.get() : 'intet telefonnummer'}</p>
                        <p>${requestScope.carportOrder.address.address}</p>

                        <br>
                        <iframe src="ToPartslistFrame" width="100%" height="350px" sandbox="allow-forms" onload="this.style.height=(this.contentWindow.document.body.scrollHeight+20)+'px';"></iframe>
                    </div>


                    <div id="CarportInfo" class="col-lg-3 col-md-12 text-center" style="border-left: 1px solid grey;">

                        <h2>Carport Information</h2>

                        <p>Bredde: ${requestScope.carportOrder.width}</p>
                        <p>Længde: ${requestScope.carportOrder.length}</p>
                        <p>Højde: ${requestScope.carportOrder.minHeight}</p>
                        <p>Tag-type: ${requestScope.carportOrder.roof.type}</p>

                        <h2>Redskabs skur</h2>
                        <p>Bredde: ${requestScope.carportOrder.toolRoom.present ? requestScope.carportOrder.toolRoom.get().width : 'Ikke sat'} cm</p>
                        <p>Længde: ${requestScope.carportOrder.toolRoom.present ? requestScope.carportOrder.toolRoom.get().length : 'ikke sat'} cm</p>
                        <p>Pris: ${requestScope.carportOrder.price.present ? requestScope.carportOrder.price.get() : 'Endnu ikke sat'} kr.</p>

                        <h2>Remarks</h2>
                        <textarea name="CustomerMessage" class="form-control" rows="5" placeholder="Ingen bemærkelser"
                                  readonly>${requestScope.carportOrder.remarks.present ? requestScope.carportOrder.remarks.get() : 'ingen kommentarer'}</textarea>
                    </div>
                </div>
            </div>
            <br>
            <br>
            <br>
        </c:if>

        <style>
            .popup {
                opacity: 95%;
                background-color: white;
                z-index: 10;
                border: 1px solid black;
                border-radius: 10px;
                visibility: visible;
            }

            .closebtn {
                border: none !important;
                color: black;
                font-weight: bold;
                float: right;
                font-size: 22px;
                line-height: 20px;
                cursor: pointer;
                transition: 0.3s;
            }

            /* When moving the mouse over the close button */
            .closebtn:hover {
                color: white;
            }

        </style>


    </jsp:body>

</t:pagetemplate>
