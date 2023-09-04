package com.trip.finalProject.guideMain.service.impl;

import java.util.List;

import com.trip.finalProject.guideMain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.guideMain.mapper.GuideMainMapper;

@Service
public class GuideMainServiceImpl implements GuideMainService {

	@Autowired
	GuideMainMapper guideMainMapper;
	
	@Override
	public List<PackageRegistVO> getPackageSaleInfo(String guideId) {
		
		return guideMainMapper.getPackageSaleInfo(guideId);
	}

	@Override
	public List<GuideQuestionVO> getQuestionInfo(String guideId) {

		return guideMainMapper.getQuestionInfo(guideId);
	}

	@Override
	public List<CalculationVO> getCalculationInfo(String guideId) {

		return guideMainMapper.getCalculationInfo(guideId);
	}

	@Override
	public List<PackagePurchaseVO> getSaleChartInfo(String guideId) {

		return guideMainMapper.getSaleChartInfo(guideId);
	}

}
