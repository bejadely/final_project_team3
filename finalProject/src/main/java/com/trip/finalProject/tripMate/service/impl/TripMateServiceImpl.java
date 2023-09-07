package com.trip.finalProject.tripMate.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.finalProject.attachedFile.mapper.AttachedFileMapper;
import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.tripMate.mapper.TripMateMapper;
import com.trip.finalProject.tripMate.service.PostCommentVO;
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
	
	//여행 메이트 게시글 전체 조회
	@Override
	public List<TripMateVO> getTripMateAll(PagingVO pagingVO) {
		return tripMateMapper.selectAllTripMate(pagingVO);
	}
	
	//전체 여행 메이트 글 수 카운트
	@Override
	public int mateCount() {
		return tripMateMapper.getAllMateListCount();
	}
	
	//여행 지역으로 검색
	@Override
	public List<TripMateVO> searchMateByTripArea(TripMateVO tripMateVO, PagingVO pagingVO) {
		return tripMateMapper.searchMateByTripArea(tripMateVO, pagingVO);
	}
	
	//여행 지역으로 여행메이트 글 수 카운트
	@Override
	public int countTripArea(String keyword) {
		return tripMateMapper.countTripArea(keyword);
	}
	
	//여행 스타일로 검색
	@Override
	public List<TripMateVO> searchMateByStyle(TripMateVO tripMateVO, PagingVO pagingVO) {
		return tripMateMapper.searchMateByStyle(tripMateVO, pagingVO);
	}
			
	//여행 스타일로 여행메이트 글 수 카운트
	@Override
	public int countTripStyle(String keyword) {
		return tripMateMapper.countTripStyle(keyword);
	}
	
	//여행 타이틀로 검색
	@Override
	public List<TripMateVO> searchMateByTripTitle(TripMateVO tripMateVO, PagingVO pagingVO) {
		return tripMateMapper.searchMateByTripTitle(tripMateVO, pagingVO);
	}
		
	//여행 메이트 게시글 제목으로 글 수 카운트
	@Override
	public int countTripTitle(String keyword) {
		return tripMateMapper.countTripTitle(keyword);
	}
	
	//게시글 작성자로 아이디로 검색
	@Override
	public List<TripMateVO> searchMateByTripWriterId(TripMateVO tripMateVO, PagingVO pagingVO) {
		return tripMateMapper.searchMateByTripWriterId(tripMateVO, pagingVO);
	}	
		
	//여행 메이트 작성자 아이디로 글 수 카운트
	@Override
	public int countTripWrtierId(String keyword) {
		return tripMateMapper.countTripWrtierId(keyword);
	}
	

	//여행 메이트 게시글 상세 조회
	@Override
	public TripMateVO getTripMateInfo(TripMateVO tripMateVO) {
		return tripMateMapper.selectTripMateInfo(tripMateVO);
	}
	
	//여행 메이트 게시글 조회수 카운트
	@Override
	public int updateMateRecruitHit(TripMateVO tripMateVO) {
		return tripMateMapper.updateMateRecruitHit(tripMateVO);
	}

	//여행 메이트 게시글 등록
	@Transactional
	@Override
	public void insertTripMateRecruit(TripMateVO tripMateVO) {
		mapper.insertEditor(tripMateVO);
		if (tripMateVO.getAttachList() == null || tripMateVO.getAttachList().size() <= 0) {			
			return ;
		}else {
			tripMateVO.getAttachList().forEach(attach->{
				attach.setPostId(tripMateVO.getPostId());
				attachedFileMapper.insertAttachedFile(attach);
			});
		}
		
		if(tripMateVO.getEditorAttachList()==null || tripMateVO.getEditorAttachList().size()<=0) {
			return;
		}else {
			tripMateVO.getEditorAttachList().forEach(attach->{
				attach.setPostId(tripMateVO.getPostId());
				attachedFileMapper.insertAttachedFile(attach);
			});
		}
	}	

	//여행 메이트 게시글 삭제
	@Override
	public int deleteTripMateRecruit(TripMateVO tripMAteVO) {
		return tripMateMapper.deleteTripMateRecruit(tripMAteVO);
	}
	
	//여행 메이트 글 삭제 시 해당 게시글과 관련된 첨부파일 테이블 데이터 삭제
	@Override
	public int deleteAttachedFile(TripMateVO tripMateVO) {
		return tripMateMapper.deleteAttachedFile(tripMateVO);
	}

	//여행 메이트 게시글 수정
	@Override
	public int updateTripMateRecruit(TripMateVO tripMateVO) {
		return tripMateMapper.updateTripMateRecruit(tripMateVO);
	}
	
	//여행 메이트 게시글 신고
	@Override
	public int reportTripMate(TripMateVO tripMateVO) {
		return tripMateMapper.reportTripMate(tripMateVO);
	}

	//여행 메이트 신청 (등록된 게시글에 대한 여행메이트 신청)
	@Override
	public TripMateVO InsertTripMateApply(TripMateVO tripMateVO) {
		tripMateMapper.insertTripMateApply(tripMateVO);
		return tripMateVO;
	}
	
	//여행 메이트 신청시 작성자에게 알림
	@Override
	public int sendAlert(TripMateVO tripMateVO) {
		return tripMateMapper.sendAlert(tripMateVO);
	}

	//여행 메이트 신청시 게시글의 신청자 수 업데이트
	@Override
	public int updateMateRecruitApplyNum(TripMateVO tripMateVO) {
		return tripMateMapper.updateMateRecruitApplyNum(tripMateVO);
	}

	//여행 메이트 게시글의 현재 신청자 수 카운트
	@Override
	public int selectMateRecruitApplyNum(TripMateVO tripMateVO) {
		int result = tripMateMapper.selectMateRecruitApplyNum(tripMateVO);
		return result;
	}

	//댓글, 대댓글 가져오기
	@Override
	public List<PostCommentVO> getCommentInfo(TripMateVO tripMateVO) {
		
		return tripMateMapper.getCommentInfo(tripMateVO);
	}
	
	//마이페이지--------------------------------------------------------------------------------------
	//내가 작성한 마이페이지 페이징
	@Override
	public int myTripCount(String memberId) {
		return tripMateMapper.myTripCount(memberId);
	}
	//내가 작성한 마이페이지
	@Override
	public List<TripMateVO> myMateList(TripMateVO trVO, PagingVO pagingVO) {
		return tripMateMapper.myMateList(trVO, pagingVO);
	}
	
	//여행 메이트 게시글 상세 조회
	@Override
	public List<TripMateVO> getTripMateMyInfo(TripMateVO tripMateVO) {
		return tripMateMapper.selectTripMateMyInfo(tripMateVO);
	}
	
	//내가 참여한 마이페이지 페이징
	@Override
	public int myTripAppCount(String memberId) {
		return tripMateMapper.myTripAppCount(memberId);
	}
	//내가 참여한 마이페이지
	@Override
	public List<TripMateVO> myMateAppList(TripMateVO trVO, PagingVO pagingVO) {
		return tripMateMapper.myMateAppList(trVO, pagingVO);
	}

	@Override
	public int myMateCancle(TripMateVO trVO) {
		return tripMateMapper.myTripCancle(trVO);
	}

	//참여한 메이트 정보 불러오기
	@Override
	public TripMateVO memberInfo(TripMateVO tripMateVO) {
		return tripMateMapper.memberInfo(tripMateVO);
	}

	@Override
	public int myTripnum(TripMateVO trVO) {
		return tripMateMapper.myTripnum(trVO);
	}


}
