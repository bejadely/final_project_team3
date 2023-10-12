package com.trip.finalProject.adminMember.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.adminMember.mapper.AdminMemberMapper;
import com.trip.finalProject.adminMember.service.AdminMemberService;
import com.trip.finalProject.adminMember.service.AdminMemberVO;
import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.report.service.ReportVO;
import com.trip.finalProject.security.service.AesProcessor;

@Service
public class AdminMemberServiceImpl implements AdminMemberService {
	
	@Autowired
	AdminMemberMapper adminMemberMapper;
	
	@Autowired
	AesProcessor aesProcessor;
	
	@Override
	public List<AdminMemberVO> selectAllMember(PagingVO pagingVO) {
		// 회원 전체 조회
		return adminMemberMapper.selectAllMemeber(pagingVO);
	}
	
	@Override
	public int memberCount() {
		// 전체 회원 수 카운트
		return adminMemberMapper.getAllMemberCount();
	}
	
	@Override
	public AdminMemberVO getMemberDetail(AdminMemberVO vo) {
		// 회원 상세 조회
		String decodedAccountNum = "";
		
		//계좌번호 복호화
		try {
			vo = adminMemberMapper.getMemberDetail(vo);
			decodedAccountNum = aesProcessor.aesCBCDecode(vo.getAccountNumber());
			vo.setAccountNumber(decodedAccountNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return vo;
	}
	
	@Override
	public String modifyMemberInfo(AdminMemberVO vo) {
		
		// 계좌번호 암호화
		try {
			vo.setAccountNumber(aesProcessor.aesCBCEncode(vo.getAccountNumber())); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 회원 정보 수정
		int result = adminMemberMapper.modifyMemberInfo(vo);
		
		if(result > 1) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	@Override
	public String withdrawMember(AdminMemberVO vo) {
		// 회원 삭제
		int result = adminMemberMapper.withdrawMember(vo);
		
		if(result > 1) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	@Override
	public int countName(String keyword) {
		// 이름 검색의 총 결과값 카운트
		return adminMemberMapper.countName(keyword);
	}

	@Override
	public int countId(String keyword) {
		// 아이디 검색의 총 결과값 카운트
		return adminMemberMapper.countId(keyword);
	}
	
	@Override
	public List<AdminMemberVO> searchMemberByName(AdminMemberVO adminVO, PagingVO pagingVO) {
		// 이름으로 회원 검색
		return adminMemberMapper.searchMemberByName(adminVO, pagingVO);
	}
	
	@Override
	public List<AdminMemberVO> searchMemberById(AdminMemberVO adminVO, PagingVO pagingVO) {
		// 아이디로 회원 검색
		return adminMemberMapper.searchMemberById(adminVO, pagingVO);
	}
	
	@Override
	public List<AdminMemberVO> selectAllAuthRequest() {
		// 권한 승인 요청 내역 전체 조회
		List<AdminMemberVO> list = adminMemberMapper.selectAllAuthRequest();
		
		// 복호화
		for(AdminMemberVO vo : list) {
			
			String decodedAccountNum = "";
			try {
				decodedAccountNum = aesProcessor.aesCBCDecode(vo.getAccountNumber());
				vo.setAccountNumber(decodedAccountNum); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return list;
	}

	@Override
	public Map<String, String> approveAuthRequest(String memberId) {
		// 권한 승인 요청 승인 처리
		Map<String, String> map = new HashMap<>();
		
		map.put("memberId", memberId);
		
		int result = adminMemberMapper.approveAuthRequest(memberId);
		if(result > 0) {
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}
		
		return map;
	}

	@Override
	public Map<String, String> rejectAuthRequest(String memberId) {
		// 권한 승인 요청 반려 처리
		Map<String, String> map = new HashMap<>();
		
		map.put("memberId", memberId);
		
		int result = adminMemberMapper.rejectAuthRequest(memberId);
		if(result > 0) {
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}
		
		return map;
	}
	
	@Override
	public Map<String, Object> selectFilterSearch(Integer nowPage, Integer cntPerPage, AdminMemberVO adminMemberVO) {
		
		// 필터검색할 회원 수 카운트
		int total = adminMemberMapper.countFilterSearch(adminMemberVO);
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		
		// 필터검색 회원 전체 조회
		List<AdminMemberVO> list = adminMemberMapper.selectFilterSearch(adminMemberVO, pagingVO);
		
		// 컨트롤러에 값을 보내기 위한 Map 생성
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("list", list);
		map.put("PagingVO", pagingVO);
		
		return map;
	}

}
