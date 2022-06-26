package kr.net.macaronics.configuration;

import java.util.List;

import kr.net.macaronics.configuration.handler.BaseHandlerInterceptor;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import kr.net.macaronics.utils.pagination.MySQLPageRequestHandleMethodArgumentResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	/**
	 * 머시태시 템플릿을 html 확장자 명으로 변경
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		MustacheViewResolver resolver = new MustacheViewResolver();

		resolver.setCharset("UTF-8");
		resolver.setContentType("text/html;charset=UTF-8");
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");

		registry.viewResolver(resolver);
	}

	/**
	 * Locale 값이 변경되면 인터셉터가 동작한다. url의 query parameter에 지정한 값이 들어올 때 동작한다. ex)
	 * http://localhost:8080?lang=ko
	 * 
	 * @return
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		return interceptor;
	}

	
	/**
	 *  BaseHandlerInterceptor 빈 설정
	 */
	@Bean
	public BaseHandlerInterceptor baseHandlerInterceptor() {
		return new BaseHandlerInterceptor();
	}
	
	
	/**
	 * 인터셉터 등록 LocaleChangeInterceptor 를 스프링 컨테이너에 등록한다. WebMvcConfigurer 를 상속받고
	 * addInterceptors를 오버라이딩 한다.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 로그을 찍기 위해  - BaseHandlerInterceptor 인터셉터 등록
		registry.addInterceptor(baseHandlerInterceptor());
		
		//다국어 설정을 위한 localeChangeInterceptor 을 인터셉터에 등록
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	
	
//	@Bean
//	public ObjectMapper objectMapper() {
//		ObjectMapper objectMapper =new ObjectMapper();
//		SimpleModule simpleModule =new SimpleModule();
//		simpleModule.addSerializer(BaseCodeLabelEnum.class, new BaseCodeLabelEnumJsonSerializer());
//		objectMapper.registerModule(simpleModule);
//		return objectMapper;
//	}
//	
//	
//	
//	@Bean
//	public MappingJackson2JsonView mappingJackson2JsonView() {
//		MappingJackson2JsonView jsonView=new MappingJackson2JsonView();
//		jsonView.setContentType(MediaType.APPLICATION_JSON_VALUE);
//		jsonView.setObjectMapper(objectMapper());
//		return jsonView;		
//	}
	
	
	/** 페이징 처리 */	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		// 페이지 리졸버 등록
		resolvers.add(new MySQLPageRequestHandleMethodArgumentResolver());
	}
	
	@Bean
	public GlobalConfig globalConfig(){
		return new GlobalConfig();
	}
	
}