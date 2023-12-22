package com.spring.javaProjectS.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.javaProjectS.dao.BoardDAO;
import com.spring.javaProjectS.vo.BoardReplyVO;
import com.spring.javaProjectS.vo.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardDAO boardDAO;

	@Override
	public int setBoardInput(BoardVO vo) {
		return boardDAO.setBoardInput(vo);
	}

	@Override
	public BoardVO getBoardContent(int idx) {
		return boardDAO.getBoardContent(idx);
	}

	@Override
	public List<BoardVO> getBoardList(int startIndexNo, int pageSize) {
		return boardDAO.getBoardList(startIndexNo,pageSize);
	}

	@Override
	public void imgCheck(String content) {
		// 임시파일에 저장된 그림 있는 곳은 33~ 큰 따옴표 앞까지이다.
		//                0         1         2         3         4 
		//                0123456789012345678901234567890123456789012345678
		// <p><img alt="" src="/javaProjectS/data/ckeditor/231220120201_썸네일_리리-필로루-인형.png" style="height:1000px; width:1000px" /></p>
		// <p><img alt="" src="/javaProjectS/data/board/231220120201_썸네일_리리-필로루-인형.png" style="height:1000px; width:1000px" /></p>

		
		
		// requet 객체를 사용하기 위해서 생성
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		// 이미지를 저장하기 위한 경로
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 33;
		// 그림파일의 시작위치 content.indexOf("src=\"/")+position 는 src="/ 부분을 index 0으로 시작하여 231220120201_썸네일_리리-필로루-인형.png 이런 그림파일 명이 있는 곳 앞까지가 시작부분
		// 그림이 여러개 일지도 모르니, 시작부분만 만들어서 nextImg 에 담는다. (밑에처럼 "큰 따옴표 부분까지 찾아서 자르지 않겠다는 뜻)
		String nextImg = content.substring(content.indexOf("src=\"/")+position);
		boolean sw = true;
		
		while(sw) {
			// 첫번째 그림 1개를 가져옴.. nextImg에서 짜르 부분 부터 시작하여 " 큰 따옴표가 있는 곳가지 (231220120201_썸네일_리리-필로루-인형.png 이걸 가져왔다는 뜻)
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));
			
			// 첫번째 그림의 경로와 파일명을 가져옴
			String origFilePath = realPath + "ckeditor/" + imgFile;
			// 저장할 곳의 경로로 바꿈
			String copyFilePath = realPath + "board/" + imgFile;
			
			fileCopyCheck(origFilePath,copyFilePath); //ckeditor폴더의 그림파일을 board폴더위치로 복사처리한다.
			
			// 그 다음 그림을 찾는다.
			if(nextImg.indexOf("src=\"/") == -1) sw = false;  // 만약 다음 그림이 없다면 while문을 멈춘다.
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
	}

	// 서버에서 서버로 가는 것! inputStream과 outputStream다 생성해야 함~
	private void fileCopyCheck(String origFilePath, String copyFilePath) {
		try {
			// input은 가져오는 거! 
			FileInputStream fis = new FileInputStream(new File(origFilePath));
			// 덮어씌움! (파일 저장!)
			FileOutputStream fos = new FileOutputStream(new File(copyFilePath));
			
			byte[] bytes = new byte[2048]; //2k나 4k 정도를 넘기는게 적당하다고 함.
			
			int cnt = 0;
			// bytes단위마다 읽어와서 while문이 돈다~
			while((cnt = fis.read(bytes)) != -1) {
				// cnt개수만큼 들어간다..~?
				fos.write(bytes,0,cnt);
			}
			
			// 찌꺼지 제거(?)
			fos.flush();
			fos.close();
			fis.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO오류(boardServiceImpl/이미지)" + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public int setBoardDelete(int idx) {
		return boardDAO.setBoardDelete(idx);
	}

	// 이미지 삭제 처리
	@Override
	public void imgDelete(String content) {
		// 임시파일에 저장된 그림 있는 곳은 33~ 큰 따옴표 앞까지이다.
		//                0         1         2         3         4 
		//                0123456789012345678901234567890123456789012345678
		// <p><img alt="" src="/javaProjectS/data/board/231220120201_썸네일_리리-필로루-인형.png" style="height:1000px; width:1000px" /></p>
		
		// requet 객체를 사용하기 위해서 생성
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		// 이미지를 저장하기 위한 경로
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 30;
		// 그림파일의 시작위치 content.indexOf("src=\"/")+position 는 src="/ 부분을 index 0으로 시작하여 231220120201_썸네일_리리-필로루-인형.png 이런 그림파일 명이 있는 곳 앞까지가 시작부분
		// 그림이 여러개 일지도 모르니, 시작부분만 만들어서 nextImg 에 담는다. (밑에처럼 "큰 따옴표 부분까지 찾아서 자르지 않겠다는 뜻)
		String nextImg = content.substring(content.indexOf("src=\"/")+position);
		boolean sw = true;
		
		while(sw) {
			// 첫번째 그림 1개를 가져옴.. nextImg에서 짜르 부분 부터 시작하여 " 큰 따옴표가 있는 곳가지 (231220120201_썸네일_리리-필로루-인형.png 이걸 가져왔다는 뜻)
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));
			
			// 첫번째 그림의 경로와 파일명을 가져옴
			String origFilePath = realPath + "board/" + imgFile;
			
			fileDelete(origFilePath); // board폴더의 그림파일을 삭제처리한다. 이미지 삭제할 것은 메소드로 빼서 사용
			
			// 그 다음 그림을 찾는다.
			if(nextImg.indexOf("src=\"/") == -1) sw = false;  // 만약 다음 그림이 없다면 while문을 멈춘다.
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}

		
	}

	// 실제로 서버에 저장된 파일을 삭제처리한다. (이미지가 있는지 한번 더 확인하기 위해서 메소드로 빼서 처리함)
	private void fileDelete(String origFilePath) {
		File delFile = new File(origFilePath);
		// delFile이 존재하면 delFile을 삭제해라! 라고 적음
		if(delFile.exists()) delFile.delete();
		
	}

	// 게시물 수정시 해당게시물에 사진이 있을 시 저장되어 있는 board폴더에 있는 이미지 ckeditor로 백업하기
	@Override
	public void imgBackUp(String content) {
		// 임시파일에 저장된 그림 있는 곳은 33~ 큰 따옴표 앞까지이다.
		//                0         1         2         3         4 
		//                0123456789012345678901234567890123456789012345678
		// <p><img alt="" src="/javaProjectS/data/board/231220120201_썸네일_리리-필로루-인형.png" style="height:1000px; width:1000px" /></p>
		// <p><img alt="" src="/javaProjectS/data/ckeditor/231220120201_썸네일_리리-필로루-인형.png" style="height:1000px; width:1000px" /></p>

		
		
		// requet 객체를 사용하기 위해서 생성
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		// 이미지를 저장하기 위한 경로
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 30;
		// 그림파일의 시작위치 content.indexOf("src=\"/")+position 는 src="/ 부분을 index 0으로 시작하여 231220120201_썸네일_리리-필로루-인형.png 이런 그림파일 명이 있는 곳 앞까지가 시작부분
		// 그림이 여러개 일지도 모르니, 시작부분만 만들어서 nextImg 에 담는다. (밑에처럼 "큰 따옴표 부분까지 찾아서 자르지 않겠다는 뜻)
		String nextImg = content.substring(content.indexOf("src=\"/")+position);
		boolean sw = true;
		
		while(sw) {
			// 첫번째 그림 1개를 가져옴.. nextImg에서 짜르 부분 부터 시작하여 " 큰 따옴표가 있는 곳가지 (231220120201_썸네일_리리-필로루-인형.png 이걸 가져왔다는 뜻)
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));
			
			// 첫번째 그림의 경로와 파일명을 가져옴
			String origFilePath = realPath + "board/" + imgFile;
			// 저장할 곳의 경로로 바꿈
			String copyFilePath = realPath + "ckeditor/" + imgFile;
			
			fileCopyCheck(origFilePath,copyFilePath); //ckeditor폴더의 그림파일을 board폴더위치로 복사처리한다.
			
			// 그 다음 그림을 찾는다.
			if(nextImg.indexOf("src=\"/") == -1) sw = false;  // 만약 다음 그림이 없다면 while문을 멈춘다.
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
	}

	@Override
	public int setBoardUpdate(BoardVO vo) {
		return boardDAO.setBoardUpdate(vo);
	}

	@Override
	public BoardVO getPreNexSearch(int idx, String str) {
		return boardDAO.getPreNexSearch(idx,str);
	}

	@Override
	public BoardReplyVO getBoardParentReplyCheck(int boardIdx) {
		return boardDAO.getBoardParentReplyCheck(boardIdx);
	}

	@Override
	public int setBoardReplyInput(BoardReplyVO replyVO) {
		return boardDAO.setBoardReplyInput(replyVO);
	}

	@Override
	public List<BoardReplyVO> getBoard2Reply(int idx) {
		return boardDAO.getBoard2Reply(idx);
	}

	@Override
	public void setReplyOrderUpdate(int boardIdx, int re_order) {
		boardDAO.setReplyOrderUpdate(boardIdx,re_order);
	}

	@Override
	public void setReadNumPlus(int idx) {
		boardDAO.setReadNumPlus(idx);
	}

	@Override
	public List<BoardVO> getboardSearchList(String search, String searchString, int startIndexNo, int pageSize) {
		return boardDAO.getboardSearchList(search, searchString, startIndexNo, pageSize);
	}
	
	
}
