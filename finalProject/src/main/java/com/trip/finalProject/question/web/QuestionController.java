package com.trip.finalProject.question.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
			  ,@RequestParam(value="nowPage", defaultValue="1") Integer nowPage
			  ,@RequestParam(value="cntPerPage", defaultValue="10") Integer cntPerPage) {
		int total = queService.Count();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		List<QuestionVO> guideQue = queService.getQueAll(pagingVO);
		
		model.addAttribute("guideQue", guideQue);
		model.addAttribute("paging", pagingVO);
				
		return"guide/question";
	}
	//단건조회
	@GetMapping("selectQue")
	public String selectQue(@RequestParam("questionId") String questionId, Model model) {
	    QuestionVO findVO = queService.getQueInfo(questionId); 
	    model.addAttribute("select", findVO);
	    return "redirect:/guideQue";
	}
}
