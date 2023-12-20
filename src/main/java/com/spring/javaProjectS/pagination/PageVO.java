package com.spring.javaProjectS.pagination;

import lombok.Data;

@Data
public class PageVO {
	private int pag;
	private int pageSize;
	private int totRecCnt;
	private int totPage;
	private int startIndexNo;
	private int curScrStartNo;
	private int blockSize;
	private int curBlock;
	private int lastBlock;
	
	private String part;	//게시판 인지, 자료실인지 등을 확인하기 위함.
	private String search;	//검색
	private String searchString;	//검색 내용
	
	
}
