package com.spring.javaProjectS.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javaProjectS.dao.PdsDAO;
import com.spring.javaProjectS.vo.PdsVO;

@Service
public class PdsServiceImpl implements PdsService {
	
	@Autowired
	PdsDAO pdsDAO;

	@Override
	public List<PdsVO> getPdsList(int startIndexNo, int pageSize, String part) {
		// TODO Auto-generated method stub
		return pdsDAO.getPdsList(startIndexNo,pageSize,part);
	}

	@Override
	public int setPdsInput(PdsVO vo, MultipartHttpServletRequest mFile) {
		int res = 0;
		try {
			// 이걸 이용하면 여러개의 파일을 하나씩 가져올 수 있다.
			// request.getparameter에 들어 있는 것을 가져오는 것 이기 때문에 getFiles()안에 들어있는 "file"은 jsp에 있는 pdsInput에서 type="file"에 적은 name이름이다.
			List<MultipartFile> fileList = mFile.getFiles("file");
			String oFileNames = "";
			String saveFileNames = "";
			int fileSizes = 0;
			
			// for문으로 돌려서 꺼낸다.
			for(MultipartFile file : fileList) {
				// 세부 파일에 대한 1개의 정보를 꺼내서 이름을 가져옴.
				// 서버 저장시, 변수 이름이 중복되는 것을 방지하기 위해서 다시 변수로 받았다.
				String oFileName = file.getOriginalFilename();
				String saveFileName = saveFileName(oFileName);
				
				// 파일의 속성과 서버폴더에 저장할 파일명을 가지고 메소드로 보낸다.
				writeFile(file,saveFileName);
				
				// 데이터 베이스에 저장하기 위해서
				oFileNames += oFileName + "/";
				saveFileNames += saveFileName + "/";
				fileSizes += file.getSize(); // getSize()가 개별파일의 사이즈(용량)을 가져올 수 있는 거임!
			}
			// 맨 뒤에 / 자르기
			oFileNames = oFileNames.substring(0,oFileNames.length()-1);
			saveFileNames = saveFileNames.substring(0,saveFileNames.length()-1);
			
			vo.setFName(oFileNames);
			vo.setFSName(saveFileNames);
			vo.setFSize(fileSizes);
			
			
			// file은 여기서 저장하고 끝낼 것이기 떄문에 vo만 넘긴다.
			// 파일 업로드 시킴.
			res = pdsDAO.setPdsInput(vo);
		} catch (IOException e) {
			System.out.println("IO오류" + e.getMessage());
			e.printStackTrace();
		}
		return res;
	}

	// 파일 서버 폴더에 저장
	private void writeFile(MultipartFile file, String saveFileName) throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		
		// 바이널리를 바이트로 바꿈!
		byte[] data = file.getBytes();
		FileOutputStream fos = new FileOutputStream(realPath + saveFileName);
		
		fos.write(data);
		fos.close();
		
	}

	// 파일명 중복방지를 위하여, 서버에 저장될 실제 파일명 만들기
	private String saveFileName(String oFileName) {
		String fileName = "";
		
		Calendar cal = Calendar.getInstance();
		fileName += cal.get(Calendar.YEAR);
		fileName += cal.get(Calendar.MONTH);
		fileName += cal.get(Calendar.DATE);
		fileName += cal.get(Calendar.HOUR);
		fileName += cal.get(Calendar.MINUTE);
		fileName += cal.get(Calendar.SECOND);
		fileName += cal.get(Calendar.MILLISECOND);
		fileName += "_" + oFileName;
		
		
		return fileName;
	}

	@Override
	public int setPdsDownNumCheck(int idx) {
		return pdsDAO.setPdsDownNumCheck(idx);
	}

	@Override
	public PdsVO getIdxSearch(int idx) {
		return pdsDAO.getIdxSearch(idx);
	}

	@Override
	public int setPdsDelete(PdsVO vo) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		String[] fSNames = vo.getFSName().split("/");
		
		// 서버에 저장된 실제 파일을 삭제처리
		for(int i=0; i<fSNames.length; i++) {
			new File(realPath+fSNames[i]).delete();
		}
		return pdsDAO.setPdsDelete(vo.getIdx());
	}
}
