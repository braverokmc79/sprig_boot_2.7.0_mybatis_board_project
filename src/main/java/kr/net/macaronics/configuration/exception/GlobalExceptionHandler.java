package kr.net.macaronics.configuration.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import kr.net.macaronics.configuration.http.BaseResponse;
import kr.net.macaronics.configuration.http.BaseResponseCode;
import lombok.extern.slf4j.Slf4j;

/**
 *  * @RestControllerAdvice는 @ControllerAdvice+@ResponseBody의 역할을 한다.
 * 기본은 @ControllerAdvice 어노테이션이 존재하는데,
 *  Json, Xml Format으로 return 해줄 경우 대신 사용한다. 
 *  웹 어플리케이션 전역에서 Exception 발생시, 해당 에러를 잡아 처리한다.
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@Autowired
	private MessageSource messageSource;

    @ExceptionHandler(BaseException.class)
    public BaseResponse<?> handleException(Exception ex){
        log.error("1.BaseException : GlobalExceptionHandler ===> {}, {}",ex.getMessage(), ex);
        String message=messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        return new BaseResponse<>(BaseResponseCode.ERROR.name(), ex.getMessage(),  message);
    }
    
    
    @ExceptionHandler({RuntimeException.class, Exception.class})
    public BaseResponse<?> error500(Exception ex){
        log.error("2.RuntimeException GlobalExceptionHandler ===> {}, {}",ex.getMessage(), ex);
        String message=ex.getMessage();
        if(message==null) {
        	message ="NullPointerException";
        }
        return new BaseResponse<>("error", message);	
    }
    
    

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResponse<?> handle404(NoHandlerFoundException exception) {
        String message = "존재하지 않는 URL입니다. : " +  exception.getRequestURL();
        return new BaseResponse<>("error" , BaseResponseCode.NOT_FOUND,   message);
    }



	@ExceptionHandler(IllegalArgumentException.class)
	public BaseResponse<?> handleIllegalArgumentException(IllegalArgumentException e){
		String code=e.getMessage();
		String message=messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
		log.info("code : {} ", code);
		log.info("message : {} ", message);
		log.info("e  :{} ", e);
		return new BaseResponse<Object>("error", "IllegalArgumentException", message);
	}
	
    
}