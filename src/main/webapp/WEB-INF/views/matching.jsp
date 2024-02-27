<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Matching Page</title>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.1/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
</head>
<body>

<h2>Matching Page</h2>

<form id="matchingForm">
    <label for="teamNo">Team Number:</label>
    <input type="text" id="teamNo" name="teamNo" required><br>

    <label for="teamMemberNo">Team Member Numbers (comma-separated):</label>
    <input type="text" id="teamMemberNo" name="teamMemberNo" required><br>

    <label for="matchType">Match Type:</label>
    <input type="text" id="matchType" name="matchType" required><br>

    <label for="matchCapacity">Match Capacity:</label>
    <input type="number" id="matchCapacity" name="matchCapacity" required><br>

    <label for="teamRating">Team Rating:</label>
    <input type="text" id="teamRating" name="teamRating" required><br>

    <button type="button" onclick="addToQueue()">Add to Queue</button>
</form>

<div id="matches"></div>

<script>
    var stompClient = null;

    function connect() {
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/matches', function (message) {
                showMatchResult(message.body);
            });
            stompClient.subscribe('/topic/failure', function (message) {
                showFailureMessage(message.body);
            });
        });
    }

    function addToQueue() {
        var teamNo = $("#teamNo").val();
        var teamMemberNo = $("#teamMemberNo").val().split(",").map(Number);
        var matchType = $("#matchType").val();
        var matchCapacity = $("#matchCapacity").val();
        var teamRating = $("#teamRating").val();

        var matchingRequest = {
            teamNo: teamNo,
            teamMemberNo: teamMemberNo,
            matchType: matchType,
            matchCapacity: matchCapacity,
            teamRating: teamRating
        };

        stompClient.send("/app/addTeamToQueue", {}, JSON.stringify(matchingRequest));
    }

    function showMatchResult(matchResult) {
        $("#matches").append("<p>" + matchResult + "</p>");
        alert("매치 성공!");

    }

    function showFailureMessage(message) {
        $("#matches").append("<p style='color: red;'>" + message + "</p>");
        alert("매치 실패!");
    }

    $(function () {
        connect();
    });
</script>

</body>
</html>
