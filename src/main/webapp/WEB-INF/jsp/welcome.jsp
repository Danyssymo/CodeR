<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Пример страницы</title>
    <link rel="stylesheet" href="css/welcome.css">
    <style>
        .language-buttons {
            position: absolute;
            top: 10px;
            right: 10px;
        }
        .language-buttons button {
            background-color: #007BFF;
            color: yellow;
            border: none;
            width: 40px;
            height: 40px;
            margin: 2px;
            border-radius: 50%;
            cursor: pointer;
            font-size: 14px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .language-buttons button:hover {
            background-color: #0056b3;
        }
    </style>
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.local" var="loc" />

    <fmt:message bundle="${loc}" key="local.signup" var="signup" />
    <fmt:message bundle="${loc}" key="local.signUP" var="signUP" />
    <fmt:message bundle="${loc}" key="local.message" var="message" />
    <fmt:message bundle="${loc}" key="local.signUP.user" var="user" />
    <fmt:message bundle="${loc}" key="local.signUP.email" var="email" />
    <fmt:message bundle="${loc}" key="local.signUP.password" var="password" />
    <fmt:message bundle="${loc}" key="local.signIn" var="signIn" />
    <fmt:message bundle="${loc}" key="local.button.ru" var="ru" />
    <fmt:message bundle="${loc}" key="local.button.en" var="en" />
    <fmt:message bundle="${loc}" key="local.rememberMe" var="rememberMe" />
    <fmt:message bundle="${loc}" key="local.creator" var="creator" />
</head>
<body>
<c:out value="${message}" />
<div class="language-buttons">
    <form action="MyController" method="post">
        <input type="hidden" name="command" value="change_locale" />
        <input type="hidden" name="lang" value="ru" />
        <button type="submit">${ru}</button>
    </form>

    <form action="MyController" method="post">
        <input type="hidden" name="command" value="change_locale" />
        <input type="hidden" name="lang" value="en" />
        <button type="submit">${en}</button>
    </form>
</div>
<div class="information">
    <c:if test="${not (param.Info eq null) }">
        <c:out value="${param.Info}" />
    </c:if>
</div>
<div class="container right-panel-active">
    <!-- Sign Up -->
    <div class="container__form container--signup">
        <form action="MyController" class="form" method="post" id="form1">
            <input type="hidden" name="command" value="do_registration"/>
            <h2 class="form__title">${signUP}</h2>
            <input type="text" name="username" placeholder=${user} class="input" />
            <input type="email" name="mail" placeholder=${email} class="input" />
            <input type="password" name="password" placeholder=${password} class="input" />
            <label><input type="checkbox" value="creator" name="creator">${creator}</label>
            <button class="btn" type="submit">${signUP}</button>
        </form>
    </div>

    <!-- Sign In -->
    <div class="container__form container--signin">
        <form action="MyController" class="form" method="post" id="form2">
            <input type="hidden" name="command" value="do_auth"/>
            <h2 class="form__title">${signIn}</h2>
            <input type="email" name="login" placeholder=${email} class="input" />
            <input type="password" name="password" placeholder=${password} class="input" />
            <label><input type="checkbox" value="remember-me" name="remember-me">${rememberMe}</label>
            <button type="submit" class="btn">${signIn}</button>
        </form>
    </div>

    <!-- Overlay -->
    <div class="container__overlay">
        <div class="overlay">
            <div class="overlay__panel overlay--left">
                <button class="btn" id="signIn">${signIn}</button>
            </div>
            <div class="overlay__panel overlay--right">
                <button class="btn" id="signUp">${signUP}</button>
            </div>
        </div>
    </div>
</div>
<script src="js/js.js"></script>
</body>
</html>