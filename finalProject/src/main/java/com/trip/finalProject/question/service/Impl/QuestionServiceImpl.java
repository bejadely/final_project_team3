package com.trip.finalProject.question.service.Impl;

import java.util.List;

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
	public QuestionVO getQueInfo(String questionId) {
		return queMapper.selectQueInfo(questionId);
	}

	@Override
	public List<QuestionVO> getQueAll(PagingVO pagingvo) {
		return queMapper.selectAllQue(pagingvo);
	}

	@Override
	public int insertQueInfo(QuestionVO questionVO) {
		return queMapper.insertQue(questionVO);
	}

	@Override
	public int updateQueInfo(QuestionVO questionVO) {
		return queMapper.updateQue(questionVO);
	}

}
