package com.trip.finalProject.guideMain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.guideMain.mapper.GuideMainMapper;
import com.trip.finalProject.guideMain.service.GuideMainService;
import com.trip.finalProject.guideMain.service.PackageRegistVO;

@Service
public class GuideMainServiceImpl implements GuideMainService {

	@Autowired
	GuideMainMapper guideMainMapper;
	
	@Override
	public List<PackageRegistVO> getPackageSaleInfo(String guideId) {
		
		return guideMainMapper.getPackageSaleInfo(guideId);
	}

}
