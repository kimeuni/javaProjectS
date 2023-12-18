package com.spring.javaProjectS.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javaProjectS.service.MemberService;
import com.spring.javaProjectS.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	// 로그인 화면 폼 보여주기
	@RequestMapping(value = "/memberLogin", method = RequestMethod.GET)
	public String memberLoginGet(HttpServletRequest request) {
		String cMid = "";
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(int i=0; i<cookies.length; i++){
				if(cookies[i].getName().equals("cMid")){
					cMid = cookies[i].getValue();
					request.setAttribute("cMid", cMid);
				}
			}
		}
		return "member/memberLogin";
	}
	
	// 로그인하기
	@RequestMapping(value = "/memberLogin", method = RequestMethod.POST)
	public String memberLoginPost(Model model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name="mid", defaultValue = "hkd1234", required = false) String mid,
			@RequestParam(name="pwd", defaultValue = "1234", required = false) String pwd,
			@RequestParam(name="idCheck", defaultValue = "NO", required = false) String idCheck) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		
		if(vo != null && vo.getUserDel().equals("NO") && passwordEncoder.matches(pwd, vo.getPwd())) {
			// 로그인 성공시 처리할 내용들.... (1. 세션저장, 2. 쿠키저장, 3. 방문횟수(총방문횟수,오늘방문횟수) 4. 포인트저장 등....)
			String strLevel = "";
			if(vo.getLevel() == 0) strLevel = "관리자";
			if(vo.getLevel() == 1) strLevel = "우수회원";
			if(vo.getLevel() == 2) strLevel = "정회원";
			if(vo.getLevel() == 3) strLevel = "준회원";
			
			// 1. 세션저장
			session.setAttribute("sMid", mid);
			session.setAttribute("sNickName", vo.getNickName());
			session.setAttribute("sLevel", vo.getLevel());
			session.setAttribute("strLevel", strLevel);
			
			// 2. 쿠키 저장
			if(idCheck.equals("save")) {
				Cookie cookie = new Cookie("cMid", mid);
				cookie.setMaxAge(60*60*24*5);
//				cookie.setPath("/");
				
				response.addCookie(cookie);
			}
			// 아이디 저장 체크 x시 쿠기 삭제
			else if(idCheck.equals("NO")) {
				Cookie[] cookies = request.getCookies();
				for(int i=0; i<cookies.length; i++) {
					if(cookies[i].getName().equals("cMid")) {
						cookies[i].setMaxAge(0);
//						cookies[i].setPath("/");
						
						response.addCookie(cookies[i]);
						break;
					}
				}
			}
			
			model.addAttribute("mid",mid);
			
			return "redirect:/message/memberLoginOk";
		}
		else {
			return "redirect:/message/memberLoginNo";
		}
	}
	
	// 로그아웃하기
	@RequestMapping(value = "/memberLogout", method = RequestMethod.GET)
	public String memberLogoutGet(HttpSession session, Model model ){
		String mid = (String)session.getAttribute("sMid");
		
		session.invalidate();
		
		model.addAttribute("mid",mid);
		return "redirect:/message/memberLogoutOk";
	}
	
	// 회원 메인화면 띄우기
	@RequestMapping(value = "/memberMain", method = RequestMethod.GET)
	public String memberMainGet(){
		return "member/memberMain";
	}
	
	// 회원가입창 띄우기
	@RequestMapping(value = "/memberJoin", method = RequestMethod.GET)
	public String memberJoinGet(){
		return "member/memberJoin";
	}
	
	// 회원가입 처리
	@RequestMapping(value = "/memberJoin", method = RequestMethod.POST)
	public String memberJoinPost(MemberVO vo){
		// 아이디 중복체크
		if(memberService.getMemberIdCheck(vo.getMid()) != null) return "redirect:/message/idCheckNo";
		// 닉네임 중복체크
		else if(memberService.getMemberNicKCheck(vo.getNickName()) != null) return "redirect:/message/NickCheckNo";
		
		// 비밀번호 암호화
		vo.setPwd(passwordEncoder.encode(vo.getPwd())); 
		
		// 회원사진 처리 (service 갹체에서 처리후 DB에 저장한다..)
		int res = memberService.setMemberJoinOk(vo);
		
		if(res == 1) return "redirect:/message/memberJoinOk";
		else return "redirect:/message/memberJoinNo";
	}
	
	// 아이디 중복체크
	@ResponseBody
	@RequestMapping(value = "/memberIdCheck", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String memberIdCheckPost(String mid){
		
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		if(vo != null) {
			return "1";
		}
		else return "0";
	}
	
	// 닉네임 중복체크
	@ResponseBody
	@RequestMapping(value = "/memberNickCheck", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String memberNickCheckPost(String nickName){
		
		MemberVO vo = memberService.getMemberNicKCheck(nickName);
		
		if(vo != null) {
			return "1";
		}
		else return "0";
	}
	
	// 회원 탈퇴 신청
	@RequestMapping(value = "/memberDelOk", method = RequestMethod.GET)
	public String memberDelOkGet(String mid) {
		
		System.out.println("mid : " + mid);
		
		int res = memberService.setUserDelUpdate(mid);
		
		if(res != 0) return "redirect:/message/memberDelOk";
		else return "redirect:/message/memberDelNo";
	}
	
}
