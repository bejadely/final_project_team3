package com.trip.finalProject.question.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.question.service.QuestionService;
import com.trip.finalProject.question.service.QuestionVO;

@Controller
@RequestMapping("/")
public class QuestionController {
	@Autowired
	QuestionService queService;
	
	//전체조회
	@GetMapping("guideQue")
	public String quideQue(Model model
			  , QuestionVO questionVO
			  ,@RequestParam(value="nowPage", defaultValue="1") Integer nowPage
			  ,@RequestParam(value="cntPerPage", defaultValue="10") Integer cntPerPage) {
		String answerMemberId = "101";
		int total = queService.Count(answerMemberId);
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		questionVO.setAnswerMemberId(answerMemberId);
		List<QuestionVO> guideQue = queService.getQueAll(questionVO, pagingVO);
		
		model.addAttribute("guideQue", guideQue);
		model.addAttribute("paging", pagingVO);
				
		return"guide/question";
	}
	//등록 - process
		@PostMapping("queInsert")
		@ResponseBody
		public Map<String, String> empInsertProcess(@RequestBody QuestionVO queVO) {
			return queService.insertQueInfo(queVO);
		}
		
		//수정 - process
		@PostMapping("queUpdate")
		@ResponseBody
		public Map<String, String> empUpdateProcess(@RequestBody QuestionVO queVO) {
			return queService.updateQueInfo(queVO);
		}
		
	//일반사용자
	//전체 조회	
		@GetMapping("common/memberQue")
		public String memberQue(Model model
				  ,QuestionVO questionVO
				  ,@RequestParam(value="nowPage", defaultValue="1") Integer nowPage
				  ,@RequestParam(value="cntPerPage", defaultValue="10") Integer cntPerPage) {
			
			String memberId = "101";
			 
			
			int total = queService.memberCount(memberId);
			PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
			questionVO.setMemberId(memberId);
			
			System.out.println("test 1: " + questionVO.toString());
			List<QuestionVO> memberQue = queService.getQueAllMember(questionVO, pagingVO);
			System.out.println("test 2: " + memberQue);
			
			model.addAttribute("guideQue", memberQue);
			model.addAttribute("paging", pagingVO);
					
			return"myPage/memberQuestion";
		}
		
	// 0903 창민 start
	// 문의 작성 폼 호출
	@GetMapping("/common/memberQueForm")
	public String realMemberQueForm(HttpServletRequest request, QuestionVO questionVO ,Model model){
		
		String prevUrl = request.getHeader("referer");
		
		String productHead = "";
		
		// 문의 종류 파악
		if(questionVO.getProductId() != null) {
			productHead = questionVO.getProductId().substring(0, 3);
		} 
		
		switch (productHead) {
		case "PKG":
			questionVO.setQuestionType("패키지 문의");
			break;
		case "SPE":
			questionVO.setQuestionType("특산물 문의");
			break;
		case "LOD":
			questionVO.setQuestionType("숙박상품 문의");
			break;
		default:
			questionVO.setQuestionType("일반 문의");
			break;
		}
		
		// 모델에 필요한 정보 담기
		model.addAttribute("prevUrl", prevUrl);
		model.addAttribute("QuestionVO", questionVO);
		
		return "/question/productQuestionWriteForm";
	}
	
	
	// 문의 작성
	@PostMapping("/common/insertQueProc")
	public String insertQueProc(QuestionVO questionVO, RedirectAttributes rtt) {
		
		// 문의글 등록
		String message = queService.insertQuestion(questionVO);
		
		// 이전페이지에 대한 정보 담기
		String prevUrl = questionVO.getPrevUrl();
		
		// 메시지 담기
		rtt.addFlashAttribute("message", message);
		
		return "redirect:" + prevUrl;
	}
	
	//문의 전체 조회
	@GetMapping("/admin/seeAllQuestion")
	public String seeAllPunish(Model model
							 , @RequestParam( name = "searchBy", defaultValue = "name" ) String searchBy
							 , @RequestParam( name = "keyword", defaultValue = "" ) String keyword
				             , @RequestParam( name = "nowPage", defaultValue = "1") Integer nowPage
				             , @RequestParam( name = "cntPerPage", defaultValue = "10")Integer cntPerPage) {
		
		// 문의 내역 전체 조회
		 Map<String, Object> map = queService.selectAllQuestion(nowPage, cntPerPage);
		
		// 모든 회원 정보 모델에 담기
		model.addAttribute("list", map.get("list"));
		model.addAttribute("paging", map.get("PagingVO"));
		
		// 검색어가 없을 경우를 대비한 구문
		model.addAttribute("keyword", keyword);
		model.addAttribute("searchBy", searchBy);
		
		
		return "admin/question/seeAllQuestion";
	}
	
	// 문의 답변
	@PostMapping("/admin/answerQuestion")
	@ResponseBody
	public String ajaxAnswerQuestion(QuestionVO questionVO) {
		
		// 문의 답변 등록하기
		
		
		return "냠";
	}
		
		
}
