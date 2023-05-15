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

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/carportFormulaStyle.css">

        <c:if test="${not empty requestScope.errormessage}">
            <div class="alertRed">
                <span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span>
                    ${requestScope.errormessage}
            </div>
        </c:if>

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

            <form action="PartList" method="post">
                <div class="tab-content" id="tab-content">
                    <div class="tab-pane fade show active" id="dimensions" role="tabpanel"
                         aria-labelledby="dimensions-tab"
                         tabindex="0">

                        <div class="mb-3 text-center">
                            <label for="length" class="form-label">Carport Længde (cm)</label>
                            <input style="height: 40px" class="form-control" id="length"
                                   type="length" name="carportLength" placeholder="300 cm" required>
                        </div>
                        <div class="mb-3 text-center">
                            <label for="width" class="form-label">Carport Bredde (cm)</label>
                            <input style="height: 40px" class="form-control" id="width"
                                   type="width" name="carportWidth" placeholder="500 cm" required></div>
                        <div class="mb-3 text-center">

                            <label for="carportTag" class="form-label">Carport Højde (cm)</label>
                            <div class="row" style="margin-bottom: 3%">
                                <label for="carportMinHeight" class="form-label">Min</label>
                                <input class="form-control" id="carportMinHeight" type="number"
                                       name="carportMinHeight" placeholder="300 cm" required>
                            </div>

                            <select class="btn btn-primary dropdown-toggle" aria-expanded="true"
                                    data-bs-toggle="dropdown" type="button" name="carportRoof" id="carportTag" required>
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
                                       placeholder="300 cm">
                            </div>
                            <div class="row">
                                <label for="toolshedLength" class="form-label">Redskabsskur længde (mm)</label>
                                <input class="form-control" id="toolshedLength" type="number" name="toolshedLength"
                                       placeholder="300 cm">
                            </div>

                        </div>
                    </div>
                    <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="pills-contact-tab"
                         tabindex="0">
                        <div>
                            <div class="mb-3">
                                <label for="customerName" class="form-label">Navn</label>
                                <input type="text" id="customerName" name="customerName"
                                       value="${sessionScope.user.getName()}" class="form-control" readonly>
                            </div>

                            <div class="mb-3">
                                <label for="customerEmail" class="form-label">Email</label>
                                <input type="text" id="customerEmail" name="customerEmail"
                                       value="${sessionScope.user.getEmail()}" class="form-control" readonly>
                            </div>

                            <div class="mb-3">
                                <label for="customerPhone" class="form-label">Telefonnummer</label>
                                <input type="text" id="customerPhone" name="customerPhone"
                                       value="${sessionScope.user.personalPhoneNumber.get()}"
                                       class="form-control" readonly>
                            </div>

                            <div class="mb-3">
                                <div style="border: dotted black 3px; margin-left: 30%; margin-right: 30%">

                                    <p class="text-center">Hvis ikke ønskede adresse står på nedenstående liste, så
                                        unlad at vælge denne og skriv i stedet i feltet under</p>

                                </div>

                                <label for="customerAddress" class="form-label">Adresse</label>
                                <select class="form-select" aria-label="Vælg adresse" name="customerAddress"
                                        id="customerAddress">
                                    <option value="1">${sessionScope.user.getAddress(1).get().getAddress()}</option>
                                    <option value="2">${sessionScope.user.getAddress(2).get().getAddress()}</option>
                                    <option value="3">${sessionScope.user.getAddress(3).get().getAddress()}</option>
                                    <option value="0">Anden adresse</option>
                                </select>

                                <div class="row">
                                    <div class="col-6">
                                        <label for="customerAddressOther" class="form-label">Adresse</label>
                                        <input type="text" id="customerAddressOther" name="customerAddress"
                                               placeholder="Skriv adresse her hvis det ikke står på listen."
                                               class="form-control">
                                    </div>
                                <div class="col-6">
                                    <label for="zipcode" class="form-label">Postnummer</label>
                                    <input class="form-control" type="number" name="customerZip" id="zipcode"
                                           placeholder="ex. 2400" required>
                                </div>
                                </div>
                            </div>


                        </div>

                        <div class="row" style="margin-bottom: 3%">
                            <label for="story">Evt. bemærkninger</label>

                            <textarea id="story" name="remarks" class="form-control"
                                      rows="5" cols="33" placeholder="Skriv bemærkninger her"></textarea>

                        </div>


                        <div class="container">
                            <input type="submit" class="btn">Submit</input>
                        </div>


                    </div>
                    <div class="tab-pane fade" id="pills-disabled" role="tabpanel" aria-labelledby="pills-disabled-tab"
                         tabindex="0">...
                    </div>
                </div>
            </form>
        </section>


        <c:if test="${requestScope.partsListSuccess != null}">

            <div class="popup">
                <div>
                    <img style="width: 100px; border-radius: 50%"
                         src="${pageContext.request.getContextPath()}/images/greenTickMark.jpg">
                </div>
                <div style="color: white">
                    <h2>Thank you!</h2>
                    <p>Your order has been submitted successfully</p>
                </div>
                <button type="btn btn-primary" onclick="closePopup()">OK</button>
            </div>

        </c:if>



    </jsp:body>


</t:pagetemplate>

