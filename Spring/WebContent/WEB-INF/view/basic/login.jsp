<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>LOGIN</title>
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
<script>
 function googleLoginInit() {
     gapi.load('auth2', function () {
         /* Ready. Make a call to gapi.auth2.init or some other API */
         let gAuth = gapi.auth2.init({
             client_id: '948495532957-v4dpe3r0ft0kllgjcrq2e6i9vr2f37cm.apps.googleusercontent.com'
         });
         gAuth.then(function () {
             gAuth.signIn().then(function () {
                 let profile = gAuth.currentUser.get().getBasicProfile();
                 let google_Email = profile.getEmail();
                 let google_name = profile.getName();
                 
                 $.ajax({
         			url : "GoogleLoginProc.do",
         			type : "post",
         			data : {"id" :  google_Email,
         					"name" : google_name
         					},
         			dataType : "json",
         			success : function(data) {
         				if(data==1){
         					Swal.fire({
								icon : 'success',
								title : '로그인 되었습니다.',
								showConfirmButton : false,
								timer : 1000
							})
         					location.href="/home.do";
         				}else if(data==2){
         					Swal.fire({
								icon : 'success',
								title : '회원 가입 되었습니다.',
								showConfirmButton : false,
								timer : 1000
							})
         					location.href="/home.do";
         				}else{
         					Swal.fire({
								icon : 'error',
								title : 'Oops...',
								text : '구글 로그인에 실패 하였습니다.!',
							});
         				}
         				
         			}	
         		});
             });
         }, function (error) {
             alert(JSON.stringify(error, undefined, 2));
         })
     });
 }
</script>
</head>
<body>


	<div class="container-login100"
		style="background-image: url('../resource_login/images/grass.jpg');">
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

				<div class="container-login100-form-btn mt-5">
					<button type="submit" class="login100-form-btn">Log In</button>
				</div>

				<div class="text-center p-t-57 p-b-20">
					<span class="txt1"> Or login with </span>
				</div>
				
				<div class="flex-c p-b-40">
					<a class="login100-social-itemg-signins2" onclick="googleLoginInit()">
						<img src="../resource_login/images/icons/icon-google.png" alt="GOOGLE" >
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
	<script src="https://apis.google.com/js/platform.js" async defer></script>
</body>
</html>