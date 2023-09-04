package com.trip.finalProject.guideMain.mapper;

import java.util.List;

import com.trip.finalProject.guideMain.service.CalculationVO;
import com.trip.finalProject.guideMain.service.GuideQuestionVO;
import com.trip.finalProject.guideMain.service.PackagePurchaseVO;
import com.trip.finalProject.guideMain.service.PackageRegistVO;

public interface GuideMainMapper {

	public List<PackageRegistVO> getPackageSaleInfo(String guideId);

    List<GuideQuestionVO> getQuestionInfo(String guideId);

    List<CalculationVO> getCalculationInfo(String guideId);

    List<PackagePurchaseVO> getSaleChartInfo(String guideId);
}
