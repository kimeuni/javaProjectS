package com.spring.javaProjectS.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.javaProjectS.service.GuestService;
import com.spring.javaProjectS.vo.GuestVO;

@Controller
@RequestMapping("/guest")
public class GuestController {
	
	@Autowired
	GuestService guestService;
	
	@RequestMapping(value = "/guestList", method = RequestMethod.GET)
	public String guestListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1",required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "3",required = false) int pageSize
			) {
		//page만들기
		// 3. 총 레코드 건수를 구한다. (sql문 명령어 중, count함수 사용)
		int totRecCnt =  guestService.getTotRecCnt();
		// 4. 총 페이지 건수를 구한다.
		int totPage = (totRecCnt % pageSize)==0 ? (totRecCnt / pageSize) : (totRecCnt / pageSize) + 1;
		// 5. 현재 페이지에 출력할 시작 인덱스번호를 구한다. (인덱스 번호 = 현재페이지 -1 * 페이지 사이즈 (인덱스 번호는 0부터 나옴))  
		// (ex: 5번째 페이지인데 페이지 사이즈가 5일때 ... => (5-1) * 5 = 20     .. 5번째 페이지의 시작 인덱스 번호는 20이다
		int startIndexNo = (pag - 1) * pageSize;
		// 6. 현재 화면에 표시될 시작번호를 구한다. (전체 레코드번호 - 인덱스번호)
		int curScrStartNo = totRecCnt - startIndexNo;
		
		//--------------------------------------------------------------------
		
		// 블록페이징 처리 (시작블록의 번호를 0번으로 처리했다.)
		// 1. 블록의 크기 (여기선 3으로 결정했다.)
		int blockSize = 3;
		// 2. 현재페이지가 속한 블록 번호를 구한다. (예: 총레코드갯수 38개일경우(총8페이지) 1/2/3 페이지는 0블록, 4/5/6페이지는 1블록 ,7/8 페이지는 2블록)
		int curBlock = (pag-1)/blockSize;
		// 3. 마지막 블럭을 구한다. (블록의 크기)
		int lastBlock = (totPage-1)/blockSize;
		
		// 지정된 페이지의 자료를 요청한 페이지의 블량만큼 가져온디.
		List<GuestVO> vos = guestService.getGuestList(startIndexNo,pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pag", pag);
		model.addAttribute("totPage", totPage);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("curScrStartNo", curScrStartNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		
		return "guest/guestList";
	}
	
	@RequestMapping(value = "/guestInput", method = RequestMethod.GET)
	public String guestInputGet() {
		
		return "guest/guestInput";
	}
	
	@RequestMapping(value = "/guestInput", method = RequestMethod.POST)
	public String guestInputPost(GuestVO vo) {
		
		int res = guestService.setGuestInput(vo);
		if(res != 0) return "redirect:/message/guestInputOk";
		else return "redirect:/message/guestInputNo";
	}
	
	@RequestMapping(value = "/adminLogin", method = RequestMethod.GET)
	public String adminLoginGet() {
		return "guest/adminLogin";
	}
	
	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
	public String adminLoginPost(HttpSession session, 
			@RequestParam (name="mid",defaultValue = "guest", required = false) String mid,
			@RequestParam (name="pwd",defaultValue = "", required = false) String pwd) {
		
		// DB를 아직 안 만들어서 DB처리한 것 처럼 보일려고 서비스에서 가서 하드코딩함 ㅎㅎ
		int res = guestService.adminLogin(mid,pwd);
		
		if(res != 0) {
			session.setAttribute("sAdmin", "adminOk");
			return "redirect:/message/adminLoginOk";
		}
		else return "redirect:/message/adminLoginNo";
	}
	
	@RequestMapping(value = "/guestDelete", method = RequestMethod.GET)
	public String guestDeleteGet(int idx) {
		
		int res = guestService.setguestDelete(idx);
		
		if(res != 0) {
			return "redirect:/message/guestDeleteOk";
		}
		else return "redirect:/message/guestDeleteNo";
	}
	
	@RequestMapping(value = "/adminLogout", method = RequestMethod.GET)
	public String adminLogoutGet(HttpSession session) {
		session.removeAttribute("sAdmin");
		return "redirect:/message/adminLogout";
	}
}
