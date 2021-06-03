package poly.service;

import poly.dto.MemberDTO;

public interface IUserService {

	MemberDTO getLoginInfo(MemberDTO uDTO);
	
	MemberDTO idCheck(String memberId);

	int UserSignUp(MemberDTO tDTO);

	MemberDTO reLoginInfo(MemberDTO uDTO);

	int changePwd(MemberDTO uDTO);

	MemberDTO pwdCheck(MemberDTO uDTO)throws Exception;

	int ChangeMyteam(MemberDTO uDTO)throws Exception;

	int ChangeMyName(MemberDTO uDTO)throws Exception;
	
}
