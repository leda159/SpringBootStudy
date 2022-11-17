package board.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
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
	
	//파일 업로드
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = 
				new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		//업로드시 파일 한개당 최대 5M로 제한
		commonsMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024);
		return commonsMultipartResolver;
	}
}
