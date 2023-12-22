package com.spring.javaProjectS.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaProjectS.common.SecurityUtil;
import com.spring.javaProjectS.pagination.PageProcess;
import com.spring.javaProjectS.pagination.PageVO;
import com.spring.javaProjectS.service.PdsService;
import com.spring.javaProjectS.vo.PdsVO;

@Controller
@RequestMapping("/pds")
public class PdsController {
	
	@Autowired
	PdsService pdsService;
	
	@Autowired
	PageProcess pageProcess;
	
	@RequestMapping(value = "/pdsList",method = RequestMethod.GET)
	public String pdsListGet(Model model,
				@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
				@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize,
				@RequestParam(name="part", defaultValue = "전체", required = false) String part
			) {
		PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "pds", part, "");
		
		List<PdsVO> vos = pdsService.getPdsList(pageVO.getStartIndexNo(),pageSize,part);
		
		model.addAttribute("vos",vos);
		model.addAttribute("pageVO", pageVO);
		
		return "pds/pdsList";
	}
	
	@RequestMapping(value = "/pdsInput",method = RequestMethod.GET)
	public String pdsInputGet(Model model, String part) {
		
		model.addAttribute("part",part);
		
		return "pds/pdsInput";
	}
	
	// MultipartHttpServletRequest 멀티플 이미지 가져올 때 사용?
	@RequestMapping(value = "/pdsInput",method = RequestMethod.POST)
	public String pdsInputPost(Model model, PdsVO vo, MultipartHttpServletRequest file) {
		
		SecurityUtil security = new SecurityUtil();
		// salt키 만들기
		UUID uid = UUID.randomUUID();
		String salt = uid.toString().substring(0,4);
		
		String pwd = salt +  security.encryptSHA256(vo.getPwd());
		
		vo.setPwd(pwd);
		
		int res = pdsService.setPdsInput(vo, file);
		
		if(res == 1) return "redirect:/message/pdsInputOk";
		else return "redirect:/message/pdsInputNo";
		
	}
	
	// 다운로드 횟수 업데이트처리
	@ResponseBody
	@RequestMapping(value = "/pdsDownNumCheck",method = RequestMethod.POST)
	public String pdsDownNumCheckPost(int idx) {
		
		int res = pdsService.setPdsDownNumCheck(idx);
		
		return res+"";
	}
	
	// pds 자료실 글 삭제 처리
	@ResponseBody
	@RequestMapping(value = "/pdsDeleteOk",method = RequestMethod.POST)
	public String pdsDeleteOkPost(int idx,String pwd) {
		PdsVO vo = pdsService.getIdxSearch(idx);
		// salt키(4개의 랜덤 숫자)를 확인하기 위해 앞에 4글자를 가져옴 방법1. 
		//String tempPwd = vo.getPwd().substring(0, 4);
		
		int res=0;
		SecurityUtil security = new SecurityUtil();
		if(security.encryptSHA256(pwd).equals(vo.getPwd().substring(4))) {
			res = pdsService.setPdsDelete(vo);
		}
		else return "0";
		
		return res+"";
	}
}
