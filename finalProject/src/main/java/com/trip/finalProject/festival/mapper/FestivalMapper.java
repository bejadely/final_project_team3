package com.trip.finalProject.festival.mapper;

import com.trip.finalProject.festival.service.FestivalInfoVO;

import java.util.List;

public interface FestivalMapper {

    int getFestivalInfoAndSave(List<FestivalInfoVO> festivalInfoVOList);

	List<FestivalInfoVO> getFestivalCalendarInfo();

	List<FestivalInfoVO> getFestivalListInfo();

	List<FestivalInfoVO> getFestivalNewList(String year, String month);

	Integer modifyFestivalInfo(FestivalInfoVO festivalInfoVO);

	Integer removeFestivalInfo(String contentId);
}
