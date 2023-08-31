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
	public List<QuestionVO> selectAllQue(PagingVO pagingVO);
	//등록
	public int insertQue(QuestionVO qeustionVO);
	//수정
	public int updateQue(QuestionVO questionVO);
	
	public List<QuestionVO> selectAllQueMember(PagingVO pagingVO);

}
