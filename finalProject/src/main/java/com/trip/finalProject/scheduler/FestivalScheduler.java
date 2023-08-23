package com.trip.finalProject.scheduler;

import com.trip.finalProject.festival.service.FestivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FestivalScheduler {

    @Autowired
    FestivalService festivalService;

    //축제정보 api로 받아와서 db등록
    @Scheduled
    (cron = "0 0 3 ? * MON") // 매주 월요일 오전 3시에 실행
    public void getFestivalInfoAndSave() throws Exception {

        festivalService.getFestivalInfoAndSave();
    }
}
