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

    private final int NUM_OF_ROWS = 3;

    @Override
    public Map<String, Object> getTourInfoMap() {

        Map<String, Object> tourInfoMap = new HashMap<>();

        tourInfoMap.put("tourSpot", getTourSpot(1, 4, 0));
        tourInfoMap.put("foodSpot", getFoodSpot(1, 4, 0));
        tourInfoMap.put("shoppingSpot", getShoppingSpot(1, 4, 0));
        tourInfoMap.put("cultureSpot", getCultureSpot(1, 4, 0));
        tourInfoMap.put("leportsSpot", getLeportsSpot(1, 4, 0));

        return tourInfoMap;
    }

    private List<Map<String, String>> getTourSpot(int page, int areaCode, int sigunguCode) {
        final int CONTENT_TYPE_ID = 12;

        List<Map<String, String>> tourSpotMap = getLocalBaseTourInfo(page, areaCode, sigunguCode, CONTENT_TYPE_ID);

        return tourSpotMap;
    }

    private List<Map<String, String>> getFoodSpot(int page, int areaCode, int sigunguCode) {
        final int CONTENT_TYPE_ID = 39;

        List<Map<String, String>> foodSpotMap = getLocalBaseTourInfo(page, areaCode, sigunguCode, CONTENT_TYPE_ID);

        return foodSpotMap;
    }

    private List<Map<String, String>> getShoppingSpot(int page, int areaCode, int sigunguCode) {
        final int CONTENT_TYPE_ID = 38;

        List<Map<String, String>> shoppingSpotMap = getLocalBaseTourInfo(page, areaCode, sigunguCode, CONTENT_TYPE_ID);

        return shoppingSpotMap;
    }

    private List<Map<String, String>> getCultureSpot(int page, int areaCode, int sigunguCode) {
        final int CONTENT_TYPE_ID = 14;

        List<Map<String, String>> cultureSpotMap = getLocalBaseTourInfo(page, areaCode, sigunguCode, CONTENT_TYPE_ID);

        return cultureSpotMap;
    }

    private List<Map<String, String>> getLeportsSpot(int page, int areaCode, int sigunguCode) {
        final int CONTENT_TYPE_ID = 28;

        List<Map<String, String>> leportsSpotMap = getLocalBaseTourInfo(page, areaCode, sigunguCode, CONTENT_TYPE_ID);

        return leportsSpotMap;
    }

    private List<Map<String, String>> getLocalBaseTourInfo(int page, int areaCode, int sigunguCode, int CONTENT_TYPE_ID) {

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

            // 데이터를 담을 리스트 생성
            List<Map<String, String>> itemList = new ArrayList<>();

            // 아이템별로 데이터 추출
            for (JsonElement itemElement : itemsArray) {
                JsonObject itemObject = itemElement.getAsJsonObject();
                String contentId = itemObject.get("contentid").getAsString();
                String contentTypeId = itemObject.get("contenttypeid").getAsString();
                String firstImage = itemObject.get("firstimage").getAsString();
                String title = itemObject.get("title").getAsString();

                // Map에 데이터 추가
                Map<String, String> localBaseTourInfoMap = new HashMap<>();
                localBaseTourInfoMap.put("contentId", contentId);
                localBaseTourInfoMap.put("contentTypeId", contentTypeId);
                localBaseTourInfoMap.put("firstImage", firstImage);
                localBaseTourInfoMap.put("title", title);

                itemList.add(localBaseTourInfoMap);
            }

            return itemList;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
