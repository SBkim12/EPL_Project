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
%>

<!DOCTYPE html>
<html lang="en">
<head>
<link rel="shortcut icon" href="../resource/images/icons/favicon.ico"
	type="image/vnd.microsoft.icon">
<title>EPL news</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<style>

</style>
<!-- 로딩 창 -->
<script src="../resource/js/loading.js"></script>

<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Mukta:300,400,700">
<link rel="stylesheet" href="../resource/fonts/icomoon/style.css">

<link rel="stylesheet" href="../resource/css/bootstrap.min.css">
<link rel="stylesheet" href="../resource/css/magnific-popup.css">
<link rel="stylesheet" href="../resource/css/jquery-ui.css">
<link rel="stylesheet" href="../resource/css/owl.carousel.min.css">
<link rel="stylesheet" href="../resource/css/owl.theme.default.min.css">
<link rel="stylesheet" href="../resource/css/rank_table.css">

<link rel="stylesheet" href="../resource/css/aos.css">

<link rel="stylesheet" href="../resource/css/style.css">

<script src="https://kit.fontawesome.com/84865ac036.js"
	crossorigin="anonymous"></script>
</head>
<body>


	<div class="site-wrap">
		<!-- 내비 바 -->
		<jsp:include page="../navbar.jsp"></jsp:include>

		
		<div class="site-section bg-light">
			<div class="container">
				<div class="row">
					<%
					Iterator<EPLDTO> teams = mList.iterator();
					while (teams.hasNext()) {
						EPLDTO team = teams.next();
					%>
					<div class="mb-1 mb-lg-0 col-3 col-md-2 col-lg-1 text-center p-0">
						<div class="player mb-2">
							<img src="<%=team.getLogo()%>" name="<%=team.getTeam_name()%>" alt="Image" class="img-fluid image rounded-circle btn p-2" onclick='fnSearchNews(this);'>
						</div>
					</div>
					<%
					}
					%>
				</div>
				<hr/>
				<div class="text-center">
					<h2 id="team_name">Select Team!</h2>
				</div>
				<hr/>
				<div class="row" id="news">
				</div>
			</div>
		</div>
		


		<footer class="site-footer border-top"> </footer>
	</div>

	<jsp:include page="../chat.jsp"></jsp:include>


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

	<script src="../resource/js/main.js"></script>
	
	<script>
		$(function() {
			$(".News").addClass("active");
		});
	</script>
	
	<!-- 뉴스 조회 -->
	<script>
	function fnSearchNews(team){
		var team = $(team).attr('name');
		$("#team_name").text(team);
		$.ajax({
			url : "GetTeamNews.do",
			type : "post",
			data : {"team": team},
			dataType : "json",
			success : function(data) {
				$("#news").children().remove();
				var resHTML = "";
				$.each(data, function(idx, val) {
					var contents = val.ko_contents[0].substring(0,40)+"...";
					resHTML += "<div class='col-md-6 col-lg-4 '>";
					resHTML += "<div class='post-entry cursor-pointer'>";
					resHTML += "<div class='image'>";
					resHTML += "<img src='"+val.img+"' alt='Image' class='img-fluid'>";
					resHTML += "</div>";
					resHTML += "<div class='text p-4'>";
					resHTML += "<h2 class='h5 text-black'>"+val.ko_title+"</h2>";
					resHTML += "<span class='text-uppercase date d-block mb-3'><small>"+val.date+"</small></span>";
					resHTML += "<p class='mb-0'>"+contents+"</p>";
					resHTML += "</div></div></div>";
				});
				$("#news").append(resHTML);
			},beforeSend:function(){
		        $('#loading_layer').css("display", "flex");
		    },complete:function(){
		        $('#loading_layer').css("display", "none");
		    }

		});
	}
	</script>


</body>
</html>