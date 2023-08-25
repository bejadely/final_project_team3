package com.trip.finalProject.attachedFile.service.impl;

import java.util.List;


import com.trip.finalProject.attachedFile.mapper.AttachedFileMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.finalProject.attachedFile.service.AttachedFileService;
import com.trip.finalProject.attachedFile.service.AttachedFileVO;

@Service
public class AttachedFileServiceImpl implements AttachedFileService {

	@Autowired
	AttachedFileMapper attachedFileMapper;
	
	@Override
	public List<AttachedFileVO> getAttachList(AttachedFileVO vo) {
		// TODO Auto-generated method stub
		return attachedFileMapper.findByPostId(vo);
	}

}
