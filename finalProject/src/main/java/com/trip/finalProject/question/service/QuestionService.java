package com.trip.finalProject.question.service;

import java.util.List;
import java.util.Map;

import com.trip.finalProject.common.PagingVO;

public interface QuestionService {
	//페이징
	public int Count();
	//전체조회
	public List<QuestionVO> getQueAll(PagingVO pagingvo);
	//등록
	public Map<String, String> insertQueInfo(QuestionVO questionVO);
	//수정
	public Map<String, String> updateQueInfo(QuestionVO questionVO);

}
