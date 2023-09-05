package com.trip.finalProject.guideMain.mapper;

import java.util.List;

import com.trip.finalProject.guideMain.service.GuideQuestionVO;
import com.trip.finalProject.guideMain.service.PackageCalculationVO;
import com.trip.finalProject.guideMain.service.PackagePurchaseVO;
import com.trip.finalProject.guideMain.service.PackageRegistVO;

public interface GuideMainMapper {

	public List<PackageRegistVO> getPackageSaleInfo(String guideId);

    List<GuideQuestionVO> getQuestionInfo(String guideId);

    List<PackageCalculationVO> getCalculationInfo(String guideId);

    List<PackagePurchaseVO> getMonthSaleChartInfo(String guideId);

	public int getPackageSalingCountInfo(String guideId);

	public int getPackageSaledCountInfo(String guideId);

	public int getUnanswereQuestion(String guideId);

	public List<PackageRegistVO> getPackageSaleChartInfo(String guideId);
}
