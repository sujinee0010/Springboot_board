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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/application.properties")
public class DatabaseConfiguration {

	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		return new DataSourceTransactionManager(dataSource());
	}
	

	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	@ConfigurationProperties(prefix="mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig(){
		return new org.apache.ibatis.session.Configuration();
	}
	
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public	HikariConfig hikariConfig() {
		
		return new HikariConfig();
	}
	
	@Bean
	public DataSource dataSource() throws Exception{
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println(dataSource.toString());
		return dataSource;
		}
	
	
	@Bean 
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml"));
		sessionFactory.setConfiguration(mybatisConfig());
		return sessionFactory.getObject();
		
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
