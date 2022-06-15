package com.ilyabogatskiy.coffee_shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.ilyabogatskiy.coffee_shop")
public class ApplicationConfig {

    private static final String PACKAGES_SCAN = "com.ilyabogatskiy.coffee_shop.models";

    private static final String JDBC_URL = "jdbc.url";
    private static final String JDBC_USERNAME = "jdbc.username";
    private static final String JDBC_PASSWORD = "jdbc.password";
    private static final String JDBC_DRIVER_CLASS_NAME = "jdbc.driverClassName";

    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String HIBERNATE_DDL_AUTO = "hibernate.hbm2ddl.auto";

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment environment) {
        var factoryBean = new LocalContainerEntityManagerFactoryBean();
        var vendorAdapter = new HibernateJpaVendorAdapter();

        factoryBean.setPackagesToScan(PACKAGES_SCAN);
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaProperties(hibernateProperties(environment));
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setJpaProperties(hibernateProperties(environment));

        return factoryBean;
    }

    @Bean
    public DriverManagerDataSource dataSource(Environment environment) {
        var dataSource = new DriverManagerDataSource();

        dataSource.setUrl(environment.getRequiredProperty(JDBC_URL));
        dataSource.setUsername(environment.getRequiredProperty(JDBC_USERNAME));
        dataSource.setPassword(environment.getRequiredProperty(JDBC_PASSWORD));
        dataSource.setDriverClassName(environment.getRequiredProperty(JDBC_DRIVER_CLASS_NAME));

        return dataSource;
    }

    private Properties hibernateProperties(Environment environment) {
        var properties = new Properties();

        properties.put(HIBERNATE_DIALECT, environment.getRequiredProperty(HIBERNATE_DIALECT));
        properties.put(HIBERNATE_SHOW_SQL, environment.getRequiredProperty(HIBERNATE_SHOW_SQL));
        properties.put(HIBERNATE_FORMAT_SQL, environment.getRequiredProperty(HIBERNATE_FORMAT_SQL));
        properties.put(HIBERNATE_DDL_AUTO, environment.getRequiredProperty(HIBERNATE_DDL_AUTO));

        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        var transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());

        return transactionManager;
    }
}