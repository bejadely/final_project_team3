package com.trip.finalProject.report.service;

import lombok.Data;

@Data
public class ReportVO {
	
	// 신고현황
	private String reportId; // 신고 접수번호 "REP2308230001"
	private String reporterId; // 신고자ID
	private String postId; // 신고하려는 등록글 ID
	private String punishedId; // 피신고자 ID
	private String reportDate; // 신고접수일
	private String reportReason; // 신고사유
	private String content; // 신고내용
	private String result; // 처리여부
	
	//제재횟수를 확인하기 위한 칼럼
	private Integer punishCount; //피신고자의 제재횟수
	
}
