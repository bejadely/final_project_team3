package com.trip.finalProject.notice.service;

import java.util.List;

import com.trip.finalProject.common.PagingVO;


public interface NoticeService {
	
	
	
	
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
	public List<NoticeVO> SelectAllNoticeList(String sessionAuthority,PagingVO pagingVO);

	
	//게시글 수 카운트
	public int listCount(String sessionAuthority);
	//게시글 상세조회 
	public NoticeVO getNoticeDetail(NoticeVO vo);
	//게시글 상세조회시 조회수 증가
	public int updateNoticeHit(NoticeVO vo);
	//게시글 수정적용
	public String modifyNoticeInfo(NoticeVO noticeVO);
	//전체 조회될 공지사항 타입이 n1인 수 카운트
	public int countNoticeType1n();
	
	//전체 조회될 공지사항 타입이 n2인 수 카운트
	public int countNoticeType2n();
	
	// 제목으로 공지사항 검색
	public List<NoticeVO> searchNoticeByTitle1n(NoticeVO noticeVO, PagingVO pagingVO);
	// 제목으로 이벤트 검색 
	public List<NoticeVO> searchNoticeByTitle2n(NoticeVO noticeVO, PagingVO pagingVO);

	
}
