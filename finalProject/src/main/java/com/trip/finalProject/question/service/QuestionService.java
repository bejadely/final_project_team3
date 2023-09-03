package com.trip.finalProject.question.service;

import java.util.List;
import java.util.Map;

import com.trip.finalProject.common.PagingVO;

public interface QuestionService {
	//페이징 가이드
	public int Count(String answerMemberId);
	//페이징 멤버
	public int memberCount(String memberId);
	//전체조회
	public List<QuestionVO> getQueAll(QuestionVO questionVO,PagingVO pagingvo);
	//등록
	public Map<String, String> insertQueInfo(QuestionVO questionVO);
	//수정
	public Map<String, String> updateQueInfo(QuestionVO questionVO);
	//조회 / common 용
	public List<QuestionVO> getQueAllMember(QuestionVO questionVO, PagingVO pagingVO);
	
	//0903 창민
	// 문의글 등록
	public String insertQuestion(QuestionVO questionVO);
	
	// 일반문의글 전체 조회(관리자)
	public Map<String, Object> selectAllQuestion(Integer nowPage, Integer cntPerPage);
	
	// 문의 답변 입력(관리자)
	public QuestionVO insertAnswerToQuestion(QuestionVO questionVO);
}
