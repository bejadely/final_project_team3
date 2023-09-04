package com.trip.finalProject.guideMain.wed;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class guideMainCotroller {

    @GetMapping("/guide/main")
    public String guideMainPage(){

        return "guide/main";
    }
}
