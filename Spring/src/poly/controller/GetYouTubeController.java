package poly.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.dto.YouTubeDTO;
import poly.service.IGetYoutubeDataService;

@Controller
public class GetYouTubeController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "GetYoutubeDataService")
	IGetYoutubeDataService getYoutubeDataService;
	
	
	@RequestMapping(value = "RelatedVideo")
	@ResponseBody
	public List<YouTubeDTO> RelatedVideo(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		log.info("get RelatedVideo start");
			
		
		String team = request.getParameter("team").trim().replaceAll(" ", "+");
		
		//영문 , 한글 팀명 모두 입력 할것
		
		//검색 내용
		String search = "Chelsea+FC+첼시";
		//Youtube Data API KEy
		String key = "AIzaSyDdn4XkqugGpv7pmDGIj8jOilkDzHmqES8";
		// 검색 갯수
		String max = "3";
		
		String SPOTV_ID = "UCtm_QoN2SIxwCE-59shX7Qg";
		
		// OpenAPI url search
//		String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q="+search+"&maxResults="+max+"&key="+key;
		String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId="+SPOTV_ID+"&q="+search+"&order=date&maxResults="+max+"&key="+key;
		
		log.info("api 주소 : " + url);
		
		List<YouTubeDTO> rList = getYoutubeDataService.GetTeamVideo(url);
		
		
		log.info(this.getClass().getName() + ".RelatedVideo end!");
		return rList;
	}
	
	
}
