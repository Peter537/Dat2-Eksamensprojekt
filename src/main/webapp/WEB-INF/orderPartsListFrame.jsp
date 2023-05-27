<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
            integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
            crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
            crossorigin="anonymous"></script>
    <link href="${pageContext.request.contextPath}/css/pagetemplateStyle.css" rel="stylesheet">
</head>

<body>

<table class="table table-striped table-bordered table-hover">

    <thead>
    <th>Dimensioner</th>
    <th>Længde</th>
    <th>Antal</th>
    <th>Enhed</th>
    <th>Beskrivelse</th>
    </thead>
    <tbody>

    <tr>
        <td>${requestScope.partslist.rafter.lumberType.thickness}x${requestScope.partslist.rafter.lumberType.width} mm.
            spærtre
        </td>
        <td>${requestScope.partslist.rafter.length}</td>
        <td>${requestScope.partslist.numberOfRafters}</td>
        <td>stk.</td>
        <td>${requestScope.partslist.rafter.description.get()}</td>
    </tr>

    <tr>
        <td>${requestScope.partslist.pole.lumberType.thickness}x${requestScope.partslist.pole.lumberType.width} mm.
            stolpe
        </td>
        <td>${requestScope.partslist.pole.length}</td>
        <td>${requestScope.partslist.numberOfPoles}</td>
        <td>stk.</td>
        <td>${requestScope.partslist.pole.description.get()}</td>
    </tr>

    <tr>
        <td>${requestScope.partslist.plate.lumberType.thickness}x${sessionScope.partslist.plate.lumberType.width} mm.
            rem
        </td>
        <td>${requestScope.partslist.plate.length}</td>
        <td>${requestScope.partslist.numberOfPlates}</td>
        <td>stk.</td>
        <td>${requestScope.partslist.plate.description.get()}</td>
    </tr>

    <tr>
        <td>${requestScope.partslist.roof.displayName}</td>
        <td>${requestScope.partslist.length}</td>
        <td>${requestScope.partslist.roofArea}</td>
        <td>m2</td>
        <td>Dette er taget</td>
    </tr>
    </tbody>
</table>
<table class="table table-striped table-bordered table-hover text-center">
    <thead>
    <th>Pris</th>
    </thead>
    <tbody>
    <tr>
        <td>${requestScope.partslist.getFormattedPrice()} kr.</td>
    </tr>
    </tbody>
</table>
</body>
</html>