package com.trip.finalProject.packaged.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.finalProject.attachedFile.mapper.AttachedFileMapper;
import com.trip.finalProject.common.PagingVO;
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
		}else {
			vo.getAttachList().forEach(attach->{
				attach.setPostId(vo.getPostId());
				attachedFileMapper.insertAttachedFile(attach);
			});
		}
		if(vo.getEditorAttachList()==null || vo.getEditorAttachList().size()<=0) {
			return;
		}else {
			vo.getEditorAttachList().forEach(attach->{
				attach.setPostId(vo.getPostId());
				attachedFileMapper.insertAttachedFile(attach);
			});
		}
			
		
				
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
	//가이드 페이지=====================================================================
	//리스트 페이징용
	@Override
	public int guiListCount(PackageVO pacVO) {
		return packageMapper.guiListCount(pacVO);
	}
	//리스트 불러오기
	@Override
	public List<PackageVO> guiListPackage(PackageVO pacVO, PagingVO pagingVO) {
		return packageMapper.guiListPackage(pacVO, pagingVO);
	}
	//판매완료 페이징
	@Override
	public int guiListComCount(PackageVO pacVO) {
		return packageMapper.guiListComCount(pacVO);
	}
	//판매완료 리스트
	@Override
	public List<PackageVO> guiListComPackage(PackageVO pacVO, PagingVO pagingVO) {
		return packageMapper.guiListComPackage(pacVO, pagingVO);
	}

	//가이드 패키지 상세정보
	@Override
	public PackageVO guidePacInfo(PackageVO pacVO) {
		return packageMapper.guidePacInfo(pacVO);
	}

	@Override
	public List<PackageVO> pacMember(PackageVO pacVO) {
		return packageMapper.pacMember(pacVO);
	}
	
	
	

}
