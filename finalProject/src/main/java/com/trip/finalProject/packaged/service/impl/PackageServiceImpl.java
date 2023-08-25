package com.trip.finalProject.packaged.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.finalProject.attachedFile.mapper.AttachedFileMapper;
import com.trip.finalProject.packaged.mapper.PackageMapper;
import com.trip.finalProject.packaged.service.PackageService;
import com.trip.finalProject.packaged.service.PackageVO;

import lombok.Setter;

@Service
public class PackageServiceImpl implements PackageService {
	
	@Autowired
	PackageMapper packageMapper;
	
	@Setter(onMethod_=@Autowired)
	private PackageMapper mapper;
	
	@Setter(onMethod_=@Autowired)
	private AttachedFileMapper attachedFileMapper;
	
	@Transactional
	@Override
	public void register(PackageVO vo) {
		mapper.insertEditor(vo);
		if (vo.getAttachList() == null || vo.getAttachList().size() <= 0) {			
			return ;
		}
		if(vo.getEditorAttachList()==null || vo.getEditorAttachList().size()<=0) {
			return;
		}
			vo.getAttachList().forEach(attach->{
				attach.setPostId(vo.getPostId());
				attachedFileMapper.insertAttachedFile(attach);
			});
			vo.getEditorAttachList().forEach(attach->{
				attach.setPostId(vo.getPostId());
				attachedFileMapper.insertAttachedFile(attach);
			});
				
	}
	
	@Override
	public PackageVO packageInfo(PackageVO packageVO) {
		// TODO Auto-generated method stub
		return packageMapper.packageInfo(packageVO);
	}

	@Override
	public List<PackageVO> getPackageList() {
		// TODO Auto-generated method stub
		return packageMapper.listPackage();
	}

}
