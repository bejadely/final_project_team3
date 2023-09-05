package com.trip.finalProject.calculation.service;

import static org.quartz.JobBuilder.newJob;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.trip.finalProject.calculation.service.impl.CalculationQuartzJob;

// 스케줄러 설정파일
@Configuration
public class JobConfig {
	
	@Autowired
	private Scheduler scheduler;
	
	@PostConstruct
	public void run() {
		JobDetail jobDetail = runJobDetail(CalculationQuartzJob.class, new HashMap<>());
		
		try {
	        // 크론형식 지정 후 스케줄 시작
			scheduler.scheduleJob(jobDetail, runJobTrigger("0/10 * * * * ?"));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}
	
	public Trigger runJobTrigger(String scheduleExp){
    	// 크론 스케줄 사용
		return TriggerBuilder.newTrigger()
				.withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
	}
	
	public JobDetail runJobDetail(Class job, Map params){
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.putAll(params);
	    // 스케줄 생성
		return newJob(job).usingJobData(jobDataMap).build();
	}
}
