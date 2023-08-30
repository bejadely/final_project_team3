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
	
	private String specialtyType;
	private int price;
	private List<SepcialtiesOptionVO> optionList;
	
	
	private List<AttachedFileVO> attachList;
	private List<AttachedFileVO> editorAttachList;
}
