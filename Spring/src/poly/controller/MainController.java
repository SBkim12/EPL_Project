package poly.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.dto.MemberDTO;
import poly.service.IUserService;

@Controller
public class MainController {

	private Logger log = Logger.getLogger(this.getClass());

	
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
				
				session.invalidate();
			} else {
				log.info("relogin start");
				
				log.info("uDTO.Member_id : " + uDTO.getMember_id());
				log.info("uDTO.Member_name : " + uDTO.getMember_name());

				
				session.setAttribute("member_id", uDTO.getMember_id());
				session.setAttribute("favorite_team", uDTO.getFavorite_team());
				session.setAttribute("member_name", uDTO.getMember_name());
				session.setAttribute("member_point",uDTO.getMember_point());
				
				return "/main/home";
			}

			url = "/index.do";
			
			model.addAttribute("msg", msg);
			model.addAttribute("url", url);

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
		log.info("homse start");


		log.info("test");
		return "/main/home";
	}
}
