package com.spring.javaProjectS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaProjectS.dao.MemberDAO;
import com.spring.javaProjectS.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberDAO memberDAO;

	@Override
	public MemberVO getMemberIdCheck(String mid) {
		return memberDAO.getMemberIdCheck(mid);
	}

	@Override
	public MemberVO getMemberNicKCheck(String nickName) {
		return memberDAO.getMemberNicKCheck(nickName);
	}

	@Override
	public int setMemberJoinOk(MemberVO vo) {
		// 사진 처리
		vo.setPhoto("noimage.jpg");
		
		return memberDAO.setMemberJoinOk(vo);
	}

	@Override
	public int setUserDelUpdate(String mid) {
		return memberDAO.setUserDelUpdate(mid);
	}

	@Override
	public void setMemberPasswordUpdate(String mid, String pwd) {
		memberDAO.setMemberPasswordUpdate(mid, pwd);
	}

	@Override
	public MemberVO getMemberEmailCheck(String email) {
		return memberDAO.getMemberEmailCheck(email);
	}

	@Override
	public List<MemberVO> getMemberEmailList(String email) {
		return memberDAO.getMemberEmailList( email);
	}
	
}
