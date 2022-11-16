package board.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//자바 기반의 설정 파일임을 선언하는 어노테이션
@Configuration
//스프링 부트 설정 정보를 읽어오는 어노테이션
@PropertySource("classpath:/application.properties")
public class DataBaseConfiguration {

	//ApplicationContext
	//별도의 설정 정보를 참고하여 Bean 생성,관계설정등 
	//총괄적인 일을 하는 인터페이스
	@Autowired
	private ApplicationContext applicationContext;
	
	
	//application.properties 파일에서 Connection
	//Pool Hikari CP 정보를 읽어와서 Bean등록
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}

	//데이터베이스 연결 설정 Bean 등록
	@Bean
	public DataSource dataSouce() {
		DataSource dataSource = 
				new HikariDataSource(hikariConfig());
		
		return dataSource;
	}
	
	//테이블 컬럼과 자바 필드 매핑처리 관련 
	@Bean
	@ConfigurationProperties(prefix="mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig(){
		return new org.apache.ibatis.session.Configuration();
	}
	
	//SqlSessionFactory?
	//SqlSession 을 생성하기 위해 선언
	//데이터베이스 연결과 sql 실행에 대한 모든 처리를
	//하는 중요한 객체
	@Bean
	public SqlSessionFactory 
	    sqlSessionFactory(DataSource dataSource) throws Exception {
 
		SqlSessionFactoryBean sqlSessionFactoryBean 
			 = new SqlSessionFactoryBean();
		
		//데이터베이스 연결 설정 정보 
		sqlSessionFactoryBean.setDataSource(dataSource);
		//MyBatis 실행을 위한 XML 파일 위치 선언
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml"));
		
		//p63 Mybatis 설정정보를 대입
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		
		return sqlSessionFactoryBean.getObject();
		
	}
	
	//SqlSessionTemplate?
	//MyBatis 연동 핵심 모듈
	//SqlSession을 구현하고 스레드에 안전한
	//여러개의 DAO나 mapper에서 공유 가능
	//Session에 대해 Close,Commit,RollBack 포함한
	//생명주기(life cycle)을 관리함
	@Bean
	public SqlSessionTemplate 
	   sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		  return new SqlSessionTemplate(sqlSessionFactory);
	}
}





