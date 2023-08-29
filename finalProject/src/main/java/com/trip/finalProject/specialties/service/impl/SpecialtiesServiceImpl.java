package com.trip.finalProject.specialties.service.impl;

import java.util.List;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.location.service.LocationVO;
import com.trip.finalProject.specialties.mapper.SpecialtiesMapper;
import com.trip.finalProject.specialties.service.SpecialtiesService;
import com.trip.finalProject.specialties.service.SpecialtiesVO;

@Service
public class SpecialtiesServiceImpl implements SpecialtiesService {

	@Autowired
	SpecialtiesMapper specialtiesMapper;
	
	@Override
	public SpecialtiesVO getSpecialtiesInfo(SpecialtiesVO specialtiesVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SpecialtiesVO> getSpecialtiesList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertSepcialties(SpecialtiesVO specialtiesVO) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<LocationVO> getLocationList() {
		// TODO Auto-generated method stub
		return specialtiesMapper.listArea();
	}

}
