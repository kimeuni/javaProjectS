package com.spring.javaProjectS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
	
	@RequestMapping(value = "/message/{msgFalg}", method = RequestMethod.GET )
	public String msgGet(@PathVariable String msgFalg, String mid, Model model,
				@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
				@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize,
				@RequestParam(name="idx", defaultValue = "0", required = false) int idx,
				@RequestParam(name="temp", defaultValue = "", required = false) String temp
			) {
		if(msgFalg.equals("userDeleteOk")) {
			model.addAttribute("msg", "user가 삭제되었습니다.");
			model.addAttribute("url","user/userList");
		}
		else if(msgFalg.equals("userDeleteNo")) {
			model.addAttribute("msg", "user 삭제에 실패하였습니다.");
			model.addAttribute("url","user/userList");
		}
		else if(msgFalg.equals("user2DeleteOk")) {
			model.addAttribute("msg", "user가 삭제되었습니다.");
			model.addAttribute("url","user2/user2List");
		}
		else if(msgFalg.equals("user2DeleteNo")) {
			model.addAttribute("msg", "user 삭제에 실패하였습니다.");
			model.addAttribute("url","user2/user2List");
		}
		else if(msgFalg.equals("user2InputOk")) {
			model.addAttribute("msg", "회원가입 되었습니다.");
			model.addAttribute("url","user2/user2List");
		}
		else if(msgFalg.equals("user2InputNo")) {
			model.addAttribute("msg", "회원가입에 실패하였습니다.");
			model.addAttribute("url","user2/user2List");
		}
		else if(msgFalg.equals("user2UpdateOk")) {
			model.addAttribute("msg", "수정되었습니다.");
			model.addAttribute("url","user2/user2List");
		}
		else if(msgFalg.equals("user2UpdateNo")) {
			model.addAttribute("msg", "수정에 실패하였습니다.");
			model.addAttribute("url","user2/user2List");
		}
		else if(msgFalg.equals("guestInputOk")) {
			model.addAttribute("msg", "방명록 글이 등록되었습니다.");
			model.addAttribute("url","guest/guestList");
		}
		else if(msgFalg.equals("guestInputNo")) {
			model.addAttribute("msg", "방명록 등록에 실패하였습니다.");
			model.addAttribute("url","guest/guestInput");
		}
		else if(msgFalg.equals("adminLoginOk")) {
			model.addAttribute("msg", "관리자님 로그인 되었습니다.");
			model.addAttribute("url","guest/guestList");
		}
		else if(msgFalg.equals("adminLoginNo")) {
			model.addAttribute("msg", "아이디 혹은 비밀번호를 확인해주세요.");
			model.addAttribute("url","guest/adminLogin");
		}
		else if(msgFalg.equals("guestDeleteOk")) {
			model.addAttribute("msg", "방명록이 삭제되었습니다.");
			model.addAttribute("url","guest/guestList");
		}
		else if(msgFalg.equals("guestDeleteNo")) {
			model.addAttribute("msg", "방명록 삭제에 실패하였습니다.");
			model.addAttribute("url","guest/guestList");
		}
		else if(msgFalg.equals("adminLogout")) {
			model.addAttribute("msg", "로그아웃 되었습니다.");
			model.addAttribute("url","guest/guestList");
		}
		else if(msgFalg.equals("mailSendOk")) {
			model.addAttribute("msg", "메일이 성공적으로 전송되었습니다.");
			model.addAttribute("url","study/mail/mail");
		}
		else if(msgFalg.equals("memberLoginNo")) {
			model.addAttribute("msg", "로그인에 실패하였습니다.");
			model.addAttribute("url","member/memberLogin");
		}
		else if(msgFalg.equals("memberLoginOk")) {
			model.addAttribute("msg", mid + "님 로그인 되었습니다.");
			model.addAttribute("url","member/memberMain");
		}
		else if(msgFalg.equals("memberLogoutOk")) {
			model.addAttribute("msg",mid + "님 로그아웃 되었습니다.");
			model.addAttribute("url","member/memberLogin");
		}
		else if(msgFalg.equals("memberJoinOk")) {
			model.addAttribute("msg", "회원가입에 성공하였습니다.");
			model.addAttribute("url","member/memberLogin");
		}
		else if(msgFalg.equals("memberJoinNo")) {
			model.addAttribute("msg","회원가입에 실패하였습니다,");
			model.addAttribute("url","member/memberJoin");
		}
		else if(msgFalg.equals("memberDelOk")) {
			model.addAttribute("msg", "회원 탈퇴되었습니다.");
			model.addAttribute("url","member/memberLogin");
		}
		else if(msgFalg.equals("memberDelNo")) {
			model.addAttribute("msg","회원 탈퇴에 실패하였습니다.");
			model.addAttribute("url","member/memberMain");
		}
		else if(msgFalg.equals("memberUpdateViewNo")) {
			model.addAttribute("msg","화면에 접속중에 문제가 발생하였습니다.");
			model.addAttribute("url","member/memberMain");
		}
		else if(msgFalg.equals("boardInputOk")) {
			model.addAttribute("msg","게시판에 글이 등록되었습니다.");
			model.addAttribute("url","board/boardList");
		}
		else if(msgFalg.equals("boardInputNo")) {
			model.addAttribute("msg","게시판 글 등록에 실패하였습니다.");
			model.addAttribute("url","board/boardInput");
		}
		else if(msgFalg.equals("adminNo")) {
			model.addAttribute("msg","관리자만 접속 가능합니다.");
			model.addAttribute("url","/");
		}
		else if(msgFalg.equals("memberLevelNo")) {
			model.addAttribute("msg","해당 등급은 접속이 불가능합니다.");
			model.addAttribute("url","/");
		}
		else if(msgFalg.equals("memberNo")) {
			model.addAttribute("msg","로그인후 사용하세요");
			model.addAttribute("url","member/memberLogin");
		}
		else if(msgFalg.equals("boardDeleteOk")) {
			model.addAttribute("msg","게시글이 삭제되었습니다.");
			model.addAttribute("url","board/boardList?pag="+pag + "&pageSize="+ pageSize);
		}
		else if(msgFalg.equals("boardDeleteNo")) {
			model.addAttribute("msg","게시글 삭제에 실패하였습니다.");
			model.addAttribute("url","board/boardContent?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFalg.equals("boardUpdateOk")) {
			model.addAttribute("msg","게시글이 수정되었습니다.");
			model.addAttribute("url","board/boardContent?idx="+idx+"&pag="+pag + "&pageSize="+ pageSize);
		}
		else if(msgFalg.equals("boardUpdateNo")) {
			model.addAttribute("msg","게시글 수정에 실패하였습니다.");
			model.addAttribute("url","board/boardUpdate?idx="+idx+"&pag="+pag+"&pageSize="+pageSize);
		}
		else if(msgFalg.equals("validatorError")) {
			model.addAttribute("msg","유저 등록 실패!" + temp + "를 확인하세요.");
			model.addAttribute("url","user2/user2List");
		}
		else if(msgFalg.equals("fileUploadOk")) {
			model.addAttribute("msg","파일이 업로드 되었습니다.");
			model.addAttribute("url","study/fileUpload/fileUpload");
		}
		else if(msgFalg.equals("fileUploadNo")) {
			model.addAttribute("msg","파일 업로드에 실패하였습니다.");
			model.addAttribute("url","study/fileUpload/fileUpload");
		}
		else if(msgFalg.equals("pdsInputOk")) {
			model.addAttribute("msg","자료실에 등록되었습니다.");
			model.addAttribute("url","pds/pdsList");
		}
		else if(msgFalg.equals("pdsInputNo")) {
			model.addAttribute("msg","자료실에 등록 실패되었습니다.");
			model.addAttribute("url","pds/pdsInput");
		}
		else if(msgFalg.equals("thumbnailCreateOk")) {
			model.addAttribute("msg","썸네일 이미지가 생성되었습니다.");
			model.addAttribute("url","study/thumbnail/thumbnailForm");
		}
		else if(msgFalg.equals("thumbnailCreateNo")) {
			model.addAttribute("msg","썸네일 이미지 생성 실패");
			model.addAttribute("url","study/thumbnail/thumbnailForm");
		}
			
		return "include/message";
	}
}
