package com.trip.finalProject.question.service;

import java.util.List;

import com.trip.finalProject.common.PagingVO;

public interface QuestionService {
	//페이징
	public int Count();
	//전체조회
	public List<QuestionVO> getQueAll(PagingVO pagingvo);
	//단건조회
	public QuestionVO getQueInfo(String questionId);
	//등록
	public int insertQueInfo(QuestionVO questionVO);
	//수정
	public int updateQueInfo(QuestionVO questionVO);

}
