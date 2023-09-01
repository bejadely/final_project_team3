package com.trip.finalProject.notice.service.Impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.notice.mapper.NoticeMapper;
import com.trip.finalProject.notice.service.NoticeService;
import com.trip.finalProject.notice.service.NoticeVO;


@Service
public class NoticeServiceImpl implements NoticeService {

	
	@Autowired
	NoticeMapper noticeMapper;
    
    

    
	
    //전체 게시글 수 카운트
    @Override
	public int listCount() {
		return noticeMapper.getAllNoticeCount();
	}
    
    //게시글 전체 조회
	@Override
	public List<NoticeVO> SelectAllNoticeList(PagingVO pagingVO) {
		
		return noticeMapper.SelectAllNoticeList(pagingVO);
	}
	
	//게시글 상세조회
	@Override
	public NoticeVO getNoticeDetail(NoticeVO vo) {
		
		return noticeMapper.getNoticeDetail(vo);
	}
	
	//게시글 등록
	@Override
	public int noticeInsert(NoticeVO vo) {

		return noticeMapper.noticeInsert(vo);
	}
	
	//게시물 수정하기
	@Override
	public String modifyNoticeInfo(NoticeVO vo) {
		
		int result = noticeMapper.modifyNoticeInfo(vo);
	
		if(result > 1) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	@Override
	public int boardUpdate(NoticeVO vo) {
		
		return noticeMapper.boardUpdate(vo);
	}

	@Override
	public int boardDelete(NoticeVO vo) {

		return noticeMapper.boardDelete(vo);
	}



	@Override
	public int boardSelectMax(NoticeVO vo) {

		return noticeMapper.boardSelectMax(vo);
	}

	@Override
	public NoticeVO boardDetail(NoticeVO vo) {
	
		return noticeMapper.boardDetail(vo);
	}

	@Override
	public int boardView(NoticeVO vo) {

		return noticeMapper.boardView(vo);
	}

	@Override
	public int boardReple(NoticeVO vo) {
	
		return noticeMapper.boardReple(vo);
	}

	@Override
	public int boardRepleN(NoticeVO vo) {
	
		return noticeMapper.boardReple(vo);
	}
    
    
}
