package poly.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	private Logger log = Logger.getLogger(this.getClass());

	@RequestMapping(value = "index")
	public String TheLogin(HttpSession session, ModelMap model) throws Exception {
		log.info("login start");

		if (session.getAttribute("member_id") != null) {

			return "/main/home";
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
