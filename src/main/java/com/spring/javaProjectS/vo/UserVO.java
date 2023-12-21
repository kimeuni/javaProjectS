package com.spring.javaProjectS.vo;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import lombok.Data;

// Lombok을 public 옆에다가도 넣을 수도 있다.
//public @Data class UserVO {
@Data
public class UserVO {
	private int idx;
	
	@NotEmpty(message = "아이디가 공백입니다./midEmpty") // 공백에대한 체크 어노테이션
	@Size(min=3, max=20, message = "아이디 길이가 잘못되었습니다./midSizeNo") //문자에대한 길이 체크 어노테이션
	private String mid;
	
	@NotEmpty(message = "성명이 공백입니다./nameEmpty") 
	@Size(min=2, max=20, message = "성명의 길이가 잘못되었습니다./nameSizeNo") 
	private String name;
	
//	@NotEmpty(message = "나이가 공백입니다./ageEmpty")
	@Valid
	@Range(min=19,max=99, message = "나이 범위를 벗어났습니다./ageRangeNo")
	private int age;
	
	private String address;
}
