<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
    
    String msg = (String)request.getAttribute("msg");
	String url = (String)request.getAttribute("url");
    
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="../resource/js/jquery-3.3.1.min.js"></script>
<script src="../resource/js/sweetalert.min.js"></script>
<script type="text/javascript">
$(document).ready(function confirm(){
	Swal.fire({
	      title: "<%=msg%>",
	      icon: 'info',
	    })
	    .then((toContinue) => {
	      if (toContinue) {
	        location.href = "<%=url%>";
	      }
	    });
})
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>redirect</title>
</head>
<body>
</body>
</html>
