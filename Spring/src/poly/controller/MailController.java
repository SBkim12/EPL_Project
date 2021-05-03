package poly.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.dto.MemberDTO;
import poly.service.IMailService;
import poly.service.IUserService;
import poly.util.EncryptUtil;

@Controller
public class MailController {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "MailService")
	IMailService mailService;
	
	@Resource(name = "UserService")
	IUserService userService;


	// 랜덤 변수 생성
	public String RandomNum() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			int n = (int) (Math.random() * 10);
			buffer.append(n);

		}
		return buffer.toString();
	}

	// 이메일 인증
	@ResponseBody
	@RequestMapping(value = "sendNewPwdProc", method = RequestMethod.POST)
	public int sendNewPwdProc(HttpServletRequest request, HttpSession session) throws Exception {
		log.info("sendNewPwdProc 시작");

		int result = 0;
		String email = request.getParameter("email");
		log.info("email : " + email);
		String newpwd = "";

		newpwd = RandomNum();
		log.info("authNum : " + newpwd);

		MemberDTO uDTO = new MemberDTO();
		uDTO.setMember_id(email);
		uDTO.setMember_pwd(newpwd);
		log.info("setUser_email : " + uDTO.getMember_id());
		log.info("setUser_newPWD : " + uDTO.getMember_pwd());
		
		MemberDTO idCheck = userService.idCheck(uDTO.getMember_id());
		
		if(idCheck==null) {
			log.info("등록되지 않은 아이디");
			return 0;
		}
		
		int res = mailService.doSendMail(uDTO);

		if (res == 1) {
			log.info(this.getClass().getName() + "mail.sendMail success");
			result = 1;
		} else {
			log.info(this.getClass().getName() + "mail.sendMail fail");
			result = 2;
		}
		
		log.info("비밀 번호 암호화");
		String HashEnc = EncryptUtil.enHashSHA256(newpwd);
		uDTO.setMember_pwd(HashEnc);
		
		log.info("change pwd 시작");
		int res2 = userService.changePwd(uDTO);
		log.info("change pwd 종료");
		log.info("res2 : " + res2);

		if (res2 == 1) {
			log.info("update success");
		} else {
			log.info("update fail");
			return 3;
		}

		log.info("sendNewPwdProc 종료");
		return result;
	}

}
