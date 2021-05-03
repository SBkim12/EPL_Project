<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="poly.util.CmmUtil" %>
<%@ page import="poly.dto.EPLDTO" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%
//Controller로부터 전달받은 데이터
Map<String, Object> rMap = (Map<String,Object>)request.getAttribute("rMap");

if(rMap ==null){
	rMap = new HashMap<String, Object>();
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>시즌이 몇개?</title>
</head>
<body>

<hr/><br>
<%
List<EPLDTO> rList = (List<EPLDTO>)rMap.get("res");

Iterator<EPLDTO> it = rList.iterator();

while(it.hasNext()){
	EPLDTO rDTO = (EPLDTO)it.next();
	
	out.println("season_id : " + CmmUtil.nvl(rDTO.getSeason_id()) + "<br>");
	out.println("season_name : " + CmmUtil.nvl(rDTO.getSeason_name()) + "<br>");
	out.println("start_date : " + CmmUtil.nvl(rDTO.getStart_date()) + "<br>");
	out.println("end_date : " + CmmUtil.nvl(rDTO.getEnd_date()) + "<br><hr>");
}
%>
</body>
</html>