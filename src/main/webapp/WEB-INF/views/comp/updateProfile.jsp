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
    <title>프로필 수정</title>
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

        .profile-form{
            margin-top: 30px;
            width: 100%;
        }
        .form-label {
            font-family: 'Arial', sans-serif;
            color: #333;
            margin-left: 20px;
            margin-bottom: 10px; /* Adjust as needed */
        }

        .form-label label {
            display: block;
            margin: 5px;
            font-size: 14px; /* Adjust as needed */
            font-weight: bold;
            color: black; /* Adjust as needed */
        }

        .form-label input[type="text"], .form-label input[type="password"] {
            font-size: 16px; /* Adjust as needed */
            padding: 8px 12px; /* Adjust as needed */
            border: 1px solid #ccc; /* Adjust as needed */
            border-radius: 4px; /* Adjust as needed */
            width: 70%; /* Adjust as needed */
            box-sizing: border-box; /* Keeps padding inside width */
            margin-bottom: 10px; /* Adjust as needed */
        }

        /* Placeholder styling */
        .form-label input[type="text"]::placeholder {
            color: #aaa; /* Adjust as needed */
        }

        /* Focus state styling */
        .form-label input[type="text"]:focus {
            outline: none;
            border-color: #777; /* Adjust as needed */
        }

        .btn-submit {
            display: block;
            text-align: center;
            font-weight: bold;
            border-style: none;
            border-radius: 30px;
            padding: 10px;
            margin :5px auto;
            background: #46675C;
            color: white;
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
            <form class="profile-form">
                <!-- 이름 필드 -->
                <div class="form-label">
                    <label for="name">이름</label>
                    <input type="text" id="name" name="name" placeholder="이름"> <br/>
                </div>
                <!-- 소속(직위) 필드 -->
                <div class="form-label">
                    <label for="affiliation">소속(직급)</label>
                    <input type="text" id="affiliation" name="affiliation" placeholder="IT 인프라 사업부 (선임)"> <br/>
                </div>
                <!-- 사번 필드 -->
                <div class="form-label">
                    <label for="employee-number">사번</label>
                    <input type="text" id="employee-number" name="employee_number" placeholder="20241111"> <br/>
                </div>
                <!-- 비밀번호 필드 -->
                <div class="form-label">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password"><br/>
                </div>
                <div class="form-label">
                    <label for="password">비밀번호확인</label>
                    <input type="password" id="password-check" name="password"><br/>
                </div>
                <!-- 제출 버튼 -->
                <input type="submit" value="정보 업데이트" class="btn-submit"><br/>
            </form>
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