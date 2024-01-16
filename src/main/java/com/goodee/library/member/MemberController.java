package com.goodee.library.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	MemberService memberService;
	// 회원가입 화면 이동
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String openMemberCreate() {
		LOGGER.info("[MemberController] openMemberCreate();");
		
		return "member/create";
	}
	
	//회원가입 기능 수행
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String createMember(MemberVo vo) {
		LOGGER.info("[MemberController] createMember();");
		String nextPage="member/create_success";
		if(memberService.createMember(vo) <= 0) {
			nextPage = "member/create_fail";
		}
		return nextPage;
		
	}
	//로그인 화면 이동
	@RequestMapping(value = "/login", method=RequestMethod.GET)
	public String openLoginForm() {
		LOGGER.info("[MemberController] openLoginForm();");
		
		return "member/login_form";
	}
	
	// 로그인 기능
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String loginMember(MemberVo vo, HttpSession session ) {
		LOGGER.info("[MemberController] loginMember();");
		String  nextPage = "member/login_success";
		MemberVo loginedMember = memberService.loginMember(vo);
		
		if(loginedMember == null) {
			nextPage = "member/login_fail";
		}else {
			session.setAttribute("loginMember", loginedMember);
			session.setMaxInactiveInterval(60*30);
		}
		return nextPage;
	}
	
	//로그아웃
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logoutMember(HttpSession session) {
		
		LOGGER.info("[MemberController] logoutMember();");
		session.invalidate();
		return "redirect:/";
		
	}
	
//	//회원 목록 이동 modelAndView(1)
//	@RequestMapping(method = RequestMethod.GET)
//	public ModelAndView listupMember() {
//		LOGGER.info("[MemberCotroller] listupMember();");
//		//1. 목록 정보 조회
//		List<MemberVo> memberVos = memberService.listupMember();
//		//2. 목록 전달
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("memberVos",memberVos);
//		//3. 뷰 선택
//		mav.setViewName("member/listup");
//		
//		return mav;
//		
//	}
	
//	//회원 목록 이동 modelAndView(2)
//	@RequestMapping(method = RequestMethod.GET)
//	public ModelAndView listupMember(ModelAndView mav) {
//		LOGGER.info("[MemberCotroller] listupMember();");
//		//1. 목록 정보 조회
//		List<MemberVo> memberVos = memberService.listupMember();
//		//2. 목록 전달
//	
//		mav.addObject("memberVos",memberVos);
//		//3. 뷰 선택
//		mav.setViewName("member/listup");
//		
//		return mav;
//		
//	}
	
	//회원 목록 이동 model
	@RequestMapping(method = RequestMethod.GET)
	public String listupMember(Model model) {
		LOGGER.info("[MemberCotroller] listupMember();");
		//1. 목록 정보 조회
		List<MemberVo> memberVos = memberService.listupMember();
		//2. 목록 전달
		model.addAttribute("memberVos",memberVos);
		return "member/listup";
		
	}

}

































