package com.trip.finalProject.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.trip.finalProject.login.service.MemberVO;

public class CustomUser implements UserDetails {

	private MemberVO member;
	
	public CustomUser(MemberVO member) {
		this.member = member;
	}
	
	public MemberVO getMemberInfo() {
		return this.member;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auth = new ArrayList<>();
		// 다른건 아니고, 권한 정보를 유출시키지 않기 위해 이를 위해 미리 만들어진 SimpleGrantedAuthority 라는 객체 안에 감싸서 담아버림
		// 주의 할 점 : 만약 우리가 부여한 권한이 여러개라면, 반복문을 돌려서 그 객체안에 넣어줘야함 
		auth.add(new SimpleGrantedAuthority(member.getAuthority()));
		return auth;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.member.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.member.getMemberId();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
