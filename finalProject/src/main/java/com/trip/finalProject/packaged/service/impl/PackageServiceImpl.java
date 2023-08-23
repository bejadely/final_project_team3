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
	public String register(PackageVO vo) {
		mapper.insertEditor(vo);
		if (vo.getAttachList() == null || vo.getAttachList().size() <= 0) {
			String err= "에러";
			return err;
		}
			vo.getAttachList().forEach(attach->{
				attach.setPostId(vo.getPostId());
				attachedFileMapper.insertAttachedFile(attach);
			});
		
		return "package/packageList";
	}
	
	@Override
	public int insertEdirotInfo(PackageVO packageVO) {
		// TODO Auto-generated method stub
		return packageMapper.insertEditor(packageVO);
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
