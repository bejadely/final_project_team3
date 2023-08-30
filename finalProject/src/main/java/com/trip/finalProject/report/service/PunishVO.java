package com.trip.finalProject.report.service;

import lombok.Data;

@Data
public class PunishVO {
	
	public String punishId; // 제재 ID
	public String punishDate; // 제재처리일자 DEFAULT : SYSDATE
	public String reportId; // 신고접수번호 REP+날짜+0001
	public String punishedId; // 피제재자ID 
	public String punishReason; //제재사유
	
}
