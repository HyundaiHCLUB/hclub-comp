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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>H-CLUB</title>
    <style>
        #wrapper {
            display: flex;
            flex-direction: column;
        }
        .ranking-container {
            width: 90%;
            margin: 50px auto ;
        }
        .ranking-container h2 {
            text-align: center;
        }

        .user {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
            border-radius: 25px;
            padding: 10px;
            background-color: #f5f5f5;
        }

        .top1, .top2, .top3 {
            background-color: #e0e0e0;
        }

        .profile-picture {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            margin-right: 15px;
            margin-left: 15px;
        }
        .profile-picture img {
            width: 100%; /* 이미지의 너비를 div의 너비와 같게 설정 */
            height: 100%; /* 이미지의 높이를 div의 높이와 같게 설정 */
            border-radius: 50%; /* 이미지를 원형으로 만듬 */
            object-fit: cover; /* 이미지의 비율을 유지하면서 div를 꽉 채움 */
        }
        .name-rating {
            display: flex;
            flex-direction: column;
        }

        .name {
            font-weight: bold;
        }

        .rating {
            color: black;
        }

        /* Customize the top 3 */
        .top1 .profile-picture {
            /*background-image: url('path-to-crown-image');*/
            background-size: cover;
        }
        .top-users{
            margin: auto;
        }
        .top1, .top2, .top3 {
            background-color: #76b852; /* A green color for the top 3 users */
            color: white;
        }

        .top1 {
            background-color: #4CAF50; /* A darker green color for the top user */
        }

        .top2, .top3 {
            background-color: #8BC34A; /* A lighter green color for 2nd and 3rd place */
        }

        /* 왕관 아이콘 스타일링 */
        .crown-icon {
            font-size: 24px; /* 아이콘 크기 조정 */
            margin-right: 10px; /* 아이콘과 텍스트 사이의 간격 조정 */
        }

        /* 1위, 2위, 3위에 대한 왕관 색상 */
        .top1 .crown-icon { color: gold; }
        .top2 .crown-icon { color: silver; }
        .top3 .crown-icon { color: #cd7f32; } /* Bronze color */

    </style>
</head>
<body>
<div id="wrapper">
    <header>
        <%@include file="../header.jsp"%>
    </header>
    <div class="ranking-container">
        <h2>오늘의 TOP 10</h2>
        <!-- 데이터베이스 데이터 추가되면 jstl 코드로 교체-->
        <%--<c:forEach var="user" items="${users}" varStatus="status">
            <li class="${status.index < 3 ? 'top' : ''}">
                <span class="rank">${status.index + 1}</span>
                <span class="name">${user.name}</span>
                <span class="rating">${user.rating}점</span>
            </li>
        </c:forEach>--%>
        <div class="top-users">
            <div class="user top1">
                <i class="fas fa-crown crown-icon"></i>
                <div class="rank">1</div>
                <div class="profile-picture">
                    <img src="/resources/image/sample.png" alt="팀 로고"/>
                </div>
                <div class="name-rating">
                    <div class="name">혜연</div>
                    <div class="rating">1892점</div>
                </div>
            </div>
            <div class="user top2">
                <i class="fas fa-crown crown-icon"></i>
                <div class="rank">2</div>
                <div class="profile-picture">
                    <img src="/resources/image/sample.png" alt="팀 로고"/>
                </div>
                <div class="name-rating">
                    <div class="name">차은우</div>
                    <div class="rating">1822점</div>
                </div>
            </div>
            <div class="user top3">
                <i class="fas fa-crown crown-icon"></i>
                <div class="rank">3</div>
                <div class="profile-picture">
                    <img src="/resources/image/sample.png" alt="팀 로고"/>
                </div>
                <div class="name-rating">
                    <div class="name">홍은채</div>
                    <div class="rating">1722점</div>
                </div>
            </div>
        </div>

        <div class="user">
            <div class="rank">4</div>
            <div class="profile-picture">
                <img src="/resources/image/sample.png" alt="팀 로고"/>
            </div>
            <div class="name-rating">
                <div class="name">홍은채</div>
                <div class="rating">1722점</div>
            </div>
        </div>
        <div class="user">
            <div class="rank">5</div>
            <div class="profile-picture">
                <img src="/resources/image/sample.png" alt="팀 로고"/>
            </div>
            <div class="name-rating">
                <div class="name">이름</div>
                <div class="rating">1500점</div>
            </div>
        </div>
        <div class="user">
            <div class="rank">6</div>
            <div class="profile-picture">
                <img src="/resources/image/sample.png" alt="팀 로고"/>
            </div>
            <div class="name-rating">
                <div class="name">이름</div>
                <div class="rating">1500점</div>
            </div>
        </div>
        <div class="user">
            <div class="rank">7</div>
            <div class="profile-picture">
                <img src="/resources/image/sample.png" alt="팀 로고"/>
            </div>
            <div class="name-rating">
                <div class="name">이름</div>
                <div class="rating">1500점</div>
            </div>
        </div>
        <div class="user">
            <div class="rank">8</div>
            <div class="profile-picture">
                <img src="/resources/image/sample.png" alt="팀 로고"/>
            </div>
            <div class="name-rating">
                <div class="name">이름</div>
                <div class="rating">1500점</div>
            </div>
        </div>
        <div class="user">
            <div class="rank">9</div>
            <div class="profile-picture">
                <img src="/resources/image/sample.png" alt="팀 로고"/>
            </div>
            <div class="name-rating">
                <div class="name">이름</div>
                <div class="rating">1500점</div>
            </div>
        </div>
    </div>
</div>
<footer>
    <%@include file="../footer.jsp"%>
</footer>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script> <!-- jquery CDN -->
<script>

</script>
</html>