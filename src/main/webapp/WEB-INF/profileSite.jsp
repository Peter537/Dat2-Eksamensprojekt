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
        <c:if test="${sessionScope.user == null}">
            <jsp:forward page="login"/>
        </c:if>
        <script src="${pageContext.request.contextPath}/scripts/profileSiteScript.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profileSiteStyle.css">

        <c:if test="${not empty requestScope.errormessage}">
            <div class="alertRed">
                <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
                There was an error: ${requestScope.errormessage}
            </div>
        </c:if>

        <c:if test="${not empty requestScope.nameSuccess}">
            <div class="alertGreen">
                <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
                    ${requestScope.nameSuccess}
            </div>
        </c:if>

        <c:if test="${not empty requestScope.passwordSuccess}">
            <div class="alertGreen">
                <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
                    ${requestScope.passwordSuccess}
            </div>
        </c:if>

        <c:if test="${not empty requestScope.phoneSuccess}">
            <div class="alertGreen">
                <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
                    ${requestScope.phoneSuccess}
            </div>
        </c:if>

        <c:if test="${not empty requestScope.addressSuccess}">
            <div class="alertGreen">
                <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
                    ${requestScope.addressSuccess}
            </div>
        </c:if>

        <div class="baseBody" id="baseBody">

            <div class="row" style="padding-bottom: 5%; padding-top: 5%">
                <div class="col-3">
                    <img style="height: 150px; width: 150px"
                         src="${pageContext.request.contextPath}/images/DefaultProfilePic.png" alt="Profile picture">
                </div>

                <div class="col-5 user-info" style="float: left; border-left: 2px solid green; height: 130px">

                    <p>Name: ${sessionScope.user.getName()}</p>
                    <p>Email: ${sessionScope.user.getEmail()}</p>
                    <c:choose>
                        <c:when test="${sessionScope.user.personalPhoneNumber.present}">
                            <p>personligt nummer: ${sessionScope.user.personalPhoneNumber.get()}</p>
                        </c:when>
                        <c:otherwise>
                            <p>personligt nummer: ikke sat</p>
                        </c:otherwise>
                    </c:choose>
                    <a class="link" type="button" onclick="openPopup()">Konto Redigering</a>

                </div>
                <div class="col-4 text-center">
                    <h1>Velkommen </h1>
                    <h1>${sessionScope.user.getName()}</h1>
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
                            <p class="card-text">Hvis intet i vores brede katalog er noget for dig, så indsend dine egne
                                mål
                                som en flittig medarbejder vil hjælpe dig hen med</p>
                            <a href="to-generate-partlist" class="btn btn-primary">Tag mig til formularen</a>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <div class="row" style="margin-top: 3%"></div>


        <div class="popup" id="popup" style="margin-top: 1%; opacity: 90%; background-color: #083d74; height: 85%; color: white">

            <div style="height: 100%; width: 100%; opacity: 100% !important;">

                <form method="post" action="change-customer-info" style="margin-bottom: 5%">
                    <div class="row">
                        <div class="col-3">
                            <img style="height: 150px; width: 150px; margin-top: 0.5%"
                                 src="${pageContext.request.contextPath}/images/DefaultProfilePic.png" alt="Profile Picture">
                        </div>

                        <div class="col-5 user-info" style="float: left; border-left: 2px solid green; height: 130px">
                            <p>Name: ${sessionScope.user.getName()}</p>
                            <p>Email: ${sessionScope.user.getEmail()}</p>
                            <c:choose>
                                <c:when test="${sessionScope.user.personalPhoneNumber.present}">
                                    <p>personligt nummer: ${sessionScope.user.personalPhoneNumber.get()}</p>
                                </c:when>
                                <c:otherwise>
                                    <p>personligt nummer: ikke sat</p>
                                </c:otherwise>
                            </c:choose>
                            <div class="row adresses">
                                <c:if test="${sessionScope.user.getAddress(1).present}">
                                    <p>Adresse 1: ${sessionScope.user.getAddress(1).get().getAddress()}</p>
                                </c:if>

                                <c:choose>
                                    <c:when test="${sessionScope.user.getAddress(2).present}">
                                        <p>Adresse 2: ${sessionScope.user.getAddress(2).get().getAddress()}</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p>Adresse 2 ikke sat</p>
                                    </c:otherwise>
                                </c:choose>

                                <c:choose>
                                    <c:when test="${sessionScope.user.getAddress(3).present}">
                                        <p>Adresse 2: ${sessionScope.user.getAddress(3).get().getAddress()}</p>
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
                                <input class="form-control" type="text" name="name" id="name"
                                       placeholder="ex: Mads Kildeberg">
                            </div>
                        </div>

                        <div class="col-4" style="margin-top: 1%">
                            <h3>Skift kodeord</h3>
                            <div class="form-group">
                                <label for="oldPassword">gammelt kodeord</label>
                                <input class="form-control" type="password" name="oldPassword" id="oldPassword"
                                       placeholder="skriv dit nuværende kodeord">
                            </div>
                            <div class="form-group" style="margin-top: 5%">
                                <label for="newPassword">Nyt kodeord</label>
                                <input class="form-control" type="password" name="newPassword" id="newPassword"
                                       placeholder="skriv dit nye ønskede kodeord">
                            </div>
                            <div class="form-group">
                                <label for="confirmpassword" style="margin-top: 5%">Gentag kodeord</label>
                                <input class="form-control" type="password" name="confirmPassword" id="confirmpassword"
                                       placeholder="gentag dit nye ønskede kodeord">
                            </div>
                        </div>

                        <c:choose>
                            <c:when test="${sessionScope.user.personalPhoneNumber.present}">
                            <c:set var="personalPhoneNumber" value="${sessionScope.user.personalPhoneNumber.get()}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="personalPhoneNumber" value="ikke sat"/>
                        </c:otherwise>
                        </c:choose>


                        <div class="col-3" style="margin-top: 20px;">
                            <div class="row">
                                <h3>Skift telefonnummer</h3>
                                <div class="form-group">
                                    <label for="newPhoneNumber">Nyt telefonnummer</label>
                                    <input class="form-control" id="newPhoneNumber" type="text" name="newPhoneNumber"
                                            placeholder="${personalPhoneNumber}">
                                </div>
                            </div>
                            <div class="row"></div>

                            <div class="row" style="padding-top: 10%">
                                <h3>Skift adresseinfo</h3>



                                <c:choose>
                                    <c:when test="${sessionScope.user.address1.present}">
                                        <c:set var="address1" value="${sessionScope.user.address1.get().getStreet()}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="address1" value="ikke sat"/>
                                    </c:otherwise>
                                </c:choose>

                               <c:choose>
                                   <c:when test="${sessionScope.user.address2.present}">
                                       <c:set var="address2" value="${sessionScope.user.address2.get().getStreet()}"/>
                                   </c:when>
                                   <c:otherwise>
                                         <c:set var="address2" value="ikke sat"/>
                                   </c:otherwise>
                               </c:choose>

                                <c:choose>
                                    <c:when test="${sessionScope.user.address3.present}">
                                        <c:set var="address3" value="${sessionScope.user.address3.get().getStreet()}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="address3" value="ikke sat"/>
                                    </c:otherwise>
                                </c:choose>


                                <c:choose>
                                    <c:when test="${sessionScope.user.getAddress(1).present}">
                                        <c:set var="zip1" value="${sessionScope.user.getAddress(1).get().zip.getZipCode()}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="zip1" value="ikke sat"/>
                                    </c:otherwise>
                                </c:choose>

                                <c:choose>
                                    <c:when test="${sessionScope.user.getAddress(2).present}">
                                        <c:set var="zip2" value="${sessionScope.user.getAddress(2).get().zip.getZipCode()}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="zip2" value="ikke sat"/>
                                    </c:otherwise>
                                </c:choose>


                                <c:choose>
                                    <c:when test="${sessionScope.user.getAddress(3).present}">
                                        <c:set var="zip3" value="${sessionScope.user.getAddress(3).get().zip.getZipCode()}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="zip3" value="ikke sat"/>
                                    </c:otherwise>
                                </c:choose>

                                <div class="row">

                                    <div class="col-6">
                                        <label for="street1">Street</label>
                                        <input class="form-control" type="text" name="street1" id="street1"
                                                placeholder="${address1}">
                                    </div>
                                    <div class="col-6">
                                        <label for="zipCode1">Zip code</label>
                                        <input class="form-control" type="number" name="zipCode1" id="zipCode1"
                                               placeholder="${zip1}">
                                    </div>



                                </div>
                                <div class="row">
                                    <div class="col-6">
                                        <label for="street2">Street 2</label>
                                        <input class="form-control" type="text" name="street2" id="street2"
                                               placeholder="${address2}">
                                    </div>
                                    <div class="col-6">
                                        <label for="zipCode2">Zip code 2</label>
                                        <input class="form-control" type="number" name="zipCode2" id="zipCode2"
                                               placeholder="${zip2}">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-6">
                                        <label for="street3">Street 3</label>
                                        <input class="form-control" type="text" name="street3" id="street3"
                                               placeholder="${address2}">
                                    </div>
                                    <div class="col-6">
                                        <label for="zipCode3">Zip code 3</label>
                                        <input class="form-control" type="number" name="zipCode3" id="zipCode3"
                                               placeholder="${zip3}">
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
        </div>
    </jsp:body>

</t:pagetemplate>
