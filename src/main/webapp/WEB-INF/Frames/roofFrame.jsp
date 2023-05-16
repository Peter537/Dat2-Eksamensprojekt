<%--
  Created by IntelliJ IDEA.
  User: magnu
  Date: 16/05/2023
  Time: 12.16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
    <link href="${pageContext.request.contextPath}/css/pagetemplateStyle.css" rel="stylesheet">
</head>
<body style="background: none;">
<div class="container">
    <div class="row">
        <h3>Tag</h3>
        <table class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>Kvadratmeterpris</th>
                <th>Type</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach var="roof" items="${requestScope.roofs}">
                    <tr>
                        <form action="save-catalog-item" method="post">
                            <td>${roof.id}</td>
                            <td><input type="number" name="squareMeterPrice" value="${roof.squareMeterPrice}">
                            </td>
                            <td>${roof.type}</td>
                            <td>
                                <input type="hidden" name="id" value="${roof.id}">
                                <input type="hidden" name="catalogItemType" value="roof">
                                <input type="hidden" name="roofType" value="PLASTIC_ROOF">
                                <input style="color: var(--color-light);" type="submit" value="Gem vare">
                        </form>
                        <form action="delete-catalog-item" method="post">
                            <input type="hidden" name="id" value="${roof.id}">
                            <input type="hidden" name="catalogItemType" value="roof">
                            <input style="color: var(--color-light);" type="submit" value="Slet vare">
                        </form>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <form action="save-catalog-item" method="post">
                        <td></td>
                        <td><input type="number" name="squareMeterPrice" placeholder="Kvadratmeterpris"></td>
                        <td>PLASTIC_ROOF</td>
                        <input type="hidden" name="catalogItemType" value="roof">
                        <input type="hidden" name="roofType" value="PLASTIC_ROOF">
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
