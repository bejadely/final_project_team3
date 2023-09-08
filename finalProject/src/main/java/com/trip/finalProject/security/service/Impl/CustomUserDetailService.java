package com.trip.finalProject.security.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trip.finalProject.login.service.MemberVO;
import com.trip.finalProject.security.mapper.UserMapper;
import com.trip.finalProject.security.service.CustomUser;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		// 들어온 유저 아이디를 바탕으로 해당 아이기다 있는지 없는지 DB 에 접근해서 체크
		MemberVO vo = userMapper.getMember(username);
		
		// 만약 널이 아니라면 MemberVO를 넘겨주는게 아니라 이를 CustomUser로 감싸서(변환시켜서) 넘겨줘야 데이터 유출이 방지됨
		return vo == null ? null : new CustomUser(vo);
	}

}
