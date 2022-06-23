package kr.so.songjava.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis 설정
 */
@Configuration
@MapperScan(basePackages = "kr.so.songjava.mvc.repository")
public class MybatisConfiguration {

	@Bean
	public SqlSessionFactory sqlSessionFactory(@Autowired DataSource dataSource, 
			ApplicationContext applicationContext) throws Exception{
		SqlSessionFactoryBean factoryBean=new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/sql/*.xml"));		

		//mybatis camelCase 적용
		SqlSessionFactory factory=factoryBean.getObject();
		factory.getConfiguration().setMapUnderscoreToCamelCase(true);		
		return factoryBean.getObject();
	}
	

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(@Autowired SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	
}