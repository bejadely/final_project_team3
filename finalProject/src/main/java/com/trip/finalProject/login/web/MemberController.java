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
	
		
	//카카오로그인
	
//	@Autowired 어노테이션이 적용된 필드 또는 메서드 파라미터에 해당하는 객체가 스프링 컨테이너에 빈으로 등록되어 있다면, 그 객체가 자동으로 해당 필드 또는 파라미터에 주입
	//그래서  MemberService 의 메서드 들을 컨트롤러에서 사용할 수 있게됨.
//	@Service 어노테이션은 해당 클래스를 스프링의 서비스 빈으로 등록하라는 의미입니다. 
// 그리고 이렇게 빈으로 등록된 MemberService를 다른 클래스에서 사용하려면 
// @Autowired 어노테이션을 사용하여 자동으로 주입할 수 있습니다:
	
	
	//@RequestMapping의 축약 버전
	//@GetMapping, @PostMapping, @PutMapping, @DeleteMapping 등
	
	//등록-페이지: URI memberInsert, RETURN -member/memberInsert
	//경로에 접근하면 메서드(memberInsertForm) 실행. 핸들러 메서드가 HTTP GET 요청을 처리할 수 있도록 구현되어 있어야 됨
	//      /memberInsert 여기에 접근하면 Http의 GET요청을 처리해주는 memberInsertForm메서드를 실행시킴. 
	//어노테이션은 HTTP GET 요청을 처리하는 핸들러 메서드(HTTP 요청을 처리하는 메서드)를 지정하는 역할. 핸들러 메서드는 memberInsertForm()이거임 
	//memberInsert 경로에 http의 Get을 요청함
	@GetMapping("memberInsert") 
	public String memberInsertForm() {//넘겨주고 받을게 없어서 매개변수 x 
		return"member/memberInsert";//등록페이지의 뷰를 반환함.(Get방식)(실제 jsp파일 경로)
	}
	
	//회원등록처리:URI- memberInsert, RETURN- 홈화면
	@PostMapping("memberInsert")//url에 접근하면 핸들러메서드를 실행
	public String memberInsertProcess(MemberVO memberVO) { //memberVO빈값x(input에 타이핑한게 request객체에 담겨서 이쪽으로 옴. controller에서 MemberVO에 담김) 
		memberService.insertMemberInfo(memberVO);//등록처리 insertMemberInfo 호출해서sql처리 . MemberService에 있는insertMemberInfo 메서드
		return "home";
		//return"redirect:memberList";//다시호출 해당 컨트롤러한테 보냄.(클라이언트 한테 갔다가 다시 컨틀롤러)
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
		//result에는 serviceImple에서의 메서의 login의 return값인 vo가 있을듯?
		
		// 매개변수로 집어넣은 HttpServlettRequest request 매개변수를 활용해, 거기 있는 *session을 끄집어 내서 활용
		//HttpSession은 session을 관리하기위한 인터페이스
		
		//request.getSession()은 현재 클라이언트의 세션을 얻기 위해 사용하는 메서드
		 
		 
		 //sessionId은 고유식별자 키,벨류
		 //변수 result가 참조하는 객체의 아이디(ID) 값을 가져오는 것을 의미합니다.
		 //getId() 메서드는 result가 참조하는 객체의 아이디를 반환하는 메서드
		 
		 
		//NullPointerException이 발생하는 이유는 result가 null인 상태에서 result.getId()를 호출하여 sessionId에 null 값을 설정하려고 하기 때문입니다. 
		 //따라서 result가 null인 경우에는 result.getId()를 호출하지 않도록 해야 합니다.
		
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
