package com.trip.finalProject.adminMember.service;


import lombok.Data;

@Data
public class AdminMemberVO {
	
	// 기본 필드
	
	private String memberId; // 회원 아이디
	private String memberName; // 회원명
	private String password; // 비밀번호 - 암호화
	private String nationality; // 국적
	private String email; // 이메일
	private String zipCode; // 우편번호
	private String address; // 도로명주소 - 메인
	private String addressDetail; // 도로명주소 - 상세
	private String phoneNumber; // 연락처
	private String authority; // 권한
	private String originProfileImg; // 원본 프로필 사진명
	private String savedProfileImg; // 저장 프로필 사진명
	private String bankName; // 은행이름
	private String accountNumber; // 계좌번호
	private String loginType; // 로그인타입 (카카오, 네이버, 일반 등)
	private String accessToken; // 엑세스 토큰(카카오 로그인용)
	private String refreshToken; // 리프레쉬 토큰(카카오 로그인용)
	private String birthDate; // 생년월일
	private String gender; // 성별 G1 남자 / G2 여자
	private Integer punishCount; // 제재 횟수 default = 0
	private String signUpDate; // 가입일자
	private String withdrawalDate; // 탈퇴처리일자
	
	// 내용 처리를 위한 필드 추가
	private String rejectReasonDetail;
	
	// 조건 검색을 위한 필드 추가
	private String searchBy;

}
