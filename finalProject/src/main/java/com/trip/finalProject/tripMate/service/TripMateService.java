package com.trip.finalProject.tripMate.service;

import java.util.List;
import java.util.Map;

import com.trip.finalProject.common.PagingVO;

public interface TripMateService {
	//여행 메이트 글 전체조회 - 페이징은 다른 기능이 어느정도 구현이 되고 나서 검색이 가능한 페이징으로 추가 해야함.
	public List<TripMateVO> getTripMateAll(PagingVO pagingVO);
	
	//전체 여행메이트 글 수 카운트
	public int mateCount();

	//여행 지역으로 검색
	public List<TripMateVO> searchMateByTripArea(TripMateVO tripMateVO, PagingVO pagingVO);
	
	//여행 지역으로 여행메이트 글 수 카운트
	public int countTripArea(String keyword);
	
	//여행 스타일로 검색
	public List<TripMateVO> searchMateByStyle(TripMateVO tripMateVO, PagingVO pagingVO);
		
	//여행 스타일로 여행메이트 글 수 카운트
	public int countTripStyle(String keyword);
	
	//여행 타이틀로 검색
	public List<TripMateVO> searchMateByTripTitle(TripMateVO tripMateVO, PagingVO pagingVO);
	
	//여행 메이트 게시글 제목으로 글 수 카운트
	public int countTripTitle(String keyword);
	
	//게시글 작성자로 아이디로 검색
	public List<TripMateVO> searchMateByTripWriterId(TripMateVO tripMateVO, PagingVO pagingVO);
	
	//여행 메이트 작성자 아이디로 글 수 카운트
	public int countTripWrtierId(String keyword);
	
	//여행 메이트 게시글 상세조회
	public TripMateVO getTripMateInfo(TripMateVO tripMateVO);
	
	//여행 메이트 게시글에 댓글 달기
	
	
	//여행 메이트 게시글 조회수 카운트
	public int updateMateRecruitHit(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 등록
	public void insertTripMateRecruit(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 삭제
	public int deleteTripMateRecruit(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 삭제 시 해당 게시글과 관련된 첨부파일 테이블 데이터 삭제
	public int deleteAttachedFile(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 수정
	public void updateTripMateRecruit(TripMateVO tripMateVO);
	
	//여행 메이트 게시글 신고
	public int reportTripMate(TripMateVO tripMateVO);
	
	//여행 메이트 신고시 해당 신고자가 신고한 내역이 존재하는지 확인
	public int selectReportLog(TripMateVO tripMateVO);
	
	//여행 메이트 신청 (등록된 게시글에 대한 여행메이트 신청)
	public TripMateVO InsertTripMateApply(TripMateVO tripMateVO); 
	
	//여행 메이트 신청시 해당 신청자가 신청한 내역이 존재하는지 확인
	public int selectApplyLog(TripMateVO tripMateVO);
	
	//여행 메이트 신청시 작성자에게 알림 보냄
	public int sendAlert(TripMateVO tripMateVO);
	
	//여행 메이트 신청시 게시글의 신청자 수 업데이트
	public int updateMateRecruitApplyNum(TripMateVO tripMateVO);
	
	//여행 메이트 게시글의 현재 신청자 수 조회
	public int selectMateRecruitApplyNum(TripMateVO tripMateVO);
	
	//마이페이지-----------------------------------------------------------------------------------------------------
	//내가 작성한 메이트 페이징
	public int myTripCount(String memberId);
	
	//내가 작성한 메이트
	public List<TripMateVO> myMateList(TripMateVO trVO, PagingVO pagingVO);
	
	//내가 작성한 메이트 게시글 상세조회
	public List<TripMateVO> getTripMateMyInfo(TripMateVO tripMateVO);
	
	//내가 참여한 메이트 페이징
	public int myTripAppCount(String memberId);
	
	//내가 참여한 메이트
	public List<TripMateVO> myMateAppList(TripMateVO trVO, PagingVO pagingVO);
	
	//신청한 메이트 취소
	public int myMateCancle(TripMateVO trVO);
	
	//메이트 인원수 줄이기
	public int myTripnum(TripMateVO trVO);
	
	//참여한 메이트 정보 불러오기
	public TripMateVO memberInfo(TripMateVO tripMateVO);

	//댓글, 대댓글 관련
	public List<PostCommentVO> getCommentInfo(String postId, String page);

	public Map<String, Object> insertCommentInfo(PostCommentVO postCommentVO) throws Exception;

    Map<String, Object> deleteComment(PostCommentVO postCommentVO) throws Exception;

	public Map<String, Object> insertCommentReplyInfo(PostCommentVO postCommentVO) throws Exception;

	public Map<String, Object> modifyCommentInfo(PostCommentVO postCommentVO) throws Exception;

	public int getCommentNumInfo(TripMateVO tripMateVO);

	public void getChatingToken();

}
