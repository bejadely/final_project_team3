package com.trip.finalProject.report.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.report.mapper.PunishMapper;
import com.trip.finalProject.report.service.PunishService;
import com.trip.finalProject.report.service.PunishVO;

@Service
public class PunishServiceImpl implements PunishService {
	
	@Autowired
	PunishMapper punishMapper;
	
	// 제재 내역 전체 조회
	public Map<String, Object> selectAllPunishList(Integer nowPage, Integer cntPerPage) {
			
			// 새로운 신고내역 카운트
			int total = punishMapper.countAllPunishList();
			PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
			
			// 새로운 신고내역 전체 조회
			List<PunishVO> list = punishMapper.selectAllPunishList(pagingVO);
			
			// 컨트롤러에 값을 보내기 위한 Map 생성
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("list", list);
			map.put("PagingVO", pagingVO);
			
			return map;
		}
	
	
}
