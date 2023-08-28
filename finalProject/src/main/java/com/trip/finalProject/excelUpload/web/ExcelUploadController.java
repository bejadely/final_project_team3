package com.trip.finalProject.excelUpload.web;

import com.trip.finalProject.excelUpload.service.ExcelUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public Integer uploadExcel(String fileType, String areaCode, String sigunguCode, MultipartFile file) {
        System.out.println("fileType = " + fileType);
        System.out.println("areaCode = " + areaCode);
        System.out.println("sigunguCode = " + sigunguCode);
        System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
        return excelUploadService.uploadExcel(fileType, areaCode, sigunguCode, file);
    }
}
