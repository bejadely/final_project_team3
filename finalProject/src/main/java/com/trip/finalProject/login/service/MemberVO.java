package com.trip.finalProject.login.service;












import lombok.Data;

@Data
public class MemberVO {
	private String id;
	private String to;
	
	private String memberId;
	private String memberName;
	private String password;
	private String nationality;
	private String email;
	private String zipCode;
	private String addr;
	private String addressDetail;
	//추가된 부분 23.08.23(김재운)
	private String phoneNumber;
	private String authority;
	private String originProfileImg;
	private String savedProfileImg;
	private String bankName;
	private String loginType;
	private String accessToken;
	private String refreshToken;
	private String birthDate;
	private String gender;
	private String punishCount;
	//로그인 성공 여부를 나타내는 필드
	private boolean loginSuccess;
}
