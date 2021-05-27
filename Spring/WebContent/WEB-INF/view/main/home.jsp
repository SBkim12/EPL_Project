<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="poly.dto.EPLDTO"%>
<%
	//모든 페이지에 필수로 있어야하는 것들
	String member_id = (String) session.getAttribute("member_id");
	String member_name = (String) session.getAttribute("member_name");
	String favorite_team = (String) session.getAttribute("favorite_team");
	String member_point = (String) session.getAttribute("member_point");
	String team_logo = (String) session.getAttribute("team_logo");
	//메뉴에 있는 팀 리스트 및 순위
	List<EPLDTO> mList = (List<EPLDTO>) session.getAttribute("teams");
	
	EPLDTO find = mList.get(0);
	String season = find.getSeason();
	
	if(team_logo==null){
		team_logo="../resource/images/EPL_Patch";
	}
	
	//홈에만 있으면 되는것들
	List<Map<String, Object>> rList = (List<Map<String, Object>>) request.getAttribute("mainNews");
	
%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Home</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- 로딩 창  -->
<script>
	function loading_st() {
	   layer_str = "<div id='loading_layer' style='display:block; position:absolute; text-align:center; width:100%;'><img src='../resource/images/loading-img.gif'/></div>"
	   document.write(layer_str);
	}
	function loading_ed() {
	    var ta = document.getElementById('loading_layer');
	    ta.style.display = 'none';
	}
	loading_st();
	window.onload = loading_ed;
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Stylish&display=swap');
</style>
<style>
.myTeam {
	border-bottom: 4px solid gold!important;
}

.hidden {
	display: none;
}

.table {
	z-index: 1;
	width: 100vw;
	height: 100vh;
	display: flex;
	flex-direction: column;
}

.table-row-head {
	width: 100%;
	height: 10vw;
	display: flex;
	border-bottom: 1px solid #ccc;
}

.table-row {
	width: 100%;
	height: 10vw;
	display: flex;
	border-bottom: 1px solid #ccc;
	transition: all 0.1s linear;
	cursor: pointer;
}

.table-row:hover {
	transform: scale(1.05);
}

.table-header {
	font-weight: bold;
}

.table-row-column {
	width: 10%;
	height: 100%;
	display: flex;
	align-items: center;
	justify-content: center;
}

.table-row-column-team {
	width: 20%;
	height: 100%;
	display: flex;
	align-items: center;
	justify-content: flex-start;
}

.table-row-column-team-head {
	width: 20%;
	height: 100%;
	display: flex;
	align-items: center;
	justify-content: center;
}

.champions {
	background: radial-gradient(farthest-side at 10% 50%, dodgerblue, transparent)
}

.europa {
	background: radial-gradient(farthest-side at 10% 50%, lightgreen, transparent)
}

.relegation {
	background: radial-gradient(farthest-side at 10% 50%, lightcoral, transparent)
}

.center_align {
	align-items: center;
	justify-content: center;
}

.table_icon {
	width: 20px;
}

@media ( max-width : 1200px) {
	.remove1 {
		display: none;
	}
	.table-row-column-team {
		width: 11%;
		height: 100%;
		display: flex;
		align-items: center;
		justify-content: center;
	}
	.table-row-column {
		width: 11%;
		height: 100%;
		display: flex;
		align-items: center;
		justify-content: center;
	}
	.table-row-column-team-head {
		width: 11%;
		height: 100%;
		display: flex;
		align-items: center;
		justify-content: center;
	}
}
</style>
<link rel="icon" type="image/png" href="../resource/images/icons/favicon.ico"/>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Mukta:300,400,700">
<link rel="stylesheet" href="../resource/fonts/icomoon/style.css">

<link rel="stylesheet" href="../resource/css/bootstrap.min.css">
<link rel="stylesheet" href="../resource/css/magnific-popup.css">
<link rel="stylesheet" href="../resource/css/jquery-ui.css">
<link rel="stylesheet" href="../resource/css/owl.carousel.min.css">
<link rel="stylesheet" href="../resource/css/owl.theme.default.min.css">
<link rel="stylesheet" href="../resource/css/chat.css">

<link rel="stylesheet" href="../resource/css/aos.css">

<link rel="stylesheet" href="../resource/css/style.css">
<script src="https://kit.fontawesome.com/84865ac036.js"
	crossorigin="anonymous"></script>
</head>
<body>

	<div class="site-wrap">

		<div class="site-mobile-menu">
			<div class="site-mobile-menu-header">
				<div class="site-mobile-menu-logo">
					<a href="#"><img src="../resource/images/EPL_Patch.png" alt="Image" ></a>
				</div>
				<div class="site-mobile-menu-close mt-3">
					<span class="icon-close2 js-menu-toggle"></span>
				</div>
			</div>
			<div class="site-mobile-menu-body"></div>
		</div>

		<header class="site-navbar absolute transparent" role="banner">
			<div class="py-3">
				<div class="container">
					<div class="row align-items-center">
						<div class="col-6 col-md-3 text-left">
							<div class="d-inline-block">
								<a href="#" class="text-secondary px-2 pl-0">
									<span id="favorite_team"> <%=favorite_team%> </span>
								</a> 
							</div>
						</div>
						<div class="col-6 col-md-9 text-right">
							<div class="d-inline-block">
								<a href="#" class="text-secondary p-2 d-flex align-items-center">
									<span class="mr-3" ><i class="fas fa-coins"></i></span>
								 	<span class="d-none d-md-block"><%=member_point%> Point</span>
								</a>
							</div>
							<div class="d-inline-block">
								<a href="#" class="text-secondary p-2 d-flex align-items-center">
									<span class="mr-3"><i class="fas fa-user"></i></span>
									<span class="d-none d-md-block"><%=member_name%> </span>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<nav
				class="site-navigation position-relative text-right bg-black text-md-right"
				role="navigation">
				<div class="container position-relative">
					<div class="site-logo">
						<a href="index.html"><img src="<%=team_logo%>"
							alt="" style="height:100px"></a>
					</div>

					<div class="d-inline-block d-md-none ml-md-0 mr-auto py-3">
						<a href="#" class="site-menu-toggle js-menu-toggle text-white"><span
							class="icon-menu h3"></span></a>
					</div>

					<ul class="site-menu js-clone-nav d-none d-md-block">
						<li class="active"><a href="index.html">Home</a>
							<!-- <ul class="dropdown arrow-top">
								<li><a href="#">Menu One</a></li>
								<li><a href="#">Menu Two</a></li>
								<li><a href="#">Menu Three</a></li>
								<li class="has-children"><a href="#">Sub Menu</a>
									<ul class="dropdown">
										<li><a href="#">Menu One</a></li>
										<li><a href="#">Menu Two</a></li>
										<li><a href="#">Menu Three</a></li>
									</ul></li>
							</ul> -->
						</li>
						<li><a href="news.html">News</a></li>
						<li class="has-children"><a href="/home.do">Teams</a>
							<ul class="dropdown arrow-top">
								<%
								Iterator<EPLDTO> teams = mList.iterator();
								while(teams.hasNext()){
									EPLDTO team = teams.next();
								%>
								<li><a href="#"><%=team.getTeam_name()%></a></li>
								<%}%>
							</ul>
						</li>
						<li><a href="contact.html">Contact</a></li>
						<li><a href="about.html">myInfo</a></li>
						<li><a href="/Logout.do">LogOut</a></li>
					</ul>
				</div>
			</nav>
		</header>


		<div class="slide-one-item home-slider owl-carousel owl-loaded owl-drag" id="slide">
			<%
			Iterator<Map<String, Object>> it = rList.iterator();
			while (it.hasNext()) {
				Map<String, Object> news = it.next(); %>
			<div class='site-blocks-cover overlay' style="background-image: url(<%=news.get("img")%>);" data-aos='fade' data-stellar-background-ratio='0.5'>
				<div class='container'>
					<div class='row align-items-center justify-content-start'>
						<div class='col-md-6 text-center text-md-left' data-aos='fade-up'
							data-aos-delay='400'>
							<h1 class='bg-text-line'><%=news.get("ko_title")%></h1>
							<p>
								<a class='btn btn-primary btn-sm rounded-0 py-3 px-5' data-toggle="modal" data-target="#<%=news.get("title")%>">Read More</a>
							</p>
						</div>
					</div>
				</div>
			</div>
			
			<%}%>
		</div>
		
		
		



		<div class="site-section pt-0 feature-blocks-1" data-aos="fade"
			data-aos-delay="100">
			<div class="container">
				<div class="row">
					<%
					it = rList.iterator();
					while (it.hasNext()) {
						Map<String, Object> news = it.next();
					%>
					<div class="col-md-6 col-lg-4">
						<div class="p-3 p-md-5 feature-block-1 mb-5 mb-lg-0 bg"
							style="background-image: url('<%=news.get("img")%>');">
							<div class="text" ><%=news.get("ko_title")%></h2>
								<p class="mb-0" >
									<a class="btn btn-primary btn-sm px-4 py-2 rounded-0" data-toggle="modal" data-target="#newspaper">Read More</a>
								</p>
							</div>
						</div>
					</div>
					
					<%
					}
					%>

				</div>
			</div>
		</div>

		<div class="site-blocks-vs site-section bg-light">
			<div class="container">

			</div>
		</div>

		
		
		<!-- 리그 순위 테이블 -->
		<div class="site-section block-13 bg-primary fixed overlay-primary bg-image"
			style="background-image: url('../resource/images/EPL_Table.jpg'); background-position:center;">

			<div class="container">
				<div class="row mb-2">
					<div class="col-md-12 text-center">
						<img src="../resource/images/EPL_rank_logo.png" alt="Image" style="width:200px;">
					</div>
				</div>
				<div class="row text-white center_align" style="z-index: 1">
					<div class="col-8 text-left">
						<h6 class="center_align mr-2"
							style="color: dodgerblue; display: inline-block">
							<img src="../resource/images/champions.png" class="table_icon"
								style="-webkit-filter: opacity(.5) drop-shadow(0 0 0 dodgerblue); filter: opacity(.5) drop-shadow(0 0 0 dodgerblue);">챔스
						</h6>
						<h6 class="center_align mr-2" style="color: lightgreen; display: inline-block"">
							<img src="../resource/images/europa.png" class="table_icon"
								style="-webkit-filter: opacity(.5) drop-shadow(0 0 0 lightgreen); filter: opacity(.5) drop-shadow(0 0 0 lightgreen);">유로파
						</h6>
						<h6 class="center_align" style="color: lightcoral; display: inline-block"">
							<i class="fas fa-chevron-circle-down" class="table_icon"></i>강등
						</h6>
					</div>
					<div class="col-4 text-right">
						<h6 class=""><%=season%></h6>
					</div>
				</div>
				<div class="row">
					<div class="table text-white">
						<div class="table-row-head table-header">
							<div class="table-row-column">순위</div>
							<div class="table-row-column-team-head table-center">팀</div>
							<div class="table-row-column">승점</div>
							<div class="table-row-column">승</div>
							<div class="table-row-column">무</div>
							<div class="table-row-column">패</div>
							<div class="table-row-column">득점</div>
							<div class="table-row-column">실점</div>
							<div class="table-row-column">득실</div>
						</div>
						<%
						teams = mList.iterator();
						while (teams.hasNext()) {
							EPLDTO team = teams.next();
						if(team.getRecent_rank()<=4){
						%>
						<div class="table-row champions" onclick=goTeam(this)>
						<%}else if(team.getRecent_rank()<=6){ %>
						<div class="table-row europa" onclick=goTeam(this)>
						<%}else if(team.getRecent_rank()>=18){ %>
						<div class="table-row relegation" onclick=goTeam(this)>
						<%}else{ %>
						<div class="table-row" onclick=goTeam(this)>
						<%} %>
							<div class="table-row-column"><%=team.getRecent_rank()%></div>
							<div class="table-row-column-team">
								<img src="<%=team.getLogo()%>" style="width: 30px">
								<span class="remove1"><%=team.getKo_name()%>
								<span>
							</div>
							<div class="table-row-column"><%=team.getRecent_points()%></div>
							<div class="table-row-column"><%=team.getRecent_won()%></div>
							<div class="table-row-column"><%=team.getHome_draw()%></div>
							<div class="table-row-column"><%=team.getHome_lost()%></div>
							<div class="table-row-column"><%=team.getGoals_scored()%></div>
							<div class="table-row-column"><%=team.getGoals_against()%></div>
							<div class="table-row-column"><%=team.getGoals_scored()-team.getGoals_against()%></div>
							<div class="hidden" name="<%=team.getTeam_name()%>"><%=team.getTeam_name()%></div>
						</div>
						<%} %>
					</div>
				</div>
			</div>

		</div>
		
		<div class="site-section">
			<div class="container">
				
			</div>
		</div>

		<footer class="site-footer border-top">
			
		</footer>
	</div>

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
	<%
	it = rList.iterator();
	while (it.hasNext()) {
		Map<String, Object> news = it.next();
		List<String> ko_contents = (List<String>) news.get("ko_contents");
	%>
	<!-- Newspaper Modal -->
	<div class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel" aria-hidden="true" id="<%=news.get("title")%>" style="z-index:2000;">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			<h4 class="modal-title" id="myModalLabel"><%=news.get("ko_title") %></h4>
		</div>
        <div class="modal-body">
		<% 
			Iterator<String> ko_content = ko_contents.iterator();
			while(ko_content.hasNext()){
			%>
			<p><%=ko_content.next().toString()%></p>
			<br>
		<%		
			}
		%>
		<div class="container-fluid">
            <div class="row">
              <div class="col-md-4">.col-md-4</div>
              <div class="col-md-4 col-md-offset-4">.col-md-4 .col-md-offset-4</div>
            </div>
            <div class="row">
              <div class="col-md-3 col-md-offset-3">.col-md-3 .col-md-offset-3</div>
              <div class="col-md-2 col-md-offset-4">.col-md-2 .col-md-offset-4</div>
            </div>
            <div class="row">
              <div class="col-md-6 col-md-offset-3">.col-md-6 .col-md-offset-3</div>
            </div>
            <div class="row">
              <div class="col-sm-9">
                Level 1: .col-sm-9
                <div class="row">
                  <div class="col-xs-8 col-sm-6">
                    Level 2: .col-xs-8 .col-sm-6
                  </div>
                  <div class="col-xs-4 col-sm-6">
                    Level 2: .col-xs-4 .col-sm-6
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary">Save changes</button>
        </div>
        <%
        }
        %>
	
	<script src="../resource/js/jquery-3.3.1.min.js"></script>
	<script src="../resource/js/jquery-migrate-3.0.1.min.js"></script>
	<script src="../resource/js/jquery-ui.js"></script>
	<script src="../resource/js/popper.min.js"></script>
	<script src="../resource/js/bootstrap.min.js"></script>
	<script src="../resource/js/owl.carousel.min.js"></script>
	<script src="../resource/js/jquery.stellar.min.js"></script>
	<script src="../resource/js/jquery.countdown.min.js"></script>
	<script src="../resource/js/jquery.magnific-popup.min.js"></script>
	<script src="../resource/js/aos.js"></script>
	<script src="../resource/js/sweetalert.min.js"></script>
	<!-- 웹소켓 채팅 -->
	<script>
	var element = $('.floating-chat');
	var myStorage = localStorage;

	var ws;
	var messages = $('.messages');

	var favorite_team = "<%=favorite_team%>";
	var member_name = "<%=member_name%>";

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
			ws = new WebSocket("ws://localhost:9005/echo.do");

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
			var newMessage = userInput.html().replace(/\<div\>|\<br.*?\>/ig,
					'\n').replace(/\<\/div\>/g, '').trim().replace(/\n/g,
					'<br>');
			if (newMessage.replaceAll("&nbsp;", "").replaceAll("<br>", "")
					.trim() == "") {
				return;
			}
		
			var text = newMessage + "," + member_name + "," + favorite_team;
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
			
			if (text.split(",")[1] != member_name) {
				messagesContainer.append([
		            '<li class="other">',
		            text.split(",")[0],
		            '</li>'
		        ].join(''));
			} else {
				messagesContainer.append(['<li class="self">',text.split(",")[0],'</li>'].join(''));
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
	<script>
	function goTeam(team){
		console.log(team);
		let team_name = $(team).children("div.hidden")[0].innerHTML;
		console.log(team_name);		
	}
	
	$(function() {
		var elem = $(".hidden[name='<%=favorite_team%>']");
		
		elem.parent().addClass('myTeam');
	})
	</script>
	
	
 <%--    <!-- 뉴스 조회 -->
	<script>
	$(function(){
		$.ajax({
			url : "mainNews.do",
			type : "post",
			data : {"team" :  "<%=favorite_team%>"},
			dataType : "json",
			success : function(data) {
				var resHTML = "";
				$.each(data, function(idx, val) {
					resHTML += "<div class='site-blocks-cover overlay' style='background-image: url("+val.img +"' data-aos='fade' data-stellar-background-ratio='0.5'>"
					resHTML += "<div class='container'><div class='row align-items-center justify-content-start'>";
					resHTML +="<div class='col-md-6 text-center text-md-left' data-aos='fade-up' data-aos-delay='400'>";
					resHTML +="<h1 class='bg-text-line'>"+ val.ko_title +"</h1>" ;
					resHTML +="<p><a href='#' class='btn btn-primary btn-sm rounded-0 py-3 px-5'>Read More</a></p>";
					resHTML +="</div></div></div></div>";
				});
				$(".home-slider").append(resHTML);
			}	
		});
	});
	</script> --%>
	
	<script src="../resource/js/main.js"></script>

</body>
</html>