package com.javarush.uzienko.todolist.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

@AllArgsConstructor
@Configuration
@EnableTransactionManagement
public class ApplicationConfig {

    private final PropertiesService propertiesService;

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource());
        localSessionFactoryBean.setPackagesToScan("com.javarush.uzienko.todolist.domain");
        localSessionFactoryBean.setHibernateProperties(getHibernateProperties());
        return localSessionFactoryBean;
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setMaximumPoolSize(propertiesService.getMaximumPoolSize());
        dataSource.setDriverClassName(propertiesService.getDriverClassName());
        dataSource.setJdbcUrl(propertiesService.getConnectionUrl());
        dataSource.setUsername(propertiesService.getUsername());
        dataSource.setPassword(propertiesService.getPassword());
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(DIALECT, propertiesService.getDialect());
        properties.put(DRIVER, propertiesService.getDriverClassName());
        properties.put(HBM2DDL_AUTO, propertiesService.getHbm2ddl());
        properties.put(SHOW_SQL, propertiesService.getShowSql());
        return properties;
    }
}
