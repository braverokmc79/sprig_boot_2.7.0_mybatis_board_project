package kr.net.macaronics.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.net.macaronics.configuration.GlobalConfig;
import kr.net.macaronics.configuration.http.BaseResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/file")
@Api(tags="공통 프로젝트 프로퍼티 로컬, 개발, 운영 설정값 클래스로 관리 테스트 API")
@Slf4j
public class FileApiController {

	
	@Autowired
	private GlobalConfig globalConfig;
	
	
	@GetMapping("/upload")
	@ApiOperation(value="업로드", notes = "")
	public BaseResponse<Boolean> save(){
		log.debug("config : {} ", globalConfig);
		String uploadFilePath=globalConfig.getUploadFilePath();
		log.info("uploadFilePath  :  {} " , uploadFilePath);		
		return new BaseResponse<Boolean>(true);
	}
	
}
