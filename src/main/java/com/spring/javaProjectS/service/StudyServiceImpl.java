package com.spring.javaProjectS.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.spring.javaProjectS.dao.StudyDAO;
import com.spring.javaProjectS.dao.User2DAO;
import com.spring.javaProjectS.vo.ChartVO;
import com.spring.javaProjectS.vo.KakaoAddressVO;
import com.spring.javaProjectS.vo.QrCodeVO;
import com.spring.javaProjectS.vo.TransactionVO;
import com.spring.javaProjectS.vo.UserVO;

import net.coobird.thumbnailator.Thumbnailator;

@Service
public class StudyServiceImpl implements StudyService {
	@Autowired
	StudyDAO studyDAO;

	@Autowired
	User2DAO user2DAO;
	
	@Override
	public String[] getCityStringArray(String dodo) {
		//적당히 값을 지정해서 던짐
		String[] strArray = new String[100];
		if(dodo.equals("서울")) {
			strArray[0] = "강남구";
			strArray[1] = "서초구";
			strArray[2] = "관악구";
			strArray[3] = "마포구";
			strArray[4] = "영등포구";
			strArray[5] = "강북구";
			strArray[6] = "동대문구";
			strArray[7] = "성북구";
		}
		else if(dodo.equals("경기")) {
			strArray[0] = "수원시";
			strArray[1] = "안양시";
			strArray[2] = "안성시";
			strArray[3] = "평택시";
			strArray[4] = "용인시";
			strArray[5] = "의정부시";
			strArray[6] = "광명시";
			strArray[7] = "성남시";
		}
		else if(dodo.equals("충북")) {
			strArray[0] = "청주시";
			strArray[1] = "충주시";
			strArray[2] = "괴산군";
			strArray[3] = "제천시";
			strArray[4] = "증평군";
			strArray[5] = "단양군";
			strArray[6] = "영동군";
			strArray[7] = "옥천군";
		}
		else if(dodo.equals("충남")) {
			strArray[0] = "천안시";
			strArray[1] = "아산시";
			strArray[2] = "논산시";
			strArray[3] = "공주시";
			strArray[4] = "부여군";
			strArray[5] = "홍성군";
			strArray[6] = "예산군";
			strArray[7] = "청양군";
		}
		
		return strArray;
	}

	@Override
	public ArrayList<String> getCityArrayList(String dodo) {
		ArrayList<String> vos = new ArrayList<String>();
		
		if(dodo.equals("서울")) {
			vos.add("강남구");
			vos.add("서초구");
			vos.add("관악구");
			vos.add("마포구");
			vos.add("영등포구");
			vos.add("강북구");
			vos.add("동대문구");
			vos.add("성북구");
		}
		else if(dodo.equals("경기")) {
			vos.add("수원시");
			vos.add("안양시");
			vos.add("안성시");
			vos.add("평택시");
			vos.add("용인시");
			vos.add("의정부시");
			vos.add("광명시");
			vos.add("성남시");
		}
		else if(dodo.equals("충북")) {
			vos.add("청주시");
			vos.add("충주시");
			vos.add("괴산군");
			vos.add("제천시");
			vos.add("증평군");
			vos.add("단양군");
			vos.add("영동군");
			vos.add("옥천군");
		}
		else if(dodo.equals("충남")) {
			vos.add("천안시");
			vos.add("아산시");
			vos.add("논산시");
			vos.add("공주시");
			vos.add("부여군");
			vos.add("홍성군");
			vos.add("예산군");
			vos.add("청양군");
		}
		
		return vos;
	}

	@Override
	public UserVO getUserSearchVO(String mid) {
		return user2DAO.getUserSearchVO(mid);
	}

	@Override
	public List<UserVO> getUser2SearchMid(String mid) {
		return user2DAO.getUser2SearchMid(mid);
	}

	@Override
	public int fileUpload(MultipartFile fName, String mid) {
		int res = 0; 
		
		// 파일 이름에 대한 중복처리
		UUID uid = UUID.randomUUID();
		String oFileName = fName.getOriginalFilename();
		String saveFileName = mid + "_" + uid + "_" + oFileName; // mid는 누가 파일을 올렸는지..(빼도 됨) uid는 랜덤으로 숫자를 붙여서 이미지 파일 중복이 안되도록.. oFileName은 원본 파일 이름..
		
		// 파일복사처리(서버 메모리에 올라와 있는 파일의 정보를 실제 서버 파일 시스템에 저장시킨다.)
		// jsp때와 달리 바로 저장되지 않고 메모리에 올라와 있는 상태이기 떄문에, 저장시켜줘야함.
		// fName(파일의 정보를) saveFileName 요 파일 이름으로 바꾸겠다?
		// 예외 처리함.
		try {
			writeFile(fName,saveFileName);
			// 만약 여기를 통과하면 제대로 처리 된 것으로 res 를 1로 넘긴다. 
			res = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 만약 writeFile을 제대로 통과하지 못하면 res = 0 으로 넘긴다. (위에 선언할 때 초기값을 줌)
		
		return res;
	}

	private void writeFile(MultipartFile fName, String saveFileName) throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest(); 
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/"); // 등록한 mapping의 경로가 아닌, 실제 경로
		
		// 파일이 이미 서버 메모리에 올라와 있기 때문에 FileInputStream은 필요 없음.
		FileOutputStream fos = new FileOutputStream(realPath + saveFileName);  //예외처리 해야함. (예외처리 하는 부분을 IOException으로 바꾸고 위에 메소드를 생성한 writeFile(fName,saveFileName); 부분을 try catch함) 
		
		/*
		 *  일반적으로 이렇게 적는데... 한번 더 체크해 주기 위해서 밑에처럼 적기
		 *  
		// 앞에서 넘어온 객체를 올려줘야 함.ㅣ   // fName은 파일의 정보만 넘어옴. // getBytes를 적으면 fName은 바이널리로 넘어오는데 bytes로 바꿔서 write를 사용 가능하게 함.
		fos.write(fName.getBytes());
		fos.close();
		*/
		
		// 너 용량이 있어? 용량이 0이 아니야? 하고 먼저 물어보고! 용량이 있으면 파일을 올려달라~ 라고 적음!
		if((fName.getBytes().length) != -1) {
			fos.write(fName.getBytes());
			// 혹시라도 찌꺼기가 있을경우를 위해서 flush()를 적음!
			fos.flush();
			fos.close();
			
		}
		
		
	}

	@Override
	public KakaoAddressVO getKakaoAddressSearch(String address) {
		return studyDAO.getKakaoAddressSearch(address);
	}

	@Override
	public void setKakaoAddressInput(KakaoAddressVO vo) {
		studyDAO.setKakaoAddressInput(vo);
	}

	@Override
	public List<KakaoAddressVO> getKakaoAddressList() {
		return studyDAO.getKakaoAddressList();
	}

	@Override
	public int setKakaoAddressDelete(String address) {
		return studyDAO.setKakaoAddressDelete(address);
	}
	
	@Override
	public List<ChartVO> getVisitCount() {
		return studyDAO.getVisitCount();
	}

	@Override
	public String setQrCodeCreate1(String realPath, QrCodeVO vo) {
		String qrCodeName = "";
		String qrCodeName2 = "";
		//System.out.println("리얼패스 1 " + realPath);
		
		try {
			// 저장되는 파일의 구분을 하기 위해서 uuid나 날짜를 추가해준다. (중복 방지 처리)
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String strToday = sdf.format(today);
			UUID uid = UUID.randomUUID();
			String strUid = uid.toString().substring(0,2);
			
			qrCodeName = strToday + "_" + vo.getMid() + "_" + vo.getName() + "_" + vo.getEmail() + "_" + strUid;
			qrCodeName2 = "생성날짜 : " + strToday + "\n아이디 : " + vo.getMid() + "\n성명 : " + vo.getName() + "\n이메일 : " + vo.getEmail();
			// 한글 깨지기 때문에 인코딩 사용
			qrCodeName2 =  new String(qrCodeName2.getBytes("UTF-8"), "ISO-8859-1");
			
			// 파일 껍데기 만들기 (파일을 생성할 때 경로를 줌?)
			File file = new File(realPath);
			// 경로가 있니~~~? 물어보고 없으면 폴더를 만들기
			if(!file.exists()) {
				file.mkdirs(); // 폴더가 존재하지 않으면 폴더를 생성시켜준다.
			}
			// qr코드 만들기 
			// qr코드의 글자색과 배경색을 설정
			// 0x ==> 16진수
			int qrCodeColor = 0xFF000000;  //검정색 
			int qrCodeBackColor = 0xFFFFFFFF; //흰색
			
			// 구글에서 제공하는 qr코드관련 라이브러리를 주입시켰기 때문에 사용 가능
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeName2, BarcodeFormat.QR_CODE, 200, 200);
			
			// 글자색과 배경색을 만들어서... 
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor, qrCodeBackColor);
			
			// MatrixToImageWriter : 점으로 바꿔줌
			// 객체로 만들어놓기
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			// 생성된 QR코드 이미지를 그림파일로 만들어낸다.
			// 안에 적은 것과 File에 적은 확장자 명이 같아야 함! (여기서는 png로 적음)
			ImageIO.write(bufferedImage, "png", new File(realPath + qrCodeName + ".png"));
			
		} catch (IOException e) {
			System.out.println("IO오류(study-qrCode)" + e.getMessage());
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		
		return qrCodeName;
	}

	@Override
	public String setQrCodeCreate2(String realPath, QrCodeVO vo) {
		String qrCodeName = "";
		String qrCodeName2 = "";
		
		//System.out.println("리얼패스 2 " + realPath);
		
		try {
			// 저장되는 파일의 구분을 하기 위해서 uuid나 날짜를 추가해준다. (중복 방지 처리)
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String strToday = sdf.format(today);
			UUID uid = UUID.randomUUID();
			String strUid = uid.toString().substring(0,2);
			
			qrCodeName = strToday + "_" + vo.getMoveUrl().replace(":", "_").replace("/", "_").replace("?", "_") + "_" + strUid;
			qrCodeName2 = vo.getMoveUrl();
			// 한글 깨지기 때문에 인코딩 사용
			qrCodeName2 =  new String(qrCodeName2.getBytes("UTF-8"), "ISO-8859-1");
			
			// 파일 껍데기 만들기 (파일을 생성할 때 경로를 줌?)
			File file = new File(realPath);
			// 경로가 있니~~~? 물어보고 없으면 폴더를 만들기
			if(!file.exists()) {
				file.mkdirs(); // 폴더가 존재하지 않으면 폴더를 생성시켜준다.
			}
			// qr코드 만들기 
			// qr코드의 글자색과 배경색을 설정
			// 0x ==> 16진수
			int qrCodeColor = 0xFF000000;  //검정색 
			int qrCodeBackColor = 0xFFFFFFFF; //흰색
			
			// 구글에서 제공하는 qr코드관련 라이브러리를 주입시켰기 때문에 사용 가능
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeName2, BarcodeFormat.QR_CODE, 200, 200);
			
			// 글자색과 배경색을 만들어서... 
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor, qrCodeBackColor);
			
			// MatrixToImageWriter : 점으로 바꿔줌
			// 객체로 만들어놓기
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			// 생성된 QR코드 이미지를 그림파일로 만들어낸다.
			// 안에 적은 것과 File에 적은 확장자 명이 같아야 함! (여기서는 png로 적음)
			ImageIO.write(bufferedImage, "png", new File(realPath + qrCodeName + ".png"));
			
		} catch (IOException e) {
			System.out.println("IO오류(study-qrCode)" + e.getMessage());
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		
		return qrCodeName;
	}

	@Override
	public String setQrCodeCreate3(String realPath, QrCodeVO vo) {
		String qrCodeName = "";
		String qrCodeName2 = "";
		
		try {
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String strToday = sdf.format(today);
			//UUID uid = UUID.randomUUID();
			//String strUid = uid.toString().substring(0,2);
			
			qrCodeName = strToday + "_" + vo.getMovieName() + "_" + vo.getMovieDate() + "_" + vo.getMovieTime() + "_" + vo.getMovieAdult() + "_" + vo.getMovieChild() + vo.getMid();
			qrCodeName2 = "구매일자 : " + strToday + "\n영화제목 : " + vo.getMovieName() + "\n상영일자 : " + vo.getMovieDate() + "\n상영시간 : " + vo.getMovieTime() + "\n성인 : " + vo.getMovieAdult() + "매\n어린이 : " + vo.getMovieChild() + "매\n구매자 아이디 : " + vo.getMid();
			qrCodeName2 = new String(qrCodeName2.getBytes("UTF-8"), "ISO-8859-1");
			
			File file = new File(realPath);
			if(!file.exists()) file.mkdirs();	// 폴더가 존재하지 않으면 폴더를 생성시켜준다.
			
			// qr 코드 만들기
			int qrCodeColor = 0xFF000000;	// qr코드의 글자색 - 검색
			int qrCodeBackColor = 0xFFFFFFFF;	// qr코드의 배경색(바탕색) - 흰색
			
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeName2, BarcodeFormat.QR_CODE, 200, 200);
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor, qrCodeBackColor);
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			// 생성된 QR코드 이미지를 그림파일로 만들어낸다.
			ImageIO.write(bufferedImage, "png", new File(realPath + qrCodeName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrCodeName;
	}

	@Override
	public String setQrCodeCreate4(String realPath, QrCodeVO vo) {
		String qrCodeName = "";
		String qrCodeName2 = "";
		
		try {
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String strToday = sdf.format(today);
			//UUID uid = UUID.randomUUID();
			//String strUid = uid.toString().substring(0,2);
			
			qrCodeName = strToday + "_" + vo.getMovieName() + "_" + vo.getMovieDate() + "_" + vo.getMovieTime() + "_" + vo.getMovieAdult() + "_" + vo.getMovieChild() + vo.getMid();
			qrCodeName2 = "구매일자 : " + strToday + "\n영화제목 : " + vo.getMovieName() + "\n상영일자 : " + vo.getMovieDate() + "\n상영시간 : " + vo.getMovieTime() + "\n성인 : " + vo.getMovieAdult() + "매\n어린이 : " + vo.getMovieChild() + "매\n구매자 아이디 : " + vo.getMid();
			qrCodeName2 = new String(qrCodeName2.getBytes("UTF-8"), "ISO-8859-1");
			
			File file = new File(realPath);
			if(!file.exists()) file.mkdirs();	// 폴더가 존재하지 않으면 폴더를 생성시켜준다.
			
			// qr 코드 만들기
			int qrCodeColor = 0xFF000000;	// qr코드의 글자색 - 검색
			int qrCodeBackColor = 0xFFFFFFFF;	// qr코드의 배경색(바탕색) - 흰색
			
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeName2, BarcodeFormat.QR_CODE, 200, 200);
			
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor, qrCodeBackColor);
			BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
			
			// 생성된 QR코드 이미지를 그림파일로 만들어낸다.
			ImageIO.write(bufferedImage, "png", new File(realPath + qrCodeName + ".png"));
			
			// QR코드 생성후 정보를 DB에 저장시켜준다.
			vo.setPublishNow(strToday);
			vo.setQrCodeName(qrCodeName);
			System.out.println("vo : " + vo);
			studyDAO.setQrCodeCreate(vo);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return qrCodeName;
	}

	@Override
	public QrCodeVO getQrCodeSearch(String qrCode) {
		return studyDAO.getQrCodeSearch(qrCode);
	}

	@Override
	public int setThumbnailCreate(MultipartFile file) {
		int res = 0;
		
		try {
			// 중복 이름 피하기
			UUID uid = UUID.randomUUID();
			String strUid = uid.toString();
			String sFileName = strUid.substring(strUid.lastIndexOf("-")+1) + "_" + file.getOriginalFilename();
		
			// 지정된 경로에 원본 이미지 저장하기
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest(); 
			String realPath = request.getSession().getServletContext().getRealPath("/resources/data/study/");
			File realFileName = new File(realPath + sFileName);
			file.transferTo(realFileName); // 원본 이미지 저장하기  // 파일을 저장시키는 메소드
			
			// 썸네일 이미지 저장하기 (s_ 로 썸네일 구분)
			String thumbnailSaveName = realPath + "s_" + sFileName;
			File thumbnailFile = new File(thumbnailSaveName);
			
			int width = 160;
			int height = 120;
			
			// 썸네일 만들기
			Thumbnailator.createThumbnail(realFileName, thumbnailFile, width, height); // 1.저장할 원본파일이름 2. 썸네일 파일 이름 3. 폭 4. 높이
			
			res = 1;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public List<TransactionVO> getTranscationList() {
		return studyDAO.getTranscationList();
	}

	@Override
	public void setTransactionUser1Input(TransactionVO vo) {
		studyDAO.setTransactionUser1Input(vo);
	}

	@Override
	public void setTransactionUser2Input(TransactionVO vo) {
		studyDAO.setTransactionUser2Input(vo);
	}

	@Override
	public List<TransactionVO> getTranscationList2() {
		return studyDAO.getTranscationList2();
	}

	@Transactional
	@Override
	public int setTransactionUserInput(TransactionVO vo) {
		return studyDAO.setTransactionUserInput(vo);
	}

	@Override
	public int setTransactionUserInput2(String mid, String name, int age, String address, String jab) {
		return studyDAO.setTransactionUserInput2(mid, name, age,address,jab);
	}
	
}
