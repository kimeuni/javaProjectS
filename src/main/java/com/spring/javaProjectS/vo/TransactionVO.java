package com.spring.javaProjectS.vo;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class TransactionVO {
	private int idx;
	
	@Size(min=1, max=20, message = "아이디 길이가 잘못되었습니다./midSizeNo") //문자에대한 길이 체크 어노테이션
	private String mid;
	
	@Size(min=2, max=20, message = "성명의 길이가 잘못되었습니다./nameSizeNo")
	private String name;
	
	private int age;
	private String address;
	private String jab;
}
