package com.trip.finalProject.specialties.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.finalProject.attachedFile.mapper.AttachedFileMapper;
import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.location.service.LocationVO;
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
	public List<SpecialtiesOptionVO> getOptionList(String postId) {
		// TODO Auto-generated method stub
		return specialtiesMapper.findByPostId(postId);
	}
	
	//특산물 정보
	@Override
	public SpecialtiesVO getSpecialtiesInfo(String postId) {
		// TODO Auto-generated method stub
		return specialtiesMapper.sepcialtiesInfo(postId);
	}
	
	//특산물 리스트
	@Override
	public List<SpecialtiesVO> getSpecialtiesList(PagingVO pagingVO) {
		// TODO Auto-generated method stub
		return specialtiesMapper.listSpecialties(pagingVO);
	}
	
	@Override
	public int specialitesCount() {
		// TODO Auto-generated method stub
		return specialtiesMapper.specialtiesCount();
	}
	
	@Override
	public int specialtiesCountTitle(String keyword) {
		// TODO Auto-generated method stub
		return specialtiesMapper.specialtiesCountTitle(keyword);
	}

	@Override
	public List<SpecialtiesVO> searchspecialtiesByTitle(SpecialtiesVO specialtiesVO, PagingVO pagingVO) {
		// TODO Auto-generated method stub
		return specialtiesMapper.searchspecialtiesByTitle(specialtiesVO,pagingVO);
	}


	
	//특산물, 옵션, 첨부파일 등록
	@Transactional
	@Override
	public void insertSepcialties(SpecialtiesVO specialtiesVO) {
		// TODO Auto-generated method stub
		mapper.updateSpecialites(specialtiesVO);
		
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
	//첨부파일 삭제
	@Override
	public void updateSepcialties(SpecialtiesVO specialtiesVO) {
		// TODO Auto-generated method stub
		attachedFileMapper.delete(specialtiesVO.getPostId());
		specialtiesMapper.deleteOption(specialtiesVO.getPostId());
		mapper.insertSpecialites(specialtiesVO);
		if(specialtiesVO.getOptionList()!=null || specialtiesVO.getOptionList().size()>0) {
			specialtiesVO.getOptionList().forEach(attach->{
				attach.setPostId(specialtiesVO.getPostId());
				specialtiesMapper.insertSpecialtiesOption(attach);
			});
		}
		
		if(specialtiesVO.getAttachList()!=null || specialtiesVO.getAttachList().size()>0) {
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
		
		return specialtiesMapper.listArea();
	}
	
	//0904창민
	//특산물 전체 조회
	@Override
	public Map<String, Object> selectAllSpecial(Integer nowPage, Integer cntPerPage) {
		
		// 새로운 신고내역 카운트
		int total = specialtiesMapper.countAllSpecial();
		PagingVO pagingVO = new PagingVO(total, nowPage, cntPerPage);
		
		// 새로운 신고내역 전체 조회
		List<SpecialtiesVO> list = specialtiesMapper.selectAllSpecial(pagingVO);
		
		// 컨트롤러에 값을 보내기 위한 Map 생성
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("list", list);
		map.put("PagingVO", pagingVO);
		
		return map;
	}

	






}
