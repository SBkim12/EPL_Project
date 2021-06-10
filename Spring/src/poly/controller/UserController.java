package poly.controller;

import static poly.util.CmmUtil.nvl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import poly.util.CmmUtil;
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
			
			url = "/index.do";
			model.addAttribute("url", url);
		} else {
			
			log.info("uDTO.Member_id : " + uDTO.getMember_id());
			log.info("uDTO.Member_name : " + uDTO.getMember_name());
			log.info("uDTO.favorite_team : " + uDTO.getFavorite_team());
			log.info("uDTO.member_point : " + uDTO.getMember_point());
			log.info("uDTO.getTeam_logo : " + uDTO.getTeam_logo());
			
			session.setAttribute("member_id", uDTO.getMember_id());
			session.setAttribute("member_name", uDTO.getMember_name());
			session.setAttribute("favorite_team", uDTO.getFavorite_team());
			session.setAttribute("member_point",uDTO.getMember_point());
			session.setAttribute("team_logo",uDTO.getTeam_logo());
			
			url = "/home.do";
			model.addAttribute("url", url);
			
			return "/noAlert_redirect";
		}
		
		model.addAttribute("msg", msg);

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

	// 회원가입 페이지 이동
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
		
		EPLDTO team = epldataService.getTeamLogo(favorite_team);
		
		String team_logo = team.getLogo();
		
		log.info("user_id : " + member_id);
		log.info("user_pwd : " + member_pwd);
		log.info("user_name : " + member_name);
		log.info("favorite_team : " + favorite_team);
		log.info("team_logo : " + team_logo);
		
		String HashEnc = EncryptUtil.enHashSHA256(member_pwd);

		MemberDTO tDTO = new MemberDTO();
		log.info("tDTO.set start");
		tDTO.setMember_id(member_id);
		tDTO.setMember_pwd(HashEnc);
		tDTO.setMember_name(member_name);
		tDTO.setFavorite_team(favorite_team);
		tDTO.setTeam_logo(team_logo);
		
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
			session.setAttribute("member_id", member_id);
			session.setAttribute("member_name", member_name);
			session.setAttribute("favorite_team", favorite_team);
			session.setAttribute("team_logo", team_logo);
			session.setAttribute("member_point", 0);
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
	
	// PWD Check
	@RequestMapping(value = "/pwdCheck", method = RequestMethod.POST)
	@ResponseBody
	public int pwdCheck(HttpServletRequest request,  HttpSession session) throws Exception {
		log.info("pwdCheck start");
		MemberDTO uDTO = new MemberDTO();
		
		String member_pwd = request.getParameter("member_pwd");
		String member_id =  session.getAttribute("member_id").toString();
		
		log.info("비밀 번호 암호화");
		String HashEnc = EncryptUtil.enHashSHA256(member_pwd);
		uDTO.setMember_pwd(HashEnc);
		uDTO.setMember_id(member_id);
		
		log.info("userService.pwdCheck Start");
		MemberDTO pwdCheck = userService.pwdCheck(uDTO);
		log.info("userService.pwdCheck End");
		
		int res = 0;

		log.info("id 확인");
		if (pwdCheck != null) {
			log.info("값이 있음");
			res = 1;
		}
		log.info("result : " + res);

		log.info("pwdCheck end");
		return res;
	}
	
	// PWD Change
	@RequestMapping(value = "/changePwd", method = RequestMethod.POST)
	@ResponseBody
	public int changePwd(HttpServletRequest request, HttpSession session) throws Exception {
		log.info("changePwd start");
		
		MemberDTO uDTO = new MemberDTO();
		
		String member_pwd = request.getParameter("member_pwd");
		String member_id =  session.getAttribute("member_id").toString();
		
		log.info("비밀 번호 암호화");
		String HashEnc = EncryptUtil.enHashSHA256(member_pwd);
		uDTO.setMember_pwd(HashEnc);
		uDTO.setMember_id(member_id);
		
		log.info("userService.changePwd Start");
		int res = userService.changePwd(uDTO);
		log.info("userService.changePwd End");

		if (res != 0) {
			log.info("비밀번호 변경 성공");
			res = 1;
		}
		log.info("result : " + res);

		log.info("changePwd end");
		return res;
	}
	// ChangeMyteam
	@RequestMapping(value = "/ChangeMyteam", method = RequestMethod.POST)
	@ResponseBody
	public int ChangeMyteam(HttpServletRequest request, HttpSession session) throws Exception {
		log.info("ChangeMyteam start");
		
		MemberDTO uDTO = new MemberDTO();
		
		String favorite_team = request.getParameter("favorite_team");
		String member_id =  session.getAttribute("member_id").toString();
		EPLDTO team = epldataService.getTeamLogo(favorite_team);
		String team_logo = team.getLogo();
		
		uDTO.setMember_id(member_id);
		uDTO.setFavorite_team(favorite_team);
		uDTO.setTeam_logo(team_logo);
		
		log.info("userService.changePwd Start");
		int res = userService.ChangeMyteam(uDTO);
		log.info("userService.changePwd End");
		
		if (res != 0) {
			log.info("최애팀 변경 성공");
			res = 1;
			session.setAttribute("favorite_team", favorite_team);
			session.setAttribute("team_logo", team_logo);
		}
		log.info("result : " + res);
		
		log.info("ChangeMyteam end");
		return res;
	}
	// PWD Check
	@RequestMapping(value = "/ChangeMyName", method = RequestMethod.POST)
	@ResponseBody
	public int ChangeMyName(HttpServletRequest request, HttpSession session) throws Exception {
		log.info("ChangeMyName start");
		
		MemberDTO uDTO = new MemberDTO();
		
		String member_name = CmmUtil.nvl(request.getParameter("member_name"));
		String member_id = CmmUtil.nvl( session.getAttribute("member_id").toString());
		uDTO.setMember_id(member_id);
		uDTO.setMember_name(member_name);
		
		log.info("userService.ChangeMyName Start");
		int res = userService.ChangeMyName(uDTO);
		log.info("userService.ChangeMyName End");
		
		if (res != 0) {
			log.info("이름 변경 성공");
			res = 1;
			session.setAttribute("member_name", member_name);
		}
		log.info("result : " + res);
		
		log.info("ChangeMyName end");
		return res;
	}
	
	
		
	
	// 구글 로그인 진행
	@RequestMapping(value = "/GoogleLoginProc")
	@ResponseBody
	public String GoogleLoginProc(HttpServletRequest request, Model model, HttpSession session) throws Exception {

		log.info("GoogleLoginProc start");
		String member_id = nvl(request.getParameter("id"));
		String member_name = nvl(request.getParameter("name"));

		log.info("member_id :" + member_id);
		log.info("member_name :" + member_name);

		MemberDTO uDTO = new MemberDTO();

		uDTO.setMember_id(member_id);
		
		log.info("user data check");
		uDTO = userService.reLoginInfo(uDTO);
		log.info("uDTO null? : " + (uDTO == null));
		
		String result = "0";
		if (uDTO == null) {
			//구글 메일로 회원가입 진행
			
			List<EPLDTO> mList = epldataService.getEPLteam();
			session.setAttribute("teams", mList);
			
			EPLDTO rDTO = mList.get(0);
			String favorite_team = rDTO.getTeam_name();
			String team_logo = rDTO.getLogo();
			String HashEnc = EncryptUtil.enHashSHA256("");
			
			log.info(favorite_team);
			log.info(team_logo);
			
			uDTO = new MemberDTO();
			
			uDTO.setMember_id(member_id);
			uDTO.setFavorite_team(favorite_team);
			uDTO.setTeam_logo(team_logo);
			uDTO.setMember_name(member_name);
			uDTO.setMember_pwd(HashEnc);
			
			int res = userService.UserSignUp(uDTO);
			
			if(res==1) {
				session.setAttribute("member_id", uDTO.getMember_id());
				session.setAttribute("member_name", uDTO.getMember_name());
				session.setAttribute("favorite_team", uDTO.getFavorite_team());
				session.setAttribute("member_point", 0);
				session.setAttribute("team_logo",uDTO.getTeam_logo());
				
				result = "2";
			}else {
				result = "0";
			}
		} else {
			//구글 메일로 로그인 진행
			log.info("uDTO.Member_id : " + uDTO.getMember_id());
			log.info("uDTO.Member_name : " + uDTO.getMember_name());
			log.info("uDTO.favorite_team : " + uDTO.getFavorite_team());
			log.info("uDTO.member_point : " + uDTO.getMember_point());
			log.info("uDTO.getTeam_logo : " + uDTO.getTeam_logo());
			
			session.setAttribute("member_id", uDTO.getMember_id());
			session.setAttribute("member_name", uDTO.getMember_name());
			session.setAttribute("favorite_team", uDTO.getFavorite_team());
			session.setAttribute("member_point",uDTO.getMember_point());
			session.setAttribute("team_logo",uDTO.getTeam_logo());
			
			result="1";
		}
		
		log.info("GoogleLoginProc end");

		return result;

	}
	
	@RequestMapping("/callback")
	public ModelAndView callback() {
		String message = "Simple Callback Page";
		return new ModelAndView("callback", "message", message);
	}

}
