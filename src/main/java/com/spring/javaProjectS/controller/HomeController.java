package com.spring.javaProjectS.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = {"/","/h"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "home";
//		return "redirect:/user/userList";
//		return "redirect:/user2/user2List";
	}
	
	// request는 저장하기 위해, response는 화면에 띄워주기 위해서 필요.
	@RequestMapping(value = "/imageUpload",method = RequestMethod.POST)
	public void imageUploadGet(MultipartFile upload,
				HttpServletRequest request,
				HttpServletResponse response) throws IOException{
		// 게시판 글쓰기에서 사진을 클릭 후 => 업로드 => 사진 파일 선택 = > 서버전송을 누를 시, 여기로 들어온다.
		
		
		// 한글 깨지는 것을 방지
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		//request.getSession().getServletContext().getRealPath() 이렇게 적는 것을 권장 한다고 함.
		// 절대경로로 지정할 것. //여긴 서버에 넣을 실제 주소...
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/ckeditor/");
		String oFileName = upload.getOriginalFilename(); // 파일 원본이름 가져오기
		
		// 저장되는 파일명 중복되지 않도록 (날짜 + 원본 파일명)
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		oFileName = sdf.format(date) + "_" + oFileName;
		
		// 한번에 꺼낼 수 없어서 바이트 별로 나눠서 꺼낸다.
		//getBytes() 알아서 바이트로 쪼개줌
		byte[] bytes = upload.getBytes();
		// FileOutputStream 여기 안에 업로드할 파일명을 적을 것.
		FileOutputStream fos = new FileOutputStream(new File(realPath+oFileName));
		// 바로 bytes로 넘겨줌 (위에 적은 경로로 들어감  => FileOutputStream(new File(realPath+oFileName))).
		fos.write(bytes);
		
		PrintWriter out = response.getWriter();
		// 저장되어있는 경로를 읽어서 불러옴?
		// 여긴 메핑 주소.. (servlet-context에서 설정한 주소)
		String fileUrl = request.getContextPath() + "/data/ckeditor/" + oFileName;
		// JSON은 가운데 들어간다고 해서 '' 사용 불가.. 그래서 \""를 사용, 변수 : 값
		// uploaded : 업로드 시킬 위치 (예약어)
		out.println("{\"originalFilename\": \""+ oFileName +"\", \"uploaded\":1, \"url\": \""+ fileUrl +"\"}");
		
		// 찌꺼기 있으면 없애기?
		out.flush();
		fos.close();
	}
	
}
