package sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//세가지 어노테이션의 역할을 수행
//1.@EnableAutoConfiguration
//스프링부트의 장점인 자동설정을 수행
//2.@ComponentScan
//해당 패키지에서 @Component,@Configuration,
//@Controller,@RestController등 어노테이션 검색
//3.@Configuration
//자바기반의 설정 파일임을 선언
@SpringBootApplication
public class SampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

}
