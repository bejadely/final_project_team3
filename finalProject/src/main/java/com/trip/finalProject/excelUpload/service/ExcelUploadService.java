package com.trip.finalProject.excelUpload.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelUploadService {


    Integer uploadExcel(String fileType, String areaCode, String sigunguCode, MultipartFile file);
}
