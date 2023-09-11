package com.trip.finalProject.packaged.service.impl;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import com.trip.finalProject.packaged.mapper.PackageMapper;

@Service
public class PackageQuartzJob extends QuartzJobBean{
	@Autowired
	PackageMapper packageMapper;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		try {
			int result = packageMapper.packageCalculate();
			
			result = packageMapper.packageUpdateDeadline();
			
			if(result > 0) {
				
				System.out.println("여행기간이 지난 여행기록의 여행완료 처리되었습니다.");
			}		
					
		}catch (Exception e) {
			System.out.println(e);
		}
	}
}
