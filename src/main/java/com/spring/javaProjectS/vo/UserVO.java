package com.spring.javaProjectS.vo;

import lombok.Data;

// Lombok을 public 옆에다가도 넣을 수도 있다.
//public @Data class UserVO {
@Data
public class UserVO {
	private int idx;
	private String mid;
	private String name;
	private int age;
	private String address;
}
