package com.trip.finalProject.calculation.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.finalProject.calculation.mapper.CalculationMapper;
import com.trip.finalProject.calculation.service.CalculationService;
import com.trip.finalProject.calculation.service.CalculationVO;
import com.trip.finalProject.common.PagingVO;

@Service
public class CalculationServiceImpl implements CalculationService {
	
	@Autowired
	CalculationMapper calculationMapper;
	
	// 미정산 내역 전체 조회
	@Override
	public Map<String, Object> selectNotCalList(Integer nowPage, Integer cntPerPage) {
			
		// 미정산 내역 카운트
		int total = calculationMapper.countNotCalList();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		
		// 미정산 내역 전체 조회
		List<CalculationVO> list = calculationMapper.selectNotCalList(pagingVO);
		
		// 컨트롤러에 값을 보내기 위한 Map 생성
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("list", list);
		map.put("PagingVO", pagingVO);
		
		return map;
	}
	
	@Transactional
	@Override
	public String calAtOnce(CalculationVO calculationVO) {
		// 일괄 정산 처리
		
		// 한꺼번에 넘어온 VO값 분리 처리
		String[] calIdArry = calculationVO.getCalId().split(",");
		String[] writerIdArry = calculationVO.getWriterId().split(",");
		String[] calAmountArry = calculationVO.getCalAmount().split(",");
		String[] bankCodeArry = calculationVO.getBankCode().split(",");
		String[] accountNumberArry = calculationVO.getAccountNumber().split(",");
		
		// 분리된 각각의 값들을 활용해 mapper로 처리
		int result = 0;
		for(String calId : calIdArry) {
			calculationMapper.calProc(calId);
			result += 1;
		}
		
		if(result == calIdArry.length) {
			return "일괄 정산이 성공적으로 완료되었습니다.";
		} else {
			return "일괄 정산이 성공적으로 이루지지 않았습니다.\n다시 시도해 주세요.";
		}
		
	}
	
	
	@Override
	public Map<String, Object> selectCompCalList(Integer nowPage, Integer cntPerPage, Integer searchMonth) {
		
		// 특정월 정산내역 전체 조회
		
		// 특정월 내역 카운트
		int total = calculationMapper.countCompCalList(searchMonth);
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		
		// 미정산 내역 전체 조회
		List<CalculationVO> list = calculationMapper.selectCompCalList(pagingVO, searchMonth);
		
		// 컨트롤러에 값을 보내기 위한 Map 생성
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("list", list);
		map.put("PagingVO", pagingVO);
		
		return map;
	}
	
}
