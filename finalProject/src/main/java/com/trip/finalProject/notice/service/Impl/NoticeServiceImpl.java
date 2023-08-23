package com.trip.finalProject.notice.service.Impl;



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
}
