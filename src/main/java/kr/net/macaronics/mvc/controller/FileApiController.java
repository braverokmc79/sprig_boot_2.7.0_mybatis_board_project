package kr.net.macaronics.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.net.macaronics.configuration.GlobalConfig;
import kr.net.macaronics.configuration.http.BaseException;
import kr.net.macaronics.configuration.http.BaseResponse;
import kr.net.macaronics.configuration.http.BaseResponseCode;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/file")
@Api(tags="공통 프로젝트 프로퍼티 로컬, 개발, 운영 설정값 클래스로 관리 테스트 API")
@Slf4j
public class FileApiController {

	
	@Autowired
	private GlobalConfig globalConfig;
	
	
	@PostMapping("/save")
	@ApiOperation(value="업로드", notes = "")
	public BaseResponse<Boolean> save(@RequestParam("uploadFile") MultipartFile multipartFile) {
		if(multipartFile==null || multipartFile.isEmpty()) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL.name());
		}
		log.debug("config : {} ", globalConfig);
		String uploadFilePath=globalConfig.getUploadFilePath();
		log.info("uploadFilePath  :  {} " , uploadFilePath);
		String prefix=multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1, multipartFile.getOriginalFilename().length());
		String filename=UUID.randomUUID().toString()+"."+prefix;
		String pathname=uploadFilePath +filename;
	
		File dest =new File(pathname);
		
		if(!dest.isDirectory()) {
			dest.mkdirs();
		}
		
		try {
			multipartFile.transferTo(dest);
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return new BaseResponse<Boolean>(true);
	}
	
	
	
	
	
	
	
	
}
