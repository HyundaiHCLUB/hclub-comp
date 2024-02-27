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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>메인 페이지</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background: #f0f0f0;
        }
        .container {
            background: white;
            display: flex;
            flex-direction: column; /* Stack children vertically */
            justify-content: center; /* Align children vertically in the center */
            align-items: center; /* Align children horizontally in the center */
            min-height: 100vh; /* Full viewport height */
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .match-detail {
            border-style: solid;
            border-radius: 20px;
            border-color: #668C4A;
            padding: 10px;
            display: flex;
            justify-content: space-around;
            align-items: center; /* Align items vertically */
            width: 90%; /* Full width */
        }
        .team {
            text-align: center;
            flex: 1; /* Flex grow to take available space */
        }
        .team img {
            width: 80px;
            height: 80px;
            border-radius: 40px;
        }
        .team p {
            margin: 5px 10px;
            font-size: 12px;
        }
        .vs {
            flex: 0; /* Do not grow or shrink */
            padding: 0 20px; /* Spacing around VS */
            color: blue;
        }
        .chat-button{
            color:#668C4A;
            border-style: solid;
            border-color: #668C4A;
            background: none;
            padding: 15px;
            width: 80%;
            border-radius: 10px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            margin: 20px 0; /* Spacing for buttons */
        }
        .team-detail h5 {
            color: black; /* 글자색 */
            padding: 5px 10px; /* 상하좌우 패딩 */
            border-style: solid;
            border-color: black;
            border-radius: 10px; /* 둥근 모서리 */
            display: inline-block; /* 인라인 블록 요소로 만들기 */
            margin: 0; /* 기본 마진 제거 */
        }
        .btn-match-start{
            border-style: none;
            border-radius: 10px;
            background: #EF7353;
            color: white;
            width: fit-content;
            font-weight: bold;
            margin-left: auto; /* 버튼을 오른쪽으로 밀기 */
            margin-right: 35px;
            padding: 15px;
        }
    </style>
</head>
<body>
<div id="wrapper">
    <header>
        <%@include file="../header.jsp"%>
    </header>
    <main>
        <div class="container">
            <div class="match-detail">
                <div class="team">
                    <img src="/resources/image/team_logo_sample_1.png" alt="팀 로고"/>
                    <h5>한반두</h5>
                    <div class="team-detail">
                        <h5>팀장</h5>
                        <p>호날두</p>
                    </div>
                    <div class="team-detail">
                        <h5>장소</h5>
                        <p>서울특별시 강남구 00로 00길</p>
                    </div>
                    <div class="team-detail">
                        <h5>상품</h5>
                        <p>H-cafe 5000원 상품권</p>
                    </div>
                    <div class="team-detail">
                        <h5>팀 레이팅</h5>
                        <p>1500</p>
                    </div>
                    <button class="btn-team-detail">상세정보</button>
                </div>
                <div class="vs">
                    <h3>VS</h3>
                </div>
                <div class="team">
                    <img src="/resources/image/team_logo_sample_2.png" alt="팀 로고"/>
                    <h5>장한평핫스퍼</h5>
                    <div class="team-detail">
                        <h5>팀장</h5>
                        <p>손흥민</p>
                    </div>
                    <div class="team-detail">
                        <h5>장소</h5>
                        <p>장한평 농구장</p>
                    </div>
                    <div class="team-detail">
                        <h5>상품</h5>
                        <p>H-cafe 10000원 상품권</p>
                    </div>
                    <div class="team-detail">
                        <h5>팀 레이팅</h5>
                        <p>1400</p>
                    </div>
                    <button class="btn-team-detail">상세정보</button>
                </div>
            </div>
            <button class="chat-button">채팅하기</button>
            <button class="btn-match-start">경기시작</button>
        </div>
    </main>
</div>
<footer>
    <%@include file="../footer.jsp"%>
</footer>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script> <!-- jquery CDN -->
<script>
    $(document).ready(function() {
        $('.btn-match-start').click(function(e) {
            e.preventDefault();
            window.location.href = "${path}/comp/matchRecord";
        });
    })
</script>
</html>