<%--
  Created by IntelliJ IDEA.
  User: yusef
  Date: 03/05/2023
  Time: 11.08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>

    <jsp:attribute name="title">
        Min side
    </jsp:attribute>

    <jsp:attribute name="footer">
        My profile
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
                width: 95%;
                height: 95%;
                background: #fff;
                border-radius: 50px;
                position: absolute;
                border: limegreen 4px solid;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%) scale(0.1);
                text-align: center;
                padding: 0 30px 30px;
                color: #333;
                visibility: hidden;
                transition: transform 0.5s ease-in-out;
                box-shadow: black 0px 0px 10px 0px;
            }

            .popup img {
                width: 100px;
                margin-top: -50px;
                border-radius: 50%;
            }

            .open-popup {
                visibility: visible !important;
                position: absolute;
                top: 50%;
                transform: translate(-50%, -50%) scale(1);
                transition: 0.3s ease-in-out;
                z-index: 2;
                padding: 2%;
                border-radius: 10px;
                border: black 2px solid;
            }

            .after-popup *:not(.popup):not(body):not(html) {
                filter: blur(5px);
                backdrop-filter: blur(5px); /* adjust the blur value as needed */
            }
        </style>


        <div class="baseBody" id="baseBody">

        <div class="row" style="padding-bottom: 5%; padding-top: 5%">
            <div class="col-3">
                <img style="height: 150px; width: 150px"
                     src="${pageContext.request.contextPath}/images/DefaultProfilePic.png">
            </div>

            <div class="col-5 user-info" style="float: left; border-left: 2px solid green; height: 130px">

                <p>Name: ${sessionScope.customer.getName()}</p>
                <p>Email: ${sessionScope.customer.getEmail()}</p>
                <c:choose>
                    <c:when test="${sessionScope.customer.personalPhoneNumber.present}">
                        <p>personligt nummer: ${sessionScope.customer.personalPhoneNumber.get()}</p>
                    </c:when>
                    <c:otherwise>
                        <p>personligt nummer: ikke sat</p>
                    </c:otherwise>
                </c:choose>
                <a class="link" type="button" onclick="openPopup()">Konto Redigering</a>

            </div>
            <div class="col-4 text-center">
                <h1>Velkommen </h1>
                <h1>${sessionScope.customer.getName()}</h1>
            </div>


            </div>


            <form class="popup">
                <a class="link btn" type="button" onclick="openPopup()">Skift konto infomation</a>
            </form>

                <%--
        TODO: fix the popup somehow... files involved: profileSite, changepassowrd (servlet), testpage.jsp--%>



        <%--TODO: replace the image-links with images taken from the image folder.--%>

        <div class="card" style="height: 50%">
            <div class="row">
                <div class="col-sm-6">
                    <img style="height: 350px" class="card-img-top"
                         src="${pageContext.request.contextPath}/images/carport.jpg" alt="Card image cap">
                </div>
                <div class="col-sm-6">
                    <div class="card-body">
                        <h5 class="card-title">Vis mine ordrer</h5>
                        <p class="card-text">Her vil du kunne se dine seneste ordre og se status på igangværende og
                            gamle bestillinger</p>
                        <a href="#" class="btn btn-primary">Til mine ordrer</a>

                        <div class="row" style="padding-top: 5%">
                            <div class="col-12">
                                <p style="text-decoration: underline black">Status på igangværende ordrer</p>
                                <p style="color: red">placeholder for status-method</p>
                                    <%-- TODO: add necessary methods to do the above line--%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div class="row" style="padding: 0.5%"></div>

        <div class="card">
            <div class="row">
                <div class="col-sm-6">
                    <img style="height: 350px" class="card-img-top"
                         src="${pageContext.request.contextPath}/images/workbenchIconForFormula.jpg"
                         alt="Card image cap">
                </div>
                <div class="col-sm-6">
                    <div class="card-body">
                        <h5 class="card-title">Lav ny forestpørgsmål</h5>
                        <p class="card-text">Hvis intet i vores brede katalog er noget for dig, så indsend dine egne mål
                            som en flittig medarbejder vil hjælpe dig hen med</p>
                        <a href="to-generate-partlist" class="btn btn-primary">Tag mig til formularen</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="row" style="margin: 3%"></div>


        <%--        <button class="btn btn-primary text-center" value="vis historik">Vis historik</button>--%>
        <%--        <br>--%>
        <%--        <a href="to-generate-partlist">Hello</a>--%>

        </div>

        <div class="popup" id="popup" style="margin-top: 1%; opacity: 98%; background-color: #083d74; height: 85%; color: white">

        <div style="height: 100%; width: 100%; opacity: 100% !important;">

            <form method="post" action="change-customer-info" style="margin-bottom: 5%">
                <div class="row">
                    <div class="col-3">
                        <img style="height: 150px; width: 150px; margin-top: 0.5%"
                             src="${pageContext.request.contextPath}/images/DefaultProfilePic.png">
                    </div>

                    <div class="col-5 user-info" style="float: left; border-left: 2px solid green; height: 130px">
                        <p>Name: ${sessionScope.customer.getName()}</p>
                        <p>Email: ${sessionScope.customer.getEmail()}</p>
                        <c:choose>
                            <c:when test="${sessionScope.customer.personalPhoneNumber.present}">
                                <p>personligt nummer: ${sessionScope.customer.personalPhoneNumber.get()}</p>
                            </c:when>
                            <c:otherwise>
                                <p>personligt nummer: ikke sat</p>
                            </c:otherwise>
                        </c:choose>
                        <div class="row adresses">
                        <p>Address 1: ${sessionScope.customer.getAddress(1).get()}</p>
                            <c:choose>
                                <c:when test="${sessionScope.customer.getAddress(2).present}">
                                    <p>Adresse 2: ${sessionScope.customer.getAddress(2).get()}</p>
                                </c:when>
                                <c:otherwise>
                                    <p>Adresse 2 ikke sat</p>
                                </c:otherwise>
                            </c:choose>

                            <c:choose>
                                <c:when test="${sessionScope.customer.getAddress(3).present}">
                                    <p>Adresse 2: ${sessionScope.customer.getAddress(3).get()}</p>
                                </c:when>
                                <c:otherwise>
                                    <p>Adresse 3: ikke sat</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <div class="row" style="padding: 50px"></div>

                <div class="row">
                    <div class="col-4" style="margin-top: 1%">
                        <h3>Skift kontonavn</h3>
                        <div class="form-group">
                            <label for="name">Name</label>
                            <input class="form-control" type="text" name="name" id="name">
                        </div>
                    </div>

                    <div class="col-4" style="margin-top: 1%">
                        <h3>Skift kodeord</h3>
                        <div class="form-group">
                            <label for="oldPassword">gammelt kodeord</label>
                            <input class="form-control" type="password" name="oldPassword" id="oldPassword">
                        </div>
                        <div class="form-group" style="margin-top: 5%">
                            <label for="newPassword">Nyt kodeord</label>
                            <input class="form-control" type="password" name="newPassword" id="newPassword">
                        </div>
                        <div class="form-group">
                            <label for="confirmpassword" style="margin-top: 5%">Gentag kodeord</label>
                            <input class="form-control" type="password" name="confirmPassword" id="confirmpassword">
                        </div>
                    </div>

                    <c:if test="${sessionScope.customer.personalPhoneNumber.present}">
                        <c:set var="personalPhoneNumber" value="${sessionScope.customer.personalPhoneNumber}"/>
                    </c:if>


                    <div class="col-3" style="margin-top: 20px;">
                        <div class="row">
                            <h3>Skift telefonnummer</h3>
                            <div class="form-group">
                                <label for="newPhoneNumber">Nyt telefonnummer</label>
                                <input class="form-control" id="newPhoneNumber" type="text" name="newPhoneNumber" value="${personalPhoneNumber.get()}" placeholder="ex. 12345678">
                            </div>
                        </div>
                        <div class="row"></div>

                        <div class="row" style="padding-top: 10%">
                            <h3>Skift adresseinfo</h3>
                            <c:if test="${sessionScope.customer.address1.present}">
                                <c:set var="address1" value="${sessionScope.customer.address1}"/>
                            </c:if>

                            <c:if test="${sessionScope.customer.address2.present}">
                                <c:set var="address2" value="${sessionScope.customer.address2}"/>
                            </c:if>

                            <c:if test="${sessionScope.customer.address3.present}">

                                <c:set var="address3" value="${sessionScope.customer.address3}"/>
                            </c:if>



                            <c:if test="${sessionScope.customer.address1.present}">
                                <c:set var="zip1" value="${sessionScope.customer.getAddress(1).get().zip.getZipCode()}"/>
                            </c:if>

                            <c:if test="${sessionScope.customer.address2.present}">
                                <c:set var="zip1" value="${sessionScope.customer.getAddress(2).get().zip.getZipCode()}"/>
                            </c:if>
                            <c:if test="${sessionScope.customer.address3.present}">
                                <c:set var="zip1" value="${sessionScope.customer.getAddress(3).get().zip.getZipCode()}"/>
                            </c:if>

                            <div class="row">

                                <div class="col-6">
                                    <label for="street1">Street</label>
                                    <input class="form-control" type="text" name="street1" id="street1"
                                           value="${address1.get().getStreet()}">
                                </div>
                                <div class="col-6">
                                    <label for="zipCode1">Zip code</label>
                                    <input class="form-control" type="number" name="zipCode1" id="zipCode1"
                                           placeholder="ex. 2400" value="">
                                </div>


                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <label for="street2">Street 2</label>
                                    <input class="form-control" type="text" name="street2" id="street2"
                                           value="${address2}">
                                </div>
                                <div class="col-6">
                                    <label for="zipCode2">Zip code 2</label>
                                    <input class="form-control" type="number" name="zipCode2" id="zipCode2"
                                           placeholder="ex. 2400">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <label for="street3">Street 3</label>
                                    <input class="form-control" type="text" name="street3" id="street3"
                                           value="${address3}">
                                </div>
                                <div class="col-6">
                                    <label for="zipCode3">Zip code 3</label>
                                    <input class="form-control" type="number" name="zipCode3" id="zipCode3"
                                           placeholder="ex. 2400">
                                </div>

                            </div>
                        </div>

                    </div>



                </div>

                <div style="margin-left: 35%; margin-right: 35%; margin-top: 5%">
                    <div class="row">
                        <input class="btn btn-secondary" type="submit" value="Save changes">
                    </div>
                    <div class="row" style="margin-top: 3%">
                        <a class="btn btn-secondary" style="background-color: #a93f60 !important;" type="button"
                           onclick="closePopup()">Cancel</a>
                    </div>
                </div>


            </form>
        </div>


        <script>
            let popup = document.getElementById("popup");

            function openPopup() {
                popup.classList.add("open-popup");
                document.getElementById("baseBody").classList.add("after-popup");
            }

            function closePopup() {
                popup.classList.remove("open-popup");
                document.getElementById("baseBody").classList.remove("after-popup");
            }



        </script>

    </jsp:body>

</t:pagetemplate>
