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

import poly.dto.EPLDTO;
import poly.dto.YouTubeDTO;
import poly.service.IEPLdataService;
import poly.service.IGetYoutubeDataService;

@Controller
public class GetYouTubeController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "GetYoutubeDataService")
	IGetYoutubeDataService getYoutubeDataService;
	
	@Resource(name = "EPLdataService")
	IEPLdataService epldataService;
	
	
	@RequestMapping(value = "GetTeamHilights")
	@ResponseBody
	public List<YouTubeDTO> GetTeamHilights(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		log.info("GetTeamHilights start");
			
		
		String team = request.getParameter("team").trim();
		
		// 한국 유튜브 채널이므로 한글만 입력해서 정확도 올리기
		EPLDTO rDTO = epldataService.getkoname(team);
		
		String ko_name = rDTO.getKo_name().trim();
		
		//검색 내용
		String search = ((ko_name+" "+team).replaceAll(" FC", "").trim()).replaceAll(" ", "+");
		log.info("검색 값 :: "+search);
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
		
		
		log.info(this.getClass().getName() + ".GetTeamHilights end!");
		return rList;
	}
	
	
}
