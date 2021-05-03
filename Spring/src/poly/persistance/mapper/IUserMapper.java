package poly.persistance.mapper;

import config.Mapper;
import poly.dto.MemberDTO;

@Mapper("UserMapper")
public interface IUserMapper {

	MemberDTO getLoginInfo(MemberDTO uDTO);

	MemberDTO idCheck(String memberId);

	int userSignUp(MemberDTO tDTO);

	MemberDTO reLoginInfo(MemberDTO uDTO);

	int changePwd(MemberDTO uDTO);
	
}
