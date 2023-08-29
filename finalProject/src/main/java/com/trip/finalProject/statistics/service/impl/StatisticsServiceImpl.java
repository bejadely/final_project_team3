package com.trip.finalProject.statistics.service.impl;

import com.trip.finalProject.statistics.mapper.StatisticsMapper;
import com.trip.finalProject.statistics.service.AttractionVO;
import com.trip.finalProject.statistics.service.StatisticsService;
import com.trip.finalProject.statistics.service.VisitorVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    StatisticsMapper statisticsMapper;

    @Override
    public Map<String, Integer> getVisitorData() {
        Map<String, Integer> visitorData = new HashMap<>();

        String prevMonth = getPrevMonth();

        List<VisitorVO> visitorList = statisticsMapper.getVisitorList(prevMonth);

        addVisitorData(visitorData, visitorList);

        return visitorData;
    }

    private String getPrevMonth() {
        // 현재 날짜를 가져옴
        LocalDate currentDate = LocalDate.now();

        // 지난 달의 날짜를 계산
        LocalDate lastMonthDate = currentDate.minusMonths(1);

        // 지난 달의 날짜를 yyyyMM 형식의 문자열로 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        return lastMonthDate.format(formatter);
    }

    private void addVisitorData(Map<String, Integer> visitorData, List<VisitorVO> visitorList) {

        String locationName = "";
        Integer visitorNumber = 0;
        String areaCode = "";
        String sigunguCode = "";

        for(int i = 0; i < visitorList.size(); i++) {
            areaCode = visitorList.get(i).getAreaCode();
            sigunguCode = visitorList.get(i).getSigunguCode();
            locationName = getLocationName(areaCode, sigunguCode);
            visitorNumber = Integer.parseInt(visitorList.get(i).getVisitorNumber());

            visitorData.put(locationName, visitorNumber);
        }
    }

    private String getLocationName(String areaCode, String sigunguCode) {
        String locationName = "";

        switch(areaCode) {
            case "4":
                locationName = "대구";
                break;
            case "6":
                locationName = "부산";
                break;
            case "7":
                locationName = "울산";
                break;
            case "35":
                switch(sigunguCode) {
                    case "2":
                        locationName = "경주";
                        break;
                    case "11":
                        locationName = "안동";
                        break;
                    case "23":
                        locationName = "포항";
                        break;
                    default:
                        break;
                }
            case "36":
                switch(sigunguCode) {
                    case "1":
                        locationName = "거제";
                        break;
                    case "17":
                        locationName = "통영";
                        break;
                    default:
                        break;
                }
        }

        return locationName;
    }

	@Override
	public List<VisitorVO> getVisitorRank() {
		
		String prevMonth = getPrevMonth();
		
		List<VisitorVO> visitorList = statisticsMapper.getVisitorRank(prevMonth);
		
		for(int i = 0; i<visitorList.size(); i++) {
			
			String areaCode=visitorList.get(i).getAreaCode();
			String sigunguCode=visitorList.get(i).getSigunguCode();
			String locationName = getLocationName(areaCode, sigunguCode);
			
			visitorList.get(i).setLocationName(locationName);
		}
		
		return visitorList;
	}

	@Override
	public List<AttractionVO> getAttractionRank() {
		
		String prevMonth = getPrevMonth();
		
		List<AttractionVO> attractionList = statisticsMapper.getAttractionRank(prevMonth);
		
		for(int i = 0; i<attractionList.size(); i++) {
			
			String areaCode=attractionList.get(i).getAreaCode();
			String sigunguCode=attractionList.get(i).getSigunguCode();
			String locationName = getLocationName(areaCode, sigunguCode);
			
			attractionList.get(i).setLocationName(locationName);
			
			System.out.println(locationName);
		}
		
		return attractionList;
	}
}
