package poly.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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

				
				session.setAttribute("member_id", uDTO.getMember_id());
				session.setAttribute("favorite_team", uDTO.getFavorite_team());
				session.setAttribute("member_name", uDTO.getMember_name());
				session.setAttribute("member_point",uDTO.getMember_point());
				
				url="/home.do";
				model.addAttribute("url", url);
				return "/noAlert_redirect";
			}
			
			

			log.info("index relogin end");

			return "/redirect";
		} else {
			session.invalidate();
		}

		log.info("login page open");
		return "/basic/login";
	}
	
	@RequestMapping(value = "home")
	public String Homepage(HttpSession session, ModelMap model) throws Exception {
		log.info("home start");

		if (session.getAttribute("member_id") != null) {

			MemberDTO uDTO = new MemberDTO();

			uDTO.setMember_id((String) session.getAttribute("member_id"));

			log.info("userService start");
			uDTO = userService.reLoginInfo(uDTO);
			log.info("uDTO null? : " + (uDTO == null));

			String msg = "";
			String url = "";
			if (uDTO == null) {
				msg = "아이디 비밀번호를 확인하세요";

				session.invalidate();
			} else {
				log.info("relogin start");

				log.info("uDTO.Member_id : " + uDTO.getMember_id());
				log.info("uDTO.Member_name : " + uDTO.getMember_name());

				session.setAttribute("member_id", uDTO.getMember_id());
				session.setAttribute("favorite_team", uDTO.getFavorite_team());
				session.setAttribute("member_name", uDTO.getMember_name());
				session.setAttribute("member_point", uDTO.getMember_point());
				
				String team = uDTO.getFavorite_team();
				
				log.info("team :: " +team);
				
				//각 뉴스사에서 가져올 뉴스 개수
				int no = 3;
				
				String news = "_Sky_Sports";

				List<Map<String, Object>> rList = newsService.getMainNews(team, news, no);
				
				List<EPLDTO> mList = epldataService.getEPLteam();
				
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
				
				model.addAttribute("mainNews", rList);
				
				model.addAttribute("teams", mList);
				
				log.info("mainHome end!!");
				return "/main/home";
			}

			url = "/index.do";

			model.addAttribute("msg", msg);
			model.addAttribute("url", url);

			log.info("index relogin end");

			return "/redirect";
		}
		
		log.info("home end");
		return "/basic/login";
	}
	
}
