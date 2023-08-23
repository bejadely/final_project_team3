package com.trip.finalProject.login.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.finalProject.login.service.MemberService;
import com.trip.finalProject.login.service.MemberVO;
import com.trip.finalProject.login.service.NaverLoginVO;

//23.08.22 이경환 회원관리

//빈 자동생성 +컨트롤러 사용가능 등
 @Controller
 @RequestMapping("/")
public class MemberController {
	  
	@Autowired
	MemberService memberService;
	
	@Autowired
	NaverLoginVO naverLoginVO;
//	private String apiResult = null;
	
	//회원가입
	//넘겨주고 받을게 없어서 매개변수 x 
	//그냥 등록페이지의 뷰를 반환함.(Get방식)(return: 실제 경로)
	@GetMapping("member/memberInsert") 
	public String memberInsertForm() {
		return"member/memberInsert";
	}
	
	//form의 action에 따른 회원등록처리:URI RETURN- 홈화면
	 //memberVO빈값x(input에 타이핑한게 request객체에 담겨서 이쪽으로 옴. controller에서 MemberVO에 담김)
	@PostMapping("member/memberInsert")
	public String memberInsertProcess(MemberVO memberVO) { 
		
		memberService.insertMemberInfo(memberVO);
		return "redirect:/";
	}
	
	
	
	//멤버 로그인화면 호출
	@GetMapping("/member/login")
	public void loginMainForm(HttpSession session, Model model) {
		
		/* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginVO클래스의 getAuthorizationUrl메소드 호출 */
		String naverAuthUrl = naverLoginVO.getAuthorizationUrl(session);
		
		
		// System.out.println("네이버:" + naverAuthUrl);
		
		//네이버 
		model.addAttribute("url", naverAuthUrl);
		
		//return"member/login";
	}
	
	
	
	
	
	

	//form의 action에 따른 로그인 처리
	@PostMapping("/star")
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

	

	@RequestMapping("member/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
		
	}
	

	
	//회원가입 시 아이디 중복체크	  
	  @PostMapping("/idCheck") 
	  @ResponseBody 
	  public int idCheck(MemberVO memberVO) {
		
	
		Integer  result = memberService.checkId(memberVO);
		
		if (result == null) {
		    return 0;
		} else {
		    return result;
		}
	 
	  }
	  
	 //로그인 시 계정 DB확인 
	  @PostMapping("/loginAccountCheck") 
	  @ResponseBody 
	  public int loginAccountCheck(MemberVO memberVO) {
		
	
		Integer  result = memberService.loginAccountCheck(memberVO);
		
		if (result == null) {
		    return 0;
		} else {
		    return result;
		}
	 
	  }
	  
	  
	  
	 
	
 

}
