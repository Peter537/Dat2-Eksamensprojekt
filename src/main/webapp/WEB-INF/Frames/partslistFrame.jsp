<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: yusef
  Date: 19/05/2023
  Time: 12.57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>


<table>
    <thead>
    <th>Dimensioner</th>
    <th>Længde</th>
    <th>Antal</th>
    <th>Enhed</th>
    <th>Beskrivelse</th>

    </thead>
</table>

<table class="table table-striped table-bordered table-hover">

    <thead>
    <th>Tre & Tagplader</th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    </thead>
    <tbody>
    <tr>
        <td>${requestScope.partslist.pole.lumberType.thickness}x${requestScope.partslist.pole.lumberType.width} mm. stolpe</td>
        <td>${requestScope.partslist.pole.length}</td>
        <td>${requestScope.partslist.pole.amount}</td>
        <td>stk.</td>
        <td>Needs to be implemented</td>

    </tr>
    </tbody>


</body>
</html>
