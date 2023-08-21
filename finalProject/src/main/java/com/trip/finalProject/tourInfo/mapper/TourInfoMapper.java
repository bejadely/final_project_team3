package com.trip.finalProject.tourInfo.mapper;

import com.trip.finalProject.tourInfo.service.SpotDetailReviewVO;

import java.util.List;

public interface TourInfoMapper {
    //리뷰 조회
    List<SpotDetailReviewVO> selectSpotDetailReview(String contentId, int page);

    //리뷰 조회 시 총 리뷰 갯수 구하기
    int selectSpotDetailReviewTotalCount(String contentId);

    //리뷰 등록
    int insertReviewInfo(SpotDetailReviewVO spotDetailReviewVO);

    //리뷰 삭제
    int deleteReview(String reviewId);

}
