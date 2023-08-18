package com.trip.finalProject.tourInfo.service;


import java.util.List;
import java.util.Map;

public interface TourInfoService {
    Map<String, Object> getTourInfoMap(int areaCode, int sigunguCode);

    Map<String, String> getDetailInfoMap(int contentId, int contentTypeId);
    
    public String getContentNameDetail(String contentId);
    
    public String getLocationNameDetail(String areaCode, String sigunguCode);
    
    Map<String,Object> getSpotDetail(String contentId, String areaCode, String sigunguCode);
}
