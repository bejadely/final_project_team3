package com.trip.finalProject.statistics.mapper;

import com.trip.finalProject.statistics.web.VisitorVO;

import java.util.List;

public interface StatisticsMapper {
    List<VisitorVO> getVisitorList(String prevMonth);
}
