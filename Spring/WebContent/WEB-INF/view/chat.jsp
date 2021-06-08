<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
//모든 페이지에 필수로 있어야하는 것들
String member_id = (String) session.getAttribute("member_id");
String member_name = (String) session.getAttribute("member_name");
String favorite_team = (String) session.getAttribute("favorite_team");
String member_point = (String) session.getAttribute("member_point");
String team_logo = (String) session.getAttribute("team_logo");
%>
<link rel="stylesheet" href="../resource/css/chat.css">


<!-- 채팅 박스 -->
<div class="floating-chat" style="z-index: 100;">
	<i class="fa fa-comments" aria-hidden="true"></i>
	<div class="chat">
		<div class="header">
			<span class="title"> Live Chat </span>
			<button>
				<i class="fa fa-times" aria-hidden="true"></i>
			</button>

		</div>
		<ul id="messages" class="messages">
		</ul>
		<div class="footer">
			<div class="text-box" id="text-box" contenteditable="true"
				disabled="true"></div>
			<button id="sendMessage">send</button>
		</div>
	</div>
</div>
<script src="../resource/js/jquery-3.3.1.min.js"></script>
<!-- 웹소켓 채팅 -->
<script>
	var element = $('.floating-chat');
	var myStorage = localStorage;

	var ws;
	var messages = $('.messages');
	
	var tema_logo = "<%=team_logo%>";
	var member_id = "<%=member_id%>";
	
	setTimeout(function() {
		element.addClass('enter');
	}, 1000);

	element.click(openSocket);

	function openSocket() {

		var messages = element.find('.messages');
		var textInput = element.find('.text-box');
		element.find('>i').hide();
		element.addClass('expand');
		element.find('.chat').addClass('enter');
		var strLength = textInput.val().length * 2;
		textInput.keydown(onMetaAndEnter).prop("disabled", false).focus();
		element.off('click', openSocket);
		element.find('.header button').click(closeSocket);
		element.find('#sendMessage').click(send);
		messages.scrollTop(messages.prop("scrollHeight"));

		if (ws !== undefined && ws.readyState !== WebSocket.CLOSED) {
			writeResponse("WebSocket is already opened.");
			return;
		}
		// 웹소켓 객체 만드는 코드
		ws = new WebSocket("ws://15.164.51.243/echo.do");/* localhost:9005 *//* 15.164.51.243 */

		ws.onopen = function(event) {
			if (event.data === undefined)
				return;

			writeResponse(event.data);
		};
		ws.onmessage = function(event) {
			writeResponse(event.data);
		};
		ws.onclose = function(event) {
			writeResponse("Chat Closed");
		}
	}

	function send() {
		var userInput = $('.text-box');
		var newMessage = userInput.html().replace(/\<div\>|\<br.*?\>/ig, '\n')
				.replace(/\<\/div\>/g, '').trim().replace(/\n/g, '<br>');
		if (newMessage.replaceAll("&nbsp;", "").replaceAll("<br>", "").trim() == "") {
			return;
		}

		var text = newMessage + "ᚯᚰ" + member_id + "ᚯᚰ" + tema_logo;
		console.log(text)
		ws.send(text);
		text = "";

		var messagesContainer = $('.messages');

		// clean out old message
		userInput.html('');
		// focus on input
		userInput.focus();

		messagesContainer.finish().animate({
			scrollTop : messagesContainer.prop("scrollHeight")
		}, 250);

	}

	function closeSocket() {
		element.find('.chat').removeClass('enter').hide();
		element.find('>i').show();
		element.removeClass('expand');
		element.find('.header button').off('click', closeSocket);
		element.find('#sendMessage').off('click', send);
		element.find('.text-box').off('keydown', onMetaAndEnter).prop(
				"disabled", true).blur();
		setTimeout(function() {
			element.find('.chat').removeClass('enter').show()
			element.click(openSocket);
		}, 500);

		ws.close();
	}
	function writeResponse(text) {

		var messagesContainer = $('.messages');
		
		var message = text.split("ᚯᚰ")[0];
		var member = text.split("ᚯᚰ")[1];
		var logo = text.split("ᚯᚰ")[2];
		
		if(logo == null){
			console.log(message);
		}else{
			if (member != member_id) {
				messagesContainer.append([ '<li class="other">','<img class="chat_logo" src="'+logo+'">', message, '</li>' ].join(''));
				
			} else {
				messagesContainer.append([ '<li class="self">','<img class="chat_logo" src="'+logo+'">', message, '</li>' ].join(''));
			}
		}

		messagesContainer.finish().animate({
			scrollTop : messagesContainer.prop("scrollHeight")
		}, 250);
	}
	function onMetaAndEnter(event) {
		if ((event.metaKey || event.ctrlKey) && event.keyCode == 13) {
			send();
		}
	}
</script>