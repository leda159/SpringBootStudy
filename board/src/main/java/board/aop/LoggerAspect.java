package board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


//Bean 등록
@Component
//해당 클래스가 AOP개념을 사용하는 클래스임을 선언
@Aspect
public class LoggerAspect {
	
	//LogBack을 이용하여 로그 출력
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//공통기능을 핵심기능(메서드) 전후에 모두 적용하는 어노테이션
	// * : 접근제한자 뭐든 상관없음
	//board : 패키지 이름
	//.. : 하위 패키지
	//*Controller : 앞은 상관없고 뒤에 Controller가 있는 대상을 가르킴
	//*(..) : 매개변수가 0개 이상인 모든 메서드
	//*(*,*) : 매개변수가 2개인 모든 메서드
	@Around("execution(* board..controller.*Controller.*(..)) or execution(* board..service.*Imple.*(..))or execution(* board..mapper.*Mapper.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable{
		String type = "";
		//getSignature() : 호출되는 메서드의 모든 정보를 리턴
		//getDeclaringTypeName() : 공통기능이 적용되는 메서드를 포함하는 클래스명
		//indexOf : 문자열을 찾는다
		String name = joinPoint.getSignature().getDeclaringTypeName();
		if(name.indexOf("Controller") > -1) {
			type= "Controller \t: ";
			
		}else if(name.indexOf("Service") > -1) {
			type = "ServiceImple \t: ";
			
		}else if(name.indexOf("Mapper") > -1) {
			type = "Mapper \t: ";
		}
		log.debug("실행"+type + name + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}
}
