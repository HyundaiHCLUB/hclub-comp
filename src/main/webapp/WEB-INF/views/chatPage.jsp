<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
    <div>
    <!-- 상대방 번호 넣기 -->
        <button onclick="createRoom(3)">chat</button>
    </div>
    <div id="room" style="display:none">
		<div>대화방</div>	
		<div id="other"></div>
		<div id="message"></div>
		<input type="text" id="chatTxt">
		<button onclick="sendChat()">채팅전송</button>
    </div>	
    
	<button id="alarm" onclick="createRoom(3)" style="display:none">알람</button>
    <!-- websocket javascript -->
    <script src="https://d3e54v103j8qbb.cloudfront.net/js/jquery-3.5.1.min.dc5e7f18c8.js?site=643e03d7f19df83b3f4191ad" type="text/javascript" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>    
    <script type="text/javascript">
	    var servername = '<%=request.getServerName()%>';
	    var wsUriToWeb = "wss://<%=request.getServerName()%>:<%=request.getServerPort()%>/ws/chat"; //주소 확인!!
	    if(servername == "localhost")
	    	wsUriToWeb = "ws://<%=request.getServerName()%>:<%=request.getServerPort()%>/ws/chat"; //주소 확인!!
	    let websocketToWeb = null;
	
	    var urdata = getRandomNumber(1, 100);//"${uuIdx}"; =>회원의 번호로 대체
	    var otherUseridx;
	    var messagePop = 0;
	    console.log("urdata:"+urdata);
	    //화면에 접속하면 소켓오픈
	    if(urdata!= null && urdata!="" && urdata!="null")
	      initToWeb();
	
	    function initToWeb() {
	    	websocketToWeb = new WebSocket(wsUriToWeb);
	    	websocketToWeb.onopen = function(evt) {
	    		onOpenToWeb(evt);
	    	};
	
	    	websocketToWeb.onmessage = function(evt) {
	    		onMessageToWeb(evt);
	    	};
	
	    	websocketToWeb.onerror = function(evt) {
	    		onErrorToWeb(evt);
	    	};
	
	    	websocketToWeb.onclose = function(evt) {
	    		console.log("reconnect");
	    	};
	    }    
	    
	    function onOpenToWeb(evt) {
	    	console.log("webserver open");
	    }

	    function onErrorToWeb(evt) {
	    	console.log("onErrorToWEb");
	    }

	    function doSendToWeb(message) {
	    	websocketToWeb.send(message);
	    }
	    
	    function onMessageToWeb(evt) { // 받은 메세지를 보여준다
	    	try {
	    		let obj = JSON.parse(evt.data);
	    		console.log("obj : ", obj);

	    		if (obj.protocol == "doLogin") {
	    			//웹소켓 접속이 정상적으로 작동
	    			if (urdata != null && urdata != 'null' && urdata != '') {
	    				let obj = new Object;
	    				obj.protocol = "login";
	    				//본인의 번호 session에 저장
	    				obj.userIdx = urdata;
	    				//서버로 패킷을 전송
	    				doSendToWeb(JSON.stringify(obj));
	    			}
	    		}
	    		else if (obj.protocol == "showRoom") {
	    			otherUseridx = obj.otherUseridx;
	    			$("#other").html(otherUseridx+"님과의 대화");
	    			$("#room").show();
	    			messagePop = 1;
	    			//추후에 대화내역 prepend하기
	    		}
	    		else if (obj.protocol == "sendChat") {
	    			
	    			let fromUser = obj.fromUser;
	    			let toUser = obj.toUser;
	    			let chatMsg = obj.chatMsg;
	    			//보낸사람
	    			console.log("onMessageToWeb sendChat fromUser:"+fromUser+" urdata:"+urdata);
	    			if(urdata==fromUser){
	    				//메세지만 추가
	    				$("#message").append("<div>"+chatMsg+"</div>");
	    			}
	    			//받는사람
	    			else if(urdata==toUser){
	    				//알람
	    				if(messagePop == 0)
	    					$("#alarm").show();
	    				else if(messagePop == 1)
	    					$("#message").append("<div>"+chatMsg+"</div>");
	    			}
	    			
	    			$("#other").html(toUser+"님과의 대화");
	    			$("#room").show();
	    			//추후에 대화내역 prepend하기
	    		}
	    	} catch (err) {
	    		console.log("[protocol]" + wpro + " " + err.message);
	    	}
	    }
	    
	    function getRandomNumber(min, max) {
    	  return Math.floor(Math.random() * (max - min + 1)) + min;
    	}
	    //서버에다가 채팅방 create선언 패킷 전송
	    function createRoom(otherUseridx) {
	        let obj = new Object;
	        obj.protocol = "createRoom";
	        //상대 회원번호 
	        obj.otherUseridx = otherUseridx;
	        doSendToWeb(JSON.stringify(obj));
	    }
	    function sendChat() {
	    	console.log("sendChat");
	        let obj = new Object;
	        obj.protocol = "sendChat";
	        //상대 회원번호 
	        obj.otherUseridx = otherUseridx;
	        obj.chatMsg = $("#chatTxt").val();;
	        doSendToWeb(JSON.stringify(obj));
	    }
 	</script>
</body>
</html>
