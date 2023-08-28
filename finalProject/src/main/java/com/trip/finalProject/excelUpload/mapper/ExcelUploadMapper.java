package com.trip.finalProject.excelUpload.mapper;

import java.util.List;
import java.util.Map;

public interface ExcelUploadMapper {
    Integer uploadRestaurantList(List<Map<String, String>> restaurantList);

	Integer uploadAttractionList(List<Map<String, String>> attractionList);

    Integer uploadVisitorList(List<Map<String, String>> visitorList);

    Integer uploadSnsList(List<Map<String, String>> snsList);
}
