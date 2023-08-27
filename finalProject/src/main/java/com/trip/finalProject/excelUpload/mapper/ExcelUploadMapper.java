package com.trip.finalProject.excelUpload.mapper;

import java.util.List;
import java.util.Map;

public interface ExcelUploadMapper {
    Integer uploadRestaurantList(List<Map<String, String>> restaurantList);
}
