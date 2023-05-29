<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>

    <jsp:attribute name="footer">
        Se alle ordrer
    </jsp:attribute>

    <jsp:body>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seeAllOrdersStyle.css">

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

        <div class="row text-center">
            <form action="detailed-search-see-all-orders" method="post">
                <label for="searchId">Søg på ID</label>
                <input type="number" name="searchId" id="searchId" placeholder="Søg på Id">
                <label for="searchCustomerEmail">Søg på Kunde Email</label>
                <input type="email" name="searchCustomerEmail" id="searchCustomerEmail"
                       placeholder="Søg på kunde email">
                <input type="hidden" name="fromJsp" value="see-all-orders">
                <input class="btn btn-primary" type="submit" value="Søg">
            </form>
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
                    <th>Detaljer</th>
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
                            <td>${order.getFormattedPrice()} kr.</td>
                        </c:when>
                        <c:otherwise>
                            <td>Ikke beregnet</td>
                        </c:otherwise>
                    </c:choose>
                    <td>
                        <form action="detailed-order-info" method="post">
                            <input type="hidden" name="orderId" value="${order.id}">
                            <input type="hidden" name="fromJsp" value="see-all-orders">
                            <input type="submit" value="Se mere om ordren">
                        </form>
                    </td>
                </tr>
                </c:forEach>
            </table>
        </div>

        <c:if test="${requestScope.load != null}"> <%-- This is important, this should only be shown if an order is selected for detailed view --%>
            <div class="row" id="popup" style="z-index: 6; position: sticky">
            <div class="popup-header row">
                <div class="col-sm-8"
                     style="border: 1px solid black; border-bottom: 0; border-radius: 4px 4px 0 0; background: white;">
                    <div class="row align-items-center">
                        <div class="col-sm-9">
                            <h4 style="margin-top: 1%; text-align: left">
                                Ordrenummer: ${requestScope.carportOrder.id} |
                                Status: ${requestScope.carportOrder.orderStatus.displayName}
                            </h4>
                        </div>
                        <div class="col-sm-2" style="float: left">
                            <c:choose>
                                <c:when test="${requestScope.carportOrder.orderStatus.getStatus().equals('ORDER_CANCELLED')}">
                                    <h5>Ordre annulleret</h5>
                                </c:when>
                                <c:when test="${!requestScope.carportOrder.employee.present}">
                                    <form action="employee-claim-order" method="post">
                                        <input type="hidden" name="orderId" value="${requestScope.carportOrder.id}">
                                        <input type="hidden" name="fromJsp" value="employee">
                                        <input class="btn btn-primary" type="submit" value="Tag ordre"
                                               style="width: 80%">
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${requestScope.carportOrder.price.present}">
                                            <h5>Pris: ${requestScope.carportOrder.getFormattedPrice()} kr.</h5>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${requestScope.carportOrder.employee.get().email.equals(sessionScope.user.email)}">
                                                    <form action="employee-make-offer" method="post">
                                                        <input id="dealMaker" name="priceOffer" type="number"
                                                               placeholder="Giv Tilbud i DKK">
                                                        <input type="hidden" name="fromJsp" value="employee">
                                                        <input type="hidden" name="orderId"
                                                               value="${requestScope.carportOrder.id}">
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <p>Ordre taget af anden medarbejder</p>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
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
                        <c:choose>
                            <c:when test="${requestScope.carportOrder.customer.getProfilePicture() != null}">
                                <img style="padding-bottom: 1%; display: block; margin: 0 auto; max-width: 35%; height: auto;"
                                     class="card-img-top"
                                     src="<c:url value='data:image/jpeg;base64,${requestScope.carportOrder.customer.getProfilePicture()}'/>"
                                     alt="CustomerProfile">
                            </c:when>
                            <c:otherwise>
                                <img style="padding-bottom: 1%; display: block; margin: 0 auto; max-width: 35%; height: auto;"
                                     class="card-img-top"
                                     src="${pageContext.request.contextPath}/images/DefaultProfilePic.png"
                                     alt="CustomerProfile">
                            </c:otherwise>
                        </c:choose>
                        <div class="customer-info row" id="userInfo">
                            <p>${requestScope.carportOrder.customer.name}</p>
                            <p>${requestScope.carportOrder.customer.email}</p>
                            <p>${requestScope.carportOrder.customer.personalPhoneNumber.present ? requestScope.carportOrder.customer.personalPhoneNumber.get() : 'intet telefonnummer'}</p>
                            <p>${requestScope.carportOrder.address.address}</p>
                        </div>
                        <div class="customer-contact row" style="margin-right: 1%">
                            <h2>Kontakt kunde</h2>
                            <textarea id="messageToCustomer" name="SellerMessage" class="form-control" rows="5"
                                      cols="33"
                                      placeholder="En besked til kunden"></textarea>
                            <button type="button" class="btn btn-primary" style="margin-top: 2%">Send til kunde</button>
                        </div>
                        <br>
                    </div>

                    <div id="Seller" class="col-lg-5 col-md-12 text-center" style="border-left: 1px solid grey;">
                        <div class="seller-info row">
                            <h2>Sælgerens information</h2>
                            <c:choose>
                                <c:when test="${requestScope.carportOrder.employee.present}">

                                    <img style="display: block; margin: 0 auto; max-width: 35%; height: auto;"
                                         class="card-img-top"
                                         src="<c:url value='data:image/jpeg;base64,${requestScope.carportOrder.employee.get().getProfilePicture()}'/>"
                                         alt="SellerProfile">
                                </c:when>
                                <c:otherwise>
                                    <img style="display: block; margin: 0 auto; max-width: 35%; height: auto;"
                                         class="card-img-top"
                                         src="${pageContext.request.contextPath}/images/DefaultProfilePic.png"
                                         alt="DefaultSellerProfile">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <p>Navn: ${requestScope.carportOrder.employee.present ? requestScope.carportOrder.employee.get().name : "medarbejder ikke tildelt"}</p>
                        <p>E-mail: ${requestScope.carportOrder.employee.present ? requestScope.carportOrder.employee.get().email : "medarbejder ikke tildelt"}</p>
                        <p>Arbejdstelefonnummer: ${requestScope.carportOrder.employee.present && requestScope.carportOrder.employee.get().workPhoneNumber.present ? requestScope.carportOrder.employee.get().workPhoneNumber.get() : 'intet telefonnummer'}</p>

                        <br>
                        <iframe src="order-partslist-frame" width="100%" height="350px" sandbox="allow-forms"
                                onload="this.style.height=(this.contentWindow.document.body.scrollHeight+20)+'px';"></iframe>
                        <br>

                        <div style="padding-bottom: 3%">
                        <a href="partslist-to-pdf" methods="post" class="btn btn-primary">Generer PDF</a>
                        <br>
                        <a href="GenerateSCAD" class="btn btn-primary">Generer SCAD-filer</a>
                        <br>
                    </div>
                    </div>


                    <div id="CarportInfo" class="col-lg-3 col-md-12 text-center" style="border-left: 1px solid grey;">

                        <h2>Carport Information</h2>

                        <p>Bredde: ${requestScope.carportOrder.width}</p>
                        <p>Længde: ${requestScope.carportOrder.length}</p>
                        <p>Højde: ${requestScope.carportOrder.minHeight}</p>
                        <p>Tag-type: ${requestScope.carportOrder.roof.displayName}</p>

                        <h2>Redskabs skur</h2>
                        <p>
                            Bredde: ${requestScope.carportOrder.toolRoom.present ? requestScope.carportOrder.toolRoom.get().width : 'Ikke sat'}
                            cm</p>
                        <p>
                            Længde: ${requestScope.carportOrder.toolRoom.present ? requestScope.carportOrder.toolRoom.get().length : 'ikke sat'}
                            cm</p>
                        <p>
                            Pris: ${requestScope.carportOrder.price.present ? requestScope.carportOrder.getFormattedPrice() : 'Endnu ikke sat'}
                            kr.</p>

                        <h2>Remarks</h2>
                        <textarea name="CustomerMessage" class="form-control" rows="5" placeholder="Ingen bemærkelser"
                                  readonly>${requestScope.carportOrder.remarks.present ? requestScope.carportOrder.remarks.get() : 'ingen kommentarer'}</textarea>

                        <c:if test="${requestScope.carportOrder.employee.present}">
                            <c:if test="${requestScope.carportOrder.employee.get().equals(sessionScope.user)}">
                                <form action="generate-custom-partslist" method="post">
                                    <br>
                                    <input class="btn btn-primary" type="submit" value="edit order">
                                    <input type="hidden" name="orderId" value="${requestScope.carportOrder.id}">
                                </form>
                            </c:if>
                        </c:if>
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