<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="title">
       This is the frontpage
    </jsp:attribute>

    <jsp:attribute name="footer">
        Forside
    </jsp:attribute>

    <jsp:body>
        <div class="text-center" style="padding-bottom: 2%;">

            <h1>Et stort udvalg af carporte</h1>

            <img src="images/carport.jpg" alt="carport" width="70%" height="auto" style="margin-bottom: 5%">


            <br>
            <div class="row">
                <div class="col-4">
                    <a href="login.jsp" style="width: 150px; height: 150px">
                        <img id="flatRoof" src="images/flatroof.svg" alt="Flat roof">
                        <label for="flatRoof">Fladt tag</label>
                    </a>
                </div>

                <div class="col-4">

                </div>

                <div class="col-4">
                    <a href="login.jsp" style="width: 150px; height: 150px">
                        <img id="HighRoof" src="images/highroof.svg" alt="High roof">
                        <label for="HighRoof">Højt tag</label>
                    </a>
                </div>
            </div>
        </div>
    </jsp:body>

</t:pagetemplate>