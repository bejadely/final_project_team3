package com.trip.finalProject.notice.service;

import java.util.List;


public interface NoticeService {
	
	
	public NoticeVO insertpost(NoticeVO noticeVO) throws Exception; 
	public List<NoticeVO> boardSelectList(NoticeVO vo);
	public List<NoticeVO> boardSelect(NoticeVO vo);
	public int noticeInsert(NoticeVO vo);
	public int boardUpdate(NoticeVO vo);
	public int boardDelete(NoticeVO vo);
	public int boardSelectMax(NoticeVO vo);
	public NoticeVO boardDetail(NoticeVO vo);
	public int boardView(NoticeVO vo);
	public int boardReple(NoticeVO vo);
	public int boardRepleN(NoticeVO vo);
}
