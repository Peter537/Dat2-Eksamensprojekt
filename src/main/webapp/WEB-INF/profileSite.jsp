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
<script>
        function openPopup() {
        var popup = window.open("", "Change password", "width=40%, height=auto, resizable=no, left=50%, top=50%, transform: translate(-50%, -50%)");
        var iframe = document.createElement("iframe");
        iframe.src = "${pageContext.request.contextPath}/testpage.jsp";
        iframe.frameBorder = "0";
        iframe.style.width = "100%";
        iframe.style.height = "100%";
        popup.document.body.appendChild(iframe);
        }

        document.getElementById("change-password-link").addEventListener("click", openPopup);

</script>

        <div class="row" style="padding-bottom: 5%; padding-top: 5%">
            <div class="col-3">
                <img style="height: 150px; width: 150px"
                     src="${pageContext.request.contextPath}/images/Windows_10_Default_Profile_Picture.png">
            </div>

            <div class="col-5 user-info" style="float: left; border-left: 2px solid green; height: 130px">

                <p>Name: ${sessionScope.customer.getName()}</p>
                <p>Email: ${sessionScope.customer.getEmail()}</p>
                <a href="change-password" id="change-password-link">Change password</a>
<%--                TODO: fix the popup somehow... files involved: profileSite, changepassowrd (servlet), testpage.jsp--%>


            </div>
            <div class="col-4 text-center">
                <h1>Velkommen </h1>
                <h1>${sessionScope.customer.getName()}</h1>
            </div>



        </div>

        <%--TODO: replace the image-links with images taken from the image folder.--%>

        <div class="card" style="height: 50%">
            <div class="row">
                <div class="col-sm-6">
                    <img style="height: 350px" class="card-img-top" src="${pageContext.request.contextPath}/images/carport.jpg" alt="Card image cap">
                </div>
                <div class="col-sm-6">
                    <div class="card-body">
                        <h5 class="card-title">Vis mine ordrer</h5>
                        <p class="card-text">Her vil du kunne se dine seneste ordre og se status på igangværende og gamle bestillinger</p>
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

        <div class="card" >
            <div class="row">
                <div class="col-sm-6">
                    <img style="height: 350px" class="card-img-top" src="${pageContext.request.contextPath}/images/istockphoto-493281662-612x612.jpg" alt="Card image cap">
                </div>
                <div class="col-sm-6">
                    <div class="card-body">
                        <h5 class="card-title">Lav ny forestpørgsmål</h5>
                        <p class="card-text">Hvis intet i vores brede katalog er noget for dig, så indsend dine egne mål som en flittig medarbejder vil hjælpe dig hen med</p>
                        <a href="to-generate-partlist" class="btn btn-primary">Tag mig til formularen</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="row" style="margin: 3%"></div>


        <%--        <button class="btn btn-primary text-center" value="vis historik">Vis historik</button>--%>
        <%--        <br>--%>
        <%--        <a href="to-generate-partlist">Hello</a>--%>


    </jsp:body>

</t:pagetemplate>
