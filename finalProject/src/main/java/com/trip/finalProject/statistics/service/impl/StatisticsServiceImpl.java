package com.trip.finalProject.statistics.service.impl;

import com.trip.finalProject.statistics.mapper.StatisticsMapper;
import com.trip.finalProject.statistics.service.AttractionVO;
import com.trip.finalProject.statistics.service.StatisticsService;
import com.trip.finalProject.statistics.service.TotalDataVO;
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

    @Override
    public List<TotalDataVO> getTotalData(String locationName) {
        List<TotalDataVO> totalDataVOList = new ArrayList<>();
        TotalDataVO totalDataVO = new TotalDataVO();

        Map<String,String> areaSigunguCode = getAreaSigunguCode(locationName);
        String areaCode = areaSigunguCode.get("areaCode");
        String sigunguCode = areaSigunguCode.get("sigunguCode");

        totalDataVO.setFellowVOList(statisticsMapper.getFellowList(areaCode, sigunguCode));
        totalDataVO.setRestaurantVOList(statisticsMapper.getRestaurantList(areaCode, sigunguCode));
        totalDataVO.setAttractionVOList(statisticsMapper.getAttractionList(areaCode, sigunguCode));
        totalDataVO.setTripVOList(statisticsMapper.getTripList(areaCode, sigunguCode));
        totalDataVO.setSnsVOList(statisticsMapper.getSnsList(areaCode, sigunguCode));

        totalDataVOList.add(totalDataVO);

        System.out.println("totalDataVOList = " + totalDataVOList);

        return totalDataVOList;
    }

    private Map<String,String> getAreaSigunguCode(String locationName) {
        Map<String, String> areaSigunguCodeMap = new HashMap<>();
        String areaCode = "";
        String sigunguCode = "0";

        switch(locationName) {
            case "대구":
                areaCode = "4";
                break;
            case "부산":
                areaCode = "6";
                break;
            case "울산":
                areaCode = "7";
                break;
            case "안동":
                areaCode = "35";
                sigunguCode = "11";
                break;
            case "경주":
                areaCode = "35";
                sigunguCode = "2";
                break;
            case "포항":
                areaCode = "35";
                sigunguCode = "23";
                break;
            case "거제":
                areaCode = "36";
                sigunguCode = "1";
                break;
            case "통영":
                areaCode = "36";
                sigunguCode = "17";
                break;
        }

        areaSigunguCodeMap.put("areaCode", areaCode);
        areaSigunguCodeMap.put("sigunguCode", sigunguCode);

        return areaSigunguCodeMap;
    }
}
