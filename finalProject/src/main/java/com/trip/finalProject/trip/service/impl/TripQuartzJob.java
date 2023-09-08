package com.trip.finalProject.trip.service.impl;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import com.trip.finalProject.trip.mapper.TripMapper;

@Service
public class TripQuartzJob extends QuartzJobBean{

	@Autowired
	TripMapper tripMapper;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		try {
			int result = tripMapper.completeTripRecord();
			
			if(result > 0) {
				// 작업중 : 로그남기기 / 관리자들에게 알림 날리기
				//System.out.println("여행기간이 지난 여행기록의 여행완료 처리되었습니다.");
			}		
					
		}catch (Exception e) {
			System.out.println(e);
		}
	}

}
