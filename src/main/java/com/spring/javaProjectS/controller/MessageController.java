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
			
		return "include/message";
	}
}
