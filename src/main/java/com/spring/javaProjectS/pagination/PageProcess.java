package com.spring.javaProjectS.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaProjectS.dao.BoardDAO;

// 일하는 애니까.. 일하는 애인지 알려주기 위한 서비스 붙여주기..
@Service
public class PageProcess {
	// 필요한 자료 스캔
	@Autowired
	BoardDAO boardDAO;
	
	// section이라는 변수를 사용하여 게시판인지, 자료실인지를 알려주는 것(대분류)
	// part는 소분류.. ex) 자료실에(section) 학습(part)을 들어갈거양
	public PageVO totRecCnt(int pag,int pageSize,String section,String part,String searchString) {
		PageVO pageVO = new PageVO();
		
		int totRecCnt = 0; //초기값
		
		if(section.equals("board")) {
			// 만약 part가 있을 시 이렇게 함! part가 안 넘어오면 전체 리스트 출력 아닐 시, 파트에 관한 카테고리만 출력!
//			if(part.equals("")) totRecCnt = boardDAO.totRecCnt();
//			else totRecCnt = boardDAO.totRecCnt(part);
			totRecCnt = boardDAO.totRecCnt();
		}
		
		int totPage = (totRecCnt % pageSize)==0 ? (totRecCnt / pageSize) : (totRecCnt / pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		//--------------------------------------------------------------------
		int blockSize = 3;
		int curBlock = (pag-1)/blockSize;
		int lastBlock = (totPage-1)/blockSize;
		
		pageVO.setPag(pag);
		pageVO.setPageSize(pageSize);
		pageVO.setTotRecCnt(totRecCnt);
		pageVO.setTotPage(totPage);
		pageVO.setStartIndexNo(startIndexNo);
		pageVO.setCurScrStartNo(curScrStartNo);
		pageVO.setBlockSize(blockSize);
		pageVO.setCurBlock(curBlock);
		pageVO.setLastBlock(lastBlock);
		pageVO.setPart(part);
		pageVO.setSearch(part);
		pageVO.setSearchString(searchString);
		return pageVO;
	}
	
}
