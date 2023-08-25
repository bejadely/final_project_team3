package com.trip.finalProject.notice.mapper;


import java.util.List;

import com.trip.finalProject.notice.service.NoticeVO;

public interface NoticeMapper {
	
	public NoticeVO insertpost(NoticeVO noticeVO);

	public List<NoticeVO> boardSelectList(NoticeVO vo) ;
	
	public List<NoticeVO> boardSelect(NoticeVO vo);
	
	public int noticeInsert(NoticeVO vo);
	
	public int boardUpdate(NoticeVO vo);
	
	public int boardDelete(NoticeVO vo);
	
	public int boardSelectMax(NoticeVO vo);
	
	public NoticeVO boardDetail(NoticeVO vo);
	
	public int boardView(NoticeVO vo);
	
	public int boardReple(NoticeVO vo);


	
	
}
