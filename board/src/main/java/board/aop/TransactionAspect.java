package board.aop;

import java.util.Collections;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

//AOP를 이용하여 트랜잭션을 사용
@Configuration
public class TransactionAspect {

	//Service 클래스를 생성시 매번 @Transaction을 적용해야 하는 불편함 점이 있어
	//PointCut을 이용하여 Impl로 끝나는 클래스에 대해 트랜잭션 처리를 하기 위함
	
	// "*"  >> 모든 함수에서 적용하겠다 라는 의미
	// execution >> 어디에 적용시킬지
	// board 하위 패키지에 Service 안에 끝에Impl로 끝나는 파일에 적용 하겠다 라는 의미
	private static final String AOP_TRANSACTION_METODNAME = "*";
	private static final String AOP_TRANSACTION_EXPRESSION = 
			"execution(* board..service.*Impl.*(..))";
	
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	
	//TransactionInterceptor ?
	//트랜잭션 처리를 하는 코드를 Business Login으로 부터 분리하여
	//처리하는 기법인 선언적 트랜잭션 처리를 제공하는 AOP interceptor이다
	@Bean
	public TransactionInterceptor transactionAdvice() {
		
		//트랜잭션 속성을 지정하는 객체
		MatchAlwaysTransactionAttributeSource source =
				new MatchAlwaysTransactionAttributeSource();
		
		//특정예외가 발생시 Rollback 규칙을 적용하여 Rollback처리를 해야 하는지 확인하는
		//transactionAttribute을 구현한다.
		RuleBasedTransactionAttribute transactionAttribute = 
				new RuleBasedTransactionAttribute();
		
		//모든 메서드에 대해 트랜잭션 처리
		transactionAttribute.setName(AOP_TRANSACTION_METODNAME);
		//모든 예외발생시 rollback 규칙을 적용하되 싱글톤 패턴을 적용하여 한번만 
		//인스턴스를 생성하고이후부터는 재사용 처리
		transactionAttribute.setRollbackRules(Collections.singletonList
				(new RollbackRuleAttribute(Exception.class)));
		//트랜잭션 속성을 필드에 대입한다.
		source.setTransactionAttribute(transactionAttribute);
		
		return new TransactionInterceptor(transactionManager, source);
	}
	
	//advisor = Adivce(공통기능) + pointCut(공통기능을 
	//어느 Targer의 JoinPoint에 적용시킬건지 결정하는선언
	@Bean
	public Advisor transactionAdviceAdcisor() {
		//PointCut 표현식을 지원하는 객체
		AspectJExpressionPointcut pointcut = 
				new AspectJExpressionPointcut();
		//PointCut을 선언
		pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);
		//DefaultPointcutAdvisor?
		//PointCut, Advice를 결합시켜주는 역할을 하는 객체
		return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
	}
}







