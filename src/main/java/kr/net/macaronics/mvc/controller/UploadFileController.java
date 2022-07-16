package kr.net.macaronics.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/file")
public class UploadFileController {

	
	
	/** 파일업로드 테스트 페이지 */
	@GetMapping("/fileUpload")
	public String fileUpload() {
		return "uploadFile/file_upload";
	}
	
	
	/** 파일 목록 페이지 */
	@GetMapping("/fileList")
	public String fileList() {
		return "uploadFile/file_list";
	}
	
}
