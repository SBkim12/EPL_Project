package poly.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import poly.dto.MemberDTO;
import poly.persistance.mapper.IUserMapper;
import poly.service.IUserService;

@Service("UserService")
public class UserService implements IUserService{
	
	@Resource(name="UserMapper")
	private IUserMapper userMapper;

	@Override
	public MemberDTO getLoginInfo(MemberDTO uDTO) {
		return userMapper.getLoginInfo(uDTO);
	}

	@Override
	public MemberDTO idCheck(String memberId) {
		return userMapper.idCheck(memberId);
	}

	@Override
	public int UserSignUp(MemberDTO tDTO) {
		return userMapper.userSignUp(tDTO);
	}

	@Override
	public MemberDTO reLoginInfo(MemberDTO uDTO) {
		
		return userMapper.reLoginInfo(uDTO);
	}

	@Override
	public int changePwd(MemberDTO uDTO) {
		// TODO Auto-generated method stub
		return userMapper.changePwd(uDTO);
	}

	@Override
	public MemberDTO pwdCheck(MemberDTO uDTO) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.pwdCheck(uDTO);
	}

	@Override
	public int ChangeMyteam(MemberDTO uDTO) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.ChangeMyteam(uDTO);
	}

	@Override
	public int ChangeMyName(MemberDTO uDTO) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.ChangeMyName(uDTO);
	}

	@Override
	public int UserPointUpdate(MemberDTO uDTO) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.UserPointUpdate(uDTO);
	}


	
}
