<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="footer">
        Mine ordrer
    </jsp:attribute>

    <jsp:body>

        <div style="margin-bottom: 2%">
            <div class="row">

                <div class="col-sm-11">
                    <h1>Mine ordrer</h1>
                </div>

                <div class="col-sm-1">
                    <a href="customer-site" class="btn btn-primary" style="margin-top: 10%;">Tilbage</a>
                </div>
            </div>
        </div>

        <div style="text-align: center; margin-left: 5%" class="text-center">
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>Ordrenummer</th>
                    <th>Kundens navn</th>
                    <th>Adresse</th>
                    <th>Medarbejderens navn</th>
                    <th>Status</th>
                    <th>Pris</th>
                    <th>Detaljer</th>
                </tr>
                </thead>
                <tbody>

                <c:choose>
                    <c:when test="${requestScope.carportOrders.size() == 0}">
                        <tr>
                            <td colspan="7" style="text-align: center">Du har ingen ordrer</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="order" items="${requestScope.carportOrders}">
                            <tr>
                                <td>${order.id}</td>
                                <td>${order.customer.name}</td>
                                <td>${order.address.address}</td>
                                <td>${order.employee.present ? order.employee.get().name : 'Ikke tildelt'}</td>
                                <td>${order.orderStatus.displayName}</td>
                                <td>${order.price.present ? order.getFormattedPrice() : 'Endnu ikke bestemt'} ${order.price.present ? ' kr.' : ''}</td>
                                <td>
                                    <form action="detailed-order-info" method="post">
                                        <input type="hidden" name="orderId" value="${order.id}">
                                        <input type="hidden" name="fromJsp" value="customer">
                                        <input type="submit" value="Se mere om ordren">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </table>
        </div>

        <c:if test="${requestScope.load != null}"> <%-- This is important, this should only be shown if an order is selected for detailed view --%>
            <div class="row" id="popup"
            style="position: sticky; opacity: 95%; background-color: white; z-index: 10; border: 1px solid black; border-radius: 10px; visibility: visible">
            <div class="popup-header row">
                <div class="col-sm-8"
                     style="border: 1px solid black; border-bottom: 0; border-radius: 4px 4px 0 0; background: white; transform: translateY(-95%)">
                    <div class="row align-items-center">
                        <div class="col-sm-9">
                            <h4 style="margin-top: 1%; text-align: left">
                                Ordrenummer: ${requestScope.carportOrder.id} |
                                Status: ${requestScope.carportOrder.orderStatus.displayName}
                            </h4>
                        </div>
                        <div class="col-sm-3">
                            <c:if test="${!requestScope.carportOrder.orderStatus.getStatus().equals('ORDER_CANCELLED')}">
                                <form action="cancel-order" method="post">
                                    <input type="submit" class="btn btn-danger" style="margin-top: 1%; float: right"
                                           value="Aflys ordre">
                                    <input type="hidden" name="orderId" value="${requestScope.carportOrder.id}">
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="row popup">
                    <div id="Seller" class="col-lg-4 col-md-12 text-center" style="padding-left: 2%">
                        <div class="seller-info row">
                            <h2>Sælger information</h2>
                            <img style="display: block; margin: 0 auto; max-width: 35%; height: auto;"
                                 class="card-img-top"
                                 src="${pageContext.request.contextPath}/images/DefaultProfilePic.png"
                                 alt="SellerProfile">
                        </div>
                        <c:choose>
                            <c:when test="${requestScope.carportOrder.employee.present}">
                                <div class="seller-employee row">
                                    <p>${requestScope.carportOrder.employee.get().name}</p>
                                </div>
                                <div class="seller-email row">
                                    <p>${requestScope.carportOrder.employee.get().email}</p>
                                </div>
                                <div>
                                    <p>
                                        Telefonnummer: ${requestScope.carportOrder.employee.get().workPhoneNumber.get()}</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div>
                                    <h3>Din ordre er ikke blevet tildelt en sælger endnu.</h3>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <div class="seller-contact row" style="margin-right: 1%">
                            <h2>Kontakt sælger</h2>
                            <textarea id="messageToSeller" name="CustomerMessage" class="form-control" rows="5"
                                      cols="33"
                                      placeholder="En besked til sælgeren"></textarea>
                            <button type="button" class="btn btn-primary" style="margin-top: 2%">Send til sælger
                            </button>
                        </div>
                    </div>

                    <div id="Customer" class="col-lg-4 col-md-12 text-center" style="border-left: 1px solid grey;">
                        <h2>Din information</h2>
                        <img style="padding-bottom: 1%; display: block; margin: 0 auto; max-width: 35%; height: auto;"
                             class="card-img-top" src="${pageContext.request.contextPath}/images/DefaultProfilePic.png"
                             alt="SellerProfile">
                        <div class="customer-info row" id="userInfo">
                            <p>${sessionScope.user.name}</p>
                            <p>${sessionScope.user.email}</p>
                            <p>${sessionScope.user.personalPhoneNumber.present ? sessionScope.user.personalPhoneNumber.get() : 'Intet tlf. nummer sat'}</p>
                            <p>${requestScope.carportOrder.address.address}</p>
                        </div>
                            <%--black horizontal line--%>
                        <div style="border-bottom: 1px black solid"></div>

                    </div>

                    <div id="CarportInfo" class="col-lg-4 col-md-12 text-center" style="border-left: 1px solid grey;">

                        <h2>Carport information</h2>

                        <p>Bredde: ${requestScope.carportOrder.width} cm</p>
                        <p>Længde: ${requestScope.carportOrder.length} cm</p>
                        <p>Højde: ${requestScope.carportOrder.minHeight} cm</p>
                        <p>Tagtype: ${requestScope.carportOrder.roof.displayName}</p>

                        <h2>Redskabs skur</h2>
                        <p>
                            Bredde: ${requestScope.carportOrder.toolRoom.present ? requestScope.carportOrder.toolRoom.get().width : 'Ikke sat'} ${requestScope.carportOrder.toolRoom.present ? 'cm.' : ''}</p>
                        <p>
                            Længde: ${requestScope.carportOrder.toolRoom.present ? requestScope.carportOrder.toolRoom.get().length : 'Ikke sat'} ${requestScope.carportOrder.toolRoom.present ? 'cm.' : ''}</p>
                        <p>
                            Pris: ${requestScope.carportOrder.price.present ? requestScope.carportOrder.formattedPrice.concat(" kr.") : 'Endnu ikke sat'}</p>

                        <h2>Bemærkninger</h2>
                        <textarea name="CustomerMessage" class="form-control" rows="5" placeholder="Ingen bemærkninger"

                                  readonly>${requestScope.carportOrder.remarks.present ? requestScope.carportOrder.remarks.get() : 'Ingen bemærkninger'}</textarea>
                    </div>
                </div>
            </div>
            <br>
            <br>
            <br>
        </c:if>

        <c:if test="${requestScope.cancel != null}">

            <div class="cancelPopup">
                <div>
                    <img style="width: 100px; border-radius: 50%"
                         src="${pageContext.request.getContextPath()}/images/greenTickMark.jpg">
                </div>
                <div style="color: white">
                    <h2>Bestilling Aflyst</h2>
                    <p>Din bestilling er hermed aflyst.
                </div>
                <a class="btn" type="button" href="customer-site">OK</a>
            </div>
        </c:if>


        <style>


            .cancelPopup {
                width: 35%;
                height: 40%;
                position: absolute;
                background-color: #75b273;
                border: limegreen 4px solid;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                text-align: center;
                color: #333;
                transition: transform 0.35s ease-in-out;
                box-shadow: black 0px 0px 10px 0px;
                visibility: visible;
            !important;
                z-index: 20;
            }
        </style>

    </jsp:body>

</t:pagetemplate>