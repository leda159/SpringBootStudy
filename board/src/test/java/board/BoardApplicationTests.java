package board;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
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





