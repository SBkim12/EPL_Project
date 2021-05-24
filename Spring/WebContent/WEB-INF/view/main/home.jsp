<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="poly.dto.EPLDTO"%>
<%
	String member_id = (String) session.getAttribute("member_id");
	String member_name = (String) session.getAttribute("member_name");
	String favorite_team = (String) session.getAttribute("favorite_team");
	String member_point = (String) session.getAttribute("member_point");
	List<Map<String, Object>> rList = (List<Map<String, Object>>) request.getAttribute("mainNews");
	List<EPLDTO> mList = (List<EPLDTO>) request.getAttribute("teams");
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
<!-- JQUERY 최신 버전-->
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<link rel="icon" href="../resource/images/icons/favicon.ico"/>
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
						<div class="col-6 col-md-3 text-center">
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
						<a href="index.html"><img src="../resource/images/EPL_Patch.png"
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
								<a href='#' class='btn btn-primary btn-sm rounded-0 py-3 px-5'>Read
									More</a>
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
					int i=0;
					while (it.hasNext() && i<3) {
						Map<String, Object> news = it.next();
						List<String> ko_contents = (List<String>)news.get("ko_contents");
					%>
					<div class="col-md-6 col-lg-4">
						<div class="p-3 p-md-5 feature-block-1 mb-5 mb-lg-0 bg"
							style="background-image: url('<%=news.get("img")%>');">
							<div class="text" ><%=news.get("ko_title")%></h2>
								<p class="mb-0" >
									<a href="#" class="btn btn-primary btn-sm px-4 py-2 rounded-0">Read
										More</a>
								</p>
							</div>
						</div>
					</div>
					<%
					i++;
					}
					%>

				</div>
			</div>
		</div>

		<div class="site-blocks-vs site-section bg-light">
			<div class="container">

				<div
					class="border mb-3 rounded d-block d-lg-flex align-items-center p-3 next-match">

					<div
						class="mr-auto order-md-1 w-60 text-center text-lg-left mb-3 mb-lg-0">
						Next match
						<div id="date-countdown"></div>
					</div>

					<div class="ml-auto pr-4 order-md-2">
						<div class="h5 text-black text-uppercase text-center text-lg-left">
							<div class="d-block d-md-inline-block mb-3 mb-lg-0">
								<img src="../resource/images/img_1_sq.jpg" alt="Image"
									class="mr-3 image"><span
									class="d-block d-md-inline-block ml-0 ml-md-3 ml-lg-0">Sea
									Hawlks </span>
							</div>
							<span
								class="text-muted mx-3 text-normal mb-3 mb-lg-0 d-block d-md-inline ">vs</span>
							<div class="d-block d-md-inline-block">
								<img src="../resource/images/img_3_sq.jpg" alt="Image"
									class="mr-3 image"><span
									class="d-block d-md-inline-block ml-0 ml-md-3 ml-lg-0">Patriots</span>
							</div>
						</div>
					</div>

				</div>

				<div class="bg-image overlay-success rounded mb-5"
					style="background-image: url('../resource/images/hero_bg_1.jpg');"
					data-stellar-background-ratio="0.5">

					<div class="row align-items-center">
						<div class="col-md-12 col-lg-4 mb-4 mb-lg-0">

							<div class="text-center text-lg-left">
								<div class="d-block d-lg-flex align-items-center">
									<div class="image mx-auto mb-3 mb-lg-0 mr-lg-3">
										<img src="../resource/images/img_1_sq.jpg" alt="Image"
											class="img-fluid">
									</div>
									<div class="text">
										<h3 class="h5 mb-0 text-black">Sea Hawks</h3>
										<span class="text-uppercase small country text-black">Brazil</span>
									</div>
								</div>
							</div>

						</div>
						<div class="col-md-12 col-lg-4 text-center mb-4 mb-lg-0">
							<div class="d-inline-block">
								<p class="mb-2">
									<small class="text-uppercase text-black font-weight-bold">Premier
										League &mdash; Round 10</small>
								</p>
								<div
									class="bg-black py-2 px-4 mb-2 text-white d-inline-block rounded">
									<span class="h3">3:2</span>
								</div>
								<p class="mb-0">
									<small class="text-uppercase text-black font-weight-bold">10
										September / 7:30 AM</small>
								</p>
							</div>
						</div>

						<div class="col-md-12 col-lg-4 text-center text-lg-right">
							<div class="">
								<div class="d-block d-lg-flex align-items-center">
									<div class="image mx-auto ml-lg-3 mb-3 mb-lg-0 order-2">
										<img src="../resource/images/img_4_sq.jpg" alt="Image"
											class="img-fluid">
									</div>
									<div class="text order-1">
										<h3 class="h5 mb-0 text-black">Steelers</h3>
										<span class="text-uppercase small country text-black">London</span>
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>

				<div class="row">
					<div class="col-md-12">

						<h2 class="h6 text-uppercase text-black font-weight-bold mb-3">Latest
							Matches</h2>
						<div class="site-block-tab">
							<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
								<li class="nav-item"><a class="nav-link active"
									id="pills-home-tab" data-toggle="pill" href="#pills-home"
									role="tab" aria-controls="pills-home" aria-selected="true">Match
										1</a></li>
								<li class="nav-item"><a class="nav-link"
									id="pills-profile-tab" data-toggle="pill" href="#pills-profile"
									role="tab" aria-controls="pills-profile" aria-selected="false">Match
										2</a></li>
								<li class="nav-item"><a class="nav-link"
									id="pills-contact-tab" data-toggle="pill" href="#pills-contact"
									role="tab" aria-controls="pills-contact" aria-selected="false">Match
										3</a></li>
							</ul>
							<div class="tab-content" id="pills-tabContent">
								<div class="tab-pane fade show active" id="pills-home"
									role="tabpanel" aria-labelledby="pills-home-tab">

									<div class="row align-items-center">
										<div class="col-md-12">


											<div
												class="row bg-white align-items-center ml-0 mr-0 py-4 match-entry">
												<div class="col-md-4 col-lg-4 mb-4 mb-lg-0">

													<div class="text-center text-lg-left">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small text-center mb-3 mb-lg-0 mr-lg-3">
																<img src="../resource/images/img_1_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text">
																<h3 class="h5 mb-0 text-black">Packers</h3>
																<span class="text-uppercase small country">Brazil</span>
															</div>
														</div>
													</div>

												</div>
												<div class="col-md-4 col-lg-4 text-center mb-4 mb-lg-0">
													<div class="d-inline-block">
														<div
															class="bg-black py-2 px-4 mb-2 text-white d-inline-block rounded">
															<span class="h5">3:2</span>
														</div>
													</div>
												</div>

												<div class="col-md-4 col-lg-4 text-center text-lg-right">
													<div class="">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small ml-lg-3 mb-3 mb-lg-0 order-2">
																<img src="../resource/images/img_4_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text order-1 w-100">
																<h3 class="h5 mb-0 text-black">Steelers</h3>
																<span class="text-uppercase small country">London</span>
															</div>
														</div>
													</div>
												</div>
											</div>

											<!-- END row -->

											<div
												class="row bg-white align-items-center ml-0 mr-0 py-4 match-entry">
												<div class="col-md-4 col-lg-4 mb-4 mb-lg-0">

													<div class="text-center text-lg-left">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small text-center mb-3 mb-lg-0 mr-lg-3">
																<img src="../resource/images/img_1_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text">
																<h3 class="h5 mb-0 text-black">Patriots</h3>
																<span class="text-uppercase small country">Brazil</span>
															</div>
														</div>
													</div>

												</div>
												<div class="col-md-4 col-lg-4 text-center mb-4 mb-lg-0">
													<div class="d-inline-block">
														<div
															class="bg-black py-2 px-4 mb-2 text-white d-inline-block rounded">
															<span class="h5">3:2</span>
														</div>
													</div>
												</div>

												<div class="col-md-4 col-lg-4 text-center text-lg-right">
													<div class="">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small ml-lg-3 mb-3 mb-lg-0 order-2">
																<img src="../resource/images/img_4_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text order-1 w-100">
																<h3 class="h5 mb-0 text-black">Cowboys</h3>
																<span class="text-uppercase small country">London</span>
															</div>
														</div>
													</div>
												</div>
											</div>

											<!-- END row -->

											<div
												class="row bg-white align-items-center ml-0 mr-0 py-4 match-entry">
												<div class="col-md-4 col-lg-4 mb-4 mb-lg-0">

													<div class="text-center text-lg-left">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small text-center mb-3 mb-lg-0 mr-lg-3">
																<img src="../resource/images/img_1_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text">
																<h3 class="h5 mb-0 text-black">Raiders</h3>
																<span class="text-uppercase small country">Brazil</span>
															</div>
														</div>
													</div>

												</div>
												<div class="col-md-4 col-lg-4 text-center mb-4 mb-lg-0">
													<div class="d-inline-block">
														<div
															class="bg-black py-2 px-4 mb-2 text-white d-inline-block rounded">
															<span class="h5">3:2</span>
														</div>
													</div>
												</div>

												<div class="col-md-4 col-lg-4 text-center text-lg-right">
													<div class="">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small ml-lg-3 mb-3 mb-lg-0 order-2">
																<img src="../resource/images/img_4_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text order-1 w-100">
																<h3 class="h5 mb-0 text-black">Chiefs</h3>
																<span class="text-uppercase small country">London</span>
															</div>
														</div>
													</div>
												</div>
											</div>

											<!-- END row -->

										</div>

									</div>
								</div>
								<div class="tab-pane fade" id="pills-profile" role="tabpanel"
									aria-labelledby="pills-profile-tab">
									<div class="row align-items-center">
										<div class="col-md-12">


											<div
												class="row bg-white align-items-center ml-0 mr-0 py-4 match-entry">
												<div class="col-md-4 col-lg-4 mb-4 mb-lg-0">

													<div class="text-center text-lg-left">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small text-center mb-3 mb-lg-0 mr-lg-3">
																<img src="../resource/images/img_1_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text">
																<h3 class="h5 mb-0 text-black">Packers</h3>
																<span class="text-uppercase small country">Brazil</span>
															</div>
														</div>
													</div>

												</div>
												<div class="col-md-4 col-lg-4 text-center mb-4 mb-lg-0">
													<div class="d-inline-block">
														<div
															class="bg-black py-2 px-4 mb-2 text-white d-inline-block rounded">
															<span class="h5">3:2</span>
														</div>
													</div>
												</div>

												<div class="col-md-4 col-lg-4 text-center text-lg-right">
													<div class="">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small ml-lg-3 mb-3 mb-lg-0 order-2">
																<img src="../resource/images/img_4_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text order-1 w-100">
																<h3 class="h5 mb-0 text-black">Steelers</h3>
																<span class="text-uppercase small country">London</span>
															</div>
														</div>
													</div>
												</div>
											</div>

											<!-- END row -->

											<div
												class="row bg-white align-items-center ml-0 mr-0 py-4 match-entry">
												<div class="col-md-4 col-lg-4 mb-4 mb-lg-0">

													<div class="text-center text-lg-left">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small text-center mb-3 mb-lg-0 mr-lg-3">
																<img src="../resource/images/img_1_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text">
																<h3 class="h5 mb-0 text-black">Patriots</h3>
																<span class="text-uppercase small country">Brazil</span>
															</div>
														</div>
													</div>

												</div>
												<div class="col-md-4 col-lg-4 text-center mb-4 mb-lg-0">
													<div class="d-inline-block">
														<div
															class="bg-black py-2 px-4 mb-2 text-white d-inline-block rounded">
															<span class="h5">3:2</span>
														</div>
													</div>
												</div>

												<div class="col-md-4 col-lg-4 text-center text-lg-right">
													<div class="">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small ml-lg-3 mb-3 mb-lg-0 order-2">
																<img src="../resource/images/img_4_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text order-1 w-100">
																<h3 class="h5 mb-0 text-black">Cowboys</h3>
																<span class="text-uppercase small country">London</span>
															</div>
														</div>
													</div>
												</div>
											</div>

											<!-- END row -->

											<div
												class="row bg-white align-items-center ml-0 mr-0 py-4 match-entry">
												<div class="col-md-4 col-lg-4 mb-4 mb-lg-0">

													<div class="text-center text-lg-left">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small text-center mb-3 mb-lg-0 mr-lg-3">
																<img src="../resource/images/img_1_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text">
																<h3 class="h5 mb-0 text-black">Raiders</h3>
																<span class="text-uppercase small country">Brazil</span>
															</div>
														</div>
													</div>

												</div>
												<div class="col-md-4 col-lg-4 text-center mb-4 mb-lg-0">
													<div class="d-inline-block">
														<div
															class="bg-black py-2 px-4 mb-2 text-white d-inline-block rounded">
															<span class="h5">3:2</span>
														</div>
													</div>
												</div>

												<div class="col-md-4 col-lg-4 text-center text-lg-right">
													<div class="">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small ml-lg-3 mb-3 mb-lg-0 order-2">
																<img src="../resource/images/img_4_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text order-1 w-100">
																<h3 class="h5 mb-0 text-black">Chiefs</h3>
																<span class="text-uppercase small country">London</span>
															</div>
														</div>
													</div>
												</div>
											</div>

											<!-- END row -->

										</div>

									</div>
								</div>
								<div class="tab-pane fade" id="pills-contact" role="tabpanel"
									aria-labelledby="pills-contact-tab">
									<div class="row align-items-center">
										<div class="col-md-12">


											<div
												class="row bg-white align-items-center ml-0 mr-0 py-4 match-entry">
												<div class="col-md-4 col-lg-4 mb-4 mb-lg-0">

													<div class="text-center text-lg-left">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small text-center mb-3 mb-lg-0 mr-lg-3">
																<img src="../resource/images/img_1_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text">
																<h3 class="h5 mb-0 text-black">Packers</h3>
																<span class="text-uppercase small country">Brazil</span>
															</div>
														</div>
													</div>

												</div>
												<div class="col-md-4 col-lg-4 text-center mb-4 mb-lg-0">
													<div class="d-inline-block">
														<div
															class="bg-black py-2 px-4 mb-2 text-white d-inline-block rounded">
															<span class="h5">3:2</span>
														</div>
													</div>
												</div>

												<div class="col-md-4 col-lg-4 text-center text-lg-right">
													<div class="">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small ml-lg-3 mb-3 mb-lg-0 order-2">
																<img src="../resource/images/img_4_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text order-1 w-100">
																<h3 class="h5 mb-0 text-black">Steelers</h3>
																<span class="text-uppercase small country">London</span>
															</div>
														</div>
													</div>
												</div>
											</div>

											<!-- END row -->

											<div
												class="row bg-white align-items-center ml-0 mr-0 py-4 match-entry">
												<div class="col-md-4 col-lg-4 mb-4 mb-lg-0">

													<div class="text-center text-lg-left">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small text-center mb-3 mb-lg-0 mr-lg-3">
																<img src="../resource/images/img_1_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text">
																<h3 class="h5 mb-0 text-black">Patriots</h3>
																<span class="text-uppercase small country">Brazil</span>
															</div>
														</div>
													</div>

												</div>
												<div class="col-md-4 col-lg-4 text-center mb-4 mb-lg-0">
													<div class="d-inline-block">
														<div
															class="bg-black py-2 px-4 mb-2 text-white d-inline-block rounded">
															<span class="h5">3:2</span>
														</div>
													</div>
												</div>

												<div class="col-md-4 col-lg-4 text-center text-lg-right">
													<div class="">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small ml-lg-3 mb-3 mb-lg-0 order-2">
																<img src="../resource/images/img_4_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text order-1 w-100">
																<h3 class="h5 mb-0 text-black">Cowboys</h3>
																<span class="text-uppercase small country">London</span>
															</div>
														</div>
													</div>
												</div>
											</div>

											<!-- END row -->

											<div
												class="row bg-white align-items-center ml-0 mr-0 py-4 match-entry">
												<div class="col-md-4 col-lg-4 mb-4 mb-lg-0">

													<div class="text-center text-lg-left">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small text-center mb-3 mb-lg-0 mr-lg-3">
																<img src="../resource/images/img_1_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text">
																<h3 class="h5 mb-0 text-black">Raiders</h3>
																<span class="text-uppercase small country">Brazil</span>
															</div>
														</div>
													</div>

												</div>
												<div class="col-md-4 col-lg-4 text-center mb-4 mb-lg-0">
													<div class="d-inline-block">
														<div
															class="bg-black py-2 px-4 mb-2 text-white d-inline-block rounded">
															<span class="h5">3:2</span>
														</div>
													</div>
												</div>

												<div class="col-md-4 col-lg-4 text-center text-lg-right">
													<div class="">
														<div class="d-block d-lg-flex align-items-center">
															<div
																class="image image-small ml-lg-3 mb-3 mb-lg-0 order-2">
																<img src="../resource/images/img_4_sq.jpg" alt="Image"
																	class="img-fluid">
															</div>
															<div class="text order-1 w-100">
																<h3 class="h5 mb-0 text-black">Chiefs</h3>
																<span class="text-uppercase small country">London</span>
															</div>
														</div>
													</div>
												</div>
											</div>

											<!-- END row -->

										</div>

									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


		<div
			class="site-section block-13 bg-primary fixed overlay-primary bg-image"
			style="background-image: url('../resource/images/hero_bg_3.jpg');"
			data-stellar-background-ratio="0.5">

			<div class="container">
				<div class="row mb-5">
					<div class="col-md-12 text-center">
						<h2 class="text-white">More Game Highlights</h2>
					</div>
				</div>

				<div class="row">
					<div class="nonloop-block-13 owl-carousel">
						<div class="item">
							<!-- uses .block-12 -->
							<div class="block-12">
								<figure>
									<img src="../resource/images/img_1.jpg" alt="Image"
										class="img-fluid">
								</figure>
								<div class="text">
									<span class="meta">May 20th 2018</span>
									<div class="text-inner">
										<h2 class="heading mb-3">
											<a href="#" class="text-black">World Cup Championship</a>
										</h2>
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit. Ad culpa, consectetur! Eligendi illo, repellat
											repudiandae cumque fugiat optio!</p>
									</div>
								</div>
							</div>
						</div>

						<div class="item">
							<div class="block-12">
								<figure>
									<img src="../resource/images/img_2.jpg" alt="Image"
										class="img-fluid">
								</figure>
								<div class="text">
									<span class="meta">May 20th 2018</span>
									<div class="text-inner">
										<h2 class="heading mb-3">
											<a href="#" class="text-black">World Cup Championship</a>
										</h2>
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit. Ad culpa, consectetur! Eligendi illo, repellat
											repudiandae cumque fugiat optio!</p>
									</div>
								</div>
							</div>
						</div>

						<div class="item">
							<div class="block-12">
								<figure>
									<img src="../resource/images/img_3.jpg" alt="Image"
										class="img-fluid">
								</figure>
								<div class="text">
									<span class="meta">May 20th 2018</span>
									<div class="text-inner">
										<h2 class="heading mb-3">
											<a href="#" class="text-black">World Cup Championship</a>
										</h2>
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit. Ad culpa, consectetur! Eligendi illo, repellat
											repudiandae cumque fugiat optio!</p>
									</div>
								</div>
							</div>
						</div>

						<div class="item">
							<div class="block-12">
								<figure>
									<img src="../resource/images/img_4.jpg" alt="Image"
										class="img-fluid">
								</figure>
								<div class="text">
									<span class="meta">May 20th 2018</span>
									<div class="text-inner">
										<h2 class="heading mb-3">
											<a href="#" class="text-black">World Cup Championship</a>
										</h2>
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit. Ad culpa, consectetur! Eligendi illo, repellat
											repudiandae cumque fugiat optio!</p>
									</div>
								</div>
							</div>
						</div>
						<div class="item">
							<!-- uses .block-12 -->
							<div class="block-12">
								<figure>
									<img src="../resource/images/img_1.jpg" alt="Image"
										class="img-fluid">
								</figure>
								<div class="text">
									<span class="meta">May 20th 2018</span>
									<div class="text-inner">
										<h2 class="heading mb-3">
											<a href="#" class="text-black">World Cup Championship</a>
										</h2>
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit. Ad culpa, consectetur! Eligendi illo, repellat
											repudiandae cumque fugiat optio!</p>
									</div>
								</div>
							</div>
						</div>

						<div class="item">
							<div class="block-12">
								<figure>
									<img src="../resource/images/img_2.jpg" alt="Image"
										class="img-fluid">
								</figure>
								<div class="text">
									<span class="meta">May 20th 2018</span>
									<div class="text-inner">
										<h2 class="heading mb-3">
											<a href="#" class="text-black">World Cup Championship</a>
										</h2>
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit. Ad culpa, consectetur! Eligendi illo, repellat
											repudiandae cumque fugiat optio!</p>
									</div>
								</div>
							</div>
						</div>

						<div class="item">
							<div class="block-12">
								<figure>
									<img src="../resource/images/img_3.jpg" alt="Image"
										class="img-fluid">
								</figure>
								<div class="text">
									<span class="meta">May 20th 2018</span>
									<div class="text-inner">
										<h2 class="heading mb-3">
											<a href="#" class="text-black">World Cup Championship</a>
										</h2>
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit. Ad culpa, consectetur! Eligendi illo, repellat
											repudiandae cumque fugiat optio!</p>
									</div>
								</div>
							</div>
						</div>

						<div class="item">
							<div class="block-12">
								<figure>
									<img src="../resource/images/img_4.jpg" alt="Image"
										class="img-fluid">
								</figure>
								<div class="text">
									<span class="meta">May 20th 2018</span>
									<div class="text-inner">
										<h2 class="heading mb-3">
											<a href="#" class="text-black">World Cup Championship</a>
										</h2>
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit. Ad culpa, consectetur! Eligendi illo, repellat
											repudiandae cumque fugiat optio!</p>
									</div>
								</div>
							</div>
						</div>
						<div class="item">
							<!-- uses .block-12 -->
							<div class="block-12">
								<figure>
									<img src="../resource/images/img_1.jpg" alt="Image"
										class="img-fluid">
								</figure>
								<div class="text">
									<span class="meta">May 20th 2018</span>
									<div class="text-inner">
										<h2 class="heading mb-3">
											<a href="#" class="text-black">World Cup Championship</a>
										</h2>
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit. Ad culpa, consectetur! Eligendi illo, repellat
											repudiandae cumque fugiat optio!</p>
									</div>
								</div>
							</div>
						</div>

						<div class="item">
							<div class="block-12">
								<figure>
									<img src="../resource/images/img_2.jpg" alt="Image"
										class="img-fluid">
								</figure>
								<div class="text">
									<span class="meta">May 20th 2018</span>
									<div class="text-inner">
										<h2 class="heading mb-3">
											<a href="#" class="text-black">World Cup Championship</a>
										</h2>
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit. Ad culpa, consectetur! Eligendi illo, repellat
											repudiandae cumque fugiat optio!</p>
									</div>
								</div>
							</div>
						</div>

						<div class="item">
							<div class="block-12">
								<figure>
									<img src="../resource/images/img_3.jpg" alt="Image"
										class="img-fluid">
								</figure>
								<div class="text">
									<span class="meta">May 20th 2018</span>
									<div class="text-inner">
										<h2 class="heading mb-3">
											<a href="#" class="text-black">World Cup Championship</a>
										</h2>
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit. Ad culpa, consectetur! Eligendi illo, repellat
											repudiandae cumque fugiat optio!</p>
									</div>
								</div>
							</div>
						</div>

						<div class="item">
							<div class="block-12">
								<figure>
									<img src="../resource/images/img_4.jpg" alt="Image"
										class="img-fluid">
								</figure>
								<div class="text">
									<span class="meta">May 20th 2018</span>
									<div class="text-inner">
										<h2 class="heading mb-3">
											<a href="#" class="text-black">World Cup Championship</a>
										</h2>
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit. Ad culpa, consectetur! Eligendi illo, repellat
											repudiandae cumque fugiat optio!</p>
									</div>
								</div>
							</div>
						</div>
						<div class="item">
							<!-- uses .block-12 -->
							<div class="block-12">
								<figure>
									<img src="../resource/images/img_1.jpg" alt="Image"
										class="img-fluid">
								</figure>
								<div class="text">
									<span class="meta">May 20th 2018</span>
									<div class="text-inner">
										<h2 class="heading mb-3">
											<a href="#" class="text-black">World Cup Championship</a>
										</h2>
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit. Ad culpa, consectetur! Eligendi illo, repellat
											repudiandae cumque fugiat optio!</p>
									</div>
								</div>
							</div>
						</div>

						<div class="item">
							<div class="block-12">
								<figure>
									<img src="../resource/images/img_2.jpg" alt="Image"
										class="img-fluid">
								</figure>
								<div class="text">
									<span class="meta">May 20th 2018</span>
									<div class="text-inner">
										<h2 class="heading mb-3">
											<a href="#" class="text-black">World Cup Championship</a>
										</h2>
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit. Ad culpa, consectetur! Eligendi illo, repellat
											repudiandae cumque fugiat optio!</p>
									</div>
								</div>
							</div>
						</div>

						<div class="item">
							<div class="block-12">
								<figure>
									<img src="../resource/images/img_3.jpg" alt="Image"
										class="img-fluid">
								</figure>
								<div class="text">
									<span class="meta">May 20th 2018</span>
									<div class="text-inner">
										<h2 class="heading mb-3">
											<a href="#" class="text-black">World Cup Championship</a>
										</h2>
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit. Ad culpa, consectetur! Eligendi illo, repellat
											repudiandae cumque fugiat optio!</p>
									</div>
								</div>
							</div>
						</div>

						<div class="item">
							<div class="block-12">
								<figure>
									<img src="../resource/images/img_4.jpg" alt="Image"
										class="img-fluid">
								</figure>
								<div class="text">
									<span class="meta">May 20th 2018</span>
									<div class="text-inner">
										<h2 class="heading mb-3">
											<a href="#" class="text-black">World Cup Championship</a>
										</h2>
										<p>Lorem ipsum dolor sit amet, consectetur adipisicing
											elit. Ad culpa, consectetur! Eligendi illo, repellat
											repudiandae cumque fugiat optio!</p>
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>

		</div>

		<div class="site-section">
			<div class="container">
				<div class="row mb-5">
					<div class="col-md-12 text-center">
						<h2 class="text-black">Latest News</h2>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6 col-lg-4">
						<div class="post-entry">
							<div class="image">
								<img src="../resource/images/img_1.jpg" alt="Image"
									class="img-fluid">
							</div>
							<div class="text p-4">
								<h2 class="h5 text-black">
									<a href="#">RealMad vs Striker Who Will Win?</a>
								</h2>
								<span class="text-uppercase date d-block mb-3"><small>By
										Colorlib &bullet; Sep 25, 2018</small></span>
								<p class="mb-0">Lorem ipsum dolor sit amet, consectetur
									adipisicing elit. Fugiat beatae doloremque, ex corrupti
									perspiciatis.</p>
							</div>
						</div>
					</div>
					<div class="col-md-6 col-lg-4">
						<div class="post-entry">
							<div class="image">
								<img src="../resource/images/img_2.jpg" alt="Image"
									class="img-fluid">
							</div>
							<div class="text p-4">
								<h2 class="h5 text-black">
									<a href="#">RealMad vs Striker Who Will Win?</a>
								</h2>
								<span class="text-uppercase date d-block mb-3"><small>By
										Colorlib &bullet; Sep 25, 2018</small></span>
								<p class="mb-0">Lorem ipsum dolor sit amet, consectetur
									adipisicing elit. Fugiat beatae doloremque, ex corrupti
									perspiciatis.</p>
							</div>
						</div>
					</div>
					<div class="col-md-6 col-lg-4">
						<div class="post-entry">
							<div class="image">
								<img src="../resource/images/img_3.jpg" alt="Image"
									class="img-fluid">
							</div>
							<div class="text p-4">
								<h2 class="h5 text-black">
									<a href="#">RealMad vs Striker Who Will Win?</a>
								</h2>
								<span class="text-uppercase date d-block mb-3"><small>By
										Colorlib &bullet; Sep 25, 2018</small></span>
								<p class="mb-0">Lorem ipsum dolor sit amet, consectetur
									adipisicing elit. Fugiat beatae doloremque, ex corrupti
									perspiciatis.</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>



		<footer class="site-footer border-top">
			<div class="container">
				<div class="row">
					<div class="col-lg-4">
						<div class="mb-5">
							<h3 class="footer-heading mb-4">About Sportz</h3>
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.
								Saepe pariatur reprehenderit vero atque, consequatur id ratione,
								et non dignissimos culpa? Ut veritatis, quos illum totam quis
								blanditiis, minima minus odio!</p>
						</div>

						<div class="mb-5">
							<h3 class="footer-heading mb-4">Recent Blog</h3>
							<div class="block-25">
								<ul class="list-unstyled">
									<li class="mb-3"><a href="#" class="d-flex">
											<figure class="image mr-4">
												<img src="../resource/images/img_1.jpg" alt=""
													class="img-fluid">
											</figure>
											<div class="text">
												<h3 class="heading font-weight-light">Lorem ipsum dolor
													sit amet consectetur elit</h3>
											</div>
									</a></li>
									<li class="mb-3"><a href="#" class="d-flex">
											<figure class="image mr-4">
												<img src="../resource/images/img_1.jpg" alt=""
													class="img-fluid">
											</figure>
											<div class="text">
												<h3 class="heading font-weight-light">Lorem ipsum dolor
													sit amet consectetur elit</h3>
											</div>
									</a></li>
									<li class="mb-3"><a href="#" class="d-flex">
											<figure class="image mr-4">
												<img src="../resource/images/img_1.jpg" alt=""
													class="img-fluid">
											</figure>
											<div class="text">
												<h3 class="heading font-weight-light">Lorem ipsum dolor
													sit amet consectetur elit</h3>
											</div>
									</a></li>
								</ul>
							</div>
						</div>

					</div>
					<div class="col-lg-4 mb-5 mb-lg-0">
						<div class="row mb-5">
							<div class="col-md-12">
								<h3 class="footer-heading mb-4">Quick Menu</h3>
							</div>
							<div class="col-md-6 col-lg-6">
								<ul class="list-unstyled">
									<li><a href="#">Home</a></li>
									<li><a href="#">Matches</a></li>
									<li><a href="#">News</a></li>
									<li><a href="#">Team</a></li>
								</ul>
							</div>
							<div class="col-md-6 col-lg-6">
								<ul class="list-unstyled">
									<li><a href="#">About Us</a></li>
									<li><a href="#">Privacy Policy</a></li>
									<li><a href="#">Contact Us</a></li>
									<li><a href="#">Membership</a></li>
								</ul>
							</div>
						</div>

						<div class="row">
							<div class="col-md-12">
								<h3 class="footer-heading mb-4">Follow Us</h3>

								<div>
									<a href="#" class="pl-0 pr-3"><span class="icon-facebook"></span></a>
									<a href="#" class="pl-3 pr-3"><span class="icon-twitter"></span></a>
									<a href="#" class="pl-3 pr-3"><span class="icon-instagram"></span></a>
									<a href="#" class="pl-3 pr-3"><span class="icon-linkedin"></span></a>
								</div>
							</div>
						</div>

					</div>

					<div class="col-lg-4 mb-5 mb-lg-0">
						<div class="mb-5">
							<h3 class="footer-heading mb-4">Watch Video</h3>

							<div class="block-16">
								<figure>
									<img src="../resource/images/img_1.jpg" alt="Image placeholder"
										class="img-fluid rounded">
									<a href="https://vimeo.com/channels/staffpicks/93951774"
										class="play-button popup-vimeo"><span class="icon-play"></span></a>
								</figure>
							</div>

						</div>

						<div class="mb-5">
							<h3 class="footer-heading mb-2">Subscribe Newsletter</h3>
							<p>Lorem ipsum dolor sit amet consectetur adipisicing elit
								minima minus odio.</p>

							<form action="#" method="post">
								<div class="input-group mb-3">
									<input type="text"
										class="form-control border-secondary text-white bg-transparent"
										placeholder="Enter Email" aria-label="Enter Email"
										aria-describedby="button-addon2">
									<div class="input-group-append">
										<button class="btn btn-primary" type="button"
											id="button-addon2">Send</button>
									</div>
								</div>
							</form>

						</div>

					</div>

				</div>
				<div class="row pt-5 mt-5 text-center">
					<div class="col-md-12">
						<p>
							<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
							Copyright &copy;
							<script>
								document.write(new Date().getFullYear());
							</script>
							All rights reserved | This template is made with <i
								class="icon-heart-o" aria-hidden="true"></i> by <a
								href="https://colorlib.com" target="_blank">Colorlib</a>
							<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						</p>
					</div>

				</div>
			</div>
		</footer>
	</div>



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
	
<!-- 	<script>
      let resHTML = "";
      resHTML += '<div class="site-blocks-cover overlay" style="background-image: url(../resource/images/hero_bg_3.jpg);" data-aos="fade" data-stellar-background-ratio="0.5">';
      resHTML += '<div class="container">';
      resHTML += '<div class="row align-items-center justify-content-start">';
      resHTML += '<div class="col-md-6 text-center text-md-left" data-aos="fade-up" data-aos-delay="400">';
      resHTML += '<h1 class="bg-text-line">Russia\'s World Cup Championship</h1>';
      resHTML += '<p><a href="#" class="btn btn-primary btn-sm rounded-0 py-3 px-5">Read More</a></p>';
      resHTML += '</div>';
      resHTML += '</div>';
      resHTML += '</div>';
      resHTML += '</div>';
	
      
      
      resHTML += '<div class="site-blocks-cover overlay" style="background-image: url(../resource/images/hero_bg_3.jpg);" data-aos="fade" data-stellar-background-ratio="0.5">';
      resHTML += '<div class="container">';
      resHTML += '<div class="row align-items-center justify-content-start">';
      resHTML += '<div class="col-md-6 text-center text-md-left" data-aos="fade-up" data-aos-delay="400">';
      resHTML += '<h1 class="bg-text-line">Russia\'s World Cup Championship</h1>';
      resHTML += '<p><a href="#" class="btn btn-primary btn-sm rounded-0 py-3 px-5">Read More</a></p>';
      resHTML += '</div>';
      resHTML += '</div>';
      resHTML += '</div>';
      resHTML += '</div>';
      
      resHTML += '<div class="site-blocks-cover overlay" style="background-image: url(../resource/images/hero_bg_3.jpg);" data-aos="fade" data-stellar-background-ratio="0.5">';
      resHTML += '<div class="container">';
      resHTML += '<div class="row align-items-center justify-content-start">';
      resHTML += '<div class="col-md-6 text-center text-md-left" data-aos="fade-up" data-aos-delay="400">';
      resHTML += '<h1 class="bg-text-line">Russia\'s World Cup Championship</h1>';
      resHTML += '<p><a href="#" class="btn btn-primary btn-sm rounded-0 py-3 px-5">Read More</a></p>';
      resHTML += '</div>';
      resHTML += '</div>';
      resHTML += '</div>';
      resHTML += '</div>';


      $(".home-slider").append(resHTML);
    </script> -->
    
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