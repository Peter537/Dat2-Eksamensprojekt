<%--
  Created by IntelliJ IDEA.
  User: yusef
  Date: 03/05/2023
  Time: 11.10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>

    <jsp:attribute name="title">
        partlist:

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    </jsp:attribute>

    <jsp:attribute name="footer">
        Partlist generator
    </jsp:attribute>

    <jsp:body>
        <script>
            $(document).ready(function () {
                $(".js-btn-next").click(function () {
                    var currentStep = $(this).closest(".multisteps-form__panel");
                    currentStep.removeClass("js-active").hide();
                    currentStep.next(".multisteps-form__panel").addClass("js-active").show();
                });

                $(".js-btn-prev").click(function () {
                    var currentStep = $(this).closest(".multisteps-form__panel");
                    currentStep.removeClass("js-active").hide();
                    currentStep.prev(".multisteps-form__panel").addClass("js-active").show();
                });
            });

        </script>

        <section>
            <div id="multple-step-form-n" class="container overflow-hidden" style="margin-top: 0px;margin-bottom: 10px;padding-bottom: 300px;padding-top: 57px;">
                <div id="progress-bar-button" class="multisteps-form">
                    <div class="row">
                        <div class="col-12 col-lg-8 ml-auto mr-auto mb-4">
                            <div class="multisteps-form__progress"><a class="btn multisteps-form__progress-btn js-active" role="button" title="User Info">User Info</a><a class="btn multisteps-form__progress-btn" role="button" title="User Info">About</a><a class="btn multisteps-form__progress-btn" role="button" title="User Info">Contact&nbsp;</a></div>
                        </div>
                    </div>
                </div>
                <div id="multistep-start-row" class="row">
                    <div id="multistep-start-column" class="col-12 col-lg-8 m-auto">
                        <form id="main-form" class="multisteps-form__form">
                            <div id="single-form-next" class="multisteps-form__panel shadow p-4 rounded bg-white js-active" data-animation="scaleIn">
                                <h3 class="text-center multisteps-form__title">User Info</h3>
                                <div id="form-content" class="multisteps-form__content">
                                    <div id="input-grp-double" class="form-row mt-4">
                                        <div class="col-12 col-sm-6"><input class="form-control multisteps-form__input" type="text" placeholder="First name "></div>
                                        <div class="col-12 col-sm-6 mt-4 mt-sm-0"><input class="form-control multisteps-form__input" type="text" placeholder="Last name "></div>
                                    </div>
                                    <div id="input-grp-single" class="form-row mt-4">
                                        <div class="col-12"><input class="form-control multisteps-form__input" type="text" placeholder="House"></div>
                                    </div>
                                    <div id="next-button" class="button-row d-flex mt-4"><button class="btn btn btn-primary ml-auto js-btn-next" type="button" title="Next">Next</button></div>
                                </div>
                            </div>
                            <div id="single-form-next-prev" class="multisteps-form__panel shadow p-4 rounded bg-white" data-animation="scaleIn">
                                <h3 class="text-center multisteps-form__title">About</h3>
                                <div id="form-content-1" class="multisteps-form__content">
                                    <div id="input-grp-double-1" class="form-row mt-4">
                                        <div class="col-12 col-sm-6"><input class="form-control multisteps-form__input" type="text" placeholder="First name "></div>
                                        <div class="col-12 col-sm-6 mt-4 mt-sm-0"><input class="form-control multisteps-form__input" type="text" placeholder="Last name "></div>
                                    </div>
                                    <div id="input-grp-single-1" class="form-row mt-4">
                                        <div class="col-12"><input class="form-control multisteps-form__input" type="text" placeholder="House"></div>
                                    </div>
                                    <div id="next-prev-buttons" class="button-row d-flex mt-4"><button class="btn btn btn-primary js-btn-prev" type="button" title="Prev">Prev</button><button class="btn btn btn-primary ml-auto js-btn-next" type="button" title="Next">Next</button></div>
                                </div>
                            </div>
                            <div id="single-form-next-prev-1" class="multisteps-form__panel shadow p-4 rounded bg-white" data-animation="scaleIn">
                                <h3 class="text-center multisteps-form__title">About</h3>
                                <div id="form-content-2" class="multisteps-form__content">
                                    <div id="input-grp-double-2" class="form-row mt-4">
                                        <div class="col-12 col-sm-6"><input class="form-control multisteps-form__input" type="text" placeholder="First name "></div>
                                        <div class="col-12 col-sm-6 mt-4 mt-sm-0"><input class="form-control multisteps-form__input" type="text" placeholder="Last name "></div>
                                    </div>
                                    <div id="input-grp-single-2" class="form-row mt-4">
                                        <div class="col-12"><input class="form-control multisteps-form__input" type="text" placeholder="House"></div>
                                    </div>
                                    <div id="next-prev-buttons-1" class="button-row d-flex mt-4"><button class="btn btn btn-primary js-btn-prev" type="button" title="Prev">Prev</button><button class="btn btn btn-primary ml-auto js-btn-next" type="button" title="Next">Next</button></div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>




    </jsp:body>

</t:pagetemplate>
