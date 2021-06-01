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

import poly.service.IEPLdataService;

@Controller
public class EPLDataController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "EPLdataService")
	IEPLdataService epldataService;
	
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
	
}
