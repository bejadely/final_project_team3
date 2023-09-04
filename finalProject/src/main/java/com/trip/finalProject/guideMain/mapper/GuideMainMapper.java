package com.trip.finalProject.guideMain.mapper;

import java.util.List;

import com.trip.finalProject.guideMain.service.PackageRegistVO;

public interface GuideMainMapper {

	public List<PackageRegistVO> getPackageSaleInfo(String guideId);

}
