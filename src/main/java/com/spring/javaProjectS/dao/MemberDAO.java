package com.spring.javaProjectS.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.javaProjectS.vo.MemberVO;

public interface MemberDAO {

	public MemberVO getMemberIdCheck(@Param("mid") String mid);

	public MemberVO getMemberNicKCheck(@Param("nickName") String nickName);

	public int setMemberJoinOk(@Param("vo") MemberVO vo);

	public int setUserDelUpdate(@Param("mid") String mid);

}
