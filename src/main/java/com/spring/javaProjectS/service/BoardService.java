package com.spring.javaProjectS.service;

import java.util.List;

import com.spring.javaProjectS.vo.BoardReplyVO;
import com.spring.javaProjectS.vo.BoardVO;

public interface BoardService {

	public int setBoardInput(BoardVO vo);

	public BoardVO getBoardContent(int idx);

	public List<BoardVO> getBoardList(int startIndexNo, int pageSize);

	public void imgCheck(String content);

	public int setBoardDelete(int idx);

	public void imgDelete(String content);

	public void imgBackUp(String content);

	public int setBoardUpdate(BoardVO vo);

	public BoardVO getPreNexSearch(int idx, String str);

	public BoardReplyVO getBoardParentReplyCheck(int boardIdx);

	public int setBoardReplyInput(BoardReplyVO replyVO);

	public List<BoardReplyVO> getBoard2Reply(int idx);

	public void setReplyOrderUpdate(int boardIdx, int re_order);

	public void setReadNumPlus(int idx);

	public List<BoardVO> getboardSearchList(String search, String searchString, int startIndexNo, int pageSize);


}
