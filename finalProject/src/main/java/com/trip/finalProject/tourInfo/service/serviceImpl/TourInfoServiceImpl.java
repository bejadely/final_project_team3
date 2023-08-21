package com.trip.finalProject.tourInfo.service.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.trip.finalProject.common.PagingVO;
import com.trip.finalProject.tourInfo.mapper.TourInfoMapper;
import com.trip.finalProject.tourInfo.service.LocalTourInfoDTO;
import com.trip.finalProject.tourInfo.service.SearchInfoDTO;
import com.trip.finalProject.tourInfo.service.SpotDetailReviewVO;
import com.trip.finalProject.tourInfo.service.TourInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PropertySource("classpath:application.properties")
@Service
public class TourInfoServiceImpl implements TourInfoService {

    @Autowired
    TourInfoMapper tourInfoMapper;
    

    @Value("${tourInfoApi.auth.key}")
    private String API_KEY;

    private final int NUM_OF_ROWS = 10;

    private final int NUM_OF_ROWS_SPOT = 12;

    private final int ONE_NUM_OF_ROWS = 1;

    private final int FIRST_PAGE = 1;

    private final int SPOT_DETAIL_CNT_PER_PAGE = 12;

    //관광, 맛집, 쇼핑, 문화, 레포츠 장소에 대한 api 요청 후 정보 가져와서 map에 넣어줌
    @Override
    public Map<String, Object> getTourInfoMap(int areaCode, int sigunguCode) {

        Map<String, Object> tourInfoMap = new HashMap<>();

        tourInfoMap.put("tourSpot", getTourSpot(FIRST_PAGE, areaCode, sigunguCode));
        tourInfoMap.put("foodSpot", getFoodSpot(FIRST_PAGE, areaCode, sigunguCode));
        tourInfoMap.put("shoppingSpot", getShoppingSpot(FIRST_PAGE, areaCode, sigunguCode));
        tourInfoMap.put("cultureSpot", getCultureSpot(FIRST_PAGE, areaCode, sigunguCode));
        tourInfoMap.put("leportsSpot", getLeportsSpot(FIRST_PAGE, areaCode, sigunguCode));

        return tourInfoMap;
    }

    //모달 클릭시 표출되어야 하는 정보를 api 요청을 통해 가져옴
    @Override
    public Map<String, String> getDetailInfoMap(int contentId, int contentTypeId) {

        Map<String, String> detailInfoMap = new HashMap<>();
        detailInfoMap.putAll(getCommonApiMap(contentId));
        detailInfoMap.putAll(getIntroApiMap(contentId, contentTypeId));

        return detailInfoMap;
    }

    private List<LocalTourInfoDTO> getTourSpot(int page, int areaCode, int sigunguCode) {
        final int CONTENT_TYPE_ID = 12;

        List<LocalTourInfoDTO> tourSpotList = getLocalBaseTourInfo(page, areaCode, sigunguCode, CONTENT_TYPE_ID);

        return tourSpotList;
    }

    private List<LocalTourInfoDTO> getFoodSpot(int page, int areaCode, int sigunguCode) {
        final int CONTENT_TYPE_ID = 39;

        List<LocalTourInfoDTO> foodSpotList = getLocalBaseTourInfo(page, areaCode, sigunguCode, CONTENT_TYPE_ID);

        return foodSpotList;
    }

    private List<LocalTourInfoDTO> getShoppingSpot(int page, int areaCode, int sigunguCode) {
        final int CONTENT_TYPE_ID = 38;

        List<LocalTourInfoDTO> shoppingSpotList = getLocalBaseTourInfo(page, areaCode, sigunguCode, CONTENT_TYPE_ID);

        return shoppingSpotList;
    }

    private List<LocalTourInfoDTO> getCultureSpot(int page, int areaCode, int sigunguCode) {
        final int CONTENT_TYPE_ID = 14;

        List<LocalTourInfoDTO> cultureSpotList = getLocalBaseTourInfo(page, areaCode, sigunguCode, CONTENT_TYPE_ID);

        return cultureSpotList;
    }

    private List<LocalTourInfoDTO> getLeportsSpot(int page, int areaCode, int sigunguCode) {
        final int CONTENT_TYPE_ID = 28;

        List<LocalTourInfoDTO> leportsSpotList = getLocalBaseTourInfo(page, areaCode, sigunguCode, CONTENT_TYPE_ID);

        return leportsSpotList;
    }

    //지역기반정보 API 요청하는 공통 메서드
    private List<LocalTourInfoDTO> getLocalBaseTourInfo(int page, int areaCode, int sigunguCode, int CONTENT_TYPE_ID) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://apis.data.go.kr/B551011/KorService1/areaBasedList1");
        stringBuilder.append("?serviceKey=" + API_KEY);
        stringBuilder.append("&pageNo=" + page);
        stringBuilder.append("&numOfRows=" + NUM_OF_ROWS);
        stringBuilder.append("&MobileApp=" + "AppTest");    //  고정값
        stringBuilder.append("&MobileOS=" + "ETC"); //  고정값
        stringBuilder.append("&arrange=" + "Q");    //  A:제목순, C:수정일순, D:생성일순, 대표이미지가 반드시 있는 정렬 - O:제목순, Q:수정일순, R:생성일순
        stringBuilder.append("&contentTypeId=" + CONTENT_TYPE_ID);
        stringBuilder.append("&_type=" + "json");
        stringBuilder.append("&areaCode=" + areaCode);
        if (sigunguCode != 0) {
            stringBuilder.append("&sigunguCode=" + sigunguCode);
        }

        String apiUrl = stringBuilder.toString();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            stringBuilder.setLength(0); //  위에서 쓴 StringBuilder를 초기화 시킨 후 다시 쓰기 위한 코드
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            bufferedReader.close();

            String jsonString = stringBuilder.toString();

            // Gson 객체 생성
            Gson gson = new Gson();

            // JSON 문자열을 JsonObject로 파싱
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            // 필요한 데이터 추출
            JsonObject responseBody = jsonObject.getAsJsonObject("response").getAsJsonObject("body");
            JsonArray itemsArray = responseBody.getAsJsonObject("items").getAsJsonArray("item");

            //  각 데이터를 담을 리스트 생성
            List<LocalTourInfoDTO> itemList = new ArrayList<>();

            // 아이템별로 데이터 추출
            for (JsonElement itemElement : itemsArray) {
                JsonObject itemObject = itemElement.getAsJsonObject();
                String contentId = itemObject.get("contentid").getAsString();
                String contentTypeId = itemObject.get("contenttypeid").getAsString();
                String firstImage = itemObject.get("firstimage").getAsString();
                String title = itemObject.get("title").getAsString();
                String address = itemObject.get("addr1").getAsString();

                // LocalTourInfoDTO에 데이터 추가
                LocalTourInfoDTO localTourInfoDTO = new LocalTourInfoDTO();
                localTourInfoDTO.setContentId(contentId);
                localTourInfoDTO.setContentTypeId(contentTypeId);
                localTourInfoDTO.setFirstImage(firstImage);
                localTourInfoDTO.setTitle(title);
                localTourInfoDTO.setAddress(address);

                itemList.add(localTourInfoDTO);
            }

            return itemList;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    //공통 정보 API
    private Map<String, String> getCommonApiMap(int contentId) {
        Map<String, String> commonApiMap = getCommonApiData(contentId);

        return commonApiMap;
    }

    //소개 정보 API
    private Map<String, String> getIntroApiMap(int contentId, int contentTypeId) {
        Map<String, String> introApiMap = getIntroApiData(contentId, contentTypeId);

        return introApiMap;
    }

    //공통 정보 API
    private Map<String, String> getCommonApiData(int contentId) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://apis.data.go.kr/B551011/KorService1/detailCommon1");
        stringBuilder.append("?serviceKey=" + API_KEY);
        stringBuilder.append("&pageNo=" + FIRST_PAGE);
        stringBuilder.append("&numOfRows=" + ONE_NUM_OF_ROWS);
        stringBuilder.append("&MobileApp=" + "AppTest");    //  고정값
        stringBuilder.append("&MobileOS=" + "ETC"); //  고정값
        stringBuilder.append("&contentId=" + contentId);
        stringBuilder.append("&defaultYN=" + "Y");
        stringBuilder.append("&_type=" + "json");

        String apiUrl = stringBuilder.toString();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            stringBuilder.setLength(0); //  위에서 쓴 StringBuilder를 초기화 시킨 후 다시 쓰기 위한 코드
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            bufferedReader.close();

            String jsonString = stringBuilder.toString();

            // Gson 객체 생성
            Gson gson = new Gson();

            // JSON 문자열을 JsonObject로 파싱
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            // 필요한 데이터 추출
            JsonObject responseBody = jsonObject.getAsJsonObject("response").getAsJsonObject("body");
            JsonArray itemsArray = responseBody.getAsJsonObject("items").getAsJsonArray("item");

            //  각 데이터를 담을 맵 생성
            Map<String, String> itemMap = new HashMap<>();

            // 아이템별로 데이터 추출
            JsonObject itemObject = itemsArray.get(0).getAsJsonObject();
            String homepage = itemObject.get("homepage").getAsString();

            itemMap.put("homepage", homepage);

            return itemMap;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    //소개 정보 API
    private Map<String, String> getIntroApiData(int contentId, int contentTypeId) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://apis.data.go.kr/B551011/KorService1/detailIntro1");
        stringBuilder.append("?serviceKey=" + API_KEY);
        stringBuilder.append("&pageNo=" + FIRST_PAGE);
        stringBuilder.append("&numOfRows=" + ONE_NUM_OF_ROWS);
        stringBuilder.append("&MobileApp=" + "AppTest");    //  고정값
        stringBuilder.append("&MobileOS=" + "ETC"); //  고정값
        stringBuilder.append("&contentId=" + contentId);
        stringBuilder.append("&contentTypeId=" + contentTypeId);
        stringBuilder.append("&_type=" + "json");

        String apiUrl = stringBuilder.toString();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            stringBuilder.setLength(0); //  위에서 쓴 StringBuilder를 초기화 시킨 후 다시 쓰기 위한 코드
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            bufferedReader.close();

            String jsonString = stringBuilder.toString();

            // Gson 객체 생성
            Gson gson = new Gson();

            // JSON 문자열을 JsonObject로 파싱
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            // 필요한 데이터 추출
            JsonObject responseBody = jsonObject.getAsJsonObject("response").getAsJsonObject("body");
            JsonArray itemsArray = responseBody.getAsJsonObject("items").getAsJsonArray("item");

            //  각 데이터를 담을 맵 생성
            Map<String, String> itemMap = new HashMap<>();

            // 아이템별로 데이터 추출
            JsonObject itemObject = itemsArray.get(0).getAsJsonObject();
            String phone = "";
            String parking = "";

            switch (contentTypeId) {
                case 12:
                    phone = itemObject.get("infocenter").getAsString();
                    parking = itemObject.get("parking").getAsString();
                    break;
                case 14:
                    phone = itemObject.get("infocenterculture").getAsString();
                    parking = itemObject.get("parkingculture").getAsString();
                    break;
                case 38:
                    phone = itemObject.get("infocentershopping").getAsString();
                    parking = itemObject.get("parkingshopping").getAsString();
                    break;
                case 39:
                    phone = itemObject.get("infocenterfood").getAsString();
                    parking = itemObject.get("parkingfood").getAsString();
                    break;
                case 28:
                    phone = itemObject.get("infocenterleports").getAsString();
                    parking = itemObject.get("parkingleports").getAsString();
                    break;
                default:
                    break;
            }

            itemMap.put("phone", phone);
            itemMap.put("parking", parking);

            return itemMap;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    //areaCode코드로 상세 이름 받기
    @Override
    public String getLocationNameDetail(String areaCode, String sigunguCode) {
        String locationName = "";

        switch (areaCode) {
            case "4":
                locationName = "대구 ";
                break;
            case "6":
                locationName = "부산 ";
                break;
            case "7":
                locationName = "울산 ";
                break;
            case "35":
                switch (sigunguCode) {
                    case "2":
                        locationName = "경주 ";
                        break;
                    case "23":
                        locationName = "포항 ";
                        break;
                    case "11":
                        locationName = "안동 ";
                        break;
                    default:
                        break;
                }
            case "36":
                switch (sigunguCode) {
                    case "1":
                        locationName = "거제 ";
                        break;
                    case "17":
                        locationName = "통영 ";
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }

        return locationName;
    }

    //contentId코드로 상세 이름 받기
    @Override
    public String getContentNameDetail(String contentId) {
        String contentName = "";

        switch (contentId) {
            case "12":
                contentName = "여행지";
                break;
            case "14":
                contentName = "문화시설";
                break;
            case "38":
                contentName = "쇼핑";
                break;
            case "39":
                contentName = "맛집";
                break;
            case "28":
                contentName = "레포츠";
                break;
        }
        return contentName;
    }

    //더보기 창
    @Override
    public Map<String, Object> getSpotDetail(String page, String contentTypeId, String areaCode, String sigunguCode) {

        Map<String, Object> spotDetailMap = new HashMap<>();

        spotDetailMap.put("spotDetail", getSpotDetailInfo(Integer.parseInt(page), contentTypeId, areaCode, sigunguCode));

        return spotDetailMap;
    }

    @Override
    public PagingVO getSpotDetailPagingVo(String page, Map<String, Object> spotDetailMap) {
        //PagingVO를 쓰기 위한 전체 게시글 개수 세팅
        //오브젝트 -> 원래 리스트 타입 -> totalCount 값 추출 -> int타입
        int totalCount = 0;
        for (int i = 0; i < 1; i++) {
            totalCount = Integer.parseInt(((List<Map<String, String>>) spotDetailMap.get("spotDetail")).get(i).get("totalCount"));
        }

        PagingVO pagingVO = new PagingVO(totalCount, Integer.parseInt(page), SPOT_DETAIL_CNT_PER_PAGE);

        return pagingVO;
    }

    //spotDetail 모달창
    @Override
    public Map<String, Object> getDetailInfoReviewList(String contentId, String contentTypeId) {

        Map<String, Object> detailInfoReviewMap = new HashMap<>();

        //  장소 정보
        Map<String, String> detailInfoMap = new HashMap<>();
        detailInfoMap.putAll(getCommonApiMap(Integer.parseInt(contentId)));
        detailInfoMap.putAll(getIntroApiMap(Integer.parseInt(contentId), Integer.parseInt(contentTypeId)));
        detailInfoReviewMap.put("detailInfoMap", detailInfoMap);

        //  리뷰 정보
        List<SpotDetailReviewVO> spotDetailReviewVoList = tourInfoMapper.selectSpotDetailReview(contentId, FIRST_PAGE);
        detailInfoReviewMap.put("spotDetailReviewVoList", spotDetailReviewVoList);

        //  리뷰 총 개수 정보
        int totalCount = tourInfoMapper.selectSpotDetailReviewTotalCount(contentId);
        detailInfoReviewMap.put("totalCount", totalCount);

        return detailInfoReviewMap;
    }

    //spotDetail 모달창 내 리뷰 더하기
    @Override
    public List<SpotDetailReviewVO> getDetailReviewList(String contentId, int page) {

        return tourInfoMapper.selectSpotDetailReview(contentId, page);
    }

    //spotDetail 모달창 내 리뷰 등록하기
    @Override
    public Map<String,Object> insertReviewInfo(SpotDetailReviewVO spotDetailReviewVO) throws Exception {
        Map<String, Object> recentReviewInfo = new HashMap<>();

        int returnValue = tourInfoMapper.insertReviewInfo(spotDetailReviewVO);
        if(returnValue == 0) {
            throw new Exception("not insert");
        }

        List<SpotDetailReviewVO> recentReviewList = tourInfoMapper.selectSpotDetailReview(spotDetailReviewVO.getOriginPostId(), FIRST_PAGE);
        recentReviewInfo.put("recentReviewList", recentReviewList);

        int totalCount = tourInfoMapper.selectSpotDetailReviewTotalCount(spotDetailReviewVO.getOriginPostId());
        recentReviewInfo.put("totalCount", totalCount);

        return recentReviewInfo;
    }

    //spotDetail 모달창 내 리뷰 삭제하기
    @Override
    public Map<String, Object> deleteReviewInfo(int contentId, String reviewId) throws Exception {
        Map<String, Object> recentReviewInfo = new HashMap<>();

        int returnValue = tourInfoMapper.deleteReview(reviewId);
        if(returnValue != 1 ){
            throw new Exception("not delete");
        }

        List<SpotDetailReviewVO> recentReviewList = tourInfoMapper.selectSpotDetailReview(String.valueOf(contentId), FIRST_PAGE);
        recentReviewInfo.put("recentReviewList", recentReviewList);

        int totalCount = tourInfoMapper.selectSpotDetailReviewTotalCount(String.valueOf(contentId));
        recentReviewInfo.put("totalCount", totalCount);

        return recentReviewInfo;
    }

    private List<Map<String, String>> getSpotDetailInfo(int page, String contentTypeId, String areaCode, String sigunguCode) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("http://apis.data.go.kr/B551011/KorService1/areaBasedList1");
        stringBuilder.append("?serviceKey=" + API_KEY);
        stringBuilder.append("&pageNo=" + page);
        stringBuilder.append("&numOfRows=" + NUM_OF_ROWS_SPOT);
        stringBuilder.append("&MobileApp=" + "AppTest");    //  고정값
        stringBuilder.append("&MobileOS=" + "ETC"); //  고정값
        stringBuilder.append("&arrange=" + "Q");    //  A:제목순, C:수정일순, D:생성일순, 대표이미지가 반드시 있는 정렬 - O:제목순, Q:수정일순, R:생성일순
        stringBuilder.append("&contentTypeId=" + contentTypeId);
        stringBuilder.append("&_type=" + "json");
        stringBuilder.append("&areaCode=" + areaCode);
        if (!sigunguCode.equals("0")) {
            stringBuilder.append("&sigunguCode=" + sigunguCode);
        }

        String apiUrl = stringBuilder.toString();


        try {
            URL url = new URL(apiUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            stringBuilder.setLength(0); //  위에서 쓴 StringBuilder를 초기화 시킨 후 다시 쓰기 위한 코드
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            bufferedReader.close();

            String jsonString = stringBuilder.toString();

            // Gson 객체 생성
            Gson gson = new Gson();

            // JSON 문자열을 JsonObject로 파싱
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            // 필요한 데이터 추출
            JsonObject response = jsonObject.getAsJsonObject("response");
            JsonObject body = response.getAsJsonObject("body");
            JsonObject items = body.getAsJsonObject("items");
            JsonArray itemsArray = items.getAsJsonArray("item");

            String totalCount = body.get("totalCount").getAsString();

            //  각 데이터를 담을 리스트 생성
            List<Map<String, String>> itemList = new ArrayList<>();

            // 아이템별로 데이터 추출
            for (JsonElement itemElement : itemsArray) {
                JsonObject itemObject = itemElement.getAsJsonObject();

                String contentIdSpotDetailPage = itemObject.get("contentid").getAsString();
                String contentTypeIdSpotDetailPage = itemObject.get("contenttypeid").getAsString();
                String firstImage = itemObject.get("firstimage").getAsString();
                String title = itemObject.get("title").getAsString();
                String address = itemObject.get("addr1").getAsString();

                // Map에 데이터 추가
                Map<String, String> getSpotDetailInfoMap = new HashMap<>();
                getSpotDetailInfoMap.put("contentId", contentIdSpotDetailPage);
                getSpotDetailInfoMap.put("contentTypeId", contentTypeIdSpotDetailPage);
                getSpotDetailInfoMap.put("firstImage", firstImage);
                getSpotDetailInfoMap.put("title", title);
                getSpotDetailInfoMap.put("address", address);
                getSpotDetailInfoMap.put("totalCount", totalCount);

                itemList.add(getSpotDetailInfoMap);

            }

            return itemList;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

    }

    //searchDetail페이지 정보 가져오기
	@Override
	public List<SearchInfoDTO> getsearchInfo(String searchKeyWord) {
		
		//인코딩
		String encodedSearchKeyWord="";
		try {
			encodedSearchKeyWord = URLEncoder.encode(searchKeyWord, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			
			e1.printStackTrace();
		}
		
		StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://apis.data.go.kr/B551011/KorService1/searchKeyword1");
        stringBuilder.append("?serviceKey=" + API_KEY);
        stringBuilder.append("&MobileApp=" + "AppTest");    //  고정값
        stringBuilder.append("&MobileOS=" + "ETC"); //  고정값
        stringBuilder.append("&arrange=" + "Q");    //  A:제목순, C:수정일순, D:생성일순, 대표이미지가 반드시 있는 정렬 - O:제목순, Q:수정일순, R:생성일순
        stringBuilder.append("&_type=" + "json");
        stringBuilder.append("&keyword=" + encodedSearchKeyWord);
        
        String apiUrl = stringBuilder.toString();

        
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            stringBuilder.setLength(0); //  위에서 쓴 StringBuilder를 초기화 시킨 후 다시 쓰기 위한 코드
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            bufferedReader.close();

            String jsonString = stringBuilder.toString();
            System.out.println("jsonString = " + jsonString);

            // Gson 객체 생성
            Gson gson = new Gson();

            // JSON 문자열을 JsonObject로 파싱
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            // 필요한 데이터 추출
            JsonObject responseBody = jsonObject.getAsJsonObject("response").getAsJsonObject("body");
            JsonArray itemsArray = responseBody.getAsJsonObject("items").getAsJsonArray("item");

            
            //  각 데이터를 담을 리스트 생성
            List<SearchInfoDTO> itemList = new ArrayList<>();
            

            // 아이템별로 데이터 추출
            for (JsonElement itemElement : itemsArray) {
            	SearchInfoDTO searchInfoDTO = new SearchInfoDTO();
                JsonObject itemObject = itemElement.getAsJsonObject();
                String contentId = itemObject.get("contentid").getAsString();
                String contentTypeId = itemObject.get("contenttypeid").getAsString();
                String firstImage = itemObject.get("firstimage").getAsString();
                String title = itemObject.get("title").getAsString();
                String address = itemObject.get("addr1").getAsString();
                String areaCode = itemObject.get("areacode").getAsString();
                String sigunguCode = "0";
                if(areaCode.equals("35")  || areaCode.equals("36")) {
                	sigunguCode=itemObject.get("sigungucode").getAsString();
                }
                
                searchInfoDTO.setContentId(contentId);
                searchInfoDTO.setContentTypeId(contentTypeId);
                searchInfoDTO.setFirstImage(firstImage);
                searchInfoDTO.setAddress(address);
                searchInfoDTO.setTitle(title);
                searchInfoDTO.setAreaCode(areaCode);
                searchInfoDTO.setSigunguCode(sigunguCode);

                itemList.add(searchInfoDTO);
           
            }

            return itemList;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
        
	}

}
