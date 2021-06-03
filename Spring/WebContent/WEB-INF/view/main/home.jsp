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
	
	//뉴스 데이터
	List<Map<String, Object>> rList = (List<Map<String, Object>>) request.getAttribute("mainNews");
	
%>

<!DOCTYPE html>
<html lang="en">
<head>
<link rel="shortcut icon" href="../resource/images/icons/favicon.ico" type="image/vnd.microsoft.icon">
<title>Home</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- 로딩 창 -->
<script src="../resource/js/loading.js"></script>

<!-- news 글씨체 -->
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Stylish&display=swap" rel="stylesheet">


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

		<!-- 뉴스 슬라이드 -->	
		<div class="slide-one-item home-slider owl-carousel owl-loaded owl-drag" id="slide">
			<%
			Iterator<Map<String, Object>> it = rList.iterator();
			int i=0;
			while (it.hasNext()) {
				Map<String, Object> news = it.next(); %>
			<div class='site-blocks-cover overlay' style="background-image: url(<%=news.get("img")%>);" data-aos='fade' data-stellar-background-ratio='0.5'>
				<div class='container'>
					<div class='row align-items-center justify-content-start'>
						<div class='col-md-6 text-center text-md-left' data-aos='fade-up'
							data-aos-delay='400'>
							<h1 class='bg-text-line'><%=news.get("ko_title")%></h1>
							<p>
								<a class='btn btn-primary btn-sm rounded-0 py-3 px-5' href="javascript:fnOpenNews('<%=i%>');">Read More</a>
							</p>
						</div>
					</div>
				</div>
			</div>
			
			<%
			i++;
			news = null;
			}
			%>
		</div>
		
		
		<!-- 뉴스 슬라이드 상세 -->
		<div class="site-section pt-0 feature-blocks-1" data-aos="fade"
			data-aos-delay="100">
			<div class="container">
				<div class="row">
					<%
					it = rList.iterator();
					i=0;
					while (it.hasNext()) {
						Map<String, Object> news = it.next();
					%>
					<div class="col-md-6 col-lg-4">
						<div class="p-3 p-md-5 feature-block-1 mb-5 mb-lg-0 bg"
							style="background-image: url('<%=news.get("img")%>');">
							<div class="text" ><%=news.get("ko_title")%></h2>
								<p class="mb-0" >
									<a class="btn btn-primary btn-sm px-4 py-2 rounded-0" href="javascript:fnOpenNews('<%=i%>');">Read More</a>
								</p>
							</div>
						</div>
					</div>
					
					<%
					i++;
					news=null;
					}
					%>

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
		
		
		<!-- 리그 순위 테이블 -->
		<div class="site-section block-13 bg-primary fixed overlay-primary bg-image"
			style="background-image: url('../resource/images/EPL_Table.jpg'); background-position:center;background-origin: content-box;">
			<div  class="container">
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
						<h6 class="center_align mr-2" style="color: mediumseagreen; display: inline-block"">
							<img src="../resource/images/europa.png" class="table_icon"
								style="-webkit-filter: opacity(.5) drop-shadow(0 0 0 lightgreen); filter: opacity(.5) drop-shadow(0 0 0 lightgreen);">유로파
						</h6>
						<h6 class="center_align" style="color: gray; display: inline-block"">
							<i class="fas fa-chevron-circle-down" class="table_icon"></i>강등
						</h6>
					</div>
					<div class="col-4 text-right">
						
					</div>
				</div>
				<div class="row" id="rank_table">
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
						Iterator<EPLDTO> teams = mList.iterator();
						while (teams.hasNext()) {
							EPLDTO team = teams.next();
							if(team.getRecent_rank()<=4){
							%>
							<div class="table-row champions"  onclick=goTeam(this)>
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
								<div class="hidden" name="<%=team.getTeam_id()%>"><%=team.getTeam_id()%></div>
							</div>
							<%
							team=null;
							} 
							%>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<footer class="site-footer border-top">
			
		</footer>
	</div>
	
	<!-- Newspaper Modal -->
	<%
	it = rList.iterator();
	i=0;
	while (it.hasNext()) {
		Map<String, Object> news = it.next();
		List<String> ko_contents = (List<String>) news.get("ko_contents");
	%>
	<div class="modal fade modal-xl" role="dialog" aria-labelledby="gridSystemModalLabel" aria-hidden="true" id="<%=i%>" style="z-index:2000;">
    	<div class="modal-dialog modal-lg">
      		<div class="modal-content" style="font-family: 'Stylish', sans-serif;">
        		<div class="modal-header">
        			<h4 class="modal-title" id="myModalLabel"><%=news.get("ko_title") %></h4>
          			<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				</div>
        		<div class="modal-body">
        		<img class="mb-3" src="<%=news.get("img")%>" style="width:100%">
				<% 
				Iterator<String> ko_content = ko_contents.iterator();
				while(ko_content.hasNext()){
				%>
				<p><%=ko_content.next().toString()%></p>
				<br>
				<%		
					}
				%>
          		</div>
		        <div class="modal-footer">
		          <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
		        </div>
	    	</div>
    	</div>
   	</div>
	    <%
	    news=null;
        i++;
		}
        %>
	
	<!-- 채팅 박스 페이지 모듈화 -->
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
	
	<!-- 뉴스 modal 열기 -->
	<script>
	function fnOpenNews(no){
		console.log(no);
	    $('#'+no).modal('show')  
	}
	</script>
	
	<!-- 순위 테이블 관련 javascript -->
	<script>
	//자기 팀 강조
	$(function() {
		var elem = $(".hidden[name='<%=favorite_team%>']");
		
		elem.parent().addClass('myTeam');
	})
	
	//팀페이지 이동
	function goTeam(team){
		let team_id = $(team).children("div.hidden")[1].innerHTML;
		console.log(team_id);	
		
		$("."+team_id)[0].submit();
	}
	
	</script>
	
	
	<!-- 유튜브 영상 조회 -->
	<script>
	$(function(){
		$.ajax({
			url : "GetTeamHilights.do",
			type : "post",
			data : {"team" :  "<%=favorite_team%>"},
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
	</script>

	<script src="../resource/js/main.js"></script>
	<script>
	$(function(){
		$(".home").addClass("active");
	});
	</script>
	
	<script>
		function fnOpenYoutube(url){
			var url = $(url).attr('name');
			console.log(url);
			
			window.open(url, "_blank")
		}
		</script>
	
</body>
</html>