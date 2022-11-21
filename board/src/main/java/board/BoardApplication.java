package board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//테이블 추가,삭제,수정등 작업일 일어날 경우 등록시간,수정시간
//등을 자동으로 반영
@EnableJpaAuditing
//basePackages로 선언된 board 패키지에서 JPA Entity로
//선언된 클래스를 검색하여 날짜와 관련된 JSR310 API를 사용
//하도록 한다.
@EntityScan(basePackageClasses= {Jsr310JpaConverters.class},basePackages= {"board"})
//스프링은 애플리케이션 실행시 자동으로 설정되므로
//MultipartResolver 기능을 실행시점에 제외 시키기 위함
@SpringBootApplication(exclude= {MultipartAutoConfiguration.class})
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}
