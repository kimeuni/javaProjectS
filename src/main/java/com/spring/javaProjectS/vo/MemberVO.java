package com.spring.javaProjectS.vo;

import lombok.Data;

@Data
public class MemberVO {
	private int idx;
	private String mid;
	private String pwd;
	private String nickName;
	private String name;
	private String gender;
	private String birthday;
	private String tel;
	private String address;
	private String email;
	private String homePage;
	private String job;
	private String hobby;
	private String photo;
	private String content;
	private String userInfor;
	private String userDel;
	private int point;
	private int level;
	private int visitCnt;
	private String startDate;
	private String lastDate;
	private int todayCnt;
	
	private int deleteDiff; //오늘날짜 - 최종접속일 (탈퇴신청후 얼만큼 지났는지 확인을위해 만듦, 30일이 지나면 개인정보를 삭제해야하기 때문)
	
	
}
