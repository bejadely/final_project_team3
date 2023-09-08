package com.trip.finalProject.excelUpload.mapper;

import java.util.List;
import java.util.Map;

public interface ExcelUploadMapper {
    Integer uploadRestaurantList(List<Map<String, String>> restaurantList);

	Integer uploadAttractionList(List<Map<String, String>> attractionList);

    Integer uploadVisitorList(List<Map<String, String>> visitorList);

    Integer uploadSnsList(List<Map<String, String>> snsList);

	Integer uploadFellowList(List<Map<String, String>> fellowList);

	Integer uploadTripList(List<Map<String, String>> tripList);
    
    Integer deleteFellowList(String areaCode, String sigunguCode, String yearMonth);

    Integer deleteTripList(String areaCode, String sigunguCode, String yearMonth);

    Integer deleteRestaurantList(String areaCode, String sigunguCode, String yearMonth);

    Integer deleteAttractionList(String areaCode, String sigunguCode, String yearMonth);

    Integer deleteVisitorList(String yearMonth);

    Integer deleteSnsList(String yearMonth);
}
