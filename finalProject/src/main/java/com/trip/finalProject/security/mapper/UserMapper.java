package com.trip.finalProject.security.mapper;

import com.trip.finalProject.login.service.MemberVO;

public interface UserMapper {
	public MemberVO getMember(String username);
}
