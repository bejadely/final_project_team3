package com.trip.finalProject.login.service;

import java.util.Date;

import lombok.Data;

@Data
public class MemberVO {
	private String memberId;
	private String memberName;
	private String password;
	private String nationailty;
	private String email;
	private String zipCode;
	private String address;
	private String addressDetail;
	private String phoneNumber;
	private String authority;
	private String originProfileImg;
	private String savedProfileImg;
	private String bankName;
	private String loginType;
	private String accessToken;
	private String refreshToken;
	private Date birthDate;
	private String gender;
	private String punishCount;
	//로그인 성공 여부를 나타내는 필드
	private boolean loginSuccess;
}
