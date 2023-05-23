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
        Medarbejder side
    </jsp:attribute>

    <jsp:attribute name="footer">
        Medarbejder side
    </jsp:attribute>

    <jsp:body>

        <c:if test="${sessionScope.user == null}">
            <jsp:forward page="login"/>
        </c:if>
        <script src="${pageContext.request.contextPath}/scripts/profileSiteScript.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customerSiteStyle.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/employeeSiteStyle.css">

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

                <div class="col-3 user-info" style="float: left; border-left: 2px solid green; height: 130px">

                    <p>Navn: ${sessionScope.user.getName()}</p>
                    <p>Email: ${sessionScope.user.getEmail()}</p>
                    <c:choose>
                        <c:when test="${sessionScope.user.personalPhoneNumber.present}">
                            <p>Personligt nummer: ${sessionScope.user.personalPhoneNumber.get()}</p>
                        </c:when>
                        <c:otherwise>
                            <p>Personligt nummer: ikke sat</p>
                        </c:otherwise>
                    </c:choose>
                    <a class="link" type="button" onclick="openPopup()">Konto Redigering</a>

                </div>
                <div class="col-3">

                    <p>Afdeling: ${sessionScope.user.getDepartment().getDepartmentName()}</p>
                    <p>Stilling: ${sessionScope.user.getPosition().getPositionName()}</p>
                    <c:choose>
                        <c:when test="${sessionScope.user.workPhoneNumber.present}">
                            <p>Arbejdsnummer: ${sessionScope.user.getWorkPhoneNumber().get()}</p>
                        </c:when>
                        <c:otherwise>
                            <p>Arbejdsnummer: ikke sat</p>
                        </c:otherwise>
                    </c:choose>

                </div>
                <div class="col-3 text-center">
                    <h1>Velkommen </h1>
                    <h1>${sessionScope.user.getName()}</h1>
                </div>


            </div>


            <form class="popup">
                <a class="link btn" type="button" onclick="openPopup()">Skift konto infomation</a>
            </form>

                <%--TODO: replace the image-links with images taken from the image folder.--%>

            <div class="row">
                <div class="col-sm-8" id="modules">
                    <div class="row">
                        <div class="col-sm-6 card sm-card">
                            <div class="col-sm-12">
                                <div class="card-body">
                                    <h5 class="card-title">Mine nuværende salg</h5>
                                    <p class="card-text">Her vil du kunne se din nyeste order og se status på den
                                        igangværende salg</p>
                                    <a href="see-employee-orders" class="btn btn-primary">Til mine salg</a>

                                    <div class="row" style="padding-top: 5%">
                                        <div class="col-12">
                                            <p style="text-decoration: underline black">Status på igangværende salg</p>
                                            <p style="color: red">placeholder for status-method</p>
                                                <%-- TODO: add necessary methods to do the above line--%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 card sm-card">
                            <div class="col-sm-12">
                                <div class="card-body">
                                    <h5 class="card-title">Alle ordre i systemet</h5>
                                    <p class="card-text">Her kan du se alle ordre som er i systemet.</p>
                                    <a href="see-all-orders" class="btn btn-primary">Til alle ordre</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-6 card sm-card">
                            <div class="card-body">
                                <h5 class="card-title">Materiale-kataloget</h5>
                                <p class="card-text">Se og ret i materiale kataloget.</p>
                                <a href="see-catalog" class="btn btn-primary">Materiale-kataloget</a>
                            </div>
                        </div>
                        <div class="col-sm-6 card sm-card">
                            <div class="card-body">
                                <h5 class="card-title">Generer stykliste</h5>
                                <p class="card-text">Her kan du generere en stykliste, enten med dine egne mål eller baseret på en brugers ordre.</p>
                                <a href="to-generate-custom-partlist" class="btn btn-primary">Generer stykliste</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4" id="news">
                    <div class="card">
                        <div class="row text-center">
                            <h3>Nyheder</h3>
                        </div>
                        <table>
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Oprettede dato</th>
                                <th>Pris</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${sessionScope.news}" var="news">
                            <tr>
                                <td>${news.getId()}</td>
                                <td>${news.getCreatedOn()}</td>
                                <td>${news.getPrice().get()}</td>
                            </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>

        </div>
        <div class="row" style="margin-top: 3%"></div>


        <div class="popup" id="popup"
             style="margin-top: 1%; opacity: 90%; background-color: #083d74; height: 85%; color: white">

            <div style="height: 100%; width: 100%; opacity: 100% !important;">

                <form method="post" action="change-employee-info" style="margin-bottom: 5%">
                    <div class="row">
                        <div class="col-3">
                            <img style="height: 150px; width: 150px; margin-top: 0.5%"
                                 src="${pageContext.request.contextPath}/images/DefaultProfilePic.png"
                                 alt="Profile Picture">
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
                                <c:set var="personalPhoneNumber"
                                       value="${sessionScope.user.personalPhoneNumber.get()}"/>
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
