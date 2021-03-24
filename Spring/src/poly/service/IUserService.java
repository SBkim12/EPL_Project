package poly.service;

import poly.dto.MemberDTO;

public interface IUserService {

	MemberDTO getLoginInfo(MemberDTO uDTO);
	
	MemberDTO idCheck(String memberId);

	int UserSignUp(MemberDTO tDTO);
	
}
