package com.trip.finalProject.login.service.Impl;


import javax.servlet.http.HttpSession;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trip.finalProject.login.mapper.MemberMapper;
import com.trip.finalProject.login.service.MemberService;
import com.trip.finalProject.login.service.MemberVO;
import com.trip.finalProject.security.service.AesProcessor;


@Service //해당 클래스를 스프링의 서비스 빈으로 등록하
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberMapper memberMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AesProcessor aesProcessor;
	
	//일반회원 회원가입
	@Override
	public String insertMemberInfo(MemberVO memberVO) {
		
		// 0907 창민 추가
		// 비밀번호 암호화
		String encodePassword = passwordEncoder.encode(memberVO.getPassword());
		
		// 암호화된 키를 memberVO에 재저장
		memberVO.setPassword(encodePassword);
		
		int result = memberMapper.insertMember(memberVO);
		if(result ==1) {
			return memberVO.getMemberId();
		}else {
			return null;
		}
	}
	
	//가이드회원 회원가입
	@Override
	public String insertGuide(MemberVO memberVO) {
		
		//0907 창민 추가
		// 비밀번호 암호화
		String encodePassword = passwordEncoder.encode(memberVO.getPassword());
		
		// 암호화된 키를 memberVO에 재저장
		memberVO.setPassword(encodePassword);
		
		// 계좌번호 암호화
		String encodedAccountNum = "";
		
		try {
			encodedAccountNum = aesProcessor.aesCBCEncode(memberVO.getAccountNumber());
			memberVO.setAccountNumber(encodedAccountNum);  
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		int result = memberMapper.insertGuide(memberVO);
		if(result ==1) {
			return memberVO.getMemberId();
		}else {
			return null;
		}
	}
	

	
	
	// 로그인 기능 수행
	 @Override	
	    public MemberVO login(MemberVO memberVO) {
		 //Mapper.xml의 select문인 login의 결과가 없으면  빈값이나 null을 가져옴
		 //MemberVO 타입으로 반환
		 MemberVO result = memberMapper.login(memberVO); 
		 
		 return result;
	    }

	 
	 @Override
	public MemberVO memberSelect(MemberVO vo) {
		return memberMapper.memberselect(vo);
	}
	 
	@Override
	public void logout(HttpSession session) {
	 session.invalidate(); // 세션 초기화
	 }
	
	//회원가입시 아이디 중복체크
	 @Override
		public Integer checkId(MemberVO vo) {
		
		 Integer result = memberMapper.checkId(vo);
		return result; 
			
		}
	 
		//로그인시 계정 유무 체크
	 @Override
		public Integer loginAccountCheck(MemberVO vo) {
		
		 Integer result = memberMapper.checkId(vo);
		return result; 
			
		}
	 	
	 //휴대폰 번호로 아이디 찾기

	 @Override
		public MemberVO phoneNumberCheck(String num) {
			MemberVO result = memberMapper.checkIdByPhoneNumber(num);
			return result;
	} 
	 
	 //비밀번호 찾기 중 비밀번호 수정하기
	 
	 @Override
	 public int editPassword(MemberVO vo) {
		
			int result = memberMapper.editPassword(vo);
			return result;
		 
	 }
	 
	 
		
		//회원정보 불러이기
		@Override
		public MemberVO memberInfo(MemberVO memberVO) {
			// 회원 상세 조회
			String decodedAccountNum = "";
			System.out.println("first : " + memberVO.getAccountNumber());
			//계좌번호 복호화
			try {
				memberVO = memberMapper.memebrInfo(memberVO);
				decodedAccountNum = aesProcessor.aesCBCDecode(memberVO.getAccountNumber());
				memberVO.setAccountNumber(decodedAccountNum);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("second : " + memberVO.getAccountNumber());
			
			return memberVO;
		}

		//회원정보 수정
		@Override
		public String updateMember(MemberVO memberVO) {
			System.out.println("memberPas1 : " + memberVO.getPassword());
			try {
				memberVO.setAccountNumber(aesProcessor.aesCBCEncode(memberVO.getAccountNumber()));
				if (memberVO.getPassword() != null && !memberVO.getPassword().equals("")) {
					memberVO.setPassword(aesProcessor.aesCBCEncode(memberVO.getPassword()));
					System.out.println("memberPas2 : " + memberVO.getPassword());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			int result = memberMapper.updateMember(memberVO);
			
			if(result > 1) {
				return "success";
			} else {
				return "fail";
			}
		}

		

		@Override
		public MemberVO singleLogin(MemberVO vo) {
			// 스프링시큐어리티 session담기 위한 정보 조회
			
			return memberMapper.singleLogin(vo);
		}

		@Override
		public String passwordVerify(MemberVO memberVO) {
			
			// 비교할 암호 암호화 //fldkjsklfj
			String plainPassword = memberVO.getPassword();
			MemberVO result = memberMapper.passwordVerify(memberVO);
			
			boolean a = passwordEncoder.matches(plainPassword, result.getPassword());
			if(a == true) {
				return "true";
			}else {
				return "false";
			}
			
			
//			// 비교할 회원 정보 DB에서 호출 // flsddjsfdkj
//			
//			System.out.println("password 1: " + encodePassword);
//			System.out.println("password 2: " + result.getPassword());
//			
//			// 호출한 회원정보와 비교할 암호화된 암호 비교
//			if(result.getPassword().equals(encodePassword)) {
//				return "success";
//			} else {
//				return "fail";
//			}
			
//			if(result == 1) {
//				return "success";
//			} else {
//				return "fail";
//			}
		}
		
		
		
	


}
