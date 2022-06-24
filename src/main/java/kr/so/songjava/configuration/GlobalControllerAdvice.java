package kr.so.songjava.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.so.songjava.configuration.http.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** 
 * 예외 처리
 * @RestControllerAdvice는 @ControllerAdvice+@ResponseBody의 역할을 한다.
 * **/
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalControllerAdvice {

	private final MessageSource messageSource;
	

	@ExceptionHandler(IllegalArgumentException.class)
	public BaseResponse<?> handleIllegalArgumentException(IllegalArgumentException e){
		String code=e.getMessage();
		String message=messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
		log.info("code : {} ", code);
		log.info("message : {} ", message);
		log.info("e  :{} ", e);
		
		return new BaseResponse<Object>("error", null, message);
	}
	
}
