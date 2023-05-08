<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="true" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>


  <div class="form-popup" id="myForm" style="z-index: 2">
      <form action="" class="form-container">
          <h1>Change your pa</h1>

          <label for="oldPassword"><b>Old password</b></label>
          <input type="password" id="oldPassword" placeholder="Write your old password">

          <label for="newPassword"><b>Password</b></label>
          <input type="password" placeholder="Enter Password" id="newPassword" required>

          <button type="submit" class="btn">Login</button>
          <button type="button" class="btn cancel" onclick="closeForm()">Close</button>
      </form>
  </div>
  </body>
</html>
