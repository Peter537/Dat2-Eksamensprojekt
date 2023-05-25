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
                    <td>${order.employee.present ? order.employee.get().name : 'Ikke tildelt'}</td>
                    <td>${order.orderStatus.displayName}</td>
                    <td>${order.price.present ? order.getFormattedPrice() : 'endnu ikke bestemt'}</td>
                    <td>
                        <form action="detailed-order-info" method="post">
                            <input type="hidden" name="orderId" value="${order.id}">
                            <input type="hidden" name="fromJsp" value="customer">
                            <input type="submit" value="Se mere om ordren">
                        </form>
                    </td>
                </tr>
                </c:forEach>
            </table>
        </div>

        <c:if test="${requestScope.load != null}">

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
                                <form action="cancel-order" method="post">
                                <input type="submit"  class="btn btn-danger" style="margin-top: 1%; float: right" value="Aflys Ordre">
                                    <input type="hidden" name="orderId" value="${requestScope.carportOrder.id}">
                                </form>
                        </div>
                    </div>
                </div>
                <div class="row popup">
                    <div id="Seller" class="col-lg-4 col-md-12 text-center">
                        <div class="seller-info row">
                            <h2>Sælger Information</h2>
                            <img style="display: block; margin: 0 auto; max-width: 35%; height: auto;"
                                 class="card-img-top"
                                 src="${pageContext.request.contextPath}/images/DefaultProfilePic.png"
                                 alt="SellerProfile">
                        </div>
                        <div class="seller-employee row">
                            <p>${requestScope.carportOrder.employee.present ? requestScope.carportOrder.employee.get().name : 'Ikke tildelt'}</p>
                        </div>
                        <div class="seller-email row">
                            <p>${requestScope.carportOrder.employee.present ? requestScope.carportOrder.employee.get().email : 'Ikke tildelt'}</p>
                        </div>
                        <div>
                            <p>
                                Arbejdstelefonnummer: ${requestScope.carportOrder.employee.present ? requestScope.carportOrder.employee.get().workPhoneNumber.get() : 'Ikke tildelt'}</p>
                        </div>
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
                        <h2>Din Information</h2>
                        <img style="padding-bottom: 1%; display: block; margin: 0 auto; max-width: 35%; height: auto;"
                             class="card-img-top" src="${pageContext.request.contextPath}/images/DefaultProfilePic.png"
                             alt="SellerProfile">
                        <div class="customer-info row" id="userInfo">
                            <p>${sessionScope.user.name}</p>
                            <p>${sessionScope.user.email}</p>
                            <p>${sessionScope.user.personalPhoneNumber.get()}</p>
                            <p>${requestScope.carportOrder.address.address}</p>
                        </div>
                    </div>

                    <div id="CarportInfo" class="col-lg-4 col-md-12 text-center" style="border-left: 1px solid grey;">

                        <h2>Carport Information</h2>

                        <p>Bredde: ${requestScope.carportOrder.width}</p>
                        <p>Længde: ${requestScope.carportOrder.length}</p>
                        <p>Højde: ${requestScope.carportOrder.minHeight}</p>
                        <p>Tag-type: ${requestScope.carportOrder.roof.displayName}</p>

                        <h2>Redskabs skur</h2>
                        <p>
                            Bredde: ${requestScope.carportOrder.toolRoom.present ? requestScope.carportOrder.toolRoom.get().width + " cm" : 'Ikke sat'}</p>
                        <p>
                            Længde: ${requestScope.carportOrder.toolRoom.present ? requestScope.carportOrder.toolRoom.get().length + " cm": 'ikke sat'}</p>
                        <p>
                            Pris: ${requestScope.carportOrder.price.present ? requestScope.carportOrder.getFormattedPrice() : 'Endnu ikke sat'}
                            kr.</p>

                        <h2>Remarks</h2>
                        <textarea name="CustomerMessage" class="form-control" rows="5" placeholder="Ingen bemærkelser"
                                  readonly>${requestScope.carportOrder.remarks.present ? requestScope.carportOrder.remarks.get() : 'Ingen kommentarer'}</textarea>
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
                <h2>Bestilng Aflyst</h2>
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
        visibility: visible; !important;
        z-index: 20;
        }
        </style>

    </jsp:body>

</t:pagetemplate>