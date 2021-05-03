package poly.service;

import poly.dto.MemberDTO;

public interface IMailService {

	int doSendMail(MemberDTO mDTO);
}
