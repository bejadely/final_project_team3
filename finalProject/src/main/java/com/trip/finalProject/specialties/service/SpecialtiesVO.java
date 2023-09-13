package com.trip.finalProject.specialties.service;

import java.util.List;

import com.trip.finalProject.attachedFile.service.AttachedFileVO;

import lombok.Data;
@Data
public class SpecialtiesVO {
	private String postId;
	private String content;
	private String title;
	private String type;
	private String writerId;
	private String discount;
	private int areaCode;
	private int sigunguCode;
	private String loadingImg;
	private String specialtyType;
	private String locationName;
	private int price;
	private List<SpecialtiesOptionVO> optionList;
	
	
	private List<AttachedFileVO> attachList;
	private List<AttachedFileVO> editorAttachList;
	
	//0904 창민 추가
	private Integer salesQuantity; // 판매수량을 담기 위한 필드
}
