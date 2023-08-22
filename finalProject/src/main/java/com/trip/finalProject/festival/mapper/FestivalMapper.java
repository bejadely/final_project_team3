package com.trip.finalProject.festival.mapper;

import com.trip.finalProject.festival.service.FestivalInfoVO;

import java.util.List;

public interface FestivalMapper {

    int getFestivalInfoAndSave(List<FestivalInfoVO> festivalInfoVOList);
}
