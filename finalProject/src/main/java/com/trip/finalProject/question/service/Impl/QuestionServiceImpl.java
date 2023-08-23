package com.trip.finalProject.question.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.question.mapper.QuestionMapper;
import com.trip.finalProject.question.service.QuestionService;
import com.trip.finalProject.question.service.QuestionVO;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	QuestionMapper queMapper;

	@Override
	public int Count() {
		return queMapper.getTotalCount();
	}

	@Override
	public List<QuestionVO> getQueAll(PagingVO pagingvo) {
		return queMapper.selectAllQue(pagingvo);
	}
	//답글 입력
	@Override
	public Map<String, String> insertQueInfo(QuestionVO questionVO) {
		Map<String, String> map = new HashMap<>();
		map.put("회원 정보", String.valueOf(questionVO.getMemberId()));
		queMapper.insertQue(questionVO);
		return map;
	}

	//답글 수정
	@Override
	public Map<String, String> updateQueInfo(QuestionVO questionVO) {
		Map<String, String> map = new HashMap<>();
		map.put("회원 정보", String.valueOf(questionVO.getMemberId()));
		queMapper.updateQue(questionVO);
		return map;
	}

}
