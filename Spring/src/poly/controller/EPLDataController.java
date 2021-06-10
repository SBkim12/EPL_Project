package poly.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
public class EPLDataController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "EPLdataService")
	IEPLdataService epldataService;
	
	@Resource(name = "GetYoutubeDataService")
	IGetYoutubeDataService getYoutubeDataService;
	
	@RequestMapping(value = "getEPLteamPlayer")
	@ResponseBody
	public List<Map<String, Object>> getEPLteamPlayer(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		log.info(this.getClass().getName() + ".getEPLteamPlayer start!");
		
		
		String team = request.getParameter("team");
		
		//팀 이름 형태 변환
		if (team.endsWith("FC")) {
			team = team.substring(0, team.lastIndexOf("FC") - 1).trim();
		}
		String teamForUrl = team.replaceAll("&", "and");
		
		log.info("검색 팀 이름(형태에맞게 변환 완료) :: "+ teamForUrl);
		
		List<Map<String, Object>> playerInfo= new LinkedList<Map<String, Object>>();
		
		playerInfo = epldataService.getEPLteamPlayer(teamForUrl);
		
		if(playerInfo ==null) {
			playerInfo = new LinkedList<Map<String, Object>>();
		}
			
		
		log.info(this.getClass().getName() + ".getEPLteamPlayer end!");

		return playerInfo;
	}
	
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
	
	
	@RequestMapping(value = "GetComingMatch")
	@ResponseBody
	public List<Map<String, String>> GetComingMatch(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		log.info(this.getClass().getName() + ".GetComingMatch start!");
		
		String team =  request.getParameter("team").trim();
		
		String seasonId = epldataService.presentSeason();
		
		List<Map<String, String>> rList = epldataService.getUpcomingGame(team, seasonId);
		
		log.info(this.getClass().getName() + ".GetComingMatch end!");
		
		return rList;
	}
}
