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
<style>
.overflow {
	overflow: hidden;
	height: 100px;
}
</style>
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
	
	<!-- 뉴스 모달 -->
	<div class="modal fade modal-xl" id="news-modal" role="dialog" aria-labelledby="gridSystemModalLabel" aria-hidden="true" style="z-index:2000;">
    	<div class="modal-dialog modal-lg">
      		<div class="modal-content" style="font-family: 'Stylish', sans-serif;">
        		<div class="modal-header">
        			<h4 class="modal-title" id="myModalLabel"></h4>
          			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				</div>
        		<div class="mb-3" style="width:100%">
        		<img class="mb-3"  id="img" src="" style="width:100%">
        		</div>
        		<div class="modal-body p-4">
          		</div>
		        <div class="modal-footer">
		          <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
		        </div>
	    	</div>
    	</div>
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
					resHTML += "<div class='col-md-6 col-lg-4 mb-5'>";
					resHTML += "<div class='post-entry cursor-pointer' onclick=fnOpenNews(this)>";
					resHTML += "<div class='image'>";
					resHTML += "<img src='"+val.img+"' alt='Image' class='img-fluid'>";
					resHTML += "</div>";
					resHTML += "<div class='text title pl-4 pr-4 pt-4'>";
					resHTML += "<h2 class='h5 text-black'>"+val.ko_title+"</h2>";
					resHTML += "</div>";
					resHTML += "<div class='text body pl-4 pr-4 pb-4 overflow'>";
					resHTML += "<span class='text-uppercase date d-block mb-3'><small>"+val.date+"</small></span>";
					for(var i in val.ko_contents){
						if(i==0){
							resHTML += "<p class='mb-0 pb-4'>"+val.ko_contents[i]+"</p>";
						}else{
							resHTML += "<p class='mb-0 pb-4'>"+val.ko_contents[i]+"</p>";
						}
					}
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
	<script>
	function fnOpenNews(news){
		console.log(news);
		var img = $(news).children('div.image').children('img').attr("src");
		var title = $(news).children('div.title').children('h2')[0].innerHTML;
		var contents = $(news).children('div.body').html();
				
		
		$('.modal-title').html(title);
		$('#img').attr("src", img);
		$('.modal-body').html(contents);
		
		
		$('#news-modal').modal('show');
	}
	</script>

</body>
</html>