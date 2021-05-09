package poly.controller;

import static poly.util.CmmUtil.nvl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import poly.dto.EPLDTO;
import poly.dto.MemberDTO;
import poly.service.IEPLdataService;
import poly.service.IUserService;
import poly.util.EncryptUtil;

@Controller
public class UserController {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "UserService")
	IUserService userService;
	
	@Resource(name = "EPLdataService")
	IEPLdataService epldataService;

	// 로그인 진행
	@RequestMapping(value = "/LoginProc")
	public String LoginProc(HttpServletRequest request, Model model, HttpSession session) throws Exception {

		log.info("LoginProc start");
		String id = nvl(request.getParameter("id"));
		String pwd = nvl(request.getParameter("pwd"));

		log.info("id :" + id);
		log.info("pwd :" + pwd);

		String HashEnc = EncryptUtil.enHashSHA256(pwd);

		MemberDTO uDTO = new MemberDTO();

		uDTO.setMember_id(id);
		uDTO.setMember_pwd(HashEnc);
		
		log.info("userService start");
		uDTO = userService.getLoginInfo(uDTO);
		log.info("uDTO null? : " + (uDTO == null));

		String msg = "";
		String url = "";
		if (uDTO == null) {
			msg = "아이디 비밀번호를 확인하세요";
		} else {
			
			log.info("uDTO.Member_id : " + uDTO.getMember_id());
			log.info("uDTO.Member_name : " + uDTO.getMember_name());

			
			session.setAttribute("member_id", uDTO.getMember_id());
			session.setAttribute("member_name", uDTO.getMember_name());
			
			model.addAttribute("member_id", uDTO.getMember_id());
			model.addAttribute("favorite_team", uDTO.getFavorite_team());
			model.addAttribute("member_name", uDTO.getMember_name());
			model.addAttribute("member_point",uDTO.getMember_point());
			
			return "/main/home";
		}

		url = "/index.do";
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);

		log.info("The/TheLoginProc end");

		return "/redirect";

	}

	// 로그아웃
	@RequestMapping(value = "Logout")
	public String TheLogout(HttpSession session, Model model) throws Exception {

		log.info("Logout start");
		
		session.invalidate();

		log.info("Logout End");

		return "/basic/login";
	}

	// 회원가입
	@RequestMapping(value = "SignUp")
	public String SignUp(HttpServletRequest request, ModelMap model)throws Exception {

		log.info("TheSignUp Start");
		
		
		List<EPLDTO> rList = new ArrayList<EPLDTO>();
		
		rList = epldataService.getEPLteam();
		
		if(rList.size()>0) {
			log.info("현재 시즌 팀 목록 갯수 :: " + rList.size());
		}else {
			log.info("팀 목록 가져오기 실패");
		}
		
		model.addAttribute("teams", rList);
		EPLDTO pDTO = new EPLDTO();
		for(int i=0; i<rList.size(); i++) {
			pDTO = rList.get(i);
			log.info(pDTO.getKo_name());
			pDTO = null;
		}
		
		
		log.info("TheSignUp End");

		return "/basic/signup";
	}

	// 회원가입 진행
	@RequestMapping(value = "SignUpProc", method = RequestMethod.POST)
	public String TheSignUpProc(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {

		log.info("/The/TheSignUpProc start");

		String member_id = request.getParameter("id");
		String member_pwd = nvl(request.getParameter("pwd"));
		String member_name = request.getParameter("name");
		String favorite_team = request.getParameter("favorite_team");

		log.info("user_id : " + member_id);
		log.info("user_pwd : " + member_pwd);
		log.info("user_name : " + member_name);
		log.info("favorite_team : " + favorite_team);

		String HashEnc = EncryptUtil.enHashSHA256(member_pwd);

		MemberDTO tDTO = new MemberDTO();
		log.info("tDTO.set start");
		tDTO.setMember_id(member_id);
		tDTO.setMember_pwd(HashEnc);
		tDTO.setMember_name(member_name);
		tDTO.setFavorite_team(favorite_team);
		
		log.info("tDTO.set end");
		
		log.info("sessionSet user_id : " + session.getAttribute("member_id"));

		log.info("UserServic.SignUp start");
		int res = userService.UserSignUp(tDTO);
		log.info("UserService.SignUp end");
		log.info("res : " + res);

		String msg;
		String url = "index.do";

		if (res > 0) {
			msg = "회원 가입 완료";
			session.setAttribute("member_id", tDTO.getMember_id());
		} else {
			msg = "회원 가입 실패";
		}

		log.info("model.addAttribute start");
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		log.info("model.addAttribute end");

		log.info("TheSignUpProc end");

		return "/redirect";
	}

	// ID Check
	@ResponseBody
	@RequestMapping(value = "/idCheck", method = RequestMethod.POST)
	public int idCheck(HttpServletRequest request) throws Exception {
		log.info("idCheck start");

		String memberId = request.getParameter("memberId");

		log.info("userService.idCheck Start : " + memberId);
		MemberDTO idCheck = userService.idCheck(memberId);
		log.info("userService.idCheck End");

		int res = 0;

		log.info("id 확인");
		if (idCheck != null) {
			log.info("값이 있음");
			res = 1;
		}
		log.info("result : " + res);

		log.info("idCheck end");
		return res;
	}


	@RequestMapping("/callback")
	public ModelAndView callback() {
		String message = "Simple Callback Page";
		return new ModelAndView("callback", "message", message);
	}

}
