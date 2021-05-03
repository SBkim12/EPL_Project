<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="poly.util.CmmUtil" %>
<%@ page import="poly.dto.YouTubeDTO" %>
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
<title>유튜브 비디오 가져오기</title>
</head>
<body>
<%
List<YouTubeDTO> rList = (List<YouTubeDTO>)rMap.get("res");


Iterator<YouTubeDTO> it = rList.iterator();
int a =0;
while(it.hasNext()){
	YouTubeDTO rDTO = (YouTubeDTO)it.next();
	
	out.println("video_id :: " + CmmUtil.nvl(rDTO.getVideo_id()) + "<br>");
	out.println("video_title :: " + CmmUtil.nvl(rDTO.getVideo_title()) + "<br>");
	out.println("video_thumbnail :: " + CmmUtil.nvl(rDTO.getVideo_thumbnails()) + "<br>");
}%>
<%-- <script>

	function abc(){
		var aIframe = document.createElement("iframe");

		aIframe.setAttribute("id",<%=a%>);
		aIframe.setAttribute("name",<%=a%>);
		aIframe.setAttribute("frameborder",0);
		
		aIframe.style.width = "200px";
		aIframe.style.height = "100px";
		aIframe.src ="https://www.youtube.com/embed/"+<%=CmmUtil.nvl(rDTO.getVideo_id())%>+"";
		
		document.body.appendChild(iframe);
		} --%>

</script>
</body>
</html>