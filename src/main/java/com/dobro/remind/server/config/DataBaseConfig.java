package com.dobro.remind.server.config;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableJpaRepositories("com.dobro.remind.server.repository")
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan("com.dobro.remind.server")
public class DataBaseConfig {

    @Resource
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(env.getRequiredProperty("entity.package"));
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(getHibernateProperties());
        return factoryBean;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(env.getRequiredProperty("url"));
        dataSource.setDriverClassName(env.getRequiredProperty("driver"));
        dataSource.setUsername(env.getProperty("username"));
        dataSource.setPassword(env.getRequiredProperty("password"));

        dataSource.setInitialSize(Integer.valueOf(env.getRequiredProperty("initialSize")));
        dataSource.setMinIdle(Integer.valueOf(env.getRequiredProperty("minIdle")));
        dataSource.setMaxIdle(Integer.valueOf(env.getRequiredProperty("maxIdle")));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(env.getRequiredProperty("timeBetweenEvictionRunsMillis")));
        dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(env.getRequiredProperty("minEvictableIdleTimeMillis")));
        dataSource.setTestOnBorrow(Boolean.valueOf(env.getRequiredProperty("testOnBorrow")));
        dataSource.setValidationQuery(env.getRequiredProperty("validationQuery"));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory().getObject());

        return manager;
    }

    private Properties getHibernateProperties() {
        try {
            Properties properties = new Properties();
            InputStream stream = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
            properties.load(stream);
            return properties;
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't find hibernate.properties in classpath!", e);
        }
    }
}
