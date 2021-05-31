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
<title>EPL teams</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

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


		<!-- 일단 대기 -->
		<div class="site-blocks-vs site-section bg-light">
			<div class="container">
				<i class="flag flag-germany"></i>
				<i class="germany flag"></i>
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
			$(".teams").addClass("active");
		});
	</script>


</body>
</html>