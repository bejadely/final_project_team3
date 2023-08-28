package com.trip.finalProject.excelUpload.service.impl;

import com.trip.finalProject.excelUpload.mapper.ExcelUploadMapper;
import com.trip.finalProject.excelUpload.service.ExcelUploadService;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExcelUploadServiceImpl implements ExcelUploadService {

    @Autowired
    ExcelUploadMapper excelUploadMapper;

    @Override
    public Integer uploadExcel(String fileType, String areaCode, String sigunguCode, MultipartFile file) {

        Integer result = 0;

        List<Map<String, String>> excelDataList = new ArrayList<>();

        switch(fileType) {
            case "동행유형":
//                excelDataList = getFellowList(areaCode, sigunguCode, file);
//                result = excelUploadMapper.uploadFellowList(excelDataList);
                break;
            case "여행유형":
//                excelDataList = getTripList(areaCode, sigunguCode, file);
//                result = excelUploadMapper.uploadTripList(excelDataList);
                break;
            case "맛집방문자":
                excelDataList = getRestaurantList(areaCode, sigunguCode, file);
                result = excelUploadMapper.uploadRestaurantList(excelDataList);
                break;
            case "여행지방문자" :
                excelDataList = getAttractionList(areaCode, sigunguCode, file);
                result = excelUploadMapper.uploadAttractionList(excelDataList);
            	break;
            case "방문자수":
                excelDataList = getVisitorList(file);
                result = excelUploadMapper.uploadVisitorList(excelDataList);
                break;
            case "SNS언급량":
                excelDataList = getSnsList(file);
                result = excelUploadMapper.uploadSnsList(excelDataList);
                break;
            default: 
                break;
        }


        return result;
    }

    private List<Map<String,String>> getRestaurantList(String areaCode, String sigunguCode, MultipartFile file) {
        List<Map<String, String>> restaurantList = new ArrayList<>();

        String yearMonth = getThisYearMonth();

        try (InputStream inputStream = file.getInputStream()) {

            // 엑셀 파일로 Workbook instance를 생성한다.
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            // workbook의 첫번째 sheet를 가저온다.
            XSSFSheet sheet = workbook.getSheetAt(0);

            String restaurantName = "";
            String restaurantType = "";
            String visitorNumber = "";
            // 모든 행(row)들을 조회한다. 첫줄은 데이터 목록이므로 생략. 1~5위만 가져와야해서 1부터 6까지.
            for (int i = 1; i < 6; i++) {
                restaurantName = sheet.getRow(i).getCell(1).getStringCellValue(); //  업소명
                restaurantType = sheet.getRow(i).getCell(3).getStringCellValue(); //  타입
                visitorNumber = String.valueOf((int)sheet.getRow(i).getCell(4).getNumericCellValue()); //  방문자수 : 숫자는 getNumericCellValue()로 받는데, 이건 double 타입이라 int 타입으로 형변환 해준 후 String 값으로 가져옴

                Map<String, String> restaurantMap = new HashMap<>();
                restaurantMap.put("restaurantName", restaurantName);
                restaurantMap.put("restaurantType", restaurantType);
                restaurantMap.put("visitorNumber", visitorNumber);
                restaurantMap.put("yearMonth", yearMonth);
                restaurantMap.put("areaCode", areaCode);
                restaurantMap.put("sigunguCode", sigunguCode);

                restaurantList.add(restaurantMap);
            }
            System.out.println("restaurantList = " + restaurantList);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return restaurantList;
    }

	private List<Map<String, String>> getAttractionList(String areaCode, String sigunguCode, MultipartFile file) {
		List<Map<String, String>> attractionList = new ArrayList<>();

        String yearMonth = getThisYearMonth();

        try (InputStream inputStream = file.getInputStream()) {

            // 엑셀 파일로 Workbook instance를 생성한다.
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            // workbook의 첫번째 sheet를 가저온다.
            XSSFSheet sheet = workbook.getSheetAt(0);

            String attractionName = "";
            String attractionType = "";
            String visitorNumber = "";
            // 모든 행(row)들을 조회한다. 첫줄은 데이터 목록이므로 생략. 1~5위만 가져와야해서 1부터 6까지.
            for (int i = 1; i < 6; i++) {
                attractionName = sheet.getRow(i).getCell(1).getStringCellValue(); //  업소명
                attractionType = sheet.getRow(i).getCell(3).getStringCellValue(); //  타입
                visitorNumber = String.valueOf((int)sheet.getRow(i).getCell(4).getNumericCellValue()); //  방문자수

                Map<String, String> attractionMap = new HashMap<>();
                attractionMap.put("attractionName", attractionName);
                attractionMap.put("attractionType", attractionType);
                attractionMap.put("visitorNumber", visitorNumber);
                attractionMap.put("yearMonth", yearMonth);
                attractionMap.put("areaCode", areaCode);
                attractionMap.put("sigunguCode", sigunguCode);

                attractionList.add(attractionMap);
            }
            System.out.println("attractionList = " + attractionList);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return attractionList;
	}

    private List<Map<String, String>> getVisitorList(MultipartFile file) {
        List<Map<String, String>> visitorList = new ArrayList<>();

        String yearMonth = "";

        try (InputStream inputStream = file.getInputStream()) {

            // 엑셀 파일로 Workbook instance를 생성한다.
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            // workbook의 첫번째 sheet를 가저온다.
            XSSFSheet sheet = workbook.getSheetAt(0);

            String locationName = "";
            String areaCode = "";
            String sigunguCode = "";
            String visitorNumber = "";
            // 모든 행(row)들을 조회한다. 첫줄은 데이터 목록이므로 생략. 1~5위만 가져와야해서 1부터 6까지.
            for (int i = 1; i < 9; i++) {
                if(i == 1) {
                    yearMonth = String.valueOf((int)sheet.getRow(i).getCell(0).getNumericCellValue());
                }

                locationName = sheet.getRow(i).getCell(1).getStringCellValue(); //  지역명
                Map<String,String> areaSigunguCode = getAreaSigunguCode(locationName);
                areaCode = areaSigunguCode.get("areaCode");
                sigunguCode = areaSigunguCode.get("sigunguCode");
                visitorNumber = String.valueOf((int)sheet.getRow(i).getCell(2).getNumericCellValue()); //  방문자수

                Map<String, String> visitorMap = new HashMap<>();
                visitorMap.put("areaCode", areaCode);
                visitorMap.put("sigunguCode", sigunguCode);
                visitorMap.put("visitorNumber", visitorNumber);
                visitorMap.put("yearMonth", yearMonth);

                visitorList.add(visitorMap);
            }
            System.out.println("visitorList = " + visitorList);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return visitorList;
    }

    private List<Map<String, String>> getSnsList(MultipartFile file) {
        List<Map<String, String>> snsList = new ArrayList<>();

        String yearMonth = "";

        try (InputStream inputStream = file.getInputStream()) {

            // 엑셀 파일로 Workbook instance를 생성한다.
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            // workbook의 첫번째 sheet를 가저온다.
            XSSFSheet sheet = workbook.getSheetAt(0);

            String locationName = "";
            String areaCode = "";
            String sigunguCode = "";
            String searchNumber = "";
            // 모든 행(row)들을 조회한다. 첫줄은 데이터 목록이므로 생략. 1~5위만 가져와야해서 1부터 6까지.
            for (int i = 1; i < 9; i++) {
                if(i == 1) {
                    yearMonth = String.valueOf((int)sheet.getRow(i).getCell(0).getNumericCellValue());
                }

                locationName = sheet.getRow(i).getCell(1).getStringCellValue(); //  지역명
                Map<String,String> areaSigunguCode = getAreaSigunguCode(locationName);
                areaCode = areaSigunguCode.get("areaCode");
                sigunguCode = areaSigunguCode.get("sigunguCode");
                searchNumber = String.valueOf((int)sheet.getRow(i).getCell(2).getNumericCellValue()); //  방문자수

                Map<String, String> snsMap = new HashMap<>();
                snsMap.put("areaCode", areaCode);
                snsMap.put("sigunguCode", sigunguCode);
                snsMap.put("searchNumber", searchNumber);
                snsMap.put("yearMonth", yearMonth);

                snsList.add(snsMap);
            }
            System.out.println("snsList = " + snsList);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return snsList;
    }

    private String getThisYearMonth() {
        //오늘 날짜
        Date now = new Date(); //현재시간 부르기
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM"); //시간 포맷팅하기
        String formattedNow = formatter.format(now); //현재시간에 포맷팅 적용

        return formattedNow;
    }

    private Map<String,String> getAreaSigunguCode(String locationName) {
        Map<String, String> areaSigunguCodeMap = new HashMap<>();
        String areaCode = "";
        String sigunguCode = "0";

        switch(locationName) {
            case "대구":
                areaCode = "4";
                break;
            case "부산":
                areaCode = "6";
                break;
            case "울산":
                areaCode = "7";
                break;
            case "경주":
                areaCode = "35";
                sigunguCode = "11";
                break;
            case "안동":
                areaCode = "35";
                sigunguCode = "2";
                break;
            case "포항":
                areaCode = "35";
                sigunguCode = "23";
                break;
            case "거제":
                areaCode = "36";
                sigunguCode = "1";
                break;
            case "통영":
                areaCode = "36";
                sigunguCode = "17";
                break;
        }

        areaSigunguCodeMap.put("areaCode", areaCode);
        areaSigunguCodeMap.put("sigunguCode", sigunguCode);

        return areaSigunguCodeMap;
    }
}
