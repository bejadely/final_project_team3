package com.trip.finalProject.statistics.mapper;

import java.util.List;

import com.trip.finalProject.statistics.service.AttractionVO;
import com.trip.finalProject.statistics.service.VisitorVO;

public interface StatisticsMapper {
    List<VisitorVO> getVisitorList(String prevMonth);

	List<VisitorVO> getVisitorRank(String prevMonth);

	List<AttractionVO> getAttractionRank(String prevMonth);
}
