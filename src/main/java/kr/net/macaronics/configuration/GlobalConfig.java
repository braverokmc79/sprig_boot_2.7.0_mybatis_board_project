package kr.net.macaronics.configuration;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
	글로벌 변수
 *
 */
@Slf4j
@Data
public class GlobalConfig {

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private ResourceLoader resourceLoader;		
	
	private String uploadFilePath;
	
	private String schedulerCronExample1;
	
	
	private boolean local;
	private boolean dev;
	private boolean prod;
	
	
	
	
	/**
	 * @PostConstruct는 의존성 주입이 이루어진 후 초기화를 수행하는 메서드이다. 
	 * @PostConstruct가 붙은 메서드는 클래스가 service를 수행하기 전에 발생한다.
	 *  이 메서드는 다른 리소스에서 호출되지 않는다해도 수행된다. 
		
		@PostConstruct의 사용 이유
		1) 생성자가 호출되었을 때, 빈은 초기화되지 않았음(의존성 주입이 이루어지지 않았음) 
		이럴 때 @PostConstruct를 사용하면 의존성 주입이 끝나고 실행됨이 보장되므로 빈의 초기화에 대해서 걱정할 필요가 없다. 
		2) bean 의 생애주기에서 오직 한 번만 수행된다는 것을 보장한다. (어플리케이션이 실행될 때 한번만 실행됨)
		따라서 bean이 여러 번 초기화되는 걸 방지할 수 있다
		여기서는, ApplicationContext, ResourceLoader 가 의존성 주입이 완료되었는지에 대해 염려할 필요가 없다. 
	 */
	@PostConstruct
	public void init(){
		log.info("GlobalConfig-init" );
		String[] activeProfiles =context.getEnvironment().getActiveProfiles();
		String activeProfile="local"; // 기본위치 local
		if(ObjectUtils.isNotEmpty(activeProfiles)) {
			activeProfile=activeProfiles[0];
		}
		String resourcePath=String.format("classpath:globals/global-%s.properties", activeProfile);
		try {
			Resource resource=resourceLoader.getResource(resourcePath);
			Properties properties=PropertiesLoaderUtils.loadProperties(resource);
			this.uploadFilePath=properties.getProperty("uploadFile.path");
			this.schedulerCronExample1 =properties.getProperty("scheduler.cron.example1");
			
			this.local=activeProfile.equals("local");
			this.dev=activeProfile.equals("dev");
			this.prod=activeProfile.equals("prod");
			
		}catch (Exception e) {
			log.error("e", e);
		}
		
	}
	
	
	
	public String getUploadFilePath() {
		return uploadFilePath;
	}
	
	
}
