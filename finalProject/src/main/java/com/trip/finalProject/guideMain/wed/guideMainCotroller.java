package com.trip.finalProject.guideMain.wed;

import javax.servlet.http.HttpSession;

import com.trip.finalProject.guideMain.service.PackagePurchaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.trip.finalProject.guideMain.service.GuideMainService;

import java.util.List;

@Controller
public class guideMainCotroller {

	@Autowired
	GuideMainService guideMainService;
	
	@Autowired
	HttpSession session;
	
    @GetMapping("/guide/main")
    public String guideMainPage(Model model){

    	//아이디 값 가져오기
    	String guideId=session.getAttribute("sessionId").toString();
    	
    	//판매중 건 수
    	
    	//판매완료 건 수
    	
    	//미답변 내역
    	
    	
    	//패키지 판매 내역
    	model.addAttribute("packageSaleList", guideMainService.getPackageSaleInfo(guideId));

		List<PackagePurchaseVO> saleChartInfo = guideMainService.getSaleChartInfo(guideId);
		System.out.println("saleChartInfo = " + saleChartInfo);
		//패키지 매출 통계
		model.addAttribute("saleChartList", guideMainService.getSaleChartInfo(guideId));
    	
    	//입금 내역
		model.addAttribute("calculationList", guideMainService.getCalculationInfo(guideId));
    	
    	//최근 문의
    	model.addAttribute("questionList", guideMainService.getQuestionInfo(guideId));

        return "guide/main";
    }
}
