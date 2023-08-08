package com.trip.finalProject.member.service;

import lombok.Data;

@Data
public class MemberVO {
	private String id;
	private String memberEmail;
	private String memberPassword;
	private String memberName;
	private Integer memberAge;
	private String memberMobile;
	private String memberGrade;
	private String profileImage;
	private String birthday;
	private String gender;
	
	//로그인 성공 여부를 나타내는 필드
	private boolean loginSuccess;
}
