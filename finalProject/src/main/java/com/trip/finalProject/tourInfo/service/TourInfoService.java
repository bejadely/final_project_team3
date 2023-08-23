package com.trip.finalProject.tourInfo.service;


import com.trip.finalProject.common.PagingVO;

import java.util.List;
import java.util.Map;

public interface TourInfoService {
    Map<String, Object> getTourInfoMap(int areaCode, int sigunguCode);

    Map<String, String> getDetailInfoMap(int contentId, int contentTypeId);
    
    public String getContentNameDetail(String contentId);
    
    public String getLocationNameDetail(String areaCode, String sigunguCode);
    
    Map<String,Object> getSpotDetail(String page, String contentTypeId, String areaCode, String sigunguCode);

    PagingVO getSpotDetailPagingVo(String page, Map<String, Object> spotDetailMap);

    Map<String,Object> getDetailInfoReviewList(String contentId, String contentTypeId);

    List<SpotDetailReviewVO> getDetailReviewList(String contentId, int page);

    Map<String,Object> insertReviewInfo(SpotDetailReviewVO spotDetailReviewVO) throws Exception;

    Map<String,Object> deleteReviewInfo(int contentId, String reviewId) throws Exception;

	public List<SearchInfoDTO> getsearchInfo(String searchKeyWord);
}
