package com.trip.finalProject.tourInfo.web;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.tourInfo.service.SpotDetailReviewVO;
import com.trip.finalProject.tourInfo.service.TourInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

//오유리, 2023년 08월 : 관광정보페이지 관련 컨트롤러
@Controller
@RequestMapping("localTourInfo")
public class TourInfoController {

    @Autowired
    TourInfoService tourInfoService;

    @Autowired
    HttpSession session;

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

        return tourInfoService.getTourInfoMap(areaCode, sigunguCode);
    }

    //  관광정보 페이지 각 지역별 정보를 ajax로 가져옴
    @GetMapping("/detail")
    @ResponseBody
    public Map<String, String> getSpotDetailInfo(int contentId, int contentTypeId) {

        return tourInfoService.getDetailInfoMap(contentId, contentTypeId);
    }
    
    
    @GetMapping("/spotDetail")
    public String getSpotDetail(@RequestParam(value = "page", defaultValue = "1") String page, String contentTypeId, String areaCode, String sigunguCode, Model model) {

    	//더보기 창 정보 받아오기
    	Map<String, Object> spotDetailMap = tourInfoService.getSpotDetail(page, contentTypeId, areaCode, sigunguCode);
        model.addAttribute("spotDetails", spotDetailMap.get("spotDetail"));
    	
    	//더보기 창으로 누른 지역, 콘텐츠코드를 이름으로 받기
    	model.addAttribute("contentName",tourInfoService.getContentNameDetail(contentTypeId));
    	model.addAttribute("locationName", tourInfoService.getLocationNameDetail(areaCode, sigunguCode));

        //페이징 정보
        model.addAttribute("pagingVo", tourInfoService.getSpotDetailPagingVo(page, spotDetailMap));
        model.addAttribute("contentTypeId", contentTypeId);
        model.addAttribute("areaCode", areaCode);
        model.addAttribute("sigunguCode", sigunguCode);

    	return "tourInfo/spotDetail";
    }

    //모달창 내 정보+리뷰 가져오기
    @GetMapping("/infoReview")
    @ResponseBody
    public Map<String,Object> getSpotDetailInfoReview(String contentId, String contentTypeId) {

        return tourInfoService.getDetailInfoReviewList(contentId, contentTypeId);
    }

    //모달창 내 리뷰 가져오기
    @GetMapping("/review")
    @ResponseBody
    public List<SpotDetailReviewVO> getSpotDetailReview(String contentId, int page) {

        return tourInfoService.getDetailReviewList(contentId, page);
    }

    //모달창 내 리뷰 등록
    @PostMapping("/review")
    @ResponseBody
    public Map<String,Object> reviewInsert(SpotDetailReviewVO spotDetailReviewVO) throws Exception {
        if(session.getAttribute("sessionId") != null && !session.getAttribute("sessionId").toString().equals("")) {
            spotDetailReviewVO.setWriterId(session.getAttribute("sessionId").toString());
        } else {
            throw new Exception("no login");
        }

        return tourInfoService.insertReviewInfo(spotDetailReviewVO);
    }

    //모달창 내 리뷰 삭제
    @DeleteMapping("/review")
    @ResponseBody
    public Map<String,Object> reviewDelete(int contentId, String writerId, String reviewId) throws Exception {
        String sessionId = "";
        if(session.getAttribute("sessionId") != null && !session.getAttribute("sessionId").toString().equals("")) {
            sessionId =  session.getAttribute("sessionId").toString();
        } else {
            throw new Exception("no login");
        }

        if(!sessionId.equals(writerId)){
            throw new Exception("not same");
        }

        return tourInfoService.deleteReviewInfo(contentId, reviewId);
    }
    
    //관광정보 검색 페이지
    @GetMapping("/search")
    public String searchDetail(String searchKeyWord, Model model) {
    
    	model.addAttribute("searchInfoList", tourInfoService.getsearchInfo(searchKeyWord));
    	model.addAttribute("searchKeyWord", searchKeyWord);
    	
    	return "tourInfo/searchDetail";
    }
}
