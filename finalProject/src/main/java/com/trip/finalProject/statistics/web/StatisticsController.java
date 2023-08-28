package com.trip.finalProject.statistics.web;

import com.trip.finalProject.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

//오유리, 2023년 08월 : 지역관광통계페이지 관련 컨트롤러
@Controller
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @GetMapping("/localTourStatistics")
    public String localTourStatistics(){

        return "statistics/localTourStatistics";
    }

    @GetMapping("/visitor")
    @ResponseBody
    public Map<String,Integer> getVisitorData() {
        return statisticsService.getVisitorData();
    }

}
