package board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

//스프링은 애플리케이션 실행시 자동으로 설정되므로
//MultipartResolver 기능을 실행시점에 제외 시키기 위함
@SpringBootApplication(exclude= {MultipartAutoConfiguration.class})
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}
