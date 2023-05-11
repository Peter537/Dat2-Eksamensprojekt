<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>

    <jsp:attribute name="title">
        You can log in here
    </jsp:attribute>

    <jsp:attribute name="footer">
            Login
    </jsp:attribute>

    <jsp:body>
        <style>
            /*below is formula*/

            .btn {
                padding: 10px 60px;
                font-size: 20px;
                cursor: pointer;

            }

            .popup {
                width: 400px;
                background: #fff;
                border-radius: 6px;
                position: absolute;
                top: 0;
                left: 50%;
                transform: translate(-50%, -50%) scale(0.1);
                text-align: center;
                padding: 0 30px 30px;
                color: #333;
                visibility: hidden;
                transition: transform 0.3s ease-in-out;
            }


            .popup img {
                width: 100px;
                margin: -50px;
                border-radius: 50%;
            }

            .open-popup {
                visibility: visible !important;
                top: 50%;
                transform: translate(-50%, -50%) scale(1);
                transition: 0.3s ease-in-out;

            }
        </style>


        <section class="text-center">
            <ul class="nav nav-pills mb-3" id="tab-list" role="tablist">
                <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="dimensions-tab" data-bs-toggle="pill"
                            data-bs-target="#dimensions"
                            type="button" role="tab" aria-controls="dimensions" aria-selected="true">Dimensioner
                    </button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="toolshed-tab" data-bs-toggle="pill" data-bs-target="#toolshed"
                            type="button"
                            role="tab" aria-controls="toolshed" aria-selected="false">Redskabsskur
                    </button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="contact-tab" data-bs-toggle="pill" data-bs-target="#contact"
                            type="button"
                            role="tab" aria-controls="contact" aria-selected="false">Kontakt Information
                    </button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="disabled-tab" data-bs-toggle="pill" data-bs-target="#disabled"
                            type="button"
                            role="tab" aria-controls="disabled" aria-selected="false" disabled>tag-type
                    </button>
                </li>
            </ul>

            <form action="change-customer-info" method="post">
                <div class="tab-content" id="tab-content">
                    <div class="tab-pane fade show active" id="dimensions" role="tabpanel"
                         aria-labelledby="dimensions-tab"
                         tabindex="0">

                        <div class="mb-3 text-center">
                            <label for="length" class="form-label">Carport Længde (cm)</label>
                            <input style="height: 40px" class="form-control" id="length"
                                   type="length" name="carportLength" placeholder="300 cm">
                        </div>
                        <div class="mb-3 text-center">
                            <label for="width" class="form-label">Carport Bredde (cm)</label>
                            <input style="height: 40px" class="form-control" id="width"
                                   type="width" name="carportWidth" placeholder="500 cm"></div>
                        <div class="mb-3 text-center">
                            <label for="carportTag" class="form-label">Carport Højde (cm)</label>
                            <select class="btn btn-primary dropdown-toggle" aria-expanded="true"
                                    data-bs-toggle="dropdown" type="button" name="carportRoof" id="carportTag">
                                <option value="0">Vælg tag</option>
                                <option value="1">Fladt tag</option>
                                <option disabled value="2">Rejsning tag</option>
                            </select>
                        </div>


                        <button class="btn btn-primary">Next</button>


                    </div>

                    <div>
                        <div class="tab-pane fade" id="toolshed" role="tabpanel" aria-labelledby="toolshed-tab"
                             tabindex="0">
                            <div class="row">
                                <label for="toolshedWidth" class="form-label">Redskabsskur bredde (mm)</label>
                                <input class="form-control" id="toolshedWidth" type="number" name="toolshedWidth"
                                       placeholder="300 mm">
                            </div>
                            <div class="row">
                                <label for="toolshedLength" class="form-label">Redskabsskur længde (mm)</label>
                                <input class="form-control" id="toolshedLength" type="number" name="toolshedLength"
                                       placeholder="300 mm">
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="pills-contact-tab"
                         tabindex="0">
                        <div>
                            <div class="mb-3">
                                <label for="customerName" class="form-label">Navn</label>
                                <input type="text" id="customerName" name="customerName"
                                       value="${sessionScope.customer.getName()}" class="form-control">
                            </div>

                            <div class="mb-3">
                                <label for="customerEmail" class="form-label">Email</label>
                                <input type="text" id="customerEmail" name="customerEmail"
                                       value="${sessionScope.customer.getEmail()}" class="form-control">
                            </div>
                                <%--Popup--%>
                            <div class="popup" id="popup">
                                <div style="margin-bottom: 13%">
                                    <img src="${pageContext.request.getContextPath()}/images/greenTickMark.jpg">
                                </div>
                                <h2>Thank you!</h2>
                                <p>Your order has been submitted successfully</p>
                                <button type="btn btn-primary" onclick="closePopup()">OK</button>
                            </div>

                                <%--                            popupend--%>


                            <div class="mb-3">
                                <label for="customerPhone" class="form-label">Telefonnummer</label>
                                <input type="text" id="customerPhone" name="customerPhone"
                                       value="${sessionScope.customer.getPersonalPhoneNumber().get()}" class="form-control">
                            </div>

                            <div class="mb-3">
                                <label for="customerAddress" class="form-label">Adresse</label>
                                <select class="form-select" aria-label="Vælg adresse" name="customerAddress"
                                        id="customerAddress">
                                    <option value="0">Adresse 1</option>
                                    <option value="1">Adresse 2</option>
                                    <option value="2">Adresse 3</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="zipcode" class="form-label">Postnummer</label>
                                <select class="form-select" aria-label="Vælg postnummer" name="zipcode" id="zipcode">
                                    <option value="0">Postnummer 1</option>
                                    <option value="1">Postnummer 2</option>
                                    <option value="2">Postnummer 3</option>
                                </select>
                            </div>
                        </div>


                        <div class="container">
                            <button type="button" class="btn" onclick="openPopup()">Submit</button>
                        </div>


                    </div>
                    <div class="tab-pane fade" id="pills-disabled" role="tabpanel" aria-labelledby="pills-disabled-tab"
                         tabindex="0">...
                    </div>
                </div>
            </form>
        </section>

        <script>
            let popup = document.getElementById("popup");

            function openPopup() {
                popup.classList.add("open-popup");
            }

            function closePopup() {
                popup.classList.remove("open-popup");
            }


        </script>

    </jsp:body>

</t:pagetemplate>

