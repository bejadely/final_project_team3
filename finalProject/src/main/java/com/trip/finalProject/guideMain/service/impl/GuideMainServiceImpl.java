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
	public List<PackageCalculationVO> getCalculationInfo(String guideId) {

		return guideMainMapper.getCalculationInfo(guideId);
	}

	@Override
	public List<PackagePurchaseVO> getMonthSaleChartInfo(String guideId) {

		return guideMainMapper.getMonthSaleChartInfo(guideId);
	}

	@Override
	public int getPackageSalingCountInfo(String guideId) {
		
		return guideMainMapper.getPackageSalingCountInfo(guideId);
	}

	@Override
	public int getPackageSaledCountInfo(String guideId) {
		
		return guideMainMapper.getPackageSaledCountInfo(guideId);
	}

	@Override
	public int getUnanswereQuestion(String guideId) {
		
		return guideMainMapper.getUnanswereQuestion(guideId);
	}

	@Override
	public List<PackageRegistVO> getPackageSaleChartInfo(String guideId) {
		
		return guideMainMapper.getPackageSaleChartInfo(guideId);
	}

}
