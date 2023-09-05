package com.trip.finalProject.calculation.service.impl;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import com.trip.finalProject.calculation.mapper.CalculationMapper;

@Service
public class CalculationQuartzJob extends QuartzJobBean{
	
	@Autowired
	CalculationMapper calculationMapper;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		try {
			int result = calculationMapper.quartzCalMonthlyProc();

			if(result >= 0) {
				// 작업중 : 로그남기기 / 관리자들에게 알림 날리기
				System.out.println("월말정산이 완료되었습니다.");
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

}
