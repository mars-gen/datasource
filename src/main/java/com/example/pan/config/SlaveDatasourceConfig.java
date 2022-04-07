package com.example.pan.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * <p>
 *  从库配置
 * </p>
 *
 * @author daShen
 * @since 2022-04-07
 */
@Configuration
@MapperScan(basePackages = "com.example.pan.mapper.test02",sqlSessionFactoryRef = "")
public class SlaveDatasourceConfig {

    @Bean(name = "test2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource getDateSource2() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "test2SqlSessionFactory")
    public SqlSessionFactory test2SqlSessionFactory(@Qualifier("test2DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/test02/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "SqlSessionTemplate")
    public SqlSessionTemplate test2sqlsessiontemplate(@Qualifier("test2SqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }
}
