package com.spring.javaProjectS.service;

import com.spring.javaProjectS.vo.MemberVO;

public interface MemberService {

	public MemberVO getMemberIdCheck(String mid);

	public MemberVO getMemberNicKCheck(String nickName);

	public int setMemberJoinOk(MemberVO vo);

	public int setUserDelUpdate(String mid);

}
