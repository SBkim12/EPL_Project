package poly.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.Document;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.dto.EPLDTO;
import poly.dto.MemberDTO;
import poly.service.IEPLdataService;
import poly.service.INewsService;
import poly.service.IUserService;

@Controller
public class MainController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "EPLdataService")
	IEPLdataService epldataService;
	
	@Resource(name = "NewsService")
	INewsService newsService;
	
	@Resource(name = "UserService")
	IUserService userService;
	
	@RequestMapping(value = "index")
	public String TheLogin(HttpSession session, ModelMap model) throws Exception {
		log.info("login start");

		//아이디값 가지고 있는지 확인
		if (session.getAttribute("member_id") != null) {
			
			MemberDTO uDTO = new MemberDTO();
			
			uDTO.setMember_id((String)session.getAttribute("member_id"));
			
			log.info("userService start");
			uDTO = userService.reLoginInfo(uDTO);
			log.info("uDTO null? : " + (uDTO == null));

			String msg = "";
			String url = "";
			if (uDTO == null) {
				msg = "아이디 비밀번호를 확인하세요";
				url = "/index.do";
				
				model.addAttribute("msg", msg);
				model.addAttribute("url", url);
				session.invalidate();
			} else {
				log.info("relogin start");
				
				log.info("uDTO.Member_id : " + uDTO.getMember_id());
				log.info("uDTO.Member_name : " + uDTO.getMember_name());
				log.info("uDTO.favorite_team : " + uDTO.getFavorite_team());
				log.info("uDTO.member_point : " + uDTO.getMember_point());
				log.info("uDTO.member_point : " + uDTO.getTeam_logo());
				
				session.setAttribute("member_id", uDTO.getMember_id());
				session.setAttribute("favorite_team", uDTO.getFavorite_team());
				session.setAttribute("member_name", uDTO.getMember_name());
				session.setAttribute("member_point",uDTO.getMember_point());
				session.setAttribute("team_logo", uDTO.getTeam_logo());
				
				url="/home.do";
				model.addAttribute("url", url);
				return "/noAlert_redirect";
			}
			

			log.info("index relogin end");

			return "/redirect";
		} else {
			//아이디값 없으면 session 값 모두 초기화
			session.invalidate();
		}

		log.info("login page open");
		
		return "/basic/login";
	}
	
	@RequestMapping(value = "home")
	public String Homepage(HttpSession session, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		log.info("home start");
		
		
		String id = (String) session.getAttribute("member_id");
		//아이디 session에 없을 경우 로그인 페이지 이동
		if(id == null) {
			session.invalidate();
			
			String msg = "아이디 비밀번호를 확인하세요";
			String url = "/index.do";
			
			model.addAttribute("msg", msg);
			model.addAttribute("url", url);
			
			return "/redirect";
		}
		
		//home을 열기 위한 session 값이 없을시 값 불러오기
		if(session.getAttribute("favorite_team") == null) {

			MemberDTO uDTO = new MemberDTO();

			uDTO.setMember_id((String) session.getAttribute("member_id"));
			
			log.info("userService start");
			uDTO = userService.reLoginInfo(uDTO);
			log.info("uDTO null? : " + (uDTO == null));
			
			//초기화 후
			session.invalidate();
			
			log.info("uDTO.Member_id : " + uDTO.getMember_id());
			log.info("uDTO.Member_name : " + uDTO.getMember_name());
			log.info("uDTO.favorite_team : " + uDTO.getFavorite_team());
			log.info("uDTO.member_point : " + uDTO.getMember_point());
			log.info("uDTO.member_point : " + uDTO.getTeam_logo());
			
			//재입력
			session.setAttribute("member_id", uDTO.getMember_id());
			session.setAttribute("favorite_team", uDTO.getFavorite_team());
			session.setAttribute("member_name", uDTO.getMember_name());
			session.setAttribute("member_point", uDTO.getMember_point());
			session.setAttribute("team_logo", uDTO.getTeam_logo());
			
		}
		
		//상단 메뉴 및 순위에 필요한 현재 시즌 팀 순위 및 팀 리스트 가져와서 세션에 저장
		String team = (String) session.getAttribute("favorite_team");
		List<EPLDTO> mList = epldataService.getEPLteam();
		session.setAttribute("teams", mList);
		
		
		// 홈에서 보여줄 메인 뉴스 가져오기
		log.info("team :: " +team);
		//뉴스 개수
		int no = 3;
		//SkySports 가져오기
		String news = "_Sky_Sports";
		List<Map<String, Object>> rList = newsService.getMainNews(team, news, no);
		//TheGuardian 가져오기
		news = "_The_Guardian";
		rList.addAll(newsService.getMainNews(team, news, no));
		//현재시즌 아닌팀을 불러 왔을 경우 현재시즌 1등팀 뉴스 가져오기
		if(rList.size()<1) {
			//1등팀 가져오기
			EPLDTO data = mList.get(0);
			team = data.getName();
			//다시 뉴스 검색
			news = "_Sky_Sports";
			rList = newsService.getMainNews(team, news, no);
			news = "_The_Guardian";
			rList.addAll(newsService.getMainNews(team, news, no));
		}
		//모델에 저장
		model.addAttribute("mainNews", rList);
		
		
		log.info("home end");
		
		return "/main/home";
	}
	
	@RequestMapping(value = "/team")
	public String team(HttpSession session, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		log.info("team start");
		
		String selected_team = request.getParameter("team_name");
		String selected_team_logo = request.getParameter("team_logo");
		
		log.info("selected_team :: " +selected_team);
		model.addAttribute("selected_team", selected_team);
		model.addAttribute("selected_team_logo", selected_team_logo);
		
		String id = (String) session.getAttribute("member_id");
		//아이디 session에 없을 경우 로그인 페이지 이동
		if(id == null) {
			session.invalidate();
			
			String msg = "아이디 비밀번호를 확인하세요";
			String url = "/index.do";
			
			model.addAttribute("msg", msg);
			model.addAttribute("url", url);
			
			return "/redirect";
		}
		
		//home을 열기 위한 session 값이 없을시 값 불러오기
		if(session.getAttribute("favorite_team") == null) {

			MemberDTO uDTO = new MemberDTO();

			uDTO.setMember_id((String) session.getAttribute("member_id"));
			
			log.info("userService start");
			uDTO = userService.reLoginInfo(uDTO);
			log.info("uDTO null? : " + (uDTO == null));
			
			//초기화 후
			session.invalidate();
			
			log.info("uDTO.Member_id : " + uDTO.getMember_id());
			log.info("uDTO.Member_name : " + uDTO.getMember_name());
			log.info("uDTO.favorite_team : " + uDTO.getFavorite_team());
			log.info("uDTO.member_point : " + uDTO.getMember_point());
			log.info("uDTO.member_point : " + uDTO.getTeam_logo());
			
			//재입력
			session.setAttribute("member_id", uDTO.getMember_id());
			session.setAttribute("favorite_team", uDTO.getFavorite_team());
			session.setAttribute("member_name", uDTO.getMember_name());
			session.setAttribute("member_point", uDTO.getMember_point());
			session.setAttribute("team_logo", uDTO.getTeam_logo());
			
		}
		
		//메뉴 팀 리스트 값 없을시
		if(session.getAttribute("teams")==null) {
			List<EPLDTO> mList = epldataService.getEPLteam();
			session.setAttribute("teams", mList);
		}
		
		
		
		log.info("team end");
		
		return "/main/team";
	}
	
	@RequestMapping(value = "/news")
	public String news(HttpSession session, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		log.info("news start");
		
		String id = (String) session.getAttribute("member_id");
		
		//아이디 session에 없을 경우 로그인 페이지 이동
		if(id == null) {
			session.invalidate();
			
			String msg = "아이디 비밀번호를 확인하세요";
			String url = "/index.do";
			
			model.addAttribute("msg", msg);
			model.addAttribute("url", url);
			
			return "/redirect";
		}
		
		
		//home을 열기 위한 session 값이 없을시 값 불러오기
		if(session.getAttribute("favorite_team") == null) {

			MemberDTO uDTO = new MemberDTO();

			uDTO.setMember_id((String) session.getAttribute("member_id"));
			
			log.info("userService start");
			uDTO = userService.reLoginInfo(uDTO);
			log.info("uDTO null? : " + (uDTO == null));
			
			//초기화 후
			session.invalidate();
			
			log.info("uDTO.Member_id : " + uDTO.getMember_id());
			log.info("uDTO.Member_name : " + uDTO.getMember_name());
			log.info("uDTO.favorite_team : " + uDTO.getFavorite_team());
			log.info("uDTO.member_point : " + uDTO.getMember_point());
			log.info("uDTO.member_point : " + uDTO.getTeam_logo());
			
			//재입력
			session.setAttribute("member_id", uDTO.getMember_id());
			session.setAttribute("favorite_team", uDTO.getFavorite_team());
			session.setAttribute("member_name", uDTO.getMember_name());
			session.setAttribute("member_point", uDTO.getMember_point());
			session.setAttribute("team_logo", uDTO.getTeam_logo());
			
		}
		
		//메뉴 팀 리스트 값 없을시
		if(session.getAttribute("teams")==null) {
			List<EPLDTO> mList = epldataService.getEPLteam();
			session.setAttribute("teams", mList);
		}

		
		log.info("news end");
		
		return "/main/news";
	}
	
	
	
	@RequestMapping(value = "/predict_res")
	public String predict(HttpSession session, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		log.info("predict start");
		
		String id = (String) session.getAttribute("member_id");
		
		//아이디 session에 없을 경우 로그인 페이지 이동
		if(id == null) {
			session.invalidate();
			
			String msg = "아이디 비밀번호를 확인하세요";
			String url = "/index.do";
			
			model.addAttribute("msg", msg);
			model.addAttribute("url", url);
			
			return "/redirect";
		}
		
		
		//home을 열기 위한 session 값이 없을시 값 불러오기
		if(session.getAttribute("favorite_team") == null) {

			MemberDTO uDTO = new MemberDTO();

			uDTO.setMember_id((String) session.getAttribute("member_id"));
			
			log.info("userService start");
			uDTO = userService.reLoginInfo(uDTO);
			log.info("uDTO null? : " + (uDTO == null));
			
			//초기화 후
			session.invalidate();
			
			log.info("uDTO.Member_id : " + uDTO.getMember_id());
			log.info("uDTO.Member_name : " + uDTO.getMember_name());
			log.info("uDTO.favorite_team : " + uDTO.getFavorite_team());
			log.info("uDTO.member_point : " + uDTO.getMember_point());
			log.info("uDTO.member_point : " + uDTO.getTeam_logo());
			
			//재입력
			session.setAttribute("member_id", uDTO.getMember_id());
			session.setAttribute("favorite_team", uDTO.getFavorite_team());
			session.setAttribute("member_name", uDTO.getMember_name());
			session.setAttribute("member_point", uDTO.getMember_point());
			session.setAttribute("team_logo", uDTO.getTeam_logo());
			
		}
		
		//메뉴 팀 리스트 값 없을시
		if(session.getAttribute("teams")==null) {
			List<EPLDTO> mList = epldataService.getEPLteam();
			session.setAttribute("teams", mList);
		}

		log.info("predict end");
		
		return "/main/Predict_res";
	}
	
}
