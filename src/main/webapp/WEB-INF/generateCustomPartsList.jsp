<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="true" %>

<t:pagetemplate>
    <jsp:attribute name="footer">
        Generer stykliste
    </jsp:attribute>

    <jsp:body>
        <c:set var="carportOrder" value="${requestScope.carportOrder}"/>
        <div class="row">
        <div class="col-12">
            <h1 class="text-center">Stykliste generator</h1>
        </div>

        <div class="row" style="padding-top: 3%">
            <form action="calculate-custom-partslist" method="post" target="iframe">
                <div class="row">
                    <div class="form-group">
                        <label for="carportLength">Carport længde: </label>
                        <input type="number" min="1" name="carportLength" id="carportLength" class="form-control"
                               placeholder="Carport længde" value="${carportOrder != null ? carportOrder.length : null}"
                               required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="carportWidth">Carport bredde: </label>
                        <input type="number" min="1" name="carportWidth" id="carportWidth" class="form-control"
                               placeholder="Carport bredde" value="${carportOrder != null ? carportOrder.width : null}"
                               required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="carportHeight">Carport minimum højde: </label>
                        <input type="number" min="1" name="carportHeight" id="carportHeight" class="form-control"
                               placeholder="Carport højde"
                               value="${carportOrder != null ? carportOrder.minHeight : null}" required>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="roofType">Tagtype: </label>
                        <select name="roofType" id="roofType" class="form-control">
                            <option value="fladt tag">Fladt tag</option>
                            <option value="skråt tag" disabled>Skråt tag</option>
                        </select>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="shedWidth">Redskabskur bredde: </label>
                        <input readonly type="text" name="shedWidth" id="shedWidth" class="form-control"
                               placeholder="Redskabskur bredde"
                               value="${carportOrder.toolRoom.present ? carportOrder.toolRoom.get().width : null}">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="shedLength">Redskabskur længde: </label>
                        <input readonly type="text" name="shedLength" id="shedLength" class="form-control"
                               placeholder="Redskabskur længde"
                               value="${carportOrder.toolRoom.present ? carportOrder.toolRoom.get().length : null}">
                    </div>
                </div>
                <div class="text-center" style="padding-top: 2%">
                    <input type="hidden" name="orderId" value="${carportOrder.length > 0 ? carportOrder.id : 0}">
                    <input type="hidden" name="valid" value="valid">
                    <input class="btn btn-primary" formtarget="iframe" type="submit" value="Beregn">
                </div>
            </form>
        </div>

        <div style="padding-bottom: 5%; margin-top: 1%">
            <iframe name="iframe" id="iframe" src="calculate-custom-partslist" width="100%" height="400px"
                    sandbox="allow-forms allow-top-navigation"></iframe>
        </div>
    </jsp:body>
</t:pagetemplate>
