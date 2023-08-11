package com.trip.finalProject.login.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trip.finalProject.login.service.MemberService;
import com.trip.finalProject.login.service.MemberVO;

//빈 자동생성 +컨트롤러 사용가능 등
 @Controller
 @RequestMapping("/")
public class MemberController {
	  
		//로그인화면 호출
	
	
	@Autowired
	MemberService memberService;
	
		//회원가입
	@GetMapping("member/memberInsert") 
	public String memberInsertForm() {//넘겨주고 받을게 없어서 매개변수 x 
		return"member/memberInsert";//등록페이지의 뷰를 반환함.(Get방식)(실제 jsp파일 경로)
	}
	
	//회원등록처리:URI- memberInsert, RETURN- 홈화면
	@PostMapping("member/memberInsert1")//url에 접근하면 핸들러메서드를 실행
	public String memberInsertProcess(MemberVO memberVO) { //memberVO빈값x(input에 타이핑한게 request객체에 담겨서 이쪽으로 옴. controller에서 MemberVO에 담김) 
		memberService.insertMemberInfo(memberVO);//등록처리 insertMemberInfo 호출해서sql처리 . MemberService에 있는insertMemberInfo 메서드
		return "redirect:/";
	}
	
	
	
	//로그인화면 호출
	
	@GetMapping("/member/login")//url에 접근하면 메서드 실행
	public String loginMainForm() {
		
		return"member/login"; //로그인페이지 호출
	}
	
	
	
	/*
	 * @GetMapping("login") public String loginForm() { return"member/login"; }
	 */
	
	
	//로그인 처리
	@PostMapping("member/star")//url접근하면 메서드 실행
	public String login(@ModelAttribute MemberVO memberVO, Model model, HttpServletRequest request) {
		
		// DB와의 작업은 처리완료
		MemberVO result = memberService.login(memberVO);
		System.out.println(result);
	
		
		if(result != null) {
			HttpSession session = request.getSession();
			session.setAttribute("sessionId", result.getMemberId());
			session.setAttribute("sessionName", result.getMemberName());
			
			
			model.addAttribute("message", result.getMemberName() + "님 @환영합니다.");
			
			return "redirect:/";
			
		}else {
	        model.addAttribute("message", "아이디 또는 비밀번호가 올바르지 않습니다.");

			return "member/login"; //"member/login"
			
		}
	}
	
	// 로그인시 예외처리
//	@GetMapping("login")//url에 접근하면 메서드 실행
//	public String loginException() {
//		memberService.login();
//		return"member/login"; //로그인페이지 호출
//	}
	
	

	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "home";
		
	}
	
	

}
