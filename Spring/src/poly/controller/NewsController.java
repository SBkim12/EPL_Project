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
	
	// 뉴스 업데이트(영국 0시 30분에 동작 => 한국시간 8시 30분에 동작)
	@Scheduled(cron = "0 10 8 * * *")
	public void news_update_schedule() throws Exception {
		log.info(this.getClass().getName() + ".newsUpdate start!");
		
		String start = poly.util.dateUtil.hh_mm_ss();
		
		List<EPLDTO> rList = new ArrayList<EPLDTO>();

		rList = epldataService.getEPLteam();

		log.info("SkytSports 뉴스 웹 크롤링 및 DB업데이트 시작");
		int res = newsService.skySportsNewsUpdate(rList);
		log.info("SkytSports 뉴스 웹 크롤링 및 DB업데이트 종료");

		
		log.info("The Guardian 뉴스 웹 크롤링 및 DB업데이트 시작");
		int res1 = newsService.theGuardianNewsUpdate();
		log.info("The Guardian 뉴스 웹 크롤링 및 DB업데이트 종료");
		
		if (res > 0) {
			log.info("SkySports 뉴스 업데이트 성공");
		} else {
			log.info("SkySports 뉴스 업데이트 실패");
		}
		
		if (res1 > 0) {
			log.info("The Guardian 뉴스 업데이트 성공");
		} else {
			log.info("The Guardian 뉴스 업데이트 실패");
		}
		
		String end = poly.util.dateUtil.hh_mm_ss();
		
		log.info(start + " ~ " + end);
		
		log.info("뉴스 수집 날짜 :: "+ poly.util.dateUtil.today_month_day());

		log.info(this.getClass().getName() + ".newsUpdate end!!");
	}

	// 강제 업데이트 (DB에 url 없는 것들 추가 업데이트하는 걸로 추가 구현 할것)
	@RequestMapping(value = "skySportsUpdate")
	@ResponseBody
	public String skySportsUpdate(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		log.info(this.getClass().getName() + ".skySportsUpdate start!");

		String answer = "";

		List<EPLDTO> rList = new ArrayList<EPLDTO>();

		rList = epldataService.getEPLteam();

		log.info("SkytSports 뉴스 웹 크롤링 및 DB업데이트 시작");
		int res = newsService.skySportsNewsUpdate(rList);

		if (res > 0) {
			log.info("SkySports 뉴스 업데이트 성공");
		} else {
			log.info("SkySports 뉴스 업데이트 실패");
		}
		log.info("SkytSports 뉴스 웹 크롤링 및 DB업데이트 종료");
		
		
		log.info(this.getClass().getName() + ".skySportsUpdate end!!");
		return answer;
	}
	
	// 강제 업데이트
	@RequestMapping(value = "theGuardianUpdate")
	@ResponseBody
	public String theGuardianUpdate(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		log.info(this.getClass().getName() + ".theGuardianUpdate start!");

		String answer = "";

		log.info("The Guardian 뉴스 웹 크롤링 및 DB업데이트 시작");
		int res1 = newsService.theGuardianNewsUpdate();

		if (res1 > 0) {
			log.info("The Guardian 뉴스 업데이트 성공");
		} else {
			log.info("The Guardian 뉴스 업데이트 실패");
		}
		log.info("The Guardian 뉴스 웹 크롤링 및 DB업데이트 종료");
		
		
		log.info(this.getClass().getName() + ".theGuardianUpdate end!!");
		return answer;
	}
	
	@RequestMapping(value = "mainNews")
	@ResponseBody
	public List<Map<String, Object>> mainNews(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		log.info(this.getClass().getName() + ".mainNews start!");

		//팀 값을 넘겨주는 경우 그 팀에 관한 뉴스 표시, 넘겨주지 않는 경우 유저 정보의 최애팀 뉴스 표시
		String team = request.getParameter("team")==null ? session.getAttribute("favorite_team").toString() : request.getParameter("team");
		
		log.info("team :: " +team);
		
		//각 뉴스사에서 가져올 뉴스 개수
		int no = 3;
		
		String news = "_Sky_Sports";

		List<Map<String, Object>> rList = newsService.getMainNews(team, news, no);

		news = "_The_Guardian";

		rList.addAll(newsService.getMainNews(team, news, no));
		
		//현재시즌 아닌팀을 불러 왔을 경우 현재시즌 1등팀 뉴스 가져오기
		if(rList.size()<1) {
			List<EPLDTO> mList = epldataService.getEPLteam();
			//1등팀 가져오기
			EPLDTO data = mList.get(0);
			team = data.getName();
			
			//다시 뉴스 검색
			news = "_Sky_Sports";
			
			rList = newsService.getMainNews(team, news, no);

			news = "_The_Guardian";

			rList.addAll(newsService.getMainNews(team, news, no));
		}

		log.info(this.getClass().getName() + ".mainNews End!");
		return rList;
	}
	@RequestMapping(value = "GetTeamNews")
	@ResponseBody
	public List<Map<String, Object>> GetTeamNews(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		log.info(this.getClass().getName() + ".GetTeamNews start!");
		
		//팀 값을 넘겨주는 경우 그 팀에 관한 뉴스 표시, 넘겨주지 않는 경우 유저 정보의 최애팀 뉴스 표시
		String team = request.getParameter("team")==null ? session.getAttribute("favorite_team").toString() : request.getParameter("team");
		
		if (team.endsWith("FC")) {
			team = team.substring(0, team.lastIndexOf("FC") - 1).trim();
		}
		log.info("team :: " +team);
		
		
		String news = "_Sky_Sports";
		
		List<Map<String, Object>> rList = newsService.getTeamNews(team, news);
		
		news = "_The_Guardian";
		
		rList.addAll(newsService.getTeamNews(team, news));
		
		log.info(this.getClass().getName() + ".GetTeamNews End!");
		return rList;
	}
	
	
}
