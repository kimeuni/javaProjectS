package com.spring.javaProjectS.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	// 전체 다운로드
	@RequestMapping(value = "/pdsTotalDown", method = RequestMethod.GET)
	public String pdsTotalDownGet(int idx, HttpServletRequest request) throws IOException {
		// 파일 다운로드횟수 증가
		pdsService.setPdsDownNumCheck(idx);
		
		// 여러개의 파일을 하나의 파일(zip)로 압축(통합)하여 다운로드시켜준다. 압축파일의 이름을 '제목.zip'으로 처리한다.
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		
		PdsVO vo = pdsService.getIdxSearch(idx);
		
		String[] fNames = vo.getFName().split("/");
		String[] fSNames = vo.getFSName().split("/");
		
		// 파일이 압축될 위치 만들기
		String zipPath = realPath + "temp/";
		// 파일명 만들기
		String zipName = vo.getTitle() + ".zip";
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		// output의 파일이 출력용(zipoutput)의 파일로 가도록 한다.
		ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath + zipName));
		
		byte[] bytes = new byte[2048];
		
		for(int i=0; i<fNames.length; i++) {
				
			// 읽어서
			fis = new FileInputStream(realPath + fSNames[i]);
			// 저장하고
			fos = new FileOutputStream(zipPath + fNames[i]);
			
			// fis을 fos에 쓰기작업(파일생성)
			int data;
			while((data = fis.read(bytes,0,bytes.length)) != -1) {
				fos.write(bytes,0,data);
			}
			fos.flush();
			fos.close();
			fis.close();
			
			// fos으로 생성된 파일을 zip파일에 쓰기작업처리
			File moveAndReName = new File(zipPath + fNames[i]);
			fis =new FileInputStream(moveAndReName);
			// zip안에 들어가는 파일명 이름 원본파일명으로 넣겠다.
			zout.putNextEntry(new ZipEntry(fNames[i]));
			while((data = fis.read(bytes,0,bytes.length)) != -1) {
				zout.write(bytes,0,data);
			}
			zout.flush();
			// close로 닫으면 전체가 닫히기 때문에 1개의 객체만 닫기 위해서 closeEntry()를 사용한다.
			zout.closeEntry();
			fis.close();
		}
		// 작업이 다 끝나면 닫는다.
		zout.close();
		
		// zipName에 한글이면 깨지기 때문에 한글처리를 해야한다. (java.net.URLEncoder.encode => 이렇게 적어주면 한글처리 완료!)
		return "redirect:/pds/pdsDownAction?file="+java.net.URLEncoder.encode(zipName);
	}
	
	@RequestMapping(value = "/pdsDownAction", method = RequestMethod.GET)
	public void pdsDownActionGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String file = request.getParameter("file");
		
		String downFilePath = request.getSession().getServletContext().getRealPath("/resources/data/pds/temp/") + file;
		
		File downFile = new File(downFilePath);
		String downFileName = new String(file.getBytes("UTF-8"), "8859_1"); // 예외처리 IO로 해주기
		// url 주소에 담아주기 (헤어에 정보 넣기)
		response.setHeader("Content-Disposition", "attachment;filename=" + downFileName);
		
		FileInputStream fis = new FileInputStream(downFile);
		ServletOutputStream sos = response.getOutputStream();
		
		byte[] bytes = new byte[2048];
		int data;
		while((data = fis.read(bytes,0,bytes.length)) != -1) {
			sos.write(bytes,0,data);
		}
		sos.flush();
		sos.close();
		fis.close();
		
		// 다운로드 완료후에 전송이 끝난 zip파일을 삭제처리.. (함부로 지우면 안됨)
		downFile.delete();
		
	}
}
