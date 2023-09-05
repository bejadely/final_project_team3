package com.trip.finalProject.main.service.impl;


import com.trip.finalProject.main.mapper.MainMapper;
import com.trip.finalProject.main.service.MainService;
import com.trip.finalProject.main.service.MainSpecialtiesVO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainServiceImpl implements MainService {

	@Autowired
	MainMapper mainMapper;
	
	@Override
	public List<MainSpecialtiesVO> getSpecialtiesInfo() {
		
		List<MainSpecialtiesVO> specialtiesList = mainMapper.getSpecialtiesInfo();
		
		String areaCode = "";
		String sigunguCode = "";
		String locationName = "";
		for(MainSpecialtiesVO vo : specialtiesList) {
		    areaCode = vo.getAreaCode();
		    sigunguCode = vo.getSigunguCode();
		    locationName = getLocationName(areaCode, sigunguCode);
		    vo.setLocationName(locationName);
		}
		
		return specialtiesList;
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
}
