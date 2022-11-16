package sample;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//http://localhost:8080/hello
@Controller
@RequestMapping("/hello")
public class HelloController {

	@GetMapping
	public String hello() {
		
		///WEB-INF/jsp/ 위치의 hello.jsp 실행
		return "hello";
	}
}





