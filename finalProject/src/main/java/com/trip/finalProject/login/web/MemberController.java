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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trip.finalProject.adminMember.service.AdminMemberService;
import com.trip.finalProject.adminMember.service.AdminMemberVO;
import com.trip.finalProject.common.mapper.CommonMapper;
import com.trip.finalProject.login.service.MemberService;
import com.trip.finalProject.login.service.MemberVO;
import com.trip.finalProject.login.service.NaverLoginVO;

//23.08.22 이경환 회원관리

//빈 자동생성 +컨트롤러 사용가능 등
 @Controller
 @RequestMapping("/")
public class MemberController {
	@Autowired
	CommonMapper commonMapper;
	  
	@Autowired
	MemberService memberService;
	
	@Autowired
	NaverLoginVO naverLoginVO;
	
    @Autowired
    HttpSession session;
    
    @Autowired
    AdminMemberService adminMemberService;
//	private String apiResult = null;
	
	//회원가입
	//넘겨주고 받을게 없어서 매개변수 x 
	//그냥 등록페이지의 뷰를 반환함.(Get방식)(return: 실제 경로)
	@GetMapping("/member/memberInsert") 
	public String memberInsertForm() {
		return"/member/memberInsert";
	}
	
	//form의 action에 따른 회원등록처리:URI RETURN- 홈화면
	 //memberVO빈값x(input에 타이핑한게 request객체에 담겨서 이쪽으로 옴. controller에서 MemberVO에 담김)
	@PostMapping("member/memberInsert")
	public String memberInsertProcess(MemberVO memberVO) { 
		
		memberService.insertMemberInfo(memberVO);
		return "redirect:/";
	}

	//가이드 회원가입 폼 호출
	@GetMapping("/member/guideInsert") 
	public String guideInsertForm() {
		return"member/guideInsert";
	}
	
	//가이드 회원가입 처리
	@PostMapping("/guideInsert")
	public String guideInsertForm(MemberVO memberVO) { 		
		 memberService.insertGuide(memberVO);
		return "redirect:/";
	}
	


	
	
	
	
	
	//멤버 로그인화면 호출
	@GetMapping("/member/login")
	public void loginMainForm( Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		System.out.println(session.getId());
		/* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginVO클래스의 getAuthorizationUrl메소드 호출 */
		String naverAuthUrl = naverLoginVO.getAuthorizationUrl(session);
		
		
		System.out.println("네이버:" + naverAuthUrl);
		
		//네이버 
		model.addAttribute("url", naverAuthUrl);
		
		//return"member/login";
	}
	
	
	
	
	
	

	//form의 action에 따른 일반로그인 처리
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

			return "/"; //"member/login"
			
		}
	}

	

	@RequestMapping("member/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
		
	}
	

	
	//회원 가입시 아이디 중복체크	  
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
	  
	 //로그인시  회원계정 DB확인 
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
		//아이디 찾기 
		@GetMapping("/findAccount")
		public String fintId() {
			return"member/findAccount";
		}
		
		
		//비밀번호 찾기 
		@GetMapping("/findPassword")
		public String findPassword() {
			return"member/findPassword";
		}
		
		//입력한 전화번호로 계정 ID 찾기
		@ResponseBody
		@PostMapping("/phoneNumberCheck")
		public MemberVO phoneNumberCheck(String phoneNumber) {
			
			return memberService.phoneNumberCheck(phoneNumber);
		}
		
		
		//비밀번호 찾기. 이메일 인증 후  비밀번호 업데이트 폼 호출
		@PostMapping("/updatePaswordForm")
		public String updatePassword(MemberVO memberVO,Model model) {
			//memberService.insertMemberInfo(memberVO);
		System.out.println(memberVO);
		model.addAttribute("email", memberVO.getEmail());
		System.out.println(memberVO.getEmail());
			return "member/updatePassword";
		}
	
		//비밀번호 찾기.변경할 비밀번호로 업데이트 시행하기 
		@PostMapping("/EditPassword")
		public String EditPassword(MemberVO memberVO){
			System.out.println(memberVO);
			memberService.editPassword(memberVO);
			return "/member/login";
		}
		
		
		//마이페지이지 사용중 ================================================================================
		//회원정보 가져오기
		@GetMapping("/common/myPage")
		public String memberInfo(MemberVO memberVO, Model model,HttpServletRequest request) {
			memberVO.setMemberId(session.getAttribute("sessionId").toString());  //to do 
			MemberVO findVO = memberService.memberInfo(memberVO);
			model.addAttribute("memberInfo", findVO);
			return "myPage/info/myPage";
		}
		
		//회원수정 폼 호출
		@GetMapping("/common/myPageUpdate")
		public String memberUpdate(MemberVO memberVO, Model model) {
			memberVO.setMemberId(session.getAttribute("sessionId").toString());
			MemberVO findVO = memberService.memberInfo(memberVO);
			model.addAttribute("B",commonMapper.selectCode("0"));
			model.addAttribute("memberInfo", findVO);
			return "myPage/info/myPageUpdate";
		}
		
		//회원수정 프로세서
		@PostMapping("/common/myPageUpdate")
		public String memberUpdatePro(MemberVO memberVO, RedirectAttributes rtt){
			memberVO.setMemberId(session.getAttribute("sessionId").toString());
			System.out.println("tstst : " + memberVO );
			String result = memberService.updateMember(memberVO);
			rtt.addFlashAttribute("result", result);
			return "redirect:/common/myPage?memberId=" + memberVO.getMemberId();
		}
		
		@PostMapping("/common/deleteData")
		public String withdrawMember(AdminMemberVO adminVO, RedirectAttributes rtt) {
			adminVO.setMemberId(session.getAttribute("sessionId").toString());
			// 회원 삭제
			String result = adminMemberService.withdrawMember(adminVO);
			
			// 리다이렉트 어트리뷰트에 결과값 담기(성공 : success / 실패 : fail)
			rtt.addAttribute("result", result);
			
			session.invalidate();
			
			return "redirect:/";
			
		}
	
 

}
