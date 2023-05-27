<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="footer">
        Se mine ordrer
    </jsp:attribute>

    <jsp:body>

        <div style="margin-bottom: 2%">
            <div class="row">

                <div class="col-sm-11">
                    <h1>Se mine ordrer</h1>
                </div>

                <div class="col-sm-1">
                    <a href="employee-site" class="btn btn-primary" style="margin-top: 10%;">Tilbage</a>
                </div>
            </div>
        </div>

        <div class="row text-center">
            <form action="detailed-search-see-all-orders" method="post">
                <label for="searchId">Søg på ID</label>
                <input type="number" name="searchId" id="searchId" placeholder="Søg på Id">
                <label for="searchCustomerEmail">Søg på Kunde Email</label>
                <input type="email" name="searchCustomerEmail" id="searchCustomerEmail" placeholder="Søg på kunde email">
                <input type="hidden" name="fromJsp" value="see-employee-orders">
                <input class="btn btn-primary" type="submit" value="Søg">
            </form>
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
                <c:forEach var="order" items="${requestScope.carportOrders}">
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.customer.name}</td>
                        <td>${order.address.address}</td>
                        <td>${order.employee.get().name}</td>
                        <td>${order.orderStatus.displayName}</td>
                        <c:choose>
                            <c:when test="${order.price.present}">
                                <td>${order.getFormattedPrice()} DKK</td>
                            </c:when>
                            <c:otherwise>
                                <td>Ikke beregnet</td>
                            </c:otherwise>
                        </c:choose>
                        <td>
                            <form action="detailed-order-info" method="post">
                                <input type="hidden" name="orderId" value="${order.id}">
                                <input type="hidden" name="fromJsp" value="see-employee-orders">
                                <input style="color: var(--color-light);" type="submit" value="Se mere om ordren">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <c:if test="${requestScope.load != null}"> <%-- This part is not functional but to avoid unintended errors, do not remove --%>

            <div class="row" id="popup" style="z-index: 6; position: sticky">
                <div class="popup-header row">
                    <div class="col-sm-6"
                         style="border: 1px solid black;border-bottom: 0;border-radius: 4px 4px 0 0;background: white;">
                        <h3 style="margin-top: 1%; text-align: left">
                            Ordrenummer: ${requestScope.carportOrder.id} |
                            Status: ${requestScope.carportOrder.orderStatus.displayName}
                            <a href="${requestScope.from}" class="btn btn-primary" style="margin-left: 2%;">Luk</a>
                        </h3>
                    </div>
                </div>
                <div class="row popup">
                    <div id="Customer" class="col-lg-4 col-md-12 text-center" style="border-left: 1px solid grey;">
                        <h2>Kundens Information</h2>
                        <img style="padding-bottom: 1%; display: block; margin: 0 auto; max-width: 35%; height: auto;"
                             class="card-img-top" src="${pageContext.request.contextPath}/images/DefaultProfilePic.png"
                             alt="SellerProfile">
                        <div class="customer-info row" id="userInfo">
                            <p>${sessionScope.user.name}</p>
                            <p>${sessionScope.user.email}</p>
                            <p>${sessionScope.user.personalPhoneNumber.get()}</p>
                            <p>${requestScope.carportOrder.address.address}</p>
                        </div>
                        <div class="customer-contact row" style="margin-right: 1%">
                            <h2>Kontakt kunde</h2>
                            <textarea id="messageToCustomer" name="SellerMessage" class="form-control" rows="5"
                                      cols="33"
                                      placeholder="En besked til kunden"></textarea>
                            <button type="button" class="btn btn-primary" style="margin-top: 2%">Send til kunde</button>
                        </div>
                    </div>

                    <div id="Seller" class="col-lg-4 col-md-12 text-center">
                        <div class="seller-info row">
                            <h2>Din Information</h2>
                            <img style="display: block; margin: 0 auto; max-width: 35%; height: auto;"
                                 class="card-img-top"
                                 src="${pageContext.request.contextPath}/images/DefaultProfilePic.png"
                                 alt="SellerProfile">
                        </div>
                        <div class="seller-employee row">
                            <c:choose>
                                <c:when test="${requestScope.carportOrder.employee.present}">
                                    <p>Medarbejder: ${requestScope.carportOrder.employee.get().name}</p>
                                </c:when>
                                <c:otherwise>
                                    <h1>Medarbejder: Ikke tildelt</h1>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="seller-email row">
                            <c:choose>
                                <c:when test="${requestScope.carportOrder.employee.present}">
                                    <p>Email: ${requestScope.carportOrder.employee.get().email}</p>
                                </c:when>
                                <c:otherwise>
                                    <h1>Email: Ikke tildelt</h1>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div id="CarportInfo" class="col-lg-4 col-md-12 text-center" style="border-left: 1px solid grey;">

                        <h2>Carport Information</h2>

                        <p>Bredde: ${requestScope.carportOrder.width}</p>
                        <p>Længde: ${requestScope.carportOrder.length}</p>
                        <p>Højde: ${requestScope.carportOrder.minHeight}</p>
                        <p>Tag-type: ${requestScope.carportOrder.roof.displayName}</p>

                        <h2>Redskabs skur</h2>
                        <p>Bredde: ${requestScope.carportOrder.toolRoom.get().width} cm</p>
                        <p>Længde: ${requestScope.carportOrder.toolRoom.get().length} cm</p>
                        <h1>Pris: ${requestScope.carportOrder.price.get()} kr.</h1>

                        <h2>Remarks</h2>
                        <textarea name="CustomerMessage" class="form-control" rows="5" placeholder="Ingen bemærkelser"
                                  readonly>${requestScope.carportOrder.remarks.get()}</textarea>
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
        </style>
    </jsp:body>

</t:pagetemplate>