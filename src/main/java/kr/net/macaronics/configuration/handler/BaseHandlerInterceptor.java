package kr.net.macaronics.configuration.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kr.net.macaronics.annotation.RequestConfig;
import kr.net.macaronics.configuration.exception.BaseException;
import kr.net.macaronics.configuration.http.BaseResponseCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseHandlerInterceptor implements HandlerInterceptor {

  
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //log.info("\n\n\n*** [preHandle] URI : {} - 시작 " , request.getRequestURI());
		
        //preHandle 및 어노테이션 상용법 - 로그인 테스트
        if(handler instanceof HandlerMethod) {
        
        	HandlerMethod handlerMethod =(HandlerMethod) handler;
        	RequestConfig requestConfig=handlerMethod.getMethodAnnotation(RequestConfig.class);
        	
        	if(requestConfig!=null) {
        		//로그인 체크가 필수인 경우
        		if(requestConfig.loginCheck()) {
        			throw new BaseException(BaseResponseCode.LOGIN_REQUIRED.name());
        		}
        	}
        }
        
		return true;
    }
	

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	//log.info("\n***[postHandle] URI  : {}  - 종료 \n\n\n", request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex) throws Exception {
    	//log.info("[afterCompletion]");
    }
	
}
