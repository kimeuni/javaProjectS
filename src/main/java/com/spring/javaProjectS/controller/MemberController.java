package com.spring.javaProjectS.controller;

import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.javaProjectS.service.MemberService;
import com.spring.javaProjectS.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	JavaMailSender mailSender;
	
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
		
		
		int res = memberService.setUserDelUpdate(mid);
		
		if(res != 0) return "redirect:/message/memberDelOk";
		else return "redirect:/message/memberDelNo";
	}
	
	// 비밀번호 확인 화면 (비밀번호 변경 , 수정 들어가기 전)
	@RequestMapping(value = "/memberPwdCheck", method = RequestMethod.GET)
	public String memberPwdCheckGet(String flag, Model model) {
		System.out.println(flag);
		
		// flag를 통하여 비밀번호 변경을 갈 지, 수정으로 들어갈 지 정한다.
		model.addAttribute("flag",flag);
		
		return "member/memberPwdCheck";
	}
	
	// 비밀번호 확인 (비밀번호 변경 , 수정 들어가기 전)
	@ResponseBody
	@RequestMapping(value = "/memberPwdCheckOk", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String memberPwdCheckOkPost(String flag, String mid, String pwd , Model model) {
	
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		if(passwordEncoder.matches(pwd,vo.getPwd())) {
			return flag;
		}
		else return "0";
		
	}
	
	// 비밀번호 변경 화면 가기
	@RequestMapping(value = "/memberUpdate", method = RequestMethod.GET)
	public String memberUpdateGet(String mid, Model model) {

		MemberVO vo = memberService.getMemberIdCheck(mid);
		if(vo == null) return "redirect:/message/memberUpdateViewNo"; 
		else return "member/memberUpdate";
	}
	
	// 회원 수정 화면 가기
	@RequestMapping(value = "/memberPwdChange", method = RequestMethod.GET)
	public String memberPwdChangeGet() {

		return "member/memberPwdChange";
	}
	
	
	// 비밀번호 임시 발급
	@ResponseBody
	@RequestMapping(value = "/memberPasswordSearch", method = RequestMethod.POST)
	public String memberPasswordSearchPost(String mid, String email) throws MessagingException {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		if(vo != null && vo.getEmail().equals(email)) {
			// 정보 확인 후, 임시 비밀번호를 발급받아서 메일로 전송처리 한다.
			UUID uid = UUID.randomUUID();
			String pwd = uid.toString().substring(0,8);
			
			// 발급받은 비밀번호를 암호화후 DB에 저장한다.
			memberService.setMemberPasswordUpdate(mid, passwordEncoder.encode(pwd));
			
			// 발급받은 임시번호를 회원 메일주소로 전송처리한다.
			String title = "임시 비밀번호를 발급하셨습니다.";
			String mailFlag = "임시 비밀번호 : " + pwd;
			String res = mailSend(email, title, mailFlag);
			
			if (res == "1") return "1";
			else return "0";
		}
		else return "0";
	}
	
	// 메일 전송하기 (메소드로 빼서 사용)
	public String mailSend(String toMail,String title ,String mailFlag) throws MessagingException {
		//request사용을 위한 객체 생성
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String content = "";
		
		// 메일 전송을 위한 객체 : MimeMessage(), MimeMessageHelper()
		// 메세지를 보내기 위한 객체
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8"); //message를 사용(true)하겠다. UTF-8은 한글 변환
		
		//메일 보관함에 회원이 보내온 메세지들의 정보를 모두 저장시킨 후 작업처리하자...
		messageHelper.setTo(toMail); //TO에는 받는 쪽 메일
		messageHelper.setSubject(title); // Subject에는 제목 들어가기
		messageHelper.setText(content); // Text에는 내용 들어가기
		
		// 메세지 보관함의 내용(content)에, 발신자의 필요한 정보를 추가로 담아서 전송시켜주면 좋다...
		content = content.replace("\n","<br>");
		content += "<br><hr><h3>"+ mailFlag +"</h3><hr><br>";
		content += "<p><img src='cid:main.png' width='500p' ></p>";
		content += "<p>방문하기 : <a href='49.142.157.251:9090/cjgreen'>JavaProjectS</a></p>";
		content += "<hr>";
		
		messageHelper.setText(content, true); // content를 이걸로 다시 변경하겠다.
		
		// 본문에 기재된 그림파일의 경로와 파일명을 별도로 표시한다. 그런 후 다시 보관함에 저장한다.
//		FileSystemResource file = new FileSystemResource("D:\\JavaProject\\springframework\\works\\javaProjectS\\src\\main\\webapp\\resources\\images\\main.png"); 
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.png")); 
		messageHelper.addInline("main.png", file); // 그림파일이 어떤건지 알려준다. //본문에 추가할 때는 addInline을 사용
		
		// 메일 전송하기
		mailSender.send(message);
		return "1";
	}
	
	// 아이디 찾기 메일 인증번호 발송 (ajax)
	@ResponseBody
	@RequestMapping(value = "/memberEmailCode", method = RequestMethod.POST)
	public String memberEmailCodePost(String email, HttpSession session) throws MessagingException {
		
		System.out.println("1");
		
		List<MemberVO> vos = memberService.getMemberEmailList(email);
		
		if(vos != null) {
			UUID uid = UUID.randomUUID();
			
			// 발급받은 인증코드를 작성한 메일주소로 전송처리한다.
			String title = "인증번호가 도착했습니다.";
			String mailFlag = "인증 번호 : " + uid.toString().substring(0,8);
			String res = mailSend(email, title, mailFlag);
			
			if(res == "1") {
				// 메세지를 전송했으면 인증번호를 세션에 저장함(메일로 받은 인증번호가 맞는지 아닌지, 확인 가능하도록)
				session.setAttribute("sMailCode", uid.toString().substring(0,8));
				return "1";
			}
			else return "0";
		}
		else return "0";
	}
	
	// 이메일로 아이디 찾기 (ajax)
	@ResponseBody
	@RequestMapping(value = "/memberEmailCodeOk", method = RequestMethod.POST)
	public String memberEmailCodeOkPost(String code,String email,HttpSession session) {
		
		String sCode = session.getAttribute("sMailCode")==null? "" : (String)session.getAttribute("sMailCode");
		
		if(sCode.equals("")) {
			return "1";
		}
		else if(!sCode.equals(code)) {
			return "2";
		}
		else {
			// 인증코드가 맞을 시 세션 삭제
			session.removeAttribute("sMailCode");
			
			// 같은 이메일을 사용하는 유저 전부 가져오기
			List<MemberVO> vos = memberService.getMemberEmailList(email);
			String str = "";
			if(vos != null) {
				for(int i=0; i<vos.size(); i++) {
					str += vos.get(i).getMid() + "/";
				}
			}
			return str;
		}
	}
	
}
