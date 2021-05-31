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
	
	String selected_team_logo = (String) request.getAttribute("selected_team_logo");
	
	if(selected_team_logo != null){
		team_logo = selected_team_logo;
	}
	
	if(team_logo==null){
		team_logo="../resource/images/EPL_Patch";
	}
%>

<style>
.list_button {
	border: 0px;
	outline: 0px;
	background-color: white;
	width: 250px;
	text-align: left;
	cursor: pointer;
}
</style>

<div class="site-mobile-menu">
	<div class="site-mobile-menu-header">
		<div class="site-mobile-menu-logo">
			<a href="/home.do"><img src="<%=team_logo%>" alt="Image"></a>
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
						<a href="#" class="text-secondary px-2 pl-0"> <span
							id="favorite_team"> <%=favorite_team%>
						</span>
						</a>
					</div>
				</div>
				<div class="col-6 col-md-9 text-right">
					<div class="d-inline-block">
						<a href="#" class="text-secondary p-2 d-flex align-items-center">
							<span class="mr-3"><i class="fas fa-coins"></i></span> <span
							class="d-none d-md-block"><%=member_point%> Point</span>
						</a>
					</div>
					<div class="d-inline-block">
						<a href="#" class="text-secondary p-2 d-flex align-items-center">
							<span class="mr-3"><i class="fas fa-user"></i></span> <span
							class="d-none d-md-block"><%=member_name%> </span>
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
				<a href="/home.do"><img src="<%=team_logo%>" alt="" style="height: 100px"></a>
			</div>

			<div class="d-inline-block d-md-none ml-md-0 mr-auto py-3">
				<a href="#" class="site-menu-toggle js-menu-toggle text-white"><span
					class="icon-menu h3"></span></a>
			</div>

			<ul class="site-menu js-clone-nav d-none d-md-block">
				<li class="home"><a href="/home.do">Home</a></li>
				<li><a href="/teams.do" class="News">News</a></li>
				<li class="has-children teams"><a href="javascript:fnMove();">Teams</a>
					<ul class="dropdown arrow-top">
						<%
						Iterator<EPLDTO> teams = mList.iterator();
						while (teams.hasNext()) {
							EPLDTO team = teams.next();
						%>
						<li>
							<form id="<%=team.getTeam_id()%>" action="/team.do" method="post">
								<input type="hidden" name="team_name"
									value="<%=team.getTeam_name()%>"> <input type="hidden"
									name="team_logo" value="<%=team.getLogo()%>">
								<button class="mb-2 mt-1 list_button" type="submit">
									<img src="<%=team.getLogo()%>" style="width: 25px">
									<%=team.getKo_name()%>
								</button>
							</form>
						</li>
						<%
						}
						%>
					</ul></li>
				<li><a href="about.html" class="myINfo">myInfo</a></li>
				<li><a href="/Logout.do">LogOut</a></li>
			</ul>
		</div>
	</nav>
</header>

<!-- teams를 클릭했을경우 순위표로 이동  -->
<script>
function fnMove() {
	var offset = $("#rank_table").offset();
	if(offset==null){
		console.log("홈에서만 작동합니다.")
	}else{
		$('html, body').animate({scrollTop : offset.top}, 400);
	}
}
</script>

