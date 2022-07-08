package kr.net.macaronics.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import kr.net.macaronics.configuration.handler.BaseHandlerInterceptor;
import kr.net.macaronics.utils.pagination.MySQLPageRequestHandleMethodArgumentResolver;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

		
	private static final String WINDOW_FILE ="file:///";
	private static final String LINUX_FILE= "file:";
	
	
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
	public GlobalConfig config(){
		return new GlobalConfig();
	}
	
	
	
/**	 addResourceHandlers 설명)
WebMvcConfigurer interface를 상속받아 addResourceHandlers method를 오버 라이딩하고 리소스 등록 및 핸들러를 관리하는 
객체인 ResourceHandlerRegistry를 통해 리소스의 위치와 리소스와 매칭 될 url을 설정.

addResourceHandler : 리소스와 연결될 URL path를 지정(클라이언트가 파일에 접근하기 위해 요청하는 url)
localhost:8080/imagePath/** 

addResourceLocations: 실제 리소스가 존재하는 외부 경로를 지정합니다.
경로의 마지막은 반드시 " / "로 끝나야 하고, 로컬 디스크 경로일 경우 file:/// 접두어를 꼭 붙인다.
 
이렇게 설정하면 클라이언트로부터 http://호스트 주소:포트/imagePath/testImage.jpg 와 같은 요청이 들어 왔을 때 /home/uploadedImage/testImage.jpg 파일로 연결.
 
 예)
	private String connectPath = "/imagePath/**";
	private String resourcePath = "file:///home/uploadedImage";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(connectPath)
                .addResourceLocations(resourcePath);
    }
*/
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//업로드 파일 static resource 접근 경로
		String resourcePattern=config().getUploadResourcePath() +"**";
		log.info("addResourceHandlers  -resourcePattern   {}", resourcePattern);
		
		
		if(config().isLocal()) {
			//로컬(윈도우환경)			
			registry.addResourceHandler(resourcePattern)
			.addResourceLocations(WINDOW_FILE+config().getUploadFilePath());
			log.info("윈도우 환경");
			log.info(" resourcePattern -  {}", resourcePattern);
			log.info(" addResourceLocations -  {}", WINDOW_FILE+config().getUploadFilePath() );
		}if(config().isDev()) {
			//개발환경(윈도우환경)
			log.info("개발환경(윈도우환경)");
			registry.addResourceHandler(resourcePattern)
			.addResourceLocations(WINDOW_FILE+config().getUploadFilePath());
			log.info(" resourcePattern -  {}", resourcePattern);
			log.info(" addResourceLocations -  {}", WINDOW_FILE+config().getUploadFilePath() );
		}else {
			//리눅스 또는 유닉스 환경
			log.info("리눅스 또는 유닉스 환경");
			registry.addResourceHandler(resourcePattern)
			.addResourceLocations(LINUX_FILE+config().getUploadFilePath());
		}				
	}
	
}



