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

import poly.dto.EPLDTO;
import poly.service.IGetEPLdataService;

@Controller
public class GetEPLController {

	private Logger log = Logger.getLogger(this.getClass());

	
	@Resource(name = "GetEPLdataService")
	IGetEPLdataService getEPLdataService;
	
	@RequestMapping(value = "test")
	public String test(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		log.info("get season start");
		
		
		String contents = "seasons";
		String key = "9c342130-8b20-11eb-beac-258002402588";
		String leagueId = "237";
		
		// OpenAPI url
		String url = "https://app.sportdataapi.com/api/v1/soccer/"+contents+"?apikey="+key+"&league_id="+leagueId;
//		// 검색할 내용
//		url += "/seasons";
//		// api 키
//		url+= "?apikey=9c342130-8b20-11eb-beac-258002402588";
//		// league id
//		url += "&league_id=237";
		
		log.info("api 주소 : " + url);
		
		EPLDTO pDTO = new EPLDTO();
		pDTO.setUrl(url);
		
		//JSON 으로부터 받은 결과를 자바에서 처리 가능한 데이터 구조로 변경
		Map<String, Object> rMap = getEPLdataService.GetEPLSeason(pDTO);
		
		//JSON으로부터 받은 결과를 자바에서 처리 가능한 데이터 구조로 변경한 변수를 JSP에 전달
		model.addAttribute("rMap", rMap);
		
		log.info(this.getClass().getName() + ".getSeason end!");
		
		return "/test";
	}
	
}
