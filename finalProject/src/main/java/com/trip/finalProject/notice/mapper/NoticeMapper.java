package com.trip.finalProject.notice.mapper;


import java.util.List;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.notice.service.NoticeVO;

public interface NoticeMapper {
	
	public NoticeVO insertpost(NoticeVO noticeVO);

	public List<NoticeVO> boardSelectList(NoticeVO vo) ;
	
	//리스트 화면에 뿌리기
	public List<NoticeVO> SelectAllNoticeList(PagingVO pagingVO);
	
	//공지사항 작성
	public int noticeInsert(NoticeVO vo);
	
	// 전체 게시글 수 카운트
	public int getAllNoticeCount();
	
	public int boardUpdate(NoticeVO vo);
	
	public int boardDelete(NoticeVO vo);
	
	public int boardSelectMax(NoticeVO vo);
	
	public NoticeVO boardDetail(NoticeVO vo);
	
	public int boardView(NoticeVO vo);
	
	public int boardReple(NoticeVO vo);


	
	
}
