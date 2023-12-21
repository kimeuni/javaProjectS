package com.spring.javaProjectS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javaProjectS.pagination.PageProcess;
import com.spring.javaProjectS.pagination.PageVO;
import com.spring.javaProjectS.service.BoardService;
import com.spring.javaProjectS.vo.BoardReplyVO;
import com.spring.javaProjectS.vo.BoardVO;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	PageProcess pageProcess;
	
	@RequestMapping(value = "/boardList", method = RequestMethod.GET)
	public String boardListGet(Model model,
				@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
				@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize
			) {
		
		PageVO vo = pageProcess.totRecCnt(pag, pageSize, "board", "", "");
		
		List<BoardVO> vos = boardService.getBoardList(vo.getStartIndexNo(),pageSize);
		
		model.addAttribute("vos",vos);
		model.addAttribute("pageVO",vo);
		
		return "board/boardList";
	}
	
	// 게시글 작성 화면 이동
	@RequestMapping(value = "/boardInput", method = RequestMethod.GET)
	public String boardInputGet() {
		return "board/boardInput";
	}
	
	// 게시글 작성 처리
	@RequestMapping(value = "/boardInput", method = RequestMethod.POST)
	public String boardInputPost(BoardVO vo) {
		// content에 이미지가 저장되어 있다면, 저장된 이미지만 골라서 /resources/data/board/ 폴더에 저장시켜준다. (서비스 객체에서)
		// content에 src="/가 적혀있으면 이미지가 올라가 있다는 뜻이기 때문에, src="/가 있으면 이미지를 골라서 저장해달라는 뜻이된다.  
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgCheck(vo.getContent());
		
		// 이미지들의 모든 복사잡업을 마치면, ckeditor폴더경로를 board폴더 경로로 변경처리한다.('/data/ckeditor/' ==> '/data/board/')
		vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
		
		int res = boardService.setBoardInput(vo);
		
		if(res == 1) return "redirect:/message/boardInputOk";
		else return "redirect:/message/boardInputNo";
	}
	
	// 게시글 상세보기 화면 이동
	@RequestMapping(value = "/boardContent", method = RequestMethod.GET)
	public String boardContentGet(Model model, int idx,
				@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
				@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize
			) {
		
		// 조회수 증가하기
		boardService.setReadNumPlus(idx);
		
		BoardVO vo = boardService.getBoardContent(idx);
		model.addAttribute("vo",vo);
		model.addAttribute("pag",pag);
		model.addAttribute("pageSize",pageSize);
		
		// 이전글 다음글 처리
		BoardVO preVo = boardService.getPreNexSearch(idx, "preVo");
		BoardVO nextVo = boardService.getPreNexSearch(idx, "nextVo");
		model.addAttribute("preVo",preVo);
		model.addAttribute("nextVo",nextVo);
		
		// 댓글(대댓글) 추가로 처리
		List<BoardReplyVO> replyVos = boardService.getBoard2Reply(idx);
		model.addAttribute("replyVos",replyVos);
		
		return "board/boardContent";
	}
	
	// 게시글 삭제
	@RequestMapping(value = "/boardDelete", method = RequestMethod.GET)
	public String boardDeleteGet(Model model, int idx,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize
			) {

		// 게시글에 사진이 존재한다면 서버에 저장된 사진파일을 먼저 삭제시킨다.
		BoardVO vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(vo.getContent());
		
		// 앞의 작업을 마치면, DB에서 실제로 존재하는 게시글을 삭제처리한다.
		int res = boardService.setBoardDelete(idx);
		
		model.addAttribute("pag",pag);
		model.addAttribute("pageSize",pageSize);
		model.addAttribute("idx",idx);
		
		if(res == 1) return "redirect:/message/boardDeleteOk";
		else return "redirect:/message/boardDeleteNo";
		
	}
	
	// 게시글 수정
	@RequestMapping(value = "/boardUpdate", method = RequestMethod.GET)
	public String boardUpdateGet(Model model, int idx,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize
			) {
		// 수정화면으로 이동할 시에는 먼저 원본파일의 그림파일이 존재한다면, 현재폴더(board)의 그림파일을 ckeditor폴더로 복사시킨다.
		BoardVO vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1) boardService.imgBackUp(vo.getContent());
		
		// 해당 게시글의 내용을 가져가서 뿌리기 위해, vo를 넘긴다.
		model.addAttribute("vo",vo);
		model.addAttribute("pag",pag);
		model.addAttribute("page",pageSize);
		
		return "board/boardUpdate";
	}
	
	// 게시글 수정
	@RequestMapping(value = "/boardUpdate", method = RequestMethod.POST)
	public String boardUpdatePost(Model model, BoardVO vo,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize
			) {
		// 수정된 자료가 원본자료와 완전히 동이하다면 수정할 필요가 없다. 즉, DB에 저장된 원본자료를 불러와서 현재 vo에 담긴 내용(content)과 비교해 본다.
		BoardVO origVO = boardService.getBoardContent(vo.getIdx());
		
		// content의 내용이 조금이라도 변경이 되었다면 애용을 수정한 것이기에, 그림파일의 처리유무를 결정한다.
		if(!origVO.getContent().equals(vo.getContent())) {
			// 원본 게시글의 사진을 지울 것.
			// 실제로 수정하기 버튼을 클릭하면, 기존 board폴더의 그림파일이 존재했다면 1.모두 삭제시킨다.(삭제할 수 있는 이유 : 원본은 수정창에 들어오기전에 ckeditor폴더에 저장시켜두었다.) 2.그림파일의 경로를 'board'에서 'ckeditor'경로로 변경한다.
			if(origVO.getContent().indexOf("src=\"/") != -1) boardService.imgDelete(origVO.getContent()); //1번처리
			
			// 2번처리 ('board'폴더를 'ckeditor'로 경로를 변경처리한다.
			vo.setContent(vo.getContent().replace("/data/board/", "/data/ckeditor/")); 
			
			// 위에까지 하면 처음 업로드할 때와 같은 상태가 됨!
			
			// 앞의 작업이 끝나면, 파일을 처음 업로드한것과 같은 작업을 처리시켜준다.
			// 즉, content에 이미지가 저장되어 있다면, 저장된 이미지만 골라서 '/data/board/'폴더에 복사 저장시켜준다.
			System.out.println(vo.getContent());
			boardService.imgCheck(vo.getContent());
			
			// 이미지들의 모든 복사작업을 마치면, ckeditor폴더경로를 board폴더 경로로 변경처리한다.('/data/ckeditor/' = '/data/board/')
			vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
		}
		// 공통작업 (사진이 있든, 없든 상관없이 DB내용 업데이트)
		// content안의 내용과 그림파일까지 잘 정비된 vo를 DB에 Update시켜준다.
		int res = boardService.setBoardUpdate(vo);
		
		model.addAttribute("idx",vo.getIdx());
		model.addAttribute("pag",pag);
		model.addAttribute("page",pageSize);
		
		if(res == 1) return "redirect:/message/boardUpdateOk";
		else return "redirect:/message/boardUpdateNo";
		
	}
	
	// 부모댓글 입력처리
	@ResponseBody
	@RequestMapping(value = "/boardReplyInput", method = RequestMethod.POST)
	public String boardReplyInputPost(Model model, BoardReplyVO replyVO
			) {
		// 부모댓글의 경우 re_step=0, re_order=1로 처리한다. (단, 원본글의 첫번째 (부모댓글)댓글은 re_order=1이지만, 2번째이상이라면, 마지막 부모댓글의 re_order보다 +1 처리시켜준다.)
		// 부모댓글 체크 (부모 댓글이 있는지 없는지 체크)
		BoardReplyVO replyParentVO = boardService.getBoardParentReplyCheck(replyVO.getBoardIdx());
		
		// 기존 글에 부모 댓글이 달린 게 없으면 re_order를 1로
		if(replyParentVO == null) {
			replyVO.setRe_order(1);
		}
		// 부모댓글이 1개라도 있으면 기존에 있던 re_order를 +1한다.
		else {
			replyVO.setRe_order(replyParentVO.getRe_order()+1);
		}
		
		replyVO.setRe_step(0);
		
		int res = boardService.setBoardReplyInput(replyVO);
		
		return res +"";
	}
	
	// 대댓글 입력처리 ((부모) 댓글에 대한 답변글)
	@ResponseBody
	@RequestMapping(value = "/boardReplyInputRe", method = RequestMethod.POST)
	public String boardReplyInputRePost(Model model, BoardReplyVO replyVO
			) {
		// 답변글일경우는 1.re_step은 부모의 re_step+1, 2.re_order는 부모의 re_order보다 큰 댓글은 모두 +1처리 후, 3.자신의 re_order+1처리한다.
		replyVO.setRe_step(replyVO.getRe_step() +1);
		
		boardService.setReplyOrderUpdate(replyVO.getBoardIdx(), replyVO.getRe_order());
		
		replyVO.setRe_order(replyVO.getRe_order()+1);
		
		int res = boardService.setBoardReplyInput(replyVO);
		
		return res +"";
	}
	
	// 게시글 검색처리
	@RequestMapping(value = "/boardSearch", method = RequestMethod.GET)
	public String boardSearchGet(String search, Model model,
			@RequestParam(name="searchString", defaultValue = "", required = false) String searchString,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize) {
			
			PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "board", search, searchString);
			
//			System.out.println("pageVO : " + pageVO);
			
			List<BoardVO> vos = boardService.getboardSearchList(search,searchString,pageVO.getStartIndexNo(),pageSize);
			String searchTitle= "";
			if(pageVO.getSearch().equals("title")) searchTitle = "글제목";
			else if(pageVO.getSearch().equals("nickName")) searchTitle = "글쓴이";
			else searchTitle = "글내용";
			
			model.addAttribute("searchTitle", searchTitle);
			model.addAttribute("vos",vos);
			model.addAttribute("searchCount",vos.size());
			model.addAttribute("pageVO",pageVO);
			
		return "board/boardSearchList";
	}
	
}
