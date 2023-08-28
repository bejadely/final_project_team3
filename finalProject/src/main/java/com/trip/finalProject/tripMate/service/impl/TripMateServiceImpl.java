package com.trip.finalProject.tripMate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.finalProject.attachedFile.mapper.AttachedFileMapper;
import com.trip.finalProject.packaged.mapper.PackageMapper;
import com.trip.finalProject.tripMate.mapper.TripMateMapper;
import com.trip.finalProject.tripMate.service.TripMateService;
import com.trip.finalProject.tripMate.service.TripMateVO;

import lombok.Setter;

@Service
public class TripMateServiceImpl implements TripMateService {
	
	@Autowired
	TripMateMapper tripMateMapper;
	
	@Setter(onMethod_=@Autowired)
	private TripMateMapper mapper;
	
	@Setter(onMethod_=@Autowired)
	private AttachedFileMapper attachedFileMapper; 
	
	//여행 메이트 글 전체 조회
	@Override
	public List<TripMateVO> getTripMateAll() {
		return tripMateMapper.selectAllTripMate();
	}

	//여행 메이트 글 상세 조회
	@Override
	public TripMateVO getTripMateInfo(TripMateVO tripMateVO) {
		return tripMateMapper.selectTripMateInfo(tripMateVO);
	}

	//여행 메이트 게시글 등록
	@Transactional
	@Override
	public void insertTripMateRecruit(TripMateVO tripMateVO) {
		mapper.insertEditor(tripMateVO);
		if (tripMateVO.getAttachList() == null || tripMateVO.getAttachList().size() <= 0) {			
			return ;
		}
		if(tripMateVO.getEditorAttachList()==null || tripMateVO.getEditorAttachList().size()<=0) {
			return;
		}
			tripMateVO.getAttachList().forEach(attach->{
				attach.setPostId(tripMateVO.getPostId());
				attachedFileMapper.insertAttachedFile(attach);
			});
			tripMateVO.getEditorAttachList().forEach(attach->{
				attach.setPostId(tripMateVO.getPostId());
				attachedFileMapper.insertAttachedFile(attach);
			});
	}
	
	//여행 메이트 신청 (등록된 게시글에 대한 여행메이트 신청)
	@Override
	public TripMateVO InsertTripMateApply(TripMateVO tripMateVO) {
		tripMateMapper.insertTripMateApply(tripMateVO);
		return tripMateVO;
	}
	

}
