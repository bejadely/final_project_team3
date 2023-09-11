package com.trip.finalProject.packaged.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import com.trip.finalProject.packaged.mapper.PackageMapper;
import com.trip.finalProject.packaged.service.PackageVO;

@Service
public class PackageQuartzJob extends QuartzJobBean{
	@Autowired
	PackageMapper packageMapper;
	
	 @Override
	    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

	        try {
	            // 스케줄러 코드 작성
	        	

	            List<PackageVO> dataList = packageMapper.getPackageCalculate();
	            // 데이터를 저장할 리스트 생성
	            List<PackageVO> dataToInsert = new ArrayList<>();

	            // 데이터를 리스트에 저장
	            for (PackageVO data : dataList) {
	                // 필요한 가공 또는 조작을 수행하고 리스트에 추가
	                dataToInsert.add(data);
	            }
	            
	            for (PackageVO data : dataToInsert) {
	                packageMapper.packageCalculate(data);
	            }

	            packageMapper.packageUpdateDeadline();
	        } catch (Exception e) {
	            System.out.println("스케줄러 작업 중 오류 발생: " + e.getMessage());
	        }
	    }
	
	}