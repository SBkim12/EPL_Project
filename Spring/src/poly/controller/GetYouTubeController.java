package poly.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.dto.YouTubeDTO;
import poly.service.IGetPlayerVideoService;

@Controller
public class GetYouTubeController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "GetPlayerVideoService")
	IGetPlayerVideoService getPlayerVideoService;
	
	@RequestMapping(value = "searchYoutube")
	public String test1(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		log.info("get youtube start");
			
		//검색 내용
		String search = "e.hazard";
		//Youtube Data API KEy
		String key = "AIzaSyDdn4XkqugGpv7pmDGIj8jOilkDzHmqES8";
		// 검색 갯수
		String max = "3";
		
		
		// OpenAPI url
		String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q="+search+"&maxResults="+max+"&key="+key;

		
		log.info("api 주소 : " + url);
		
		YouTubeDTO pDTO = new YouTubeDTO();
		pDTO.setUrl(url);
		
		//JSON 으로부터 받은 결과를 자바에서 처리 가능한 데이터 구조로 변경
		Map<String, Object> rMap = getPlayerVideoService.GetPlayerVideo(pDTO);
		
		//JSON으로부터 받은 결과를 자바에서 처리 가능한 데이터 구조로 변경한 변수를 JSP에 전달
		model.addAttribute("rMap", rMap);
		
		log.info(this.getClass().getName() + ".getSeason end!");
		return "test1";
	}
	
}
