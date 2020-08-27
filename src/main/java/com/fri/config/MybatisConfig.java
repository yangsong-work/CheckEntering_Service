package com.fri.config;

import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.EnumTypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.fri.dao")
@EnableTransactionManagement
public class MybatisConfig {

    @Bean
    SqlSessionFactory sqlSessionFactory(ApplicationContext context, DataSource dataSource)
            throws Exception {
        org.apache.ibatis.session.Configuration myBatisConfiguration = new org.apache.ibatis.session.Configuration();
        myBatisConfiguration.setLogImpl(Slf4jImpl.class);
        myBatisConfiguration.setMapUnderscoreToCamelCase(true);
        // 默认值本身即是EnumTypeHandler, 这里显示声明为了明确
        myBatisConfiguration.setDefaultEnumTypeHandler(EnumTypeHandler.class);
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setConfiguration(myBatisConfiguration);
        //  sessionFactory.setTypeAliasesPackage("com.fri");
        sessionFactory.setMapperLocations(context.getResources("classpath*:com/fri/mapper/*.xml"));
        sessionFactory.setDataSource(dataSource);
        return sessionFactory.getObject();
    }


}
