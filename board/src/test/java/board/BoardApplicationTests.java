package board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

//JUnit 4버전으로 테스트
//@RunWith(SpringRunner.class)
//JUnit 5버전으로 테스트
@ExtendWith(SpringExtension.class)
@SpringBootTest
class BoardApplicationTests {

	//자동주입
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Test
	public void testSqlSession() {
		System.out.println(sqlSession.toString());
	}
}





