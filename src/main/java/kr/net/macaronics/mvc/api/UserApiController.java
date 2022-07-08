package kr.net.macaronics.mvc.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import kr.net.macaronics.mvc.domain.dto.UserDTO;

@RestController
@Api(tags="유저 API")
@RequestMapping("/api/user")
public class UserApiController {

	
	//비밀번호 예시 성공 값
	private static final String DATABASE_PASSWORD ="test1234"	;
		
	
	//@PostMapping("/confirm")
	@GetMapping("/confirm") //get 테스트용
	public ResponseEntity<UserDTO> confirm(@RequestParam String password){
		Assert.hasLength(password, "user.password.hasLength");
		Assert.isTrue(password.equals(DATABASE_PASSWORD) ,"user.password.isTrue");
		
		UserDTO userDTO=new UserDTO();
		userDTO.setUsername("개발자");
		userDTO.setEmail("test@gmail.com");
		return ResponseEntity.status(HttpStatus.OK).body(userDTO);
	}
	
	
}
