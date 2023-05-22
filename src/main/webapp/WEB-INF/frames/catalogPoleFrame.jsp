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
<div class="text-center container">
    <div class="row">
        <h3 style="margin-top: 1%;">Stolper</h3>
        <table class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>Tykkelse</th>
                <th>Bredde</th>
                <th>Længde</th>
                <th>Meter Price</th>
                <th>Antal</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="pole" items="${requestScope.poles}">
                <tr>
                    <form action="save-catalog-item" method="post">
                        <td>${pole.id}</td>
                        <td><input type="number" name="poleThickness" value="${pole.lumberType.thickness}"></td>
                        <td><input type="number" name="poleWidth" value="${pole.lumberType.width}"></td>
                        <td><input type="number" name="poleLength" value="${pole.length}"></td>
                        <td><input type="number" name="poleMeterPrice" value="${pole.lumberType.meterPrice}"></td>
                        <td><input type="number" name="amount" value="${pole.amount}"></td>
                        <td>
                            <input type="hidden" name="id" value="${pole.id}">
                            <input type="hidden" name="catalogItemType" value="pole">
                            <input type="hidden" name="lumberType" value="POLE">
                            <input style="color: var(--color-light);" type="submit" value="Gem vare">
                    </form>
                    <form action="delete-catalog-item" method="post">
                        <input type="hidden" name="id" value="${pole.id}">
                        <input type="hidden" name="catalogItemType" value="pole">
                        <input style="color: var(--color-light);" type="submit" value="Slet vare">
                        </td>
                    </form>
                </tr>
            </c:forEach>
            <tr>
                <form action="save-catalog-item" method="post">
                    <td></td>
                    <td><input type="number" name="poleThickness" placeholder="Tykkelse"></td>
                    <td><input type="number" name="poleWidth" placeholder="Bredde"></td>
                    <td><input type="number" name="poleLength" placeholder="Længde"></td>
                    <td><input type="number" name="poleMeterPrice" placeholder="Meterpris"></td>
                    <td><input type="number" name="amount" placeholder="Antal"></td>
                    <input type="hidden" name="catalogItemType" value="pole">
                    <input type="hidden" name="lumberType" value="POLE">
                    <td>
                        <input style="color: var(--color-light);" type="submit" value="Tilføj vare">
                    </td>
                </form>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>