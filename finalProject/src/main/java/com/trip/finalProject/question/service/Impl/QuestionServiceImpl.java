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
	public int Count(String writerMemberId) {
		return queMapper.getTotalCount(writerMemberId);
	}
	
	@Override
	public int memberCount(String memberId) {
		// TODO Auto-generated method stub
		return queMapper.getTotalMember(memberId);
	}

	@Override
	public List<QuestionVO> getQueAll(QuestionVO questionVO, PagingVO pagingvo) {
		return queMapper.selectAllQue(questionVO, pagingvo);
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



	@Override
	public List<QuestionVO> getQueAllMember(QuestionVO questionVO, PagingVO pagingVO) {
		return queMapper.selectAllQueMember(questionVO, pagingVO);
	}
	
	//0903 창민 추가
	@Override
	public String insertQuestion(QuestionVO questionVO) {
		
		// 문의글 타입 변환
		String rowQuestionType = questionVO.getQuestionType();
		
		switch (rowQuestionType) {
		case "패키지 문의":
			questionVO.setQuestionType("Q2");
			break;
		case "특산물 문의":
			questionVO.setQuestionType("Q3");
			break;
		case "숙박상품 문의":
			questionVO.setQuestionType("Q4");
			break;
		default:
			questionVO.setQuestionType("Q1");
			break;
		}
		
		// 문의글 등록
		int result = queMapper.insertQuestion(questionVO);
		
		// 결과값 리턴
		if(result > 0) {
			return "success";
		} else {
			return "fail";
		}
		
	}
	
	

}
