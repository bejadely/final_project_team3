package com.trip.finalProject.packaged.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.finalProject.attachedFile.mapper.AttachedFileMapper;
import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.location.service.LocationVO;
import com.trip.finalProject.packaged.mapper.PackageMapper;
import com.trip.finalProject.packaged.service.PackageReviewVO;
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
	



    private final int FIRST_PAGE = 1;

	//패키지 등록
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
	//패키지 상세 정보
	@Override
	public PackageVO packageInfo(PackageVO packageVO) {
		// TODO Auto-generated method stub
		return packageMapper.packageInfo(packageVO);
	}
	
	//패키지 리스트
	@Override
	public List<PackageVO> getPackageList(PagingVO pagingVO) {
		// TODO Auto-generated method stub
		return packageMapper.listPackage(pagingVO);
	}
	//패키지 수 카운트
	@Override
	public int packageCount() {
		// TODO Auto-generated method stub
		return packageMapper.getPackageCount();
	}
	
	//패키지 명 검색 카운트
	@Override
	public int packageCountTitle(String keyword) {
		// TODO Auto-generated method stub
		return packageMapper.packageCountTitle(keyword);
	}
	
	
	//패키지 명 검색
	@Override
	public List<PackageVO> searchPackageByTitle(PackageVO packageVO, PagingVO pagingVO) {
		// TODO Auto-generated method stub
		return packageMapper.searchPackageByTitle(packageVO, pagingVO);
	}
	
	@Override
	public void packageUpdate(PackageVO packageVO) {
		// TODO Auto-generated method stub
		attachedFileMapper.delete(packageVO.getPostId());
		mapper.updatePackage(packageVO);
		
		if(packageVO.getAttachList()!=null || packageVO.getAttachList().size()>0) {
			packageVO.getAttachList().forEach(attach->{
				attach.setPostId(packageVO.getPostId());
				attachedFileMapper.insertAttachedFile(attach);
			});
		}
		if(packageVO.getEditorAttachList()==null || packageVO.getEditorAttachList().size()<=0) {
			return;
		}else {
			packageVO.getEditorAttachList().forEach(attach->{
			attach.setPostId(packageVO.getPostId());
			attachedFileMapper.insertAttachedFile(attach);
			});
		}
	}
	
	//가이드 페이지=====================================================================
	//리스트 페이징용
	@Override
	public int guiListCount(String memberId) {
		return packageMapper.guiListCount(memberId);
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
	@Override
	public int deletePackage(String postId) {
		return packageMapper.deletePackage(postId);
	}
	
	
	
	
	
	public List<LocationVO> getLocationList() {
		// TODO Auto-generated method stub
		return packageMapper.listArea();
	}
	
	
	
	
	
	
	//spotDetail 모달창
    @Override
    public Map<String, Object> getDetailInfoReviewList(String postId) {

        Map<String, Object> detailInfoReviewMap = new HashMap<>();


        //  리뷰 정보
        List<PackageReviewVO> packageDetailReviewVoList = packageMapper.selectReview(postId, FIRST_PAGE);
        detailInfoReviewMap.put("packageDetailReviewVoList", packageDetailReviewVoList);

        //  리뷰 총 개수 정보
        int totalCount = Integer.parseInt(packageMapper.selectReviewTotalCount(postId));
        detailInfoReviewMap.put("totalCount", totalCount);

        return detailInfoReviewMap;
    }

    //spotDetail 모달창 내 리뷰 더하기
    @Override
    public List<PackageReviewVO> getDetailReviewList(String postId, int page) {

        return packageMapper.selectReview(postId, page);
    }

    //spotDetail 모달창 내 리뷰 등록하기
    @Override
    public Map<String,Object> insertReviewInfo(PackageReviewVO PackageReviewVO) throws Exception {
        Map<String, Object> recentReviewInfo = new HashMap<>();

        int returnValue = packageMapper.insertReviewInfo(PackageReviewVO);
        if(returnValue == 0) {
            throw new Exception("not insert");
        }

        List<PackageReviewVO> recentReviewList = packageMapper.selectReview(PackageReviewVO.getOriginPostId(), FIRST_PAGE);
        recentReviewInfo.put("recentReviewList", recentReviewList);

        int totalCount = Integer.parseInt(packageMapper.selectReviewTotalCount(PackageReviewVO.getOriginPostId()));
        recentReviewInfo.put("totalCount", totalCount);

        return recentReviewInfo;
    }

    //spotDetail 모달창 내 리뷰 삭제하기
    @Override
    public Map<String, Object> deleteReviewInfo(String postId, String reviewId) throws Exception {
        Map<String, Object> recentReviewInfo = new HashMap<>();
        System.out.println(postId);
        int returnValue = packageMapper.deleteReview(reviewId);
        if(returnValue != 1 ){
            throw new Exception("not delete");
        }

        List<PackageReviewVO> recentReviewList = packageMapper.selectReview(postId, FIRST_PAGE);
        recentReviewInfo.put("recentReviewList", recentReviewList);

        int totalCount = Integer.parseInt(packageMapper.selectReviewTotalCount(postId));
        recentReviewInfo.put("totalCount", totalCount);

        return recentReviewInfo;
    }

	

	


}
