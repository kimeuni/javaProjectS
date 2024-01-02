package com.spring.javaProjectS.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/errorPage")
public class ErrorController {
	
	// 에러 연습폼..
	@RequestMapping(value = "/errorMain", method = RequestMethod.GET)
	public String errorMainGet() {
		return "errorPage/errorMain";
	}
	
	// JSP페이지에서 에러발생시 이동처리할 폼 보여주기
	@RequestMapping(value = "/error1", method = RequestMethod.GET)
	public String error1Get() {
		return "errorPage/error1";
	}
	
	// JSP페이지에서 에러발생시 공사중화면으로 보내어 보여주는 폼(jsp파일에서 처리하게되면 이곳에서는 처리할 필요가 없다.)
	@RequestMapping(value = "/errorMessage1", method = RequestMethod.GET)
	public String errorMessage1Get() {
		return "errorPage/errorMessage1";
	}
	
	// 404에러가 났을 때 이동할 메세지 폼 보기
	@RequestMapping(value = "/error404", method = RequestMethod.GET)
	public String error404Get() {
		return "errorPage/error404";
	}
	
	// 405에러가 났을 때 이동할 메세지 폼 보기
	@RequestMapping(value = "/error405", method = RequestMethod.GET)
	public String error405Get() {
		return "errorPage/error405";
	}
	
	// 405에러가 났을 때 이동할 메세지 폼 보기
	@RequestMapping(value = "/error500", method = RequestMethod.GET)
	public String error500Get() {
		return "errorPage/error500";
	}
	
	// JSP페이지에서 에러발생시 공사중화면으로 보내어 보여주는 폼
	@RequestMapping(value = "/errorMessage1Get", method = RequestMethod.POST)
	public String errorMessage1GetGet() {
		return "errorPage/error405";
	}
	
	// JSP페이지에서 에러발생시 공사중화면으로 보내어 보여주는 폼
	@RequestMapping(value = "/errorNumberFormat", method = RequestMethod.GET)
	public String errorNumberFormatGet() {
		return "errorPage/errorNumberFormat";
	}
	
	// servlet에러(500)가 났을 때 이동할 메세지 폼...
	@RequestMapping(value = "/error500Check", method = RequestMethod.GET)
	public String error500CheckGetGet(HttpSession session) {
		
		String mid = (String)session.getAttribute("ssMid");
		int su = 100 + Integer.parseInt(mid);
		System.out.println("su : " + su);
		return "errorPage/errorMain";
	}
}
