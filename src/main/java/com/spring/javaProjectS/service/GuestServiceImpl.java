package com.spring.javaProjectS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaProjectS.dao.GuestDAO;
import com.spring.javaProjectS.vo.GuestVO;

@Service
public class GuestServiceImpl implements GuestService {
	
	@Autowired
	GuestDAO guestdao;

	@Override
	public List<GuestVO> getGuestList(int startIndexNo, int pageSize) {
		return guestdao.getGuestList(startIndexNo, pageSize);
	}

	@Override
	public int setGuestInput(GuestVO vo) {
		return guestdao.setGuestInput(vo);
	}

	@Override
	public int adminLogin(String mid, String pwd) {
		int res = 0;
		if(mid.equals("admin") && pwd.equals("1234")) res = 1;
		return res;
	}

	@Override
	public int setguestDelete(int idx) {
		return guestdao.setguestDelete(idx);
	}

	@Override
	public int getTotRecCnt() {
		return guestdao.getTotRecCnt();
	}

}
