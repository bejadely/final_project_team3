package com.trip.finalProject.excelUpload.web;

import com.trip.finalProject.excelUpload.service.ExcelUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ExcelUploadController {

    @Autowired
    ExcelUploadService excelUploadService;

    /*
    //  엑셀 업로드 페이지
    @GetMapping("/excel")
    public String moveExcelUpload() {

        return "excelUpload/"
    }

     */

    @PostMapping("/excel")
    @ResponseBody
    public Integer uploadExcel(String fileType, String areaCode, String sigunguCode, String yearMonth, MultipartFile file) {
        return excelUploadService.uploadExcel(fileType, areaCode, sigunguCode, yearMonth, file);
    }

    @DeleteMapping("/excel")
    @ResponseBody
    public Integer deleteExcel(String fileType, String areaCode, String sigunguCode, String yearMonth) {
        return excelUploadService.deleteExcel(fileType, areaCode, sigunguCode, yearMonth);
    }
}
