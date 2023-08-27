package com.trip.finalProject.notice.service;

import java.util.List;

import com.trip.finalProject.common.PagingVO;


public interface NoticeService {
	
	
	public NoticeVO insertpost(NoticeVO noticeVO) throws Exception; 
	
	
	//public List<NoticeVO> boardSelect(NoticeVO vo);
	//공지사항 등록
	public int noticeInsert(NoticeVO vo);
	public int boardUpdate(NoticeVO vo);
	public int boardDelete(NoticeVO vo);
	public int boardSelectMax(NoticeVO vo);
	public NoticeVO boardDetail(NoticeVO vo);
	public int boardView(NoticeVO vo);
	public int boardReple(NoticeVO vo);
	public int boardRepleN(NoticeVO vo);
	//공지사항 화면에 뿌리기
	public List<NoticeVO> SelectAllNoticeList(PagingVO pagingVO);
	//게시글 수 카운트
	public int listCount();
}
