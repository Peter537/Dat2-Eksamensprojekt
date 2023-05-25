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
        <c:if test="${sessionScope.user != null}">
            <jsp:forward page="customer-site"/>
        </c:if>

        <section class="py-4 py-xl-5">
            <div class="container">
                <div class="row mb-5">
                    <div class="col-md-8 col-xl-6 text-center mx-auto">
                        <h2>Log ind på din bruger</h2>
                        eller
                        <a class="link" href="createCustomer.jsp">opret en bruger</a>
                    </div>
                </div>
                <div class="row d-flex justify-content-center">
                    <div class="col-md-5 col-xl-4">
                        <div class="card mb-3">
                            <div class="loginForm">
                                <div class="bs-icon-xl bs-icon-circle bs-icon-primary bs-icon my-4 text-center">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="50px" height="50px"
                                         fill="currentColor" viewBox="0 0 16 16" class="bi bi-person">
                                        <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"></path>
                                    </svg>
                                </div>
                                <form action="login" method="post" style="padding-left: 20px; padding-right: 20px">
                                    <label for="email" style="padding: 10px">Email Adresse</label>
                                    <div class="mb-3 text-center"><input style="height: 40px" class="form-control"
                                                                         id="email" type="email" name="email"
                                                                         placeholder="Email"></div>
                                    <label for="password" style="padding: 10px">Password</label>
                                    <div class="mb-3 text-center"><input style="height: 40px" class="form-control"
                                                                         type="password" id="password" name="password"
                                                                         placeholder="Password"></div>
                                    <p class="text-center" style="color: red">${requestScope.errormessage}</p> <br>

                                    <div class="mb-3">
                                        <input style="scale: 1.2; text-align: center" type="checkbox" id="remember"
                                               name="remember" value="remember"/>
                                        <label class="form-check-label" for="remember">Husk mig</label>
                                        <p class="text-muted" style="float: right">Glemt password?</p>
                                    </div>
                                    <div class="mb-3 text-center">
                                        <button class="btn btn-primary d-block w-100" type="submit">Login</button>
                                    </div>

                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <br>
        <br>
    </jsp:body>

</t:pagetemplate>