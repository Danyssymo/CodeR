<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Camping Gear Website | CodingNepal</title>
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
    <style>
        .homepage {
            height: 100vh;
            width: 100%;
            position: relative;
            background: url("home-bg.jpg") center 65%;
            background-size: cover;
        }

        .form-container {
            position: absolute;
            top: 10px;
            left: 10px;
        }

        form {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
        }

        .input-row {
            display: flex;
            align-items: center;
        }

        label {
            color: #fff; /* Установка белого цвета текста */
            margin-bottom: 5px;
        }

        select, .submit-button {
            background-color: #f0f0f0;
            color: #333;
            padding: 3px 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            height: 30px; /* Уменьшение высоты */
            margin-right: 5px; /* Отступ между элементами */
        }

        .submit-button {
            background-color: #ccc;
            width: 40px; /* Уменьшение ширины */
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 0; /* Уменьшение padding до 0 для центровки текста */
        }


        .form-container1 {
            display: grid;
            grid-template-columns: auto auto;
            align-items: center;
            gap: 10px;
            margin: 20px;
        }

        .text-container1 {
            display: flex;
            flex-direction: column;
            color: white;
            font-size: 1.2em;
        }

        button {
            padding: 5px 10px;
            background-color: #a9a9a9;
            border: none;
            color: black;
            cursor: pointer;
            border-radius: 5px;
        }

        button:hover {
            background-color: #a9a9a9;
        }

        .modal {
            display: none; /* Изначально скрыто */
            position: fixed;
            z-index: 1000; /* Всегда поверх всего */
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.4);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 10% auto; /* Центрирование окна с отступом сверху */
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            height: 70%; /* Увеличенная высота */
            border-radius: 10px;
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        .news-form {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 100%;
        }

        .news-form h2 {
            margin-bottom: 15px;
            color: #333;
        }

        .news-form label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        .news-form input[type="text"],
        .news-form textarea,
        .news-form input[type="file"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .news-form button {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            border: none;
            border-radius: 5px;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }

        .news-form button:hover {
            background-color: #45a049;
        }

        .close-button {
            position: absolute;
            width: 20px;
            height: 20px;
            background-color: transparent;
            border: 1px solid gray;
            border-radius: 50%;
            cursor: pointer;
            font-size: 16px;
            color: gray;
            display: flex;
            align-items: center;
            justify-content: center;
            z-index: 1000;
        }

        .close-button:hover {
            color: darkgray;
            border-color: darkgray;
        }

        .styled-text {
            font-family: 'Arial', sans-serif; /* Замените на любой предпочитаемый шрифт */
            color: white;
            font-size: 18px; /* Чуть больше среднего размера */
            font-weight: 400; /* Вы можете изменить на 600 или 700 для более жирного текста */
            line-height: 1.5; /* Для лучшей читаемости */
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5); /* Тень для текста, чтобы он выглядел лучше на темных фонах */
        }

    </style>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="localization.local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.changeLang" var="changeLang"/>+
    <fmt:message bundle="${loc}" key="local.changeLang.ok" var="ok"/>
    <fmt:message bundle="${loc}" key="local.navbar.home" var="home"/>
    <fmt:message bundle="${loc}" key="local.navbar.services" var="services"/>
    <fmt:message bundle="${loc}" key="local.navbar.news" var="news"/>
    <fmt:message bundle="${loc}" key="local.navbar.aboutUs" var="aboutUs"/>
    <fmt:message bundle="${loc}" key="local.navbar.contactUs" var="contactUs"/>
    <fmt:message bundle="${loc}" key="local.navbar.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.welcome" var="welcome"/>
    <fmt:message bundle="${loc}" key="local.welcome.message" var="message"/>
    <fmt:message bundle="${loc}" key="local.welcome.ourServices" var="ourServices"/>
    <fmt:message bundle="${loc}" key="local.welcome.ourServices.message" var="servicesMessage"/>
    <fmt:message bundle="${loc}" key="local.ourNews" var="ourNews"/>
    <fmt:message bundle="${loc}" key="local.ourNews.message" var="ourNewsMessage"/>
    <fmt:message bundle="${loc}" key="local.aboutUs.message" var="aboutUsMessage"/>
    <fmt:message bundle="${loc}" key="local.info.story" var="story"/>
    <fmt:message bundle="${loc}" key="local.info.story.message" var="storyMessage"/>
    <fmt:message bundle="${loc}" key="local.info.mission" var="mission"/>
    <fmt:message bundle="${loc}" key="local.info.mission.message" var="missionMessage"/>
    <fmt:message bundle="${loc}" key="local.info.vision" var="vision"/>
    <fmt:message bundle="${loc}" key="local.info.vision.message" var="visionMessage"/>
    <fmt:message bundle="${loc}" key="local.info.team" var="team"/>
    <fmt:message bundle="${loc}" key="local.info.team.john" var="john"/>
    <fmt:message bundle="${loc}" key="local.info.team.jane" var="jane"/>
    <fmt:message bundle="${loc}" key="local.info.team.mark" var="mark"/>
    <fmt:message bundle="${loc}" key="local.info.team.sarah" var="sarah"/>
    <fmt:message bundle="${loc}" key="local.info.contactUs" var="contactUsTwo"/>
    <fmt:message bundle="${loc}" key="local.info.contactUs.message" var="contactUsTwoMessage"/>
    <fmt:message bundle="${loc}" key="local.us.adress" var="adress"/>
    <fmt:message bundle="${loc}" key="local.us.mail" var="mail"/>
    <fmt:message bundle="${loc}" key="local.us.phone" var="phone"/>
    <fmt:message bundle="${loc}" key="local.us.work" var="work"/>
    <fmt:message bundle="${loc}" key="local.us.work.time" var="time"/>
    <fmt:message bundle="${loc}" key="local.us.close" var="close"/>
    <fmt:message bundle="${loc}" key="local.us.site" var="site"/>
    <fmt:message bundle="${loc}" key="local.us.name" var="name"/>
    <fmt:message bundle="${loc}" key="local.us.send.mail" var="sendMail"/>
    <fmt:message bundle="${loc}" key="local.us.message" var="MessageUs"/>
    <fmt:message bundle="${loc}" key="local.sendMessage" var="sendMessage"/>
    <fmt:message bundle="${loc}" key="local.secure" var="secure"/>
    <fmt:message bundle="${loc}" key="local.button.ru" var="ru"/>
    <fmt:message bundle="${loc}" key="local.button.en" var="en"/>
    <fmt:message bundle="${loc}" key="local.user" var="user"/>
    <fmt:message bundle="${loc}" key="local.admin" var="admin"/>
    <fmt:message bundle="${loc}" key="local.creatorMsg" var="creator"/>
    <fmt:message bundle="${loc}" key="local.addNewsOne" var="addNewsOne"/>
    <fmt:message bundle="${loc}" key="local.addNewsTwo" var="addNewsTwo"/>
    <fmt:message bundle="${loc}" key="local.addNewsBTN" var="addNewsBTN"/>
</head>
<body>
<header>
    <nav class="navbar">
        <div class="form-container">
            <form action="MyController" method="post">
                <input type="hidden" name="command" value="change_locale"/>
                <label for="language">${changeLang}</label>
                <div class="input-row">
                    <select id="language" name="lang">
                        <option value="" disabled selected></option>
                        <option value="ru">${ru}</option>
                        <option value="en">${en}</option>
                    </select>
                    <button type="submit" class="submit-button">OK</button>
                </div>
            </form>
        </div>
        <c:if test="${sessionScope.user.role eq 'USER'}">
            <h2 class="logo"><a href="#">${user}</a></h2>
        </c:if>
        <c:if test="${sessionScope.user.role eq 'ADMIN'}">
            <h2 class="logo"><a href="#">${admin}</a></h2>
        </c:if>
        <c:if test="${sessionScope.user.role eq 'CREATOR'}">
            <h2 class="logo"><a href="#">${creator}</a></h2>
            <div class="form-container1">
                <div class="text-container1">
                    <span>${addNewsOne}</span>
                    <span>${addNewsTwo}</span>
                </div>
                <div class="button-container">
                    <button id="openModalBtn">${addNewsBTN}</button>
                </div>
            </div>
        </c:if>
        <input type="checkbox" id="menu-toggler">
        <label for="menu-toggler" id="hamburger-btn">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="white" width="24px" height="24px">
                <path d="M0 0h24v24H0z" fill="none"/>
                <path d="M3 18h18v-2H3v2zm0-5h18V11H3v2zm0-7v2h18V6H3z"/>
            </svg>
        </label>
        <ul class="all-links">
            <li><a href="#home">${home}</a></li>
            <li><a href="#services">${services}</a></li>
            <li><a href="#portfolio">${news}</a></li>
            <li><a href="#about">${aboutUs}</a></li>
            <li><a href="#contact">${contactUs}</a></li>
        </ul>
        <a href="MyController?command=do_logout" class="all-links logout">${logout}</a>
    </nav>
</header>


<section class="homepage" id="home">

    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <div class="news-form">
                <h2>Add News</h2>
                <form action="MyController" method="post">
                    <input type="hidden" name="command" value="add_news"/>
                    <label for="news-title">News Title:</label>
                    <input type="text" id="news-title" name="title" required>

                    <label for="news-content">News Content:</label>
                    <textarea id="news-content" name="content" rows="5" required></textarea>

                    <label for="news-image">Upload Image:</label>
                    <select id="news-image" name="image">
                        <c:forEach var="image" items="${images}">
                            <option value="${image}">${image}</option>
                        </c:forEach>
                    </select>

                    <button type="submit">Submit</button>
                </form>
            </div>
        </div>
    </div>


    <div class="content">
        <div class="styled-text">
        <c:if test="${not (param.Info1 eq null) }">
            <c:out value="${param.Info1}" />
        </c:if>
        </div>
        <div class="text">
            <h1>${welcome} <c:out value="${sessionScope.user.username }"/></h1>
            <p>
                ${message}</p>
        </div>
        <a href="#services">${ourServices}</a>
    </div>
</section>

<section class="services" id="services">
    <h2>${ourServices}</h2>
    <p>${servicesMessage}</p>
    <ul class="cards">
        <li class="card">
            <img src="images/tents.jpg" alt="img">
            <h3>Tents</h3>
            <p>Experience comfort and protection with our high-quality camping tents.</p>
        </li>
        <li class="card">
            <img src="images/bags.jpg" alt="img">
            <h3>Sleeping Bags</h3>
            <p>Stay cozy and warm during your camping trips with our premium sleeping bags.</p>
        </li>
        <li class="card">
            <img src="images/stoves.jpg" alt="img">
            <h3>Camp Stoves</h3>
            <p>Cook delicious meals in the great outdoors with our reliable camp stoves.</p>
        </li>
        <li class="card">
            <img src="images/backpacks.jpg" alt="img">
            <h3>Backpacks</h3>
            <p>Carry your essentials comfortably with our durable and functional camping backpacks.</p>
        </li>
        <li class="card">
            <img src="images/chair.jpg" alt="img">
            <h3>Camp Chairs</h3>
            <p>Relax and unwind in style with our comfortable and portable camping chairs.</p>
        </li>
        <li class="card">
            <img src="images/light.jpg" alt="img">
            <h3>Camp Lights</h3>
            <p>Illuminate your campsite with our reliable and energy-efficient camping lights.</p>
        </li>
    </ul>
</section>

<section class="portfolio" id="portfolio">
    <h2>${ourNews}</h2>
    <p>${ourNewsMessage}</p>
    <ul class="cards">
        <%--                <li class="card">--%>
        <%--                  <img src="images/camping-4.jpg" alt="img">--%>
        <%--                  <h3>Forest Exploration</h3>--%>
        <%--                  <p>Discover the wonders of lush forests and immerse yourself in nature's beauty.</p>--%>
        <%--                </li>--%>
        <c:forEach var="news" items="${requestScope.myNews}">
            <div class="card">
                <li>
                    <c:if test="${sessionScope.user.mail eq news.authorMail}">
                        <form action="MyController" method="post">
                            <input type="hidden" name="command" value="delete_news"/>
                            <input type="hidden" name="news_id" value="${news.id}"/>
                            <button class="close-button" type="submit">&times;</button>
                        </form>
                    </c:if>
                    <img src="${news.imgPath}" alt="img">
                    <h3>${news.title}</h3>
                    <p>${news.content}</p>
                    <p style="font-size: 12px; opacity: 0.6; text-align: left;">Author: ${news.authorUsername}</p>
                </li>
            </div>
        </c:forEach>
    </ul>
</section>

<section class="about" id="about">
    <h2>${aboutUs}</h2>
    <p>${aboutUsMessage}</p>
    <div class="row company-info">
        <h3>${story}</h3>
        <p>${storyMessage}</p>
    </div>
    <div class="row mission-vision">
        <h3>${mission}</h3>
        <p>${missionMessage}</p>
        <h3>${vision}</h3>
        <p>${visionMessage}</p>
    </div>
    <div class="row team">
        <h3>${team}</h3>
        <ul>
            <li>${john}</li>
            <li>${jane}</li>
            <li>${mark}</li>
            <li>${sarah}</li>
        </ul>
    </div>
</section>

<section class="contact" id="contact">
    <h2>${contactUsTwo}</h2>
    <p>${contactUsTwoMessage}</p>
    <div class="row">
        <div class="col information">
            <div class="contact-details">
                <p><i class="fas fa-map-marker-alt"></i>${adress}</p>
                <p><i class="fas fa-envelope"></i>${mail}</p>
                <p><i class="fas fa-phone"></i>${phone}</p>
                <p><i class="fas fa-clock"></i>${work}</p>
                <p><i class="fas fa-clock"></i>${time}</p>
                <p><i class="fas fa-clock"></i>${close}</p>
                <p><i class="fas fa-globe"></i>${site}</p>
            </div>
        </div>
        <div class="col form">
            <form>
                <input type="text" placeholder=${name} required>
                <input type="email" placeholder=${sendMail} required>
                <textarea placeholder=${MessageUs} required></textarea>
                <button id="submit" type="submit">${sendMessage}</button>
            </form>
        </div>
    </div>
</section>

<footer>
    <div>
        <span>${secure}</span>
        <span class="link">
            <a href="#">${home}</a>
            <a href="#contact">${contactUs}</a>
        </span>
    </div>
</footer>
<script>
    // Получаем элементы
    var modal = document.getElementById("myModal");
    var btn = document.getElementById("openModalBtn");
    var span = document.getElementsByClassName("close")[0];

    // Открытие модального окна при клике на кнопку
    btn.onclick = function () {
        modal.style.display = "block";
    }

    // Закрытие модального окна при клике на <span> (x)
    span.onclick = function () {
        modal.style.display = "none";
    }

    // Закрытие модального окна при клике вне его области
    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
</script>
</body>
</html>
