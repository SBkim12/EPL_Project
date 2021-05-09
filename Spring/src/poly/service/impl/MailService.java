package poly.service.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import poly.dto.MemberDTO;
import poly.service.IMailService;
import poly.util.CmmUtil;
import poly.util.EmailUtil;

@Service("MailService")
public class MailService implements IMailService{

	private Logger log = Logger.getLogger(this.getClass());
	
	final String host = EmailUtil.host; //smtp
	final String user = EmailUtil.user;
	final String password = EmailUtil.password; 
	
	@Override
	public int doSendMail(MemberDTO uDTO)throws Exception {
		log.info("Service.doSendMail 시작");
		
		int res = 1;
		
		if(uDTO == null) {
			uDTO = new MemberDTO();
		}
		
		String Email = CmmUtil.nvl(uDTO.getMember_id());
		
		log.info(Email);
		
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		// props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(Email));
			
			message.setSubject("My Favorite EPL 임시 비밀번호 발급");
			message.setText("임시 비밀번호는 " + uDTO.getMember_pwd() + " 입니다.");
			
			Transport.send(message);
			
		} catch (MessagingException e){
			res = 0;
			log.info("[ERROR]" + this.getClass().getName() + ".doSendMail : " + e);
		} catch (Exception e){
			res = 0;
			log.info("[ERROR]" + this.getClass().getName() + ".doSendMail : " + e);
		}
		
		
		log.info("Service.doSendMail 종료");
		return res;
	}
	
	
}
