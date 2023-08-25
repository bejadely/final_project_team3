package com.trip.finalProject.alert.service;

import lombok.Data;

@Data
public class AlertVO {
	
//	ALERT_ID	VARCHAR2(13 BYTE)	No		1	
//	ALERT_DATE	DATE	Yes		2	
//	CONTENT	VARCHAR2(1000 BYTE)	Yes		3	
//	CONFIRMATION	CHAR(2 BYTE)	Yes		4	
//	MEMBER_ID	VARCHAR2(70 BYTE)	No		5	
	
	private String alertId; // ALT + 날짜(230815) + 0001
	private String alertDate; // 알림이 발생한 날짜
	private String content; // 알림 내용
	private String confirmation; // 알림확인여부 W1 : 확인완료, W2 : 미확인
	private String memberId; // 외래키 - member 테이블의 primary키 참조
}
