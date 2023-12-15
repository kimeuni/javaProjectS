package com.spring.javaProjectS.service;

import java.util.List;

import com.spring.javaProjectS.vo.GuestVO;

public interface GuestService {

	public List<GuestVO> getGuestList(int startIndexNo, int pageSize);

	public int setGuestInput(GuestVO vo);

	public int adminLogin(String mid, String pwd);

	public int setguestDelete(int idx);

	public int getTotRecCnt();

}
