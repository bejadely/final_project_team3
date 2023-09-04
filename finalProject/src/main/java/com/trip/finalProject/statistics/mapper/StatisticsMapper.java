package com.trip.finalProject.statistics.mapper;

import java.util.List;

import com.trip.finalProject.statistics.service.*;

public interface StatisticsMapper {
    List<VisitorVO> getVisitorList(String prevMonth);

	List<VisitorVO> getVisitorRank(String prevMonth);

	List<AttractionVO> getAttractionRank(String prevMonth);

	List<FellowVO> getFellowList(String areaCode, String sigunguCode);

	List<RestaurantVO> getRestaurantList(String areaCode, String sigunguCode);

	List<AttractionVO> getAttractionList(String areaCode, String sigunguCode);

	List<TripDataVO> getTripList(String areaCode, String sigunguCode);

	List<SnsVO> getSnsList(String areaCode, String sigunguCode);
}
