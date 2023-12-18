package com.spring.javaProjectS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MessageController {
	
	@RequestMapping(value = "/message/{msgFalg}", method = RequestMethod.GET )
	public String msgGet(@PathVariable String msgFalg, String mid, Model model) {
		if(msgFalg.equals("userDeleteOk")) {
			model.addAttribute("msg", "user가 삭제되었습니다.");
			model.addAttribute("url","user/userList");
		}
		else if(msgFalg.equals("userDeleteNo")) {
			model.addAttribute("msg", "user 삭제에 실패하였습니다.");
			model.addAttribute("url","user/userList");
		}
		else if(msgFalg.equals("user2DeleteOk")) {
			model.addAttribute("msg", "user가 삭제되었습니다.");
			model.addAttribute("url","user2/user2List");
		}
		else if(msgFalg.equals("user2DeleteNo")) {
			model.addAttribute("msg", "user 삭제에 실패하였습니다.");
			model.addAttribute("url","user2/user2List");
		}
		else if(msgFalg.equals("user2InputOk")) {
			model.addAttribute("msg", "회원가입 되었습니다.");
			model.addAttribute("url","user2/user2List");
		}
		else if(msgFalg.equals("user2InputNo")) {
			model.addAttribute("msg", "회원가입에 실패하였습니다.");
			model.addAttribute("url","user2/user2List");
		}
		else if(msgFalg.equals("user2UpdateOk")) {
			model.addAttribute("msg", "수정되었습니다.");
			model.addAttribute("url","user2/user2List");
		}
		else if(msgFalg.equals("user2UpdateNo")) {
			model.addAttribute("msg", "수정에 실패하였습니다.");
			model.addAttribute("url","user2/user2List");
		}
		else if(msgFalg.equals("guestInputOk")) {
			model.addAttribute("msg", "방명록 글이 등록되었습니다.");
			model.addAttribute("url","guest/guestList");
		}
		else if(msgFalg.equals("guestInputNo")) {
			model.addAttribute("msg", "방명록 등록에 실패하였습니다.");
			model.addAttribute("url","guest/guestInput");
		}
		else if(msgFalg.equals("adminLoginOk")) {
			model.addAttribute("msg", "관리자님 로그인 되었습니다.");
			model.addAttribute("url","guest/guestList");
		}
		else if(msgFalg.equals("adminLoginNo")) {
			model.addAttribute("msg", "아이디 혹은 비밀번호를 확인해주세요.");
			model.addAttribute("url","guest/adminLogin");
		}
		else if(msgFalg.equals("guestDeleteOk")) {
			model.addAttribute("msg", "방명록이 삭제되었습니다.");
			model.addAttribute("url","guest/guestList");
		}
		else if(msgFalg.equals("guestDeleteNo")) {
			model.addAttribute("msg", "방명록 삭제에 실패하였습니다.");
			model.addAttribute("url","guest/guestList");
		}
		else if(msgFalg.equals("adminLogout")) {
			model.addAttribute("msg", "로그아웃 되었습니다.");
			model.addAttribute("url","guest/guestList");
		}
		else if(msgFalg.equals("mailSendOk")) {
			model.addAttribute("msg", "메일이 성공적으로 전송되었습니다.");
			model.addAttribute("url","study/mail/mail");
		}
		else if(msgFalg.equals("memberLoginNo")) {
			model.addAttribute("msg", "로그인에 실패하였습니다.");
			model.addAttribute("url","member/memberLogin");
		}
		else if(msgFalg.equals("memberLoginOk")) {
			model.addAttribute("msg", mid + "님 로그인 되었습니다.");
			model.addAttribute("url","member/memberMain");
		}
		else if(msgFalg.equals("memberLogoutOk")) {
			model.addAttribute("msg",mid + "님 로그아웃 되었습니다.");
			model.addAttribute("url","member/memberLogin");
		}
		else if(msgFalg.equals("memberJoinOk")) {
			model.addAttribute("msg", "회원가입에 성공하였습니다.");
			model.addAttribute("url","member/memberLogin");
		}
		else if(msgFalg.equals("memberJoinNo")) {
			model.addAttribute("msg","회원가입에 실패하였습니다,");
			model.addAttribute("url","member/memberJoin");
		}
			
		return "include/message";
	}
}
