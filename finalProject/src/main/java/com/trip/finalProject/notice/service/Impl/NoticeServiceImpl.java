package com.trip.finalProject.notice.service.Impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.notice.mapper.NoticeMapper;
import com.trip.finalProject.notice.service.NoticeService;
import com.trip.finalProject.notice.service.NoticeVO;


@Service
public class NoticeServiceImpl implements NoticeService {

	
	@Autowired
	NoticeMapper noticeMapper;
    
    
    @Override
	public NoticeVO insertpost(NoticeVO  noticeVO) throws Exception {

		NoticeVO result = noticeMapper.insertpost(noticeVO);
		
		if(result !=null) {
			return noticeMapper.insertpost(noticeVO);
		}else {
			return null;
		}

    }
    
    @Override
	public List<NoticeVO> boardSelectList(NoticeVO vo) {

		return noticeMapper.boardSelectList(vo);
	}

	@Override
	public List<NoticeVO> NoticeVO(NoticeVO vo) {
	
		return noticeMapper.boardSelect(vo);
	}

	@Override
	public int boardInsert(NoticeVO vo) {

		return noticeMapper.boardInsert(vo);
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
	
		return noticeMapper.boardRepleN(vo);
	}
    
    
}
