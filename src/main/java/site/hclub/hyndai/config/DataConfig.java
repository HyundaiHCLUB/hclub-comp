package site.hclub.hyndai.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = "site.hclub.hyndai.mapper")
@PropertySource("classpath:application.yml")
public class DataConfig {

    @Value("${driver-class-name}")
    private String driverClassName;
    @Value("${jdbc-url}")
    private String jdbcUrl;
    @Value("${user-name}")
    private String userName;
    @Value("${password}")
    private String password;

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory(dataSource()));
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        // Register type aliases
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.getTypeAliasRegistry().registerAlias("Team", site.hclub.hyndai.domain.Team.class);
        configuration.getTypeAliasRegistry().registerAlias("MemberTeam", site.hclub.hyndai.domain.MemberTeam.class);
        sessionFactoryBean.setConfiguration(configuration);

        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/*.xml");
        sessionFactoryBean.setMapperLocations(resources);
        return sessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(driverClassName);
        basicDataSource.setUrl(jdbcUrl);
        basicDataSource.setUsername(userName);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }
}
