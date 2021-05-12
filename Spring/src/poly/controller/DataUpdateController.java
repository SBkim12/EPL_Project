package poly.controller;

import java.util.ArrayList;
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
import poly.service.IEPLdataService;
import poly.util.SportsDataUtil;

@Controller
public class DataUpdateController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "EPLdataService")
	IEPLdataService epldataService;
	
	@RequestMapping(value = "TeamLogoUpdate")
	@ResponseBody
	public String teamLogoUpdate(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		log.info(this.getClass().getName() + ".TeamLogoUpdate start!");
		
		String key = SportsDataUtil.APIKey;
		String England = SportsDataUtil.England;
		
		String url ="https://app.sportdataapi.com/api/v1/soccer/teams?apikey="+key+"&country_id="+England;
		
		EPLDTO pDTO = new EPLDTO();
		pDTO.setUrl(url);
		
		int res = epldataService.updateLogo(pDTO);
		
		String answer = "";
		if(res>0) {
			answer = "성공";
		}else {
			answer = "실패";
		}
		
		return "팀 로고 업데이트 " +answer;
	}
	
	@RequestMapping(value = "seasonDataUpdate")
	@ResponseBody
	public List<EPLDTO> seasonDataUpdate(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		log.info(this.getClass().getName() + ".seasonDateUpdate start!");
		
		String key = SportsDataUtil.APIKey;
		String EPL = SportsDataUtil.EPL;
		
		String url ="https://app.sportdataapi.com/api/v1/soccer/seasons?apikey="+key+"&league_id="+EPL;
		
		List<EPLDTO> rList = new ArrayList<EPLDTO>();
		
		rList = epldataService.updateSeasonRank(url);
		
		log.info(this.getClass().getName() + ".seasonDateUpdate end!");
		return rList;
	}
	
	@RequestMapping(value = "seasonUpdate")
	@ResponseBody
	public String seasonUpdate(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		log.info(this.getClass().getName() + ".seasonUpdate start!");
		
		String key = SportsDataUtil.APIKey;
		String EPL = SportsDataUtil.EPL;
		
		String url ="https://app.sportdataapi.com/api/v1/soccer/seasons?apikey="+key+"&league_id="+EPL;
		
		int res = epldataService.updateSeason(url);
		
		String answer = "";
		if(res>0) {
			answer = "성공";
		}else {
			answer = "실패";
		}
		
		log.info(this.getClass().getName() + ".seasonDateUpdate end!");
		return answer;
	}
}
