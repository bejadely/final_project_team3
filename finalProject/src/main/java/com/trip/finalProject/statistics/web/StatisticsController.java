package com.trip.finalProject.statistics.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//오유리, 2023년 08월 : 지역관광통계페이지 관련 컨트롤러
@Controller
public class StatisticsController {

    @GetMapping("/localTourStatistics")
    public String localTourStatistics(){

        return "statistics/localTourStatistics";
    }

}
