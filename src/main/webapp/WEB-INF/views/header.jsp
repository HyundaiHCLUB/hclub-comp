<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <link rel="stylesheet" href="${path}/resources/css/nav.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet"> <!--CDN 링크 -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100..900&display=swap" rel="stylesheet">
</head>
<body>
<nav class="navbar">
    <div class="navbar_logo">
        <a href="home.jsp"><img src="${path}/resources/image/logo.png"></a>
    </div>
    <div class="navbar_icons">
        <li><i class="fa-solid fa-magnifying-glass fa-2x"></i></li>
        <li><i class="fa-regular fa-heart fa-2x"></i></li>
        <li><i class="fa-regular fa-bell fa-2x"></i></li>
    </div>
</nav>
<div class="navbar_menu">
    <li><a href="home.jsp"><h1>투데이</h1></a></li>
    <li><a href=""><h1>인기 동아리</h1></a></li>
    <li><a href=""><h1>신규 동아리</h1></a></li>
    <li><a href=""><h1>오늘의 랭킹</h1></a></li>
    <li><a href=""><h1>내 모임</h1></a></li>
</div>
</body>
</html>