package com.spring.javaProjectS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javaProjectS.vo.UserVO;

public interface User2DAO {

	public List<UserVO> getUser2List();

	// @Param("변수명") 가져온 값을 변수명에 담아서 넘기기 위해 사용한다.
	public List<UserVO> getUser2Search(@Param("name") String name);

	public int setUser2Delete(@Param("idx") int idx);

	public int setUser2Input(@Param("vo") UserVO vo);

	public int setUser2Update(@Param("vo") UserVO vo);
	
}