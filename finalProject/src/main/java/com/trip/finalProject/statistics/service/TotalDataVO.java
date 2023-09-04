package com.trip.finalProject.statistics.service;

import lombok.Data;

import java.util.List;

@Data
public class TotalDataVO {
    List<AttractionVO> attractionVOList;
    List<FellowVO> fellowVOList;
    List<RestaurantVO> restaurantVOList;
    List<TripDataVO> tripVOList;
    List<SnsVO> snsVOList;
}
