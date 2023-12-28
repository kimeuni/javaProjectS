package com.spring.javaProjectS.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javaProjectS.dao.StudyDAO;
import com.spring.javaProjectS.dao.User2DAO;
import com.spring.javaProjectS.vo.ChartVO;
import com.spring.javaProjectS.vo.KakaoAddressVO;
import com.spring.javaProjectS.vo.UserVO;

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
}
