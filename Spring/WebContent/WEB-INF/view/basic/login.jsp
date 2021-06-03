<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Login V9</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->
<link rel="icon" type="image/png"
	href="../resource_login/images/icons/favicon.ico" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resource_login/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resource_login/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resource_login/fonts/iconic/css/material-design-iconic-font.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resource_login/vendor/animate/animate.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resource_login/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resource_login/vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resource_login/vendor/select2/select2.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resource_login/vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../resour	ce_login/css/util.css">
<link rel="stylesheet" type="text/css"
	href="../resource_login/css/main.css">
<!--===============================================================================================-->
<script src="https://kit.fontawesome.com/84865ac036.js"
	crossorigin="anonymous"></script>
<style>
/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content/Box */
.modal-content {
	background-color: #fefefe;
	margin: 15% auto; /* 15% from the top and centered */
	padding: 20px;
	border: 1px solid #888;
	width: 80%; /* Could be more or less, depending on screen size */
}
</style>
</head>
<body>


	<div class="container-login100"
		style="background-image: url('../resource_login/images/EPL.png');">
		<div class="wrap-login100 p-l-55 p-r-55 p-t-80 p-b-30">
			<form class="login100-form validate-form" action="/LoginProc.do"
				method="post">
				<span class="login100-form-title p-b-37"> Sign In </span>

				<div class="wrap-input100 validate-input m-b-20"
					data-validate="Enter email">
					<input class="input100" type="text" name="id" id="userId"
						placeholder="Email"> <span class="focus-input100"></span>
				</div>

				<div class="wrap-input100 validate-input m-b-3"
					data-validate="Enter password">
					<input class="input100" type="password" name="pwd"
						placeholder="password"> <span class="focus-input100"></span>
				</div>

				<div class="text-right m-b-15" style="font-size: 13px">
					<span onClick="open_pop();" class="m-r-10 hov1"
						style="cursor: pointer">Forgot password</span>
				</div>

				<div class="container-login100-form-btn">
					<button type="submit" class="login100-form-btn">Log In</button>
				</div>

				<div class="text-center p-t-57 p-b-20">
					<span class="txt1"> Or login with </span>
				</div>

				<div class="flex-c p-b-112">
					<a href="#" class="login100-social-item"> <i
						class="fa fa-facebook-f"></i>
					</a> <a href="#" class="login100-social-item"> <img
						src="../resource_login/images/icons/icon-google.png" alt="GOOGLE">
					</a>
				</div>

				<div class="text-center">
					<a href="SignUp.do" class="txt2 hov1"> Sign Up </a>
				</div>
			</form>


		</div>
	</div>


	<script type="text/javascript">
	
		
		//mail 알림창


		function open_pop() {
			Swal.fire({
				title : 'Get new password\nPlease insert Email Address',
				input : 'email',
				showCancelButton : true,
				confirmButtonText : 'Submit',
				showLoaderOnConfirm : true,
			}).then(function(data) {
				if (data.isConfirmed) {
					var email = data.value;
					$.ajax({
						type : "POST",
						url : "sendNewPwdProc.do",
						dataType : "json",
						data : {
							"email" : email
						},
						success : function(data) {
							console.log(data)
							if (data == 0) {
								Swal.fire({
									icon : 'error',
									title : 'Oops...',
									text : '회원정보가 없는 이메일 입니다!',
								});
							} else if (data == 1) {
								Swal.fire({
									icon : 'success',
									title : '임시 비밀번호가 발송 되었습니다.',
									showConfirmButton : false,
									timer : 1000
								})
							} else if (data == 2) {
								Swal.fire({
									icon : 'error',
									title : 'Oops...',
									text : '임시 비밀번호 발송에 실패했습니다!',
								});
							} else {
								Swal.fire({
									icon : 'error',
									title : 'Oops...',
									text : '전산에 오류로 임시 비밀번호 변경을 실패했습니다!',
								});
							}

						}
					});
				}
			});
		}
	</script>
	<!--  알림 javascript -->
	<script src="../resource/js/sweetalert.min.js"></script>
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
	<script
		src="../resource_login/vendor/daterangepicker/daterangepicker.js"></script>
	<!--===============================================================================================-->
	<script src="../resource_login/vendor/countdowntime/countdowntime.js"></script>
	<!--===============================================================================================-->
	<script src="../resource_login/js/main.js"></script>

</body>
</html>