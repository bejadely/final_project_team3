package com.trip.finalProject.packaged.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Controller
public class PackageController {
	
	
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@PostMapping("upload")
	public ModelAndView image(@RequestParam Map<String, Object> map,MultipartHttpServletRequest request) throws Exception {
		String uploadPath = null;
		
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView(); 
		
		ModelAndView mav = new ModelAndView(jsonView);

		List<MultipartFile> uploadFile = request.getFiles("upload");
		
		for (MultipartFile mf : uploadFile) {
			if (mf.getSize() > 0) {
				String originalFileName = mf.getOriginalFilename();
				System.out.println(originalFileName);
				String ext = originalFileName.substring(originalFileName.indexOf("."));
				System.out.println(ext);
				String newFileName = UUID.randomUUID() + ext;
				System.out.println(newFileName);
				/*
				 * String realPath2 = request.getServletContext().getRealPath("/");
				 * System.out.println(realPath2);
				 */
				
				Resource resource = resourceLoader.getResource("classpath:static/");
				
		        String realPath = resource.getFile().getAbsolutePath();
				
		        System.out.println("realPath : "+realPath);
		        
		        
//		        String absolutePath = uploadPath2;
//		        
//		        System.out.println("absolutePath: "+absolutePath);
//		        File file2 = new File(absolutePath);
//		        File file2 = new File(absolutePath, newFileName);
//		        mf.transferTo(file2);
		       
		        
		        
		        //파일을 저장할 경로
				String savePath = realPath + "/upload/" + newFileName;
		        
				System.out.println("savePath: "+savePath);
				
				//업로드된 파일 url 생성
				uploadPath = "./upload/" + newFileName; 
				
				System.out.println(uploadPath);
				
				//업로드된 파일을 실제 경로로 복사
				File file = new File(savePath);
		
				mf.transferTo(file);
			}
		}
		mav.addObject("uploaded", true);
		mav.addObject("url", uploadPath);

		return mav;
	}
}
