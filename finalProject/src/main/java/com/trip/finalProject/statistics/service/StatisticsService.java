package com.trip.finalProject.statistics.service;

import java.util.List;
import java.util.Map;

public interface StatisticsService {
    Map<String, Integer> getVisitorData();

	List<VisitorVO> getVisitorRank();

	List<AttractionVO> getAttractionRank();

    List<TotalDataVO> getTotalData(String locationName);
}
