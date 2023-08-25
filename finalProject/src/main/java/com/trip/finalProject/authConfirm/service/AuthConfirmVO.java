package com.trip.finalProject.authConfirm.service;

import lombok.Data;

@Data
public class AuthConfirmVO {
	
//	CONFIRM_ID	NUMBER	No		1	
//	CONFIRM_DATE	DATE	No		2	
//	STATUS	CHAR(2 BYTE)	No		3	권한승인 : A2 승인거절 : A1
//	REQUESTER_ID	VARCHAR2(70 BYTE)	No		4	
//	REJECT_REASON	VARCHAR2(150 BYTE)	Yes		5	
	
	// 권한승인요청 처리내역
	public Integer confirmId;
	public String confirmDate;
	public String status;
	public String requesterId;
	public String rejectReason;
	public String rejectReasonDetail;
	
}
