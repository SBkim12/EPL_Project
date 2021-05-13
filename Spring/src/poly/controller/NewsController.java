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
import poly.service.INewsService;

@Controller
public class NewsController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "EPLdataService")
	IEPLdataService epldataService;
	
	@Resource(name = "NewsService")
	INewsService newsService;
	
	
	@RequestMapping(value = "newsUpdate")
	@ResponseBody
	public String newsUpdate(HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		log.info(this.getClass().getName() + ".newsUpdate start!");
		
		String answer = "";
		
		
		List<EPLDTO> rList = new ArrayList<EPLDTO>();
		
		rList = epldataService.getEPLteam();
		
		log.info("SkytSports 뉴스 웹 크롤링 시작");
		int res = newsService.skySportsNewsUpdate(rList);
		
		
		if(res>0) {
			log.info("SkySports 뉴스 크롤링 성공");
			answer = "스카이 스포츠 성공 //";
		}else {
			log.info("SkySports 뉴스 크롤링 실패");
			answer = "스카이 스포츠 실패 //";
		}
		
		
//		log.info("The Guardian 뉴스 웹 크롤링 시작");
//		int res1 = newsService.theGuardianNewsUpdate();
		
		
		log.info(this.getClass().getName() + ".newsUpdate end!!");
		return answer;
	}
	
	
	
}
