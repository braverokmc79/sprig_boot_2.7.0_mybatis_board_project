package kr.net.macaronics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  //스케쥴러 사용 설정
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
