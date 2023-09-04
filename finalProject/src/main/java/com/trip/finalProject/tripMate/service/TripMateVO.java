package com.trip.finalProject.tripMate.service;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.trip.finalProject.attachedFile.service.AttachedFileVO;

import lombok.Data;

@Data
public class TripMateVO {
		
		//member
		private String memberId;
		private String memberName;
		private String password;
		private String authority;
		private int punishCount;
		private String nationality;
		private Date birthDate;
		private String gender;
		
		//file upload관련
		private String fileId;
		private String originImg;
		private String savedImg;
		private int fileNo;
		private String imgType;
		private String loadingImg;
		
		//mate_recruit 메이트 글 등록
		private String postId;
		private String mateTitle;
		private String mateContent;
		private int mateExpense;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date startDay;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date endDay;
		private String mateStyle;
		private String mateTag;
		private int mateMax;
		private int applyNum;
		private String complition;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date writeDate;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date deadline;
		private int hit;
		private String writerId;
		
		//mate_apply 메이트 신청
		private String applyId;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date applyDay;
		private String applyInfo;
		private String approveStatus;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date approveDate;
		
		//report
		private String reportId;
		private String reporterId; //신고자
		private String punishedId;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date reportDate;
		private String reportReason;
		private String content;
		private String result;
		
		//alert
		private String alertId;
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date alertDate;
		private String confirmation;
		
		//선택한 여행 지역을 최초로 지도에 표시하기 위한 위도,경도 값
		private String tripArea;

		//에디터
		private List<AttachedFileVO> attachList;
		private List<AttachedFileVO> editorAttachList;
		
	
}
