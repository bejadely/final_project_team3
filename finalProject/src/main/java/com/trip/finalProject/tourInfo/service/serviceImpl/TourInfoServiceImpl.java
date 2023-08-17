package com.trip.finalProject.tourInfo.service.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.trip.finalProject.tourInfo.service.TourInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PropertySource("classpath:application.properties")
@Service
public class TourInfoServiceImpl implements TourInfoService {

    @Value("${tourInfoApi.auth.key}")
    private String API_KEY;

    private final int NUM_OF_ROWS = 99;

    private final int ONE_NUM_OF_ROWS = 1;

    private final int FIRST_PAGE = 1;

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

    @Override
    public Map<String, String> getDetailInfoMap(int contentId, int contentTypeId) {

        Map<String, String> detailInfoMap = new HashMap<>();
        detailInfoMap.putAll(getCommonApiMap(contentId));
        detailInfoMap.putAll(getIntroApiMap(contentId, contentTypeId));

        return detailInfoMap;
    }

    private List<List<Map<String, String>>> getTourSpot(int page, int areaCode, int sigunguCode) {
        final int CONTENT_TYPE_ID = 12;

        List<List<Map<String, String>>> tourSpotList = getLocalBaseTourInfo(page, areaCode, sigunguCode, CONTENT_TYPE_ID);

        return tourSpotList;
    }

    private List<List<Map<String, String>>> getFoodSpot(int page, int areaCode, int sigunguCode) {
        final int CONTENT_TYPE_ID = 39;

        List<List<Map<String, String>>> foodSpotMap = getLocalBaseTourInfo(page, areaCode, sigunguCode, CONTENT_TYPE_ID);

        return foodSpotMap;
    }

    private List<List<Map<String, String>>> getShoppingSpot(int page, int areaCode, int sigunguCode) {
        final int CONTENT_TYPE_ID = 38;

        List<List<Map<String, String>>> shoppingSpotMap = getLocalBaseTourInfo(page, areaCode, sigunguCode, CONTENT_TYPE_ID);

        return shoppingSpotMap;
    }

    private List<List<Map<String, String>>> getCultureSpot(int page, int areaCode, int sigunguCode) {
        final int CONTENT_TYPE_ID = 14;

        List<List<Map<String, String>>> cultureSpotMap = getLocalBaseTourInfo(page, areaCode, sigunguCode, CONTENT_TYPE_ID);

        return cultureSpotMap;
    }

    private List<List<Map<String, String>>> getLeportsSpot(int page, int areaCode, int sigunguCode) {
        final int CONTENT_TYPE_ID = 28;

        List<List<Map<String, String>>> leportsSpotMap = getLocalBaseTourInfo(page, areaCode, sigunguCode, CONTENT_TYPE_ID);

        return leportsSpotMap;
    }

    private List<List<Map<String, String>>> getLocalBaseTourInfo(int page, int areaCode, int sigunguCode, int CONTENT_TYPE_ID) {

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
        if(sigunguCode != 0) {
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

            //  3개씩 잘라줄 리스트
            List<List<Map<String, String>>> chunkedItemList = new ArrayList<>();
            //  각 데이터를 담을 리스트 생성
            List<Map<String, String>> itemList = new ArrayList<>();

            // 아이템별로 데이터 추출
            int index = 1;
            for (JsonElement itemElement : itemsArray) {
                JsonObject itemObject = itemElement.getAsJsonObject();
                String contentId = itemObject.get("contentid").getAsString();
                String contentTypeId = itemObject.get("contenttypeid").getAsString();
                String firstImage = itemObject.get("firstimage").getAsString();
                String title = itemObject.get("title").getAsString();
                String address = itemObject.get("addr1").getAsString();

                // Map에 데이터 추가
                Map<String, String> localBaseTourInfoMap = new HashMap<>();
                localBaseTourInfoMap.put("contentId", contentId);
                localBaseTourInfoMap.put("contentTypeId", contentTypeId);
                localBaseTourInfoMap.put("firstImage", firstImage);
                localBaseTourInfoMap.put("title", title);
                localBaseTourInfoMap.put("address", address);

                itemList.add(localBaseTourInfoMap);
                if(index % 3 == 0) {
                    List<Map<String,String>> copiedList = new ArrayList<>(itemList);
                    chunkedItemList.add(copiedList);
                    itemList.clear();
                }
                index++;
            }

            return chunkedItemList;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    private Map<String, String> getCommonApiMap(int contentId) {
        Map<String, String> commonApiMap = getCommonApiData(contentId);

        return commonApiMap;
    }

    private Map<String, String> getIntroApiMap(int contentId, int contentTypeId) {
        Map<String, String> introApiMap = getIntroApiData(contentId, contentTypeId);

        return introApiMap;
    }

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

            switch(contentTypeId) {
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
}
