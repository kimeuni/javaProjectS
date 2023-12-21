package com.spring.javaProjectS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.javaProjectS.service.UserService;
import com.spring.javaProjectS.vo.UserVO;

// 선생님이 하시는 방식
@Controller
@RequestMapping("/user2")
public class User2Controller {
	
	@Autowired
	UserService userService;
	
	// 유저 전체 리스트
	@RequestMapping(value = "/user2List",method = RequestMethod.GET)
	public String user2ListGet(Model model) {
		
		List<UserVO> vos = userService.getUser2List();
		
		model.addAttribute("vos",vos);
		
		return "study/user/user2List";
	}
	
	// 유저 회원가입
	// validator를 이용한 backEnd 유효성 검사하기 검사후 정확한 자료를 DB에 저장시켜준다...
	@RequestMapping(value = "/user2List",method = RequestMethod.POST)
	public String user2ListPost(@Validated UserVO vo, BindingResult bindingResult, Model model) {
		
		//System.out.println("vo" + vo);
		System.out.println("error :" + bindingResult.hasErrors());
		
		if(bindingResult.hasFieldErrors()) {
			List<ObjectError> list = bindingResult.getAllErrors();
			
			String temp = "";
			for(ObjectError e : list) {
				System.out.println("메세지 :"+ e.getDefaultMessage());
				temp = e.getDefaultMessage().substring(e.getDefaultMessage().indexOf("/")+1);
				if(temp.equals("midEmpty") || temp.equals("midSizeNo") || temp.equals("nameEmpty") || temp.equals("nameSizeNo") || temp.equals("ageEmpty") || temp.equals("ageRangeNo")) {
					break; //반복문(for문)을 빠져나감
				}
				
				
			}
			if(temp.equals("midEmpty")) temp = "아이디";
			else if(temp.equals("midSizeNo")) temp = "아이디";
			else if(temp.equals("nameEmpty")) temp = "성명";
			else if(temp.equals("nameSizeNo")) temp = "성명";
			else if(temp.equals("ageEmpty")) temp = "나이";
			else if(temp.equals("ageRangeNo")) temp = "나이";
			
			model.addAttribute("temp",temp);
			
			System.out.println("temp : " + temp);
			return "redirect:/message/validatorError";
		}
		int res = userService.setUser2Input(vo);
		if(res != 0 ) return "redirect:/message/user2InputOk"; 
		else return "redirect:/message/user2InputNo";
	}
	
	// 유저 검색 리스트
	@RequestMapping(value = "/user2Search",method = RequestMethod.GET)
	public String user2SearchGet(Model model, String name) {
		System.out.println(name);
		List<UserVO> vos = userService.getUser2Search(name);
		
		model.addAttribute("vos",vos);
		model.addAttribute("name", name);
		
		return "study/user/user2List";
	}

	// 유저 삭제
	@RequestMapping(value = "/user2Delete",method = RequestMethod.GET)
	public String user2DeleteGet(Model model, int idx) {
		int res = userService.setUser2Delete(idx);
		
		if(res != 0 ) return "redirect:/message/user2DeleteOk"; 
		else return "redirect:/message/user2DeleteNo";
		
	}
	
	// 유저 수정
	@RequestMapping(value = "/user2UpdateOk",method = RequestMethod.POST)
	public String user2UpdateOkPost(Model model, UserVO vo) {
		int res = userService.setUser2Update(vo);
		
		if(res != 0 ) return "redirect:/message/user2UpdateOk"; 
		else return "redirect:/message/user2UpdateNo";
		
	}
	
}
