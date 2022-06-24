package kr.so.songjava.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags="인덱스 API")
public class IndexController {

	
	@GetMapping({"","/"})
	@ApiOperation(value="인덱스화면", notes="인덱스 홈 화면을 조회할수 있습니다.")
	public String index(){		
		return "index";
	}
	
	
}
