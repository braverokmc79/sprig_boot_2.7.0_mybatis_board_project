package kr.so.songjava.mvc.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Controller
@Api(tags="인덱스 API")
@Slf4j
public class IndexController {

	 @Autowired
	 private MessageSource messageSource;
	 
	@GetMapping({"","/"})
	@ApiOperation(value="인덱스화면", notes="인덱스 홈 화면을 조회할수 있습니다.")
	public String index(){				
		return "index";
	}
	
	
	
	
	@GetMapping("/i18n")
	@ResponseBody
	@ApiOperation(value="다국어테스트", notes="다국어테스트 입니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="name", value="이름", example = "홍길동"),
		@ApiImplicitParam(name="age", value="나이", example = "24"),
	})
	public String 다국어테스트(String name, String age){				
		log.info(messageSource.getMessage("welcome", new String[]{"kim"}, Locale.ENGLISH));
		log.info(messageSource.getMessage("welcome", new String[]{"kim"}, Locale.KOREAN));
					
		String code="login.info.error";
		String message =messageSource.getMessage(code, null, LocaleContextHolder.getLocale());  
		log.info("message  : {} " , message);
		
		String message2=messageSource.getMessage("testName", new String[]{name ,age}, LocaleContextHolder.getLocale());
		return message2;
	}
	
	
}
