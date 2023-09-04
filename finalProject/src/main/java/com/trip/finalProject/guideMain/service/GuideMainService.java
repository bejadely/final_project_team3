package com.trip.finalProject.guideMain.service;

import java.util.List;

public interface GuideMainService {

	List<PackageRegistVO> getPackageSaleInfo(String guideId);

    List<GuideQuestionVO> getQuestionInfo(String guideId);

    List<CalculationVO> getCalculationInfo(String guideId);

    List<PackagePurchaseVO> getSaleChartInfo(String guideId);
}
