package com.trip.finalProject.packaged.web;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.trip.finalProject.attachedFile.service.AttachedFileVO;
import com.trip.finalProject.packaged.service.PackageService;
import com.trip.finalProject.tripMate.service.TripMateService;

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
	
	@Autowired
	TripMateService tripMateService;
    
	
	//에디터 이미지 업로드
	@PostMapping("/common/upload")
	public ModelAndView image(MultipartHttpServletRequest request)
			throws Exception {
		String saveName = null;
		String loadingPathUrl = null;
		String originalFileName = null;
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

		ModelAndView mav = new ModelAndView(jsonView);

		List<MultipartFile> uploadFile = request.getFiles("upload");
		for (MultipartFile mf : uploadFile) {
		
			if (mf.getContentType().startsWith("image") == false) {
				System.err.println("this file is not image type");
				return null;
			}
			if (mf.getSize() > 0) {
				originalFileName = mf.getOriginalFilename();
				String ext = originalFileName.substring(originalFileName.indexOf("."));
				String newFileName = UUID.randomUUID() + ext;

				// 날짜 폴더 생성
				String folderPath = makeFolder();
				
				String savePath = folderPath + "/" + newFileName;
				// 파일을 저장할 경로
				saveName = uploadPath + "/" + setImagePath(savePath);
	
				loadingPathUrl = "/upload/" + setImagePath(savePath);

				// 업로드된 파일을 실제 경로로 복사
				File file = new File(saveName);

				try {
					mf.transferTo(file);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		mav.addObject("uploaded", true);
		mav.addObject("url",loadingPathUrl);
		mav.addObject("filename",originalFileName);


		return mav;
	}
	
	
	//첨부파일 이미지 업로드
	@PostMapping("/common/mainImageUpload")
	@ResponseBody
	public List<AttachedFileVO> uploadMainFile(MultipartFile[] mainImage){
		
		List<AttachedFileVO> imageList = new ArrayList<>();
		String saveName = null;
		String loadingPathUrl = null;
		for (MultipartFile mf : mainImage) {
			
			if (mf.getContentType().startsWith("image") == false) {
				System.err.println("this file is not image type");
				return null;
			}
			if (mf.getSize() > 0) {
				String originalFileName = mf.getOriginalFilename();
				String ext = originalFileName.substring(originalFileName.indexOf("."));
				String newFileName = UUID.randomUUID() + ext;

				// 날짜 폴더 생성
				String folderPath = makeFolder();
				
				String savePath = folderPath + "/" + newFileName;
				// 파일을 저장할 경로
				saveName = uploadPath + "/" +setImagePath(savePath);
				
				loadingPathUrl = "/upload/" + setImagePath(savePath);
				
				String imgType = "U2";

				// 업로드된 파일을 실제 경로로 복사
				File file = new File(saveName);

				try {
					mf.transferTo(file);
					// tarsferTo 메소드가 알아서 업로드 해줌 / 문제는 경로
					// uploadFile에 파일을 업로드 하는 메서드 transferTo(file)
				} catch (IOException e) {
					e.printStackTrace();
				}
				AttachedFileVO attachedFileVO = new AttachedFileVO();
				attachedFileVO.setOriginImg(originalFileName);
				attachedFileVO.setSavedImg(saveName);
				attachedFileVO.setImgType(imgType);
				//실제 화면에 이미지 뿌려주는 url
				attachedFileVO.setLoadingImg(loadingPathUrl);
				imageList.add(attachedFileVO);
			}
		}
		
		return imageList;
		
	}
	private String setImagePath(String savePath) {
		// TODO Auto-generated method stub
		return savePath.replace(File.separator, "/");
	}
	
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String savedImg){
	
		//System.out.println(savedImg);
		File file= new File(savedImg);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders headers = new HttpHeaders();
			
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
			
			
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
		
	}


	@PostMapping("/common/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String savedImg){
		File file;
		
		try {
			file = new File(URLDecoder.decode(savedImg,"UTF-8"));
			System.out.println(file);
			file.delete();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("delete",HttpStatus.OK);
	}


	@PostMapping("/common/deleteImg")
	public ResponseEntity<String> deleteImages(@RequestBody Map<String, List<String>> requestData) throws Exception {
		List<String> filenames = requestData.get("filenames");
		
		
		try {
			for (String filename : filenames) {
				File imageFile = new File(uploadPath + "/" + filename);
				System.out.println(imageFile);
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
	
//	@PostMapping("insertEditor")
//	public String insertEditor(@RequestBody String data) {
//		//System.out.println(data);
//		PackageVO packageVO = new PackageVO();
//		packageVO.setContent(data);
//		//System.out.println(objData.getName());
//		packageService.insertEdirotInfo(packageVO);
//		return data;
//	}
	
	//@PostMapping("insertFormData")
	//@ResponseBody
	//public int insertFormData(@RequestBody PackageVO combinedData) {
	//	System.out.println(combinedData.getPrice());
	//	System.out.println(combinedData.getContent());
	//	return packageService.insertEdirotInfo(combinedData);
	//}
	
	

}
