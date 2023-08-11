package com.trip.finalProject.tourInfo.web;

import com.trip.finalProject.tourInfo.service.TourInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class TourInfoController {

    @Autowired
    TourInfoService tourInfoService;

    @GetMapping("localTourInfo")
    public String localTourInfo(Model model) {

        Map<String, Object> tourInfoMap = tourInfoService.getTourInfoMap();

        model.addAttribute("tourSpot", tourInfoMap.get("tourSpot"));
        model.addAttribute("foodSpot", tourInfoMap.get("foodSpot"));
        model.addAttribute("shoppingSpot", tourInfoMap.get("shoppingSpot"));
        model.addAttribute("cultureSpot", tourInfoMap.get("cultureSpot"));
        model.addAttribute("leportsSpot", tourInfoMap.get("leportsSpot"));

        return "tourInfo/localTourInfo";
    }
}
