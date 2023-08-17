package com.trip.finalProject.tourInfo.web;

import com.trip.finalProject.tourInfo.service.TourInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("localTourInfo")
public class TourInfoController {

    @Autowired
    TourInfoService tourInfoService;

    //  메인이 아닌 관광정보 페이지
    @GetMapping("")
    public String localTourInfo(Model model) {

        Map<String, Object> tourInfoMap = tourInfoService.getTourInfoMap(4, 0);  //  1페이지, 대구일 때 areaCode = 4, sigunguCode는 없음

        model.addAttribute("tourSpot", tourInfoMap.get("tourSpot"));
        model.addAttribute("foodSpot", tourInfoMap.get("foodSpot"));
        model.addAttribute("shoppingSpot", tourInfoMap.get("shoppingSpot"));
        model.addAttribute("cultureSpot", tourInfoMap.get("cultureSpot"));
        model.addAttribute("leportsSpot", tourInfoMap.get("leportsSpot"));

        return "tourInfo/localTourInfo";
    }

    //  관광정보 페이지 각 지역별 정보를 ajax로 가져옴
    //  @PathVariable : url을 변수로 받아와서 유동적으로 쓸 수 있게 함
    @GetMapping("/{areaCode}")
    @ResponseBody
    public Map<String, Object> getTourInfoByLocation(@PathVariable("areaCode") int areaCode, int sigunguCode) {

        Map<String, Object> tourInfoMap = tourInfoService.getTourInfoMap(areaCode, sigunguCode);

        return tourInfoMap;
    }

    //  관광정보 페이지 각 지역별 정보를 ajax로 가져옴
    @GetMapping("/detail")
    @ResponseBody
    public Map<String, String> getSpotDetailInfo(int contentId, int contentTypeId) {

        Map<String, String> spotDetailInfo = tourInfoService.getDetailInfoMap(contentId, contentTypeId);

        return spotDetailInfo;
    }
    
    //해당 지역에 해당하는 각 spot별 더보기창으로 이동
    @GetMapping("/spotDetail")
    public String getTourSpot(String contentId, String areaCode, Model model) {
    	
    	model.addAttribute("contentId",contentId);
    	model.addAttribute("areaCode", areaCode);
    	
    	
    	
    	return "tourInfo/spotDetail";
    }

}
