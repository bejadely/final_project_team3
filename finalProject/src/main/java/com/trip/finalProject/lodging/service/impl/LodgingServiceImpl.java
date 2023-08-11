package com.trip.finalProject.lodging.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.lodging.mapper.LodgingMapper;
import com.trip.finalProject.lodging.service.LodgingService;
import com.trip.finalProject.lodging.service.LodgingVO;

@Service
public class LodgingServiceImpl implements LodgingService {
	 @Autowired
	 LodgingMapper lodgingMapper;	 

	 
	 @Override
	 public int insertLodgingInfo(LodgingVO lodginVO) {
		 return lodgingMapper.insertLodging(lodginVO);
	 }
	 
	 @Override
	 public List<LodgingVO> getLodgingList(){
		 return lodgingMapper.listLodging();
	 }

}
