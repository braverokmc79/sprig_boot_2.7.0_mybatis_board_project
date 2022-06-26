package kr.net.macaronics.configuration.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.net.macaronics.configuration.http.BaseException;
import kr.net.macaronics.configuration.http.BaseResponse;
import kr.net.macaronics.configuration.http.BaseResponseCode;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@Autowired
	MessageSource messageSource;

    @ExceptionHandler(BaseException.class)
    public BaseResponse<?> handleException(Exception ex){
        log.error("* GlobalExceptionHandler ===> {}, {}",ex.getMessage(), ex);
        String message=messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        return new BaseResponse<>(BaseResponseCode.ERROR.name(), ex.getMessage(),  message);
    }
    
    
}