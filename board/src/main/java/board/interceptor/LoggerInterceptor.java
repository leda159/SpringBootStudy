package board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggerInterceptor extends HandlerInterceptorAdapter {

	//로깅 선언
		private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//특정 Controller 수행전 처리되는 메서드
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("============== START ============");
		log.debug("Request URI \t: " + request.getRequestURI() );
		return super.preHandle(request, response, handler);
	}
	
	//특정 Controller 수행후 처리되는 메서드
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.debug("============== END ============");
	}


}
