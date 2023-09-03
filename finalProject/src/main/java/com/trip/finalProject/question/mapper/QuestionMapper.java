	package com.trip.finalProject.question.mapper;

import java.util.List;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.question.service.QuestionVO;

public interface QuestionMapper {
	//페이징 - 가이드용
	public int getTotalCount(String answerMemberId);
	//페이징 - 멤버용
	public int getTotalMember(String memberId);
	//전체조회
	public List<QuestionVO> selectAllQue(QuestionVO questionVO, PagingVO pagingVO);
	//등록
	public int insertQue(QuestionVO qeustionVO);
	//수정
	public int updateQue(QuestionVO questionVO);
	
	public List<QuestionVO> selectAllQueMember(QuestionVO questionVO, PagingVO pagingVO);
	
	// 0903 창민 추가
	public int insertQuestion(QuestionVO questionVO);

}
