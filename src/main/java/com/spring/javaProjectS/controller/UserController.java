package com.spring.javaProjectS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.javaProjectS.service.UserService;
import com.spring.javaProjectS.vo.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	// 의존성 주입한 걸 본다.
	// @Autowired를 적음으로서 밑에 적은 UserService를 찾아간다.
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public String userListGer(Model model) {
		List<UserVO> vos = userService.getUserList();
		
		model.addAttribute("vos",vos);
		
		return "study/user/userList";
	}
	
	@RequestMapping(value = "/userDelete", method = RequestMethod.GET)
	public String userDeleteGet(int idx) {
		int res = userService.setUserDelete(idx);
		
		if(res != 0) return "redirect:/message/userDeleteOk"; 
		else return "redirect:/message/userDeleteNo";
		
	}
	
	@RequestMapping(value = "/userSearch", method = RequestMethod.GET)
	public String userSearchGet(String name, Model model) {
		
		List<UserVO> vos = userService.getUserSearch(name);
		
		model.addAttribute("vos",vos);
		
		return "study/user/userList";
		
	}
}
