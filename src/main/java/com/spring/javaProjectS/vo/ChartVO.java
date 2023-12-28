package com.spring.javaProjectS.vo;

import lombok.Data;

@Data
public class ChartVO {
	// 공통필드
	private String part;
	private String title; //메인제목
	
	/* 수직막대차트(barV) */
	private String subTitle; //부제목
	private String xTitle; //x축 타이틀
	private String legend1; //범례
	private String legend2;
	private String legend3;
	private String x1;
	private String x2;
	private String x3;
	private String x4;
	private String x1Value1;
	private String x1Value2;
	private String x1Value3;
	private String x2Value1;
	private String x2Value2;
	private String x2Value3;
	private String x3Value1;
	private String x3Value2;
	private String x3Value3;
	private String x4Value1;
	private String x4Value2;
	private String x4Value3;
	
	private String visitDate;
	private int visitCount;
}
