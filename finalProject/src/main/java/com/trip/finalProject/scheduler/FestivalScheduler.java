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
    (cron = "0 50 23 * * *") // 매일 오후 11시50분에 실행
    public void getFestivalInfoAndSave() throws Exception {

        festivalService.getFestivalInfoAndSave();
    }
}
