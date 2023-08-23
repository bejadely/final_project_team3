package com.trip.finalProject.notice.service;

import java.util.List;


public interface NoticeService {
	
	
	public NoticeVO insertpost(NoticeVO noticeVO) throws Exception; 
	public List<NoticeVO> boardSelectList(NoticeVO vo);
	public List<NoticeVO> NoticeVO(NoticeVO vo);
	public int boardInsert(NoticeVO vo);
	public int boardUpdate(NoticeVO vo);
	public int boardDelete(NoticeVO vo);
	public int boardSelectMax(NoticeVO vo);
	public NoticeVO boardDetail(NoticeVO vo);
	public int boardView(NoticeVO vo);
	public int boardReple(NoticeVO vo);
	public int boardRepleN(NoticeVO vo);
}
