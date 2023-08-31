package com.trip.finalProject.specialties.service.impl;

import java.util.List;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.finalProject.attachedFile.mapper.AttachedFileMapper;
import com.trip.finalProject.location.service.LocationVO;
import com.trip.finalProject.packaged.mapper.PackageMapper;
import com.trip.finalProject.specialties.mapper.SpecialtiesMapper;
import com.trip.finalProject.specialties.service.SpecialtiesOptionVO;
import com.trip.finalProject.specialties.service.SpecialtiesService;
import com.trip.finalProject.specialties.service.SpecialtiesVO;

import lombok.Setter;

@Service
public class SpecialtiesServiceImpl implements SpecialtiesService {

	@Autowired
	SpecialtiesMapper specialtiesMapper;
	
	@Setter(onMethod_=@Autowired)
	private SpecialtiesMapper mapper;
	
	@Setter(onMethod_=@Autowired)
	private AttachedFileMapper attachedFileMapper;
	
	//옵션 정보 리스트
	@Override
	public List<SpecialtiesOptionVO> getOptionList(SpecialtiesOptionVO specialtiesOptionVO) {
		// TODO Auto-generated method stub
		return specialtiesMapper.findByPostId(specialtiesOptionVO);
	}
	
	//특산물 정보
	@Override
	public SpecialtiesVO getSpecialtiesInfo(SpecialtiesVO specialtiesVO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//특산물 리스트
	@Override
	public List<SpecialtiesVO> getSpecialtiesList() {
		// TODO Auto-generated method stub
		return specialtiesMapper.listSpecialties();
	}
	
	//특산물, 옵션, 첨부파일 등록
	@Transactional
	@Override
	public void insertSepcialties(SpecialtiesVO specialtiesVO) {
		// TODO Auto-generated method stub
		mapper.insertSpecialites(specialtiesVO);
		
		specialtiesVO.getOptionList().forEach(option->{
			option.setPostId(specialtiesVO.getPostId());
			mapper.insertSpecialtiesOption(option);
		});
		
		if(specialtiesVO.getAttachList()==null || specialtiesVO.getAttachList().size()<=0) {
			return;
		}else {
			specialtiesVO.getAttachList().forEach(attach->{
				attach.setPostId(specialtiesVO.getPostId());
				attachedFileMapper.insertAttachedFile(attach);
			});
		}
		if(specialtiesVO.getEditorAttachList()==null || specialtiesVO.getEditorAttachList().size()<=0) {
			return;
		}else {
			specialtiesVO.getEditorAttachList().forEach(attach->{
				attach.setPostId(specialtiesVO.getPostId());
				attachedFileMapper.insertAttachedFile(attach);
			});
		}
		
	}
	
	//지역 정보 리스트
	@Override
	public List<LocationVO> getLocationList() {
		// TODO Auto-generated method stub
		return specialtiesMapper.listArea();
	}



}
