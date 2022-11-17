package board.common;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;


//해당 클래스는 예외발생시 전역에 대해 처리하는 객체임을 선언
@ControllerAdvice
public class ExceptionHandler {
	
	//LogBack을 이용하여 로그 출력
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(
			HttpServletRequest request, Exception exception) {
		
		ModelAndView mv = new ModelAndView("/error/error_default");
		
		//전달할 속성을 지정한다. 이름 >  "exception" 
		mv.addObject("exception", exception);
		log.error("exception", exception);
		return mv;
	}
}
