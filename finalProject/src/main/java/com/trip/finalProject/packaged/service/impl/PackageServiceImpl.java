package com.trip.finalProject.packaged.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.finalProject.attachedFile.mapper.AttachedFileMapper;
import com.trip.finalProject.location.service.LocationVO;
import com.trip.finalProject.packaged.mapper.PackageMapper;
import com.trip.finalProject.packaged.service.PackageReviewVO;
import com.trip.finalProject.packaged.service.PackageService;
import com.trip.finalProject.packaged.service.PackageVO;
import com.trip.finalProject.tourInfo.service.SpotDetailReviewVO;

import lombok.Setter;

@Service
public class PackageServiceImpl implements PackageService {
	
	@Autowired
	PackageMapper packageMapper;
	
	@Setter(onMethod_=@Autowired)
	private PackageMapper mapper;
	
	@Setter(onMethod_=@Autowired)
	private AttachedFileMapper attachedFileMapper;
	
	private final int NUM_OF_ROWS = 10;

    private final int NUM_OF_ROWS_SPOT = 12;

    private final int ONE_NUM_OF_ROWS = 1;

    private final int FIRST_PAGE = 1;

    private final int SPOT_DETAIL_CNT_PER_PAGE = 12;
	
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
