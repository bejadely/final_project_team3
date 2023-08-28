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

        switch(fileType) {
            case "restaurant":
                result = uploadRestaurant(areaCode, sigunguCode, file);
                break;
            case "attraction" :
            	result = uploadAttraction(areaCode, sigunguCode, file); 
            	break;
            default: 
                break;
        }


        return result;
    }

    private Integer uploadAttraction(String areaCode, String sigunguCode, MultipartFile file) {
		int result = 0;
		
		List<Map<String, String>> attractionList = getAttractionList(areaCode, sigunguCode, file);
		
		if(attractionList.size() > 0) {
            result = excelUploadMapper.uploadAttractionList(attractionList);
        }
				
		return result;
	}

	private List<Map<String, String>> getAttractionList(String areaCode, String sigunguCode, MultipartFile file) {
		List<Map<String, String>> attractionList = new ArrayList<>();

        String yearMonth = getThisYearMonth();

        try (InputStream inputStream = file.getInputStream()) {

            // 엑셀 파일로 Workbook instance를 생성한다.
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            // workbook의 첫번째 sheet를 가저온다.
            XSSFSheet sheet = workbook.getSheetAt(0);

            // 모든 행(row)들을 조회한다. 첫줄은 데이터 목록이므로 생략. 1~5위만 가져와야해서 1부터 6까지.
            for (int i = 1; i < 6; i++) {
                // 각각의 행에 존재하는 모든 열(cell)을 순회한다.
                Iterator<Cell> cellIterator = sheet.getRow(i).cellIterator();

                String attractionName = sheet.getRow(i).getCell(1).getStringCellValue(); //  업소명
                String attractionType = sheet.getRow(i).getCell(3).getStringCellValue(); //  타입
                String visitorNumber = String.valueOf((int)sheet.getRow(i).getCell(4).getNumericCellValue()); //  방문자수

                Map<String, String> attractionMap = new HashMap<>();
                attractionMap.put("restaurantName", attractionName);
                attractionMap.put("restaurantType", attractionType);
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

	private Integer uploadRestaurant(String areaCode, String sigunguCode, MultipartFile file) {

        int result = 0;
        List<Map<String,String>> restaurantList = getRestaurantList(areaCode, sigunguCode, file);

        if(restaurantList.size() > 0) {
            result = excelUploadMapper.uploadRestaurantList(restaurantList);
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

            // 모든 행(row)들을 조회한다. 첫줄은 데이터 목록이므로 생략. 1~5위만 가져와야해서 1부터 6까지.
            for (int i = 1; i < 6; i++) {
                // 각각의 행에 존재하는 모든 열(cell)을 순회한다.
                Iterator<Cell> cellIterator = sheet.getRow(i).cellIterator();

                String restaurantName = sheet.getRow(i).getCell(1).getStringCellValue(); //  업소명
                String restaurantType = sheet.getRow(i).getCell(3).getStringCellValue(); //  타입
                String visitorNumber = String.valueOf((int)sheet.getRow(i).getCell(4).getNumericCellValue()); //  방문자수

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

    private String getThisYearMonth() {
        //오늘 날짜
        Date now = new Date(); //현재시간 부르기
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM"); //시간 포맷팅하기
        String formattedNow = formatter.format(now); //현재시간에 포맷팅 적용

        return formattedNow;
    }

    private String getLocationName(String areaCode, String sigunguCode) {
        String locationName = "";

        switch(areaCode) {
            case "4":
                locationName = "대구";
                break;
            case "6":
                locationName = "부산";
                break;
            case "7":
                locationName = "울산";
                break;
            case "35":
                switch(sigunguCode) {
                    case "2":
                        locationName = "경주";
                        break;
                    case "11":
                        locationName = "안동";
                        break;
                    case "23":
                        locationName = "포항";
                        break;
                    default:
                        break;
                }
            case "36":
                switch(sigunguCode) {
                    case "1":
                        locationName = "거제";
                        break;
                    case "17":
                        locationName = "통영";
                        break;
                    default:
                        break;
                }
        }

        return locationName;
    }
}
