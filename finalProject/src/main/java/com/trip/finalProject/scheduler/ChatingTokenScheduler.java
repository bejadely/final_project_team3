package com.trip.finalProject.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.trip.finalProject.tripMate.service.TripMateService;

public class ChatingTokenScheduler {
	
	@Autowired
    TripMateService tripMateService;

    //축제정보 api로 받아와서 db등록
    @Scheduled
    (cron = "0 30 0 * * *") // 매일 오후 11시50분에 실행
    public void getChatingToken() throws Exception {

        tripMateService.getChatingToken();
    }
}
