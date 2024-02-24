<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <link rel="stylesheet" href="${path}/resources/css/main.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet"> <!--CDN 링크 -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100..900&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>마이페이지</title>
    <style>
        #wrapper {
            display: flex;
            flex-direction: column;
        }
        .container {
            width: 100%;
        }
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }
        a:active {
            color: #4CAF50;
        }
        .profile-container {
            background-color: #fff;
            max-width: 600px;
            max-height: 100%;
            margin: 50px auto;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .profile-header {
            width: 100%;
            display: flex;
            align-items: center;
            border-bottom: 1px solid #e1e1e1;
            padding: 16px;
        }

        .profile-pic {
            width: 70px;
            height: 70px;
            border-radius: 35px;
            margin-right: 16px;
        }

        .mypage-menus {
            margin-top: 30px;
            padding-bottom: 30px;
        }
        .menu {
            display: flex;
            align-items: center;
            width: 70%;
            height: 50px;
            padding: 10px;
            padding-left: 30px;
            background-color: #f0f0f0;
            border-radius: 20px;
            margin: 10px auto;
        }
        .menu span {
            margin-top: auto;
            margin-bottom: auto;
            margin-left: 20px;
            font-weight: bold;
        }
        .fas fa-user-circle icon {
            margin : auto 10px auto 30px;
        }



    </style>
</head>
<body>
<div id="wrapper">
    <header>
        <%@include file="../header.jsp"%>
    </header>
    <div class = "container">
        <div class="profile-container">
            <div class="profile-header">
                <img src="/resources/image/sample.png" alt="프로필 이미지" class="profile-pic">
                <div class="user-info">
                    <h3>김은채</h3>
                    <p>IT 인프라 사업부 (선임)</p>
                </div>
            </div>
            <div class="mypage-menus">
                <div class="menu">
                    <i class="fa-solid fa-user"></i>
                    <span><a href="/comp/updateProfileView">프로필 수정</a></span>
                </div>
                <div class="menu">
                    <i class="fa-regular fa-flag"></i>
                    <span><a>내가 개설한 동아리</a></span>
                </div>
                <div class="menu">
                    <i class="fa-solid fa-heart"></i>
                    <span><a>즐겨찾기</a></span>
                </div>
                <div class="menu">
                    <i class="fa-solid fa-trophy"></i>
                    <span><a>매치 히스토리</a></span>
                </div>
            </div>
        </div>
    </div>
</div>
<footer>
    <%@include file="../footer.jsp"%>
</footer>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script> <!-- jquery CDN -->
<script src="https://kit.fontawesome.com/5ba1c6c3a8.js" crossorigin="anonymous"></script> <!-- font awesome icons-->
<script>

</script>
</html>