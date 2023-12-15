package com.spring.javaProjectS.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javaProjectS.service.StudyService;
import com.spring.javaProjectS.vo.UserVO;

@Controller
@RequestMapping("/study")
public class StudyController {
	
	@Autowired
	StudyService studyService;
	
	@RequestMapping(value = "/ajax/ajaxForm", method = RequestMethod.GET)
	public String ajaxFormGet() {
		return "study/ajax/ajaxForm";
	}
	
	
	// @ResponseBody는 문자형식으로 변환해서 보내주는 어노테이션이다. (서버에서 클라이언트로 보낼 때 문자형식으로 변환해줌)
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest1", method = RequestMethod.POST)
	public String ajaxTest1Post(int idx) {
		
		// ajax는 return에 res에 넣을 값을 넘김
		// ajax는 문자로 받아야 하기 때문에 idx에 +"" 을 작성하여 문자로 변환하여 넘긴다.
		return idx+"";
	}
	
	// 그냥 한글을 ajax로 넘기면 ?? 이런식으로 제대로 넘어가지 않기 때문에 produces = "application/text; charset=utf8"을 작성하여 한글이 깨지는 오류를 막는다.  (서버에서 클라이언트로 보낼때 오류가 생기는 것)  
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest2", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String ajaxTest2Post(String str) {
		
		return str;
	}
	
	@RequestMapping(value = "/ajax/ajaxTest3_1", method = RequestMethod.GET)
	public String ajaxTest3Get() {
		
		return "study/ajax/ajaxTest3_1";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest3_1", method = RequestMethod.POST)
	public String[] ajaxTest3_1Post(String dodo) {
		
//		String[] strArray = new String[100];
//		String[] strArray = studyService.getCityStringArray(dodo);
//		return strArray;
		
		// 이렇게 적어도 위에처럼 배열로 된다.
		return studyService.getCityStringArray(dodo);
	}

	@RequestMapping(value = "/ajax/ajaxTest3_2", method = RequestMethod.GET)
	public String ajaxTest3_2Get() {
		return "study/ajax/ajaxTest3_2";
	}

	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest3_2", method = RequestMethod.POST)
	public ArrayList<String> ajaxTest3_2Post(String dodo) {
		
		// spring은 vos로 값을 넘겨도 값이 제대로 넘어감!
		// jsp에서는 json 형식으로 넘어가서 key와 value로 따로따로 넘거갔었음!
		return studyService.getCityArrayList(dodo);
	}

	@RequestMapping(value = "/ajax/ajaxTest3_3", method = RequestMethod.GET)
	public String ajaxTest3_3Get() {
		return "study/ajax/ajaxTest3_3";
	}

	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest3_3", method = RequestMethod.POST)
	public HashMap<Object, Object> ajaxTest3_3Post(String dodo) {
		ArrayList<String> vos = studyService.getCityArrayList(dodo);
		
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("city", vos);
		
		return map;
	}

	@RequestMapping(value = "/ajax/ajaxTest4_1", method = RequestMethod.GET)
	public String ajaxTest4_1Get() {
		return "study/ajax/ajaxTest4_1";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest4_1", method = RequestMethod.POST)
	public UserVO ajaxTest4_1Post(String mid) {
		return studyService.getUserSearchVO(mid);
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/ajaxTest4_2", method = RequestMethod.POST)
	public List<UserVO> ajaxTest4_2Post(String mid) {
		return studyService.getUser2SearchMid(mid);
	}

}
