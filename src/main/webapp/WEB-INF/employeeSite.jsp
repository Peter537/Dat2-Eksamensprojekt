<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="footer">
        Medarbejderside
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
                    <c:choose>
                        <c:when test="${sessionScope.user.getProfilePicture() != null}">
                            <img style="height: 150px; width: 150px" src="<c:url value='data:image/jpeg;base64,${sessionScope.user.getProfilePicture()}'/>" alt="Profile Picture">
                        </c:when>
                        <c:otherwise>
                            <img style="height: 150px; width: 150px" src="${pageContext.request.contextPath}/images/DefaultProfilePic.png" alt="Profile Picture">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-3 user-info" style="float: left; border-left: 2px solid green; height: 130px">
                    <p>Navn: ${sessionScope.user.getName()}</p>
                    <p>Email: ${sessionScope.user.getEmail()}</p>
                    <c:choose>
                        <c:when test="${sessionScope.user.personalPhoneNumber.present}">
                            <p>Personligt telefonnummer: ${sessionScope.user.personalPhoneNumber.get()}</p>
                        </c:when>
                        <c:otherwise>
                            <p>Personligt telefonnummer: Ikke sat</p>
                        </c:otherwise>
                    </c:choose>
                    <a class="link" type="button" onclick="openPopup()">Konto redigering</a>
                </div>
                <div class="col-3">
                    <p>Afdeling: ${sessionScope.user.getDepartment().getDepartmentName()}</p>
                    <p>Stilling: ${sessionScope.user.getPosition().getPositionName()}</p>
                    <c:choose>
                        <c:when test="${sessionScope.user.workPhoneNumber.present}">
                            <p>Arbejdstelefon: ${sessionScope.user.getWorkPhoneNumber().get()}</p>
                        </c:when>
                        <c:otherwise>
                            <p>Arbejdstelefon: ikke sat</p>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-3 text-center">
                    <h1>Velkommen </h1>
                    <h1>${sessionScope.user.getName()}</h1>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-8" id="modules">
                    <div class="row">
                        <div class="col-sm-6 card sm-card">
                            <div class="col-sm-12">
                                <div class="card-body">
                                    <h5 class="card-title">Mine nuværende salg</h5>
                                    <p class="card-text">Her vil du kunne se dine nyeste ordrer og se status på de
                                        igangværende salg.</p>
                                    <a href="see-employee-orders" class="btn btn-primary">Til mine salg</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 card sm-card">
                            <div class="col-sm-12">
                                <div class="card-body">
                                    <h5 class="card-title">Alle ordrer i systemet</h5>
                                    <p class="card-text">Her kan du se alle ordrer som er i systemet.</p>
                                    <a href="see-all-orders" class="btn btn-primary">Til alle ordrer</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-6 card sm-card">
                            <div class="card-body">
                                <h5 class="card-title">Materialekataloget</h5>
                                <p class="card-text">Se og ret i materialekataloget.</p>
                                <a href="see-catalog" class="btn btn-primary">Til materialekataloget</a>
                            </div>
                        </div>
                        <div class="col-sm-6 card sm-card">
                            <div class="card-body">
                                <h5 class="card-title">Generer stykliste</h5>
                                <p class="card-text">Her kan du generere en stykliste med dine egne mål.</p>
                                <a href="generate-custom-partslist" class="btn btn-primary">Generer stykliste</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4" id="news">
                    <div class="card" style="height: 100%">
                        <div class="row text-center">
                            <h3>Nyheder</h3>
                        </div>
                        <table style="margin-left: 5%;height: 100%;">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Oprettelsesdato</th>
                                <th>Styklistepris</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${sessionScope.news}" var="news">
                            <tr>
                                <td>${news.getId()}</td>
                                <td>${news.getCreatedOn()}</td>
                                <td>${news.getFormattedPrice()}</td>
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
                <form method="post" action="change-employee-info" enctype="multipart/form-data" style="margin-bottom: 5%">
                    <div class="row">
                        <div class="col-3">
                            <label for="upload-image">
                                <c:choose>
                                    <c:when test="${sessionScope.user.getProfilePicture() != null}">
                                        <img style="height: 150px; width: 150px; margin-top: 0.5%" src="<c:url value='data:image/jpeg;base64,${sessionScope.user.getProfilePicture()}'/>" alt="Profile Picture">
                                    </c:when>
                                    <c:otherwise>
                                        <img style="height: 150px; width: 150px; margin-top: 0.5%" src="${pageContext.request.contextPath}/images/DefaultProfilePic.png" alt="Profile Picture">
                                    </c:otherwise>
                                </c:choose>
                            </label>
                            <input class="form-control" id="upload-image" name="imageFile" type="file">
                        </div>

                        <div class="col-5 user-info" style="float: left; border-left: 2px solid green; height: 130px">
                            <p>Navn: ${sessionScope.user.getName()}</p>
                            <p>E-mail: ${sessionScope.user.getEmail()}</p>
                            <c:choose>
                                <c:when test="${sessionScope.user.personalPhoneNumber.present}">
                                    <p>Personligt telefonnummer: ${sessionScope.user.personalPhoneNumber.get()}</p>
                                </c:when>
                                <c:otherwise>
                                    <p>Personligt telefonnummer: Ikke sat</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div class="row" style="padding: 50px"></div>

                    <div class="row">
                        <div class="col-4" style="margin-top: 1%">
                            <h3>Skift kontonavn</h3>
                            <div class="form-group">
                                <label for="name">Navn</label>
                                <input class="form-control" type="text" name="name" id="name"
                                       placeholder="Ex: Mads Kildeberg">
                            </div>
                        </div>

                        <div class="col-4" style="margin-top: 1%">
                            <h3>Skift kodeord</h3>
                            <div class="form-group" style="margin-top: 5%">
                                <label for="newPassword">Nyt kodeord</label>
                                <input class="form-control" type="password" name="newPassword" id="newPassword"
                                       placeholder="Skriv dit nye ønskede kodeord">
                            </div>
                            <div class="form-group">
                                <label for="confirmPassword" style="margin-top: 5%">Gentag kodeord</label>
                                <input class="form-control" type="password" name="confirmPassword" id="confirmPassword"
                                       placeholder="Gentag dit nye ønskede kodeord">
                            </div>
                        </div>

                        <c:choose>
                            <c:when test="${sessionScope.user.personalPhoneNumber.present}">
                                <c:set var="personalPhoneNumber"
                                       value="${sessionScope.user.personalPhoneNumber.get()}"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="personalPhoneNumber" value="Ikke sat"/>
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