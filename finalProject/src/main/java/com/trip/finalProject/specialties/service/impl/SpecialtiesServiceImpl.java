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
	
	
	@Override
	public SpecialtiesVO getSpecialtiesInfo(SpecialtiesVO specialtiesVO) {
		// TODO Auto-generated method stub
		return null;
	}
	@Transactional
	@Override
	public List<SpecialtiesVO> getSpecialtiesList() {
		// TODO Auto-generated method stub
		return mapper.listSpecialties();
	}
	
	@Override
	public void insertSepcialties(SpecialtiesVO specialtiesVO) {
		// TODO Auto-generated method stub
		mapper.insertSpecialites(specialtiesVO);
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
		specialtiesVO.getOptionList().forEach(attach->{
			attach.setPostId(specialtiesVO.getPostId());
			mapper.insertSpecialtiesOption(attach);
		});
	}

	@Override
	public List<LocationVO> getLocationList() {
		// TODO Auto-generated method stub
		return specialtiesMapper.listArea();
	}

}
