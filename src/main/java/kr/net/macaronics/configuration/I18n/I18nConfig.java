package kr.net.macaronics.configuration.I18n;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * 다국어 처리 
 */
@Configuration
public class I18nConfig  {

	/** 스프링이 클라이언트의 언어,국가 정보를 인식하게 하는 메소드  여기서는 쿠키의 값을 저장하여 사용
	 * 
	 *  AcceptHeaderLocaleResolver : Http 헤더의 Accept-Language의 값을 사용한다. (기본값)
		CookieLocaleResolver : 쿠키의 값을 저장하여 사용한다.
		SessionLocaleResolver : 세션에 값을 저장하여 사용한다.
		FixedLocaleResolver : 요청과 관계없이 default locale 사용한다.
	 *  */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.getDefault());
        resolver.setCookieName("lang");
        return resolver;
    }



    /** 스프링이 작성한 언어 리소스들을 사용할 수 있게 등록,설정 */
    @Bean
    public MessageSource messageSource() {
        // 지정한 시간마다 다시 리로드 하도록 한다.
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // 언어 리소스들이 있는 경로를 지정한다.
        messageSource.setBasename("classpath:/i18n/messages");
        messageSource.setDefaultEncoding("UTF-8"); // 인코딩
        messageSource.setCacheSeconds(1); //개발시 
        //messageSource.setCacheSeconds(10 * 60); // 리로드 시간
        messageSource.setDefaultLocale(Locale.KOREA);
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }


    
}