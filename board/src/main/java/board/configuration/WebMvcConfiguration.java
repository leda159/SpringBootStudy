package board.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import board.interceptor.LoggerInterceptor;

@Configuration
/* @EnableWebMvc */
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Autowired
	WebApplicationContext webApplicationContext;    
	
	@Override 
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(60 * 60 * 24 * 365); 
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(60 * 60 * 24 * 365); 
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/").setCachePeriod(60 * 60 * 24 * 365); 
        registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/").setCachePeriod(60 * 60 * 24 * 365); 
	}

	//인터셉터를 추가
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor());
	}
	
	//파일 업로드 처리를 위한 설정 추가 10.06
	//@Bean 과 @Component 의 차이
	//@Bean:스프링에서는 클래스를 빈등록을 해줘야 실행되는데
	//@Bean은 스프링에서 제공해주는 클래스의 경우 이 어노테이션을 사용하고
	//@Component는 개발자가 작성한 클래스를 빈등록하는 경우 선언한다.
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver =
				new CommonsMultipartResolver();
		
		//인코딩 방식 utf-8 지정
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		//파일업로드시 파일 한개당 크기를 5M로 제한
		commonsMultipartResolver.setMaxUploadSizePerFile(5*1024*1024);
		
		return commonsMultipartResolver;
	}
	
	//thymeleaf를 처리하기 위한 Resolver 선언
	@Bean
	public ITemplateResolver templateResolver() {
	  SpringResourceTemplateResolver templateResolver = 
			  new SpringResourceTemplateResolver();
	  templateResolver.setTemplateMode("HTML5");
	  templateResolver.setPrefix("classpath:/templates/");
	  templateResolver.setSuffix(".html");
	  templateResolver.setCharacterEncoding("UTF-8");
	  templateResolver.setCacheable(false);
	  return templateResolver;
	}
	 
	//Thymeleaf파일을 실행하기 위한 템플릿엔진 선언
	@Bean 
	public SpringTemplateEngine templateEngine() { 

	    SpringTemplateEngine templateEngine 
	        = new SpringTemplateEngine();

	    templateEngine.setTemplateResolver(templateResolver());
	    templateEngine.setEnableSpringELCompiler(true);

	    return templateEngine;
	}   

	@Bean
	ThymeleafViewResolver configureViewResolvers() {

	    ThymeleafViewResolver resolver 
	        = new ThymeleafViewResolver();

	    resolver.setTemplateEngine(templateEngine());
	    //기본적으로 thymeleaf를 먼저 찾는다. 
	    resolver.setOrder(1);
	    resolver.setViewNames(new String[]{"/*"});
	    //한글 인코딩 처리
	    resolver.setCharacterEncoding("UTF-8");

	    return resolver;
	}

	//jsp 파일을 실행시키기 위한 Resolver 선언
	@Bean
	public ViewResolver  getUrlBasedViewResolver() {     

		InternalResourceViewResolver viewResolver 
		     = new InternalResourceViewResolver();

	    viewResolver.setViewClass(JstlView.class);
	    //jsp파일이 있는 경로 선언
	    viewResolver.setPrefix("/WEB-INF/jsp/");
	    viewResolver.setSuffix(".jsp");
	    viewResolver.setOrder(2);

	    return viewResolver;        
	}

}