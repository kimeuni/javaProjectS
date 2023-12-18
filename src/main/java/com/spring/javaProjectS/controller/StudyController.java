package com.spring.javaProjectS.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javaProjectS.common.ARIAUtil;
import com.spring.javaProjectS.common.SecurityUtil;
import com.spring.javaProjectS.service.StudyService;
import com.spring.javaProjectS.vo.MailVO;
import com.spring.javaProjectS.vo.UserVO;

@Controller
@RequestMapping("/study")
public class StudyController {
	
	@Autowired
	StudyService studyService;
	
	// bCryptPasswordEncoder 주입된 암호화 사용하기
	@Autowired
	BCryptPasswordEncoder PasswordEncoder;
	
	@Autowired
	JavaMailSenderImpl mailSender;
	
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

	@RequestMapping(value = "/password/sha256", method = RequestMethod.GET)
	public String sha256Get() {
		return "study/password/sha256";
	}
	
	@ResponseBody
	@RequestMapping(value = "/password/sha256", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String sha256Post(String pwd) {
		UUID uid = UUID.randomUUID();
		String salt = uid.toString().substring(0,8);
		
		SecurityUtil security = new SecurityUtil();
		String encPwd = security.encryptSHA256(pwd+salt);
		
		pwd = "원본 비밀번호 : " + pwd + " / solt 키 : "+ salt +" / 암호화된 비밀번호 : " + encPwd;
		
		return pwd;
	}
	
	@RequestMapping(value = "/uuid/uuidForm", method = RequestMethod.GET)
	public String UUIDGet() {
		return "study/uuid/uidForm";
	}
	
	@ResponseBody
	@RequestMapping(value = "/uuid/uidForm", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String uuidPost() {
		
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	@RequestMapping(value = "/password/aria", method = RequestMethod.GET)
	public String ariaGet() {
		return "study/password/aria";
	}
	
	@ResponseBody
	@RequestMapping(value = "/password/aria", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String ariaPost(String pwd) throws InvalidKeyException, UnsupportedEncodingException {
		
		UUID uid = UUID.randomUUID();
		String salt = uid.toString().substring(0,8); 
		
		String encPwd = "";
		String decPwd = "";
		
		// 암호화
		encPwd = ARIAUtil.ariaEncrypt(pwd + salt);
		
		// 복호화
		decPwd = ARIAUtil.ariaDecrypt(encPwd);
		
		pwd = "원본 비밀번호 : " + pwd + " / salt : "+ salt +" / 암호화된 비밀번호 : " + encPwd + " / 복호화된 비밀번호 : "+ decPwd;
		
		return pwd;
	}
	
	// bCryptPassword 화면 호출
	@RequestMapping(value = "/password/bCryptPassword", method = RequestMethod.GET)
	public String bCryptPasswordGet() {
		return "study/password/bCryptPassword";
	}
	
	// bCryptPasswordEncoder 암호화
	@ResponseBody
	@RequestMapping(value = "/password/bCryptPassword", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String bCryptPasswordPost(String pwd) {
		
		String encPwd = "";
		
		encPwd = PasswordEncoder.encode(pwd);
		
		pwd = "원본 비밀번호 : " + pwd + " / 암호화된 비밀번호 : " + encPwd ;
		
		return pwd;
	}
	
	// 메일 전송폼 호출
	@RequestMapping(value = "/mail/mail", method = RequestMethod.GET)
	public String mailGet() {
		return "study/mail/mailForm";
	}
	
	@RequestMapping(value = "/mail/mail", method = RequestMethod.POST)
	public String mailPost(MailVO vo, HttpServletRequest request) throws MessagingException {
		String toMail = vo.getToMail();
		String title = vo.getTitle();
		String content = vo.getContent();
		
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
		content += "<br><hr><h3>JavaProjectS 보냅니다.</h3><hr><br>";
		content += "<p><img src='cid:main.png' width='500p' ></p>";
		content += "<p>방문하기 : <a href='49.142.157.251:9090/cjgreen'>JavaProjectS</a></p>";
		content += "<hr>";
		
		messageHelper.setText(content, true); // content를 이걸로 다시 변경하겠다.
		
		// 본문에 기재된 그림파일의 경로와 파일명을 별도로 표시한다. 그런 후 다시 보관함에 저장한다.
//		FileSystemResource file = new FileSystemResource("D:\\JavaProject\\springframework\\works\\javaProjectS\\src\\main\\webapp\\resources\\images\\main.png"); //절대경로 
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.png")); 
		messageHelper.addInline("main.png", file); // 그림파일이 어떤건지 알려준다. //본문에 추가할 때는 addInline을 사용
		
		// 첨부파일 보내기
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/chicago.jpg")); 
		messageHelper.addAttachment("chicago.jpg", file);  // 첨부파일 등에 추가할 때는 addAttachment를 사용
		
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/images.zip")); 
		messageHelper.addAttachment("images.zip", file);  // 첨부파일 등에 추가할 때는 addAttachment를 사용
		
		
		// 메일 전송하기
		mailSender.send(message);
		
		return "redirect:/message/mailSendOk";
	}
}
