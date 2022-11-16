package board.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//해당 클래스가 Bean설정 파일임을 선언
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	
	//인텁셉터 추가
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor());
	}
}
