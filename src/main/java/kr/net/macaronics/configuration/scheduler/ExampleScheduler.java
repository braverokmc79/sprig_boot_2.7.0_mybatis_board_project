package kr.net.macaronics.configuration.scheduler;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
public class ExampleScheduler {

	@Value("#{@isLocal}")
	private boolean local;
	
	/** 5초에 한번씩 실행 */
	//@Scheduled(cron ="#{@schedulerCronExample1}")
	public void schedule1() {
		log.info(" schedule1 동작하고 있음 {}, {} " ,local,  Calendar.getInstance().getTime());		
	}
	
	
}
