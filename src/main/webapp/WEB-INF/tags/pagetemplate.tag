<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@attribute name="title" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<!DOCTYPE html>
<html lang="da">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><jsp:invoke fragment="title"/></title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>

</head>
<body>
<header class="header">
    <nav class="navbar navbar-expand-lg" style="margin-top: -1%; height: var(--header-height)">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">
                <svg style="height: var(--header-height); width: var(--header-height); z-index: 100;" width="1em" height="1em" viewBox="0 0 181 181" xmlns="http://www.w3.org/2000/svg"><path fill="#003D76" d="M-2-2h183v183H-2z"></path><path fill="#FFF" d="M42.62 111.91h8.89V83.05h12.77V74.9H51.51V53.49h14.32v-8.06H42.62zM86.98 58.59c-8.89 0-14.26 5.19-14.26 14.27v25.59c0 9.07 5.37 14.27 14.26 14.27s14.26-5.2 14.26-14.27V72.86c0-9.08-5.38-14.27-14.26-14.27m5.48 39.35c0 4.69-2.18 6.93-5.48 6.93-3.3 0-5.48-2.24-5.48-6.93V73.37c0-4.69 2.17-6.93 5.48-6.93 3.31 0 5.48 2.24 5.48 6.93v24.57ZM129.73 62.86c-1-2.65-4.54-4.28-7.75-4.28-8.37 0-12.29 5.09-12.29 14.27v25.59c0 9.17 3.92 14.27 12.29 14.27 3 0 6.51-1.73 7.44-4.08v8.36c0 4.69-2.06 6.94-5.37 6.94s-5.37-2.25-5.37-6.94v-.91h-8.75v1.42c0 9.08 5.27 14.28 14.15 14.28s14.15-5.2 14.15-14.28V59.4h-8.5v3.46Zm-.31 35.07c0 4.69-2.17 6.93-5.47 6.93s-5.48-2.24-5.48-6.93V73.37c0-4.69 2.17-6.93 5.48-6.93 3.31 0 5.47 2.24 5.47 6.93v24.56ZM149.93 64.86a2 2 0 0 0-.5-.79 1.77 1.77 0 0 0-.63-.38 2 2 0 0 0 1-.51 1.45 1.45 0 0 0 .36-1 1.72 1.72 0 0 0-.15-.74 1.33 1.33 0 0 0-.45-.52 2.29 2.29 0 0 0-.74-.31 5.55 5.55 0 0 0-1-.09h-1.62a.6.6 0 0 0-.46.17.7.7 0 0 0-.16.48v5c0 .095.06.18.15.21.189.03.381.03.57 0 .185.03.375.03.56 0a.21.21 0 0 0 .12-.21v-2h.38a1 1 0 0 1 .69.2c.198.196.34.44.41.71l.29 1a.38.38 0 0 0 .08.17.21.21 0 0 0 .14.1h1.28c.06 0 .1 0 .11-.07a.21.21 0 0 0 0-.11 1.8 1.8 0 0 0-.1-.4l-.33-.91Zm-1.53-2a1.16 1.16 0 0 1-.81.22h-.66v-1.5h.65a2 2 0 0 1 .48.06.63.63 0 0 1 .32.15.51.51 0 0 1 .16.25.93.93 0 0 1 0 .29.78.78 0 0 1-.23.58"></path><path d="M152.87 61.4a5.12 5.12 0 0 0-2.86-2.82 5.93 5.93 0 0 0-2.21-.4 5.59 5.59 0 0 0-2.17.42 5.25 5.25 0 0 0-2.92 2.86 5.16 5.16 0 0 0-.43 2.12c-.01.74.13 1.475.41 2.16a5 5 0 0 0 1.13 1.7 5.23 5.23 0 0 0 1.73 1.13 5.91 5.91 0 0 0 2.2.4 5.51 5.51 0 0 0 2.16-.42 5.42 5.42 0 0 0 1.76-1.15 5.38 5.38 0 0 0 1.6-3.85 5.62 5.62 0 0 0-.4-2.15m-1.16 3.88a4.21 4.21 0 0 1-.86 1.34 4.11 4.11 0 0 1-1.34.92 4.72 4.72 0 0 1-3.43 0 3.88 3.88 0 0 1-1.34-.89 4.27 4.27 0 0 1-.88-1.38 4.91 4.91 0 0 1-.31-1.78 4.58 4.58 0 0 1 .3-1.64 4 4 0 0 1 .86-1.36c.381-.39.837-.7 1.34-.91a4.39 4.39 0 0 1 1.75-.33 4.84 4.84 0 0 1 1.68.3 3.89 3.89 0 0 1 2.21 2.31c.22.57.33 1.178.32 1.79a4.68 4.68 0 0 1-.3 1.65" fill="#FFF"></path></svg>
            </a>
            <%--            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"--%>
            <%--                    aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">--%>
            <%--                <span class="navbar-toggler-icon"></span>--%>
            <%--            </button>--%>
            <div class="collapse navbar-collapse justify-content-start" id="navbarNavAltMarkup">

            </div>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNavAltMarkup2">
                <div class="navbar-nav">
                    <c:if test="${sessionScope.user == null }">
                        <a class="nav-item nav-link navtext" href="${pageContext.request.contextPath}/login.jsp">Login</a>
                    </c:if>
                    <c:if test="${sessionScope.user != null }">
                        <a class="nav-item nav-link navtext" href="${pageContext.request.contextPath}/logout">Log out</a>
                    </c:if>
                    <a class="nav-item nav-link">
                        <svg viewBox="0 0 25 25" xmlns="http://www.w3.org/2000/svg" width="1em" height="1em"><path d="M19.167 14.706H8.944L7.167 7.647H20.5l-1.333 7.059Zm-8.445 3.97c0 .732-.596 1.324-1.333 1.324a1.328 1.328 0 0 1-1.333-1.324c0-.731.596-1.323 1.333-1.323s1.333.592 1.333 1.323Zm8.89 0c0 .732-.597 1.324-1.334 1.324a1.328 1.328 0 0 1-1.334-1.324c0-.731.597-1.323 1.334-1.323.737 0 1.333.592 1.333 1.323ZM7.166 7.647 6.277 5H4.5" stroke="currentColor" stroke-width="2" fill="none" fill-rule="evenodd" stroke-linecap="round" stroke-linejoin="round"></path></svg><span>Kurv</span>
                    </a>
                    <c:if test="${sessionScope.user.getCurrentOrder().getShoppingCart().getTotalAmount() > 0}">
                        <a class="nav-item nav-link navtext" style="border: 0;" href="${pageContext.request.contextPath}/ToCart">(${sessionScope.user.getCurrentOrder().getShoppingCart().getTotalAmount()})</a>
                    </c:if>
                </div>
            </div>

        </div>
    </nav>
</header>

<div id="body">
    <div id="body-inner" class="container mt-4" style="min-height: 400px;">
        <jsp:doBody/>
    </div>
</div>


<!-- Footer -->
<div class="container mt-3">
    <hr/>
    <div class="row mt-4">
        <div class="col">
            Nørgaardsvej 30<br/>
            2800 Lyngby
        </div>
        <div class="col">
            <jsp:invoke fragment="footer"/><br/>
            <p>&copy; 2022 Cphbusiness</p>
        </div>
        <div class="col">
            Datamatikeruddannelsen<br/>
            2. semester efterår 2022
        </div>
    </div>

</div>
</body>
<style>
    :root {
        --header-height: 3.75rem;
        --header-img-multi: 1.5;
        --hero-img-multi: 0.3;
        --bs-body-font-family: Dinpro, helvetica, arial, sans-serif;
        --color: hsla(210, 98%, 23%, 1);
        --color-dark: hsla(210, 98%, 13%, 1);
        --color-light: hsla(210, 98%, 33%, 1);
    }

    a {
        color: var(--color);
        display: inline-block;
        position: relative;
        text-decoration: none;
    }

    .link {
        color: var(--color);
        display: inline-block;
        position: relative;
        text-decoration: none;
    }

    .link::before {
        content: '';
        position: absolute;
        bottom: 0;
        left: 0;
        width: 100%;
        height: 2px;
        background-image: linear-gradient(transparent calc(100% - 1px), currentcolor 1px);
        background-size: 1000% 100%;
        transition: background-position 0.5s ease, clip-path 0.5s ease;
        background-position: 0 100%;
        clip-path: inset(0 0 0 1%);
    }

    .link:hover::before {
        background-position: 100% 100%;
        clip-path: inset(0 100% 0 0);
    }

    .btn {
        background-color: var(--color) !important;
    }

    html {
        margin: 0;
        padding: 0;
        scroll-behavior: smooth;
    }

    .footer {
        left: 0;
        bottom: 5%;
        width: 100%;
        text-align: center;
    }

    .header {
        position: sticky;
        top: 0;
        width: 100%;
        background-color: #ffffff;
        background-size : cover;
        background-repeat : no-repeat;
        z-index: 100;
    }

    .hero-img {
        width: calc(100%*var(--hero-img-multi));
        text-align: center;
        display: block;
        margin: auto;
    }

    .navtext {
        color: var(--color);
        border-right: 1px solid #00000038;
        border-radius: 3px 3px 3px 3px;
    }

    #body {
        background-color: #f5f5f3;
    }
</style>
</html>