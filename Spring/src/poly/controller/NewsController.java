package poly.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.dto.EPLDTO;
import poly.service.IEPLdataService;
import poly.service.INewsService;

@Controller
public class NewsController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "EPLdataService")
	IEPLdataService epldataService;
	
	@Resource(name = "NewsService")
	INewsService newsService;
	
	//뉴스 업데이트(영국 23시에  동작 => 한국시간 7시에 동작)
	@Scheduled(cron = "0 0 7 * * *")
	@RequestMapping(value = "newsUpdate")
	@ResponseBody
	public String newsUpdate(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		log.info(this.getClass().getName() + ".newsUpdate start!");
		
		String answer = "";
		
		
		List<EPLDTO> rList = new ArrayList<EPLDTO>();
		
		rList = epldataService.getEPLteam();
		
		
		log.info("SkytSports 뉴스 웹 크롤링 및 DB업데이트 시작");
		int res = newsService.skySportsNewsUpdate(rList);
		
		if(res>0) {
			log.info("SkySports 뉴스 업데이트 성공");
		}else {
			log.info("SkySports 뉴스 업데이트 실패");
		}
		log.info("SkytSports 뉴스 웹 크롤링 및 DB업데이트 종료");

		
		log.info("The Guardian 뉴스 웹 크롤링 및 DB업데이트 시작");
		int res1 = newsService.theGuardianNewsUpdate();
		
		if(res1>0) {
			log.info("The Guardian 뉴스 업데이트 성공");
		}else {
			log.info("The Guardian 뉴스 업데이트 실패");
		}
		log.info("The Guardian 뉴스 웹 크롤링 및 DB업데이트 종료");
		
		log.info(this.getClass().getName() + ".newsUpdate end!!");
		return answer;
	}
	
	@RequestMapping(value = "mainNews")
	@ResponseBody
	public List<Map<String, Object>> mainNews(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		log.info(this.getClass().getName() + ".mainNews start!");

		//팀 값을 넘겨주는 경우 그 팀에 관한 뉴스 표시, 넘겨주지 않는 경우 유저 정보의 최애팀 뉴스 표시
		//String team = request.getParameter("team")==null ? session.getAttribute("favorite_team").toString() : request.getParameter("team");

		String team = "Aston Villa";

		String news = "_Sky_Sports";

		List<Map<String, Object>> rList = newsService.getMainNews(team, news);

		news = "_The_Guardian";

		rList.addAll(newsService.getMainNews(team, news));

		log.info(this.getClass().getName() + ".mainNews End!");
		return rList;
	}
	
}
