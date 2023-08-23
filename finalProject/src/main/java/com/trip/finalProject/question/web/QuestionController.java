package com.trip.finalProject.question.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		
}
