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
<link rel="stylesheet" href="../resource/css/check_button.css">

<link rel="stylesheet" href="../resource/css/aos.css">

<link rel="stylesheet" href="../resource/css/style.css">

<script src="https://kit.fontawesome.com/84865ac036.js"
	crossorigin="anonymous"></script>
<style>
.bg_img{
	background-image: url('../resource/images/soccer.jpg');
}

.chek{
}
</style>
</head>
<body>


	<div class="site-wrap">
		<!-- 내비 바 -->
		<jsp:include page="../navbar.jsp"></jsp:include>

		<!-- 일단 대기 -->
		<div class="site-blocks-vs site-section bg-light">
			<div class="container" id="matches">
			
				
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
			$(".Predict").addClass("active");
		});
	</script>
	<script>
		$(function allPredict(){
			$.ajax({
				url : "GetPredictData.do",
				type : "post",
				data : {"member_id" :  "<%=member_id%>"},
				dataType : "json",
				success : function(data) {
					var resHTML = "";
					$.each(data, function(idx, val) {
						resHTML += "<div class='bg-image overlay-success rounded mb-5 bg_img'  data-stellar-background-ratio='0.5' id='"+val.match_id+"'>";
						resHTML += "<div class='row align-items-center'>";
						resHTML += "<div class='col-md-12 col-lg-4 mb-4 mb-lg-0'>";
						resHTML += "<div class='text-center text-lg-left'>";
						resHTML += "<div class='d-block d-lg-flex align-items-center'>";
						resHTML += "<div class='image mx-auto mb-3 mb-lg-0 mr-lg-3'>";
						resHTML += "<img src='"+val.home_logo+"' alt='Image' class='img-fluid'>";
						resHTML += "</div>";
						resHTML += "<div class='text'>";
						resHTML += "<h3 class='h5 mb-0 text-black'>"+val.home+"</h3>";
						resHTML += "<span class='text-uppercase small country text-black'></span>";
						resHTML += " </div> </div> </div>  </div>";
						resHTML += "<div class='col-md-12 col-lg-4 text-center mb-4 mb-lg-0'>";
						resHTML += "<div class='d-inline-block'>";
						resHTML += "<p class='mb-2'><small class='text-uppercase text-black font-weight-bold'>Premier League - "+val.round+" ROUND</small></p>";
						resHTML += "<a class='social-container check' match_id='"+val.match_id+"'href='javascript:;' onclick='fnPredictCheck(this);'><div class='social-cube'><div class='front'><span class='home_score'>"+val.home_score+"</span><span>  ::  </span><span class='away_score'>"+val.away_score+"</span></div><div class='bottom'>CHECK</div></div></a>";
						/* resHTML += "<div class='bg-black py-2 px-4 mb-2 text-white d-inline-block rounded check'  data-hover='CHECK'></div>"; */
						resHTML += "<p class='mb-0'><small class='text-uppercase text-black font-weight-bold'>my Predict</small></p>";
						resHTML += "</div> </div>";
						resHTML += " <div class='col-md-12 col-lg-4 text-center text-lg-right'>";
						resHTML += "<div class=''>";
						resHTML += " <div class='d-block d-lg-flex align-items-center'>";
						resHTML += "<div class='image mx-auto ml-lg-3 mb-3 mb-lg-0 order-2'>";
						resHTML += "<img src='"+val.away_logo+"' alt='Image' class='img-fluid'>";
						resHTML += "</div>";
						resHTML += "<div class='text order-1'>";
						resHTML += "<h3 class='h5 mb-0 text-black'>"+val.away+"</h3>";
						resHTML += "<span class='text-uppercase small country text-black'></span>";
						resHTML += "</div></div></div></div></div></div>";
					});
					$("#matches").html(resHTML);
				}
			})
		});
	</script>
	
	
	<script>
	function fnPredictCheck(predict){
		console.log(predict);
		var match_id = $(predict).attr("match_id");
		var home_score = $(predict).children('div.social-cube').children('div.front').children('span.home_score')[0].innerHTML;
		var away_score = $(predict).children('div.social-cube').children('div.front').children('span.away_score')[0].innerHTML;
		
		$.ajax({
			url : "PredictCheck.do",
			type : "post",
			data : {"match_id" :  match_id,
					"home_score" : home_score,
					"away_score" : away_score,
					},
			dataType : "json",
			success : function(data) {
				let res = data.res;
				let point = data.point;
				
				if(res==3){
					Swal.fire({
						icon : 'success',
						title : point+'점 획득 했습니다.',
						showConfirmButton : false,
						timer : 1000
					})
				}else if(res==2){
					Swal.fire({
						icon : 'error',
						title : 'Oops...',
						text : '데이터 업데이트 오류 발생!',
					});
				}else{
					Swal.fire({
						icon : 'error',
						title : 'Oops...',
						text : '오류 발생!',
					});
				}
				
			}
		})
		var parent = document.getElementById(match_id).remove();
	};
	</script>
                  
                    
                    

</body>
</html>
