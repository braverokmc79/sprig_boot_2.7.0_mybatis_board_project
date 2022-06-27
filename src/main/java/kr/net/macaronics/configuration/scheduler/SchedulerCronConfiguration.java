package kr.net.macaronics.configuration.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.net.macaronics.configuration.GlobalConfig;

@Configuration
public class SchedulerCronConfiguration {

	/**  * global ~.properties 값을 읽어와	글로벌 변수설정하는 클래스 */
	@Autowired
	private GlobalConfig config;
	
	
	/**   global properties 값을 가져와 전역 사용할 수 있도록  빈등록 *  */	
	@Bean
	public String schedulerCronExample1() {
		return config.getSchedulerCronExample1();
	}
	
	
	@Bean
	public boolean isLocal() {
		return config.isLocal();
	}
	
	
	@Bean
	public boolean isDev() {
		return config.isDev();
	}
	
	@Bean
	public boolean isProd() {
		return config.isProd();
	}
	
	
	
}
