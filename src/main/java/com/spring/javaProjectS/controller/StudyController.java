package com.spring.javaProjectS.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaProjectS.common.ARIAUtil;
import com.spring.javaProjectS.common.SecurityUtil;
import com.spring.javaProjectS.service.StudyService;
import com.spring.javaProjectS.vo.ChartVO;
import com.spring.javaProjectS.vo.KakaoAddressVO;
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
	
	// 메일 전송하기
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
	
	// 파일 업로드+서버에 저장된 파일 확인 화면 이동 
	@RequestMapping(value ="/fileUpload/fileUpload" , method = RequestMethod.GET)
	public String fileUploadGet(HttpServletRequest request, Model model) {
		// 여기 경로를 ckediter로 바꾸면.. ckediter에 저장된 임시 파일 들이 나오는데... 확인하고 지울 수 있도록 한다.
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study"); //폴더명으로 본다. (이런식으로 폴더명으로만 적으면 폴더 밑에 있는 모든 파일을 가르킨다.)
		
		// .list()를 적으면 "/resources/data/study" 해당 폴더에 들어있는 모든 파일의 목록을 읽어올 수 있다.
		String[] files = new File(realPath).list();
		
		model.addAttribute("files", files);
		model.addAttribute("fileCount", files.length);
		
		return "study/fileUpload/fileUpload";
	}
	
	// 파일 업로드 처리
	@RequestMapping(value ="/fileUpload/fileUpload" , method = RequestMethod.POST)
	public String fileUploadPost(HttpServletRequest request, MultipartFile fName, String mid) {
		
		int res = studyService.fileUpload(fName,mid);
		
		
		if(res == 1) return "redirect:/message/fileUploadOk";
		else return "redirect:/message/fileUploadNo";
	}
	
	// 파일 업로드 처리
	@ResponseBody
	@RequestMapping(value ="/fileUpload/fileDelete" , method = RequestMethod.POST)
	public String fileDeletePost(HttpServletRequest request,
				@RequestParam(name="file", defaultValue = "", required = false) String fName
			) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/");
		
		int res = 0;
		File file = new File(realPath + fName);
		
		// 파일이 존재하는가?
		if(file.exists()) {
			// 파일이 존재하면 지우기.
			file.delete();
			res = 1;
		}
		
		return res + "";
	}
	
	@RequestMapping(value = "/fileUpload/fileDownAction", method = RequestMethod.GET)
	public void fileDownActionGet(HttpServletRequest request, HttpServletResponse response,
				@RequestParam(name="file", defaultValue = "", required = false) String file
			) throws IOException {
		// 이런식으로 값 받아와도 됨 ㅎㅎ 
		//String file = request.getParameter("file");
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/");
		
		// input객체로 생성해서 넘겨주어야 한다. (FileInputStream은 서버에서 읽어올 때 사용!)
		// 이렇게 생성을 해야 String객체로 받을 수 있음? 넘길 수 있음?
		File downFile = new File(realPath + file);
		
		// String 객체에서 한글처리
		// file이름이 한글로 넘어올 수 있기 때문에 깨지는 것을 방지하기 위하여 한글처리해줌
		String downFileName = new String(file.getBytes("UTF-8"), "8859_1");
		//
		response.setHeader("Content-Disposition", "attachment:filename=" + downFileName);
		
		FileInputStream fis = new FileInputStream(downFile); // 예외 처리한 거 원래는 FileNotFoundException 이게 들어가는 IOException으로 바꿔줌!
		
		// 준비 끝
		
		// 실제 파일 던지기
		// ServletOutputStream에 담아서 http통신 response를 통하여 클라이언트에 던진다.
		ServletOutputStream sos = response.getOutputStream();
		
		byte[] bytes = new byte[2048];
		int data = 0;
		while((data = fis.read(bytes,0 , bytes.length)) != -1) {
			// ServletOutputStream로 담아서 클라이언트로 보낸다...
			sos.write(bytes,0,data);
		}
		// 혹시라도 찌꺼기 남아 있을 까봐.
		sos.flush();
		// 닫아주기
		sos.close();
		fis.close();
	}
	
	// 카카오 맵 API (기본)
	@RequestMapping(value = "/kakao/kakaoMap", method = RequestMethod.GET)
	public String kakagoMapGet() {
		return "study/kakao/kakaoMap";
	}
	
	// 카카오 맵 API (연습1)
	@RequestMapping(value = "/kakao/kakaoEx1", method = RequestMethod.GET)
	public String kakaoEx1Get() {
		return "study/kakao/kakaoEx1";
	}
	
	// 카카오 맵 API (연습1/선택한 지점명을 DB에 저장하기)
	@ResponseBody
	@RequestMapping(value = "/kakao/kakaoEx1", method = RequestMethod.POST)
	public String kakaoEx1Post(KakaoAddressVO vo, Model model) {
		
		KakaoAddressVO kakaoVO = studyService.getKakaoAddressSearch(vo.getAddress());
		
		if(kakaoVO != null) return "0";
		
		studyService.setKakaoAddressInput(vo);
		
		return "1";
	}

	// 카카오 맵 API (MyDB에 저장된 주소목록 가져오기 / 지점검색하기 추가)
	@RequestMapping(value = "/kakao/kakaoEx2", method = RequestMethod.GET)
	public String kakaoEx2Get(Model model,
			@RequestParam(name="address", defaultValue = "", required = false) String address) {
		
		KakaoAddressVO vo = new KakaoAddressVO();
		
		List<KakaoAddressVO> vos = studyService.getKakaoAddressList();
		if(address.equals("")) {
			vo.setAddress("카카오");
			vo.setLatitude(33.450701);
			vo.setLongitude(126.570667);
		}
		else {
			vo = studyService.getKakaoAddressSearch(address);
		}
		
		model.addAttribute("vos",vos);
		model.addAttribute("vo",vo);
		
		return "study/kakao/kakaoEx2";
	}
	
	// 카카오 맵 API (연습2/삭제)
	@ResponseBody
	@RequestMapping(value = "/kakao/kakaoDelete", method = RequestMethod.POST)
	public String kakaoDeletePost( 
			@RequestParam(name="address", defaultValue = "", required = false) String address) {
		int res = 0;
		
		res = studyService.setKakaoAddressDelete(address);
		
		return res + "";
	}
	
	//키워드로 장소검색하기
	// 카카오 맵 API (연습3/kakaoDB에 저장된 지명으로 검색 후 MyDB에 주소 저장하기)
	@RequestMapping(value = "/kakao/kakaoEx3", method = RequestMethod.GET)
	public String kakaoEx2Get(
			@RequestParam(name="address", defaultValue = "청주시청", required = false) String address,
			Model model
			) {
		model.addAttribute("address",address);
		
		return "study/kakao/kakaoEx3";
	}
	
	// 차트연습(다양한 차트 그려보기)
	@RequestMapping(value = "/chart/chart", method = RequestMethod.GET)
	public String chartGet(Model model,
			@RequestParam(name="part", defaultValue = "", required = false) String part) {
		model.addAttribute("part", part);
		return "study/chart/chart";
	}
	
	// 차트연습(다양한 차트) 보여주기
	@RequestMapping(value = "/chart2/chart", method = RequestMethod.GET)
	public String chart2Get(Model model, ChartVO vo) {
		model.addAttribute("vo", vo);
		
		return "study/chart2/chart";
	}
	
	// 차트연습(다양한 차트 분석처리)
	@RequestMapping(value = "/chart2/chart", method = RequestMethod.POST)
	public String chart2Post(Model model, ChartVO vo) {
		model.addAttribute("vo", vo);
		return "study/chart2/chart";
	}
	// 차트연습(최근방문횟수 분석처리)
	@RequestMapping(value = "/chart2/visitCount", method = RequestMethod.GET)
	public String chart2Get(Model model,
			@RequestParam(name="part", defaultValue="", required=false) String part) {
		//System.out.println("part  " + part);
		List<ChartVO> vos = studyService.getVisitCount();	// 최근 8일간 방문한 총 횟수를 가져온다.
		String[] visitDates = new String[8];
		int[] visitDays = new int[8];
		int[] visitCounts = new int[8];
		for(int i=0; i<visitDates.length; i++) {
			visitDates[i] = vos.get(i).getVisitDate().replaceAll("-", "").substring(4);
			visitDays[i] = Integer.parseInt(vos.get(i).getVisitDate().toString().substring(8));
			visitCounts[i] = vos.get(i).getVisitCount();
		}
		
		model.addAttribute("title", "최근 8일간 방문횟수");
		model.addAttribute("subTitle", "최근 8일동안 방문한 해당일자 방문자 총수를 표시합니다.");
		model.addAttribute("visitCount", "방문횟수");
		model.addAttribute("legend", "일일 방문 총횟수");
		model.addAttribute("topTitle", "방문날짜");
		model.addAttribute("xTitle", "방문날짜");
		model.addAttribute("part", part);
		model.addAttribute("visitDates", visitDates);
		model.addAttribute("visitDays", visitDays);
		model.addAttribute("visitCounts", visitCounts);
		return "study/chart2/chart";
	}
	
	// randomAlphaNumeric : 알파벳과 숫자를 랜덤하게 출력하기 폼.
	@RequestMapping(value = "/captcha/randomAlphaNumeric", method = RequestMethod.GET)
	public String randomAlphaNumericGet() {
		return "study/captcha/randomAlphaNumeric";
	}
	
	// randomAlphaNumeric : 알파벳과 숫자를 랜덤하게 출력하기 처리...
	@ResponseBody
	@RequestMapping(value = "/captcha/randomAlphaNumeric", method = RequestMethod.POST)
	public String randomAlphaNumericPost() {
		String res = RandomStringUtils.randomAlphanumeric(64);
		return res;
	}
	
	// 캡차 : 사람과 기계 구별하기 폼...
	@RequestMapping(value = "/captcha/captcha", method = RequestMethod.GET)
	public String chptchaGet() {
		return "study/captcha/captcha";
	}
	
	// 캡차 이미지 만들기...
	@ResponseBody
	@RequestMapping(value = "/captcha/captchaImage", method = RequestMethod.POST)
	public String chptchaImagePost(HttpSession session, HttpServletRequest request) {
		// 시스템에 설정된 폰트 출력해보기
//		Font[] fontList = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
//		for(Font f : fontList) {
//			System.out.println(f.getName());
//		}
		
		try {
			// 알파뉴메릭문자 5개를 가져온다.
			String randomString = RandomStringUtils.randomAlphanumeric(5);
			System.out.println("randomString : " + randomString);
			session.setAttribute("sCaptcha", randomString);
			
			Font font = new Font("Jokerman", Font.ITALIC, 30);
			FontRenderContext frc = new FontRenderContext(null, true, true);
			Rectangle2D bounds = font.getStringBounds(randomString, frc);
			int w = (int) bounds.getWidth();
			int h = (int) bounds.getHeight();
			
			// 이미지로 생성하기
			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			
			g.fillRect(0, 0, w, h);
			g.setColor(new Color(0, 156, 240));
			g.setFont(font);
			// 각종 랜더링 명령어에 의한 chptcha 문자 작업..(생략)..
			g.drawString(randomString, (float)bounds.getX(), (float)-bounds.getY());
			g.dispose();
			
			String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/");
			ImageIO.write(image, "png", new File(realPath + "captcha.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "study/captcha/captcha";
	}
	
	// 캡차확인하기 처리
	@ResponseBody
	@RequestMapping(value = "/captcha/captcha", method = RequestMethod.POST)
	public String chptchaPost(HttpSession session, String strCaptcha) {
		if(strCaptcha.equals(session.getAttribute("sCaptcha").toString())) return "1";
		else return "0";
	}
}
