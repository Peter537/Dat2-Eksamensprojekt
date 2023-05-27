<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>

    <jsp:attribute name="footer">
        Se kataloget
    </jsp:attribute>

    <jsp:body>

        <div style="margin-bottom: 2%">
            <div class="row">

                <div class="col-sm-11">
                    <h1>Se kataloget</h1>
                </div>

                <div class="col-sm-1">
                    <a href="employee-site" class="btn btn-primary" style="margin-top: 10%;">Tilbage</a>
                </div>
            </div>
        </div>

        <div class="text-center">
            <iframe src="catalog-roof-frame" style="box-shadow: 1px 0 3px 0 gray;" width="100%" height="500px"
                    sandbox="allow-forms"
                    onload="this.style.height=(this.contentWindow.document.body.scrollHeight+20)+'px';"></iframe>

            <br>
            <br>
            <br>

            <iframe src="catalog-pole-frame" style="box-shadow: 1px 0px 3px 0px gray;" width="100%" height="500px"
                    sandbox="allow-forms"
                    onload="this.style.height=(this.contentWindow.document.body.scrollHeight+20)+'px';"></iframe>

            <br>
            <br>
            <br>

            <iframe src="catalog-rafter-frame" style="box-shadow: 1px 0px 3px 0px gray;" width="100%" height="500px"
                    sandbox="allow-forms"
                    onload="this.style.height=(this.contentWindow.document.body.scrollHeight+20)+'px';"></iframe>

            <br>
            <br>
            <br>
        </div>
    </jsp:body>

</t:pagetemplate>