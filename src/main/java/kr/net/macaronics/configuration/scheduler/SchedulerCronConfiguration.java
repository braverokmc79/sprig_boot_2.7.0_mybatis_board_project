package kr.net.macaronics.configuration.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.net.macaronics.configuration.GlobalConfig;

@Configuration
public class SchedulerCronConfiguration {

	@Autowired
	private GlobalConfig config;
	
	
	/**  빈등록  global properties 값을 가져온다
	 * 
	 *  */
	@Bean
	public String schedulerCronExample1() {
		return config.getSchedulerCronExample1();
	}
	
	@Bean
	public boolean isLocal() {
		return config.isLocal();
	}
	
	
	
	
}
