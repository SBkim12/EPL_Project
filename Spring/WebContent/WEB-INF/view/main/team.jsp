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
	
	String selected_team = (String) request.getAttribute("selected_team");
 	
%>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="shortcut icon" href="../resource/images/icons/favicon.ico" type="image/vnd.microsoft.icon">
<title><%=selected_team %></title>
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
<style>
#BEST{
    background: center;
    border-radius: 5rem;
    color: white;
    background-image: url("https://pbs.twimg.com/media/C1KWvW4XAAAXRrI.jpg:large");
    text-shadow: 2px 5px 2px black;
}
.overflow_hide{
	overflow:hidden;
}
</style>
</head>
<body>
	
	
	<div class="site-wrap">
		<!-- 내비 바 -->
		<jsp:include page="../navbar.jsp"></jsp:include>


		<div class="site-section">
			<div class="container" id="players">
			
				<div class="row mb-5" id="BEST">
					<div class='col-md-12 text-center mb-1'>
						<h2 class='' style="color:gold; ">TEAM BEST</h2>
					</div>
				</div>
				
				<div class='row'>
					<div class='col-md-12 text-center mb-5'>
						<h2 class='text-gold'>GOALKEEPER</h2>
					</div>
				</div>
				<div class="row" id="GOALKEEPER">
				
				</div>
				
				<div class='row'>
					<div class='col-md-12 text-center mb-5'>
						<h2 class='text-black'>DEFENDER</h2>
					</div>
				</div>
				<div class="row" id="DEFENDER">
				
				</div>
				
				<div class='row'>
					<div class='col-md-12 text-center mb-5'>
						<h2 class='text-black'>MIDFIELDER</h2>
					</div>
				</div>
				<div class="row" id="MIDFIELDER">
				
				</div>
				
				<div class='row'>
					<div class='col-md-12 text-center mb-5'>
						<h2 class='text-black'>FORWARD</h2>
					</div>
				</div>
				<div class="row" id="FORWARD">
				
				</div>
				
				
			</div>
		</div>
		
		<!-- 팀 하이라이트 영상 -->
		<div class="site-blocks-vs site-section bg-light">
			<div class="container" >
				<div class="row mb-5">
					<div class="col-md-12 text-center">
						<h2 class="text-black">Latest Hilights</h2>
					</div>
				</div>
				<!-- 영상이 들어가는 곳 -->
				<div class="row" id="Team_Hilight">
					
				</div>
			</div>
		</div>

		<footer class="site-footer border-top"> 
		</footer>
	</div>
	
	<!-- player_modal -->
	<div class="modal fade modal-xl" role="dialog" aria-labelledby="gridSystemModalLabel" aria-hidden="true" id="player_modal" style="z-index:2000;">
    	<div class="modal-dialog modal-lg">
    		<div class="modal-header">
          			<button type="button" class="close cursor-pointer" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
			</div>
      		<div class="modal-content" style="font-family: 'Stylish', sans-serif;">
        		<iframe id="iframe" name="iframe" src="" 
        		style="width:100%; height:2000px;"  frameborder=0 framespacing=0	marginheight=0 marginwidth=0 vspace=0 scrolling=no></iframe>
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
	
	<!-- 선수 목록 생성 -->
	<script>
	var team = "<%=selected_team%>"; 
	
	$(function(){
		$.ajax({
			url : "getEPLteamPlayer.do",
			type : "post",
			data : {"team" : team},
			dataType : "json",
			success : function(data) {
	
				
				var FORWARD = "";
				var MIDFIELDER ="";
				var DEFENDER ="";
				var GOALKEEPER ="";
				
				//키퍼왕
				var bestGOALKEEPER_img = "";
				var bestGOALKEEPER_name = "";
				var bestGOALKEEPER_clean = 0;
				var bestGOALKEEPER_url = "";
				//수비왕
				var bestDEFENDER_img ="";
				var bestDEFENDER_name ="";
				var bestDEFENDER_clean =0;
				var bestDEFENDER_url = "";
				//어시왕                  
				var bestASSIST_img ="";
				var bestASSIST_name ="";
				var best_assist =0;
				var bestASSIST_url = "";
				//득점왕                  
				var bestSCORER_img = "";
				var bestSCORER_name ="";
				var best_goal =0;
				var bestSCORER_url = "";
				
				var resHTML = "";
				$.each(data, function(idx, val){
					resHTML += "<div class='mb-4 mb-lg-0 col-6 col-md-4 col-lg-3 text-center btn' onclick='fnOpenInfo(this);'>"
					resHTML += "<div class='player mb-3'>";
					resHTML += "<span class='team-number'>"+val.player_no+"</span>"
					resHTML += "<img src='"+val.player_img+"' onerror='this.src=\"../resource/images/Photo-Missing.png\"' alt='Image' class='img-fluid image rounded-circle'>"
					resHTML += "<h5 class='overflow_hide'>"+val.player_name+"</h5>"
					resHTML += "<span class='position'>"+val.player_position+"</span>"
					resHTML += "<span class='hidden'>"+val.player_url+"</span>"
					resHTML += "</div>"
					resHTML += "</div>"
					
					if(val.player_position == "Forward"){
						if(Number(val.Goals) > best_goal){
							bestSCORER_img = val.player_img;
							bestSCORER_name = val.player_name;
							bestSCORER_url = val.player_url;
							best_goal = Number(val.Goals);
						}
						if( Number(val.Assists) > best_assist){
							bestASSIST_img = val.player_img;
							bestASSIST_name = val.player_name;
							bestASSIST_url = val.player_url;
							best_assist = Number(val.Assists);
						}
						FORWARD += resHTML;
					
					}else if(val.player_position == "Midfielder"){
						if(Number(val.Goals) > best_goal){
							bestSCORER_img = val.player_img;
							bestSCORER_name = val.player_name;
							bestSCORER_url = val.player_url;
							best_goal = Number(val.Goals);
						}
						if( Number(val.Assists) > best_assist){
							bestASSIST_img = val.player_img;
							bestASSIST_name = val.player_name;
							bestASSIST_url = val.player_url;
							best_assist = Number(val.Assists);
						}
						MIDFIELDER += resHTML;
						
					}else if(val.player_position == "Defender"){
						if( Number(val.Clean_sheets) > bestDEFENDER_clean ){
							bestDEFENDER_img = val.player_img;
							bestDEFENDER_name = val.player_name;
							bestDEFENDER_url = val.player_url;
							bestDEFENDER_clean = Number(val.Clean_sheets);
						}
						DEFENDER += resHTML;
						
					}else if(val.player_position == "Goalkeeper"){
						if(Number(val.Clean_sheets) > bestGOALKEEPER_clean){
							bestGOALKEEPER_img = val.player_img;
							bestGOALKEEPER_name = val.player_name;
							bestGOALKEEPER_url = val.player_url;
							bestGOALKEEPER_clean = Number(val.Clean_sheets);
						}
						GOALKEEPER += resHTML;
						
					}
					resHTML="";
				});
				
				$("#GOALKEEPER").append(GOALKEEPER);
				$("#DEFENDER").append(DEFENDER);
				$("#MIDFIELDER").append(MIDFIELDER);
				$("#FORWARD").append(FORWARD);
				
				//팀 득점왕
				resHTML += "<div class='mb-2 mb-lg-0 col-6 col-md-4 col-lg-3 text-center btn' onclick='fnOpenInfo(this);' style='border-radius: 5rem;'>"
				resHTML += "<h3>SCORE</h3>";
				resHTML += "<div class='player mb-3'>";
				resHTML += "<img src='"+bestSCORER_img+"' onerror='this.src=\"../resource/images/Photo-Missing.png\"' alt='Image' class='img-fluid image rounded-circle'>"
				resHTML += "<h5 class='overflow_hide'>"+bestSCORER_name+"</h5>"
				resHTML += "<span class='position'>득점 :: "+best_goal+"</span>"
				resHTML += "<span class='hidden'>"+bestSCORER_url+"</span>"
				resHTML += "</div></div>"
			
				//팀 어시왕
				resHTML += "<div class='mb-2 mb-lg-0 col-6 col-md-4 col-lg-3 text-center btn' onclick='fnOpenInfo(this);' style='border-radius: 5rem;'>"
				resHTML += "<h3>ASSIST</h3>";
				resHTML += "<div class='player mb-3'>";
				resHTML += "<img src='"+bestASSIST_img+"' onerror='this.src=\"../resource/images/Photo-Missing.png\"' alt='Image' class='img-fluid image rounded-circle'>"
				resHTML += "<h5 style='overflow=hidden'>"+bestASSIST_name+"</h5>"
				resHTML += "<span class='position'>어시스트 :: "+best_assist+"</span>"
				resHTML += "<span class='hidden'>"+bestASSIST_url+"</span>"
				resHTML += "</div></div>"
				
				//팀 수비왕
				resHTML += "<div class='mb-2 mb-lg-0 col-6 col-md-4 col-lg-3 text-center btn' onclick='fnOpenInfo(this);' style='border-radius: 5rem;'>"
				resHTML += "<h3>DEFENSE</h3>";
				resHTML += "<div class='player mb-3'>";
				resHTML += "<img src='"+bestDEFENDER_img+"' onerror='this.src=\"../resource/images/Photo-Missing.png\"' alt='Image' class='img-fluid image rounded-circle'>"
				resHTML += "<h5 class='overflow_hide'>"+bestDEFENDER_name+"</h5>"
				resHTML += "<span class='position'>클린시트 :: "+bestDEFENDER_clean+"</span><br>"
				resHTML += "<span class='hidden'>"+bestDEFENDER_url+"</span>"
				resHTML += "</div></div>"
				
				//팀 베스트 키퍼
				resHTML += "<div class='mb-2 mb-lg-0 col-6 col-md-4 col-lg-3 text-center btn' onclick='fnOpenInfo(this);' style='border-radius: 5rem;'>"
				resHTML += "<h5>GOALKEEPER</h5>";
				resHTML += "<div class='player mb-3'>";
				resHTML += "<img src='"+bestGOALKEEPER_img+"' onerror='this.src=\"../resource/images/Photo-Missing.png\"' alt='Image' class='img-fluid image rounded-circle'>"
				resHTML += "<h5 class='overflow_hide'>"+bestGOALKEEPER_name+"</h5>"
				resHTML += "<span class='position'>클린시트 :: "+bestGOALKEEPER_clean+"</span>"
				resHTML += "<span class='hidden'>"+bestGOALKEEPER_url+"</span>"
				resHTML += "</div></div>"
				
				$("#BEST").append(resHTML);
			}	
		});
	});
	</script>
	
		<script>
			$(function() {
				$(".teams").addClass("active");
			});
	</script>
	<script src="../resource/js/main.js"></script>
	<!-- 유튜브 영상 조회 -->
	<script>
	$(function(){
		$.ajax({
			url : "GetTeamHilights.do",
			type : "post",
			data : {"team" :  "<%=selected_team%>"},
			dataType : "json",
			success : function(data) {
				var resHTML = "";
				$.each(data, function(idx, val) {
					
					var video_url = "https://www.youtube.com/watch?v="+val.video_id;
					
					resHTML += "<div class='col-md-6 col-lg-4 '>";
					resHTML += "<div class='post-entry cursor-pointer'>";
					resHTML += "<div class='block-16'>";
					resHTML += "<figure name='"+video_url+"' onclick='fnOpenYoutube(this);'>";
					resHTML += "<img src='"+val.video_thumbnails+"' alt='Image Placeholder' class='img-fluid rounded w-100'>";
					resHTML += "<span class='play-button'>";
					resHTML += "<span class='icon-play'></span>";
					resHTML += "</span></figure></div>";
					resHTML += "<div class='text-center p-1 mb-1'>"+val.video_title+"</div>";
					resHTML += "</div></div>";
				});
				$("#Team_Hilight").append(resHTML);
				
			}

		});
	});
	
	function fnOpenYoutube(url){
		var url = $(url).attr('name');
		console.log(url);
		
		window.open(url, "_blank")
	}
	</script>
	
	<script>
	function fnOpenInfo(player){
		
		var url = $(player).children('div.player').children('span.hidden')[0].innerHTML;
		console.log(url);
		
		$('#iframe').attr("src",url);
		
		var popup = $('#iframe').contents().find('body > section');
		
		console.log(popup);
		
		$('#player_modal').modal('show')  
	}
	</script>
</body>
</html>