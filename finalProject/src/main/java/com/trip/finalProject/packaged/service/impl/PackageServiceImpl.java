package com.trip.finalProject.packaged.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.packaged.mapper.PackageMapper;
import com.trip.finalProject.packaged.service.PackageService;
import com.trip.finalProject.packaged.service.PackageVO;

@Service
public class PackageServiceImpl implements PackageService {
	
	@Autowired
	PackageMapper packageMapper;
	
	@Override
	public int insertEdirotInfo(PackageVO packageVO) {
		// TODO Auto-generated method stub
		return packageMapper.insertEditor(packageVO);
	}

	@Override
	public PackageVO packageInfo(PackageVO packageVO) {
		// TODO Auto-generated method stub
		return packageMapper.packageInfo(packageVO);
	}

	@Override
	public List<PackageVO> getPackageList() {
		// TODO Auto-generated method stub
		return packageMapper.listPackage();
	}

}
