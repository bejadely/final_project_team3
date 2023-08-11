package com.trip.finalProject.question.mapper;

import java.util.List;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.question.service.QuestionVO;

public interface QuestionMapper {
	//페이징
	public int getTotalCount();
	//전체조회
	public List<QuestionVO> selectAllQue(PagingVO pagingVO);
	//단건조회
	public QuestionVO selectQueInfo(String questionId);
	
	//등록
	public int insertQue(QuestionVO qeustionVO);
	//수정
	public int updateQue(QuestionVO questionVO);

}
