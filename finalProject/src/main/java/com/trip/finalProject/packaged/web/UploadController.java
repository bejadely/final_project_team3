package com.trip.finalProject.packaged.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.trip.finalProject.packaged.service.PackageService;
import com.trip.finalProject.packaged.service.PackageVO;

@RestController
public class UploadController {
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@Value("${file.loading.path}")
	private String loadingPath;

	// 정적 리소의 실제 경로르 얻어오는 메소드 제공
	//@Autowired
	//private ResourceLoader resourceLoader;
	
	@Autowired
	PackageService packageService;

	@PostMapping("upload")
	public ModelAndView image(@RequestParam Map<String, Object> map, MultipartHttpServletRequest request)
			throws Exception {
		//String uploadPathUrl = null;
		
		String loadingPathUrl = null;
		
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

		ModelAndView mav = new ModelAndView(jsonView);

		List<MultipartFile> uploadFile = request.getFiles("upload");

		for (MultipartFile mf : uploadFile) {
			if (mf.getContentType().startsWith("image") == false) {
				System.err.println("this file is not image type");
				return null;
			}
			if (mf.getSize() > 0) {
				String originalFileName = mf.getOriginalFilename();
				System.out.println(originalFileName);
				String ext = originalFileName.substring(originalFileName.indexOf("."));
				System.out.println(ext);
				String newFileName = UUID.randomUUID() + ext;
				System.out.println(newFileName);
				
				//저장경로
				//Resource resource = resourceLoader.getResource("classpath:static/");

				// 파일로 변환하는 메소드 -> getAbsolutePath File 객체의 메소드로 파일의 절대 경로를 문자열 형태로 반환
				//String realPath = resource.getFile().getAbsolutePath();

				//System.out.println("realPath : " + realPath);

				//String fileUrl = realPath + "/upload/" + newFileName;
				//System.out.println(fileUrl);

				// 날짜 폴더 생성
				String folderPath = makeFolder();

				
				String savePath = folderPath + File.separator + newFileName;
				// 파일을 저장할 경로
				String saveName = uploadPath + File.separator + savePath;
				
				//loadingPathUrl = loadingPath + savePath;
				loadingPathUrl = "/upload/" + savePath;
				
				System.out.println(loadingPathUrl);

				System.out.println("savePath: " + savePath);

				System.out.println("saveName: " + saveName);

				// 업로드된 파일 url 생성
				//uploadPathUrl = "./upload/" + newFileName;


				// 업로드된 파일을 실제 경로로 복사
				File file = new File(saveName);
//				File file2 = new File(fileUrl);
//				try (OutputStream outputStream = new FileOutputStream(file2)) {
//					byte[] buffer = new byte[4096]; // 더 큰 버퍼 크기를 사용할 수도 있습니다.
//					int bytesRead;
//					try (FileInputStream inputStream = (FileInputStream) mf.getInputStream()) {
//						while ((bytesRead = inputStream.read(buffer)) != -1) {
//							outputStream.write(buffer, 0, bytesRead);
//						}
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				try {
					mf.transferTo(file);
					// tarsferTo 메소드가 알아서 업로드 해줌 / 문제는 경로
					// uploadFile에 파일을 업로드 하는 메서드 transferTo(file)
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		mav.addObject("uploaded", true);
		mav.addObject("url",loadingPathUrl);


		return mav;
	}

	@PostMapping("/deleteImg")
	public ResponseEntity<String> deleteImages(@RequestBody Map<String, List<String>> requestData) throws Exception {
		List<String> filenames = requestData.get("filenames");
		
		//Resource resource = resourceLoader.getResource("classpath:static/");
		// 파일로 변환하는 메소드 -> getAbsolutePath File 객체의 메소드로 파일의 절대 경로를 문자열 형태로 반환
		//String realPath = resource.getFile().getAbsolutePath();
		 
		//String directory = realPath + "/upload/";
		
		//String folderPath = makeFolder();
		//String uploadDirectory = uploadPath;
		
		try {
			for (String filename : filenames) {
				File imageFile = new File(uploadPath + File.separator + filename);
				
				//File imageFile2 = new File(directory+filename);		
				if (imageFile.exists() && imageFile.delete()) {
					System.out.println("Image deleted: " + filename);
				} else {
					System.err.println("Error deleting image: " + filename);
				}
			}
			return ResponseEntity.ok("Images deleted successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error deleting images: " + e.getMessage());
		}
	}

	private String makeFolder() {
		// 1. 오늘 날짜를 가져움
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		// LocalDate를 문자열로 포멧
		// 자바는 폴더상하위구분시 /를 인식하지 못함, 이를 처리하기 위해 File.separator를 지원해줌, 이를 바탕으로 상하위 구분하라는거임
		// (참고)윈도우는 / 인식함
		String folderPath = str.replace("/", File.separator);
		File uploadPathFoler = new File(uploadPath, folderPath);
		// 여기서 uploadPath 라는 변수는 아까 config 폴더 > app.properties에서 설정한 우리의 경로를 이야기하는 것임
		// File newFile= new File(dir,"파일명");
		// 실제로 우리가 경로지정한 곳에서, 집어넣은 폴더가 실제로 local에 있는지 확인후, 만약 폴더가 없다면 폴더를 자동으로 만들어줌 >
		// 우리가 직접 폴더 만들 필요가 없음
		if (uploadPathFoler.exists() == false) {
			uploadPathFoler.mkdirs();
			// 만약 uploadPathFolder가 존재하지않는다면 makeDirectory하라는 의미입니다.
			// mkdir(): 디렉토리에 상위 디렉토리가 존재하지 않을경우에는 생성이 불가능한 함수
			// mkdirs(): 디렉토리의 상위 디렉토리가 존재하지 않을 경우에는 상위 디렉토리까지 모두 생성하는 함수
		}
		return folderPath;
	}
	
	@PostMapping("insertEditor")
	public String insertEditor(@RequestBody String data, PackageVO packageVO) {
		System.out.println(data);
		packageVO.setContent(data);
		packageService.insertEdirotInfo(packageVO);
		return "index";
	}
	


}
