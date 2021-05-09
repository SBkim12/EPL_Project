<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="poly.dto.EPLDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="poly.util.CmmUtil"%>
<%
	List<EPLDTO> rList = (List<EPLDTO>) request.getAttribute("teams");

	if(rList==null){
		rList = new ArrayList<EPLDTO>();
	}
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Login V9</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="../resource_login/images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="../resource_login/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="../resource_login/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="../resource_login/fonts/iconic/css/material-design-iconic-font.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="../resource_login/vendor/animate/animate.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="../resource_login/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="../resource_login/vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="../resource_login/vendor/select2/select2.min.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="../resource_login/vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="../resour	ce_login/css/util.css">
	<link rel="stylesheet" type="text/css" href="../resource_login/css/main.css">
<!--===============================================================================================-->
</head>
<body>
	<div class="container-login100" style="background-image: url('../resource_login/images/EPL.png');">
		<div class="wrap-login100 p-l-55 p-r-55 p-t-80 p-b-30">
			<form class="login100-form validate-form" action="SignUpProc.do" method="post" >
				<span class="login100-form-title p-b-37">
					Sign Up
				</span>

				<div class="wrap-input100 validate-input m-b-15" data-validate="Enter email">
					<input class="input100" type="email" id="userId" name="id" placeholder="Email">
					<span class="focus-input100"></span>
				</div>

				<div class="wrap-input100 validate-input m-b-15" data-validate = "Enter password">
					<input id="newPassWord" class="input100" type="password" name="pwd" placeholder="password">
					<span class="focus-input100"></span>
				</div>
				
				<div class="wrap-input100 validate-input m-b-15" data-validate = "Enter password check">
					<input id="passWordCheck" class="input100" type="password" name="pwd_check" placeholder="password check">
					<span class="focus-input100"></span>
				</div>
				
				<div class="wrap-input100 validate-input m-b-15	" data-validate = "Enter Your nickname">
					<input id="passWordCheck" class="input100" type="text" name="name" placeholder="nickname">
					<span class="focus-input100"></span>
				</div>
				
				<div class="bline flexbox p-b-18">
					<div class="login100-form-title label-input100 text-right p-b-5 p-t-20" style="font-size:15px;">Favorite EPL TEAM</div>
					<div>
					<select class="label-input100"style="width:100%" name="favorite_team"
						style="margin-left: 15px">
						<!-- 1부 리그에 현존하는 팀만 조회 -->
						<%for(int i=0; i<rList.size(); i++){
							EPLDTO rDTO = rList.get(i);
							if(rDTO==null){
								rDTO = new EPLDTO();
							}
						%>
						<option value="<%=CmmUtil.nvl(rDTO.getKo_name())%>"><%=CmmUtil.nvl(rDTO.getKo_name())%></option>	
						<%}%>
					</select></div>
				</div>
				
				
				
				
				<div class="container-login100-form-btn">
					<button type="submit" class="login100-form-btn">
						Complete
					</button>
				</div>

			</form>

			
		</div>
	</div>
	
	

	<div id="dropDownSelect1"></div>

<!--===============================================================================================-->
	<script src="../resource_login/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="../resource_login/vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
	<script src="../resource_login/vendor/bootstrap/js/popper.js"></script>
	<script src="../resource_login/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="../resource_login/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
	<script src="../resource_login/vendor/daterangepicker/moment.min.js"></script>
	<script src="../resource_login/vendor/daterangepicker/daterangepicker.js"></script>
<!--===============================================================================================-->
	<script src="../resource_login/vendor/countdowntime/countdowntime.js"></script>
<!--===============================================================================================-->
	<script src="../resource_login/js/main.js" ></script>
</body>
</html>